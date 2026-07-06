package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.world.gen.feature.WorldGenChasm;
import com.captrojo.resadditae.world.gen.feature.WorldGenGiantStalactite;

import net.minecraft.world.World;

public class BiomeSapphireDepths extends BiomeDepthsBase
{
	public BiomeSapphireDepths(int id)
	{
		super(id, new int[] {0x6883f2, 0x3653c4, 0x4859a0, 0x5968b0});
		
		this.setBiomeName("The Depths (Sapphire)");
		this.setTemperatureRainfall(0.9f, 1.0f);
	}

	@Override
	public void decorate(World world, Random rand, int chunk_x, int chunk_z)
	{
		super.decorate(world, rand, chunk_x, chunk_z);
		
		int x0 = chunk_x << 4;
		int y0;
		int z0 = chunk_z << 4;
		
		for (int i = 0; i < 6; i++) {
			for (y0 = 192; y0 > 140; y0--) {
				if (world.getBlock(x0, y0, z0).isAir(world, x0, y0, z0)) {
					break;
				}
			}
			if (y0 != 140) {
				BlockMeta bm = new BlockMeta(ModBlocks.depth_stones, rand.nextBoolean() ? 4 : 0);
				(new WorldGenGiantStalactite(bm)).generate(world, rand, x0 + rand.nextInt(12) + 4, y0, z0 + rand.nextInt(12) + 4);
			}
		}
	}
}
