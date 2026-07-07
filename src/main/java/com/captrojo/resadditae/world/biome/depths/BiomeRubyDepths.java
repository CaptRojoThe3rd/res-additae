package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BiomeRubyDepths extends BiomeDepthsBase
{
	private int lavafalls_generated;
	
	public BiomeRubyDepths(int id)
	{
		super(id, new int[] {0xc9283d, 0xdb5365, 0xc16a76});

		this.setBiomeName("The Depths (Ruby)");
		this.setTemperatureRainfall(0.9f, 0.8f);
	}
	
	@Override
	public void decorateFloor(World world, Random rand, int chunk_x, int chunk_z, int y)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;
		
		if (rand.nextBoolean() && this.lavafalls_generated < 1) {
			int gx = block_x + rand.nextInt(16);
			int gz = block_z + rand.nextInt(16);
		gyLoop:
			for (int gy = y + 4; gy < (y + 64); gy++) {
				if (world.getBlock(gx, gy, gz).getMaterial() == Material.rock) {
					for (int oy = gy - 2; oy > 36; oy--) {
						Block test = world.getBlock(gx, oy, gz);
						if (test.isOpaqueCube()) {
							break;
						}
						if (test.getMaterial() == Material.water) {
							break gyLoop;
						}
					}
					world.setBlock(gx, gy, gz, Blocks.flowing_lava, 0, 3);
					this.lavafalls_generated++;
					break;
				}
			}
		}
	}

	@Override
	public void decorate(World world, Random rand, int x1, int z1)
	{
		this.lavafalls_generated = 0;
		super.decorate(world, rand, x1, z1);
	}
}
