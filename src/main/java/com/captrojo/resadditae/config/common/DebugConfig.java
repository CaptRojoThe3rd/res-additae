package com.captrojo.resadditae.config.common;

import com.captrojo.resadditae.config.ModConfig;

public class DebugConfig
{
	public static boolean log_structure_gens = true;
	public static boolean log_failed_structure_gens = true;
	
	public static void load()
	{
		ModConfig._category = "debug";
		ModConfig.setCategoryComment("Debugging settings");
		
		log_structure_gens = ModConfig.getBool("log_structure_gens", "Log the position of all generated structures and notable features", log_structure_gens);
		log_failed_structure_gens = ModConfig.getBool("log_failed_structure_gens", "Log the positions of structures that did not generate. This does not affect the reporting of errors", log_failed_structure_gens);
	}
}
