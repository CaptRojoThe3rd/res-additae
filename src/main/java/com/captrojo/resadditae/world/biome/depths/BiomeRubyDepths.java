package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

import net.minecraft.world.World;

public class BiomeRubyDepths extends BiomeDepthsBase
{
	public BiomeRubyDepths(int id)
	{
		super(id, new int[] {0xc9283d, 0xdb5365, 0xc16a76});

		this.setBiomeName("The Depths (Ruby)");
		this.setTemperatureRainfall(0.9f, 0.8f);
	}

	@Override
	public void decorate(World world, Random rand, int x1, int z1)
	{
		super.decorate(world, rand, x1, z1);
	}
}
