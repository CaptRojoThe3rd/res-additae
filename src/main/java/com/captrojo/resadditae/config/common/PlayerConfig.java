package com.captrojo.resadditae.config.common;

import com.captrojo.resadditae.config.ModConfig;

public class PlayerConfig
{
	public static int health_base = 10;
	public static int health_minimum = 3;
	public static int health_maximum = 40;
	
	public static int mana_base = 100;
	public static int mana_vessel_maximum = 99;
	public static int mana_vessel_value = 100;
	
	public static void load()
	{
		ModConfig._category = "player";
		ModConfig.setCategoryComment("Player attribute settings.");
		
		health_base = ModConfig.getInt("0000.health_base", "Starting number of heart containers", health_base);
		health_minimum = ModConfig.getInt("0001.health_minimum", "Minimum number of hearts a player may have", health_minimum);
		health_maximum = ModConfig.getInt("0002.health_mod_maximum", "Maximum number of hearts a player may have", health_maximum);
	
		mana_base = ModConfig.getInt("0100.mana_base", "Base amount of mana", mana_base);
		mana_vessel_maximum = ModConfig.getInt("0101.mana_vessel_maximum", "Maximum number of mana vessels a player may have", mana_vessel_maximum);
		mana_vessel_value = ModConfig.getInt("0102.mana_vessel_value", "Amount of mana that one mana vessel is worth", mana_vessel_value);
	}
}