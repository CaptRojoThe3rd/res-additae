package com.captrojo.resadditae.world.gen.feature.tree;

import com.captrojo.resadditae.block.WoodTypes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenTreeNetherPalm extends WorldGenTreePalm
{
	public WorldGenTreeNetherPalm(boolean notify, WoodTypes wood_type, int min_height, int max_height)
	{
		super(notify, wood_type, min_height, max_height);
	}
	
	@Override
	public boolean isValidSoilBlock(World world, Block soil, int x, int y, int z)
	{
		if (soil == Blocks.soul_sand) {
			return true;
		}
		return super.isValidSoilBlock(world, soil, x, y, z);
	}
}
