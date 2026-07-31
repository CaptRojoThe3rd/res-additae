package com.captrojo.resadditae.world.biome;

import com.captrojo.resadditae.config.common.BiomeConfig;
import com.captrojo.resadditae.world.biome.depths.BiomeAmberDepths;
import com.captrojo.resadditae.world.biome.depths.BiomeDepthsBase;
import com.captrojo.resadditae.world.biome.depths.BiomeJadeDepths;
import com.captrojo.resadditae.world.biome.depths.BiomeRubyDepths;
import com.captrojo.resadditae.world.biome.depths.BiomeSapphireDepths;
import com.captrojo.resadditae.world.biome.depths.BiomeTopazDepths;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class ModBiomes
{
	public static BiomeDepthsBase depths_amber;
	public static BiomeDepthsBase depths_jade;
	public static BiomeDepthsBase depths_ruby;
	public static BiomeDepthsBase depths_sapphire;
	public static BiomeDepthsBase depths_topaz;
	
	public static BiomeGenBase chestnut_forest;
	public static BiomeGenBase enchanted_ash_forest;
	public static BiomeGenBase violet_forest;

	public static BiomeEntry chestnut_forest_entry;
	public static BiomeEntry enchanted_ash_forest_entry;
	public static BiomeEntry violet_forest_entry;
	
	public static void initBiomes()
	{
		depths_amber = new BiomeAmberDepths(BiomeConfig.depths_amber_id);
		depths_jade = new BiomeJadeDepths(BiomeConfig.depths_jade_id);
		depths_ruby = new BiomeRubyDepths(BiomeConfig.depths_ruby_id);
		depths_sapphire = new BiomeSapphireDepths(BiomeConfig.depths_sapphire_id);
		depths_topaz = new BiomeTopazDepths(BiomeConfig.depths_topaz_id);
		
		if (BiomeConfig.chestnut_forest_enabled) {
			chestnut_forest = new BiomeChestnutForest(BiomeConfig.chestnut_forest_id);
			chestnut_forest_entry = new BiomeEntry(chestnut_forest, BiomeConfig.chestnut_forest_weight);
		}
		if (BiomeConfig.enchanted_ash_forest_enabled) {
			enchanted_ash_forest = new BiomeEnchantedAshForest(BiomeConfig.enchanted_ash_forest_id);
			enchanted_ash_forest_entry = new BiomeEntry(enchanted_ash_forest, BiomeConfig.enchanted_ash_forest_weight);
		}
		if (BiomeConfig.violet_forest_enabled) {
			violet_forest = new BiomeVioletForest(BiomeConfig.violet_forest_id);
			violet_forest_entry = new BiomeEntry(violet_forest, BiomeConfig.violet_forest_weight);
		}
	}
	
	public static void registerBiomes()
	{
		/* BiomeDictionary is meant for mod compat. I don't think we want other mods 
		 * messing with biomes for The Depths, but NTM registers its crater biomes
		 * with BiomeDictionary, so I guess I should register these biomes with it.
		 */
		BiomeDictionary.registerBiomeType(depths_amber, Type.SPOOKY);
		BiomeDictionary.registerBiomeType(depths_jade, Type.SPOOKY);
		BiomeDictionary.registerBiomeType(depths_ruby, Type.SPOOKY);
		BiomeDictionary.registerBiomeType(depths_sapphire, Type.SPOOKY);
		BiomeDictionary.registerBiomeType(depths_topaz, Type.SPOOKY);
		
		if (BiomeConfig.chestnut_forest_enabled) {
			BiomeDictionary.registerBiomeType(chestnut_forest, Type.FOREST);
			BiomeManager.addBiome(BiomeType.WARM, chestnut_forest_entry);
			BiomeManager.addSpawnBiome(chestnut_forest);
			BiomeManager.addVillageBiome(chestnut_forest, true);
			BiomeManager.addStrongholdBiome(chestnut_forest);
		}
		if (BiomeConfig.enchanted_ash_forest_enabled) {
			BiomeDictionary.registerBiomeType(enchanted_ash_forest, Type.FOREST, Type.MAGICAL);
			BiomeManager.addBiome(BiomeType.COOL, enchanted_ash_forest_entry);
			BiomeManager.addStrongholdBiome(enchanted_ash_forest);
		}
		if (BiomeConfig.violet_forest_enabled) {
			BiomeDictionary.registerBiomeType(violet_forest, Type.FOREST, Type.CONIFEROUS);
			BiomeManager.addBiome(BiomeType.COOL, violet_forest_entry);
			BiomeManager.addSpawnBiome(violet_forest);
			BiomeManager.addVillageBiome(violet_forest, true);
			BiomeManager.addStrongholdBiome(violet_forest);
		}
	}
}
