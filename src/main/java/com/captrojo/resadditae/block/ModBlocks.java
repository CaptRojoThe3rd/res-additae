package com.captrojo.resadditae.block;

import com.captrojo.resadditae.block.devtool.BlockStructureThingy;
import com.captrojo.resadditae.block.generic.BlockBasic;
import com.captrojo.resadditae.block.generic.BlockBasicSlab;
import com.captrojo.resadditae.block.generic.BlockBasicSlabTransparent;
import com.captrojo.resadditae.block.generic.BlockBasicTransparent;
import com.captrojo.resadditae.block.generic.BlockConcretePowder;
import com.captrojo.resadditae.block.generic.BlockFallingMulti;
import com.captrojo.resadditae.block.generic.BlockMaterialPile;
import com.captrojo.resadditae.block.generic.BlockMulti;
import com.captrojo.resadditae.block.generic.BlockMultiFlower;
import com.captrojo.resadditae.block.generic.BlockMultiPillar;
import com.captrojo.resadditae.block.generic.BlockMultiSlab;
import com.captrojo.resadditae.block.generic.BlockMultiSlabTransparent;
import com.captrojo.resadditae.block.generic.BlockMultiStair;
import com.captrojo.resadditae.block.generic.BlockMultiStairTransparent;
import com.captrojo.resadditae.block.generic.BlockMultiTransparentWithDoubleSlab;
import com.captrojo.resadditae.block.generic.BlockMultiWithDoubleSlab;
import com.captrojo.resadditae.block.generic.BlockStainedGlassPane;
import com.captrojo.resadditae.block.generic.BlockStairs;
import com.captrojo.resadditae.block.ore.BlockOreBase;
import com.captrojo.resadditae.block.ore.BlockOreFromDict;
import com.captrojo.resadditae.block.ore.BlockOreFromDict.FromOredictType;
import com.captrojo.resadditae.block.special.BlockDepthSoil;
import com.captrojo.resadditae.block.special.BlockDepthsPortal;
import com.captrojo.resadditae.block.special.BlockFlashoverAir;
import com.captrojo.resadditae.block.special.BlockMossLayer;
import com.captrojo.resadditae.block.ore.BlockOreVanilla;
import com.captrojo.resadditae.block.ore.BlockPlatinumOre;
import com.captrojo.resadditae.block.ore.BlockSilverOre;
import com.captrojo.resadditae.block.ore.OreStones;
import com.captrojo.resadditae.block.utility.BlockMultiSpawner;
import com.captrojo.resadditae.block.utility.BlockSnowDungeonSpawner;
import com.captrojo.resadditae.block.utility.BlockSnowDungeonVault;
import com.captrojo.resadditae.block.utility.BlockStonecutter;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.item.Dyes;
import com.captrojo.resadditae.item.block.ItemBlockBasicSlab;
import com.captrojo.resadditae.item.block.ItemBlockMaterialPile;
import com.captrojo.resadditae.item.block.ItemBlockMulti;
import com.captrojo.resadditae.item.block.ItemBlockMultiDumb;
import com.captrojo.resadditae.item.block.ItemBlockMultiSlab;
import com.captrojo.resadditae.item.block.ItemMossLayer;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModBlocks
{
	/* Andesite, Diorite, Granite */
	public static BlockMulti bountiful_stones;
	public static BlockMultiSlab bountiful_stone_slabs;
	public static BlockMultiStair bountiful_stone_stairs_a;
	public static BlockMultiStair bountiful_stone_stairs_b;
	
	/* Hellstone, Bloodstone */
	public static BlockMulti nether_stones;
	public static BlockMultiSlab nether_stone_slabs;
	public static BlockMultiStair nether_stone_stairs;
	
	/* Depth stone variants */
	public static BlockMulti depth_stones;
	public static BlockMultiSlab depth_stone_slabs;
	public static BlockMultiStair depth_stone_stairs_a;
	public static BlockMultiStair depth_stone_stairs_b;
	public static BlockMultiStair depth_stone_stairs_c;
	
	public static BlockMulti depth_stones_special;
	public static Block depth_soil;
	
	public static Block dark_cobblestone;
	
	public static BlockMulti compacted_snow;
	public static BlockMultiSlab compacted_snow_slab;
	public static BlockMultiStair compacted_snow_stair_a;
	public static BlockMultiStair compacted_snow_stair_b;
	
	public static BlockMaterialPile material_piles_vanilla_a;
	public static BlockMaterialPile material_piles_vanilla_b;
	
	public static BlockMulti metal_blocks;
	public static BlockMulti raw_metal_blocks;
	public static BlockMaterialPile metal_piles_a;
	public static BlockMulti gem_blocks;
	public static BlockMaterialPile gem_piles_a;
	
	public static BlockOreBase ore_coal;
	public static BlockOreBase ore_iron;
	public static BlockOreBase ore_gold;
	public static BlockOreBase ore_diamond;
	public static BlockOreBase ore_redstone;
	public static BlockOreBase ore_lapis;
	public static BlockOreBase ore_emerald;
	
	public static BlockOreBase ore_silver;
	public static BlockOreBase ore_platinum;
	
	public static BlockOreFromDict ore_copper;
	public static BlockOreFromDict ore_aluminium;
	public static BlockOreFromDict ore_tin;
	public static BlockOreFromDict ore_lead;
	public static BlockOreFromDict ore_nickel;
	public static BlockOreFromDict ore_mythril;
	public static BlockOreFromDict ore_uranium;
	public static BlockOreFromDict ore_thorium;
	public static BlockOreFromDict ore_tungsten;
	public static BlockOreFromDict ore_titanium;
	public static BlockOreFromDict ore_zinc;
	public static BlockOreFromDict ore_magnesium;
	public static BlockOreFromDict ore_boron;
	
	public static BlockOreFromDict ore_sulfur;
	public static BlockOreFromDict ore_niter;
	public static BlockOreFromDict ore_fluorite;
	public static BlockOreFromDict ore_beryllium;
	public static BlockOreFromDict ore_rare_earth;
	public static BlockOreFromDict ore_cobalt;
	public static BlockOreFromDict ore_cinnebar;
	public static BlockOreFromDict ore_coltan;
	
	public static BlockOreFromDict[] oredict_ores;
	
	public static BlockMultiFlower flowers_a;
	public static BlockMultiFlower depths_plants;
	public static Block moss_layer;
	
	public static Block stonecutter;
	
	public static Block multi_spawner;
	
	public static Block snow_dungeon_spawner;
	public static Block snow_dungeon_vault;
	
	public static BlockStructureThingy structure_block;
	
	public static Block depths_portal;
	
	public static Block flashover_air;
	
	public static BlockMulti prismarine_0;
	public static BlockMulti prismarine_1;
	public static BlockMultiPillar prismarine_pillar;
	public static BlockMultiSlab prismarine_slab_0;
	public static BlockMultiSlab prismarine_slab_1;
	public static BlockMultiStair prismarine_stair_0;
	public static BlockMultiStair prismarine_stair_1;
	public static BlockMultiStair prismarine_stair_2;
	public static BlockMultiStair prismarine_stair_3;
	public static BlockMultiStair prismarine_stair_4;
	public static BlockMultiStair prismarine_stair_5;
	public static BlockMultiStair prismarine_stair_6;
	public static BlockMulti prismarine_rune_0;
	public static BlockMulti prismarine_rune_1;
	public static BlockMulti prismarine_rune_2;
	public static BlockMulti prismarine_rune_3;

	public static BlockMulti[] vanilla_wool_double_slabs;
	public static BlockMultiSlab[] vanilla_wool_slabs;
	public static BlockMultiStair[] vanilla_wool_stairs;
	public static BlockMulti[] wools;
	public static BlockMultiSlab[] wool_slabs;
	public static BlockMultiStair[] wool_stairs;
	
	public static Block glass_double_slab;
	public static Block glass_slab;
	public static Block glass_stair;
	public static BlockMulti[] vanilla_stained_glass_double_slabs;
	public static BlockMultiSlab[] vanilla_stained_glass_slabs;
	public static BlockMultiStair[] vanilla_stained_glass_stairs;
	public static BlockMulti[] stained_glass;
	public static BlockMultiSlab[] stained_glass_slabs;
	public static BlockMultiStair[] stained_glass_stairs;
	public static BlockStainedGlassPane[] stained_glass_panes;
	
	public static Block hardened_clay_double_slab;
	public static Block hardened_clay_slab;
	public static Block hardened_clay_stair;
	public static BlockMulti[] vanilla_stained_clay_double_slabs;
	public static BlockMultiSlab[] vanilla_stained_clay_slabs;
	public static BlockMultiStair[] vanilla_stained_clay_stairs;
	public static BlockMulti[] stained_clays;
	public static BlockMultiSlab[] stained_clay_slabs;
	public static BlockMultiStair[] stained_clay_stairs;
	
	public static BlockMulti[] vanilla_concrete_double_slabs;
	public static BlockMultiSlab[] vanilla_concrete_slabs;
	public static BlockMultiStair[] vanilla_concrete_stairs;
	public static BlockMulti[] concretes;
	public static BlockMultiSlab[] concrete_slabs;
	public static BlockMultiStair[] concrete_stairs;
	public static BlockFallingMulti[] concrete_powders;
	
	public static BlockMulti[] hbm_base_concrete_double_slabs;
	public static BlockMultiSlab[] hbm_base_concrete_slabs;
	public static BlockMultiStair[] hbm_base_concrete_stairs;
	public static BlockMulti[] hbm_concretes;
	public static BlockMultiSlab[] hbm_concrete_slabs;
	public static BlockMultiStair[] hbm_concrete_stairs;
	
	public static void initBlocks()
	{
		bountiful_stones = new BlockMultiWithDoubleSlab("bountiful_stones", MultiBlocks.BOUNTIFUL_STONES);
		bountiful_stone_slabs = new BlockMultiSlab("bountiful_stone_slabs", MultiBlocks.BOUNTIFUL_STONES);
		bountiful_stone_stairs_a = new BlockMultiStair("bountiful_stone_stairs_a", bountiful_stones, 0, 1, false);
		bountiful_stone_stairs_b = new BlockMultiStair("bountiful_stone_stairs_b", bountiful_stones, 2, -1, false);
		
		nether_stones = new BlockMultiWithDoubleSlab("nether_stones", MultiBlocks.NETHER_STONES);
		nether_stone_slabs = new BlockMultiSlab("nether_stone_slabs", MultiBlocks.NETHER_STONES);
		nether_stone_stairs = new BlockMultiStair("nether_stone_stairs", nether_stones, 0, 1, false);
		
		depth_stones = new BlockMultiWithDoubleSlab("depth_stones", MultiBlocks.DEPTH_STONES);
		depth_stone_slabs = new BlockMultiSlab("depth_stone_slabs", MultiBlocks.DEPTH_STONES);
		depth_stone_stairs_a = new BlockMultiStair("depth_stone_stairs_a", depth_stones, 0, 1, false);
		depth_stone_stairs_b = new BlockMultiStair("depth_stone_stairs_b", depth_stones, 2, 3, false);
		depth_stone_stairs_c = new BlockMultiStair("depth_stone_stairs_c", depth_stones, 4, 5, false);
		
		depth_stones_special = new BlockMulti("depth_stones_special", MultiBlocks.DEPTH_STONES_SPECIAL);
		depth_soil = new BlockDepthSoil();
		
		dark_cobblestone = new BlockBasic("dark_cobblestone", "dark_cobblestone", BasicBlockData.cobblestone);
		
		compacted_snow = new BlockMultiWithDoubleSlab("compacted_snow", MultiBlocks.COMPACTED_SNOW);
		compacted_snow_slab = new BlockMultiSlab("compacted_snow_slab", MultiBlocks.COMPACTED_SNOW);
		compacted_snow_stair_a = new BlockMultiStair("compacted_snow_stair_a", compacted_snow, 0, 1, false);
		compacted_snow_stair_b = new BlockMultiStair("compacted_snow_stair_b", compacted_snow, 2, 3, false);
		
		material_piles_vanilla_a = new BlockMaterialPile("material_piles_vanilla_a", MultiBlocks.MATERIAL_PILES_VANILLA_A, 0);
		material_piles_vanilla_b = new BlockMaterialPile("material_piles_vanilla_b", MultiBlocks.MATERIAL_PILES_VANILLA_B, 0);
		
		metal_blocks = new BlockMulti("metal_blocks", MultiBlocks.METAL_BLOCKS);
		raw_metal_blocks = new BlockMulti("raw_metal_blocks", MultiBlocks.RAW_METAL_BLOCKS);
		metal_piles_a = new BlockMaterialPile("metal_piles_a", MultiBlocks.METAL_PILES_A, 0);
		gem_blocks = new BlockMulti("gem_blocks", MultiBlocks.GEM_BLOCKS);
		gem_piles_a = new BlockMaterialPile("gem_piles_a", MultiBlocks.GEM_PILES_A, 0);
		
		ore_coal = new BlockOreVanilla("coal", new ItemStack(Items.coal));
		ore_iron = new BlockOreVanilla("iron", null);
		ore_gold = new BlockOreVanilla("gold", null);
		ore_diamond = new BlockOreVanilla("diamond", new ItemStack(Items.diamond));
		ore_redstone = new BlockOreVanilla("redstone", new ItemStack(Items.redstone));
		ore_lapis = new BlockOreVanilla("lapis", new ItemStack(Items.dye, 1, Dyes.M_BLUE_LAPIS));
		ore_emerald = new BlockOreVanilla("emerald", new ItemStack(Items.emerald));
		
		ore_silver = new BlockSilverOre();
		ore_platinum = new BlockPlatinumOre();
		
		ore_copper = new BlockOreFromDict("copper", "oreCopper", "ingotCopper", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_aluminium = new BlockOreFromDict("aluminium", "oreAluminum", "ingotAluminum", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_tin = new BlockOreFromDict("tin", "oreTin", "ingotTin", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_lead = new BlockOreFromDict("lead", "oreLead", "ingotLead", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_nickel = new BlockOreFromDict("nickel", "oreNickel", "ingotNickel", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_mythril = new BlockOreFromDict("mythril", "oreMythril", "ingotMythril", FromOredictType.DROP_MATERIAL, OreStones.A_VANILLA);
		ore_uranium = new BlockOreFromDict("uranium", "oreUranium", "ingotUranium", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_thorium = new BlockOreFromDict("thorium", "oreThorium", "ingotThorium", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_tungsten = new BlockOreFromDict("tungsten", "oreTungsten", "ingotTungsten", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_titanium = new BlockOreFromDict("titanium", "oreTitanium", "ingotTitanium", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_zinc = new BlockOreFromDict("zinc", "oreZinc", "ingotZinc", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_magnesium = new BlockOreFromDict("magnesium", "oreMagnesium", "ingotMagnesium", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_boron = new BlockOreFromDict("boron", "oreBoron", "ingotBoron", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		
		ore_sulfur = new BlockOreFromDict("sulfur", "oreSulfur", "dustSulfur", FromOredictType.DROP_MATERIAL, OreStones.A_VANILLA);
		ore_niter = new BlockOreFromDict("niter", "oreNiter", "dustNiter", FromOredictType.DROP_MATERIAL, OreStones.A_VANILLA);
		ore_fluorite = new BlockOreFromDict("fluorite", "oreFluorite", "dustFluorite", FromOredictType.DROP_MATERIAL, OreStones.A_VANILLA);
		ore_beryllium = new BlockOreFromDict("beryllium", "oreBeryllium", "ingotBeryllium", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_rare_earth = new BlockOreFromDict("rare_earth", "oreRareEarth", "ingotRareEarth", null, OreStones.A_VANILLA);
		ore_cobalt = new BlockOreFromDict("cobalt", "oreCobalt", "ingotCobalt", FromOredictType.SMELT_FOR_MATERIAL, OreStones.A_VANILLA);
		ore_cinnebar = new BlockOreFromDict("cinnebar", "oreCinnebar", "gemCinnebar", FromOredictType.DROP_MATERIAL, OreStones.A_VANILLA);
		ore_coltan = new BlockOreFromDict("coltan", "oreColtan", "ingotColtan", null, OreStones.A_VANILLA);
		
		oredict_ores = new BlockOreFromDict[] {
			ore_copper,
			ore_aluminium,
			ore_tin,
			ore_lead,
			ore_nickel,
			ore_mythril,
			ore_uranium,
			ore_thorium,
			ore_tungsten,
			ore_titanium,
			ore_zinc,
			ore_magnesium,
			ore_boron,
			
			ore_sulfur,
			ore_niter,
			ore_fluorite,
			ore_beryllium,
			ore_rare_earth,
			ore_cobalt,
			ore_cinnebar,
			ore_coltan
		};
		
		flowers_a = new BlockMultiFlower("flowers_a", MultiBlocks.FLOWERS_A, 0.3f);
		depths_plants = new BlockMultiFlower("depths_plant", MultiBlocks.DEPTHS_PLANTS, 0.4f);
		moss_layer = new BlockMossLayer();
		
		stonecutter = new BlockStonecutter();
		
		multi_spawner = new BlockMultiSpawner();
		
		snow_dungeon_spawner = new BlockSnowDungeonSpawner();
		snow_dungeon_vault = new BlockSnowDungeonVault();
		
		structure_block = new BlockStructureThingy();
		
		depths_portal = new BlockDepthsPortal();
		
		flashover_air = new BlockFlashoverAir();
	
		WoodTypes.initBlocks();
		StoneTypes.values();
		
		prismarine_0 = new BlockMultiWithDoubleSlab("prismarine_0", MultiBlocks.PRISMARINE_0);
		prismarine_1 = new BlockMultiWithDoubleSlab("prismarine_1", MultiBlocks.PRISMARINE_1);
		prismarine_pillar = new BlockMultiPillar("prismarine_pillar", MultiBlocks.PRISMARINE_PILLAR);
		prismarine_slab_0 = new BlockMultiSlab("prismarine_slab_0", MultiBlocks.PRISMARINE_0);
		prismarine_slab_1 = new BlockMultiSlab("prismarine_slab_1", MultiBlocks.PRISMARINE_1);
		prismarine_stair_0 = new BlockMultiStair("prismarine_stair_0", prismarine_0, 0, 1, false);
		prismarine_stair_1 = new BlockMultiStair("prismarine_stair_1", prismarine_0, 2, 3, false);
		prismarine_stair_2 = new BlockMultiStair("prismarine_stair_2", prismarine_0, 4, 5, false);
		prismarine_stair_3 = new BlockMultiStair("prismarine_stair_3", prismarine_0, 6, 7, false);
		prismarine_stair_4 = new BlockMultiStair("prismarine_stair_4", prismarine_1, 0, 1, false);
		prismarine_stair_5 = new BlockMultiStair("prismarine_stair_5", prismarine_1, 2, 3, false);
		prismarine_stair_6 = new BlockMultiStair("prismarine_stair_6", prismarine_1, 4, -1, false);
		prismarine_rune_0 = new BlockMulti("prismarine_rune_0", PrismarineRuneMultiBlockData.RUNES_0);
		prismarine_rune_1 = new BlockMulti("prismarine_rune_1", PrismarineRuneMultiBlockData.RUNES_1);
		prismarine_rune_2 = new BlockMulti("prismarine_rune_2", PrismarineRuneMultiBlockData.RUNES_2);
		prismarine_rune_3 = new BlockMulti("prismarine_rune_3", PrismarineRuneMultiBlockData.RUNES_3);
	
		vanilla_wool_double_slabs = new BlockMulti[2];
		vanilla_wool_double_slabs[0] = new BlockMultiWithDoubleSlab("vanilla_wool_double_slab_0", MultiBlocks.VANILLA_WOOL_0);
		vanilla_wool_double_slabs[1] = new BlockMultiWithDoubleSlab("vanilla_wool_double_slab_1", MultiBlocks.VANILLA_WOOL_1);
		vanilla_wool_slabs = new BlockMultiSlab[2];
		vanilla_wool_slabs[0] = new BlockMultiSlab("vanilla_wool_slab_0", MultiBlocks.VANILLA_WOOL_0);
		vanilla_wool_slabs[1] = new BlockMultiSlab("vanilla_wool_slab_1", MultiBlocks.VANILLA_WOOL_1);
		vanilla_wool_stairs = new BlockMultiStair[8];
		vanilla_wool_stairs[0] = new BlockMultiStair("vanilla_wool_stair_0", Blocks.wool, MultiBlocks.VANILLA_WOOL_0, 0, 1, false);
		vanilla_wool_stairs[1] = new BlockMultiStair("vanilla_wool_stair_1", Blocks.wool, MultiBlocks.VANILLA_WOOL_0, 2, 3, false);
		vanilla_wool_stairs[2] = new BlockMultiStair("vanilla_wool_stair_2", Blocks.wool, MultiBlocks.VANILLA_WOOL_0, 4, 5, false);
		vanilla_wool_stairs[3] = new BlockMultiStair("vanilla_wool_stair_3", Blocks.wool, MultiBlocks.VANILLA_WOOL_0, 6, 7, false);
		vanilla_wool_stairs[4] = new BlockMultiStair("vanilla_wool_stair_4", Blocks.wool, MultiBlocks.VANILLA_WOOL_1, 8, 9, false);
		vanilla_wool_stairs[5] = new BlockMultiStair("vanilla_wool_stair_5", Blocks.wool, MultiBlocks.VANILLA_WOOL_1, 10, 11, false);
		vanilla_wool_stairs[6] = new BlockMultiStair("vanilla_wool_stair_6", Blocks.wool, MultiBlocks.VANILLA_WOOL_1, 12, 13, false);
		vanilla_wool_stairs[7] = new BlockMultiStair("vanilla_wool_stair_7", Blocks.wool, MultiBlocks.VANILLA_WOOL_1, 14, 15, false);
		
		wools = new BlockMulti[9];
		wool_slabs = new BlockMultiSlab[9];
		wool_stairs = new BlockMultiStair[36];
		for (int i = 0; i < 9; i++) {
			ColoredBlocks data = ColoredBlocks.values()[ColoredBlocks.WOOL_0.ordinal() + i];
			wools[i] = new BlockMultiWithDoubleSlab("wool_" + i, data);
			wool_slabs[i] = new BlockMultiSlab("wool_slab_" + i, data);
			wool_stairs[i * 4] = new BlockMultiStair("wool_stair_" + (i * 4), wools[i], 0, 1, false);
			wool_stairs[i * 4 + 1] = new BlockMultiStair("wool_stair_" + (i * 4 + 1), wools[i], 2, 3, false);
			wool_stairs[i * 4 + 2] = new BlockMultiStair("wool_stair_" + (i * 4 + 2), wools[i], 4, 5, false);
			wool_stairs[i * 4 + 3] = new BlockMultiStair("wool_stair_" + (i * 4 + 3), wools[i], 6, 7, false);
		}
		
		BasicBlockData glass_data = new BasicBlockData(Material.glass, Block.soundTypeGlass, 0.3f, 0.3f, null, 0);
		glass_double_slab = new BlockBasicTransparent("glass_double_slab", "minecraft:glass", glass_data);
		glass_slab = new BlockBasicSlabTransparent("glass_slab", "minecraft:glass", glass_data);
		glass_stair = new BlockStairs("glass_stair", Blocks.glass, 0);
		
		vanilla_stained_glass_double_slabs = new BlockMulti[2];
		vanilla_stained_glass_double_slabs[0] = new BlockMultiTransparentWithDoubleSlab("vanilla_stained_glass_double_slab_0", MultiBlocks.VANILLA_STAINED_GLASS_0);
		vanilla_stained_glass_double_slabs[1] = new BlockMultiTransparentWithDoubleSlab("vanilla_stained_glass_double_slab_1", MultiBlocks.VANILLA_STAINED_GLASS_1);
		vanilla_stained_glass_slabs = new BlockMultiSlab[2];
		vanilla_stained_glass_slabs[0] = new BlockMultiSlabTransparent("vanilla_stained_glass_slab_0", MultiBlocks.VANILLA_STAINED_GLASS_0);
		vanilla_stained_glass_slabs[1] = new BlockMultiSlabTransparent("vanilla_stained_glass_slab_1", MultiBlocks.VANILLA_STAINED_GLASS_1);
		vanilla_stained_glass_stairs = new BlockMultiStair[8];
		vanilla_stained_glass_stairs[0] = new BlockMultiStairTransparent("vanilla_stained_glass_stair_0", Blocks.stained_glass, MultiBlocks.VANILLA_STAINED_GLASS_0, 0, 1, false);
		vanilla_stained_glass_stairs[1] = new BlockMultiStairTransparent("vanilla_stained_glass_stair_1", Blocks.stained_glass, MultiBlocks.VANILLA_STAINED_GLASS_0, 2, 3, false);
		vanilla_stained_glass_stairs[2] = new BlockMultiStairTransparent("vanilla_stained_glass_stair_2", Blocks.stained_glass, MultiBlocks.VANILLA_STAINED_GLASS_0, 4, 5, false);
		vanilla_stained_glass_stairs[3] = new BlockMultiStairTransparent("vanilla_stained_glass_stair_3", Blocks.stained_glass, MultiBlocks.VANILLA_STAINED_GLASS_0, 6, 7, false);
		vanilla_stained_glass_stairs[4] = new BlockMultiStairTransparent("vanilla_stained_glass_stair_4", Blocks.stained_glass, MultiBlocks.VANILLA_STAINED_GLASS_1, 8, 9, false);
		vanilla_stained_glass_stairs[5] = new BlockMultiStairTransparent("vanilla_stained_glass_stair_5", Blocks.stained_glass, MultiBlocks.VANILLA_STAINED_GLASS_1, 10, 11, false);
		vanilla_stained_glass_stairs[6] = new BlockMultiStairTransparent("vanilla_stained_glass_stair_6", Blocks.stained_glass, MultiBlocks.VANILLA_STAINED_GLASS_1, 12, 13, false);
		vanilla_stained_glass_stairs[7] = new BlockMultiStairTransparent("vanilla_stained_glass_stair_7", Blocks.stained_glass, MultiBlocks.VANILLA_STAINED_GLASS_1, 14, 15, false);
		
		stained_glass = new BlockMulti[9];
		stained_glass_slabs = new BlockMultiSlab[9];
		stained_glass_stairs = new BlockMultiStair[36];
		for (int i = 0; i < 9; i++) {
			ColoredBlocks data = ColoredBlocks.values()[ColoredBlocks.STAINED_GLASS_0.ordinal() + i];
			stained_glass[i] = new BlockMultiTransparentWithDoubleSlab("stained_glass_" + i, data);
			stained_glass_slabs[i] = new BlockMultiSlabTransparent("stained_glass_slab_" + i, data);
			stained_glass_stairs[i * 4] = new BlockMultiStairTransparent("stained_glass_stair_" + (i * 4), stained_glass[i], 0, 1, false);
			stained_glass_stairs[i * 4 + 1] = new BlockMultiStairTransparent("stained_glass_stair_" + (i * 4 + 1), stained_glass[i], 2, 3, false);
			stained_glass_stairs[i * 4 + 2] = new BlockMultiStairTransparent("stained_glass_stair_" + (i * 4 + 2), stained_glass[i], 4, 5, false);
			stained_glass_stairs[i * 4 + 3] = new BlockMultiStairTransparent("stained_glass_stair_" + (i * 4 + 3), stained_glass[i], 6, 7, false);
		}
		stained_glass_panes = new BlockStainedGlassPane[5];
		stained_glass_panes[0] = new BlockStainedGlassPane("stained_glass_pane_0", stained_glass[0], stained_glass[1]);
		stained_glass_panes[1] = new BlockStainedGlassPane("stained_glass_pane_1", stained_glass[2], stained_glass[3]);
		stained_glass_panes[2] = new BlockStainedGlassPane("stained_glass_pane_2", stained_glass[4], stained_glass[5]);
		stained_glass_panes[3] = new BlockStainedGlassPane("stained_glass_pane_3", stained_glass[6], stained_glass[7]);
		stained_glass_panes[4] = new BlockStainedGlassPane("stained_glass_pane_4", stained_glass[8], null);
		
		BasicBlockData hardened_clay_data = new BasicBlockData(Material.rock, Block.soundTypeStone, 1.25f, 4.2f, "pickaxe", 0);
		hardened_clay_double_slab = new BlockBasic("hardened_clay_double_slab", "minecraft:hardened_clay", hardened_clay_data);
		hardened_clay_slab = new BlockBasicSlab("hardened_clay_slab", "minecraft:hardened_clay", hardened_clay_data);
		hardened_clay_stair = new BlockStairs("hardened_clay_stair", Blocks.hardened_clay, 0);
		
		vanilla_stained_clay_double_slabs = new BlockMulti[2];
		vanilla_stained_clay_double_slabs[0] = new BlockMultiWithDoubleSlab("vanilla_stained_clay_double_slab_0", MultiBlocks.VANILLA_STAINED_CLAY_0);
		vanilla_stained_clay_double_slabs[1] = new BlockMultiWithDoubleSlab("vanilla_stained_clay_double_slab_1", MultiBlocks.VANILLA_STAINED_CLAY_1);
		vanilla_stained_clay_slabs = new BlockMultiSlab[2];
		vanilla_stained_clay_slabs[0] = new BlockMultiSlab("vanilla_stained_clay_slab_0", MultiBlocks.VANILLA_STAINED_CLAY_0);
		vanilla_stained_clay_slabs[1] = new BlockMultiSlab("vanilla_stained_clay_slab_1", MultiBlocks.VANILLA_STAINED_CLAY_1);
		vanilla_stained_clay_stairs = new BlockMultiStair[8];
		vanilla_stained_clay_stairs[0] = new BlockMultiStair("vanilla_stained_clay_stair_0", Blocks.stained_hardened_clay, MultiBlocks.VANILLA_STAINED_CLAY_0, 0, 1, false);
		vanilla_stained_clay_stairs[1] = new BlockMultiStair("vanilla_stained_clay_stair_1", Blocks.stained_hardened_clay, MultiBlocks.VANILLA_STAINED_CLAY_0, 2, 3, false);
		vanilla_stained_clay_stairs[2] = new BlockMultiStair("vanilla_stained_clay_stair_2", Blocks.stained_hardened_clay, MultiBlocks.VANILLA_STAINED_CLAY_0, 4, 5, false);
		vanilla_stained_clay_stairs[3] = new BlockMultiStair("vanilla_stained_clay_stair_3", Blocks.stained_hardened_clay, MultiBlocks.VANILLA_STAINED_CLAY_0, 6, 7, false);
		vanilla_stained_clay_stairs[4] = new BlockMultiStair("vanilla_stained_clay_stair_4", Blocks.stained_hardened_clay, MultiBlocks.VANILLA_STAINED_CLAY_1, 8, 9, false);
		vanilla_stained_clay_stairs[5] = new BlockMultiStair("vanilla_stained_clay_stair_5", Blocks.stained_hardened_clay, MultiBlocks.VANILLA_STAINED_CLAY_1, 10, 11, false);
		vanilla_stained_clay_stairs[6] = new BlockMultiStair("vanilla_stained_clay_stair_6", Blocks.stained_hardened_clay, MultiBlocks.VANILLA_STAINED_CLAY_1, 12, 13, false);
		vanilla_stained_clay_stairs[7] = new BlockMultiStair("vanilla_stained_clay_stair_7", Blocks.stained_hardened_clay, MultiBlocks.VANILLA_STAINED_CLAY_1, 14, 15, false);
		
		stained_clays = new BlockMulti[9];
		stained_clay_slabs = new BlockMultiSlab[9];
		stained_clay_stairs = new BlockMultiStair[36];
		for (int i = 0; i < 9; i++) {
			ColoredBlocks data = ColoredBlocks.values()[ColoredBlocks.STAINED_CLAY_0.ordinal() + i];
			stained_clays[i] = new BlockMultiWithDoubleSlab("stained_clay_" + i, data);
			stained_clay_slabs[i] = new BlockMultiSlab("stained_clay_slab_" + i, data);
			stained_clay_stairs[i * 4] = new BlockMultiStair("stained_clay_stair_" + (i * 4), stained_clays[i], 0, 1, false);
			stained_clay_stairs[i * 4 + 1] = new BlockMultiStair("stained_clay_stair_" + (i * 4 + 1), stained_clays[i], 2, 3, false);
			stained_clay_stairs[i * 4 + 2] = new BlockMultiStair("stained_clay_stair_" + (i * 4 + 2), stained_clays[i], 4, 5, false);
			stained_clay_stairs[i * 4 + 3] = new BlockMultiStair("stained_clay_stair_" + (i * 4 + 3), stained_clays[i], 6, 7, false);
		}
		
		if (CommonConfig.General.vanilla_concrete_ext) {
			vanilla_concrete_double_slabs = new BlockMulti[2];
			vanilla_concrete_double_slabs[0] = new BlockMultiWithDoubleSlab("vanilla_concrete_double_slab_0", MultiBlocks.VANILLA_CONCRETE_0);
			vanilla_concrete_double_slabs[1] = new BlockMultiWithDoubleSlab("vanilla_concrete_double_slab_1", MultiBlocks.VANILLA_CONCRETE_1);
			vanilla_concrete_slabs = new BlockMultiSlab[2];
			vanilla_concrete_slabs[0] = new BlockMultiSlab("vanilla_concrete_slab_0", MultiBlocks.VANILLA_CONCRETE_0);
			vanilla_concrete_slabs[1] = new BlockMultiSlab("vanilla_concrete_slab_1", MultiBlocks.VANILLA_CONCRETE_1);
			vanilla_concrete_stairs = new BlockMultiStair[8];
			vanilla_concrete_stairs[0] = new BlockMultiStair("vanilla_concrete_stair_0", vanilla_concrete_double_slabs[0], 0, 1, false);
			vanilla_concrete_stairs[1] = new BlockMultiStair("vanilla_concrete_stair_1", vanilla_concrete_double_slabs[0], 2, 3, false);
			vanilla_concrete_stairs[2] = new BlockMultiStair("vanilla_concrete_stair_2", vanilla_concrete_double_slabs[0], 4, 5, false);
			vanilla_concrete_stairs[3] = new BlockMultiStair("vanilla_concrete_stair_3", vanilla_concrete_double_slabs[0], 6, 7, false);
			vanilla_concrete_stairs[4] = new BlockMultiStair("vanilla_concrete_stair_4", vanilla_concrete_double_slabs[1], 0, 1, false);
			vanilla_concrete_stairs[5] = new BlockMultiStair("vanilla_concrete_stair_5", vanilla_concrete_double_slabs[1], 2, 3, false);
			vanilla_concrete_stairs[6] = new BlockMultiStair("vanilla_concrete_stair_6", vanilla_concrete_double_slabs[1], 4, 5, false);
			vanilla_concrete_stairs[7] = new BlockMultiStair("vanilla_concrete_stair_7", vanilla_concrete_double_slabs[1], 6, 7, false);
			
			concretes = new BlockMulti[9];
			concrete_slabs = new BlockMultiSlab[9];
			concrete_stairs = new BlockMultiStair[36];
			for (int i = 0; i < 9; i++) {
				ColoredBlocks data = ColoredBlocks.values()[ColoredBlocks.CONCRETE_0.ordinal() + i];
				concretes[i] = new BlockMultiWithDoubleSlab("concrete_" + i, data);
				concrete_slabs[i] = new BlockMultiSlab("concrete_slab_" + i, data);
				concrete_stairs[i * 4] = new BlockMultiStair("concrete_stair_" + (i * 4), concretes[i], 0, 1, false);
				concrete_stairs[i * 4 + 1] = new BlockMultiStair("concrete_stair_" + (i * 4 + 1), concretes[i], 2, 3, false);
				concrete_stairs[i * 4 + 2] = new BlockMultiStair("concrete_stair_" + (i * 4 + 2), concretes[i], 4, 5, false);
				concrete_stairs[i * 4 + 3] = new BlockMultiStair("concrete_stair_" + (i * 4 + 3), concretes[i], 6, 7, false);
			}
			concrete_powders = new BlockFallingMulti[5];
			for (int i = 0, j = 0; i < 5; i++, j += 2) {
				ColoredBlocks data = ColoredBlocks.values()[ColoredBlocks.CONCRETE_POWDER_0.ordinal() + i];
				concrete_powders[i] = new BlockConcretePowder("concrete_powder_" + i, data, concretes[j], i == 4 ? null : concretes[j + 1]);
			}
		}
		
		if (CommonConfig.General.hbm_concrete_ext) {
			hbm_base_concrete_double_slabs = new BlockMulti[3];
			hbm_base_concrete_double_slabs[0] = new BlockMultiWithDoubleSlab("hbm_base_concrete_double_slab_0", MultiBlocks.HBM_BASE_CONCRETE_0);
			hbm_base_concrete_double_slabs[1] = new BlockMultiWithDoubleSlab("hbm_base_concrete_double_slab_1", MultiBlocks.HBM_BASE_CONCRETE_1);
			hbm_base_concrete_double_slabs[2] = new BlockMultiWithDoubleSlab("hbm_base_concrete_double_slab_2", MultiBlocks.HBM_BASE_CONCRETE_2);
			hbm_base_concrete_slabs = new BlockMultiSlab[3];
			hbm_base_concrete_slabs[0] = new BlockMultiSlab("hbm_base_concrete_slab_0", MultiBlocks.HBM_BASE_CONCRETE_0);
			hbm_base_concrete_slabs[1] = new BlockMultiSlab("hbm_base_concrete_slab_1", MultiBlocks.HBM_BASE_CONCRETE_1);
			hbm_base_concrete_slabs[2] = new BlockMultiSlab("hbm_base_concrete_slab_2", MultiBlocks.HBM_BASE_CONCRETE_2);
			hbm_base_concrete_stairs = new BlockMultiStair[12];
			hbm_base_concrete_stairs[0] = new BlockMultiStair("hbm_base_concrete_stair_0", hbm_base_concrete_double_slabs[0], 0, 1, false);
			hbm_base_concrete_stairs[1] = new BlockMultiStair("hbm_base_concrete_stair_1", hbm_base_concrete_double_slabs[0], 2, 3, false);
			hbm_base_concrete_stairs[2] = new BlockMultiStair("hbm_base_concrete_stair_2", hbm_base_concrete_double_slabs[0], 4, 5, false);
			hbm_base_concrete_stairs[3] = new BlockMultiStair("hbm_base_concrete_stair_3", hbm_base_concrete_double_slabs[0], 6, 7, false);
			hbm_base_concrete_stairs[4] = new BlockMultiStair("hbm_base_concrete_stair_4", hbm_base_concrete_double_slabs[1], 0, 1, false);
			hbm_base_concrete_stairs[5] = new BlockMultiStair("hbm_base_concrete_stair_5", hbm_base_concrete_double_slabs[1], 2, 3, false);
			hbm_base_concrete_stairs[6] = new BlockMultiStair("hbm_base_concrete_stair_6", hbm_base_concrete_double_slabs[1], 4, 5, false);
			hbm_base_concrete_stairs[7] = new BlockMultiStair("hbm_base_concrete_stair_7", hbm_base_concrete_double_slabs[1], 6, 7, false);
			hbm_base_concrete_stairs[8] = new BlockMultiStair("hbm_base_concrete_stair_8", hbm_base_concrete_double_slabs[2], 0, 1, false);
			hbm_base_concrete_stairs[9] = new BlockMultiStair("hbm_base_concrete_stair_9", hbm_base_concrete_double_slabs[2], 2, 3, false);
			hbm_base_concrete_stairs[10] = new BlockMultiStair("hbm_base_concrete_stair_10", hbm_base_concrete_double_slabs[2], 4, 5, false);
			hbm_base_concrete_stairs[11] = new BlockMultiStair("hbm_base_concrete_stair_11", hbm_base_concrete_double_slabs[2], 6, 7, false);
			
			hbm_concretes = new BlockMulti[9];
			hbm_concrete_slabs = new BlockMultiSlab[9];
			hbm_concrete_stairs = new BlockMultiStair[36];
			for (int i = 0; i < 9; i++) {
				ColoredBlocks data = ColoredBlocks.values()[ColoredBlocks.CONCRETE_0.ordinal() + i];
				hbm_concretes[i] = new BlockMultiWithDoubleSlab("hbm_concrete_" + i, data);
				hbm_concrete_slabs[i] = new BlockMultiSlab("hbm_concrete_slab_" + i, data);
				hbm_concrete_stairs[i * 4] = new BlockMultiStair("hbm_concrete_stair_" + (i * 4), hbm_concretes[i], 0, 1, false);
				hbm_concrete_stairs[i * 4 + 1] = new BlockMultiStair("hbm_concrete_stair_" + (i * 4 + 1), hbm_concretes[i], 2, 3, false);
				hbm_concrete_stairs[i * 4 + 2] = new BlockMultiStair("hbm_concrete_stair_" + (i * 4 + 2), hbm_concretes[i], 4, 5, false);
				hbm_concrete_stairs[i * 4 + 3] = new BlockMultiStair("hbm_concrete_stair_" + (i * 4 + 3), hbm_concretes[i], 6, 7, false);
			}
		}
	}
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(bountiful_stones, ItemBlockMulti.class, bountiful_stones.getUnlocalizedName());
		GameRegistry.registerBlock(bountiful_stone_slabs, ItemBlockMultiSlab.class, bountiful_stone_slabs.getUnlocalizedName());
		GameRegistry.registerBlock(bountiful_stone_stairs_a, ItemBlockMulti.class, bountiful_stone_stairs_a.getUnlocalizedName());
		GameRegistry.registerBlock(bountiful_stone_stairs_b, ItemBlockMulti.class, bountiful_stone_stairs_b.getUnlocalizedName());
		
		GameRegistry.registerBlock(nether_stones, ItemBlockMulti.class, nether_stones.getUnlocalizedName());
		GameRegistry.registerBlock(nether_stone_slabs, ItemBlockMultiSlab.class, nether_stone_slabs.getUnlocalizedName());
		GameRegistry.registerBlock(nether_stone_stairs, ItemBlockMulti.class, nether_stone_stairs.getUnlocalizedName());
		
		GameRegistry.registerBlock(depth_stones, ItemBlockMulti.class, depth_stones.getUnlocalizedName());
		GameRegistry.registerBlock(depth_stone_slabs, ItemBlockMultiSlab.class, depth_stone_slabs.getUnlocalizedName());
		GameRegistry.registerBlock(depth_stone_stairs_a, ItemBlockMulti.class, depth_stone_stairs_a.getUnlocalizedName());
		GameRegistry.registerBlock(depth_stone_stairs_b, ItemBlockMulti.class, depth_stone_stairs_b.getUnlocalizedName());
		GameRegistry.registerBlock(depth_stone_stairs_c, ItemBlockMulti.class, depth_stone_stairs_c.getUnlocalizedName());
		
		GameRegistry.registerBlock(depth_stones_special, ItemBlockMulti.class, depth_stones_special.getUnlocalizedName());
		GameRegistry.registerBlock(depth_soil, depth_soil.getUnlocalizedName());
		
		GameRegistry.registerBlock(dark_cobblestone, dark_cobblestone.getUnlocalizedName());
		
		GameRegistry.registerBlock(compacted_snow, ItemBlockMulti.class, compacted_snow.getUnlocalizedName());
		GameRegistry.registerBlock(compacted_snow_slab, ItemBlockMultiSlab.class, compacted_snow_slab.getUnlocalizedName());
		GameRegistry.registerBlock(compacted_snow_stair_a, ItemBlockMulti.class, compacted_snow_stair_a.getUnlocalizedName());
		GameRegistry.registerBlock(compacted_snow_stair_b, ItemBlockMulti.class, compacted_snow_stair_b.getUnlocalizedName());
		
		GameRegistry.registerBlock(material_piles_vanilla_a, ItemBlockMaterialPile.class, material_piles_vanilla_a.getUnlocalizedName());
		GameRegistry.registerBlock(material_piles_vanilla_b, ItemBlockMaterialPile.class, material_piles_vanilla_b.getUnlocalizedName());
		
		GameRegistry.registerBlock(metal_blocks, ItemBlockMulti.class, metal_blocks.getUnlocalizedName());
		GameRegistry.registerBlock(raw_metal_blocks, ItemBlockMulti.class, raw_metal_blocks.getUnlocalizedName());
		GameRegistry.registerBlock(metal_piles_a, ItemBlockMaterialPile.class, metal_piles_a.getUnlocalizedName());
		GameRegistry.registerBlock(gem_blocks, ItemBlockMulti.class, gem_blocks.getUnlocalizedName());
		GameRegistry.registerBlock(gem_piles_a, ItemBlockMaterialPile.class, gem_piles_a.getUnlocalizedName());
		
		GameRegistry.registerBlock(ore_coal, ItemBlockMultiDumb.class, ore_coal.getUnlocalizedName());
		GameRegistry.registerBlock(ore_iron, ItemBlockMultiDumb.class, ore_iron.getUnlocalizedName());
		GameRegistry.registerBlock(ore_gold, ItemBlockMultiDumb.class, ore_gold.getUnlocalizedName());
		GameRegistry.registerBlock(ore_diamond, ItemBlockMultiDumb.class, ore_diamond.getUnlocalizedName());
		GameRegistry.registerBlock(ore_redstone, ItemBlockMultiDumb.class, ore_redstone.getUnlocalizedName());
		GameRegistry.registerBlock(ore_lapis, ItemBlockMultiDumb.class, ore_lapis.getUnlocalizedName());
		GameRegistry.registerBlock(ore_emerald, ItemBlockMultiDumb.class, ore_emerald.getUnlocalizedName());
		
		GameRegistry.registerBlock(ore_silver, ItemBlockMultiDumb.class, ore_silver.getUnlocalizedName());
		GameRegistry.registerBlock(ore_platinum, ItemBlockMultiDumb.class, ore_platinum.getUnlocalizedName());
		
		for (BlockOreFromDict block : oredict_ores) {
			GameRegistry.registerBlock(block, ItemBlockMultiDumb.class, block.getUnlocalizedName());
		}
		
		GameRegistry.registerBlock(flowers_a, ItemBlockMulti.class, flowers_a.getUnlocalizedName());
		GameRegistry.registerBlock(depths_plants, ItemBlockMulti.class, depths_plants.getUnlocalizedName());
		GameRegistry.registerBlock(moss_layer, ItemMossLayer.class, moss_layer.getUnlocalizedName());
		
		GameRegistry.registerBlock(stonecutter, stonecutter.getUnlocalizedName());
		
		GameRegistry.registerBlock(multi_spawner, ItemBlockMultiDumb.class, multi_spawner.getUnlocalizedName());
		
		GameRegistry.registerBlock(snow_dungeon_spawner, ItemBlockMultiDumb.class, snow_dungeon_spawner.getUnlocalizedName());
		GameRegistry.registerBlock(snow_dungeon_vault, ItemBlockMultiDumb.class, snow_dungeon_vault.getUnlocalizedName());
		
		GameRegistry.registerBlock(structure_block, ItemBlockMultiDumb.class, structure_block.getUnlocalizedName());
		
		GameRegistry.registerBlock(depths_portal, depths_portal.getUnlocalizedName());
		
		GameRegistry.registerBlock(flashover_air, flashover_air.getUnlocalizedName());
		
		WoodTypes.registerBlocks();
		StoneTypes.registerBlocks();
	
		GameRegistry.registerBlock(prismarine_0, ItemBlockMulti.class, prismarine_0.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_1, ItemBlockMulti.class, prismarine_1.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_pillar, ItemBlockMulti.class, prismarine_pillar.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_slab_0, ItemBlockMultiSlab.class, prismarine_slab_0.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_slab_1, ItemBlockMultiSlab.class, prismarine_slab_1.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_stair_0, ItemBlockMulti.class, prismarine_stair_0.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_stair_1, ItemBlockMulti.class, prismarine_stair_1.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_stair_2, ItemBlockMulti.class, prismarine_stair_2.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_stair_3, ItemBlockMulti.class, prismarine_stair_3.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_stair_4, ItemBlockMulti.class, prismarine_stair_4.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_stair_5, ItemBlockMulti.class, prismarine_stair_5.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_stair_6, ItemBlockMulti.class, prismarine_stair_6.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_rune_0, ItemBlockMulti.class, prismarine_rune_0.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_rune_1, ItemBlockMulti.class, prismarine_rune_1.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_rune_2, ItemBlockMulti.class, prismarine_rune_2.getUnlocalizedName());
		GameRegistry.registerBlock(prismarine_rune_3, ItemBlockMulti.class, prismarine_rune_3.getUnlocalizedName());
		
		for (BlockMulti block : vanilla_wool_double_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSlab block : vanilla_wool_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
		}
		for (BlockMultiStair block : vanilla_wool_stairs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		
		for (BlockMulti block : wools) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSlab block : wool_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
		}
		for (BlockMultiStair block : wool_stairs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		
		GameRegistry.registerBlock(glass_double_slab, glass_double_slab.getUnlocalizedName());
		GameRegistry.registerBlock(glass_slab, ItemBlockBasicSlab.class, glass_slab.getUnlocalizedName());
		GameRegistry.registerBlock(glass_stair, glass_stair.getUnlocalizedName());
		for (BlockMulti block : vanilla_stained_glass_double_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSlab block : vanilla_stained_glass_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
		}
		for (BlockMultiStair block : vanilla_stained_glass_stairs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		
		for (BlockMulti block : stained_glass) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSlab block : stained_glass_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
		}
		for (BlockMultiStair block : stained_glass_stairs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockStainedGlassPane block : stained_glass_panes) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		
		GameRegistry.registerBlock(hardened_clay_double_slab, hardened_clay_double_slab.getUnlocalizedName());
		GameRegistry.registerBlock(hardened_clay_slab, ItemBlockBasicSlab.class, hardened_clay_slab.getUnlocalizedName());
		GameRegistry.registerBlock(hardened_clay_stair, hardened_clay_stair.getUnlocalizedName());
		for (BlockMulti block : vanilla_stained_clay_double_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSlab block : vanilla_stained_clay_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
		}
		for (BlockMultiStair block : vanilla_stained_clay_stairs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		
		for (BlockMulti block : stained_clays) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSlab block : stained_clay_slabs) {
			GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
		}
		for (BlockMultiStair block : stained_clay_stairs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		
		if (CommonConfig.General.vanilla_concrete_ext) {
			for (BlockMulti block : vanilla_concrete_double_slabs) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
			for (BlockMultiSlab block : vanilla_concrete_slabs) {
				GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
			}
			for (BlockMultiStair block : vanilla_concrete_stairs) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
			
			for (BlockMulti block : concretes) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
			for (BlockMultiSlab block : concrete_slabs) {
				GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
			}
			for (BlockMultiStair block : concrete_stairs) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
			for (BlockFallingMulti block : concrete_powders) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
		}
		
		if (CommonConfig.General.hbm_concrete_ext) {
			for (BlockMulti block : hbm_base_concrete_double_slabs) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
			for (BlockMultiSlab block : hbm_base_concrete_slabs) {
				GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
			}
			for (BlockMultiStair block : hbm_base_concrete_stairs) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
			
			for (BlockMulti block : hbm_concretes) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
			for (BlockMultiSlab block : hbm_concrete_slabs) {
				GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
			}
			for (BlockMultiStair block : hbm_concrete_stairs) {
				GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
			}
		}
	}
	
	public static void doPostRegistrationSetup()
	{
		for (BlockOreFromDict block : oredict_ores) {
			block.initAndCheckAvailability();
		}
	}
}
