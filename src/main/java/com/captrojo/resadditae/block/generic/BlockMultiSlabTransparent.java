package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.IBlockAccess;

public class BlockMultiSlabTransparent extends BlockMultiSlab
{
	public BlockMultiSlabTransparent(String name, IMultiBlockData data)
	{
		super(name, data);
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
