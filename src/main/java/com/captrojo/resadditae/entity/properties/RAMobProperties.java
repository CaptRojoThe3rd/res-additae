package com.captrojo.resadditae.entity.properties;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants.NBT;

public class RAMobProperties implements IExtendedEntityProperties
{
	public static final String KEY = "RAMobProperties";
	
	public static RAMobProperties get(EntityLiving entity)
	{
		RAMobProperties p = (RAMobProperties) entity.getExtendedProperties(KEY);
		if (p == null) {
			p = new RAMobProperties(entity);
			p.reset();
			entity.registerExtendedProperties(KEY, p);
		}
		return p;
	}
	
	private EntityLiving entity;
	
	private ArrayList<MobDropDataBase> special_drops;
	
	private SpawnSource spawn_src;
	private int spawn_src_x;
	private int spawn_src_y;
	private int spawn_src_z;
	
	public RAMobProperties(EntityLiving entity)
	{
		this.entity = entity;
		this.special_drops = new ArrayList<MobDropDataBase>();
		this.spawn_src = SpawnSource.NONE;
	}
	
	public void reset()
	{
		this.load();
	}
	
	public void load()
	{
	}

	@Override
	public void saveNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		if (this.special_drops.size() > 0) {
			NBTTagList drops = new NBTTagList();
			for (MobDropDataBase data : this.special_drops) {
				drops.appendTag(data.saveToNBT(new NBTTagCompound()));
			}
			tag.setTag("special_drops", drops);
		}
		
		tag.setByte("spawn_src", (byte) this.spawn_src.ordinal());
		if (this.spawn_src != SpawnSource.NONE) {
			tag.setInteger("spawn_src_x", this.spawn_src_x);
			tag.setInteger("spawn_src_y", this.spawn_src_y);
			tag.setInteger("spawn_src_z", this.spawn_src_z);
		}
		
		nbt.setTag(KEY, tag);
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt)
	{
		NBTTagCompound tag = nbt.getCompoundTag(KEY);
		
		if (tag.hasKey("special_drops")) {
			NBTTagList drops = tag.getTagList("special_drops", NBT.TAG_COMPOUND);
			for (int i = 0; i < drops.tagCount(); i++) {
				this.special_drops.add(new MobDropDataSimple(drops.getCompoundTagAt(i)));
			}
		}
		
		this.spawn_src = SpawnSource.values()[tag.getByte("spawn_src")];
		if (this.spawn_src != SpawnSource.NONE) {
			this.spawn_src_x = tag.getInteger("spawn_src_x");
			this.spawn_src_y = tag.getInteger("spawn_src_y");
			this.spawn_src_z = tag.getInteger("spawn_src_z");
		}
		
		this.load();
	}

	@Override
	public void init(Entity entity, World world)
	{
	}
	
	public ArrayList<MobDropDataBase> getSpecialDrops()
	{
		return this.special_drops;
	}
	
	public void addSpecialDrop(MobDropDataBase drop)
	{
		this.special_drops.add(drop);
	}
	
	public SpawnSource getSpawnSrc()
	{
		return this.spawn_src;
	}
	
	public int[] getSpawnSrcPos()
	{
		return new int[] {this.spawn_src_x, this.spawn_src_y, this.spawn_src_z};
	}
	
	public void setSpawnSrc(SpawnSource src, int x, int y, int z)
	{
		this.spawn_src = src;
		this.spawn_src_x = x;
		this.spawn_src_y = y;
		this.spawn_src_z = z;
	}
}
