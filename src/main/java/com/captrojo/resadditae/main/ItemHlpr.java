package com.captrojo.resadditae.main;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.item.block.ItemBlockMulti;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHlpr
{
	public static void spawnEntityItem(ItemStack stack, World world, double x, double y, double z)
	{
		EntityItem ei = new EntityItem(world, x, y, z, stack);
		world.spawnEntityInWorld(ei);
	}
	
	public static void spawnEntityItemAt(ItemStack stack, Entity entity)
	{
		spawnEntityItem(stack, entity.worldObj, entity.posX, entity.posY, entity.posZ);
	}
	
	public static void spawnEntityItemAt(Item item, Entity entity)
	{
		spawnEntityItemAt(new ItemStack(item), entity);
	}
	
	public static void spawnEntityItemFromBlock(ItemStack stack, World world, int x, int y, int z)
	{
		EntityItem ei = new EntityItem(world, x + 0.5, y + 1.25, z + 0.5, stack);
		ei.motionX *= 0.25;
		ei.motionZ *= 0.25;
		world.spawnEntityInWorld(ei);
	}
	
	public static boolean checkWLooting(Random rand, float chance, int looting)
	{
		float f = chance * (looting + 1);
		return rand.nextFloat() <= f;
	}
	
	public static Block getBlockFromStack(ItemStack stack)
	{
		return Block.getBlockFromItem(stack.getItem());
	}

	public static void addItemDescription(ItemStack stack, List list)
	{
		Item item = stack.getItem();
		String unlocalized;
		if (item instanceof ItemBlockMulti) {
			unlocalized = ((ItemBlockMulti) item).getUnlocalizedName(stack, true);
		} else {
			unlocalized = item.getUnlocalizedName(stack);
		}
		
		String base = I18nHlpr.getf(unlocalized + ".desc");
		if (base.equals(unlocalized + ".desc")) return;
		
		String s = "";
		for (int i = 0; i < base.length(); i++) {
			if (base.charAt(i) == '\\') {
				if (base.charAt(i + 1) == 'n') {
					list.add(s);
					s = "";
					i++;
					continue;
				}
			}
			s += base.charAt(i);
		}
		list.add(s);
	}
}
