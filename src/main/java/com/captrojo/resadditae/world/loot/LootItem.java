package com.captrojo.resadditae.world.loot;

import java.util.Random;

import com.captrojo.resadditae.util.NBTHlpr;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class LootItem
{
	public ItemStack item;
	public int weight;
	public int min_size;
	public int max_size;
	
	public double rand_exp;
	public boolean expires;
	
	public boolean is_enchanted;
	public byte enchant_level;
	
	public LootItem()
	{
	}
	
	public LootItem(ItemStack item, int weight, int min_size, int max_size)
	{
		this.item = item;
		this.weight = weight;
		this.min_size = min_size;
		this.max_size = max_size;
		
		this.rand_exp = 1.0;
		this.expires = false;
		
		this.is_enchanted = false;
		this.enchant_level = 0;
	}
	
	public LootItem(NBTTagCompound nbt)
	{
		this.loadFromNBT(nbt);
	}
	
	public LootItem loadFromNBT(NBTTagCompound nbt)
	{
		this.item = NBTHlpr.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
		this.weight = nbt.getInteger("Weight");
		this.min_size = nbt.getInteger("MinSize");
		this.max_size = nbt.getInteger("MaxSize");
		
		if (nbt.hasKey("RandExp")) {
			this.rand_exp = nbt.getFloat("RandExp");
		} else {
			this.rand_exp = 1.0;
		}
		
		this.expires = nbt.hasKey("Expires") && nbt.getBoolean("Expires");
		
		this.is_enchanted = nbt.hasKey("EnchLvl");
		this.enchant_level = nbt.getByte("EnchLvl");
		
		return this;
	}
	
	public NBTTagCompound saveToNBT(NBTTagCompound nbt)
	{
		NBTTagCompound itemtag = new NBTTagCompound();
		NBTHlpr.saveItemStackToNBT(this.item, itemtag);
		nbt.setTag("Item", itemtag);
		
		nbt.setInteger("Weight", this.weight);
		nbt.setInteger("MinSize", this.min_size);
		nbt.setInteger("MaxSize", this.max_size);
		
		if (this.rand_exp != 1.0) {
			nbt.setFloat("RandExp", (float) this.rand_exp);
		}
		
		if (this.expires) {
			nbt.setBoolean("Expires", this.expires);
		}
		
		if (this.is_enchanted) {
			nbt.setByte("EnchLvl", this.enchant_level);
		}
		
		return nbt;
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
