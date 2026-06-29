package com.captrojo.resadditae.creativetab;

import java.util.ArrayList;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.WoodTypes;
import com.captrojo.resadditae.block.generic.BlockMultiFlower;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TabNature
{
	public static ModCreativeTab tab;
	
	public static void create()
	{
		tab = new ModCreativeTab("resadditae.nature", WoodTypes.VIOLET.getSapling().stack(1).getItem());

		for (WoodTypes type : WoodTypes.values()) {
			tab.add(type.getSapling().stack(1));
		}
		for (WoodTypes type : WoodTypes.values()) {
			tab.add(type.getLeaves().stack(1));
		}

		ModBlocks.flowers_a.getSubBlocks(null, tab, tab.items);
		ModBlocks.depths_plants.getSubBlocks(null, tab, tab.items);
	}
}
