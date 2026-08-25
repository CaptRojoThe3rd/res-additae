package com.captrojo.resadditae.crafting;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.WoodTypes;
import com.captrojo.resadditae.block.ore.BlockOreFromDict;
import com.captrojo.resadditae.block.ore.BlockOreFromDict.FromOredictType;
import com.captrojo.resadditae.compatibility.CommonItems;
import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.config.common.CommonStuffConfig;
import com.captrojo.resadditae.item.Dyes;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItemStacks;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FurnaceRecipes
{
	public static void register()
	{
		for (WoodTypes type : WoodTypes.values()) {
			GameRegistry.addSmelting(type.getLog().stack(1), new ItemStack(Items.coal, 1, 1), 0.15f);
		}
		
		GameRegistry.addSmelting(ModBlocks.ore_coal, new ItemStack(Items.coal), 0.1f);
		GameRegistry.addSmelting(ModBlocks.ore_iron, new ItemStack(Items.iron_ingot), 0.7f);
		GameRegistry.addSmelting(ModBlocks.ore_gold, new ItemStack(Items.gold_ingot), 1.0f);
		GameRegistry.addSmelting(ModBlocks.ore_diamond, new ItemStack(Items.diamond), 1.0f);
		GameRegistry.addSmelting(ModBlocks.ore_redstone, new ItemStack(Items.redstone), 0.7f);
		GameRegistry.addSmelting(ModBlocks.ore_lapis, new ItemStack(Items.dye, 1, Dyes.M_BLUE_LAPIS), 0.2f);
		GameRegistry.addSmelting(ModBlocks.ore_emerald, new ItemStack(Items.emerald), 1.0f);
		
		GameRegistry.addSmelting(ModBlocks.ore_silver, MultiItemStacks.SILVER_INGOT.stack(1), 0.85f);
		GameRegistry.addSmelting(ModBlocks.ore_platinum, MultiItemStacks.PLATINUM_INGOT.stack(1), 1.15f);
		
		if (CommonStuffConfig.raw_silver) {
			GameRegistry.addSmelting(MultiItemStacks.SILVER_RAW.stack(1), MultiItemStacks.SILVER_INGOT.stack(1), 0.85f);
		}
		if (CommonStuffConfig.raw_platinum) {
			GameRegistry.addSmelting(MultiItemStacks.PLATINUM_RAW.stack(1), MultiItemStacks.PLATINUM_INGOT.stack(1), 1.15f);
		}
		
		for (BlockOreFromDict block : ModBlocks.oredict_ores) {
			if (block.isOreAvailable() && block.type == FromOredictType.SMELT_FOR_MATERIAL) {
				if (ModList.HBM_NTM.isLoaded() && block == ModBlocks.ore_aluminium) {
					continue;
				}
				
				ItemStack item = block.getMaterialItem(1);
				if (item == null) {
					ResAdditae.LOG.warn(String.format("Wasn't able to get smelting output for ore %s (%s)", block.getUnlocalizedName(), block.material_oredict_key));
					continue;
				}
				
				GameRegistry.addSmelting(block, item, 1.0f);
			}
		}
		
		GameRegistry.addSmelting(new ItemStack(Items.dye, 0), new ItemStack(ModItems.dye, 1, Dyes.JET_BLACK.item_meta), 1.0f);
		
		addToolBreakdowns(CommonItems.IRON_NUGGET.stack(1), 0.1f, ModItems.iron_scythe, ModItems.iron_halberd);
		addToolBreakdowns(new ItemStack(Items.gold_nugget), 0.1f, ModItems.gold_scythe, ModItems.gold_halberd);
		addToolBreakdowns(MultiItemStacks.SILVER_NUGGET.stack(1), 0.1f, ModItems.silver_pickaxe, ModItems.silver_axe, ModItems.silver_shovel, ModItems.silver_sword, ModItems.silver_hoe, ModItems.silver_scythe, ModItems.silver_halberd, ModItems.silver_helmet, ModItems.silver_chestplate, ModItems.silver_leggings, ModItems.silver_boots);
		addToolBreakdowns(MultiItemStacks.PLATINUM_NUGGET.stack(1), 0.1f, ModItems.platinum_pickaxe, ModItems.platinum_axe, ModItems.platinum_shovel, ModItems.platinum_sword, ModItems.platinum_hoe, ModItems.platinum_scythe, ModItems.platinum_halberd, ModItems.platinum_helmet, ModItems.platinum_chestplate, ModItems.platinum_leggings, ModItems.platinum_boots);
	}
	
	private static void addToolBreakdowns(ItemStack nugget, float xp, Item...tools)
	{
		for (Item tool : tools) {
			GameRegistry.addSmelting(tool, nugget, xp);
		}
	}
}
