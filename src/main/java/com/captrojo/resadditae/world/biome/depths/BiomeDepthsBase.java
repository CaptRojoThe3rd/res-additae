package com.captrojo.resadditae.world.biome.depths;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.world.gen.feature.WorldGenLargeGeodeBase;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeDepthsBase extends BiomeGenBase
{
	public int[] soil_colors;
	public double[] soil_color_thresholds;
	
	public BiomeDepthsBase(int id, int[] soil_colors)
	{
		super(id);
		
		this.soil_colors = soil_colors;
		this.soil_color_thresholds = new double[this.soil_colors.length];
		double threshold_inc = 2.0 / (double) this.soil_colors.length;
		for (int i = 0; i < this.soil_colors.length; i++) {
			this.soil_color_thresholds[i] = -1.0 + (threshold_inc * (i + 1));
		}
		
		this.setDisableRain();
		this.waterColorMultiplier = 0x1e2228;

		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
	}
	
	protected abstract WorldGenLargeGeodeBase getRandomGeode(Random rand);
	
	public int getBiomeSoilColor(int x, int y, int z)
	{
		double d0 = plantNoise.func_151601_a((double)x * 0.0225D, (double)z * 0.0225D);
		for (int i = 0; i < this.soil_colors.length; i++) {
			if (d0 < this.soil_color_thresholds[i]) {
				return this.soil_colors[i];
			}
		}
	        return this.soil_colors[0];
	}
	
	public List<Integer> getFloors(World world, int chunk_x, int chunk_z)
	{
		int x = chunk_x * 16 + 8;
		int z = chunk_z * 16 + 8;
		ArrayList<Integer> yl = new ArrayList<Integer>();
		
		for (int y = 32; y < 188; y++) {
			Block block0 = world.getBlock(x, y, z);
			Block block1 = world.getBlock(x, y - 1, z);
			
			if (block0.isAir(world, x, y, z) && !block1.isAir(world, x, y, z)) {
				yl.add(y);
			}
		}
		
		return yl;
	}
	
	public int getNearestOpenY(World world, int x, int y, int z)
	{
		for (int d = 0; d < 32; d++) {
			Block block0 = world.getBlock(x, y + d, z);
			Block block1 = world.getBlock(x, y + d - 1, z);
			if (block0.isAir(world, x, y, z) && !block1.isAir(world, x, y, z)) {
				return y + d;
			}
			block0 = world.getBlock(x, y - d, z);
			block1 = world.getBlock(x, y - d - 1, z);
			if (block0.isAir(world, x, y, z) && !block1.isAir(world, x, y, z)) {
				return y - d;
			}
		}
		return y;
	}
	
	public void decorateFloor(World world, Random rand, int chunk_x, int chunk_z, int y)
	{
	}
	
	public void decorateFloors(World world, Random rand, int chunk_x, int chunk_z)
	{
		List<Integer> y_list = this.getFloors(world, chunk_x, chunk_z);
		for (int y : y_list) {
			this.decorateFloor(world, rand, chunk_x, chunk_z, y);
		}
	}
	
	@Override
	public void decorate(World world, Random rand, int chunk_x, int chunk_z)
	{	
		if (WorldGenLargeGeodeBase.PLACEMENT_CHK.canPlaceAt(world, chunk_x, chunk_z)) {
			WorldGenLargeGeodeBase geode = this.getRandomGeode(rand);
			if (geode != null) {
				geode.generate(world, rand, chunk_x << 4, 75 + rand.nextInt(75), chunk_z << 4);
			}
		}
		
		this.decorateFloors(world, rand, chunk_x, chunk_z);
	}
}
