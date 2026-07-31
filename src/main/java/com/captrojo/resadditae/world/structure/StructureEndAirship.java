package com.captrojo.resadditae.world.structure;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class StructureEndAirship extends StructureVerySimple
{
	public StructureEndAirship(String name, NBTTagCompound tag, int rng_seed, BiomeGenBase[] valid_biomes, int spawn_exclusion_radius, int min_distance, int max_distance)
	{
		super(name, tag, rng_seed, valid_biomes, spawn_exclusion_radius, min_distance, max_distance);
	}
	
	@Override
	public StructurePiece getRotationOfStructure(World world, int chunk_x, int chunk_z)
	{
		double theta = Math.atan2(chunk_z, chunk_x);

		if (theta > (Math.PI * 0.25d) && theta <= (Math.PI * 0.75d)) {
			return this.pieces[0];
		}
		if (theta > (Math.PI * 0.75d) && theta <= (Math.PI * 1.25d)) {
			return this.pieces[1];
		}
		if (theta > (Math.PI * 1.25d) && theta <= (Math.PI * 1.75d)) {
			return this.pieces[2];
		}
		if (theta > (Math.PI * 1.75d) || theta <= (Math.PI * 0.25d)) {
			return this.pieces[3];
		}
		
		return this.pieces[0];
	}
	
	@Override
	public int getYCoordForGen(World world, int x, int z)
	{
		return world.rand.nextInt(40) + 150;
	}
}
