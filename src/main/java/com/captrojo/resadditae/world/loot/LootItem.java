package com.captrojo.resadditae.world.loot;

import java.util.Random;

import com.captrojo.resadditae.main.NBTHlpr;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LootItem
{
	public final ItemStack item;
	public final int weight;
	public final int min_size;
	public final int max_size;
	public final double rand_exp;
	public final boolean expires;
	
	public boolean is_enchanted;
	public byte enchant_level;
	
	public LootItem(ItemStack item, int weight, int min_size, int max_size, double rand_exp, boolean expires)
	{
		this.item = item;
		this.weight = weight;
		this.min_size = min_size;
		this.max_size = max_size;
		this.rand_exp = rand_exp;
		this.expires = expires;
		
		this.is_enchanted = false;
		this.enchant_level = 0;
	}
	
	public LootItem(NBTTagCompound tag)
	{
		this.item = NBTHlpr.loadItemStackFromNBT(tag.getCompoundTag("item"));
		this.weight = tag.getInteger("weight");
		this.min_size = tag.getInteger("min_size");
		this.max_size = tag.getInteger("max_size");
		this.rand_exp = tag.getDouble("rand_exp");
		this.expires = tag.getBoolean("expires");
		
		this.is_enchanted = tag.hasKey("enchant_level");
		this.enchant_level = tag.getByte("enchant_level");
	}
	
	public NBTTagCompound saveToNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		NBTTagCompound itemtag = new NBTTagCompound();
		NBTHlpr.saveItemStackToNBT(this.item, itemtag);
		tag.setTag("item", itemtag);
		
		tag.setInteger("weight", this.weight);
		tag.setInteger("min_size", this.min_size);
		tag.setInteger("max_size", this.max_size);
		tag.setDouble("rand_exp", this.rand_exp);
		tag.setBoolean("expires", this.expires);
		
		if (this.is_enchanted) {
			tag.setByte("enchant_level", this.enchant_level);
		}
		
		return tag;
	}
	
	public ItemStack generateItemStack(Random rand)
	{
		if (this.item == null) {
			return null;
		}
		
		ItemStack stack = this.item.copy();
		stack.stackSize = this.min_size;
		
		if (this.max_size > this.min_size) {
			double m = Math.pow(rand.nextDouble(), this.rand_exp);
			stack.stackSize += (this.max_size - this.min_size) * m;
		}
		
		if (this.is_enchanted) {
			EnchantmentHelper.addRandomEnchantment(rand, stack, this.enchant_level);
		}
		
		return stack;
	}
	
	public boolean exists()
	{
		return this.item != null;
	}
}
