package com.captrojo.resadditae.config.common;

import com.captrojo.resadditae.config.ModConfig;
import com.captrojo.resadditae.config.OreConfigOptns;

public class WorldGenConfig
{
	public static int depths_dimension_id;
	
	private static final int COMMON_ORE_CHANCES = 9;
	private static final int UNCOMMON_ORE_CHANCES = 5;
	private static final int RARE_ORE_CHANCES = 2;
	private static final int VERY_RARE_ORE_CHANCES = 1;
	
	/* name, min_y, max_y, min_size, max_size, chances [enabled, excl_rad, min_dist, max_dist] */
	public static OreConfigOptns ore_ow_silver = new OreConfigOptns("silver", 3, 40, 1, 5, 6);
	public static OreConfigOptns ore_ow_platinum = new OreConfigOptns("platinum", 3, 20, 1, 3, 4);

	public static OreConfigOptns ore_dp_coal = new OreConfigOptns("coal", 0, 192, 10, 20, COMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_iron = new OreConfigOptns("iron", 0, 192, 7, 15, COMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_gold = new OreConfigOptns("gold", 0, 192, 7, 15, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_diamond = new OreConfigOptns("diamond", 0, 192, 3, 8, RARE_ORE_CHANCES);
	public static OreConfigOptns ore_dp_redstone = new OreConfigOptns("redstone", 0, 192, 6, 12, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_lapis = new OreConfigOptns("lapis", 0, 192, 3, 8, RARE_ORE_CHANCES);
	public static OreConfigOptns ore_dp_emerald = new OreConfigOptns("emerald", 0, 192, 1, 5, RARE_ORE_CHANCES);
	
	public static OreConfigOptns ore_dp_silver = new OreConfigOptns("silver", 0, 192, 6, 12, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_platinum = new OreConfigOptns("platinum", 0, 192, 6, 12, RARE_ORE_CHANCES);
	
	public static OreConfigOptns ore_dp_copper = new OreConfigOptns("copper", 0, 192, 7, 15, COMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_aluminium = new OreConfigOptns("aluminium", 0, 192, 7, 15, COMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_tin = new OreConfigOptns("tin", 0, 192, 7, 15, COMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_lead = new OreConfigOptns("lead", 0, 192, 7, 15, COMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_nickel = new OreConfigOptns("nickel", 0, 192, 7, 15, COMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_mythril = new OreConfigOptns("mythril", 0, 192, 2, 8, RARE_ORE_CHANCES);
	public static OreConfigOptns ore_dp_uranium = new OreConfigOptns("uranium", 0, 192, 5, 12, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_thorium = new OreConfigOptns("thorium", 0, 192, 5, 12, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_tungsten = new OreConfigOptns("tungsten", 0, 192, 5, 12, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_titanium = new OreConfigOptns("titanium", 0, 192, 5, 12, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_zinc = new OreConfigOptns("zinc", 0, 192, 3, 10, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_magnesium = new OreConfigOptns("magnesium", 0, 192, 3, 10, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_boron = new OreConfigOptns("boron", 0, 192, 1, 5, RARE_ORE_CHANCES);
	
	public static OreConfigOptns ore_dp_sulfur = new OreConfigOptns("sulfur", 0, 192, 3, 10, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_niter = new OreConfigOptns("niter", 0, 192, 3, 8, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_fluorite = new OreConfigOptns("fluorite", 0, 192, 3, 8, UNCOMMON_ORE_CHANCES);
	public static OreConfigOptns ore_dp_beryllium = new OreConfigOptns("beryllium", 0, 192, 3, 8, RARE_ORE_CHANCES);
	public static OreConfigOptns ore_dp_rare_earth = new OreConfigOptns("rare_earth", 0, 192, 1, 4, VERY_RARE_ORE_CHANCES);
	public static OreConfigOptns ore_dp_cobalt = new OreConfigOptns("cobalt", 0, 192, 1, 4, VERY_RARE_ORE_CHANCES);
	public static OreConfigOptns ore_dp_cinnebar = new OreConfigOptns("cinnebar", 0, 192, 1, 4, VERY_RARE_ORE_CHANCES);
	
	public static int chasm_dimension;
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
		
		
		ModConfig._category = "oregen";
		ModConfig.setCategoryComment(
			"Ore generation stuff.\n\n" +
			"'enabled' - Whether the ore is enabled\n" +
			"'exclusion_radius' - The minimum distance from spawn\n" +
			"'min_distance' - The minimum distance between chunks where ores generate\n" +
			"'max_distance' - The maximum distance between chunks where ores generate\n" +
			"'min_y' - The minimum Y level of the ore\n" +
			"'max_y' - The maximum Y level of the ore\n" +
			"'min_size' - The minimum size of an ore vein\n" +
			"'max_size' - The maximum size of an ore vein\n" +
			"'spawn_chances' - The number of times an ore will attempt to spawn per chunk\n"
		);
		
		
		ModConfig._category = "oregen.overworld";
		
		ore_ow_silver.load();
		ore_ow_platinum.load();
		
		
		ModConfig._category = "oregen.depths";
		
		ore_dp_coal.load();
		ore_dp_iron.load();
		ore_dp_gold.load();
		ore_dp_diamond.load();
		ore_dp_redstone.load();
		ore_dp_lapis.load();
		ore_dp_emerald.load();
		
		ore_dp_silver.load();
		ore_dp_platinum.load();
		
		ore_dp_copper.load();
		ore_dp_aluminium.load();
		ore_dp_tin.load();
		ore_dp_lead.load();
		ore_dp_nickel.load();
		ore_dp_mythril.load();
		ore_dp_uranium.load();
		ore_dp_thorium.load();
		ore_dp_tungsten.load();
		ore_dp_titanium.load();
		ore_dp_zinc.load();
		ore_dp_magnesium.load();
		ore_dp_boron.load();
		
		ore_dp_sulfur.load();
		ore_dp_niter.load();
		ore_dp_fluorite.load();
		ore_dp_beryllium.load();
		ore_dp_rare_earth.load();
		ore_dp_cobalt.load();
		ore_dp_cinnebar.load();
		
		
		ModConfig._category = "structures";
		ModConfig.setCategoryComment("Structure spawning stuff.\n\n'enabled' - Whether the structure is enabled\n'exclusion_radius' - Radius (in chunks) around spawn in which the structure cannot generate\n'min_distance' - Minimum distance between each instance of the structure\n'max-distance' - Maximum distance between each instance of the structure");
		
		
		ModConfig._category = "structures.depths";
		
		chasm_dimension = ModConfig.getInt("chasm_dimension", "Which dimension chasms should connect the Depths to (default = overworld)", 0);
		chasm_excl_rad = ModConfig.getInt("chasm_exclusion_radius", null, 4);
		chasm_min_dist = ModConfig.getInt("chasm_min_distance", null, 12);
		chasm_max_dist = ModConfig.getInt("chasm_max_distance", null, 24);
		
		geode_enabled = ModConfig.getBool("geode_enabled", null, true);
		geode_excl_rad = ModConfig.getInt("geode_exclusion_radius", null, 0);
		geode_min_dist = ModConfig.getInt("geode_min_distance", null, 6);
		geode_max_dist = ModConfig.getInt("geode_max_distance", null, 12);
		
		
		ModConfig._category = "structures.overworld";
		
		dark_dungeon_enabled = ModConfig.getBool("dark_dungeon_enabled", null, true);
		dark_dungeon_excl_rad = ModConfig.getInt("dark_dungeon_exclusion_radius", null, 0);
		dark_dungeon_min_dist = ModConfig.getInt("dark_dungeon_min_distance", null, 12);
		dark_dungeon_max_dist = ModConfig.getInt("dark_dungeon_max_distance", null, 24);
		
		snow_dungeon_enabled = ModConfig.getBool("snow_dungeon_enabled", null, true);
		snow_dungeon_excl_rad = ModConfig.getInt("snow_dungeon_exclusion_radius", null, 0);
		snow_dungeon_min_dist = ModConfig.getInt("snow_dungeon_min_distance", null, 24);
		snow_dungeon_max_dist = ModConfig.getInt("snow_dungeon_max_distance", null, 48);
		
		wooden_house_enabled = ModConfig.getBool("wooden_house_enabled", null, true);
		wooden_house_excl_rad = ModConfig.getInt("wooden_house_exclusion_radius", null, 0);
		wooden_house_min_dist = ModConfig.getInt("wooden_house_min_distance", null, 24);
		wooden_house_max_dist = ModConfig.getInt("wooden_house_max_distance", null, 48);
		
		
		ModConfig._category = "structures.end";
		
		end_airship_enabled = ModConfig.getBool("end_airship_enabled", null, true);
		end_airship_excl_rad = ModConfig.getInt("end_airship_exclusion_radius", null, 320);
		end_airship_min_dist = ModConfig.getInt("end_airship_min_distance", null, 40);
		end_airship_max_dist = ModConfig.getInt("end_airship_max_distance", null, 80);
	}
}