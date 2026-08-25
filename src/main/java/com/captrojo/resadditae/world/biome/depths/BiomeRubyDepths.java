package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.world.gen.feature.Geodes;
import com.captrojo.resadditae.world.gen.feature.WorldGenLargeGeodeBase;
import com.captrojo.resadditae.world.gen.feature.WorldGenShallowPond;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BiomeRubyDepths extends BiomeDepthsBase
{
	public final Geodes[] geodes = {
		Geodes.ILMENITE,
		Geodes.CARNELIAN,
		Geodes.CHAROITE,
		Geodes.UNAKITE,
		Geodes.KUNZITE,
		Geodes.RHODOCHROSITE,
		Geodes.CORUNDUM,
		Geodes.PURPURITE,
		Geodes.LOLITE
	};
	
	public BiomeRubyDepths(int id)
	{
		super(id, new int[] {0xc9283d, 0xdb5365, 0xc16a76});

		this.setBiomeName("The Depths (Ruby)");
		this.setTemperatureRainfall(0.9f, 0.8f);
	}
	
	@Override
	protected WorldGenLargeGeodeBase getRandomGeode(Random rand)
	{
		return Geodes.getRandGeode(rand, Geodes.getBlocks(this.geodes[rand.nextInt(this.geodes.length)]));
	}
	
	@Override
	public void decorateFloor(World world, Random rand, int chunk_x, int chunk_z, int y)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;
		
		if (rand.nextDouble() < 0.4) {
			(new WorldGenShallowPond(new BlockMeta(Blocks.flowing_lava, 0), null, 5 + rand.nextInt(3))).generate(world, rand, block_x + 8 + rand.nextInt(8), y + 2, block_z + 8 + rand.nextInt(8));
		}
	}

	@Override
	public void decorate(World world, Random rand, int x1, int z1)
	{
		super.decorate(world, rand, x1, z1);
	}
}
