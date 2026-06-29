package com.captrojo.resadditae.block.ore;

import java.util.Random;

import com.captrojo.resadditae.block.ModBlocks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlockOreVanilla extends BlockOreBase
{
	private final ItemStack drop;
	
	public BlockOreVanilla(String name, ItemStack drop)
	{
		super(name, OreStones.A_VANILLA);
		this.drop = drop;
	}
	
	@Override
	public ItemStack itemDropped(int meta)
	{
		if (this.drop == null) {
			return new ItemStack(this, 1, meta);
		}
		return this.drop;
	}
	
	@Override
	public int quantityDropped(Random rand)
	{
		if (this == ModBlocks.ore_lapis) {
			return 4 + rand.nextInt(5);
		}
		return super.quantityDropped(rand);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		if (this.drop == null) {
			return 1;
		} else if (this.drop.getItem() == Items.redstone) {
			return 4 + rand.nextInt(2) + rand.nextInt(fortune + 1);
		}
		return super.quantityDropped(meta, fortune, rand);
	}
}
