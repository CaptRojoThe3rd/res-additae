package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IBlockData;
import com.captrojo.resadditae.block.IDoubleSlab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBasicSlabTransparent extends BlockBasicSlab
{
	public BlockBasicSlabTransparent(String name, String texture_name, IBlockData data, IDoubleSlab double_slab)
	{
		super(name, texture_name, data, double_slab);
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}
}
