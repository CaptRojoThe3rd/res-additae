package com.captrojo.resadditae.world.gen.feature;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.config.common.DebugConfig;
import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.SpacedThingCheck;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenChasm extends WorldGenerator
{
	public static boolean generating = false;
	
	public static final SpacedThingCheck PLACEMENT_CHK = new SpacedThingCheck(
		"WorldGenChasm".hashCode(),
		null,
		WorldGenConfig.chasm_excl_rad,
		WorldGenConfig.chasm_min_dist,
		WorldGenConfig.chasm_max_dist
//		0,
//		4,
//		8
	);
	
	public static final BiomeGenBase[] COVER_BIOMES = {
		BiomeGenBase.beach,
		BiomeGenBase.coldBeach,
		BiomeGenBase.stoneBeach,
		BiomeGenBase.ocean,
		BiomeGenBase.deepOcean,
		BiomeGenBase.frozenOcean,
		BiomeGenBase.river
	};
	
	private static final int MIN_EXIT_SPACE = 10;
	
	public static World getOverworld()
	{
		try {
			return MinecraftServer.getServer().worldServerForDimension(WorldGenConfig.chasm_dimension);
		} catch (RuntimeException e) {
			ResAdditae.LOG.info("Failed to get surface world, skipping chasm gen");
			e.printStackTrace();
			return null;
		}
	}
	
	public static World getDepthsWorld()
	{
		try {
			return MinecraftServer.getServer().worldServerForDimension(WorldGenConfig.depths_dimension_id);
		} catch (RuntimeException e) {
			ResAdditae.LOG.info("Failed to get depths world, skipping chasm gen");
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean canPlaceAt(World world, int chunk_x, int chunk_z)
	{
		if (!PLACEMENT_CHK.canPlaceAt(world, chunk_x, chunk_z)) {
			return false;
		}
		
		return true;
	}
	
	public WorldGenChasm()
	{
	}
	
	public boolean shouldBeCovered(World worldsurf, int x0, int z0)
	{
		BiomeGenBase biome = worldsurf.getBiomeGenForCoords(x0, z0);
		for (BiomeGenBase chk : COVER_BIOMES) {
			if (chk.isEqualTo(biome)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean canGenerateAt(World worldsurf, World worlddepths, int x0, int y0, int z0)
	{
		int air_blocks = 0;
		for (int y = 192; y > 40; y--) {
			if (worlddepths.getBlock(x0, y, z0).isAir(worlddepths, x0, y, z0)) {
				air_blocks++;
				if (air_blocks == 10) {
					break;
				}
				continue;
			}
			air_blocks = 0;
		}
		if (air_blocks < 10) {
			if (ResAdditae.testing_mode || DebugConfig.log_failed_structure_gens) {
				ResAdditae.LOG.info("Did not generate a chasm due to lack of space in the Depths");
			}
			return false;
		}
		
		return true;
	}
	
	private int[][] getMessyCircle(Random rand, int avg_rad)
	{
		int[][] arr = new int[avg_rad * 2 + 1][avg_rad * 2 + 1];
		for (int x = -avg_rad; x <= avg_rad; x++) {
			for (int z = -avg_rad; z <= avg_rad; z++) {
				if (Math.sqrt(x * x + z * z) > ((double) avg_rad - rand.nextDouble())) {
					arr[x + avg_rad][z + avg_rad] = 0;
				} else if (Math.sqrt(x * x + z * z) > ((double) avg_rad - rand.nextDouble() - 3)) {
					arr[x + avg_rad][z + avg_rad] = 1;
				} else {
					arr[x + avg_rad][z + avg_rad] = 2;
				}
			}
		}
		return arr;
	}
	
	public void generateDepthsPortion(World worlddepths, Random rand, int x0, int y0, int z0, int radius,
		BlockMeta air, BlockMeta depth_side)
	{
		int[][] circle = this.getMessyCircle(rand, radius);
		int circle_bit = 0;
		
		int bottom_y;
		int air_blocks = 0;
		for (bottom_y = 192; bottom_y > 40; bottom_y--) {
			if (worlddepths.getBlock(x0, bottom_y, z0).isAir(worlddepths, x0, bottom_y, z0)) {
				air_blocks++;
				if (air_blocks == 10) {
					break;
				}
				continue;
			}
			air_blocks = 0;
		}
		
		if (air_blocks == 0) {
			air = new BlockMeta(Blocks.water, 0);
		}
		
		for (int y = 255; y > bottom_y; y--) {
			if ((y & 0x1) == circle_bit) {
				circle = this.getMessyCircle(rand, radius);
				circle_bit ^= rand.nextInt(2);
			}
			for (int xc = 0; xc < circle.length; xc++) {
				int x = x0 + xc - radius;
				for (int zc = 0; zc < circle.length; zc++) {
					int z = z0 + zc - radius;
					Block existing = worlddepths.getBlock(x, y, z);
					
					if (circle[xc][zc] == 0) {
						continue;
					} else if (circle[xc][zc] == 1) {
						if (existing.isAir(worlddepths, x, y, z)) {
							continue;
						}
						if (existing == ModBlocks.depth_stones_special) {
							continue;
						}
						this.setBlockAndNotifyAdequately(worlddepths, x, y, z, depth_side.block, depth_side.meta);
					} else {
						if (y > 250) {
							this.setBlockAndNotifyAdequately(worlddepths, x, y, z, ModBlocks.depths_portal, 0);
						} else {
							this.setBlockAndNotifyAdequately(worlddepths, x, y, z, air.block, air.meta);
						}
					}
				}
			}
		}
	}
	
	public void generateSurfacePortion(World worldsurf, Random rand, int x0, int y0, int z0, int radius,
		BlockMeta air, BlockMeta surf_side, int y_limit)
	{
		int[][] circle = this.getMessyCircle(rand, radius);
		int circle_bit = 0;
		
		for (int y = 0; y < y_limit; y++) {
			if ((y & 0x1) == circle_bit) {
				circle = this.getMessyCircle(rand, radius);
				circle_bit ^= rand.nextInt(2);
			}
			for (int xc = 0; xc < circle.length; xc++) {
				int x = x0 + xc - radius;
				for (int zc = 0; zc < circle.length; zc++) {
					int z = z0 + zc - radius;
					Block existing = worldsurf.getBlock(x, y, z);
					
					if (circle[xc][zc] == 0) {
						continue;
					} else if (circle[xc][zc] == 1 && !existing.isOpaqueCube()) {
						if (y < 63) {
							this.setBlockAndNotifyAdequately(worldsurf, x, y, z, surf_side.block, surf_side.meta);
						} else if (y < (y_limit - 3)) {
							this.setBlockAndNotifyAdequately(worldsurf, x, y, z, air.block, air.meta);
						}
					} else if (circle[xc][zc] == 2) {
						if (y < 4) {
							this.setBlockAndNotifyAdequately(worldsurf, x, y, z, ModBlocks.depths_portal, 0);
						} else if (y > (y_limit - 3)) {
							if (rand.nextBoolean()) {
								this.setBlockAndNotifyAdequately(worldsurf, x, y, z, air.block, air.meta);
							}
						} else {
							this.setBlockAndNotifyAdequately(worldsurf, x, y, z, air.block, air.meta);
						}
					}
				}
			}
		}
		
		if (air.block == ModBlocks.flashover_air) {
			for (int y = 0; y < y_limit; y++) {
				for (int xo = -20; xo <= 20; xo++) {
					for (int zo = -20; zo <= 20; zo++) {
						double d = Math.sqrt(xo * xo + zo * zo);
						if (d < radius || d > 20) {
							continue;
						}
						int x = x0 + xo;
						int z = z0 + zo;
						Block existing = worldsurf.getBlock(x, y, z);
						if (existing.isAir(worldsurf, x, y, z)) {
							this.setBlockAndNotifyAdequately(worldsurf, x, y, z, air.block, air.meta);
						}
					}
				}
			}
		}
	}
	
	public void generateEntireChasm(World worldsurf, World worlddepths, Random rand, int x0, int y0, int z0, int radius,
		BlockMeta air, BlockMeta depth_side, BlockMeta surf_side, int surf_y_limit)
	{
		this.generateDepthsPortion(worlddepths, rand, x0, y0, z0, radius, air, depth_side);
		this.generateSurfacePortion(worldsurf, rand, x0, y0, z0, radius, air, surf_side, surf_y_limit);
	}
	
	public boolean generateRespawned(Random rand, int chunk_x, int chunk_z)
	{
		World worldsurf = getOverworld();
		World worlddepths = getDepthsWorld();
		if (worldsurf == null || worlddepths == null) {
			return false;
		}
		
		int x0 = chunk_x << 4;
		int z0 = chunk_z << 4;
		int y0 = worldsurf.getHeightValue(x0, z0);
		
		if (!this.canGenerateAt(worldsurf, worlddepths, x0, y0, z0)) {
			return false;
		}
		
		BlockMeta air;
		if (rand.nextInt(5) == 0) {
			air = new BlockMeta(ModBlocks.flashover_air, 1);
		} else {
			air = new BlockMeta(Blocks.air, 0);
		}
		BlockMeta depth_side = new BlockMeta(ModBlocks.depth_stones_special, 1);
		BlockMeta surf_side = new BlockMeta(Blocks.stone, 0);
		
		int radius = rand.nextInt(5) + 8;
		
		this.generateEntireChasm(worldsurf, worlddepths, rand, x0, y0, z0, radius, air, depth_side, surf_side, 5);
		return true;
	}
	
	@Override
	public boolean generate(World world, Random rand, int x0, int y0, int z0)
	{
		if (generating) {
			ResAdditae.LOG.error("Didn't generate chasm because one was already being generated");
			return false;
		}
		generating = true;
		
		World worldsurf = getOverworld();
		World worlddepths = getDepthsWorld();
		if (worldsurf == null || worlddepths == null) {
			ResAdditae.LOG.error("Didn't generate chasm due to world error");
			generating = false;
			return false;
		}
		
		if (!this.canGenerateAt(worldsurf, worlddepths, x0, y0, z0)) {
			generating = false;
			return false;
		}
		
		if (ResAdditae.testing_mode || DebugConfig.log_structure_gens) {
			ResAdditae.LOG.info(String.format("Generated chasm at (%d, %d, %d)", x0, y0, z0));
		}

		BlockMeta air;
		if (rand.nextInt(5) == 0) {
			air = new BlockMeta(ModBlocks.flashover_air, 1);
		} else {
			air = new BlockMeta(Blocks.air, 0);
		}
		BlockMeta depth_side = new BlockMeta(ModBlocks.depth_stones_special, 1);
		BlockMeta surf_side = new BlockMeta(Blocks.stone, 0);
		
		int radius = rand.nextInt(5) + 8;
		
		int surf_y_limit = 256;
		if (this.shouldBeCovered(worldsurf, x0, z0)) {
			surf_y_limit = 24;
			if (ResAdditae.testing_mode || DebugConfig.log_structure_gens) {
				ResAdditae.LOG.info("The chasm was generated completely underground");
			}
		}
		
		this.generateEntireChasm(worldsurf, worlddepths, rand, x0, y0, z0, radius, air, depth_side, surf_side, surf_y_limit);
		generating = false;
		return true;
	}
}
