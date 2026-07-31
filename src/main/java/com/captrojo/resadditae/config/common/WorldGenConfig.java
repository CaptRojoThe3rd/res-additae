package com.captrojo.resadditae.config.common;

import com.captrojo.resadditae.config.ModConfig;

public class WorldGenConfig
{
	public static int depths_dimension_id;
	
	public static int chasm_excl_rad;
	public static int chasm_min_dist;
	public static int chasm_max_dist;
	
	public static boolean geode_enabled;
	public static int geode_excl_rad;
	public static int geode_min_dist;
	public static int geode_max_dist;
	
	public static boolean dark_dungeon_enabled;
	public static int dark_dungeon_excl_rad;
	public static int dark_dungeon_min_dist;
	public static int dark_dungeon_max_dist;
	
	public static boolean snow_dungeon_enabled;
	public static int snow_dungeon_excl_rad;
	public static int snow_dungeon_min_dist;
	public static int snow_dungeon_max_dist;
	
	public static boolean wooden_house_enabled;
	public static int wooden_house_excl_rad;
	public static int wooden_house_min_dist;
	public static int wooden_house_max_dist;

	public static boolean end_airship_enabled;
	public static int end_airship_excl_rad;
	public static int end_airship_min_dist;
	public static int end_airship_max_dist;
	
	public static void load()
	{
		ModConfig._category = "dimensions";
		
		depths_dimension_id = ModConfig.getInt("depths_dim_id", "Dimension ID for The Depths", -2);
		
		
		ModConfig._category = "structures";
		ModConfig.setCategoryComment("Structure spawning stuff.\n\n'enabled' - Whether the structure is enabled\n'exclusion_radius' - Radius (in chunks) around spawn in which the structure cannot generate\n'min_distance' - Minimum distance between each instance of the structure\n'max-distance' - Maximum distance between each instance of the structure");
		
		chasm_excl_rad = ModConfig.getInt("chasm_exclusion_radius", null, 4);
		chasm_min_dist = ModConfig.getInt("chasm_min_distance", null, 24);
		chasm_max_dist = ModConfig.getInt("chasm_max_distance", null, 48);
		
		geode_enabled = ModConfig.getBool("geode_enabled", null, true);
		geode_excl_rad = ModConfig.getInt("geode_exclusion_radius", null, 0);
		geode_min_dist = ModConfig.getInt("geode_min_distance", null, 8);
		geode_max_dist = ModConfig.getInt("geode_max_distance", null, 16);
		
		
		ModConfig._category = "structures.overworld";
		
		dark_dungeon_enabled = ModConfig.getBool("dark_dungeon_enabled", null, true);
		dark_dungeon_excl_rad = ModConfig.getInt("dark_dungeon_exclusion_radius", null, 0);
		dark_dungeon_min_dist = ModConfig.getInt("dark_dungeon_min_distance", null, 24);
		dark_dungeon_max_dist = ModConfig.getInt("dark_dungeon_max_distance", null, 48);
		
		snow_dungeon_enabled = ModConfig.getBool("snow_dungeon_enabled", null, true);
		snow_dungeon_excl_rad = ModConfig.getInt("snow_dungeon_exclusion_radius", null, 64);
		snow_dungeon_min_dist = ModConfig.getInt("snow_dungeon_min_distance", null, 64);
		snow_dungeon_max_dist = ModConfig.getInt("snow_dungeon_max_distance", null, 128);
		
		wooden_house_enabled = ModConfig.getBool("wooden_house_enabled", null, true);
		wooden_house_excl_rad = ModConfig.getInt("wooden_house_exclusion_radius", null, 0);
		wooden_house_min_dist = ModConfig.getInt("wooden_house_min_distance", null, 48);
		wooden_house_max_dist = ModConfig.getInt("wooden_house_max_distance", null, 96);
		
		
		ModConfig._category = "structures.end";
		
		end_airship_enabled = ModConfig.getBool("end_airship_enabled", null, true);
		end_airship_excl_rad = ModConfig.getInt("end_airship_exclusion_radius", null, 320);
		end_airship_min_dist = ModConfig.getInt("end_airship_min_distance", null, 96);
		end_airship_max_dist = ModConfig.getInt("end_airship_max_distance", null, 192);
	}
}