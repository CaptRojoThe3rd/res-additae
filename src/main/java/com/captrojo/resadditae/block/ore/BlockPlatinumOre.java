package com.captrojo.resadditae.block.ore;

import java.util.Random;

import com.captrojo.resadditae.item.MultiItemStacks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockPlatinumOre extends BlockOreBase
{
	public BlockPlatinumOre()
	{
		super("platinum", OreStones.A_EARTH);
	}
	
	@Override
	public ItemStack itemDropped(int meta)
	{
		return MultiItemStacks.PLATINUM_RAW.info();
	}
}
