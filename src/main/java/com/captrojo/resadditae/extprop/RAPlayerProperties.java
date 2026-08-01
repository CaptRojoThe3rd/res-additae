package com.captrojo.resadditae.extprop;

import java.util.ArrayList;

import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.SpellTargetData;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;
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
	
	public Spell[] spell_slots;
	public int spell_in_use;
	public int spell_use_time;
	public SpellTargetData spell_target;
	
	public ItemStack wand_item;
	
	public RAPlayerProperties(EntityPlayer player)
	{
		this.player = player;
		this.periodic_update_counter = 0;
		
		this.learned_spells = new ArrayList<LearnedSpell>();
		this.spell_slots = new Spell[6];
		this.spell_in_use = -1;
		this.spell_target = new SpellTargetData();
	}
	
	public void reset()
	{
		this.allow_charm_helping_players = true;
		this.allow_charm_helping_entities = false;
		this.allow_charm_harming_players = false;
		this.allow_charm_harming_entities = true;
		this.charm_tamed_mob_behavior = 0;
		
		this.mana_level = 1; /* TODO: this is for debug purposes, remove this later */
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
				this.mana = Math.min(this.mana + 2, this.mana_max);
				this.mana_recharge_counter = 3;
			}
			updated = true;
		}
		
		if (this.spell_in_use < 0 && this.spell_use_time > 0) {
			this.resetSpellUseTime();
			updated = true;
		} else if (this.spell_in_use > 0) {
			this.spell_use_time++;
			Spell spell = this.getSpellInUse();
			spell.tickWhileActive(this.player.worldObj, this.player, this);
			if (spell.max_use_time < this.spell_use_time) {
				this.deactivateSpell();
			}
			updated = true;
		}
		
		if (updated && this.player != null) {
			this.sendUpdate();
		}
		this.update_scheduled = false;
	}
	
	@SideOnly(Side.CLIENT)
	public void tickClient()
	{
		if (this.isSpellInUse()) {
			this.getSpellInUse().tickWhileActiveClient(this.player.worldObj, this.player, this);
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
		if (PlayerAttributes.isInCreativeMode(this.player)) {
			return 0;
		}
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
		return ModItems.magic_wand.getWandPower(this.wand_item.getItemDamage());
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
	
	public boolean isSpellInUse()
	{
		return (this.spell_in_use >= 0 && this.spell_in_use < this.spell_slots.length);
	}
	
	public Spell getSpellInUse()
	{
		return this.isSpellInUse() ? this.spell_slots[this.spell_in_use] : null;
	}
	
	public void resetSpellUseTime()
	{
		this.spell_use_time = 0;
		this.scheduleUpdate();
	}
	
	public void activateSpell(int idx)
	{
		if (idx < 0 || idx >= this.spell_slots.length) {
			return;
		}
		Spell spell = this.spell_slots[idx];

		if (!spell.canCastSpell(this)) {
			return;
		}
		this.spell_in_use = idx;
		
		if (spell.is_instant) {
			this.triggerSpell();
			this.spell_in_use = -1;
			return;
		}
		
		spell.onActivated(this.player.worldObj, this.player, this);
		this.scheduleUpdate();
	}
	
	public void triggerSpell()
	{
		if (!this.isSpellInUse()) {
			return;
		}
		this.getSpellInUse().onTriggered(this.player.worldObj, this.player, this);
	}
	
	public void deactivateSpell()
	{
		if (!this.isSpellInUse()) {
			return;
		}
		this.getSpellInUse().onDeactivated(this.player.worldObj, this.player, this);
		this.spell_in_use = -1;
		this.spell_use_time = 0;
	}
	
	@SideOnly(Side.CLIENT)
	public void activateSpellClient(int idx)
	{
		if (idx < 0 || idx >= this.spell_slots.length) {
			return;
		}
		Spell spell = this.spell_slots[idx];

		if (!spell.canCastSpell(this)) {
			return;
		}
		this.spell_in_use = idx;
		
		if (spell.is_instant) {
			this.triggerSpellClient();
			this.spell_in_use = -1;
			return;
		}
		
		spell.onActivatedClient(this.player.worldObj, this.player, this);
	}
	
	@SideOnly(Side.CLIENT)
	public void triggerSpellClient()
	{
		if (!this.isSpellInUse()) {
			return;
		}
		this.getSpellInUse().onTriggeredClient(this.player.worldObj, this.player, this);
	}
	
	@SideOnly(Side.CLIENT)
	public void deactivateSpellClient()
	{
		if (!this.isSpellInUse()) {
			return;
		}
		this.getSpellInUse().onDeactivatedClient(this.player.worldObj, this.player, this);
		this.spell_in_use = -1;
	}
	
	public void onSpellUsed()
	{
		LearnedSpell ls = this.getLearnedFromSpell(this.spell_slots[this.spell_in_use]);
		if (ls != null) {
			ls.onSpellUsed();
		}
		this.scheduleUpdate();
	}
	
	public void onWandChanged(ItemStack wand_stack)
	{
		this.wand_item = wand_stack;
		MagicComplexity wand_pwr = null;
		if (this.wand_item != null) {
			wand_pwr = ModItems.magic_wand.getWandPower(wand_stack.getItemDamage());
		}
		for (int i = 0; i < this.spell_slots.length; i++) {
			Spell spell = this.spell_slots[i];
			if (spell == null) {
				continue;
			}
			if (!spell.isComplexityRequirementMet(wand_pwr)) {
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
			buf.writeInt((this.spell_slots[i] == null) ? -1 : this.spell_slots[i].getID());
		}
		buf.writeByte(this.spell_in_use);
		
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
			this.spell_slots[i] = Spells.getByID(buf.readInt());
		}
		this.spell_in_use = buf.readByte();
		
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
		
		int[] iarr = new int[this.spell_slots.length];
		for (int i = 0; i < this.spell_slots.length; i++) {
			iarr[i] = (this.spell_slots[i] == null) ? -1 : this.spell_slots[i].getID();
		}
		nbt.setIntArray("spell_slots", iarr);
		
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
		
		int[] iarr = nbt.getIntArray("spell_slots");
		if (iarr.length == this.spell_slots.length) {
			for (int i = 0; i < this.spell_slots.length; i++) {
				this.spell_slots[i] = Spells.getByID(iarr[i]);
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
