package com.captrojo.resadditae.world.snowdungeon;

import java.util.ArrayList;
import java.util.List;

import com.captrojo.resadditae.stats.ModAchievements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

public class SnowDungeonCollection extends WorldSavedData
{
	public static final String KEY = "ra_snow_dungeons";
	
	public World world;
	public int ticks;
	
	public List<SnowDungeon> snow_dungeons;
	
	public SnowDungeonCollection()
	{
		this(SnowDungeonCollection.KEY);
	}
	
	public SnowDungeonCollection(String key)
	{
		super(key);
		this.snow_dungeons = new ArrayList<SnowDungeon>();
	}
	
	public void tick()
	{
		this.ticks++;
		if ((this.ticks % 300) == 0) {
			this.markDirty();
		}
		int tick_idx = this.ticks % 20;
		
		if (tick_idx == 0) {
			for (SnowDungeon sd : this.snow_dungeons) {
				for (Object o : this.world.playerEntities) {
					EntityPlayer player = (EntityPlayer) o;
					if (sd.isPlayerInsidePyramid(player)) {
						player.triggerAchievement(ModAchievements.snow_dungeon_find);
					}
				}
			}
		}
	}
	
	public void addSnowDungeon(SnowDungeon sd)
	{
		this.snow_dungeons.add(sd);
		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.ticks = nbt.getInteger("Tick");
		
		this.snow_dungeons.clear();
		NBTTagList sd_list = nbt.getTagList("SnowDungeons", NBT.TAG_COMPOUND);
		for (int i = 0; i < sd_list.tagCount(); i++) {
			this.snow_dungeons.add((new SnowDungeon()).loadFromNBT(sd_list.getCompoundTagAt(i)));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("Tick", this.ticks);
		
		NBTTagList sd_list = new NBTTagList();
		for (SnowDungeon sd : this.snow_dungeons) {
			sd_list.appendTag(sd.saveToNBT(new NBTTagCompound()));
		}
		nbt.setTag("SnowDungeons", sd_list);
	}
}
