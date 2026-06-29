package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

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
	public void decorate(World world, Random rand, int x1, int z1)
	{
		super.decorate(world, rand, x1, z1);
	}
}
