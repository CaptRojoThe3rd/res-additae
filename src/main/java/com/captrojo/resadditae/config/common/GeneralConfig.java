package com.captrojo.resadditae.config.common;

import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.config.ModConfig;
import com.captrojo.resadditae.main.ResAdditae;

public class GeneralConfig
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
	
	public static boolean v_concrete_more_colors = false;
	public static boolean v_concrete_slabs_stairs = false;
	
	public static boolean hbm_concrete_more_colors = ModList.HBM_NTM.isLoaded();
	public static boolean hbm_concrete_slabs_stairs = ModList.HBM_NTM.isLoaded();
	
	public static boolean netherite_tools = ModList.ET_FUTURUM.isLoaded();
	public static boolean hbm_tools = ModList.HBM_NTM.isLoaded();
	
	public static String[] oredict_priority_list = new String[] {"hbm"};
	public static boolean use_oredict_for_equipment_recipes = false;
	
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
		use_oredict_for_equipment_recipes = ModConfig.getBool("use_oredict_for_equipment_recipes", "Use the oredict for tool and armor recipes, instead of this mod's materials only", use_oredict_for_equipment_recipes);
	}
}