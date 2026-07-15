package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBasicTransparent extends BlockBasicWDblSlab
{
	public BlockBasicTransparent(String name, String texture_name, IBlockData data)
	{
		super(name, texture_name, data);
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
