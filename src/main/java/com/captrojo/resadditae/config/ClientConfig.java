package com.captrojo.resadditae.config;

import com.captrojo.resadditae.main.ResAdditae;

public class ClientConfig
{
	public static void loadAll()
	{
		ModConfig.load(ResAdditae.config_client);
		
		HUD.load();
		
		ModConfig.save();
	}
	
	public static class HUD
	{
		public static void load()
		{
		}
	}
}
