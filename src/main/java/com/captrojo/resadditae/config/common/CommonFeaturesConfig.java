package com.captrojo.resadditae.config.common;

import com.captrojo.resadditae.config.ModConfig;

public class CommonFeaturesConfig
{
	public static boolean fill_command = true;
	
	public static void load()
	{
		ModConfig._category = "common_features";
		ModConfig.setCategoryComment("Enable/disable features that are often added by multiple mods.");
		
		fill_command = ModConfig.getBool("0000.fill_command", "Add the /fill command", true);
	}
}