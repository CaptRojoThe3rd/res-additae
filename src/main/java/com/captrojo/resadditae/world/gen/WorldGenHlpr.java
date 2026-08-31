package com.captrojo.resadditae.world.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.world.gen.feature.WorldGenMinableDynamic;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenHlpr
{
	public static boolean isSimpleNaturalBlock(Block block)
	{
		return (block == Blocks.dirt || block == Blocks.grass || block == Blocks.tallgrass);
	}

	public static boolean is5x5Clearing(IBlockAccess world, int x, int y, int z)
	{
		for (int x1 = x - 2; x1 <= x + 2; x1++) {
			for (int y1 = y - 2; y1 <= y + 2; y1++) {
				for (int z1 = z - 2; z1 <= z + 2; z1++) {
					if (!isSimpleNaturalBlock(world.getBlock(x1, y1, z1))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static int getCeiling(World world, int x, int z, int start_y)
	{
		for (int y = start_y; y > 0; y--) {
			if (world.getBlock(x, y, z).isAir(world, x, y, z)) {
				return y;
			}
		}
		return 0;
	}

	public static int[] getRandCoordsInChunkAt(Random rand, int block_x, int block_z, int min_y, int max_y)
	{
		return new int[] {rand.nextInt(16) + block_x, rand.nextInt(max_y - min_y + 1) + min_y, rand.nextInt(16) + block_z};
	}

	public static void addOreSpawn(BlockMeta ore, Block target, World world, Random rand, int base_x, int base_z,
		int add_x, int add_z, int max_vein_size, int chances, int min_y, int max_y)
	{
		for (int i = 0; i < chances; i++) {
			int x = base_x + rand.nextInt(add_x);
			int y = min_y + rand.nextInt(max_y - min_y);
			int z = base_z + rand.nextInt(add_z);
			(new WorldGenMinable(ore.block, ore.meta, max_vein_size, target)).generate(world, rand, x, y, z);
		}
	}

	public static void addOreSpawn(HashMap<BlockMeta, BlockMeta> tgt_ore_map, World world, Random rand, int base_x, int base_z,
		int add_x, int add_z, int max_vein_size, int chances, int min_y, int max_y)
	{
		for (int i = 0; i < chances; i++) {
			int x = base_x + rand.nextInt(add_x);
			int y = min_y + rand.nextInt(max_y - min_y);
			int z = base_z + rand.nextInt(add_z);
			(new WorldGenMinableDynamic(max_vein_size, tgt_ore_map)).generate(world, rand, x, y, z);
		}
	}

	public static void generateFlowers(World world, Random rand, int x1, int z1, Block[] flower_blocks, int[] flower_metas, int rounds)
	{
		int b = 0;
		for (int i = 0; i < rounds; ) {
			int x = rand.nextInt(15) + x1;
			int z = rand.nextInt(15) + z1;
			int y = world.getHeightValue(x1, z1);
			Block t = world.getBlock(x, y, z);
			while (t != Blocks.grass && y > 2) {
				y--;
				t = world.getBlock(x, y, z);
			}
			y++;
	
			int flower = rand.nextInt(flower_blocks.length);
			Block flower_block = flower_blocks[flower];
			int flower_meta = flower_metas[flower];
	
			if (!flower_block.canBlockStay(world, x, y, z)) {
				b++;
				if (b == 16) break;
				continue;
			}
	
			WorldGenFlowers flower_gen = new WorldGenFlowers(flower_block);
			flower_gen.func_150550_a(flower_block, flower_meta);
			flower_gen.generate(world, rand, x, y, z);
			i++;
			b = 0;
		}
	}

	public static void generateFlowers(World world, Random rand, int x, int y, int z, Block[] flower_blocks, int[] flower_metas)
	{
		int flower = rand.nextInt(flower_blocks.length);
		Block flower_block = flower_blocks[flower];
		int flower_meta = flower_metas[flower];
	
		if (!flower_block.canBlockStay(world, x, y, z)) {
			return;
		}
	
		WorldGenFlowers flower_gen = new WorldGenFlowers(flower_block);
		flower_gen.func_150550_a(flower_block, flower_meta);
		flower_gen.generate(world, rand, x, y, z);
	}

	public static List<Integer> getFloors(World world, int chunk_x, int chunk_z)
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
}
