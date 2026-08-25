package com.captrojo.resadditae.world.gen.feature.tree;

import com.captrojo.resadditae.block.WoodTypes;

import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class ModTrees
{
	public static WorldGenAbstractTree getGenFromWood(WoodTypes type)
	{
		switch (type) {
		case VIOLET:
			return violetGen(true);
		case CHESTNUT:
			return chestnutGen(true);
		case NETHER_PALM:
			return netherPalmGen(true);
		case THERMARBOL:
			return thermarbolGen(true);
		case ENCHANTED_ASH:
			return enchantedAshGen(true);
		case DEEPWOOD:
			return deepwoodGen(true);
		default:
			return null;
		}
	}
	
	public static WorldGenAbstractTree violetGen(boolean notify)
	{
		return new WorldGenTreeTallPine(notify, WoodTypes.VIOLET, 25, 35);
	}
	
	public static WorldGenAbstractTree chestnutGen(boolean notify)
	{
		return new WorldGenTreeLarge(notify, WoodTypes.CHESTNUT, 7, 18, 4, 1, 1.0d);
	}
	
	public static WorldGenAbstractTree netherPalmGen(boolean notify)
	{
		return new WorldGenTreeNetherPalm(notify, WoodTypes.NETHER_PALM, 7, 11);
	}
	
	public static WorldGenAbstractTree thermarbolGen(boolean notify)
	{
		return new WorldGenTreeThermarbol(notify, WoodTypes.THERMARBOL, 6, 16, 4, 1, 1.0d);
	}
	
	public static WorldGenAbstractTree enchantedAshGen(boolean notify)
	{
		return new WorldGenTreeLarge(notify, WoodTypes.ENCHANTED_ASH, 15, 20, 6, 2, 0.66d);
	}
	
	public static WorldGenAbstractTree deepwoodGen(boolean notify)
	{
		return new WorldGenTreeShrub(notify, WoodTypes.DEEPWOOD);
	}
}
