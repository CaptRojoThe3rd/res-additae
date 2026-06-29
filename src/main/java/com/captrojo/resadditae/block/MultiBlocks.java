package com.captrojo.resadditae.block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import com.captrojo.resadditae.material.BlockMaterials;
import com.captrojo.resadditae.render.block.BlockTexture;
import com.captrojo.resadditae.render.block.BlockTexture.Type;
import com.captrojo.resadditae.sounds.ModSoundType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum MultiBlocks implements IMultiBlockData
{
	BOUNTIFUL_STONES(
		3, Material.rock, Block.soundTypeStone, true, 1.5f, 6.0f, true, "pickaxe", 0, true, 0, 0,
		0, "andesite", new BlockTexture(Type.STANDARD, "minecraft:andesite"),
		1, "diorite", new BlockTexture(Type.STANDARD, "minecraft:diorite"),
		2, "granite", new BlockTexture(Type.STANDARD, "minecraft:granite")
	),
	
	NETHER_STONES(
		2, Material.rock, Block.soundTypeStone, true, 1.5f, 6.0f, true, "pickaxe", 0, true, 0, 0,
		0, "hellstone", new BlockTexture(Type.STANDARD, "hellstone/plain"),
		1, "bloodstone", new BlockTexture(Type.STANDARD, "bloodstone/plain")
	),
	
	DEPTH_STONES(
		6, Material.rock, Block.soundTypeStone, true, 3.0f, 6.0f, true, "pickaxe", 0, true, 0, 0,
		0, "regular", new BlockTexture(Type.STANDARD, "depths/stone"),
		1, "amber", new BlockTexture(Type.STANDARD, "depths/stone_amber"),
		2, "jade", new BlockTexture(Type.STANDARD, "depths/stone_jade"),
		3, "ruby", new BlockTexture(Type.STANDARD, "depths/stone_ruby"),
		4, "sapphire", new BlockTexture(Type.STANDARD, "depths/stone_sapphire"),
		5, "topaz", new BlockTexture(Type.STANDARD, "depths/stone_topaz")
	),
	
	DEPTH_STONES_SPECIAL(
		3, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "dark", new BlockTexture(Type.STANDARD, "depths/dark_stone"), -1.0f, 6000000.0f, null, 0,
		1, "fractured", new BlockTexture(Type.STANDARD, "depths/fractured_stone"), 2.0f, 2.0f, "pickaxe", 0,
		2, "stained_fractured", new BlockTexture(Type.STANDARD, "depths/stained_fractured_stone"), 2.0f, 2.0f, "pickaxe", 0
	),
	
	COMPACTED_SNOW(
		4, Material.craftedSnow, Block.soundTypeSnow, false, false, true, 0, 0,
		0, "plain", new BlockTexture(Type.STANDARD, "compacted_snow"), 0.35f, 0.35f, "shovel", 0,
		1, "bricks", new BlockTexture(Type.STANDARD, "compacted_snow_bricks"), 0.35f, 0.35f, "shovel", 0,
		2, "tiles", new BlockTexture(Type.STANDARD, "compacted_snow_tiles"), 0.35f, 0.35f, "shovel", 0,
		3, "hard", new BlockTexture(Type.STANDARD, "hardened_snow"), 0.7f, 0.7f, "shovel", 0
	),
	
	FLOWERS_A(
		9, Material.plants, Block.soundTypeGrass, true, 0.0f, 0.0f, true, null, 0, true, 0, 0,
		0, "aster", new BlockTexture(Type.STANDARD, "flower/aster"),
		1, "black_eyed_susan", new BlockTexture(Type.STANDARD, "flower/black_eyed_susan"),
		2, "calendula", new BlockTexture(Type.STANDARD, "flower/calendula"),
		3, "dahlia", new BlockTexture(Type.STANDARD, "flower/dahlia"),
		4, "forgetmenot", new BlockTexture(Type.STANDARD, "flower/forgetmenot"),
		5, "hyacinth", new BlockTexture(Type.STANDARD, "flower/hyacinth"),
		6, "iris", new BlockTexture(Type.STANDARD, "flower/iris"),
		7, "mimosa", new BlockTexture(Type.STANDARD, "flower/mimosa"),
		8, "yellow_daffodil", new BlockTexture(Type.STANDARD, "flower/yellow_daffodil")
	),
	
	DEPTHS_PLANTS(
		1, Material.plants, Block.soundTypeGrass, true, 0.0f, 0.0f, true, null, 0, true, 0, 0,
		0, "shrub", new BlockTexture(Type.STANDARD, "depths/shrub")
	),
	
	MATERIAL_PILES_VANILLA_A(
		2, BlockMaterials.METAL_PILE, ModSoundType.METAL_PILE, false, false, true, 0, 0,
		0, "iron", new BlockTexture(Type.STANDARD, "iron_pile"), 0.5f, 0.5f, "shovel", 0,
		1, "gold", new BlockTexture(Type.STANDARD, "gold_pile"), 0.5f, 0.5f, "shovel", 0
	),
	MATERIAL_PILES_VANILLA_B(
		2, BlockMaterials.METAL_PILE, ModSoundType.METAL_PILE, false, false, true, 0, 0,
		0, "diamond", new BlockTexture(Type.STANDARD, "diamond_pile"), 0.5f, 0.5f, "shovel", 0,
		1, "emerald", new BlockTexture(Type.STANDARD, "emerald_pile"), 0.5f, 0.5f, "shovel", 0
	),
	
	METAL_BLOCKS(
		2, Material.iron, Block.soundTypeMetal, false, false, true, 0, 0,
		0, "silver", new BlockTexture(Type.STANDARD, "silver/block"), 5.0f, 6.0f, "pickaxe", 2,
		1, "platinum", new BlockTexture(Type.STANDARD, "platinum/block"), 6.0f, 7.0f, "pickaxe", 2
	),
	
	RAW_METAL_BLOCKS(
		2, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "silver", new BlockTexture(Type.STANDARD, "silver/raw"), 5.0f, 6.0f, "pickaxe", 2,
		1, "platinum", new BlockTexture(Type.STANDARD, "platinum/raw"), 5.0f, 6.0f, "pickaxe", 2
	),
	
	METAL_PILES_A(
		2, BlockMaterials.METAL_PILE, ModSoundType.METAL_PILE, false, false, true, 0, 0,
		0, "silver", new BlockTexture(Type.STANDARD, "silver/pile"), 0.5f, 0.5f, "shovel", 0,
		1, "platinum", new BlockTexture(Type.STANDARD, "platinum/pile"), 0.5f, 0.5f, "shovel", 0
	),
	
	GEM_BLOCKS(
		1, Material.iron, Block.soundTypeMetal, false, false, true, 0, 0,
		0, "ancient_gem", new BlockTexture(Type.STANDARD, "ancient_gem/block"), 5.0f, 6.0f, "pickaxe", 2
	),
	
	GEM_PILES_A(
		1, BlockMaterials.METAL_PILE, ModSoundType.METAL_PILE, false, false, true, 0, 0,
		0, "ancient_gem", new BlockTexture(Type.STANDARD, "ancient_gem/pile"), 0.5f, 0.5f, "shovel", 0
	),
	
	PRISMARINE_0(
		8, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "plain", new BlockTexture(Type.STANDARD, "minecraft:prismarine"), 1.5f, 6.0f, "pickaxe", 0,
		1, "carved", new BlockTexture(Type.STANDARD, "prismarine/plain/carved"), 1.5f, 6.0f, "pickaxe", 0,
		2, "chiseled", new BlockTexture(Type.STANDARD, "prismarine/plain/chiseled"), 1.5f, 6.0f, "pickaxe", 0,
		3, "smooth", new BlockTexture(Type.STANDARD, "prismarine/plain/smooth"), 1.5f, 6.0f, "pickaxe", 0,
		4, "smooth_bricks", new BlockTexture(Type.STANDARD, "prismarine/plain/smooth_bricks"), 1.5f, 6.0f, "pickaxe", 0,
		5, "bricks", new BlockTexture(Type.STANDARD, "minecraft:prismarine_bricks"), 1.5f, 6.0f, "pickaxe", 0,
		6, "bricks_chiseled", new BlockTexture(Type.STANDARD, "prismarine/bricks/chiseled"), 1.5f, 6.0f, "pickaxe", 0,
		7, "bricks_cracked", new BlockTexture(Type.STANDARD, "prismarine/bricks/cracked"), 1.5f, 6.0f, "pickaxe", 0
	),
	PRISMARINE_1(
		5, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "dark", new BlockTexture(Type.STANDARD, "minecraft:dark_prismarine"), 1.5f, 6.0f, "pickaxe", 0,
		1, "dark_scales", new BlockTexture(Type.STANDARD, "prismarine/dark/scales"), 1.5f, 6.0f, "pickaxe", 0,
		2, "dark_scales_cracked", new BlockTexture(Type.STANDARD, "prismarine/dark/scales_cracked"), 1.5f, 6.0f, "pickaxe", 0,
		3, "dark_smooth", new BlockTexture(Type.STANDARD, "prismarine/dark/smooth"), 1.5f, 6.0f, "pickaxe", 0,
		4, "dark_triangle", new BlockTexture(Type.STANDARD, "prismarine/dark/triangle"), 1.5f, 6.0f, "pickaxe", 0
	),
	PRISMARINE_PILLAR(
		4, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "plain", new BlockTexture(Type.PILLAR, "prismarine/plain/pillar_end", "prismarine/plain/pillar_side"), 1.5f, 6.0f, "pickaxe", 0,
		1, "bricks", new BlockTexture(Type.PILLAR, "prismarine/bricks/pillar_end", "prismarine/bricks/pillar_side"), 1.5f, 6.0f, "pickaxe", 0,
		2, "dark_scales", new BlockTexture(Type.PILLAR, "prismarine/dark/scales_pillar_end", "prismarine/dark/scales_pillar_side"), 1.5f, 6.0f, "pickaxe", 0,
		3, "dark_smooth", new BlockTexture(Type.PILLAR, "prismarine/dark/smooth_pillar_end", "prismarine/dark/smooth_pillar_side"), 1.5f, 6.0f, "pickaxe", 0
	),
	
	VANILLA_WOOL_0(
		8, Material.cloth, Block.soundTypeCloth,  false, true, null, 0, true, 10, 30,
		0, "white", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_white"), 0.8f, 0.8f,
		1, "orange", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_orange"), 0.8f, 0.8f,
		2, "magenta", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_magenta"), 0.8f, 0.8f,
		3, "light_blue", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_light_blue"), 0.8f, 0.8f,
		4, "yellow", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_yellow"), 0.8f, 0.8f,
		5, "lime", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_lime"), 0.8f, 0.8f,
		6, "pink", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_pink"), 0.8f, 0.8f,
		7, "gray", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_gray"), 0.8f, 0.8f
	),
	VANILLA_WOOL_1(
		8, Material.cloth, Block.soundTypeCloth, false, true, null, 0, true, 10, 30,
		0, "silver", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_silver"), 0.8f, 0.8f,
		1, "cyan", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_cyan"), 0.8f, 0.8f,
		2, "purple", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_purple"), 0.8f, 0.8f,
		3, "blue", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_blue"), 0.8f, 0.8f,
		4, "brown", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_brown"), 0.8f, 0.8f,
		5, "green", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_green"), 0.8f, 0.8f,
		6, "red", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_red"), 0.8f, 0.8f,
		7, "black", new BlockTexture(Type.STANDARD, "minecraft:wool_colored_black"), 0.8f, 0.8f
	),
	
	VANILLA_STAINED_GLASS_0(
		8, Material.glass, Block.soundTypeGlass, false, true, null, 0, true, 0, 0,
		0, "white", new BlockTexture(Type.STANDARD, "minecraft:glass_white"), 0.3f, 0.3f,
		1, "orange", new BlockTexture(Type.STANDARD, "minecraft:glass_orange"), 0.3f, 0.3f,
		2, "magenta", new BlockTexture(Type.STANDARD, "minecraft:glass_magenta"), 0.3f, 0.3f,
		3, "light_blue", new BlockTexture(Type.STANDARD, "minecraft:glass_light_blue"), 0.3f, 0.3f,
		4, "yellow", new BlockTexture(Type.STANDARD, "minecraft:glass_yellow"), 0.3f, 0.3f,
		5, "lime", new BlockTexture(Type.STANDARD, "minecraft:glass_lime"), 0.3f, 0.3f,
		6, "pink", new BlockTexture(Type.STANDARD, "minecraft:glass_pink"), 0.3f, 0.3f,
		7, "gray", new BlockTexture(Type.STANDARD, "minecraft:glass_gray"), 0.3f, 0.3f
	),
	VANILLA_STAINED_GLASS_1(
		8, Material.glass, Block.soundTypeGlass, false, true, null, 0, true, 0, 0,
		0, "silver", new BlockTexture(Type.STANDARD, "minecraft:glass_silver"), 0.3f, 0.3f,
		1, "cyan", new BlockTexture(Type.STANDARD, "minecraft:glass_cyan"), 0.3f, 0.3f,
		2, "purple", new BlockTexture(Type.STANDARD, "minecraft:glass_purple"), 0.3f, 0.3f,
		3, "blue", new BlockTexture(Type.STANDARD, "minecraft:glass_blue"), 0.3f, 0.3f,
		4, "brown", new BlockTexture(Type.STANDARD, "minecraft:glass_brown"), 0.3f, 0.3f,
		5, "green", new BlockTexture(Type.STANDARD, "minecraft:glass_green"), 0.3f, 0.3f,
		6, "red", new BlockTexture(Type.STANDARD, "minecraft:glass_red"), 0.3f, 0.3f,
		7, "black", new BlockTexture(Type.STANDARD, "minecraft:glass_black"), 0.3f, 0.3f
	),
	
	VANILLA_STAINED_CLAY_0(
		8, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "white", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_white"), 1.25f, 4.2f, "pickaxe", 0,
		1, "orange", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_orange"), 1.25f, 4.2f, "pickaxe", 0,
		2, "magenta", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_magenta"), 1.25f, 4.2f, "pickaxe", 0,
		3, "light_blue", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_light_blue"), 1.25f, 4.2f, "pickaxe", 0,
		4, "yellow", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_yellow"), 1.25f, 4.2f, "pickaxe", 0,
		5, "lime", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_lime"), 1.25f, 4.2f, "pickaxe", 0,
		6, "pink", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_pink"), 1.25f, 4.2f, "pickaxe", 0,
		7, "gray", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_gray"), 1.25f, 4.2f, "pickaxe", 0
	),
	VANILLA_STAINED_CLAY_1(
		8, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "silver", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_silver"), 1.25f, 4.2f, "pickaxe", 0,
		1, "cyan", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_cyan"), 1.25f, 4.2f, "pickaxe", 0,
		2, "purple", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_purple"), 1.25f, 4.2f, "pickaxe", 0,
		3, "blue", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_blue"), 1.25f, 4.2f, "pickaxe", 0,
		4, "brown", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_brown"), 1.25f, 4.2f, "pickaxe", 0,
		5, "green", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_green"), 1.25f, 4.2f, "pickaxe", 0,
		6, "red", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_red"), 1.25f, 4.2f, "pickaxe", 0,
		7, "black", new BlockTexture(Type.STANDARD, "minecraft:hardened_clay_stained_black"), 1.25f, 4.2f, "pickaxe", 0
	),
	
	VANILLA_CONCRETE_0(
		8, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "white", new BlockTexture(Type.STANDARD, "minecraft:white_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		1, "orange", new BlockTexture(Type.STANDARD, "minecraft:orange_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		2, "magenta", new BlockTexture(Type.STANDARD, "minecraft:magenta_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		3, "light_blue", new BlockTexture(Type.STANDARD, "minecraft:light_blue_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		4, "yellow_concrete", new BlockTexture(Type.STANDARD, "minecraft:yellow_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		5, "lime", new BlockTexture(Type.STANDARD, "minecraft:lime_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		6, "pink", new BlockTexture(Type.STANDARD, "minecraft:pink_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		7, "gray", new BlockTexture(Type.STANDARD, "minecraft:gray_concrete"), 1.5f, 6.0f, "pickaxe", 0
	),
	VANILLA_CONCRETE_1(
		8, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "silver", new BlockTexture(Type.STANDARD, "minecraft:light_gray_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		1, "cyan", new BlockTexture(Type.STANDARD, "minecraft:cyan_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		2, "purple", new BlockTexture(Type.STANDARD, "minecraft:purple_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		3, "blue", new BlockTexture(Type.STANDARD, "minecraft:blue_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		4, "brown", new BlockTexture(Type.STANDARD, "minecraft:brown_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		5, "green", new BlockTexture(Type.STANDARD, "minecraft:green_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		6, "red", new BlockTexture(Type.STANDARD, "minecraft:red_concrete"), 1.5f, 6.0f, "pickaxe", 0,
		7, "black", new BlockTexture(Type.STANDARD, "minecraft:black_concrete"), 1.5f, 6.0f, "pickaxe", 0
	),
	
	HBM_BASE_CONCRETE_0(
		8, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "white", new BlockTexture(Type.STANDARD, "hbm:concrete_white"), 15.0f, 140.0f, "pickaxe", 0,
		1, "orange", new BlockTexture(Type.STANDARD, "hbm:concrete_orange"), 15.0f, 140.0f, "pickaxe", 0,
		2, "magenta", new BlockTexture(Type.STANDARD, "hbm:concrete_magenta"), 15.0f, 140.0f, "pickaxe", 0,
		3, "light_blue", new BlockTexture(Type.STANDARD, "hbm:concrete_light_blue"), 15.0f, 140.0f, "pickaxe", 0,
		4, "yellow", new BlockTexture(Type.STANDARD, "hbm:concrete_yellow"), 15.0f, 140.0f, "pickaxe", 0,
		5, "lime", new BlockTexture(Type.STANDARD, "hbm:concrete_lime"), 15.0f, 140.0f, "pickaxe", 0,
		6, "pink", new BlockTexture(Type.STANDARD, "hbm:concrete_pink"), 15.0f, 140.0f, "pickaxe", 0,
		7, "gray", new BlockTexture(Type.STANDARD, "hbm:concrete_gray"), 15.0f, 140.0f, "pickaxe", 0
	),
	HBM_BASE_CONCRETE_1(
		8, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "silver", new BlockTexture(Type.STANDARD, "hbm:concrete_silver"), 15.0f, 140.0f, "pickaxe", 0,
		1, "cyan", new BlockTexture(Type.STANDARD, "hbm:concrete_cyan"), 15.0f, 140.0f, "pickaxe", 0,
		2, "purple", new BlockTexture(Type.STANDARD, "hbm:concrete_purple"), 15.0f, 140.0f, "pickaxe", 0,
		3, "blue", new BlockTexture(Type.STANDARD, "hbm:concrete_blue"), 15.0f, 140.0f, "pickaxe", 0,
		4, "brown", new BlockTexture(Type.STANDARD, "hbm:concrete_brown"), 15.0f, 140.0f, "pickaxe", 0,
		5, "green", new BlockTexture(Type.STANDARD, "hbm:concrete_green"), 15.0f, 140.0f, "pickaxe", 0,
		6, "red", new BlockTexture(Type.STANDARD, "hbm:concrete_red"), 15.0f, 140.0f, "pickaxe", 0,
		7, "black", new BlockTexture(Type.STANDARD, "hbm:concrete_black"), 15.0f, 140.0f, "pickaxe", 0
	),
	HBM_BASE_CONCRETE_2(
		8, Material.rock, Block.soundTypeStone, false, false, true, 0, 0,
		0, "industrial", new BlockTexture(Type.STANDARD, "hbm:concrete_colored_ext.machine"), 15.0f, 140.0f, "pickaxe", 0,
		1, "industrial_stripe", new BlockTexture(Type.STANDARD, "hbm:concrete_colored_ext.machine_stripe"), 15.0f, 140.0f, "pickaxe", 0,
		2, "deep_indigo", new BlockTexture(Type.STANDARD, "hbm:concrete_colored_ext.indigo"), 15.0f, 140.0f, "pickaxe", 0,
		3, "mysterious_purple", new BlockTexture(Type.STANDARD, "hbm:concrete_colored_ext.purple"), 15.0f, 140.0f, "pickaxe", 0,
		4, "manly_pink", new BlockTexture(Type.STANDARD, "hbm:concrete_colored_ext.pink"), 15.0f, 140.0f, "pickaxe", 0,
		5, "hazard_stripe", new BlockTexture(Type.STANDARD, "hbm:concrete_colored_ext.hazard"), 15.0f, 140.0f, "pickaxe", 0,
		6, "desert_storm", new BlockTexture(Type.STANDARD, "hbm:concrete_colored_ext.sand"), 15.0f, 140.0f, "pickaxe", 0,
		7, "bronze_plating", new BlockTexture(Type.STANDARD, "hbm:concrete_colored_ext.bronze"), 15.0f, 140.0f, "pickaxe", 0
	);
	
	public final Material material;
	public final SoundType sound_type;
	
	public final int[] metas;
	
	public final String[] names;
	private final BlockTexture[] textures;
	
	public final float[] hardnesses;
	public final float[] resistances;
	public final String[] harvest_tools;
	public final int[] harvest_levels;
	public final int[] flammabilities;
	public final int[] fire_spread_speeds;
	
	private MultiBlocks(int size, Material material, SoundType sound, Object...data)
	{
		this.material = material;
		this.sound_type = sound;
		
		this.names = new String[size];
		this.textures = new BlockTexture[size];
		
		this.hardnesses = new float[size];
		this.resistances = new float[size];
		this.harvest_tools = new String[size];
		this.harvest_levels = new int[size];
		this.flammabilities = new int[size];
		this.fire_spread_speeds = new int[size];
		
		HashSet<Integer> metas = new HashSet<Integer>();
		Iterator it = Arrays.asList(data).iterator();
		
		boolean single_hardness = false;
		boolean single_tool = false;
		boolean single_fire = false;
		
		single_hardness = (boolean) it.next();
		if (single_hardness) {
			this.hardnesses[0] = (float) it.next();
			this.resistances[0] = (float) it.next();
		}
		single_tool = (boolean) it.next();
		if (single_tool) {
			this.harvest_tools[0] = (String) it.next();
			this.harvest_levels[0] = (int) it.next();
		}
		single_fire = (boolean) it.next();
		if (single_fire) {
			this.fire_spread_speeds[0] = (int) it.next();
			this.flammabilities[0] = (int) it.next();
		}
		
		while (it.hasNext()) {
			int meta = (int) it.next();
			metas.add(meta);
			
			this.names[meta] = (String) it.next();
			this.textures[meta] = (BlockTexture) it.next();
			
			this.hardnesses[meta] = single_hardness ? this.hardnesses[0] : (float) it.next();
			this.resistances[meta] = single_hardness ? this.resistances[0] : (float) it.next();
			this.harvest_tools[meta] = single_tool ? this.harvest_tools[0] : (String) it.next();
			this.harvest_levels[meta] = single_tool ? this.harvest_levels[0] : (int) it.next();
			this.fire_spread_speeds[meta] = single_fire ? this.fire_spread_speeds[0] : (int) it.next();
			this.flammabilities[meta] = single_fire ? this.flammabilities[0] : (int) it.next();
		}
		
		this.metas = new int[metas.size()];
		for (int i = 0; i < this.metas.length; i++) {
			this.metas[i] = (int) metas.toArray()[i];
		}
	}
	
	public Material getMaterial()
	{
		return this.material;
	}
	
	public SoundType getSoundType()
	{
		return this.sound_type;
	}

	@Override
	public String getName(int meta)
	{
		if (!this.isValidMeta(meta)) {
			meta = this.metas[0];
		}
		return this.names[meta];
	}
	
	@Override
	public String[] getNames()
	{
		return this.names;
	}
	
	@Override
	public boolean doesBlockShatter(int meta)
	{
		return this.getMaterial() == Material.glass;
	}
	
	@Override
	public float getHardness(int meta)
	{
		if (!this.isValidMeta(meta)) {
			meta = this.metas[0];
		}
		return this.hardnesses[meta];
	}
	
	@Override
	public float getResistance(int meta)
	{
		if (!this.isValidMeta(meta)) {
			meta = this.metas[0];
		}
		return this.resistances[meta];
	}
	
	@Override
	public int getFlammability(int meta)
	{
		if (!this.isValidMeta(meta)) {
			meta = this.metas[0];
		}
		return this.flammabilities[meta];
	}
	
	@Override
	public int getFireSpreadSpeed(int meta)
	{
		if (!this.isValidMeta(meta)) {
			meta = this.metas[0];
		}
		return this.fire_spread_speeds[meta];
	}
	
	@Override
	public void setBlockData(Block block)
	{
		block.setHardness(this.hardnesses[0]);
		block.setResistance(this.resistances[0]);
		for (int m : this.metas) {
			if (this.harvest_tools[m] == null) {
				continue;
			}
			block.setHarvestLevel(this.harvest_tools[m], this.harvest_levels[m], m);
		}
		block.setStepSound(this.sound_type);
	}
	
	@Override
	public int[] getValidMetas()
	{
		return this.metas;
	}
	
	@Override
	public boolean isValidMeta(int meta)
	{
		for (int m : this.metas) {
			if (m == meta) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		for (BlockTexture texture : this.textures) {
			if (texture != null) {
				texture.registerIcons(reg);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		BlockTexture texture = this.textures[meta];
		if (texture == null) {
			texture = this.textures[this.metas[0]];
		}
		return texture.getIcon(side, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFast(int side, int meta)
	{
		BlockTexture texture = this.textures[meta];
		if (texture == null) {
			texture = this.textures[this.metas[0]];
		}
		return texture.getIconFast(side, meta);
	}
}
