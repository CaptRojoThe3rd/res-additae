package com.captrojo.resadditae.block;

import com.captrojo.resadditae.config.CommonConfig;

import net.minecraft.block.Block;

public class SlabAssociations
{
	public static Block getBlockFromSlab(Block block)
	{
		Block b;
		b = WoodTypes.getBlockFromSlab(block);
		if (b != null) {
			return b;
		}
		b = StoneTypes.getBlockFromSlab(block);
		if (b != null) {
			return b;
		}
		
		if (block == ModBlocks.prismarine_slab_0) {
			return ModBlocks.prismarine_0;
		}
		if (block == ModBlocks.prismarine_slab_1) {
			return ModBlocks.prismarine_1;
		}
		
		for (int i = 0; i < ModBlocks.vanilla_wool_slabs.length; i++) {
			if (block == ModBlocks.vanilla_wool_slabs[i]) {
				return ModBlocks.vanilla_wool_double_slabs[i];
			}
		}
		for (int i = 0; i < ModBlocks.wool_slabs.length; i++) {
			if (block == ModBlocks.wool_slabs[i]) {
				return ModBlocks.wools[i];
			}
		}
		
		if (block == ModBlocks.glass_slab) {
			return ModBlocks.glass_double_slab;
		}
		for (int i = 0; i < ModBlocks.vanilla_stained_glass_slabs.length; i++) {
			if (block == ModBlocks.vanilla_stained_glass_slabs[i]) {
				return ModBlocks.vanilla_stained_glass_double_slabs[i];
			}
		}
		for (int i = 0; i < ModBlocks.stained_glass_slabs.length; i++) {
			if (block == ModBlocks.stained_glass_slabs[i]) {
				return ModBlocks.stained_glass[i];
			}
		}
		
		if (block == ModBlocks.hardened_clay_slab) {
			return ModBlocks.hardened_clay_double_slab;
		}
		for (int i = 0; i < ModBlocks.vanilla_stained_clay_slabs.length; i++) {
			if (block == ModBlocks.vanilla_stained_clay_slabs[i]) {
				return ModBlocks.vanilla_stained_clay_double_slabs[i];
			}
		}
		for (int i = 0; i < ModBlocks.stained_clay_slabs.length; i++) {
			if (block == ModBlocks.stained_clay_slabs[i]) {
				return ModBlocks.stained_clays[i];
			}
		}
		
		if (CommonConfig.General.vanilla_concrete_ext) {
			for (int i = 0; i < ModBlocks.vanilla_concrete_slabs.length; i++) {
				if (block == ModBlocks.vanilla_concrete_slabs[i]) {
					return ModBlocks.vanilla_concrete_double_slabs[i];
				}
			}
			for (int i = 0; i < ModBlocks.concrete_slabs.length; i++) {
				if (block == ModBlocks.concrete_slabs[i]) {
					return ModBlocks.concretes[i];
				}
			}
		}
		
		if (CommonConfig.General.hbm_concrete_ext) {
			for (int i = 0; i < ModBlocks.hbm_base_concrete_slabs.length; i++) {
				if (block == ModBlocks.hbm_base_concrete_slabs[i]) {
					return ModBlocks.hbm_base_concrete_double_slabs[i];
				}
			}
			for (int i = 0; i < ModBlocks.hbm_concrete_slabs.length; i++) {
				if (block == ModBlocks.hbm_concrete_slabs[i]) {
					return ModBlocks.hbm_concretes[i];
				}
			}
		}
		
		return null;
	}
	
	public static Block getSlabFromBlock(Block block)
	{
		Block b;
		b = WoodTypes.getSlabFromBlock(block);
		if (b != null) {
			return b;
		}
		b = StoneTypes.getSlabFromBlock(block);
		if (b != null) {
			return b;
		}
		
		if (block == ModBlocks.prismarine_0) {
			return ModBlocks.prismarine_slab_0;
		}
		if (block == ModBlocks.prismarine_1) {
			return ModBlocks.prismarine_slab_1;
		}
		
		for (int i = 0; i < ModBlocks.vanilla_wool_double_slabs.length; i++) {
			if (block == ModBlocks.vanilla_wool_double_slabs[i]) {
				return ModBlocks.vanilla_wool_slabs[i];
			}
		}
		for (int i = 0; i < ModBlocks.wools.length; i++) {
			if (block == ModBlocks.wools[i]) {
				return ModBlocks.wool_slabs[i];
			}
		}
		
		if (block == ModBlocks.glass_double_slab) {
			return ModBlocks.glass_slab;
		}
		for (int i = 0; i < ModBlocks.vanilla_stained_glass_double_slabs.length; i++) {
			if (block == ModBlocks.vanilla_stained_glass_double_slabs[i]) {
				return ModBlocks.vanilla_stained_glass_slabs[i];
			}
		}
		for (int i = 0; i < ModBlocks.stained_glass.length; i++) {
			if (block == ModBlocks.stained_glass[i]) {
				return ModBlocks.stained_glass_slabs[i];
			}
		}
		
		if (block == ModBlocks.hardened_clay_double_slab) {
			return ModBlocks.hardened_clay_slab;
		}
		for (int i = 0; i < ModBlocks.vanilla_stained_clay_double_slabs.length; i++) {
			if (block == ModBlocks.vanilla_stained_clay_double_slabs[i]) {
				return ModBlocks.vanilla_stained_clay_slabs[i];
			}
		}
		for (int i = 0; i < ModBlocks.stained_clays.length; i++) {
			if (block == ModBlocks.stained_clays[i]) {
				return ModBlocks.stained_clay_slabs[i];
			}
		}
		
		if (CommonConfig.General.vanilla_concrete_ext) {
			for (int i = 0; i < ModBlocks.vanilla_concrete_double_slabs.length; i++) {
				if (block == ModBlocks.vanilla_concrete_double_slabs[i]) {
					return ModBlocks.vanilla_concrete_slabs[i];
				}
			}
			for (int i = 0; i < ModBlocks.concretes.length; i++) {
				if (block == ModBlocks.concretes[i]) {
					return ModBlocks.concrete_slabs[i];
				}
			}
		}
		
		if (CommonConfig.General.hbm_concrete_ext) {
			for (int i = 0; i < ModBlocks.hbm_base_concrete_double_slabs.length; i++) {
				if (block == ModBlocks.hbm_base_concrete_double_slabs[i]) {
					return ModBlocks.hbm_base_concrete_slabs[i];
				}
			}
			for (int i = 0; i < ModBlocks.hbm_concretes.length; i++) {
				if (block == ModBlocks.hbm_concretes[i]) {
					return ModBlocks.hbm_concrete_slabs[i];
				}
			}
		}
		
		return null;
	}
}
