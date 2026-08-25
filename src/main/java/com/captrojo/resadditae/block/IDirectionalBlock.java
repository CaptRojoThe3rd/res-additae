package com.captrojo.resadditae.block;

import net.minecraft.world.IBlockAccess;

public interface IDirectionalBlock
{
	public int getDirection(IBlockAccess world, int x, int y, int z);
}
