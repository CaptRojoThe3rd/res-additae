package com.captrojo.resadditae.compatibility;

import java.util.ArrayList;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.MultiBlockStacks;
import com.captrojo.resadditae.block.WoodTypes;
import com.captrojo.resadditae.block.ore.BlockOreFromDict;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItemStacks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDict
{
	public static void registerOres()
	{
		for (WoodTypes type : WoodTypes.values()) {
			OreDictionary.registerOre("logWood", type.getLog().stack(1));
			OreDictionary.registerOre("logWood", type.getStrippedLog().stack(1));
			OreDictionary.registerOre("plankWood", type.getPlanks().stack(1));
			OreDictionary.registerOre("slabWood", type.getSlab().stack(1));
			OreDictionary.registerOre("stairWood", type.getStair().stack(1));
		}
		
		if (CommonConfig.General.glass_more_colors) {
			for (int i = 0; i < 9; i++) {
				OreDictionary.registerOre("blockGlass", new ItemStack(ModBlocks.stained_glass[i], 1, OreDictionary.WILDCARD_VALUE));
			}
			for (int i = 0; i < 5; i++) {
				OreDictionary.registerOre("paneGlass", new ItemStack(ModBlocks.stained_glass_panes[i], 1, OreDictionary.WILDCARD_VALUE));
			}
		}
		
		if (CommonConfig.CommonStuff.iron_nuggets) {
			OreDictionary.registerOre("nuggetIron", ModItems.iron_nugget);
		}
		
		OreDictionary.registerOre("oreCoal", new ItemStack(ModBlocks.ore_coal, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("oreIron", new ItemStack(ModBlocks.ore_iron, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("oreGold", new ItemStack(ModBlocks.ore_gold, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("oreDiamond", new ItemStack(ModBlocks.ore_diamond, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("oreRedstone", new ItemStack(ModBlocks.ore_redstone, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("oreLapis", new ItemStack(ModBlocks.ore_lapis, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("oreEmerald", new ItemStack(ModBlocks.ore_emerald, 1, OreDictionary.WILDCARD_VALUE));
		
		OreDictionary.registerOre("blockSilver", MultiBlockStacks.SILVER_BLOCK.info());
		OreDictionary.registerOre("ingotSilver", MultiItemStacks.SILVER_INGOT.info());
		OreDictionary.registerOre("nuggetSilver", MultiItemStacks.SILVER_NUGGET.info());
		if (CommonConfig.CommonStuff.raw_silver) {
			OreDictionary.registerOre("oreSilver", MultiItemStacks.SILVER_RAW.info());
			OreDictionary.registerOre("rawSilver", MultiItemStacks.SILVER_RAW.info());
		}
		OreDictionary.registerOre("oreSilver", new ItemStack(ModBlocks.ore_silver, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("blockPlatinum", MultiBlockStacks.PLATINUM_BLOCK.info());
		OreDictionary.registerOre("ingotPlatinum", MultiItemStacks.PLATINUM_INGOT.info());
		OreDictionary.registerOre("nuggetPlatinum", MultiItemStacks.PLATINUM_NUGGET.info());
		if (CommonConfig.CommonStuff.raw_platinum) {
			OreDictionary.registerOre("orePlatinum", MultiItemStacks.PLATINUM_RAW.info());
			OreDictionary.registerOre("rawPlatinum", MultiItemStacks.PLATINUM_RAW.info());
		}
		OreDictionary.registerOre("orePlatinum", new ItemStack(ModBlocks.ore_platinum, 1, OreDictionary.WILDCARD_VALUE));
	}
	
	public static void registerModdedOres()
	{
		for (BlockOreFromDict block : ModBlocks.oredict_ores) {
			if (block.isOreAvailable()) {
				OreDictionary.registerOre(block.ore_oredict_key, new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
			}
		}
	}
	
	public static ItemStack getSingleFromOreDict(String key)
	{
		ArrayList<ItemStack> items = OreDictionary.getOres(key);
		
		for (String modid : CommonConfig.General.oredict_priority_list) {
			for (ItemStack item : items) {
				UniqueIdentifier uidr = GameRegistry.findUniqueIdentifierFor(item.getItem());
				if (uidr.modId.equals(modid)) {
					return item;
				}
			}
		}
		
		if (items.size() > 0) {
			return items.get(0);
		}
		return null;
	}
}
