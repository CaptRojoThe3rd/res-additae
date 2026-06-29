package com.captrojo.resadditae.creativetab;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.generic.BlockFallingMulti;
import com.captrojo.resadditae.block.generic.BlockMulti;
import com.captrojo.resadditae.block.generic.BlockMultiSlab;
import com.captrojo.resadditae.block.generic.BlockMultiStair;
import com.captrojo.resadditae.block.generic.BlockStainedGlassPane;
import com.captrojo.resadditae.config.CommonConfig;

import net.minecraft.item.Item;

public class TabColors
{
	public static ModCreativeTab tab;
	
	public static void create()
	{
		tab = new ModCreativeTab("resadditae.colors", Item.getItemFromBlock(ModBlocks.wools[4]));
		
		for (BlockMultiSlab block : ModBlocks.vanilla_wool_slabs) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiStair block : ModBlocks.vanilla_wool_stairs) {
			tab.add(block, 0, 8);
		}
		
		for (BlockMulti block : ModBlocks.wools) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiSlab block : ModBlocks.wool_slabs) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiStair block : ModBlocks.wool_stairs) {
			tab.add(block, 0, 8);
		}
		
		for (BlockMultiSlab block : ModBlocks.vanilla_stained_glass_slabs) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiStair block : ModBlocks.vanilla_stained_glass_stairs) {
			tab.add(block, 0, 8);
		}
		
		for (BlockMulti block : ModBlocks.stained_glass) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiSlab block : ModBlocks.stained_glass_slabs) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiStair block : ModBlocks.stained_glass_stairs) {
			tab.add(block, 0, 8);
		}
		for (BlockStainedGlassPane block : ModBlocks.stained_glass_panes) {
			tab.add(block, 0, 1, 2, 3, 4, 5, 6, 7);
			if (block.parent1 != null) {
				tab.add(block, 8, 9, 10, 11, 12, 13, 14, 15);
			}
		}
		
		for (BlockMultiSlab block : ModBlocks.vanilla_stained_clay_slabs) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiStair block : ModBlocks.vanilla_stained_clay_stairs) {
			tab.add(block, 0, 8);
		}
		
		for (BlockMulti block : ModBlocks.stained_clays) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiSlab block : ModBlocks.stained_clay_slabs) {
			tab.add(block, block.data.getValidMetas());
		}
		for (BlockMultiStair block : ModBlocks.stained_clay_stairs) {
			tab.add(block, 0, 8);
		}
		
		if (CommonConfig.General.vanilla_concrete_ext) {
			for (BlockMultiSlab block : ModBlocks.vanilla_concrete_slabs) {
				tab.add(block, block.data.getValidMetas());
			}
			for (BlockMultiStair block : ModBlocks.vanilla_concrete_stairs) {
				tab.add(block, 0, 8);
			}
			
			for (BlockMulti block : ModBlocks.concretes) {
				tab.add(block, block.data.getValidMetas());
			}
			for (BlockMultiSlab block : ModBlocks.concrete_slabs) {
				tab.add(block, block.data.getValidMetas());
			}
			for (BlockMultiStair block : ModBlocks.concrete_stairs) {
				tab.add(block, 0, 8);
			}
			for (BlockFallingMulti block : ModBlocks.concrete_powders) {
				tab.add(block, block.data.getValidMetas());
			}
		}
		
		if (CommonConfig.General.hbm_concrete_ext) {
			for (BlockMultiSlab block : ModBlocks.hbm_base_concrete_slabs) {
				tab.add(block, block.data.getValidMetas());
			}
			for (BlockMultiStair block : ModBlocks.hbm_base_concrete_stairs) {
				tab.add(block, 0, 8);
			}
			
			for (BlockMulti block : ModBlocks.hbm_concretes) {
				tab.add(block, block.data.getValidMetas());
			}
			for (BlockMultiSlab block : ModBlocks.hbm_concrete_slabs) {
				tab.add(block, block.data.getValidMetas());
			}
			for (BlockMultiStair block : ModBlocks.hbm_concrete_stairs) {
				tab.add(block, 0, 8);
			}
		}
	}
}
