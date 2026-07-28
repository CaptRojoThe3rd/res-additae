package com.captrojo.resadditae.creativetab;

import java.util.ArrayList;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabUtility
{
	public static ModCreativeTab tab;
	
	public static void create()
	{
		tab = new ModCreativeTab("resadditae.utility", Item.getItemFromBlock(ModBlocks.stonecutter));
		
		tab.add(ModBlocks.stonecutter);
		
		ModBlocks.multi_spawner.getSubBlocks(Item.getItemFromBlock(ModBlocks.multi_spawner), tab, tab.items);
		
		ModBlocks.snow_dungeon_spawner.getSubBlocks(Item.getItemFromBlock(ModBlocks.snow_dungeon_spawner), tab, tab.items);
		tab.add(ModBlocks.snow_dungeon_vault, 0, 1, 2, 3, 4, 5, 6, 7);
		
		for (int m : MultiItems.KEYS.metas) {
			tab.add(ModItems.keys, m);
		}
		
		ArrayList<ItemStack> vessels = new ArrayList<ItemStack>();
		ModItems.vessels.getSubItems(ModItems.vessels, null, vessels);
		for (ItemStack stack : vessels) {
			tab.add(stack);
		}
		
		tab.add(ModItems.structure_wand, 0, 1, 2);
		tab.add(ModBlocks.structure_block, 0, 1, 2, 3);
	}
}
