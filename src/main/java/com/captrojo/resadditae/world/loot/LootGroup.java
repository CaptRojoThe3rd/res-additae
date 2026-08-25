package com.captrojo.resadditae.world.loot;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class LootGroup
{
	public final ArrayList<LootPool> pools;
	
	public LootGroup()
	{
		this.pools = new ArrayList<LootPool>();
	}
	
	public LootGroup(NBTTagCompound tag)
	{
		this();
		this.loadFromNBT(tag);
	}
	
	public LootGroup loadFromNBT(NBTTagCompound tag)
	{
		this.pools.clear();
		
		NBTTagList list = tag.getTagList("Pools", NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); i++) {
			this.pools.add(new LootPool(list.getCompoundTagAt(i)));
		}
		
		return this;
	}
	
	public NBTTagCompound saveToNBT(NBTTagCompound nbt)
	{
		NBTTagList list = new NBTTagList();
		for (LootPool pool : this.pools) {
			list.appendTag(pool.saveToNBT());
		}
		nbt.setTag("Pools", list);
		
		return nbt;
	}
	
	private void putItemIntoInv(IInventory inv, ItemStack stack, Random rand)
	{
		for (int c = 0; c < 100; c++) {
			int s = rand.nextInt(inv.getSizeInventory());

			ItemStack existing = inv.getStackInSlot(s);
			if (existing == null) {
				inv.setInventorySlotContents(s, stack);
				return;
			}
			if (existing.isItemEqual(stack)) {
				int m = existing.getMaxStackSize() - existing.stackSize;
				if (stack.stackSize < m) {
					existing.stackSize += stack.stackSize;
					return;
				}
				existing.stackSize = existing.getMaxStackSize();
				stack.stackSize -= m;
			}
		}
	}
	
	public ItemStack[] getRandomItems(Random rand)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		for (LootPool pool : this.pools) {
			boolean[] expired = new boolean[pool.items.size()];
			int[] weight_points = new int[pool.items.size()];
			
			int w = 0;
			for (int p = 0; p < pool.items.size(); p++) {
				weight_points[p] = w;
				w += pool.items.get(p).weight;
			}
			
			for (int r = 0, z = 0; r < pool.rounds && z < 1000; z++) {
				int w1 = rand.nextInt(w);
				int i;
				for (i = 0; i < (weight_points.length - 1) && weight_points[i] < w1; i++);
				
				LootItem item = pool.items.get(i);
				if(!item.exists()) {
					continue;
				}
				
				if (item.expires) {
					if (expired[i]) {
						continue;
					}
					expired[i] = true;
				}
				ItemStack stack = item.generateItemStack(rand);
				stacks.add(stack);
				r++;
			}
		}
		
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
	
	public void fillWithLoot(IInventory inv, Random rand)
	{
		for (ItemStack stack : this.getRandomItems(rand)) {
			this.putItemIntoInv(inv, stack, rand);
		}
	}
}
