package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.block.SlabAssociations;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockMultiWithDoubleSlab extends BlockMulti
{
	public BlockMultiWithDoubleSlab(String name, IMultiBlockData block_data)
	{
		super(name, block_data);
	}
	
	@Override
	public int fixMeta(int meta)
	{
		return meta & 0x7;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		if ((meta & 0x8) == 0) {
			return Item.getItemFromBlock(this);
		}
		return Item.getItemFromBlock(SlabAssociations.getSlabFromBlock(this));
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		if (this.data.doesBlockShatter(meta)) {
			return 0;
		}
		return ((meta & 0x8) == 0) ? 1 : 2;
	}
	
	@Override
	public boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return this.getItemDropped(world.getBlockMetadata(x, y, z), world.rand, 0);
	}
}
