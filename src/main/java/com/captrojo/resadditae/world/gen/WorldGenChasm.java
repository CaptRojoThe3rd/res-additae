package com.captrojo.resadditae.world.gen;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.config.CommonConfig;
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
	public static final SpacedThingCheck PLACEMENT_CHK = new SpacedThingCheck(
		"WorldGenChasm".hashCode(),
		null,
		CommonConfig.WorldGen.chasm_excl_rad,
		CommonConfig.WorldGen.chasm_min_dist,
		CommonConfig.WorldGen.chasm_max_dist
	);
	
	public static final BiomeGenBase[] INVALID_BIOMES = {
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
			return MinecraftServer.getServer().worldServerForDimension(0);
		} catch (RuntimeException e) {
			ResAdditae.LOG.info("Failed to get overworld world, skipping chasm gen");
			return null;
		}
	}
	
	public static World getDepthsWorld()
	{
		try {
			return MinecraftServer.getServer().worldServerForDimension(CommonConfig.WorldGen.depths_dimension_id);
		} catch (RuntimeException e) {
			ResAdditae.LOG.info("Failed to get depths world, skipping chasm gen");
			return null;
		}
	}
	
	public static boolean canPlaceAt(World world, int chunk_x, int chunk_z)
	{
		if (!PLACEMENT_CHK.canPlaceAt(world, chunk_x, chunk_z)) {
			return false;
		}
		
		World worldsurf = getOverworld();
		World worlddepths = getDepthsWorld();
		if (worldsurf == null || worlddepths == null) {
			return false;
		}
		
		int x = chunk_x << 4 + 8;
		int z = chunk_z << 4 + 8;
		
		BiomeGenBase biome = worldsurf.getBiomeGenForCoords(x, z);
		for (BiomeGenBase chk : INVALID_BIOMES) {
			if (biome.isEqualTo(chk)) {
				return false;
			}
		}
		
		int air_count = 0;
		for (int y = 192; y > 40; y--) {
			if (worlddepths.getBlock(x, y, z).isAir(worlddepths, x, y, z)) {
				air_count++;
				if (air_count == MIN_EXIT_SPACE) {
					break;
				}
				continue;
			}
			air_count = 0;
		}
		if (air_count < MIN_EXIT_SPACE) {
			return false;
		}
		
		return true;
	}
	
	public WorldGenChasm()
	{
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
	
	private void generate(World worldsurf, World worlddepths, Random rand, int x0, int y0, int z0, int radius,
		BlockMeta air, BlockMeta depth_side, BlockMeta surf_side)
	{
		int[][] circle = this.getMessyCircle(rand, radius);
		int circle_bit = 0;
		
		
		/* Depths Generation */
		
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
						worlddepths.setBlock(x, y, z, depth_side.block, depth_side.meta, 2);
					} else {
						if (y > 250) {
							worlddepths.setBlock(x, y, z, ModBlocks.depths_portal);
						} else {
							worlddepths.setBlock(x, y, z, air.block, air.meta, 2);
						}
					}
				}
			}
		}
		
		
		/* Surface Generation */
		
		for (int y = 0; y < 256; y++) {
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
					} else if (circle[xc][zc] == 1 && existing.isAir(worldsurf, x, y, z)) {
						if (y < 56) {
							worldsurf.setBlock(x, y, z, surf_side.block, surf_side.meta, 2);
						} else {
							worldsurf.setBlock(x, y, z, air.block, air.meta, 2);
						}
					} else if (circle[xc][zc] == 2) {
						if (y < 4) {
							worldsurf.setBlock(x, y, z, ModBlocks.depths_portal);
						} else {
							worldsurf.setBlock(x, y, z, air.block, air.meta, 2);
						}
					}
				}
			}
		}
		
		if (air.block == ModBlocks.flashover_air) {
			for (int y = 0; y < 256; y++) {
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
							worldsurf.setBlock(x, y, z, air.block, air.meta, 2);
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean generate(World world, Random rand, int x0, int y0, int z0)
	{
		/* Checks */
		
		if (world.provider.dimensionId == 0 && world.getBlock(x0, y0 - 1, z0) == Blocks.water) {
			return false;
		}
		
		
		/* Setup */
		
		World worldsurf = getOverworld();
		World worlddepths = getDepthsWorld();
		if (worldsurf == null || worlddepths == null) {
			return false;
		}

		BlockMeta air;
		if (rand.nextInt(5) == 0) {
			air = new BlockMeta(ModBlocks.flashover_air, 0);
		} else {
			air = new BlockMeta(Blocks.air, 0);
		}
		BlockMeta depth_side = new BlockMeta(ModBlocks.depth_stones_special, 1);
		BlockMeta surf_side = new BlockMeta(Blocks.stone, 0);
		
		int radius = rand.nextInt(5) + 8;
		
		
		/* Generation */
		
		this.generate(worldsurf, worlddepths, rand, x0, y0, z0, radius, air, depth_side, surf_side);
		
		
		/* Debug Generation */
		
//		for (int y = 0; y < 256; y++) {
//			world.setBlock(x0, y, z0, Blocks.diamond_block);
//		}
//		world.setBlock(x0, y0, z0, Blocks.emerald_block);
		
		return true;
	}
}
