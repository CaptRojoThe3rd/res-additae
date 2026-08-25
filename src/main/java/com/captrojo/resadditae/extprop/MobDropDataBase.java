package com.captrojo.resadditae.extprop;

import java.util.Random;

import com.captrojo.resadditae.util.NBTHlpr;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class MobDropDataBase
{
	private double drop_chance;
	private boolean drop_chance_looting;
	
	private int min_amount;
	private int max_amount;
	private double amount_skew;
	private boolean amount_looting;
	
	public MobDropDataBase(float drop_chance, boolean drop_chance_looting, int min_amount, int max_amount, float amount_skew, boolean amount_looting)
	{
		this.drop_chance = drop_chance;
		this.drop_chance_looting = drop_chance_looting;
		
		this.min_amount = min_amount;
		this.max_amount = max_amount;
		this.amount_skew = amount_skew;
		this.amount_looting = amount_looting;
	}
	
	public MobDropDataBase(NBTTagCompound tag)
	{
		this.drop_chance = tag.getFloat("chance");
		this.drop_chance_looting = tag.getBoolean("chance_looting");
		
		this.min_amount = tag.getByte("min");
		this.max_amount = tag.getByte("max");
		this.amount_skew = tag.getFloat("skew");
		this.amount_looting = tag.getBoolean("looting");
	}
	
	public NBTTagCompound saveToNBT(NBTTagCompound tag)
	{
		tag.setFloat("chance", (float) this.drop_chance);
		tag.setBoolean("chance_looting", this.drop_chance_looting);
		
		tag.setByte("min", (byte) this.min_amount);
		tag.setByte("max", (byte) this.max_amount);
		tag.setFloat("skew", (float) this.amount_skew);
		tag.setBoolean("looting", this.amount_looting);
		
		return tag;
	}
	
	protected abstract ItemStack getItem(Random rand);
	
	public boolean shouldDrop(Random rand, int looting)
	{
		double d = this.drop_chance;
		if (this.drop_chance_looting) {
			d *= (looting + 1);
		}
		return this.drop_chance >= rand.nextFloat();
	}
	
	public ItemStack getDrop(Random rand, int looting)
	{
		ItemStack stack = this.getItem(rand).copy();
		double d = Math.pow(rand.nextDouble(), this.amount_skew);
		if (this.amount_looting) {
			d *= rand.nextDouble() * looting + 1;
		}
		stack.stackSize = (int) (d * (this.max_amount - this.min_amount)) + this.min_amount;
		return stack;
	}
}
