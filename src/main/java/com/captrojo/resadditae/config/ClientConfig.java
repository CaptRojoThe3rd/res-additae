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
		public static int mana_bar_fill_direction = 0;
		
		public static void load()
		{
			ModConfig._category = "HUD";
			
			mana_bar_fill_direction = ModConfig.getInt("0000.mana_bar_fill_direction", "0 = left to right, 1 = right to left", 1);
		}
	}
}
