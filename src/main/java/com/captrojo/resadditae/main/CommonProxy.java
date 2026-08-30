package com.captrojo.resadditae.main;

import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.config.JsonConfig;
import com.captrojo.resadditae.world.WorldEvents;
import com.captrojo.resadditae.world.gen.WorldGenEventHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
	protected void loadConfig()
	{
		CommonConfig.loadAll();
		JsonConfig.init();
	}
	
	protected void registerEventHandlers(CommonEventHandler common)
	{
		FMLCommonHandler.instance().bus().register(common);
		MinecraftForge.EVENT_BUS.register(common);
		
		PlayerEvents player_event_handler = new PlayerEvents();
		FMLCommonHandler.instance().bus().register(player_event_handler);
		MinecraftForge.EVENT_BUS.register(player_event_handler);
		
		WorldGenEventHandler worldgen_event_handler = new WorldGenEventHandler();
		MinecraftForge.TERRAIN_GEN_BUS.register(worldgen_event_handler);
		
		WorldEvents world_event_handler = new WorldEvents();
		FMLCommonHandler.instance().bus().register(world_event_handler);
		MinecraftForge.EVENT_BUS.register(world_event_handler);
	}
	
	public void registerEventHandlers()
	{
		this.registerEventHandlers(new CommonEventHandler());
	}
	
	public void registerRenderers()
	{
	}
	
	public void initRenderingStuff()
	{
	}
	
	public void registerKeybinds()
	{
	}
	
	public void createCreativeTabs()
	{
	}
	
	public void handleNEIStuff()
	{
	}
	
	public void displayHotbarStatusMsg(String msg, boolean rainbow)
	{
	}
}
