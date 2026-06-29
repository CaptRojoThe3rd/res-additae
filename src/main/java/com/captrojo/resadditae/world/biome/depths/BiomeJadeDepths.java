package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

import com.captrojo.resadditae.world.feature.tree.ModTrees;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BiomeJadeDepths extends BiomeDepthsBase
{
	private int waterfalls_generated;
	
	public BiomeJadeDepths(int id)
	{
		super(id, new int[] {0x60af6d, 0x41c657, 0x83c18e});

		this.setBiomeName("The Depths (Jade)");
		this.setTemperatureRainfall(0.9f, 1.0f);
	}
	
	@Override
	public void decorateFloor(World world, Random rand, int chunk_x, int chunk_z, int y)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;
		
		if (rand.nextBoolean() && this.waterfalls_generated < 1) {
			int gx = block_x + rand.nextInt(16);
			int gz = block_z + rand.nextInt(16);
			for (int gy = y + 4; gy < (y + 64); gy++) {
				if (world.getBlock(gx, gy, gz).getMaterial() == Material.rock) {
					world.setBlock(gx, gy, gz, Blocks.flowing_water, 0, 3);
					this.waterfalls_generated++;
					break;
				}
			}
		}
		
		for (int i = 0; i < 6; i++) {
			int gx = block_x + rand.nextInt(16);
			int gz = block_z + rand.nextInt(16);
			
			ModTrees.deepwoodGen(false).generate(world, rand, gx, this.getNearestOpenY(world, gx, y, gz), gz);
		}
	}

	@Override
	public void decorate(World world, Random rand, int chunk_x, int chunk_z)
	{
		this.waterfalls_generated = 0;
		super.decorate(world, rand, chunk_x, chunk_z);
	}
}
