package com.captrojo.resadditae.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

/* When in doubt, steal code from NTM */
public class WorldChunkManagerDepths extends WorldChunkManager
{
	private GenLayer biome_layer;
	private GenLayer biome_detail_layer;

	private BiomeCache biome_cache;

	public WorldChunkManagerDepths(BiomeGenLayers layers)
	{
		this.biome_cache = new BiomeCache(this);
		this.biome_layer = layers.biome_layer;
		this.biome_detail_layer = layers.biome_detail_layer;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int x, int z)
	{
		return this.biome_cache.getBiomeGenAt(x, z);
	}

	@Override
	public float[] getRainfall(float[] p_76936_1_, int p_76936_2_, int p_76936_3_, int p_76936_4_, int p_76936_5_)
	{
		if (p_76936_1_ == null || p_76936_1_.length < p_76936_4_ * p_76936_5_) {
			p_76936_1_ = new float[p_76936_4_ * p_76936_5_];
		}

		Arrays.fill(p_76936_1_, 0, p_76936_4_ * p_76936_5_, 0f);
		return p_76936_1_;
	}

	@Override
	public float getTemperatureAtHeight(float temperature, int y)
	{
		return temperature;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] biomes, int x, int z, int width, int length)
	{
		IntCache.resetIntCache();

		if (biomes == null || biomes.length < width * length) {
			biomes = new BiomeGenBase[width * length];
		}

		int[] biomeIds = this.biome_layer.getInts(x, z, width, length);

		for (int i = 0; i < width * length; ++i) {
			biomes[i] = BiomeGenBase.getBiome(biomeIds[i]);
		}

		return biomes;
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomes, int x, int z, int width, int length, boolean flag)
	{
		IntCache.resetIntCache();

		if (biomes == null || biomes.length < width * length) {
			biomes = new BiomeGenBase[width * length];
		}

		if (flag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0) {
			BiomeGenBase[] cachedBiomes = this.biome_cache.getCachedBiomes(x, z);
			System.arraycopy(cachedBiomes, 0, biomes, 0, width * length);
			return biomes;
		} else {
			int[] biomeIds = this.biome_detail_layer.getInts(x, z, width, length);

			for (int i = 0; i < width * length; ++i) {
				biomes[i] = BiomeGenBase.getBiome(biomeIds[i]);
			}

			return biomes;
		}
	}

	@Override
	public boolean areBiomesViable(int x, int z, int size, List biomes)
	{
		IntCache.resetIntCache();

		int l = x - size >> 2;
		int i1 = z - size >> 2;
		int j1 = x + size >> 2;
		int k1 = z + size >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = this.biome_layer.getInts(l, i1, l1, i2);

		for (int j2 = 0; j2 < l1 * i2; ++j2) {
			BiomeGenBase biomeGenBase = BiomeGenBase.getBiome(aint[j2]);

			if (!biomes.contains(biomeGenBase)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public ChunkPosition findBiomePosition(int x, int z, int size, List biomes, Random rand)
	{
		IntCache.resetIntCache();

		int l = x - size >> 2;
		int i1 = z - size >> 2;
		int j1 = x + size >> 2;
		int k1 = z + size >> 2;
		int l1 = j1 - l + 1;
		int i2 = k1 - i1 + 1;
		int[] aint = this.biome_layer.getInts(l, i1, l1, i2);
		ChunkPosition chunkposition = null;
		int j2 = 0;

		for (int k2 = 0; k2 < l1 * i2; ++k2) {
			int l2 = l + k2 % l1 << 2;
			int i3 = i1 + k2 / l1 << 2;
			BiomeGenBase biomegenbase = BiomeGenBase.getBiome(aint[k2]);

			if (biomes.contains(biomegenbase) && (chunkposition == null || rand.nextInt(j2 + 1) == 0)) {
				chunkposition = new ChunkPosition(l2, 0, i3);
				++j2;
			}
		}

		return chunkposition;
	}

	public void cleanupCache()
	{
		this.biome_cache.cleanupCache();
	}

	public static class BiomeGenLayers
	{
		private GenLayer biome_layer;
		private GenLayer biome_detail_layer;

		public BiomeGenLayers(GenLayer biome_layer, GenLayer biome_detail_layer, long seed)
		{
			this.biome_layer = biome_layer;
			this.biome_detail_layer = biome_detail_layer;

			biome_layer.initWorldGenSeed(seed);
			biome_detail_layer.initWorldGenSeed(seed);
		}
	}
}
