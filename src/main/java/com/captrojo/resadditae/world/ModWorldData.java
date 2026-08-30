package com.captrojo.resadditae.world;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.captrojo.resadditae.world.snowdungeon.SnowDungeon;
import com.captrojo.resadditae.world.snowdungeon.SnowDungeonCollection;

import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class ModWorldData
{
	public static Map<Integer, ModWorldData> data_map = new HashMap<Integer, ModWorldData>();
	
	public static ModWorldData getForWorld(World world)
	{
		return ModWorldData.data_map.get(world.provider.dimensionId);
	}
	
	public static void worldLoad(World world)
	{
		ModWorldData data = new ModWorldData(world);
		data.load();
		ModWorldData.data_map.put(world.provider.dimensionId, data);
	}
	
	public static void worldUnload(World world)
	{
		ModWorldData.data_map.remove(world.provider.dimensionId);
	}
	
	public World world;
	
	SnowDungeonCollection snow_dungeon_data;
	
	public ModWorldData(World world)
	{
		this.world = world;
		
		this.load();
	}
	
	public void tick()
	{
		this.snow_dungeon_data.tick();
	}
	
	public void addSnowDungeon(SnowDungeon sd)
	{
		this.snow_dungeon_data.addSnowDungeon(sd);
	}
	
	public void load()
	{
		this.snow_dungeon_data = this.loadFromPerWorldStorage(SnowDungeonCollection.class, SnowDungeonCollection.KEY);
		this.snow_dungeon_data.world = this.world;
	}
	
	<T extends WorldSavedData> T loadFromPerWorldStorage(Class<? extends T> clazz, String key)
	{
		T data = (T) this.world.perWorldStorage.loadData(clazz, key);
		if (data == null) {
			try {
				Constructor c = clazz.getConstructor(String.class);
				data = (T) c.newInstance(key);
				this.world.perWorldStorage.setData(key, data);
			} catch (Exception e) {
				throw new WorldLoadingException("Failed to load world save data: " + key, e);
			}
		}
		return data;
	}
}
