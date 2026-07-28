package com.captrojo.resadditae.creativetab;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.compatibility.CommonBlocks;
import com.captrojo.resadditae.compatibility.CommonItems;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItems;

import net.minecraft.item.Item;

public class TabMaterials
{
	public static ModCreativeTab tab;
	
	public static void create()
	{
		tab = new ModCreativeTab("resadditae.materials", ModItems.ingots);
		
		if (CommonConfig.CommonStuff.iron_nuggets) {
			tab.add(ModItems.iron_nugget);
		}
		
		tab.add(ModBlocks.material_piles_vanilla_a, 0, 1);
		tab.add(ModBlocks.material_piles_vanilla_b, 0, 1);
		
		for (int m : MultiItems.INGOTS.metas) {
			tab.add(ModBlocks.metal_blocks, m);
			tab.add(ModItems.ingots, m);
			tab.add(ModItems.nuggets, m);
			tab.add(ModBlocks.metal_piles_a, m);
			if (m == 0) {
				if (CommonConfig.CommonStuff.raw_silver) {
					tab.add(ModItems.raws, m);
					tab.add(ModBlocks.raw_metal_blocks, m);
				} else if (CommonConfig.General.show_other_mod_items) {
					tab.add(CommonItems.RAW_SILVER.stack(1));
					tab.add(CommonBlocks.RAW_SILVER.stack(1));
				}
			} else if (m == 1) {
				if (CommonConfig.CommonStuff.raw_platinum) {
					tab.add(ModItems.raws, m);
					tab.add(ModBlocks.raw_metal_blocks, m);
				} else if (CommonConfig.General.show_other_mod_items) {
					tab.add(CommonItems.RAW_PLATINUM.stack(1));
					tab.add(CommonBlocks.RAW_PLATINUM.stack(1));
				}
			}
		}
		for (int m : MultiItems.GEMS.metas) {
			tab.add(ModBlocks.gem_blocks, m);
			tab.add(ModItems.gems, m);
			tab.add(ModBlocks.gem_piles_a, m);
		}
		
		tab.add(ModItems.potion_ingredients, MultiItems.POTION_INGREDS.getValidMetas());
		tab.add(ModItems.dye, MultiItems.DYE.getValidMetas());
		tab.add(ModItems.shiny_rocks, MultiItems.SHINY_ROCKS.getValidMetas());
	}
}
