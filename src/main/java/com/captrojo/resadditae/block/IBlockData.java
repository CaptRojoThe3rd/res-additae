package com.captrojo.resadditae.block;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;

public interface IBlockData
{
	public Material getMaterial();
	public SoundType getSoundType();
	
	public boolean doesBlockShatter();
	public float getHardness(int meta);
	public float getResistance(int meta);
	public void setBlockData(Block block);
}
