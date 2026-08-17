package com.captrojo.resadditae.world.gen.structure;

import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.SpacedThingCheck;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class MapGenScattered extends MapGenStructure
{
	final String name;
	final SpacedThingCheck pos_check;
	
	public MapGenScattered(String name, BiomeGenBase[] biomes, int excl_rad, int min_dist, int max_dist)
	{
		this.name = name;
		this.pos_check = new SpacedThingCheck(this.name.hashCode(), biomes, excl_rad, min_dist, max_dist);
	}
	
	@Override
	public String func_143025_a()
	{
		return this.name;
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunk_x, int chunk_z)
	{
		return this.pos_check.canPlaceAt(this.worldObj, chunk_x, chunk_z);
	}
	
	@Override
	abstract protected StructureStart getStructureStart(int chunk_x, int chunk_z);
}
