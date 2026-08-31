package com.captrojo.resadditae.main;

import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.config.JsonConfig;
import com.captrojo.resadditae.world.WorldEvents;
import com.captrojo.resadditae.world.gen.ModWorldGen;

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
		
		PlayerEvents.instance = new PlayerEvents();
		FMLCommonHandler.instance().bus().register(PlayerEvents.instance);
		MinecraftForge.EVENT_BUS.register(PlayerEvents.instance);
		
		WorldEvents.instance = new WorldEvents();
		FMLCommonHandler.instance().bus().register(WorldEvents.instance);
		MinecraftForge.EVENT_BUS.register(WorldEvents.instance);
		
		MinecraftForge.EVENT_BUS.register(ModWorldGen.instance);
		MinecraftForge.TERRAIN_GEN_BUS.register(ModWorldGen.instance);
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
