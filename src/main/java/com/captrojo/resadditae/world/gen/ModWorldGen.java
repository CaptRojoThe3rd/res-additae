package com.captrojo.resadditae.world.gen;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.compatibility.CommonBlocks;
import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.config.common.CommonStuffConfig;
import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.world.gen.feature.WorldGenChasm;
import com.captrojo.resadditae.world.gen.feature.tree.ModTrees;
import com.captrojo.resadditae.world.gen.structure.MapGenDarkDungeon;
import com.captrojo.resadditae.world.gen.structure.MapGenEndAirship;
import com.captrojo.resadditae.world.gen.structure.MapGenSnowDungeon;
import com.captrojo.resadditae.world.gen.structure.MapGenWoodenHouse;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class ModWorldGen implements IWorldGenerator
{
	public static ModWorldGen instance;

	public static int cur_chunk_x;
	public static int cur_chunk_z;
	
	final Random rand;

	final SpacedThingCheck depths_gas_fracture_chk;
	final HashMap<BlockMeta, BlockMeta> depths_gas_fracture_map;

	final OreGenerator oregen_ow_silver;
	final OreGenerator oregen_ow_platinum;

	final OreGenerator oregen_dp_coal;
	final OreGenerator oregen_dp_iron;
	final OreGenerator oregen_dp_gold;
	final OreGenerator oregen_dp_diamond;
	final OreGenerator oregen_dp_redstone;
	final OreGenerator oregen_dp_lapis;
	final OreGenerator oregen_dp_emerald;

	final OreGenerator oregen_dp_silver;
	final OreGenerator oregen_dp_platinum;

	final OreGenerator oregen_dp_copper;
	final OreGenerator oregen_dp_aluminium;
	final OreGenerator oregen_dp_tin;
	final OreGenerator oregen_dp_lead;
	final OreGenerator oregen_dp_nickel;
	final OreGenerator oregen_dp_mythril;
	final OreGenerator oregen_dp_uranium;
	final OreGenerator oregen_dp_thorium;
	final OreGenerator oregen_dp_tungsten;
	final OreGenerator oregen_dp_titanium;
	final OreGenerator oregen_dp_zinc;
	final OreGenerator oregen_dp_magnesium;
	final OreGenerator oregen_dp_boron;

	final OreGenerator oregen_dp_sulfur;
	final OreGenerator oregen_dp_niter;
	final OreGenerator oregen_dp_fluorite;
	final OreGenerator oregen_dp_beryllium;
	final OreGenerator oregen_dp_rare_earth;
	final OreGenerator oregen_dp_cobalt;
	final OreGenerator oregen_dp_cinnebar;

	final MapGenStructure mapgen_wooden_house;
	final MapGenStructure mapgen_dark_dungeon;
	final MapGenStructure mapgen_snow_dungeon;

	final MapGenStructure mapgen_end_airship;

	public ModWorldGen()
	{
		this.rand = new Random();
		
		this.depths_gas_fracture_chk = new SpacedThingCheck("depths_gas_fracture_chk".hashCode(), null, 0, 16, 24);
		this.depths_gas_fracture_map = new HashMap<BlockMeta, BlockMeta>();
		for (int m = 0; m < 6; m++) {
			this.depths_gas_fracture_map.put(new BlockMeta(ModBlocks.depth_stones, m), new BlockMeta(ModBlocks.depth_stones_special, 1));
		}
		this.depths_gas_fracture_map.put(new BlockMeta(Blocks.air, 0), new BlockMeta(ModBlocks.flashover_air, 1));

		this.oregen_ow_silver = new OreGenerator(ModBlocks.ore_silver.getTgtOreMap(), WorldGenConfig.ore_ow_silver);
		this.oregen_ow_platinum = new OreGenerator(ModBlocks.ore_platinum.getTgtOreMap(), WorldGenConfig.ore_ow_platinum);

		this.oregen_dp_coal = new OreGenerator(ModBlocks.ore_coal.getTgtOreMap(), WorldGenConfig.ore_dp_coal);
		this.oregen_dp_iron = new OreGenerator(ModBlocks.ore_iron.getTgtOreMap(), WorldGenConfig.ore_dp_iron);
		this.oregen_dp_gold = new OreGenerator(ModBlocks.ore_gold.getTgtOreMap(), WorldGenConfig.ore_dp_gold);
		this.oregen_dp_diamond = new OreGenerator(ModBlocks.ore_diamond.getTgtOreMap(), WorldGenConfig.ore_dp_diamond);
		this.oregen_dp_redstone = new OreGenerator(ModBlocks.ore_redstone.getTgtOreMap(), WorldGenConfig.ore_dp_redstone);
		this.oregen_dp_lapis = new OreGenerator(ModBlocks.ore_lapis.getTgtOreMap(), WorldGenConfig.ore_dp_lapis);
		this.oregen_dp_emerald = new OreGenerator(ModBlocks.ore_emerald.getTgtOreMap(), WorldGenConfig.ore_dp_emerald);

		this.oregen_dp_silver = new OreGenerator(ModBlocks.ore_silver.getTgtOreMap(), WorldGenConfig.ore_dp_silver);
		this.oregen_dp_platinum = new OreGenerator(ModBlocks.ore_platinum.getTgtOreMap(), WorldGenConfig.ore_dp_platinum);

		this.oregen_dp_copper = new OreGenerator(ModBlocks.ore_copper.getTgtOreMap(), WorldGenConfig.ore_dp_copper);
		this.oregen_dp_aluminium = new OreGenerator(ModBlocks.ore_aluminium.getTgtOreMap(), WorldGenConfig.ore_dp_aluminium);
		this.oregen_dp_tin = new OreGenerator(ModBlocks.ore_tin.getTgtOreMap(), WorldGenConfig.ore_dp_tin);
		this.oregen_dp_lead = new OreGenerator(ModBlocks.ore_lead.getTgtOreMap(), WorldGenConfig.ore_dp_lead);
		this.oregen_dp_nickel = new OreGenerator(ModBlocks.ore_nickel.getTgtOreMap(), WorldGenConfig.ore_dp_nickel);
		this.oregen_dp_mythril = new OreGenerator(ModBlocks.ore_mythril.getTgtOreMap(), WorldGenConfig.ore_dp_mythril);
		this.oregen_dp_uranium = new OreGenerator(ModBlocks.ore_uranium.getTgtOreMap(), WorldGenConfig.ore_dp_uranium);
		this.oregen_dp_thorium = new OreGenerator(ModBlocks.ore_thorium.getTgtOreMap(), WorldGenConfig.ore_dp_thorium);
		this.oregen_dp_tungsten = new OreGenerator(ModBlocks.ore_tungsten.getTgtOreMap(), WorldGenConfig.ore_dp_tungsten);
		this.oregen_dp_titanium = new OreGenerator(ModBlocks.ore_titanium.getTgtOreMap(), WorldGenConfig.ore_dp_titanium);
		this.oregen_dp_zinc = new OreGenerator(ModBlocks.ore_zinc.getTgtOreMap(), WorldGenConfig.ore_dp_zinc);
		this.oregen_dp_magnesium = new OreGenerator(ModBlocks.ore_magnesium.getTgtOreMap(), WorldGenConfig.ore_dp_magnesium);
		this.oregen_dp_boron = new OreGenerator(ModBlocks.ore_boron.getTgtOreMap(), WorldGenConfig.ore_dp_boron);

		this.oregen_dp_sulfur = new OreGenerator(ModBlocks.ore_sulfur.getTgtOreMap(), WorldGenConfig.ore_dp_sulfur);
		this.oregen_dp_niter = new OreGenerator(ModBlocks.ore_niter.getTgtOreMap(), WorldGenConfig.ore_dp_niter);
		this.oregen_dp_fluorite = new OreGenerator(ModBlocks.ore_fluorite.getTgtOreMap(), WorldGenConfig.ore_dp_fluorite);
		this.oregen_dp_beryllium = new OreGenerator(ModBlocks.ore_beryllium.getTgtOreMap(), WorldGenConfig.ore_dp_beryllium);
		this.oregen_dp_rare_earth = new OreGenerator(ModBlocks.ore_rare_earth.getTgtOreMap(), WorldGenConfig.ore_dp_rare_earth);
		this.oregen_dp_cobalt = new OreGenerator(ModBlocks.ore_cobalt.getTgtOreMap(), WorldGenConfig.ore_dp_cobalt);
		this.oregen_dp_cinnebar = new OreGenerator(ModBlocks.ore_cinnebar.getTgtOreMap(), WorldGenConfig.ore_dp_cinnebar);

		this.mapgen_wooden_house = new MapGenWoodenHouse();
		this.mapgen_dark_dungeon = new MapGenDarkDungeon();
		this.mapgen_snow_dungeon = new MapGenSnowDungeon();

		this.mapgen_end_airship = new MapGenEndAirship();
	}
	
	public void setChunkRNGSeed(World world, int chunk_x, int chunk_z)
	{
		this.rand.setSeed(world.getSeed() + world.provider.dimensionId);
		/* Vanilla code does (l / 2 * 2 + 1), which is the same as the following LOL */
		final long i1 = this.rand.nextLong() | 0x1;
		final long j1 = this.rand.nextLong() | 0x1;
		this.rand.setSeed((long) chunk_x * i1 + (long) chunk_z * j1 ^ world.getSeed());
	}
	
	public void populateNether(World world, IChunkProvider chunk_prov, int chunk_x, int chunk_z)
	{
	}

	public void generateNether(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;

		for (int y = 30; y <= 210; y += 60) {
			WorldGenHlpr.addOreSpawn(new BlockMeta(ModBlocks.nether_stones, 0), Blocks.netherrack, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
			WorldGenHlpr.addOreSpawn(new BlockMeta(ModBlocks.nether_stones, 1), Blocks.netherrack, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
		}

		List<Integer> floors = WorldGenHlpr.getFloors(world, chunk_x, chunk_z);

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
	
	public void populateOverworld(World world, IChunkProvider chunk_prov, int chunk_x, int chunk_z)
	{
		/* Chasms */
		if (WorldGenChasm.canPlaceAt(world, chunk_x, chunk_z)) {
			int block_x = chunk_x << 4;
			int block_z = chunk_z << 4;
			(new WorldGenChasm()).generate(world, rand, block_x, world.getHeightValue(block_x, block_z), block_z);
		}
		
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

	public void generateOverworld(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;

		/* Stones */
		for (int y = 30; y <= 210; y += 60) {
			if (CommonStuffConfig.andesite) {
				WorldGenHlpr.addOreSpawn(CommonBlocks.ANDESITE.blkm(), Blocks.stone, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
			}
			if (CommonStuffConfig.diorite) {
				WorldGenHlpr.addOreSpawn(CommonBlocks.DIORITE.blkm(), Blocks.stone, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
			}
			if (CommonStuffConfig.granite) {
				WorldGenHlpr.addOreSpawn(CommonBlocks.GRANITE.blkm(), Blocks.stone, world, rand, block_x, block_z, 16, 16, 16 + rand.nextInt(32), 3, y, y + 60);
			}
		}

		/* Ores */
		this.oregen_ow_silver.generate(world, rand, chunk_x, chunk_z);
		this.oregen_ow_platinum.generate(world, rand, chunk_x, chunk_z);
	}
	
	public void populateEnd(World world, IChunkProvider chunk_prov, int chunk_x, int chunk_z)
	{
		if (WorldGenConfig.end_airship_enabled) {
			this.mapgen_end_airship.func_151539_a(chunk_prov, world, chunk_x, chunk_z, null);
			this.mapgen_end_airship.generateStructuresInChunk(world, rand, chunk_x, chunk_z);
		}
	}

	public void generateEnd(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
	}

	public void populateDepthsConnectionDimension(World world, IChunkProvider chunk_prov, int chunk_x, int chunk_z)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;

		if (WorldGenChasm.canPlaceAt(world, chunk_x, chunk_z)) {
			(new WorldGenChasm()).generate(world, rand, block_x, world.getHeightValue(block_x, block_z), block_z);
		}
	}
	
	public void populateDepths(World world, IChunkProvider chunk_prov, int chunk_x, int chunk_z)
	{
	}

	public void generateDepths(Random rand, int chunk_x, int chunk_z, World world, IChunkProvider chunk_gen, IChunkProvider chunk_prov)
	{
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;

		/* Random pockets of flashover air */
		if (this.depths_gas_fracture_chk.canPlaceAt(world, chunk_x, chunk_z)) {
			int block_y = WorldGenHlpr.getCeiling(world, block_x, block_z, 192);
			WorldGenHlpr.addOreSpawn(this.depths_gas_fracture_map, world, rand, block_x, block_z, 1, 1, 256, 1, block_y, block_y + 1);
		}

		/* Ores */
		this.oregen_dp_coal.generate(world, rand, chunk_x, chunk_z);
		this.oregen_dp_iron.generate(world, rand, chunk_x, chunk_z);
		this.oregen_dp_gold.generate(world, rand, chunk_x, chunk_z);
		this.oregen_dp_diamond.generate(world, rand, chunk_x, chunk_z);
		this.oregen_dp_redstone.generate(world, rand, chunk_x, chunk_z);
		this.oregen_dp_lapis.generate(world, rand, chunk_x, chunk_z);
		this.oregen_dp_emerald.generate(world, rand, chunk_x, chunk_z);

		this.oregen_dp_silver.generate(world, rand, chunk_x, chunk_z);
		this.oregen_dp_platinum.generate(world, rand, chunk_x, chunk_z);

		if (ModBlocks.ore_copper.isOreAvailable()) {
			this.oregen_dp_copper.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_aluminium.isOreAvailable()) {
			this.oregen_dp_aluminium.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_tin.isOreAvailable()) {
			this.oregen_dp_tin.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_lead.isOreAvailable()) {
			this.oregen_dp_lead.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_nickel.isOreAvailable()) {
			this.oregen_dp_nickel.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_mythril.isOreAvailable()) {
			this.oregen_dp_mythril.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_uranium.isOreAvailable()) {
			this.oregen_dp_uranium.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_thorium.isOreAvailable()) {
			this.oregen_dp_thorium.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_tungsten.isOreAvailable()) {
			this.oregen_dp_tungsten.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_titanium.isOreAvailable()) {
			this.oregen_dp_titanium.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_zinc.isOreAvailable()) {
			this.oregen_dp_zinc.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_magnesium.isOreAvailable()) {
			this.oregen_dp_magnesium.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_boron.isOreAvailable()) {
			this.oregen_dp_boron.generate(world, rand, chunk_x, chunk_z);
		}

		if (ModBlocks.ore_sulfur.isOreAvailable()) {
			this.oregen_dp_sulfur.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_niter.isOreAvailable()) {
			this.oregen_dp_niter.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_fluorite.isOreAvailable()) {
			this.oregen_dp_fluorite.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_beryllium.isOreAvailable()) {
			this.oregen_dp_beryllium.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_rare_earth.isOreAvailable()) {
			this.oregen_dp_rare_earth.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_cobalt.isOreAvailable()) {
			this.oregen_dp_cobalt.generate(world, rand, chunk_x, chunk_z);
		}
		if (ModBlocks.ore_cinnebar.isOreAvailable()) {
			this.oregen_dp_cinnebar.generate(world, rand, chunk_x, chunk_z);
		}
	}

	@SubscribeEvent
	public void getVillageBlock(BiomeEvent.GetVillageBlockID event)
	{
		if (ModList.VILLAGE_NAMES.isLoaded()) {
			return;
		}
		if (event.biome == BiomeGenBase.taiga) {
			if (event.original == Blocks.oak_stairs) {
				event.replacement = Blocks.spruce_stairs;
				event.setResult(Result.DENY);
				return;
			}
		}
	}

	@SubscribeEvent
	public void getVillageBlockMeta(BiomeEvent.GetVillageBlockMeta event)
	{
		if (ModList.VILLAGE_NAMES.isLoaded()) {
			return;
		}
		if (event.biome == BiomeGenBase.taiga) {
			if (event.original == Blocks.planks) {
				event.replacement = 1;
				event.setResult(Result.DENY);
				return;
			}
			if (event.original == Blocks.log) {
				event.replacement = 1;
				event.setResult(Result.DENY);
				return;
			}
		}
	}

	@SubscribeEvent
	public void populateChunk(PopulateChunkEvent.Pre event)
	{
		World world = event.world;
		IChunkProvider chunk_prov = event.chunkProvider;
		int chunk_x = event.chunkX;
		int chunk_z = event.chunkZ;
		
		this.setChunkRNGSeed(world, chunk_x, chunk_z);
		
		if (world.provider.dimensionId == -1) {
			this.populateNether(world, chunk_prov, chunk_x, chunk_z);
		} else if (world.provider.dimensionId == 0) {
			this.populateOverworld(world, chunk_prov, chunk_x, chunk_z);
		} else if (world.provider.dimensionId == 1) {
			this.populateEnd(world, chunk_prov, chunk_x, chunk_z);
		} else if (world.provider.dimensionId == WorldGenConfig.depths_dimension_id) {
			this.populateDepths(world, chunk_prov, chunk_x, chunk_z);
		}
		
		if (world.provider.dimensionId == WorldGenConfig.chasm_dimension) {
			this.populateDepthsConnectionDimension(world, chunk_prov, chunk_x, chunk_z);
		}
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
	}
}
