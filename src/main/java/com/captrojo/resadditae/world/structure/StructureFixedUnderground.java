package com.captrojo.resadditae.world.structure;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class StructureFixedUnderground extends StructureVerySimple
{
	private final int y_level;
	
	public StructureFixedUnderground(String name, NBTTagCompound tag, int rng_seed, BiomeGenBase[] valid_biomes, int spawn_exclusion_radius, int min_distance, int max_distance, int y_level)
	{
		super(name, tag, rng_seed, valid_biomes, spawn_exclusion_radius, min_distance, max_distance);
		this.y_level = y_level;
	}
	
	@Override
	public int getYCoordForGen(World world, int x, int z)
	{
		return this.y_level;
	}
}
