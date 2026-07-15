package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.IBlockAccess;

public class BlockMultiTransparentWDblSlab extends BlockMultiWDblSlab
{
	public BlockMultiTransparentWDblSlab(String name, IMultiBlockData block_data)
	{
		super(name, block_data);
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
