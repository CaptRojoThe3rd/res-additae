package com.captrojo.resadditae.entity.properties;

import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketPlayerExtProps;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

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
	private int periodic_update_counter;
	
	public boolean allow_charm_helping_players;
	public boolean allow_charm_helping_entities;
	public boolean allow_charm_harming_players;
	public boolean allow_charm_harming_entities;
	public byte charm_tamed_mob_behavior;
	
	public int mana;
	public int mana_vessels;
	public long mana_upgrades;
	
	public int mana_max;
	private int mana_recharge_rate;
	private int mana_recharge_counter;
	
	public RAPlayerProperties(EntityPlayer player)
	{
		this.player = player;
		this.periodic_update_counter = 0;
	}
	
	public void reset()
	{
		this.allow_charm_helping_players = true;
		this.allow_charm_helping_entities = false;
		this.allow_charm_harming_players = false;
		this.allow_charm_harming_entities = true;
		this.charm_tamed_mob_behavior = 0;
		
		this.mana = 100;
		this.mana_vessels = 0;
		this.mana_upgrades = 0l;
		
		this.load();
	}
	
	public void load()
	{
		this.mana_max = 100 + (this.mana_vessels * CommonConfig.Player.mana_vessel_value);
		if (this.mana > this.mana_max) {
			this.mana = this.mana_max;
		}
		
		if (this.hasManaUpgrade(ManaUpgrades.RECHARGE_2)) {
			this.mana_recharge_rate = 3;
		} else if (this.hasManaUpgrade(ManaUpgrades.RECHARGE_1)) {
			this.mana_recharge_rate = 2;
		} else {
			this.mana_recharge_rate = 1;
		}
		
		this.mana_recharge_counter = 5;
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
				this.mana += this.mana_recharge_rate;
				this.mana_recharge_counter = 3;
			}
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
		
		buf.writeInt(this.mana);
		buf.writeInt(this.mana_vessels);
		buf.writeLong(this.mana_upgrades);
	}
	
	public void deserialize(ByteBuf buf)
	{
		this.allow_charm_helping_players = buf.readBoolean();
		this.allow_charm_helping_entities = buf.readBoolean();
		this.allow_charm_harming_players = buf.readBoolean();
		this.allow_charm_harming_entities = buf.readBoolean();
		this.charm_tamed_mob_behavior = buf.readByte();
		
		this.mana = buf.readInt();
		this.mana_vessels = buf.readInt();
		this.mana_upgrades = buf.readLong();
		
		this.load();
	}
	
	@Override
	public void saveNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setBoolean("allow_charm_helping_players", this.allow_charm_helping_players);
		tag.setBoolean("allow_charm_helping_entities", this.allow_charm_helping_entities);
		tag.setBoolean("allow_charm_harming_players", this.allow_charm_harming_players);
		tag.setBoolean("allow_charm_harming_entities", this.allow_charm_harming_entities);
		tag.setByte("charm_tamed_mob_behavior", this.charm_tamed_mob_behavior);
		
		tag.setInteger("mana", this.mana);
		tag.setInteger("mana_vessels", this.mana_vessels);
		tag.setLong("mana_upgrades", this.mana_upgrades);
		
		nbt.setTag(KEY, tag);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound tag = nbt.getCompoundTag(KEY);
		
		this.allow_charm_helping_players = tag.getBoolean("allow_charm_helping_players");
		this.allow_charm_helping_entities = tag.getBoolean("allow_charm_helping_entities");
		this.allow_charm_harming_players = tag.getBoolean("allow_charm_harming_players");
		this.allow_charm_harming_entities = tag.getBoolean("allow_charm_harming_entities");
		this.charm_tamed_mob_behavior = tag.getByte("charm_tamed_mob_behavior");
		
		this.mana = tag.getInteger("mana");
		this.mana_vessels = tag.getInteger("mana_vessels");
		this.mana_upgrades = tag.getLong("mana_upgrades");
		
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
	
	public boolean hasManaUpgrade(ManaUpgrades upgrade)
	{
		if (upgrade == ManaUpgrades.RECHARGE_1 && this.hasManaUpgrade(ManaUpgrades.RECHARGE_2)) {
			return true;
		}
		return (this.mana_upgrades & upgrade.bit) != 0;
	}
	
	public void applyManaUpgrade(ManaUpgrades upgrade)
	{
		this.mana_upgrades |= upgrade.bit;
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
