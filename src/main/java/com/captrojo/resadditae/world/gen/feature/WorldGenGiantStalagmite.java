package com.captrojo.resadditae.world.gen.feature;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;

import net.minecraft.world.World;

public class WorldGenGiantStalagmite extends WorldGenGiantStalactite
{
	public WorldGenGiantStalagmite(BlockMeta block)
	{
		super(block);
	}
	
	public WorldGenGiantStalagmite(BlockMeta block, int min_size, int max_size)
	{
		super(block, min_size, max_size);
	}
	
	@Override
	public boolean generate(World world, Random rand, int x0, int y0, int z0, int radius)
	{
		boolean[][][] arr_yxz = this.createArray(rand, radius);
		
		int y2;
	y2Loop:
		for (y2 = 0; y2 < 32; y2++) {
			int y = y0 - y2;
			for (int x1 = 0; x1 < arr_yxz[0].length; x1++) {
				int x = x0 + x1;
				for (int z1 = 0; z1 < arr_yxz[0][0].length; z1++) {
					int z = z0 + z1;
					if (!arr_yxz[0][x1][z1]) {
						continue;
					}
					if (!world.getBlock(x, y, z).isOpaqueCube()) {
						continue y2Loop;
					}
				}
			}
			break;
		}
		
		for (int y1 = 0; y1 < arr_yxz.length; y1++) {
			int y = y0 + y1 - y2;
			this.placeRow(world, arr_yxz[y1], x0, y, z0);
		}
		
		return true;
	}
}
