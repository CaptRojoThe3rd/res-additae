package com.captrojo.resadditae.extprop;

import java.util.ArrayList;

import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.SpellTargetData;
import com.captrojo.resadditae.magic.UseType;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.Alerts;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.main.SerialHlpr;
import com.captrojo.resadditae.packet.toclient.PacketPlayerExtProps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class RAPlayerProperties implements IExtendedEntityProperties
{
	public static final String KEY = "RAPlayerProperties";
	
	public static final int MANA_LVL_MAX = 20;
	public static final int MAGIC_SKILL_LVL_MAX = 100;
	
	public static RAPlayerProperties get(EntityPlayer player)
	{
		RAPlayerProperties rpp = (RAPlayerProperties) player.getExtendedProperties(KEY);
		if (rpp == null) {
			rpp = new RAPlayerProperties(player);
			rpp.reset();
			player.registerExtendedProperties(KEY, rpp);
		}
		return rpp;
	}
	
	public static void reset(EntityPlayer player)
	{
		RAPlayerProperties rpp = get(player);
		NBTTagCompound nbt = new NBTTagCompound();
		(new RAPlayerProperties(player)).saveNBTData(nbt);
		rpp.loadNBTData(nbt);
	}
	
	public static void transfer(EntityPlayer old_player, EntityPlayer new_player)
	{
		RAPlayerProperties rpp = get(old_player);
		if (new_player.getExtendedProperties(KEY) == null) {
			new_player.registerExtendedProperties(KEY, rpp);
		} else {
			/* Just in case */
			RAPlayerProperties rpp2 = RAPlayerProperties.get(new_player);
			NBTTagCompound nbt = new NBTTagCompound();
			rpp.saveNBTData(nbt);
			rpp2.loadNBTData(nbt);
		}
	}
	
	private boolean update_scheduled = false;
	
	public EntityPlayer player;
	int periodic_update_counter;
	
	public boolean allow_charm_helping_players;
	public boolean allow_charm_helping_entities;
	public boolean allow_charm_harming_players;
	public boolean allow_charm_harming_entities;
	public byte charm_tamed_mob_behavior;
	
	public int mana_level;
	public int mana;
	public int mana_max;
	int mana_recharge_counter;
	
	public int magic_skill_level;
	public ArrayList<LearnedSpell> learned_spells;
	
	public LearnedSpell[] spell_slots;
	public int active_spell;
	public int spell_active_time;
	public boolean[] active_continuous_spells;
	public int[] spell_cooldowns;
	public int[] spell_cooldown_starts;
	public SpellTargetData spell_target;
	
	public ItemStack wand_item;
	
	public RAPlayerProperties(EntityPlayer player)
	{
		this.player = player;
		this.periodic_update_counter = 0;
		
		this.learned_spells = new ArrayList<LearnedSpell>();
		this.spell_slots = new LearnedSpell[6];
		this.active_spell = -1;
		this.active_continuous_spells = new boolean[6];
		this.spell_cooldowns = new int[6];
		this.spell_cooldown_starts = new int[6];
		this.spell_target = new SpellTargetData();
		
		this.reset();
	}
	
	public void reset()
	{
		this.allow_charm_helping_players = true;
		this.allow_charm_helping_entities = false;
		this.allow_charm_harming_players = false;
		this.allow_charm_harming_entities = true;
		this.charm_tamed_mob_behavior = 0;
		
		this.mana_level = 1;
		this.magic_skill_level = 1;
		this.mana = 0;
		
		this.load();
	}
	
	/* Do initialization after deserialization or loading from NBT */
	public void load()
	{
		this.mana_max = this.mana_level * 100;
	}
	
	public void tick()
	{
		boolean updated = this.update_scheduled;
		
		this.periodic_update_counter--;
		if (this.periodic_update_counter <= 0) {
			this.periodic_update_counter = 20;
			updated = true;
		}
		
		if (this.mana < this.mana_max) {
			if (this.mana_recharge_counter > 0) {
				this.mana_recharge_counter--;
			} else {
				this.mana = Math.min(this.mana + 1, this.mana_max);
				this.mana_recharge_counter = 3;
			}
			updated = true;
		}
		
		for (int i = 0; i < this.spell_cooldowns.length; i++) {
			if (this.spell_cooldowns[i] > 0) {
				this.spell_cooldowns[i]--;
				updated = true;
			}
		}
		
		if (this.isSpellActive()) {
			this.spell_active_time++;
			LearnedSpell ls = this.getActiveSpell();
			if (ls != null) {
				ls.spell.tickWhileActive(this.player.worldObj, this.player, this, ls, this.active_spell);
			}
			if (ls.spell.max_use_time < this.spell_active_time) {
				this.deactivateSpell(this.active_spell);
			}
			updated = true;
		} else {
			if (this.spell_active_time > 0) {
				this.spell_active_time = 0;
				updated = true;
			}
		}
		for (int i = 0; i < this.active_continuous_spells.length; i++) {
			if (this.active_continuous_spells[i]) {
				LearnedSpell ls = this.spell_slots[i];
				if (ls != null) {
					ls.spell.tickWhileActive(this.player.worldObj, this.player, this, ls, i);
				}
			}
		}
		
		if (updated && this.player != null) {
			this.sendUpdate();
		}
		this.update_scheduled = false;
	}
	
	@SideOnly(Side.CLIENT)
	public void tickClient()
	{
		if (this.isSpellActive()) {
			LearnedSpell ls = this.getActiveSpell();
			if (ls != null) {
				ls.spell.tickWhileActiveClient(this.player.worldObj, this.player, this, ls, this.active_spell);
			}
		} else if (this.spell_target.targetfx != null) {
			this.spell_target.targetfx.destroy();
			this.spell_target.targetfx = null;
		}
		
		for (int i = 0; i < this.active_continuous_spells.length; i++) {
			if (this.active_continuous_spells[i]) {
				LearnedSpell ls = this.spell_slots[i];
				if (ls != null) {
					ls.spell.tickWhileActiveClient(this.player.worldObj, this.player, this, ls, i);
				}
			}
		}
	}
	
	/* Increase the amount of mana the player has.
	 * Returns the actual amount added.
	 */
	public int replenishMana(int amount)
	{
		int dif = this.mana_max - this.mana;
		dif = (dif < amount) ? dif : amount;
		this.mana += dif;
		this.scheduleUpdate();
		return dif;
	}
	
	/* Decrease the amount of mana the player has, optionally triggering a delay before
	 * the player's mana begins recharging.
	 * Returns the actual amount subtracted.
	 */
	public int useMana(int amount, boolean recharge_delay)
	{
//		if (PlayerAttributes.isInCreativeMode(this.player)) {
//			return 0;
//		}
		int dif = (this.mana < amount) ? this.mana : amount;
		this.mana -= dif;
		if (recharge_delay) {
			this.mana_recharge_counter = 60;
		}
		this.scheduleUpdate();
		return dif;
	}
	
	/* Get the power of the equipped wand */
	public MagicComplexity getWandPower()
	{
		if (this.wand_item == null) {
			return null;
		}
		return ModItems.magic_wand.getWandLevel(this.wand_item.getItemDamage());
	}
	
	public int getManaLvlXPReq()
	{
		return 20 + (this.mana_level * 10);
	}
	
	public float getManaLvlXPProg()
	{
		float prog = (float) this.player.experienceLevel / (float) this.getManaLvlXPReq();
		return Math.min(prog, 1.0f);
	}
	
	public void manaLvlUp()
	{
		this.player.addExperienceLevel(-this.getManaLvlXPReq());
		this.mana_level++;
		this.mana_max = this.mana_level * 100;
		this.scheduleUpdate();
	}
	
	public int getMagicSkillLvlXPReq()
	{
		return 2 + this.magic_skill_level * 2;
	}
	
	public float getMagicSkillLvlXPProg()
	{
		float prog = (float) this.player.experienceLevel / (float) this.getMagicSkillLvlXPReq();
		return Math.min(prog, 1.0f);
	}
	
	public void magicSkillLvlUp()
	{
		this.player.addExperienceLevel(-this.getMagicSkillLvlXPReq());
		this.magic_skill_level++;
		this.scheduleUpdate();
	}
	
	/* Get a learned spell from a provided spell */
	public LearnedSpell getLearnedFromSpell(Spell spell)
	{
		for (LearnedSpell ls : this.learned_spells) {
			if (ls.spell == spell) {
				return ls;
			}
		}
		return null;
	}
	
	public boolean hasLearnedSpell(Spell spell)
	{
		return this.getLearnedFromSpell(spell) != null;
	}
	
	public void learnNewSpell(Spell spell)
	{
		if (spell != null) {
			this.learned_spells.add(new LearnedSpell(spell));
		}
	}
	
	public boolean isSpellActive()
	{
		return (this.active_spell >= 0 && this.active_spell < this.spell_slots.length);
	}
	
	public LearnedSpell getActiveSpell()
	{
		return this.isSpellActive() ? this.spell_slots[this.active_spell] : null;
	}
	
	public void resetSpellUseTime()
	{
		this.spell_active_time = 0;
		this.scheduleUpdate();
	}
	
	/* Activate a spell. Called upon receiving a packet from the client requesting this.
	 * Calls triggerSpell if the requested spell is instant-use.
	 */
	public void activateSpell(int idx)
	{
		if (idx < 0 || idx >= this.spell_slots.length) {
			return;
		}
		LearnedSpell ls = this.spell_slots[idx];
		Spell spell = ls.spell;

		if (!spell.isManaRequirementMet(this, ls) || this.isSpellOnCooldown(idx)) {
			return;
		}
		
		if (spell.use_type == UseType.INSTANT) {
			this.triggerSpell(idx);
		} else if (spell.use_type == UseType.TRIGGER) {
			this.active_spell = idx;
			spell.onActivated(this.player.worldObj, this.player, this, this.spell_slots[idx], idx);
		} else if (spell.use_type == UseType.CONTINUOUS) {
			this.active_continuous_spells[idx] = true;
			spell.onActivated(this.player.worldObj, this.player, this, this.spell_slots[idx], idx);
		}
		this.scheduleUpdate();
	}
	
	/* Called to trigger the current active spell, or to activate an instant-use spell. */
	public void triggerSpell(int idx)
	{
		if (this.isSpellOnCooldown(idx)) {
			return;
		}
		LearnedSpell ls = this.spell_slots[idx];
		ls.spell.onTriggered(this.player.worldObj, this.player, this, ls, idx);
	}

	/* Deactivate the spell. Index is primarily for deactivating continuous spells, but it
	 * should still be provided for deactivating an active spell.
	 */
	public void deactivateSpell(int idx)
	{
		if (idx < 0) {
			ResAdditae.LOG.error("Spell to deactivate is " + Integer.toString(idx));
			return;
		}
		
		if (this.active_continuous_spells[idx]) {
			this.active_continuous_spells[idx] = false;
			LearnedSpell ls = this.spell_slots[idx];
			ls.spell.onDeactivated(this.player.worldObj, this.player, this, ls, idx);
			this.scheduleUpdate();
			return;
		}
		
		if (!this.isSpellActive()) {
			return;
		}
		LearnedSpell ls = this.getActiveSpell();
		ls.spell.onDeactivated(this.player.worldObj, this.player, this, ls, idx);
		this.active_spell = -1;
		this.spell_active_time = 0;
		this.scheduleUpdate();
	}
	
	@SideOnly(Side.CLIENT)
	public void activateSpellClient(int idx)
	{
		if (idx < 0 || idx >= this.spell_slots.length) {
			return;
		}
		LearnedSpell ls = this.spell_slots[idx];
		Spell spell = ls.spell;

		if (!spell.isManaRequirementMet(this, ls)) {
			Alerts.display(Alerts.NOT_ENOUGH_MANA);
			return;
		}
		if (this.isSpellOnCooldown(idx)) {
			Alerts.display(Alerts.ON_COOLDOWN);
			return;
		}
		
		if (spell.use_type == UseType.INSTANT) {
			this.triggerSpellClient(idx);
		} else if (spell.use_type == UseType.TRIGGER) {
			this.active_spell = idx;
			spell.onActivatedClient(this.player.worldObj, this.player, this, this.spell_slots[idx], idx);
		} else if (spell.use_type == UseType.CONTINUOUS) {
			this.active_continuous_spells[idx] = true;
			spell.onActivatedClient(this.player.worldObj, this.player, this, this.spell_slots[idx], idx);
		}
		this.scheduleUpdate();
	}
	
	@SideOnly(Side.CLIENT)
	public void triggerSpellClient(int idx)
	{
		if (!this.isSpellActive()) {
			return;
		}
		if (this.isSpellOnCooldown(idx)) {
			Alerts.display(Alerts.ON_COOLDOWN);
			return;
		}
		LearnedSpell ls = this.spell_slots[idx];
		ls.spell.onTriggeredClient(this.player.worldObj, this.player, this, ls, idx);
	}
	
	@SideOnly(Side.CLIENT)
	public void deactivateSpellClient(int idx)
	{
		if (this.active_continuous_spells[idx]) {
			this.active_continuous_spells[idx] = false;
			LearnedSpell ls = this.spell_slots[idx];
			ls.spell.onDeactivatedClient(this.player.worldObj, this.player, this, ls, idx);
			return;
		}
		
		if (!this.isSpellActive()) {
			return;
		}
		LearnedSpell ls = this.getActiveSpell();
		ls.spell.onDeactivatedClient(this.player.worldObj, this.player, this, ls, idx);
		this.active_spell = -1;
	}
	
	public boolean isSpellOnCooldown(int idx)
	{
		if (idx < 0) {
			return false;
		}
		return this.spell_cooldowns[idx] > 0;
	}
	
	public void onSpellUsed(int idx, int cooldown, int xp)
	{
		if (idx < 0) {
			return;
		}
		LearnedSpell ls = this.spell_slots[idx];
		if (ls != null) {
			ls.onSpellUsed(this);
			this.spell_cooldowns[idx] = cooldown;
			this.spell_cooldown_starts[idx] = cooldown;
		}
		if (xp > 0) {
			this.player.addExperience(xp);
		}
		this.scheduleUpdate();
	}
	
	public void onWandChanged(ItemStack wand_stack)
	{
		this.wand_item = wand_stack;
		MagicComplexity wand_pwr = null;
		if (this.wand_item != null) {
			wand_pwr = ModItems.magic_wand.getWandLevel(wand_stack.getItemDamage());
		}
		for (int i = 0; i < this.spell_slots.length; i++) {
			LearnedSpell ls = this.spell_slots[i];
			if (ls == null) {
				continue;
			}
			if (!ls.spell.isPowerRequirementMet(wand_pwr)) {
				this.spell_slots[i] = null;
			}
		}
		this.scheduleUpdate();
	}
	
	public void scheduleUpdate()
	{
		this.update_scheduled = true;
	}
	
	public void sendUpdate()
	{
		ResAdditae.network.sendTo(new PacketPlayerExtProps(this), (EntityPlayerMP) this.player);
	}
	
	public void serialize(ByteBuf buf)
	{
		buf.writeBoolean(this.allow_charm_helping_players);
		buf.writeBoolean(this.allow_charm_helping_entities);
		buf.writeBoolean(this.allow_charm_harming_players);
		buf.writeBoolean(this.allow_charm_harming_entities);
		buf.writeByte(this.charm_tamed_mob_behavior);
		
		buf.writeInt(this.mana_level);
		buf.writeInt(this.mana);
		
		buf.writeShort((short) this.magic_skill_level);
		
		buf.writeInt(this.learned_spells.size());
		for (LearnedSpell ls : this.learned_spells) {
			ls.serialize(buf);
		}
		
		for (int i = 0; i < this.spell_slots.length; i++) {
			buf.writeInt((this.spell_slots[i] == null) ? -1 : this.spell_slots[i].spell.getID());
			buf.writeShort(this.spell_cooldowns[i]);
			buf.writeShort(this.spell_cooldown_starts[i]);
		}
		buf.writeByte(this.active_spell);
		for (int i = 0; i < this.active_continuous_spells.length; i++) {
			buf.writeBoolean(this.active_continuous_spells[i]);
		}
		
		if (this.wand_item == null) {
			buf.writeByte(0);
		} else {
			buf.writeByte(1);
			SerialHlpr.serializeItemStack(this.wand_item, buf);
		}
	}
	
	public void deserialize(ByteBuf buf)
	{
		this.allow_charm_helping_players = buf.readBoolean();
		this.allow_charm_helping_entities = buf.readBoolean();
		this.allow_charm_harming_players = buf.readBoolean();
		this.allow_charm_harming_entities = buf.readBoolean();
		this.charm_tamed_mob_behavior = buf.readByte();

		this.mana_level = buf.readInt();
		this.mana = buf.readInt();
		
		this.magic_skill_level = buf.readShort();
		
		this.learned_spells.clear();
		int ls_size = buf.readInt();
		for (int i = 0; i < ls_size; i++) {
			LearnedSpell ls = new LearnedSpell();
			ls.deserialize(buf);
			if (ls.spell != null) {
				this.learned_spells.add(ls);
			}
		}
		
		for (int i = 0; i < this.spell_slots.length; i++) {
			this.spell_slots[i] = this.getLearnedFromSpell(Spells.getByID(buf.readInt()));
			this.spell_cooldowns[i] = buf.readShort();
			this.spell_cooldown_starts[i] = buf.readShort();
		}
		this.active_spell = buf.readByte();
		for (int i = 0; i < this.active_continuous_spells.length; i++) {
			this.active_continuous_spells[i] = buf.readBoolean();
		}
		
		if (buf.readByte() == 1) {
			this.wand_item = SerialHlpr.deserializeItemStack(buf);
		} else {
			this.wand_item = null;
		}
		
		this.load();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound nbt0)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		
		nbt.setBoolean("allow_charm_helping_players", this.allow_charm_helping_players);
		nbt.setBoolean("allow_charm_helping_entities", this.allow_charm_helping_entities);
		nbt.setBoolean("allow_charm_harming_players", this.allow_charm_harming_players);
		nbt.setBoolean("allow_charm_harming_entities", this.allow_charm_harming_entities);
		nbt.setByte("charm_tamed_mob_behavior", this.charm_tamed_mob_behavior);
		
		nbt.setInteger("mana_level", this.mana_level);
		nbt.setInteger("mana", this.mana);
		
		nbt.setShort("magic_skill_level", (short) this.magic_skill_level);
		
		NBTTagList list = new NBTTagList();
		for (LearnedSpell ls : this.learned_spells) {
			NBTTagCompound tag = new NBTTagCompound();
			ls.saveToNBT(tag);
			list.appendTag(tag);
		}
		nbt.setTag("learned_spells", list);
		
		int[] iarr1 = new int[this.spell_slots.length];
		int[] iarr2 = new int[this.spell_cooldowns.length];
		int[] iarr3 = new int[this.spell_cooldown_starts.length];
		for (int i = 0; i < this.spell_slots.length; i++) {
			iarr1[i] = (this.spell_slots[i] == null) ? -1 : this.spell_slots[i].spell.getID();
			iarr2[i] = this.spell_cooldowns[i];
			iarr3[i] = this.spell_cooldown_starts[i];
		}
		nbt.setIntArray("spell_slots", iarr1);
		nbt.setIntArray("spell_cooldowns", iarr2);
		nbt.setIntArray("spell_cooldown_starts", iarr3);
		
		if (this.wand_item != null) {
			nbt.setTag("wand_item", this.wand_item.writeToNBT(new NBTTagCompound()));
		}
		
		nbt0.setTag(KEY, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt0)
	{
		NBTTagCompound nbt = nbt0.getCompoundTag(KEY);
		
		this.allow_charm_helping_players = nbt.getBoolean("allow_charm_helping_players");
		this.allow_charm_helping_entities = nbt.getBoolean("allow_charm_helping_entities");
		this.allow_charm_harming_players = nbt.getBoolean("allow_charm_harming_players");
		this.allow_charm_harming_entities = nbt.getBoolean("allow_charm_harming_entities");
		this.charm_tamed_mob_behavior = nbt.getByte("charm_tamed_mob_behavior");
		
		this.mana_level = nbt.getInteger("mana_level");
		this.mana = nbt.getInteger("mana");
		
		this.magic_skill_level = nbt.getShort("magic_skill_level");
		
		NBTTagList list = nbt.getTagList("learned_spells", NBT.TAG_COMPOUND);
		this.learned_spells.clear();
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			LearnedSpell ls = new LearnedSpell();
			ls.loadFromNBT(tag);
			if (ls.spell != null) {
				this.learned_spells.add(ls);
			}
		}
		
		int[] iarr1 = nbt.getIntArray("spell_slots");
		int[] iarr2 = nbt.getIntArray("spell_cooldowns");
		int[] iarr3 = nbt.getIntArray("spell_cooldown_starts");
		if (iarr1.length == this.spell_slots.length) {
			for (int i = 0; i < this.spell_slots.length; i++) {
				this.spell_slots[i] = this.getLearnedFromSpell(Spells.getByID(iarr1[i]));
				this.spell_cooldowns[i] = iarr2[i];
				this.spell_cooldown_starts[i] = iarr3[i];
			}
		}
		
		if (nbt.hasKey("wand_item")) {
			this.wand_item = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("wand_item"));
		} else {
			this.wand_item = null;
		}
		
		this.load();
	}
	
	@Override
	public void init(Entity entity, World world)
	{
	}
}
