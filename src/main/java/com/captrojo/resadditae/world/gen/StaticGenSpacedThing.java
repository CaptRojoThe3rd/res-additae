package com.captrojo.resadditae.world.gen;

import com.captrojo.resadditae.world.SpacedThingCheck;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class StaticGenSpacedThing
{
	private final SpacedThingCheck placement_check;
	
	public StaticGenSpacedThing(int rng_seed, BiomeGenBase[] valid_biomes, int spawn_exclusion_radius, int min_distance, int max_distance)
	{
		this.placement_check = new SpacedThingCheck(rng_seed, valid_biomes, spawn_exclusion_radius, min_distance, max_distance);
	}
	
	public abstract void generate(World world, int chunk_x, int chunk_z);
	
	public boolean canPlaceAt(World world, int chunk_x, int chunk_z)
	{
		return this.placement_check.canPlaceAt(world, chunk_x, chunk_z);
	}
}
