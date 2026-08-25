package com.captrojo.resadditae.world.gen.layer;

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
	public int[] getInts(int x0, int z0, int width, int depth)
	{
		int[] dest = IntCache.getIntCache(width * depth);

		for (int x1 = 0; x1 < depth; x1++) {
			for (int z1 = 0; z1 < width; z1++) {
				this.initChunkSeed(x0 + z1, z0 + x1);
				dest[z1 + x1 * width] = BIOMES[this.nextInt(BIOMES.length)].biomeID;
			}
		}

		return dest;
	}
}
