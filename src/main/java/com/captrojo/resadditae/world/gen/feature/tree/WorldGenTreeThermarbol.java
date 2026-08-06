package com.captrojo.resadditae.world.gen.feature.tree;

import com.captrojo.resadditae.block.WoodTypes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class WorldGenTreeThermarbol extends WorldGenTreeLarge
{
	public WorldGenTreeThermarbol(boolean notify, WoodTypes wood_type, int min_height, int max_height,
		int max_leaf_distance, int trunk_size, double leaf_density)
	{
		super(notify, wood_type, min_height, max_height, max_leaf_distance, trunk_size, leaf_density);
	}
	
	@Override
	boolean isValidSoilBlock(Block block, int x, int y, int z)
	{
		if (block == Blocks.soul_sand) {
			return true;
		}
		return super.isValidSoilBlock(block, x, y, z);
	}
}
