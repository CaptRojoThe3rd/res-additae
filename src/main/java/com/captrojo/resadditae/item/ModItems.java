package com.captrojo.resadditae.item;

import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.item.devtool.ItemStructureWand;
import com.captrojo.resadditae.item.equipment.ItemArmor;
import com.captrojo.resadditae.item.equipment.ItemAxe;
import com.captrojo.resadditae.item.equipment.ItemHalberd;
import com.captrojo.resadditae.item.equipment.ItemHoe;
import com.captrojo.resadditae.item.equipment.ItemPickaxe;
import com.captrojo.resadditae.item.equipment.ItemScythe;
import com.captrojo.resadditae.item.equipment.ItemShovel;
import com.captrojo.resadditae.item.equipment.ItemSword;
import com.captrojo.resadditae.item.generic.ItemKey;
import com.captrojo.resadditae.item.generic.ItemMulti;
import com.captrojo.resadditae.item.generic.ItemMultiPotionIngredient;
import com.captrojo.resadditae.item.generic.ItemVessel;
import com.captrojo.resadditae.material.ArmorMaterials;
import com.captrojo.resadditae.material.ExtendedToolMaterial;
import com.captrojo.resadditae.material.ToolMaterials;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;

public class ModItems
{
	public static final int HELMET = 0;
	public static final int CHESTPLATE = 1;
	public static final int LEGGINGS = 2;
	public static final int BOOTS = 3;
	
	public static Item iron_nugget;

	public static Item ingots;
	public static Item nuggets;
	public static Item raws;
	public static Item gems;
	
	public static Item potion_ingredients;
	public static Item dye;
	public static Item shiny_rocks;
	
	public static Item wood_scythe;
	public static Item wood_halberd;
	
	public static Item stone_scythe;
	public static Item stone_halberd;
	
	public static Item iron_scythe;
	public static Item iron_halberd;
	
	public static Item gold_scythe;
	public static Item gold_halberd;
	
	public static Item diamond_scythe;
	public static Item diamond_halberd;
	
	public static Item netherite_scythe;
	public static Item netherite_halberd;
	
	public static Item steel_scythe;
	public static Item steel_halberd;
	
	public static Item titanium_scythe;
	public static Item titanium_halberd;
	
	public static Item cobalt_scythe;
	public static Item cobalt_halberd;
	
	public static Item starmetal_scythe;
	public static Item starmetal_halberd;
	
	public static Item cmb_scythe;
	public static Item cmb_halberd;

	public static Item silver_pickaxe;
	public static Item silver_axe;
	public static Item silver_shovel;
	public static Item silver_sword;
	public static Item silver_hoe;
	public static Item silver_scythe;
	public static Item silver_halberd;
	public static Item silver_helmet;
	public static Item silver_chestplate;
	public static Item silver_leggings;
	public static Item silver_boots;

	public static Item platinum_pickaxe;
	public static Item platinum_axe;
	public static Item platinum_shovel;
	public static Item platinum_sword;
	public static Item platinum_hoe;
	public static Item platinum_scythe;
	public static Item platinum_halberd;
	public static Item platinum_helmet;
	public static Item platinum_chestplate;
	public static Item platinum_leggings;
	public static Item platinum_boots;
	
	public static Item ancient_gem_pickaxe;
	public static Item ancient_gem_axe;
	public static Item ancient_gem_shovel;
	public static Item ancient_gem_sword;
	public static Item ancient_gem_hoe;
	public static Item ancient_gem_scythe;
	public static Item ancient_gem_halberd;
	public static Item ancient_gem_helmet;
	public static Item ancient_gem_chestplate;
	public static Item ancient_gem_leggings;
	public static Item ancient_gem_boots;
	
	public static Item keys;
	public static Item vessels;
	
	public static Item structure_wand;

	public static void initItems()
	{
		if (CommonConfig.CommonStuff.iron_nuggets) {
			iron_nugget = new Item().setUnlocalizedName("iron_nugget").setTextureName("iron_nugget");
		}
		
		ingots = new ItemMulti("ingots", MultiItems.INGOTS);
		nuggets = new ItemMulti("nuggets", MultiItems.NUGGETS);
		raws = new ItemMulti("raws", MultiItems.RAWS);
		gems = new ItemMulti("gems", MultiItems.GEMS);
		
		potion_ingredients = new ItemMultiPotionIngredient("potion_ingredients", MultiItems.POTION_INGREDS);
		dye = new ItemMulti("dye", new DyeItemData());
		shiny_rocks = new ItemMulti("shiny_rocks", MultiItems.SHINY_ROCKS);
		
		wood_scythe = new ItemScythe("wood_scythe", "wood_scythe", ToolMaterial.WOOD, ExtendedToolMaterial.WOOD);
		wood_halberd = new ItemHalberd("wood_halberd", "wood_halberd", "textures/tools/wood_halberd.png", ToolMaterial.WOOD, ExtendedToolMaterial.WOOD);

		stone_scythe = new ItemScythe("stone_scythe", "stone_scythe", ToolMaterial.STONE, ExtendedToolMaterial.STONE);
		stone_halberd = new ItemHalberd("stone_halberd", "stone_halberd", "textures/tools/stone_halberd.png", ToolMaterial.STONE, ExtendedToolMaterial.STONE);
		
		iron_scythe = new ItemScythe("iron_scythe", "iron_scythe", ToolMaterial.IRON, ExtendedToolMaterial.IRON);
		iron_halberd = new ItemHalberd("iron_halberd", "iron_halberd", "textures/tools/iron_halberd.png", ToolMaterial.IRON, ExtendedToolMaterial.IRON);
		
		gold_scythe = new ItemScythe("gold_scythe", "gold_scythe", ToolMaterial.GOLD, ExtendedToolMaterial.GOLD);
		gold_halberd = new ItemHalberd("gold_halberd", "gold_halberd", "textures/tools/gold_halberd.png", ToolMaterial.GOLD, ExtendedToolMaterial.GOLD);
		
		diamond_scythe = new ItemScythe("diamond_scythe", "diamond_scythe", ToolMaterial.EMERALD, ExtendedToolMaterial.DIAMOND);
		diamond_halberd = new ItemHalberd("diamond_halberd", "diamond_halberd", "textures/tools/diamond_halberd.png", ToolMaterial.EMERALD, ExtendedToolMaterial.DIAMOND);
		
		if (CommonConfig.General.netherite_tools) {
			netherite_scythe = new ItemScythe("netherite_scythe", "netherite_scythe", ToolMaterials.NETHERITE, ExtendedToolMaterial.NETHERITE);
			netherite_halberd = new ItemHalberd("netherite_halberd", "netherite_halberd", "textures/tools/netherite_halberd.png", ToolMaterials.NETHERITE, ExtendedToolMaterial.NETHERITE);
		}
		
		if (CommonConfig.General.hbm_tools) {
			steel_scythe = new ItemScythe("steel_scythe", "steel_scythe", ToolMaterials.HBM_STEEL, ExtendedToolMaterial.HBM_STEEL);
			steel_halberd = new ItemHalberd("steel_halberd", "steel_halberd", "textures/tools/steel_halberd.png", ToolMaterials.HBM_STEEL, ExtendedToolMaterial.HBM_STEEL);
		
			titanium_scythe = new ItemScythe("titanium_scythe", "titanium_scythe", ToolMaterials.HBM_TITANIUM, ExtendedToolMaterial.HBM_TITANIUM);
			titanium_halberd = new ItemHalberd("titanium_halberd", "titanium_halberd", "textures/tools/titanium_halberd.png", ToolMaterials.HBM_TITANIUM, ExtendedToolMaterial.HBM_TITANIUM);
			
			cobalt_scythe = new ItemScythe("cobalt_scythe", "cobalt_scythe", ToolMaterials.HBM_COBALT, ExtendedToolMaterial.HBM_COBALT);
			cobalt_halberd = new ItemHalberd("cobalt_halberd", "cobalt_halberd", "textures/tools/cobalt_halberd.png", ToolMaterials.HBM_COBALT, ExtendedToolMaterial.HBM_COBALT);
			
			starmetal_scythe = new ItemScythe("starmetal_scythe", "starmetal_scythe", ToolMaterials.HBM_STARMETAL, ExtendedToolMaterial.HBM_STARMETAL);
			starmetal_halberd = new ItemHalberd("starmetal_halberd", "starmetal_halberd", "textures/tools/starmetal_halberd.png", ToolMaterials.HBM_STARMETAL, ExtendedToolMaterial.HBM_STARMETAL);
			
			cmb_scythe = new ItemScythe("cmb_scythe", "cmb_scythe", ToolMaterials.HBM_CMB, ExtendedToolMaterial.HBM_CMB);
			cmb_halberd = new ItemHalberd("cmb_halberd", "cmb_halberd", "textures/tools/cmb_halberd.png", ToolMaterials.HBM_CMB, ExtendedToolMaterial.HBM_CMB);
		}
		
		silver_pickaxe = new ItemPickaxe("silver_pickaxe", "silver/silver_pickaxe", ToolMaterials.SILVER);
		silver_axe = new ItemAxe("silver_axe", "silver/silver_axe", ToolMaterials.SILVER);
		silver_shovel = new ItemShovel("silver_shovel", "silver/silver_shovel", ToolMaterials.SILVER);
		silver_sword = new ItemSword("silver_sword", "silver/silver_sword", ToolMaterials.SILVER);
		silver_hoe = new ItemHoe("silver_hoe", "silver/silver_hoe", ToolMaterials.SILVER);
		silver_scythe = new ItemScythe("silver_scythe", "silver/silver_scythe", ToolMaterials.SILVER, ExtendedToolMaterial.SILVER);
		silver_halberd = new ItemHalberd("silver_halberd", "silver/silver_halberd", "textures/tools/silver_halberd.png", ToolMaterials.SILVER, ExtendedToolMaterial.SILVER);
		silver_helmet = new ItemArmor("silver_helmet", "silver/silver_helmet", "silver", ArmorMaterials.SILVER, MultiItemStacks.SILVER_INGOT.info(), HELMET);
		silver_chestplate = new ItemArmor("silver_chestplate", "silver/silver_chestplate", "silver", ArmorMaterials.SILVER, MultiItemStacks.SILVER_INGOT.info(), CHESTPLATE);
		silver_leggings = new ItemArmor("silver_leggings", "silver/silver_leggings", "silver", ArmorMaterials.SILVER, MultiItemStacks.SILVER_INGOT.info(), LEGGINGS);
		silver_boots = new ItemArmor("silver_boots", "silver/silver_boots", "silver", ArmorMaterials.SILVER, MultiItemStacks.SILVER_INGOT.info(), BOOTS);

		platinum_pickaxe = new ItemPickaxe("platinum_pickaxe", "platinum/platinum_pickaxe", ToolMaterials.PLATINUM);
		platinum_axe = new ItemAxe("platinum_axe", "platinum/platinum_axe", ToolMaterials.PLATINUM);
		platinum_shovel = new ItemShovel("platinum_shovel", "platinum/platinum_shovel", ToolMaterials.PLATINUM);
		platinum_sword = new ItemSword("platinum_sword", "platinum/platinum_sword", ToolMaterials.PLATINUM);
		platinum_hoe = new ItemHoe("platinum_hoe", "platinum/platinum_hoe", ToolMaterials.PLATINUM);
		platinum_scythe = new ItemScythe("platinum_scythe", "platinum/platinum_scythe", ToolMaterials.PLATINUM, ExtendedToolMaterial.PLATINUM);
		platinum_halberd = new ItemHalberd("platinum_halberd", "platinum/platinum_halberd", "textures/tools/platinum_halberd.png", ToolMaterials.PLATINUM, ExtendedToolMaterial.PLATINUM);
		platinum_helmet = new ItemArmor("platinum_helmet", "platinum/platinum_helmet", "platinum", ArmorMaterials.PLATINUM, MultiItemStacks.PLATINUM_INGOT.info(), HELMET);
		platinum_chestplate = new ItemArmor("platinum_chestplate", "platinum/platinum_chestplate", "platinum", ArmorMaterials.PLATINUM, MultiItemStacks.PLATINUM_INGOT.info(), CHESTPLATE);
		platinum_leggings = new ItemArmor("platinum_leggings", "platinum/platinum_leggings", "platinum", ArmorMaterials.PLATINUM, MultiItemStacks.PLATINUM_INGOT.info(), LEGGINGS);
		platinum_boots = new ItemArmor("platinum_boots", "platinum/platinum_boots", "platinum", ArmorMaterials.PLATINUM, MultiItemStacks.PLATINUM_INGOT.info(), BOOTS);
	
		ancient_gem_pickaxe = new ItemPickaxe("ancient_gem_pickaxe", "ancient_gem/ancient_gem_pickaxe", ToolMaterials.ANCIENT_GEM);
		ancient_gem_axe = new ItemAxe("ancient_gem_axe", "ancient_gem/ancient_gem_axe", ToolMaterials.ANCIENT_GEM);
		ancient_gem_shovel = new ItemShovel("ancient_gem_shovel", "ancient_gem/ancient_gem_shovel", ToolMaterials.ANCIENT_GEM);
		ancient_gem_sword = new ItemSword("ancient_gem_sword", "ancient_gem/ancient_gem_sword", ToolMaterials.ANCIENT_GEM);
		ancient_gem_hoe = new ItemHoe("ancient_gem_hoe", "ancient_gem/ancient_gem_hoe", ToolMaterials.ANCIENT_GEM);
		ancient_gem_scythe = new ItemScythe("ancient_gem_scythe", "ancient_gem/ancient_gem_scythe", ToolMaterials.ANCIENT_GEM, ExtendedToolMaterial.ANCIENT_GEM);
		ancient_gem_halberd = new ItemHalberd("ancient_gem_halberd", "ancient_gem/ancient_gem_halberd", "textures/tools/ancient_gem_halberd.png", ToolMaterials.ANCIENT_GEM, ExtendedToolMaterial.ANCIENT_GEM);
		ancient_gem_helmet = new ItemArmor("ancient_gem_helmet", "ancient_gem/ancient_gem_helmet", "ancient_gem", ArmorMaterials.ANCIENT_GEM, MultiItemStacks.ANCIENT_GEM.info(), HELMET);
		ancient_gem_chestplate = new ItemArmor("ancient_gem_chestplate", "ancient_gem/ancient_gem_chestplate", "ancient_gem", ArmorMaterials.ANCIENT_GEM, MultiItemStacks.ANCIENT_GEM.info(), CHESTPLATE);
		ancient_gem_leggings = new ItemArmor("ancient_gem_leggings", "ancient_gem/ancient_gem_leggings", "ancient_gem", ArmorMaterials.ANCIENT_GEM, MultiItemStacks.ANCIENT_GEM.info(), LEGGINGS);
		ancient_gem_boots = new ItemArmor("ancient_gem_boots", "ancient_gem/ancient_gem_boots", "ancient_gem", ArmorMaterials.ANCIENT_GEM, MultiItemStacks.ANCIENT_GEM.info(), BOOTS);
		
		keys = new ItemKey();
		vessels = new ItemVessel();
		
		structure_wand = new ItemStructureWand();
	}

	public static void registerItems()
	{
		if (CommonConfig.CommonStuff.iron_nuggets) {
			GameRegistry.registerItem(iron_nugget, iron_nugget.getUnlocalizedName());
		}
		
		GameRegistry.registerItem(ingots, ingots.getUnlocalizedName());
		GameRegistry.registerItem(nuggets, nuggets.getUnlocalizedName());
		GameRegistry.registerItem(raws, raws.getUnlocalizedName());
		GameRegistry.registerItem(gems, gems.getUnlocalizedName());
		
		GameRegistry.registerItem(potion_ingredients, potion_ingredients.getUnlocalizedName());
		GameRegistry.registerItem(dye, dye.getUnlocalizedName());
		GameRegistry.registerItem(shiny_rocks, shiny_rocks.getUnlocalizedName());
		
		GameRegistry.registerItem(wood_scythe, wood_scythe.getUnlocalizedName());
		GameRegistry.registerItem(wood_halberd, wood_halberd.getUnlocalizedName());
		
		GameRegistry.registerItem(stone_scythe, stone_scythe.getUnlocalizedName());
		GameRegistry.registerItem(stone_halberd, stone_halberd.getUnlocalizedName());
		
		GameRegistry.registerItem(iron_scythe, iron_scythe.getUnlocalizedName());
		GameRegistry.registerItem(iron_halberd, iron_halberd.getUnlocalizedName());
		
		GameRegistry.registerItem(gold_scythe, gold_scythe.getUnlocalizedName());
		GameRegistry.registerItem(gold_halberd, gold_halberd.getUnlocalizedName());
		
		GameRegistry.registerItem(diamond_scythe, diamond_scythe.getUnlocalizedName());
		GameRegistry.registerItem(diamond_halberd, diamond_halberd.getUnlocalizedName());
		
		if (CommonConfig.General.netherite_tools) {
			GameRegistry.registerItem(netherite_scythe, netherite_scythe.getUnlocalizedName());
			GameRegistry.registerItem(netherite_halberd, netherite_halberd.getUnlocalizedName());
		}
		
		if (CommonConfig.General.hbm_tools) {
			GameRegistry.registerItem(steel_scythe, steel_scythe.getUnlocalizedName());
			GameRegistry.registerItem(steel_halberd, steel_halberd.getUnlocalizedName());
			
			GameRegistry.registerItem(titanium_scythe, titanium_scythe.getUnlocalizedName());
			GameRegistry.registerItem(titanium_halberd, titanium_halberd.getUnlocalizedName());
			
			GameRegistry.registerItem(cobalt_scythe, cobalt_scythe.getUnlocalizedName());
			GameRegistry.registerItem(cobalt_halberd, cobalt_halberd.getUnlocalizedName());
			
			GameRegistry.registerItem(starmetal_scythe, starmetal_scythe.getUnlocalizedName());
			GameRegistry.registerItem(starmetal_halberd, starmetal_halberd.getUnlocalizedName());
			
			GameRegistry.registerItem(cmb_scythe, cmb_scythe.getUnlocalizedName());
			GameRegistry.registerItem(cmb_halberd, cmb_halberd.getUnlocalizedName());
		}

		GameRegistry.registerItem(silver_pickaxe, silver_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(silver_axe, silver_axe.getUnlocalizedName());
		GameRegistry.registerItem(silver_shovel, silver_shovel.getUnlocalizedName());
		GameRegistry.registerItem(silver_sword, silver_sword.getUnlocalizedName());
		GameRegistry.registerItem(silver_hoe, silver_hoe.getUnlocalizedName());
		GameRegistry.registerItem(silver_scythe, silver_scythe.getUnlocalizedName());
		GameRegistry.registerItem(silver_halberd, silver_halberd.getUnlocalizedName());
		GameRegistry.registerItem(silver_helmet, silver_helmet.getUnlocalizedName());
		GameRegistry.registerItem(silver_chestplate, silver_chestplate.getUnlocalizedName());
		GameRegistry.registerItem(silver_leggings, silver_leggings.getUnlocalizedName());
		GameRegistry.registerItem(silver_boots, silver_boots.getUnlocalizedName());

		GameRegistry.registerItem(platinum_pickaxe, platinum_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(platinum_axe, platinum_axe.getUnlocalizedName());
		GameRegistry.registerItem(platinum_shovel, platinum_shovel.getUnlocalizedName());
		GameRegistry.registerItem(platinum_sword, platinum_sword.getUnlocalizedName());
		GameRegistry.registerItem(platinum_hoe, platinum_hoe.getUnlocalizedName());
		GameRegistry.registerItem(platinum_scythe, platinum_scythe.getUnlocalizedName());
		GameRegistry.registerItem(platinum_halberd, platinum_halberd.getUnlocalizedName());
		GameRegistry.registerItem(platinum_helmet, platinum_helmet.getUnlocalizedName());
		GameRegistry.registerItem(platinum_chestplate, platinum_chestplate.getUnlocalizedName());
		GameRegistry.registerItem(platinum_leggings, platinum_leggings.getUnlocalizedName());
		GameRegistry.registerItem(platinum_boots, platinum_boots.getUnlocalizedName());
		
		GameRegistry.registerItem(ancient_gem_pickaxe, ancient_gem_pickaxe.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_axe, ancient_gem_axe.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_shovel, ancient_gem_shovel.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_sword, ancient_gem_sword.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_hoe, ancient_gem_hoe.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_scythe, ancient_gem_scythe.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_halberd, ancient_gem_halberd.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_helmet, ancient_gem_helmet.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_chestplate, ancient_gem_chestplate.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_leggings, ancient_gem_leggings.getUnlocalizedName());
		GameRegistry.registerItem(ancient_gem_boots, ancient_gem_boots.getUnlocalizedName());
		
		GameRegistry.registerItem(keys, keys.getUnlocalizedName());
		GameRegistry.registerItem(vessels, vessels.getUnlocalizedName());
		
		GameRegistry.registerItem(structure_wand, structure_wand.getUnlocalizedName());
	}
}
