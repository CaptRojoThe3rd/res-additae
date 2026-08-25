package com.captrojo.resadditae.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum MultiItemStacks
{
	SILVER_INGOT(ModItems.ingots, 0),
	PLATINUM_INGOT(ModItems.ingots, 1),
	
	SILVER_NUGGET(ModItems.nuggets, 0),
	PLATINUM_NUGGET(ModItems.nuggets, 1),
	
	SILVER_RAW(ModItems.raws, 0),
	PLATINUM_RAW(ModItems.raws, 1),
	
	ANCIENT_GEM(ModItems.gems, 0);
	
	private ItemStack stack;
	
	private MultiItemStacks(Item item, int meta)
	{
		if (item == null) {
			throw new NullPointerException("MultiItemStacks was called too early");
		}
		this.stack = new ItemStack(item, 1, meta);
	}
	
	/**
	 * Only use to inform of item type and metadata.
	 * Don't use as an actual item stack.
	 */
	public ItemStack info()
	{
		return this.stack;
	}
	
	public ItemStack stack(int count)
	{
		ItemStack copy = this.stack.copy();
		copy.stackSize = count;
		return copy;
	}
}
