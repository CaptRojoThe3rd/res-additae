package com.captrojo.resadditae.world.gen;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.captrojo.resadditae.config.StructureConfigOptns;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class SpacedThingCheck
{
	public static final Map<Integer, String> FAILMAP_INT_STR = new HashMap<Integer, String>();
	
	static
	{
//		FAILMAP_INT_STR.put(0, "No checks failed");
//		FAILMAP_INT_STR.put(1, "Not at this position");
		FAILMAP_INT_STR.put(2, "Incorrect biome");
	}
	
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
	
	public SpacedThingCheck(StructureConfigOptns options)
	{
		this.seed = options.name.hashCode();
		this.valid_biomes = null;
		
		this.excl_rad = options.excl_rad;
		this.min_dist = options.min_dist;
		this.max_dist = options.max_dist;
	}
	
	public boolean posCheck(World world, int chunk_x, int chunk_z)
	{
		if (Math.sqrt((chunk_x * chunk_x) + (chunk_z * chunk_z)) < this.excl_rad) {
			return false;
		}
		
		if (this.max_dist == 0) {
			return true;
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
		
		int rand_add = this.max_dist - this.min_dist;
		if (rand_add > 0) {
			cxm += rand.nextInt(rand_add);
			czm += rand.nextInt(rand_add);
		}
		
		if (cx != cxm || cz != czm) {
			return false;
		}
		
		return true;
	}
	
	public boolean biomeCheck(World world, int chunk_x, int chunk_z)
	{
		if (this.valid_biomes == null) {
			return true;
		}
		for (BiomeGenBase biome : this.valid_biomes) {
			if (world.getBiomeGenForCoords((chunk_x << 4) + 8, (chunk_z << 4) + 8).biomeID == biome.biomeID) {
				return true;
			}
		}
		return false;
	}
	
	public int allChecks(World world, int chunk_x, int chunk_z)
	{
		if (!this.posCheck(world, chunk_x, chunk_z)) {
			return 1;
		}
		if (!this.biomeCheck(world, chunk_x, chunk_z)) {
			return 2;
		}
		return 0;
	}
	
	public boolean canPlaceAt(World world, int chunk_x, int chunk_z)
	{
		return this.allChecks(world, chunk_x, chunk_z) == 0;
	}
}
