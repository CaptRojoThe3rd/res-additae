package com.captrojo.resadditae.world.loot;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants.NBT;

public class LootPool
{
	public final ArrayList<LootItem> items;
	public int rounds;
	
	public LootPool(int rounds)
	{
		this.items = new ArrayList<LootItem>();
		this.rounds = rounds;
	}
	
	public LootPool(NBTTagCompound tag)
	{
		this.items = new ArrayList<LootItem>();
		NBTTagList list = tag.getTagList("Items", NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); i++) {
			LootItem item = new LootItem(list.getCompoundTagAt(i));
			this.items.add(item);
		}
		this.rounds = tag.getInteger("Rounds");
	}
	
	public NBTTagCompound saveToNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		NBTTagList list = new NBTTagList();
		for (LootItem item : this.items) {
			list.appendTag(item.saveToNBT());
		}
		tag.setTag("Items", list);
		tag.setInteger("Rounds", this.rounds);
		
		return tag;
	}
}
