package com.captrojo.resadditae.entity.properties;

import java.util.ArrayList;

import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketPlayerExtProps;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
		RAPlayerProperties p = (RAPlayerProperties) player.getExtendedProperties(KEY);
		if (p == null) {
			p = new RAPlayerProperties(player);
			p.reset();
			player.registerExtendedProperties(KEY, p);
		}
		return p;
	}
	
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
	public Spell spell_in_use;
	public int spell_use_time;
	
	public RAPlayerProperties(EntityPlayer player)
	{
		this.player = player;
		this.periodic_update_counter = 0;
		
		this.learned_spells = new ArrayList<LearnedSpell>();
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
	
	public void load()
	{
		this.mana_max = this.mana_level * 100;
	}
	
	public void tick(EntityPlayer player)
	{
		boolean updated = false;
		
		this.periodic_update_counter--;
		if (this.periodic_update_counter <= 0) {
			this.periodic_update_counter = 20;
			updated = true;
		}
		
		if (this.mana < this.mana_max) {
			if (this.mana_recharge_counter > 0) {
				this.mana_recharge_counter--;
			} else {
				this.replenishMana(2);
				this.mana_recharge_counter = 3;
			}
			updated = true;
		}
		
		if (this.spell_in_use == null && this.spell_use_time > 0) {
			this.spell_use_time = 0;
			updated = true;
		} else if (this.spell_in_use != null) {
			this.spell_use_time++;
			updated = true;
		}
		
		if (updated && this.player != null) {
			ResAdditae.network.sendTo(new PacketPlayerExtProps(this), (EntityPlayerMP) this.player);
		}
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
		
		buf.writeInt(this.spell_in_use == null ? -1 : this.spell_in_use.getID());
		buf.writeInt(this.spell_use_time);
		
		buf.writeInt(this.learned_spells.size());
		for (LearnedSpell ls : this.learned_spells) {
			ls.serialize(buf);
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
		
		this.spell_in_use = Spells.getByID(buf.readInt());
		this.spell_use_time = buf.readInt();
		
		this.learned_spells.clear();
		int ls_size = buf.readInt();
		for (int i = 0; i < ls_size; i++) {
			LearnedSpell ls = new LearnedSpell();
			ls.deserialize(buf);
			this.learned_spells.add(ls);
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
		
		nbt.setInteger("spell_in_use", this.spell_in_use == null ? -1 : this.spell_in_use.getID());
		nbt.setInteger("spell_use_time", this.spell_use_time);
		
		NBTTagList list = new NBTTagList();
		for (LearnedSpell ls : this.learned_spells) {
			NBTTagCompound tag = new NBTTagCompound();
			ls.saveToNBT(tag);
			list.appendTag(tag);
		}
		nbt.setTag("learned_spells", list);
		
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
		
		this.spell_in_use = Spells.getByID(nbt.getInteger("spell_in_use"));
		this.spell_use_time = nbt.getInteger("spell_use_time");
		
		NBTTagList list = nbt.getTagList("learned_spells", NBT.TAG_COMPOUND);
		this.learned_spells.clear();
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			LearnedSpell ls = new LearnedSpell();
			ls.loadFromNBT(tag);
			this.learned_spells.add(ls);
		}
		
		this.load();
	}

	@Override
	public void init(Entity entity, World world)
	{
	}
	
	public void replenishMana(int amount)
	{
		this.mana = Math.min(this.mana + amount, this.mana_max);
	}
	
	public void useMana(int amount)
	{
		if (PlayerAttributes.isInCreativeMode(this.player)) {
			return;
		}
		this.mana -= amount;
		this.mana_recharge_counter = 60;
	}
	
	public boolean hasEnoughMana(int amount)
	{
		if (PlayerAttributes.isInCreativeMode(this.player)) {
			return true;
		}
		return amount <= this.mana;
	}
	
	public boolean canCharmHarmEntity(EntityLivingBase entity, EntityPlayer player)
	{
		if (entity instanceof EntityPlayer) {
			return this.allow_charm_harming_players;
		}
		if (entity instanceof EntityTameable && ((EntityTameable) entity).isTamed() && this.allow_charm_harming_entities) {
			if (this.charm_tamed_mob_behavior == 0) {
				return false;
			}
			return !((EntityTameable) entity).func_152113_b().equals(player.getUniqueID().toString());
		}
		return this.allow_charm_harming_entities;
	}
	
	public boolean canCharmHelpEntity(Entity entity)
	{
		if (entity instanceof EntityPlayer) {
			return this.allow_charm_helping_players;
		}
		if (entity instanceof EntityTameable && ((EntityTameable) entity).isTamed()) {
			if (this.charm_tamed_mob_behavior == 0) {
				return true;
			}
			return ((EntityTameable) entity).func_152113_b().equals(player.getUniqueID().toString());
		}
		return this.allow_charm_helping_entities;
	}
}
