package com.captrojo.resadditae.world.gen.feature;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGiantStalactite extends WorldGenerator
{
	/*
	 * y = n^2 / (sx + n - 1) - 1
	 * x = (n(n - y - 1) + y + 1) / s(y + 1)
	 * 
	 * Shortened for y=0:
	 * x = (n^2 - n + 1) / s
	 */
	
	private BlockMeta stone;
	
	public WorldGenGiantStalactite(BlockMeta block)
	{
		this.stone = block;
	}
	
	/*
	 * boolean[y][x][z]
	 */
	private boolean[][][] createArray(int radius)
	{
		double scale = 1.25;
		int height = MathHelper.floor_double((radius * radius - radius + 1) / scale);
		boolean[][][] arr_yxz = new boolean[height * 2 + 1][radius * 2 + 1][radius * 2 + 1];
		
		for (int y = 0; y < height; y++) {
			double row_rad = (radius * radius) / (scale * y + radius - 1) - 1;
			if (row_rad < 0.75) {
				continue;
			}
			for (int x = -radius; x <= radius; x++) {
				for (int z = -radius; z <= radius; z++) {
					double d = Math.sqrt(x * x + z * z);
					if (d < row_rad) {
						arr_yxz[y][x + radius][z + radius] = true;
					}
				}
			}
		}
		
		return arr_yxz;
	}
	
	public boolean generate(World world, Random rand, int x0, int y0, int z0, int radius)
	{
		boolean[][][] arr_yxz = this.createArray(radius);
		
		int y2;
		for (y2 = 0; y2 < 32; y2++) {
			int y = y0 + y2;
			boolean f = false;
		zxLoop:
			for (int x1 = 0; x1 < arr_yxz[0].length; x1++) {
				int x = x0 + x1;
				for (int z1 = 0; z1 < arr_yxz[0][0].length; z1++) {
					int z = z0 + z1;
					if (!arr_yxz[0][x1][z1]) {
						continue;
					}
					if (world.getBlock(x, y, z).isAir(world, x, y, z)) {
						f = true;
						break zxLoop;
					}
				}
			}
			if (!f) {
				break;
			}
		}
		
		for (int y1 = 0; y1 < arr_yxz.length; y1++) {
			int y = y0 - y1 + y2;
			for (int x1 = 0; x1 < arr_yxz[0].length; x1++) {
				int x = x0 + x1;
				for (int z1 = 0; z1 < arr_yxz[0][0].length; z1++) {
					int z = z0 + z1;
					if (!arr_yxz[y1][x1][z1]) {
						continue;
					}
					this.setBlockAndNotifyAdequately(world, x, y, z, this.stone.block, this.stone.meta);
				}
			}
		}
		
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, int x0, int y0, int z0)
	{
		int radius = rand.nextInt(5) + 4;
		return this.generate(world, rand, x0, y0, z0, radius);
	}
}
