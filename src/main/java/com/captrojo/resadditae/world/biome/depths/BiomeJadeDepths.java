package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

import com.captrojo.resadditae.block.special.BlockMossLayer;
import com.captrojo.resadditae.world.feature.tree.ModTrees;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

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
		
		int x0 = chunk_x << 4;
		int z0 = chunk_z << 4;
		for (int x1 = 0; x1 < 16; x1++) {
			int x = x0 + x1;
			for (int y = 36; y < 192; y++) {
				for (int z1 = 0; z1 < 16; z1++) {
					int z = z0 + z1;
					if (!world.getBlock(x, y, z).isAir(world, x, y, z)) {
						continue;
					}
					double d0 = plantNoise.func_151601_a(x * 0.05, z * 0.05);
					if (d0 < 0.60) {
						continue;
					}
					int thickness = (d0 > 0.90) ? 3 : ((d0 > 0.75) ? 2 : 1);
					BlockMossLayer.placeOnAllSupportingSides(world, x, y, z, thickness);
				}
			}
		}
	}
}
