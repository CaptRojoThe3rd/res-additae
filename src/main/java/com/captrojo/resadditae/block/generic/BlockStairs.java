package com.captrojo.resadditae.block.generic;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

/**
 * Literally only exists because the vanilla BlockStairs constructor is protected
 */
public class BlockStairs extends net.minecraft.block.BlockStairs
{
	protected final Block parent;
	
	public BlockStairs(String name, Block block, int meta)
	{
		super(block, meta);
		this.parent = block;
		
		this.setBlockName(name);
		this.setCreativeTab(null);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		if (this.parent == Blocks.glass) {
			return 0;
		}
		return super.quantityDropped(meta, fortune, rand);
	}
	
	@Override
	public boolean canSilkHarvest()
	{
		return true;
	}
}
