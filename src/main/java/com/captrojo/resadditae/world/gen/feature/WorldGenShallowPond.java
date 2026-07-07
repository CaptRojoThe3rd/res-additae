package com.captrojo.resadditae.world.gen.feature;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenShallowPond extends WorldGenerator
{
	private static int[][] CHK_OFFS = {
		{-1, -1},
		{0, -1},
		{1, -1},
		{-1, 0},
		{0, 0},
		{1, 0},
		{-1, 1},
		{0, 1},
		{1, 1}
	};
	
	private final BlockMeta liquid;
	private final BlockMeta lily;
	private final int radius;
	
	public WorldGenShallowPond(int radius)
	{
		this(new BlockMeta(Blocks.water, 0), new BlockMeta(Blocks.waterlily, 0), radius);
	}
	
	public WorldGenShallowPond(BlockMeta liquid, BlockMeta lily, int radius)
	{
		this.liquid = liquid;
		this.lily = lily;
		this.radius = radius;
	}
	
	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		Block t = world.getBlock(x, y, z);
		while (t.getMaterial() != Material.grass && t.getMaterial() != Material.ground && t.getMaterial() != Material.rock) {
			y--;
			t = world.getBlock(x, y, z);
		}
		
		int max_offs = rand.nextInt(Math.min(radius / 5, 3) + 1);
		for (int x0 = (x - radius); x0 <= (x + radius); x0++) {
		zloop:
			for (int z0 = (z - radius); z0 <= (z + radius); z0++) {
				double dx = (Math.pow((double) (x - x0), 2));
				double dz = (Math.pow((double) (z - z0), 2));
				double d = Math.sqrt(dx + dz);
				double dc = (double) (max_offs + radius);
				if (d > dc) {
					continue;
				}
				if (d > (dc - 2d) && rand.nextBoolean()) {
					continue;
				}
				Block test = world.getBlock(x0, y + 1, z0);
				if (test != Blocks.air && !(test instanceof BlockBush)) {
					continue;
				}
				for (int[] offs : CHK_OFFS) {
					test = world.getBlock(x0 + offs[0], y, z0 + offs[1]);
					if (test == Blocks.air || (test instanceof BlockBush)) {
						continue zloop;
					}
					test = world.getBlock(x0 + offs[0], y + 1, z0 + offs[1]);
					if (test != Blocks.air && !(test instanceof BlockBush)) {
						continue zloop;
					}
				}
				if (this.lily != null && (world.getBlock(x0, y + 1, z0) instanceof BlockBush) && rand.nextBoolean()) {
					this.setBlockAndNotifyAdequately(world, x0, y + 1, z0, this.lily.block, this.lily.meta);
				}
				this.setBlockAndNotifyAdequately(world, x0, y, z0, this.liquid.block, this.liquid.meta);
			}
		}
		
		return true;
	}
}
