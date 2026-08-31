package com.captrojo.resadditae.world.biome.depths;

import java.util.Random;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.world.gen.WorldGenHlpr;
import com.captrojo.resadditae.world.gen.feature.Geodes;
import com.captrojo.resadditae.world.gen.feature.WorldGenLargeGeodeBase;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BiomeAmberDepths extends BiomeDepthsBase
{
	public final Geodes[] geodes = {
		Geodes.ILMENITE,
		Geodes.UNAKITE,
		Geodes.PORPHYRITE,
		Geodes.STROMATOLITE,
		Geodes.VARIOLITE,
		Geodes.CORUNDUM
	};
	public final Block[] flower_blocks = {ModBlocks.depths_plants};
	public final int[] flower_metas = {0};
	
	public BiomeAmberDepths(int id)
	{
		super(id, new int[] {0xbc9c69, 0xbc9c46, 0x7c7333});
		
		this.setBiomeName("The Depths (Amber)");
		this.setTemperatureRainfall(0.9f, 0.9f);
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
		
		for (int i = 0; i < 2; i++) {
			int gx = rand.nextInt(16) + block_x;
			int gz = rand.nextInt(16) + block_z;
			
			WorldGenHlpr.generateFlowers(world, rand, gx, this.getNearestOpenY(world, gx, y, gz), gz, this.flower_blocks, this.flower_metas);
		}
	}

	@Override
	public void decorate(World world, Random rand, int chunk_x, int chunk_z)
	{
		super.decorate(world, rand, chunk_x, chunk_z);
	}
}
