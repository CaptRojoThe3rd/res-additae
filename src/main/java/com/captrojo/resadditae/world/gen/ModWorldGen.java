package com.captrojo.resadditae.world.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.compatibility.CommonBlocks;
import com.captrojo.resadditae.config.common.CommonStuffConfig;
import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.world.gen.feature.WorldGenChasm;
import com.captrojo.resadditae.world.gen.feature.WorldGenMinableDynamic;
import com.captrojo.resadditae.world.gen.feature.tree.ModTrees;
import com.captrojo.resadditae.world.gen.structure.MapGenDarkDungeon;
import com.captrojo.resadditae.world.gen.structure.MapGenEndAirship;
import com.captrojo.resadditae.world.gen.structure.MapGenSnowDungeon;
import com.captrojo.resadditae.world.gen.structure.MapGenWoodenHouse;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenStructure;

public class ModWorldGen implements IWorldGenerator
{
	public static int cur_chunk_x;
	public static int cur_chunk_z;
	
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
	
	private final SpacedThingCheck depths_gas_fracture_chk;
	private final HashMap<BlockMeta, BlockMeta> depths_gas_fracture_map;
	
	private final MapGenStructure mapgen_wooden_house;
	private final MapGenStructure mapgen_dark_dungeon;
	private final MapGenStructure mapgen_snow_dungeon;
	
	private final MapGenStructure mapgen_end_airship;
	
	public ModWorldGen()
	{
		this.depths_gas_fracture_chk = new SpacedThingCheck("depths_gas_fracture_chk".hashCode(), null, 0, 16, 24);
		this.depths_gas_fracture_map = new HashMap<BlockMeta, BlockMeta>();
		for (int m = 0; m < 6; m++) {
			this.depths_gas_fracture_map.put(new BlockMeta(ModBlocks.depth_stones, m), new BlockMeta(ModBlocks.depth_stones_special, 1));
		}
		this.depths_gas_fracture_map.put(new BlockMeta(Blocks.air, 0), new BlockMeta(ModBlocks.flashover_air, 1));
		
		this.mapgen_wooden_house = new MapGenWoodenHouse();
		this.mapgen_dark_dungeon = new MapGenDarkDungeon();
		this.mapgen_snow_dungeon = new MapGenSnowDungeon();
		
		this.mapgen_end_airship = new MapGenEndAirship();
	}
	
	@Override
	public void generate(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		ModWorldGen.cur_chunk_x = chunk_x;
		ModWorldGen.cur_chunk_z = chunk_z;
		
		if (world.provider.dimensionId == -1) {
			this.generateNether(rand, chunk_x, chunk_z, world, chunk_gen, chunk_prov);
		} else if (world.provider.dimensionId == 0) {
			this.generateOverworld(rand, chunk_x, chunk_z, world, chunk_gen, chunk_prov);
		} else if (world.provider.dimensionId == 1) {
			this.generateEnd(rand, chunk_x, chunk_z, world, chunk_gen, chunk_prov);
		} else if (world.provider.dimensionId == WorldGenConfig.depths_dimension_id) {
			this.generateDepths(rand, chunk_x, chunk_z, world, chunk_gen, chunk_prov);
		}
		
		if (world.provider.dimensionId == WorldGenConfig.chasm_dimension) {
			this.generateDepthsConnectionDimension(rand, chunk_x, chunk_z, world, chunk_gen, chunk_prov);
		}
	}
	
	public void generateNether(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;
		
		for (int y = 30; y <= 210; y += 60) {
			addOreSpawn(new BlockMeta(ModBlocks.nether_stones, 0), Blocks.netherrack, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
			addOreSpawn(new BlockMeta(ModBlocks.nether_stones, 1), Blocks.netherrack, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
		}
		
		List<Integer> floors = getFloors(world, chunk_x, chunk_z);
		
		for (int y1 : floors) {
			if (rand.nextInt(3) > 0) {
				continue;
			}
			int x1 = block_x + rand.nextInt(16);
			int z1 = block_z + rand.nextInt(16);
			for (y1 = y1 + 5; y1 > 0; y1--) {
				Block check1 = world.getBlock(x1, y1, z1);
				Block check2 = world.getBlock(x1, y1 + 1, z1);
				if (!check1.isAir(world, x1, y1, z1) && check2.isAir(world, x1, y1 + 1, z1)) {
					break;
				}
			}
			if (world.getBlock(x1, y1, z1) == Blocks.soul_sand) {
				y1++;
				WorldGenAbstractTree tree;
				if (y1 > 50) {
					tree = ModTrees.thermarbolGen(false);
				} else {
					tree = ModTrees.netherPalmGen(false);
				}
				tree.generate(world, rand, x1, y1, z1);
			}
		}
	}
	
	public void generateOverworld(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;
		
		/* Stones */
		for (int y = 30; y <= 210; y += 60) {
			if (CommonStuffConfig.andesite) {
				addOreSpawn(CommonBlocks.ANDESITE.blkm(), Blocks.stone, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
			}
			if (CommonStuffConfig.diorite) {
				addOreSpawn(CommonBlocks.DIORITE.blkm(), Blocks.stone, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
			}
			if (CommonStuffConfig.granite) {
				addOreSpawn(CommonBlocks.GRANITE.blkm(), Blocks.stone, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
			}
		}
		
		/* Ores */
		addOreSpawn(ModBlocks.ore_silver.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 1 + rand.nextInt(5), 6, 3, 40);
		addOreSpawn(ModBlocks.ore_platinum.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 1 + rand.nextInt(3), 4, 3, 20);
		
		/* Structures */
		if (WorldGenConfig.wooden_house_enabled) {
			this.mapgen_wooden_house.func_151539_a(chunk_prov, world, chunk_x, chunk_z, null);
			this.mapgen_wooden_house.generateStructuresInChunk(world, rand, chunk_x, chunk_z);
		}
		if (WorldGenConfig.dark_dungeon_enabled) {
			this.mapgen_dark_dungeon.func_151539_a(chunk_prov, world, chunk_x, chunk_z, null);
			this.mapgen_dark_dungeon.generateStructuresInChunk(world, rand, chunk_x, chunk_z);
		}
		if (WorldGenConfig.snow_dungeon_enabled) {
			this.mapgen_snow_dungeon.func_151539_a(chunk_prov, world, chunk_x, chunk_z, null);
			this.mapgen_snow_dungeon.generateStructuresInChunk(world, rand, chunk_x, chunk_z);
		}
	}
	
	public void generateEnd(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		if (WorldGenConfig.end_airship_enabled) {
			this.mapgen_end_airship.func_151539_a(chunk_prov, world, chunk_x, chunk_z, null);
			this.mapgen_end_airship.generateStructuresInChunk(world, rand, chunk_x, chunk_z);
		}
	}
	
	public void generateDepthsConnectionDimension(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;
		
		if (WorldGenChasm.canPlaceAt(world, chunk_x, chunk_z)) {
			(new WorldGenChasm()).generate(world, rand, block_x, world.getHeightValue(block_x, block_z), block_z);
		}
	}
	
	public void generateDepths(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;
		
		/* Chasms */
		if (WorldGenChasm.canPlaceAt(world, chunk_x, chunk_z)) {
			(new WorldGenChasm()).generate(world, rand, block_x, world.getHeightValue(block_x, block_z), block_z);
		}
		
		/* Random pockets of flashover air */
		if (this.depths_gas_fracture_chk.canPlaceAt(world, chunk_x, chunk_z)) {
			int block_y = this.getCeiling(world, block_x, block_z, 192);
			addOreSpawn(this.depths_gas_fracture_map, world, rand, block_x, block_z, 1, 1, 256, 1, block_y, block_y + 1);
		}
		
		int common_ore_chances = 9;
		int uncommon_ore_chances = 5;
		int rare_ore_chances = 2;
		int very_rare_ore_chances = 1;
		
//		int filled_subchunks = 0;
//		for (int cy = 8; cy < 192; cy += 16) {
//			Block block = world.getBlock(block_x + 8, cy, block_z + 8);
//			if (block.getMaterial() != Material.air) {
//				filled_subchunks++;
//			}
//		}
//		if (filled_subchunks < 6) {
//			common_ore_chances = 2;
//			uncommon_ore_chances = 1;
//			rare_ore_chances = 0;
//			very_rare_ore_chances = 0;
//		}
		
		/* Ores */
		addOreSpawn(ModBlocks.ore_coal.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 20, common_ore_chances, 0, 192);
		addOreSpawn(ModBlocks.ore_iron.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 15, common_ore_chances, 0, 192);
		addOreSpawn(ModBlocks.ore_gold.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 15, uncommon_ore_chances, 0, 192);
		addOreSpawn(ModBlocks.ore_diamond.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 8, rare_ore_chances, 0, 192);
		addOreSpawn(ModBlocks.ore_redstone.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 12, uncommon_ore_chances, 0, 192);
		addOreSpawn(ModBlocks.ore_lapis.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 8, rare_ore_chances, 0, 192);
		addOreSpawn(ModBlocks.ore_emerald.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 5, rare_ore_chances, 0, 192);
		
		addOreSpawn(ModBlocks.ore_silver.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 12, uncommon_ore_chances, 0, 192);
		addOreSpawn(ModBlocks.ore_platinum.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 12, rare_ore_chances, 0, 192);
		
		if (ModBlocks.ore_copper.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_copper.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 15, common_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_aluminium.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_aluminium.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 15, common_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_tin.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_tin.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 15, common_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_lead.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_lead.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 15, common_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_nickel.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_nickel.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 15, common_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_mythril.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_mythril.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 8, rare_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_uranium.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_uranium.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 12, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_thorium.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_thorium.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 12, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_tungsten.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_tungsten.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 12, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_titanium.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_titanium.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 12, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_zinc.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_zinc.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 10, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_magnesium.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_magnesium.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 10, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_boron.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_boron.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 5, rare_ore_chances, 0, 192);
		}
		
		if (ModBlocks.ore_sulfur.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_sulfur.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 10, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_niter.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_niter.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 8, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_fluorite.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_fluorite.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 8, uncommon_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_beryllium.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_beryllium.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 8, rare_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_rare_earth.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_rare_earth.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 4, very_rare_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_cobalt.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_cobalt.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 4, very_rare_ore_chances, 0, 192);
		}
		if (ModBlocks.ore_cinnebar.isOreAvailable()) {
			addOreSpawn(ModBlocks.ore_cinnebar.getTgtOreMap(), world, rand, block_x, block_z, 16, 16, 4, very_rare_ore_chances, 0, 192);
		}
		/* TODO: coltan gen */
	}
}
