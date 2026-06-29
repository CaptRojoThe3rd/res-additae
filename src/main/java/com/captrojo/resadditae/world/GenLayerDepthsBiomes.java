package com.captrojo.resadditae.world;

import com.captrojo.resadditae.world.biome.ModBiomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerDepthsBiomes extends GenLayer
{
	private static final BiomeGenBase[] BIOMES = {
		ModBiomes.depths_amber,
		ModBiomes.depths_jade,
		ModBiomes.depths_ruby,
		ModBiomes.depths_sapphire,
		ModBiomes.depths_topaz
	};

	public GenLayerDepthsBiomes(long l)
	{
		super(l);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth)
	{
		int[] dest = IntCache.getIntCache(width * depth);

		for (int k = 0; k < depth; ++k) {
			for (int i = 0; i < width; ++i) {
				initChunkSeed(x + i, z + k);
				dest[i + k * width] = BIOMES[nextInt(BIOMES.length)].biomeID;
			}
		}

		return dest;
	}
}
