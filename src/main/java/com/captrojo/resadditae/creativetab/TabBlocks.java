package com.captrojo.resadditae.creativetab;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.MultiBlocks;
import com.captrojo.resadditae.block.PrismarineRuneMultiBlockData;
import com.captrojo.resadditae.block.StoneTypes;
import com.captrojo.resadditae.block.WoodTypes;
import com.captrojo.resadditae.block.generic.BlockFallingMulti;
import com.captrojo.resadditae.block.generic.BlockMulti;
import com.captrojo.resadditae.block.generic.BlockMultiSlab;
import com.captrojo.resadditae.block.generic.BlockMultiStair;
import com.captrojo.resadditae.block.generic.BlockStainedGlassPane;
import com.captrojo.resadditae.compatibility.CommonBlocks;
import com.captrojo.resadditae.config.CommonConfig.CommonItems;
import com.captrojo.resadditae.config.CommonConfig;

import net.minecraft.item.ItemStack;

public class TabBlocks
{
	public static ModCreativeTab tab;
	
	public static void create()
	{
		ModCreativeTab tab = new ModCreativeTab("resadditae.blocks", WoodTypes.ENCHANTED_ASH.getLog().stack(1).getItem());

		tab.add(ModBlocks.glass_slab, 0);
		tab.add(ModBlocks.glass_stair, 0);
		
		tab.add(ModBlocks.hardened_clay_slab, 0);
		tab.add(ModBlocks.hardened_clay_stair, 0);
		
		tab.add(ModBlocks.bountiful_stones, 0, 1, 2);
		tab.add(ModBlocks.bountiful_stone_slabs, 0, 1, 2);
		tab.add(ModBlocks.bountiful_stone_stairs_a, 0, 8);
		tab.add(ModBlocks.bountiful_stone_stairs_b, 0);
		
		tab.add(ModBlocks.nether_stones, 0, 1);
		tab.add(ModBlocks.nether_stone_slabs, 0, 1);
		tab.add(ModBlocks.nether_stone_stairs, 0, 8);

		tab.add(ModBlocks.depth_stones, ModBlocks.depth_stones.data.getValidMetas());
		tab.add(ModBlocks.depth_stone_slabs, ModBlocks.depth_stone_slabs.data.getValidMetas());
		tab.add(ModBlocks.depth_stone_stairs_a, 0, 8);
		tab.add(ModBlocks.depth_stone_stairs_b, 0, 8);
		tab.add(ModBlocks.depth_stone_stairs_c, 0, 8);
		tab.add(ModBlocks.depth_stones_special, ModBlocks.depth_stones_special.data.getValidMetas());
		
		tab.add(ModBlocks.dark_cobblestone);
		
		tab.add(ModBlocks.compacted_snow, 0, 1, 2, 3);
		tab.add(ModBlocks.compacted_snow_slab, 0, 1, 2, 3);
		tab.add(ModBlocks.compacted_snow_stair_a, 0, 8);
		tab.add(ModBlocks.compacted_snow_stair_b, 0, 8);
		
		for (WoodTypes type : WoodTypes.values()) {
			for (BlockMeta blockmeta : type.getAllWoodBlocks()) {
				tab.add(blockmeta.stack(1));
			}
		}
		
		for (StoneTypes type : StoneTypes.values()) {
			for (ItemStack stack : type.getAllBlocks()) {
				tab.add(stack);
			}
		}
		
		if (CommonConfig.CommonItems.prismarine || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.PRISMARINE.stack(1));
		}
		tab.add(ModBlocks.prismarine_0, 1, 2, 3, 4);
		tab.add(ModBlocks.prismarine_pillar, 0);
		if (CommonConfig.CommonItems.prismarine || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.PRISMARINE_SLAB.stack(1));
		}
		tab.add(ModBlocks.prismarine_slab_0, 1, 2, 3, 4);
		if (CommonConfig.CommonItems.prismarine || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.PRISMARINE_STAIR.stack(1));
		}
		tab.add(ModBlocks.prismarine_stair_0, 8);
		tab.add(ModBlocks.prismarine_stair_1, 0, 8);
		tab.add(ModBlocks.prismarine_stair_2, 0);
		
		if (CommonConfig.CommonItems.prismarine_bricks || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.PRISMARINE_BRICKS.stack(1));
		}
		tab.add(ModBlocks.prismarine_0, 6, 7);
		tab.add(ModBlocks.prismarine_pillar, 1);
		if (CommonConfig.CommonItems.prismarine_bricks || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.PRISMARINE_BRICK_SLAB.stack(1));
		}
		tab.add(ModBlocks.prismarine_slab_0, 6, 7);
		if (CommonConfig.CommonItems.prismarine_bricks || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.PRISMARINE_BRICK_STAIR.stack(1));
		}
		tab.add(ModBlocks.prismarine_stair_3, 0, 8);
		
		if (CommonConfig.CommonItems.dark_prismarine || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.DARK_PRISMARINE.stack(1));
		}
		tab.add(ModBlocks.prismarine_1, 1, 2, 3, 4);
		tab.add(ModBlocks.prismarine_pillar, 2, 3);
		if (CommonConfig.CommonItems.dark_prismarine || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.DARK_PRISMARINE_SLAB.stack(1));
		}
		tab.add(ModBlocks.prismarine_slab_1, 1, 2, 3, 4);
		if (CommonConfig.CommonItems.dark_prismarine || CommonConfig.General.show_other_mod_items) {
			tab.add(CommonBlocks.DARK_PRISMARINE_STAIR.stack(1));
		}
		tab.add(ModBlocks.prismarine_stair_4, 8);
		tab.add(ModBlocks.prismarine_stair_5, 0, 8);
		tab.add(ModBlocks.prismarine_stair_6, 0);
		
		tab.add(ModBlocks.prismarine_rune_0, PrismarineRuneMultiBlockData.RUNES_0.getValidMetas());
		tab.add(ModBlocks.prismarine_rune_1, PrismarineRuneMultiBlockData.RUNES_1.getValidMetas());
		tab.add(ModBlocks.prismarine_rune_2, PrismarineRuneMultiBlockData.RUNES_2.getValidMetas());
		tab.add(ModBlocks.prismarine_rune_3, PrismarineRuneMultiBlockData.RUNES_3.getValidMetas());
		
		ModBlocks.ore_coal.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_iron.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_gold.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_diamond.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_redstone.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_lapis.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_emerald.getSubBlocks(null, tab, tab.items);

		ModBlocks.ore_silver.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_platinum.getSubBlocks(null, tab, tab.items);

		ModBlocks.ore_copper.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_aluminium.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_tin.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_lead.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_nickel.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_mythril.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_uranium.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_thorium.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_tungsten.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_titanium.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_zinc.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_magnesium.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_boron.getSubBlocks(null, tab, tab.items);
		
		ModBlocks.ore_sulfur.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_niter.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_fluorite.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_beryllium.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_rare_earth.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_cobalt.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_cinnebar.getSubBlocks(null, tab, tab.items);
		ModBlocks.ore_coltan.getSubBlocks(null, tab, tab.items);
	}
}
