package com.captrojo.resadditae.container.slot;

import com.captrojo.resadditae.item.ModItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWand extends Slot
{
	public SlotWand(IInventory inventory, int slot, int x, int y)
	{
		super(inventory, slot, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (stack == null) {
			return false;
		}
		return stack.getItem() == ModItems.magic_wand;
	}
}
