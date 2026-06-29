package com.captrojo.resadditae.world.feature.tree;

import java.util.Random;

import com.captrojo.resadditae.block.WoodTypes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenShrub;

public class WorldGenTreeShrub extends WorldGenShrub
{
	private final boolean do_notify;
	private final WoodTypes wood;
	
	public WorldGenTreeShrub(boolean notify, WoodTypes wood)
	{
		super(wood.getLog().meta, wood.getLeaves().meta);
		this.do_notify = notify;
		this.wood = wood;
	}
	
	@Override
	protected void setBlockAndNotifyAdequately(World world, int x, int y, int z, Block block, int meta)
	{
		if (block == Blocks.log) {
			block = this.wood.getLog().block;
		} else if (block == Blocks.leaves) {
			block = this.wood.getLeaves().block;
		}
		if (this.do_notify) {
			world.setBlock(x, y, z, block, meta, 3);
		} else {
			world.setBlock(x, y, z, block, meta, 2);
		}
	}
}
