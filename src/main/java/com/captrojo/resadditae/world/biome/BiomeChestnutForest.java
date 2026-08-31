package com.captrojo.resadditae.world.biome;

import java.util.Random;

import com.captrojo.resadditae.block.MultiBlockStacks;
import com.captrojo.resadditae.world.gen.WorldGenHlpr;
import com.captrojo.resadditae.world.gen.feature.tree.ModTrees;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class BiomeChestnutForest extends BiomeGenBase
{
	public static final Block[] FLOWER_BLOCKS = {
		MultiBlockStacks.FLOWER_BLACK_EYED_SUSAN.block,
		MultiBlockStacks.FLOWER_CALENDULA.block,
		MultiBlockStacks.FLOWER_DAHLIA.block,
		MultiBlockStacks.FLOWER_FORGETMENOT.block,
		MultiBlockStacks.FLOWER_YELLOW_DAFFODIL.block,
		Blocks.red_flower
	};
	public static final int[] FLOWER_METAS = {
		MultiBlockStacks.FLOWER_BLACK_EYED_SUSAN.meta,
		MultiBlockStacks.FLOWER_CALENDULA.meta,
		MultiBlockStacks.FLOWER_DAHLIA.meta,
		MultiBlockStacks.FLOWER_FORGETMENOT.meta,
		MultiBlockStacks.FLOWER_YELLOW_DAFFODIL.meta,
		0
	};
	
	public BiomeChestnutForest(int id)
	{
		super(id);

		this.setBiomeName("Chestnut Forest");

		this.setTemperatureRainfall(0.7f, 0.7f);
		this.setHeight(BiomeGenBase.height_LowHills);

		this.fillerBlock = Blocks.dirt;
		this.topBlock = Blocks.grass;

		this.theBiomeDecorator.treesPerChunk = 4;
		this.theBiomeDecorator.grassPerChunk = 5;
	}
	
	@Override
	public WorldGenAbstractTree func_150567_a(Random random)
	{
		if (random.nextInt(10) == 1) {
			return new WorldGenTrees(false, 5, 0, 0, false);
		}
		return ModTrees.chestnutGen(false);
	}
	
	@Override
	public void decorate(World world, Random rand, int x1, int z1)
	{
		super.decorate(world, rand, x1, z1);

		int x = rand.nextInt(15) + x1;
		int z = rand.nextInt(15) + z1;
		int y = world.getHeightValue(x, z);
		if (rand.nextInt(3) < 1) {
			if (WorldGenHlpr.is5x5Clearing(world, x, y, z)) {
				(new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0)).generate(world, rand, x, y, z);
			}
		}

		WorldGenHlpr.generateFlowers(world, rand, x1, z1, FLOWER_BLOCKS, FLOWER_METAS, 2);
	}
}
