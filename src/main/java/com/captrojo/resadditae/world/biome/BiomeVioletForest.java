package com.captrojo.resadditae.world.biome;

import java.util.Random;

import com.captrojo.resadditae.block.MultiBlockStacks;
import com.captrojo.resadditae.world.gen.WorldGenHlpr;
import com.captrojo.resadditae.world.gen.feature.tree.ModTrees;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenForest;

public class BiomeVioletForest extends BiomeGenBase
{
	public static final Block[] FLOWER_BLOCKS = {
		MultiBlockStacks.FLOWER_ASTER.block,
		MultiBlockStacks.FLOWER_FORGETMENOT.block,
		MultiBlockStacks.FLOWER_HYACINTH.block,
		MultiBlockStacks.FLOWER_IRIS.block,
		MultiBlockStacks.FLOWER_MIMOSA.block,
		Blocks.red_flower,
		Blocks.red_flower
	};
	public static final int[] FLOWER_METAS = {
		MultiBlockStacks.FLOWER_ASTER.meta,
		MultiBlockStacks.FLOWER_FORGETMENOT.meta,
		MultiBlockStacks.FLOWER_HYACINTH.meta,
		MultiBlockStacks.FLOWER_IRIS.meta,
		MultiBlockStacks.FLOWER_MIMOSA.meta,
		1,
		2
	};

	public BiomeVioletForest(int id)
	{
		super(id);

		this.setBiomeName("Violet Forest");

		this.setTemperatureRainfall(0.3f, 0.6f);
		this.setHeight(BiomeGenBase.height_LowHills);

		this.fillerBlock = Blocks.dirt;
		this.topBlock = Blocks.grass;

		this.theBiomeDecorator.treesPerChunk = 5;
		this.theBiomeDecorator.grassPerChunk = 4;

		this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 5, 4, 8));
		
		this.flowers.clear();
		for (int i = 0; i < FLOWER_BLOCKS.length; i++) {
			this.addFlower(FLOWER_BLOCKS[i], FLOWER_METAS[i], 20);
		}
	}

	@Override
	public WorldGenAbstractTree func_150567_a(Random random)
	{
		if (random.nextInt(10) == 1) {
			return new WorldGenForest(false, false);
		}
		return ModTrees.violetGen(false);
	}

	@Override
	public void decorate(World world, Random rand, int x1, int z1)
	{
		super.decorate(world, rand, x1, z1);

		int x = rand.nextInt(15) + x1;
		int z = rand.nextInt(15) + z1;
		int y = world.getHeightValue(x, z);
		if (rand.nextInt(3) < 1) {
			(new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0)).generate(world, rand, x, y, z);
		}

		WorldGenHlpr.generateFlowers(world, rand, x1, z1, FLOWER_BLOCKS, FLOWER_METAS, 2);
	}
}
