package com.captrojo.resadditae.world;

import com.captrojo.resadditae.world.snowdungeon.SnowDungeon;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEvents
{
	public static WorldEvents instance;
	
	@SubscribeEvent
	public void loadWorld(WorldEvent.Load event)
	{
		if (event.world.isRemote) {
			return;
		}
		ModWorldData.worldLoad(event.world);
	}
	
	@SubscribeEvent
	public void unloadWorld(WorldEvent.Unload event)
	{
		if (event.world.isRemote) {
			return;
		}
		ModWorldData.worldUnload(event.world);
	}
	
	@SubscribeEvent
	public void tickWorld(TickEvent.WorldTickEvent event)
	{
		ModWorldData mwd = ModWorldData.getForWorld(event.world);
		mwd.tick();
	}
}
