package com.captrojo.resadditae.block.ore;

import com.captrojo.resadditae.compatibility.CommonItems;
import com.captrojo.resadditae.item.MultiItemStacks;

import net.minecraft.item.ItemStack;

public class BlockSilverOre extends BlockOreBase
{
	public BlockSilverOre()
	{
		super("silver", OreStones.A_EARTH);
	}
	
	@Override
	public ItemStack itemDropped(int meta)
	{
		return CommonItems.RAW_SILVER.info();
	}
}
