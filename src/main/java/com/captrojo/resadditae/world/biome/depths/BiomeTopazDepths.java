package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

import net.minecraft.world.World;

public class BiomeTopazDepths extends BiomeDepthsBase
{
	public BiomeTopazDepths(int id)
	{
		super(id, new int[] {0xfbfcb0, 0xe9ed2f, 0xbfba63});

		this.setBiomeName("The Depths (Topaz)");
		this.setTemperatureRainfall(0.9f, 0.87f);
	}

	@Override
	public void decorate(World world, Random rand, int x1, int z1)
	{
		super.decorate(world, rand, x1, z1);
	}
}
