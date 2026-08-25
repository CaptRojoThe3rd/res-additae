package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;

public class BlockMultiStairTransparent extends BlockMultiStair
{
	public BlockMultiStairTransparent(String name, Block block, IMultiBlockData data, int meta_0, int meta_1, boolean use_meta_1)
	{
		super(name, block, data, meta_0, meta_1, use_meta_1);
	}
	
	public BlockMultiStairTransparent(String name, BlockMulti block, int meta_0, int meta_1, boolean use_meta_1)
	{
		this(name, block, block.data, meta_0, meta_1, use_meta_1);
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
