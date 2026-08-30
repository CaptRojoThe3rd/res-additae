package com.captrojo.resadditae.compatibility.helper;

import java.lang.reflect.Method;

import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * There is a bunch of nonsense in here because I couldn't figure out how to get optional
 * dependencies working in Eclipse. So, we will just use this code until someone gets mad
 * enough to tell me how to fix it.
 */
public class EtFuturumHlpr
{
	public static void registerRecipes()
	{
		try {
			Class clazz = Class.forName("ganymedes01.etfuturum.recipes.SmithingTableRecipes");
			
			Method method_getInstance = clazz.getMethod("getInstance", new Class[] {});
			Object instance = method_getInstance.invoke(null);
			
			Method method_addRecipe = clazz.getMethod("addRecipe", new Class[] {
				ItemStack.class,
				Object.class,
				Object.class
			});
			
			method_addRecipe.invoke(instance, new ItemStack(ModItems.netherite_scythe), "ingotNetherite", new ItemStack(ModItems.diamond_scythe, 1, OreDictionary.WILDCARD_VALUE));
			method_addRecipe.invoke(instance, new ItemStack(ModItems.netherite_halberd), "ingotNetherite", new ItemStack(ModItems.diamond_halberd, 1, OreDictionary.WILDCARD_VALUE));
		} catch (Exception e) {
			ResAdditae.LOG.error("Couldn't register smithing table recipes with Et Futurum Requiem");
			e.printStackTrace();
		}
		
//		SmithingTableRecipes sm = SmithingTableRecipes.getInstance();
//		sm.addRecipe(new ItemStack(ModItems.netherite_scythe), "ingotNetherite", new ItemStack(ModItems.diamond_scythe, 1, OreDictionary.WILDCARD_VALUE));
//		sm.addRecipe(new ItemStack(ModItems.netherite_halberd), "ingotNetherite", new ItemStack(ModItems.diamond_halberd, 1, OreDictionary.WILDCARD_VALUE));
	}
}
