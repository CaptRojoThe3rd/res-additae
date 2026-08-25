package com.captrojo.resadditae.world.biome;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.MultiBlockStacks;
import com.captrojo.resadditae.world.ModWorldGen;
import com.captrojo.resadditae.world.gen.feature.WorldGenShallowPond;
import com.captrojo.resadditae.world.gen.feature.tree.ModTrees;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenTaiga2;

public class BiomeEnchantedAshForest extends BiomeGenBase
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
	
	public BiomeEnchantedAshForest(int id)
	{
		super(id);

		this.setBiomeName("Enchanted Ash Forest");
		
		this.setTemperatureRainfall(0.3f, 0.6f);
		this.setHeight(new BiomeGenBase.Height(0.5f, 0.05f));

		this.fillerBlock = Blocks.dirt;
		this.topBlock = Blocks.grass;

		this.theBiomeDecorator.treesPerChunk = 5;
		this.theBiomeDecorator.grassPerChunk = 5;

		this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 10, 8, 16));
		
		this.flowers.clear();
		for (int i = 0; i < FLOWER_BLOCKS.length; i++) {
			this.addFlower(FLOWER_BLOCKS[i], FLOWER_METAS[i], 20);
		}
	}
	
	@Override
	public WorldGenAbstractTree func_150567_a(Random random)
	{
		if (random.nextInt(10) == 1) {
			return new WorldGenTaiga2(false);
		}
		return ModTrees.enchantedAshGen(false);
	}
	
	@Override
	public void decorate(World world, Random rand, int x1, int z1)
	{
		super.decorate(world, rand, x1, z1);

		int x = rand.nextInt(16) + x1;
		int z = rand.nextInt(16) + z1;
		int y = world.getHeightValue(x, z);
		if (rand.nextInt(3) < 1) {
			(new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0)).generate(world, rand, x, y, z);
		}
		
		x = rand.nextInt(16) + x1;
		z = rand.nextInt(16) + z1;
		y = world.getHeightValue(x, z);
		if (rand.nextInt(2) < 1) {
			(new WorldGenShallowPond(7)).generate(world, rand, x, y, z);
		}

		ModWorldGen.generateFlowers(world, rand, x1, z1, FLOWER_BLOCKS, FLOWER_METAS, 6);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int x, int y, int z)
	{
		double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
		if (d0 < -0.3d) {
			return 0x69bc8c;
		}
		if (d0 < 0.3d) {
			return 0x69bca7;
		}
	        return 0x69a6bc;
	}
}
