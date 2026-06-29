package com.captrojo.resadditae.compatibility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Lists of different equipment, sorted by tier in ascending order. Includes equipment from other
 * mods. Generally, equipment tiers that require significant progression (e.g. a lot of NTM stuff)
 * or have with complex features (lots of abilities or electric charge) are not included, as these
 * lists are primarily intended for giving equipment to mobs.
 */
public class OrderedEquipmentLists
{
	private static Random rand = new Random();
	
	public static final ArrayList<Item> boots = new ArrayList<Item>();
	public static final ArrayList<Item> leggings = new ArrayList<Item>();
	public static final ArrayList<Item> chestplates = new ArrayList<Item>();
	public static final ArrayList<Item> helmets = new ArrayList<Item>();
	
	public static final ArrayList<Item> pickaxes = new ArrayList<Item>();
	public static final ArrayList<Item> axes = new ArrayList<Item>();
	public static final ArrayList<Item> shovels = new ArrayList<Item>();
	public static final ArrayList<Item> swords = new ArrayList<Item>();
	public static final ArrayList<Item> hoes = new ArrayList<Item>();
	
	public static final ArrayList<Item> scythes = new ArrayList<Item>();
	public static final ArrayList<Item> halberds = new ArrayList<Item>();
	
	public static final Map<Tiers, Item> boots_map = new HashMap<Tiers, Item>();
	public static final Map<Tiers, Item> leggings_map = new HashMap<Tiers, Item>();
	public static final Map<Tiers, Item> chestplate_map = new HashMap<Tiers, Item>();
	public static final Map<Tiers, Item> helmet_map = new HashMap<Tiers, Item>();

	public static final Map<Tiers, Item> pickaxe_map = new HashMap<Tiers, Item>();
	public static final Map<Tiers, Item> axe_map = new HashMap<Tiers, Item>();
	public static final Map<Tiers, Item> shovel_map = new HashMap<Tiers, Item>();
	public static final Map<Tiers, Item> sword_map = new HashMap<Tiers, Item>();
	public static final Map<Tiers, Item> hoe_map = new HashMap<Tiers, Item>();

	public static final Map<Tiers, Item> scythe_map = new HashMap<Tiers, Item>();
	public static final Map<Tiers, Item> halberd_map = new HashMap<Tiers, Item>();
	
	public static enum Tiers
	{
		LEATHER_WOOD,
		STONE_CHAINMAIL,
		IRON,
		STEEL,
		SILVER,
		GOLD,
		PLATINUM,
		TITANIUM,
		DIAMOND,
		COBALT,
		STARMETAL,
		CMB_STEEL,
		ANCIENT_GEM;
	}
	
	private static final String[] BOOTS = {
		"minecraft:leather_boots",
		"minecraft:chainmail_boots",
		"minecraft:iron_boots",
		"hbm:item.steel_boots",
		"resadditae:item.silver_boots",
		"minecraft:golden_boots",
		"resadditae:item.platinum_boots",
		"hbm:item.titanium_boots",
		"minecraft:diamond_boots",
		"hbm:item.cobalt_boots",
		"hbm:item.starmetal_boots",
		"hbm:item.cmb_boots",
		"resadditae:item.ancient_gem_boots"
	};
	
	private static final String[] LEGGINGS = {
		"minecraft:leather_leggings",
		"minecraft:chainmail_leggings",
		"minecraft:iron_leggings",
		"hbm:item.steel_legs",
		"resadditae:item.silver_leggings",
		"minecraft:golden_leggings",
		"resadditae:item.platinum_leggings",
		"hbm:item.titanium_legs",
		"minecraft:diamond_leggings",
		"hbm:item.cobalt_legs",
		"hbm:item.starmetal_legs",
		"hbm:item.cmb_legs",
		"resadditae:item.ancient_gem_leggings"
	};
	
	private static final String[] CHESTPLATES = {
		"minecraft:leather_chestplate",
		"minecraft:chainmail_chestplate",
		"minecraft:iron_chestplate",
		"hbm:item.steel_plate",
		"resadditae:item.silver_chestplate",
		"minecraft:golden_chestplate",
		"resadditae:item.platinum_chestplate",
		"hbm:item.titanium_plate",
		"minecraft:diamond_chestplate",
		"hbm:item.cobalt_plate",
		"hbm:item.starmetal_plate",
		"hbm:item.cmb_plate",
		"resadditae:item.ancient_gem_chestplate"
	};
	
	private static final String[] HELMETS = {
		"minecraft:leather_helmet",
		"minecraft:chainmail_helmet",
		"minecraft:iron_helmet",
		"hbm:item.steel_helmet",
		"resadditae:item.silver_helmet",
		"minecraft:golden_helmet",
		"resadditae:item.platinum_helmet",
		"hbm:item.titanium_helmet",
		"minecraft:diamond_helmet",
		"hbm:item.cobalt_helmet",
		"hbm:item.starmetal_helmet",
		"hbm:item.cmb_helmet",
		"resadditae:item.ancient_gem_helmet"
	};
	
	private static final String[] PICKAXES = {
		"minecraft:wooden_pickaxe",
		"minecraft:stone_pickaxe",
		"minecraft:iron_pickaxe",
		"hbm:item.steel_pickaxe",
		"resadditae:item.silver_pickaxe",
		"minecraft:golden_pickaxe",
		"resadditae:item.platinum_pickaxe",
		"hbm:item.titanium_pickaxe",
		"minecraft:diamond_pickaxe",
		"hbm:item.cobalt_pickaxe",
		"hbm:item.starmetal_pickaxe",
		"hbm:item.cmb_pickaxe",
		"resadditae:item.ancient_gem_pickaxe"
	};
	
	private static final String[] AXES = {
		"minecraft:wooden_axe",
		"minecraft:stone_axe",
		"minecraft:iron_axe",
		"hbm:item.steel_axe",
		"resadditae:item.silver_axe",
		"minecraft:golden_axe",
		"resadditae:item.platinum_axe",
		"hbm:item.titanium_axe",
		"minecraft:diamond_axe",
		"hbm:item.cobalt_axe",
		"hbm:item.starmetal_axe",
		"hbm:item.cmb_axe",
		"resadditae:item.ancient_gem_axe"
	};
	
	private static final String[] SHOVELS = {
		"minecraft:wooden_shovel",
		"minecraft:stone_shovel",
		"minecraft:iron_shovel",
		"hbm:item.steel_shovel",
		"resadditae:item.silver_shovel",
		"minecraft:golden_shovel",
		"resadditae:item.platinum_shovel",
		"hbm:item.titanium_shovel",
		"minecraft:diamond_shovel",
		"hbm:item.cobalt_shovel",
		"hbm:item.starmetal_shovel",
		"hbm:item.cmb_shovel",
		"resadditae:item.ancient_gem_shovel"
	};
	
	private static final String[] SWORDS = {
		"minecraft:wooden_sword",
		"minecraft:stone_sword",
		"minecraft:iron_sword",
		"hbm:item.steel_sword",
		"resadditae:item.silver_sword",
		"minecraft:golden_sword",
		"resadditae:item.platinum_sword",
		"hbm:item.titanium_sword",
		"minecraft:diamond_sword",
		"hbm:item.cobalt_sword",
		"hbm:item.starmetal_sword",
		"hbm:item.cmb_sword",
		"resadditae:item.ancient_gem_sword"
	};
	
	private static final String[] HOES = {
		"minecraft:wooden_hoe",
		"minecraft:stone_hoe",
		"minecraft:iron_hoe",
		"hbm:item.steel_hoe",
		"resadditae:item.silver_hoe",
		"minecraft:golden_hoe",
		"resadditae:item.platinum_hoe",
		"hbm:item.titanium_hoe",
		"minecraft:diamond_hoe",
		"hbm:item.cobalt_hoe",
		"hbm:item.starmetal_hoe",
		"hbm:item.cmb_hoe",
		"resadditae:item.ancient_gem_hoe"
	};
	
	private static final String[] SCYTHES = {
		"resadditae:item.wood_scythe",
		"resadditae:item.stone_scythe",
		"resadditae:item.iron_scythe",
		"resadditae:item.steel_scythe",
		"resadditae:item.silver_scythe",
		"resadditae:item.gold_scythe",
		"resadditae:item.platinum_scythe",
		"resadditae:item.titanium_scythe",
		"resadditae:item.diamond_scythe",
		"resadditae:item.cobalt_scythe",
		"resadditae:item.starmetal_scythe",
		"resadditae:item.cmb_scythe",
		"resadditae:item.ancient_gem_scythe"
	};
	
	private static final String[] HALBERDS = {
		"resadditae:item.wood_halberd",
		"resadditae:item.stone_halberd",
		"resadditae:item.iron_halberd",
		"resadditae:item.steel_halberd",
		"resadditae:item.silver_halberd",
		"resadditae:item.gold_halberd",
		"resadditae:item.platinum_halberd",
		"resadditae:item.titanium_halberd",
		"resadditae:item.diamond_halberd",
		"resadditae:item.cobalt_halberd",
		"resadditae:item.starmetal_halberd",
		"resadditae:item.cmb_halberd",
		"resadditae:item.ancient_gem_scythe"
	};
	
	private static void writeLists(ArrayList<Item> list, Map<Tiers, Item> map, String[] ids)
	{
		int i = 0;
		for (String id : ids) {
			UniqueIdentifier uidr = new UniqueIdentifier(id);
			Item item = GameRegistry.findItem(uidr.modId, uidr.name);
			if (item != null) {
				list.add(item);
			}
			map.put(Tiers.values()[i], item);
			i++;
		}
	}
	
	public static void init()
	{
		writeLists(boots, boots_map, BOOTS);
		writeLists(leggings, leggings_map, LEGGINGS);
		writeLists(chestplates, chestplate_map, CHESTPLATES);
		writeLists(helmets, helmet_map, HELMETS);
		
		writeLists(pickaxes, pickaxe_map, PICKAXES);
		writeLists(axes, axe_map, AXES);
		writeLists(shovels, shovel_map, SHOVELS);
		writeLists(swords, sword_map, SWORDS);
		writeLists(hoes, hoe_map, HOES);
		
		writeLists(scythes, scythe_map, SCYTHES);
		writeLists(halberds, halberd_map, HALBERDS);
	}
	
	public static ArrayList<Item> getViableItems(Map<Tiers, Item> map, Tiers min_tier, Tiers max_tier)
	{
		ArrayList<Item> viable_items = new ArrayList<Item>();
		for (int t = min_tier.ordinal(); t <= max_tier.ordinal(); t++) {
			Item item = map.get(Tiers.values()[t]);
			if (item != null) {
				viable_items.add(item);
			}
		}
		return viable_items;
	}
	
	public static Item getRandomItem(Map<Tiers, Item> map, Tiers min_tier, Tiers max_tier, double exp)
	{
		ArrayList<Item> viable_items = getViableItems(map, min_tier, max_tier);
		double d = Math.pow(rand.nextDouble(), exp) * (viable_items.size() - 1);
		return viable_items.get((int) d);
	}
	
	public static ItemStack getRandomEnchantedItem(Map<Tiers, Item> map, Tiers min_tier, Tiers max_tier, double exp, int ench_lvl)
	{
		Item item = getRandomItem(map, min_tier, max_tier, exp);
		ItemStack stack = new ItemStack(item);
		EnchantmentHelper.addRandomEnchantment(rand, stack, ench_lvl);
		return stack;
	}
	
	public static void applyRandomArmor(EntityLiving entity, Tiers min_tier, Tiers max_tier, double exp, double thr, int ench_lvl)
	{
		ArrayList<Item> viable_boots = getViableItems(boots_map, min_tier, max_tier);
		ArrayList<Item> viable_leggings = getViableItems(leggings_map, min_tier, max_tier);
		ArrayList<Item> viable_chestplates = getViableItems(chestplate_map, min_tier, max_tier);
		ArrayList<Item> viable_helmets = getViableItems(helmet_map, min_tier, max_tier);
		ItemStack[] equipment = new ItemStack[4];
		
		double d0 = Math.pow(rand.nextDouble(), exp);
		double d;
		d = Math.min(d0 + (rand.nextDouble() * 0.2) - 0.1, 1) * (viable_boots.size() - 1);
		equipment[0] = new ItemStack(viable_boots.get((int) d));
		d = Math.min(d0 + (rand.nextDouble() * 0.2) - 0.1, 1) * (viable_boots.size() - 1);
		equipment[1] = new ItemStack(viable_leggings.get((int) d));
		d = Math.min(d0 + (rand.nextDouble() * 0.2) - 0.1, 1) * (viable_boots.size() - 1);
		equipment[2] = new ItemStack(viable_chestplates.get((int) d));
		d = Math.min(d0 + (rand.nextDouble() * 0.2) - 0.1, 1) * (viable_boots.size() - 1);
		equipment[3] = new ItemStack(viable_helmets.get((int) d));
		
		if (ench_lvl > 0) {
			for (ItemStack stack : equipment) {
				EnchantmentHelper.addRandomEnchantment(rand, stack, ench_lvl);
			}
		}
		
		for (int i = 0; i < 4; i++) {
			if (rand.nextDouble() < thr) {
				continue;
			}
			entity.setCurrentItemOrArmor(i + 1, equipment[i]);
		}
	}
}
