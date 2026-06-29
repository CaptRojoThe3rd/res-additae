package com.captrojo.resadditae.creativetab;

import java.util.ArrayList;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.item.ModItems;

import net.minecraft.item.ItemStack;

public class TabEquipment
{
	public static ModCreativeTab tab;
	
	public static void create()
	{
		tab = new ModCreativeTab("resadditae.equipment", ModItems.silver_pickaxe);
		
		tab.add(ModItems.wood_scythe);
		tab.add(ModItems.wood_halberd);
		
		tab.add(ModItems.stone_scythe);
		tab.add(ModItems.stone_halberd);
		
		tab.add(ModItems.iron_scythe);
		tab.add(ModItems.iron_halberd);
		
		tab.add(ModItems.gold_scythe);
		tab.add(ModItems.gold_halberd);
		
		tab.add(ModItems.diamond_scythe);
		tab.add(ModItems.diamond_halberd);
		
		if (CommonConfig.General.netherite_tools) {
			tab.add(ModItems.netherite_scythe);
			tab.add(ModItems.netherite_halberd);
		}
		
		if (CommonConfig.General.hbm_tools) {
			tab.add(ModItems.steel_scythe);
			tab.add(ModItems.steel_halberd);
			
			tab.add(ModItems.titanium_scythe);
			tab.add(ModItems.titanium_halberd);
			
			tab.add(ModItems.cobalt_scythe);
			tab.add(ModItems.cobalt_halberd);
			
			tab.add(ModItems.starmetal_scythe);
			tab.add(ModItems.starmetal_halberd);
			
			tab.add(ModItems.cmb_scythe);
			tab.add(ModItems.cmb_halberd);
		}
		
		tab.add(ModItems.silver_pickaxe);
		tab.add(ModItems.silver_axe);
		tab.add(ModItems.silver_shovel);
		tab.add(ModItems.silver_sword);
		tab.add(ModItems.silver_hoe);
		tab.add(ModItems.silver_scythe);
		tab.add(ModItems.silver_halberd);
		tab.add(ModItems.silver_helmet);
		tab.add(ModItems.silver_chestplate);
		tab.add(ModItems.silver_leggings);
		tab.add(ModItems.silver_boots);
		
		tab.add(ModItems.platinum_pickaxe);
		tab.add(ModItems.platinum_axe);
		tab.add(ModItems.platinum_shovel);
		tab.add(ModItems.platinum_sword);
		tab.add(ModItems.platinum_hoe);
		tab.add(ModItems.platinum_scythe);
		tab.add(ModItems.platinum_halberd);
		tab.add(ModItems.platinum_helmet);
		tab.add(ModItems.platinum_chestplate);
		tab.add(ModItems.platinum_leggings);
		tab.add(ModItems.platinum_boots);
		
		tab.add(ModItems.ancient_gem_pickaxe);
		tab.add(ModItems.ancient_gem_axe);
		tab.add(ModItems.ancient_gem_shovel);
		tab.add(ModItems.ancient_gem_sword);
		tab.add(ModItems.ancient_gem_hoe);
		tab.add(ModItems.ancient_gem_scythe);
		tab.add(ModItems.ancient_gem_halberd);
		tab.add(ModItems.ancient_gem_helmet);
		tab.add(ModItems.ancient_gem_chestplate);
		tab.add(ModItems.ancient_gem_leggings);
		tab.add(ModItems.ancient_gem_boots);
	}
}
