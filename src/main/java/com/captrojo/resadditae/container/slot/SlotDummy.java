package com.captrojo.resadditae.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDummy extends Slot
{
	public SlotDummy(int id, int x, int y)
	{
		super(null, id, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
	
	@Override
	public ItemStack getStack()
	{
		return null;
	}
	
	@Override
	public boolean getHasStack()
	{
		return false;
	}
	
	@Override
	public void putStack(ItemStack stack)
	{
	}
	
	@Override
	public void onSlotChanged()
	{
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
	
	@Override
	public ItemStack decrStackSize(int slot)
	{
		return null;
	}
	
	@Override
	public boolean isSlotInInventory(IInventory inv, int slot)
	{
		return false;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		return false;
	}
}
