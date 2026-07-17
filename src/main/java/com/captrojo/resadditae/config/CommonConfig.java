package com.captrojo.resadditae.config;

import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraftforge.common.config.Configuration;

public class CommonConfig
{
	public static void loadAll()
	{
		ModConfig.load(ResAdditae.config_common);
		
		General.load();
		Player.load();
		Biomes.load();
		CommonFeatures.load();
		CommonItems.load();
		WorldGen.load();
		
		ModConfig.save();
	}
	
	public static class General
	{
		public static boolean creative_search_tab_fix = true;
		public static boolean enable_motd = true;
		public static boolean show_other_mod_items = true;
		
		public static boolean strip_logs = true;
		
		public static boolean wool_more_colors = true;
		public static boolean wool_slabs_stairs = true;
		
		public static boolean clay_more_colors = true;
		public static boolean clay_slabs_stairs = true;
		
		public static boolean glass_more_colors = true;
		public static boolean glass_slabs_stairs = true;
		
		public static boolean v_concrete_more_colors = ModList.isVanillaConcreteProvided();
		public static boolean v_concrete_slabs_stairs = ModList.isVanillaConcreteProvided();
		
		public static boolean hbm_concrete_more_colors = ModList.HBM_NTM.isLoaded();
		public static boolean hbm_concrete_slabs_stairs = ModList.HBM_NTM.isLoaded();
		
		public static boolean netherite_tools = ModList.ET_FUTURUM.isLoaded();
		public static boolean hbm_tools = ModList.HBM_NTM.isLoaded();
		
		public static String[] oredict_priority_list = new String[] {"hbm"};
		
		public static void load()
		{
			ModConfig._category = "general";
			ModConfig.setCategoryComment("General mod settings.");
			
			enable_motd = ModConfig.getBool("0000.enable_motd", "Enable chat MotD", true);
			
			creative_search_tab_fix = ModConfig.getBool("0100.creative_search_tab_fix", "Employ a fix to make Res Additae items show up in the 'Search Items' tab of the Creative inventory", true);
			show_other_mod_items = ModConfig.getBool("0101.show_other_mod_items", "If a Res Additae item is replaced by one from another mod (e.g. raw metals), show it in the Res Additae creative tab", true);
			
			strip_logs = ModConfig.getBool("0300.stripable_logs", "Enable stripping wood logs with an axe", true);
			
			wool_more_colors = ModConfig.getBool("0400.wool_more_colors", "Add more wool colors", wool_more_colors);
			wool_slabs_stairs = ModConfig.getBool("0401.wool_slabs_stairs", "Add wool slabs and stairs", wool_slabs_stairs);
			clay_more_colors = ModConfig.getBool("0402.clay_more_colors", "Add more clay colors", clay_more_colors);
			clay_slabs_stairs = ModConfig.getBool("0403.clay_slabs_stairs", "Add clay slabs and stairs", clay_slabs_stairs);
			glass_more_colors = ModConfig.getBool("0404.glass_more_colors", "Add more glass colors", glass_more_colors);
			glass_slabs_stairs = ModConfig.getBool("0405.glass_slabs_stairs", "Add glass slabs and stairs", glass_slabs_stairs);
			v_concrete_more_colors = ModConfig.getBool("0406.vanilla_concrete_more_colors", "Add more vanilla concrete colors", v_concrete_more_colors);
			v_concrete_slabs_stairs = ModConfig.getBool("0407.vanilla_concrete_slabs_stairs", "Add vanilla concrete slabs and stairs", v_concrete_slabs_stairs);
			hbm_concrete_more_colors = ModConfig.getBool("0408.hbm_concrete_more_colors", "Add more Nuclear Tech Mod concrete colors", hbm_concrete_more_colors);
			hbm_concrete_slabs_stairs = ModConfig.getBool("0409.hbm_concrete_slabs_stairs", "Add Nuclear Tech Mod concrete slabs and stairs", hbm_concrete_slabs_stairs);
			
			if ((v_concrete_more_colors && hbm_concrete_more_colors) || (v_concrete_slabs_stairs && hbm_concrete_slabs_stairs)) {
				ResAdditae.LOG.warn("Vanilla (Et Futurum) concrete and Nuclear Tech Mod concrete enabled simultaneously. This is probably a misconfiguration.");
			}
			
			netherite_tools = ModConfig.getBool("0500.netherite_tools", "Enable netherite scythes and halberds", netherite_tools);
			hbm_tools = ModConfig.getBool("0501.hbm_tools", "Enable NTM material scythes and halberds", hbm_tools);
			
			oredict_priority_list = ModConfig.getStringList("0600.oredict_priority", "When choosing an item from a list of ores in the Ore Dictionary, prefer items from the following mods.\nMods at the top of the list will take priority", oredict_priority_list);
		}
	}
	
	public static class Player
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
	
	public static class Biomes
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
	
	public static class CommonFeatures
	{
		public static boolean fill_command = true;
		
		public static void load()
		{
			ModConfig._category = "common_features";
			ModConfig.setCategoryComment("Enable/disable features that are often added by multiple mods.");
			
			fill_command = ModConfig.getBool("0000.fill_command", "Add the /fill command", true);
		}
	}
	
	public static class CommonItems
	{
		public static boolean andesite = true;
		public static boolean polished_andesite = true;
		public static boolean diorite = true;
		public static boolean polished_diorite = true;
		public static boolean granite = true;
		public static boolean polished_granite = true;
		
		public static boolean prismarine = true;
		public static boolean prismarine_bricks = true;
		public static boolean dark_prismarine = true;
		
		public static boolean iron_nuggets = true;
		public static boolean raw_silver = true;
		public static boolean raw_platinum = true;
		
		public static void load()
		{
			ModConfig._category = "common_items";
			ModConfig.setCategoryComment("Enable/disable items that are often added by multiple mods.");
			
			andesite = ModConfig.getBool("andesite", null, !ModList.ET_FUTURUM.isLoaded());
			polished_andesite = ModConfig.getBool("polished_andesite", null, !ModList.ET_FUTURUM.isLoaded());
			diorite = ModConfig.getBool("diorite", null, !ModList.ET_FUTURUM.isLoaded());
			polished_diorite = ModConfig.getBool("polished_diorite", null, !ModList.ET_FUTURUM.isLoaded());
			granite = ModConfig.getBool("granite", null, !ModList.ET_FUTURUM.isLoaded());
			polished_granite = ModConfig.getBool("polished_granite", null, !ModList.ET_FUTURUM.isLoaded());
			
			prismarine = ModConfig.getBool("prismarine", null, !ModList.ET_FUTURUM.isLoaded());
			prismarine_bricks = ModConfig.getBool("prismarine_bricks", null, !ModList.ET_FUTURUM.isLoaded());
			dark_prismarine = ModConfig.getBool("dark_prismarine", null, !ModList.ET_FUTURUM.isLoaded());
			
			iron_nuggets = ModConfig.getBool("iron_nuggets", null, !ModList.ET_FUTURUM.isLoaded());
			raw_silver = ModConfig.getBool("raw_silver", null, !ModList.ET_FUTURUM.isLoaded());
			raw_platinum = ModConfig.getBool("raw_platinum", null, !ModList.ET_FUTURUM.isLoaded());
		}
	}
	
	/* Originally named this World, then Eclipse put this above net.minecraft.world.World in the import menu */
	public static class WorldGen
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
}
