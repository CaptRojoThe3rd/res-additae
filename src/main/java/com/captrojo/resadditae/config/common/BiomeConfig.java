package com.captrojo.resadditae.config.common;

import com.captrojo.resadditae.config.ModConfig;

public class BiomeConfig
{
	public static boolean chestnut_forest_enabled;
	public static boolean enchanted_ash_forest_enabled;
	public static boolean violet_forest_enabled;
	
	public static int chestnut_forest_id;
	public static int enchanted_ash_forest_id;
	public static int violet_forest_id;
	
	public static int depths_amber_id;
	public static int depths_jade_id;
	public static int depths_ruby_id;
	public static int depths_sapphire_id;
	public static int depths_topaz_id;
	
	public static int chestnut_forest_weight;
	public static int enchanted_ash_forest_weight;
	public static int violet_forest_weight;
	
	public static void load()
	{
		ModConfig._category = "biomes.enables";
		ModConfig.setCategoryComment("Enable/disable biomes here.");
		
		chestnut_forest_enabled = ModConfig.getBool("00.chestnut_forest", null, true);
		enchanted_ash_forest_enabled = ModConfig.getBool("01.enchanted_ash_forest", null, true);
		violet_forest_enabled = ModConfig.getBool("02.violet_forest", null, true);
		
		
		ModConfig._category = "biomes.ids";
		ModConfig.setCategoryComment("Set biome IDs here.");
		
		chestnut_forest_id = ModConfig.getInt("00.chestnut_forest", null, 60);
		enchanted_ash_forest_id = ModConfig.getInt("01.enchanted_ash_forest", null, 61);
		violet_forest_id = ModConfig.getInt("02.violet_forest", null, 62);
		
		depths_amber_id = ModConfig.getInt("D0.depths_amber", null, 80);
		depths_jade_id = ModConfig.getInt("D1.depths_jade", null, 81);
		depths_ruby_id = ModConfig.getInt("D2.depths_ruby", null, 82);
		depths_sapphire_id = ModConfig.getInt("D3.depths_sapphire", null, 83);
		depths_topaz_id = ModConfig.getInt("D4.depths_topaz", null, 84);
		
		ModConfig._category = "biomes.weights";
		ModConfig.setCategoryComment("Set how often biomes generate here.");
		
		chestnut_forest_weight = ModConfig.getInt("00.chestnut_forest", null, 5);
		enchanted_ash_forest_weight = ModConfig.getInt("01.enchanted_ash_forest", null, 5);
		violet_forest_weight = ModConfig.getInt("02.violet_forest", null, 5);
	}
}