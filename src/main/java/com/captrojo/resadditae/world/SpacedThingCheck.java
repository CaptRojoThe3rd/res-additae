package com.captrojo.resadditae.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class SpacedThingCheck
{
	private final int seed;
	private final BiomeGenBase[] valid_biomes;
	
	private final int excl_rad;
	private final int min_dist;
	private final int max_dist;
	
	public SpacedThingCheck(int rng_seed, BiomeGenBase[] valid_biomes, int spawn_exclusion_radius, int min_distance, int max_distance)
	{
		this.seed = rng_seed;
		this.valid_biomes = valid_biomes;
		
		this.excl_rad = spawn_exclusion_radius;
		this.min_dist = min_distance;
		this.max_dist = max_distance;
	}
	
	public boolean canPlaceAt(World world, int chunk_x, int chunk_z)
	{
		if ((chunk_x * chunk_x) + (chunk_z * chunk_z) < (this.excl_rad * this.excl_rad)) {
			return false;
		}
		
		int cx = chunk_x;
		int cz = chunk_z;
		
		if (chunk_x < 0) {
			chunk_x -= this.max_dist - 1;
		}
		if (chunk_z < 0) {
			chunk_z -= this.max_dist - 1;
		}
		
		int cxm = chunk_x / this.max_dist;
		int czm = chunk_z / this.max_dist;
		
		Random rand = world.setRandomSeed(cxm, czm, this.seed);
		
		cxm *= this.max_dist;
		czm *= this.max_dist;
		cxm += rand.nextInt(this.max_dist - this.min_dist);
		czm += rand.nextInt(this.max_dist - this.min_dist);
		
		if (cx != cxm || cz != czm) {
			return false;
		}
		
		if (this.valid_biomes == null) {
			return true;
		}
		for (BiomeGenBase biome : this.valid_biomes) {
			if (world.getBiomeGenForCoords(cx * 16 + 8, cz * 16 + 8).biomeID == biome.biomeID) {
				return true;
			}
		}
		return false;
	}
}
