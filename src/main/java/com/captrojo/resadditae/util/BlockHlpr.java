package com.captrojo.resadditae.util;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class BlockHlpr
{
	public static boolean isAnyBlockNearby(IBlockAccess world, int x0, int y0, int z0, int radius, Block...blocks)
	{
		for (int x1 = -radius; x1 <= radius; x1++) {
			int x = x0 + x1;
			for (int y1 = -radius; y1 <= radius; y1++) {
				int y = y0 + y1;
				for (int z1 = -radius; z1 <= radius; z1++) {
					int z = z0 + z1;
					Block block = world.getBlock(x, y, z);
					for (Block b : blocks) {
						if (block == b) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
