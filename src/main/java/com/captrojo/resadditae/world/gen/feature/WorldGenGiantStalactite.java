package com.captrojo.resadditae.world.gen.feature;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;

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
	
	private final BlockMeta stone;
	private final int min_size;
	private final int max_size;
	
	public WorldGenGiantStalactite(BlockMeta block)
	{
		this.stone = block;
		this.min_size = 4;
		this.max_size = 8;
	}
	
	public WorldGenGiantStalactite(BlockMeta block, int min_size, int max_size)
	{
		this.stone = block;
		this.min_size = min_size;
		this.max_size = max_size;
	}
	
	/*
	 * boolean[y][x][z]
	 */
	protected boolean[][][] createArray(Random rand, int size)
	{
		double scale = 1.25;
		double radius = size + rand.nextDouble() - rand.nextDouble();
		int height = MathHelper.floor_double((radius * radius - radius + 1) / scale);
		boolean[][][] arr_yxz = new boolean[height * 2 + 1][size * 2 + 3][size * 2 + 3];
		
		for (int y = 0; y < height; y++) {
			double row_rad = (radius * radius) / (scale * y + radius - 1) - 1;
			if (row_rad < 0.75) {
				continue;
			}
			for (int x = -size; x <= size; x++) {
				for (int z = -size; z <= size; z++) {
					double d = Math.sqrt(x * x + z * z);
					if (d < row_rad) {
						arr_yxz[y][x + size][z + size] = true;
					}
				}
			}
		}
		
		return arr_yxz;
	}
	
	protected void placeRow(World world, boolean[][] arr_xz, int x0, int y, int z0)
	{
		for (int x1 = 0; x1 < arr_xz.length; x1++) {
			int x = x0 + x1;
			for (int z1 = 0; z1 < arr_xz[0].length; z1++) {
				int z = z0 + z1;
				if (!arr_xz[x1][z1]) {
					continue;
				}
				this.setBlockAndNotifyAdequately(world, x, y, z, this.stone.block, this.stone.meta);
			}
		}
	}
	
	public boolean generate(World world, Random rand, int x0, int y0, int z0, int radius)
	{
		boolean[][][] arr_yxz = this.createArray(rand, radius);
		
		int y2;
	y2Loop:
		for (y2 = 0; y2 < 32; y2++) {
			int y = y0 + y2;
			for (int x1 = 0; x1 < arr_yxz[0].length; x1++) {
				int x = x0 + x1;
				for (int z1 = 0; z1 < arr_yxz[0][0].length; z1++) {
					int z = z0 + z1;
					if (!arr_yxz[0][x1][z1]) {
						continue;
					}
					if (world.getBlock(x, y, z).isAir(world, x, y, z)) {
						continue y2Loop;
					}
				}
			}
			break;
		}
		
		for (int y1 = 0; y1 < arr_yxz.length; y1++) {
			int y = y0 - y1 + y2;
			this.placeRow(world, arr_yxz[y1], x0, y, z0);
		}
		
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, int x0, int y0, int z0)
	{
		int radius = rand.nextInt(this.max_size - this.min_size + 1) + this.min_size;
		return this.generate(world, rand, x0, y0, z0, radius);
	}
}
