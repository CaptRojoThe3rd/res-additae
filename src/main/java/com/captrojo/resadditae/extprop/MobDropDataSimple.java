package com.captrojo.resadditae.extprop;

import java.util.Random;

import com.captrojo.resadditae.main.NBTHlpr;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MobDropDataSimple extends MobDropDataBase
{
	private ItemStack item;
	
	public MobDropDataSimple(ItemStack item, float drop_chance, boolean drop_chance_looting, int min_amount, int max_amount, float amount_skew, boolean amount_looting)
	{
		super(drop_chance, drop_chance_looting, min_amount, max_amount, amount_skew, amount_looting);
		this.item = item;
	}
	
	public MobDropDataSimple(NBTTagCompound tag)
	{
		super(tag);
		this.item = NBTHlpr.loadItemStackFromNBT(tag.getCompoundTag("item"));
	}
	
	@Override
	public NBTTagCompound saveToNBT(NBTTagCompound tag)
	{
		super.saveToNBT(tag);
		tag.setTag("item", NBTHlpr.saveItemStackToNBT(this.item));	
		return tag;
	}
	
	@Override
	protected ItemStack getItem(Random rand)
	{
		return this.item;
	}
}
