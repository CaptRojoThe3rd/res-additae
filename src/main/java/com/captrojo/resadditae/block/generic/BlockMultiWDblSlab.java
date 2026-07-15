package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IDoubleSlab;
import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockMultiWDblSlab extends BlockMulti implements IDoubleSlab
{
	private Block slab;
	
	public BlockMultiWDblSlab(String name, IMultiBlockData block_data)
	{
		super(name, block_data);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		if ((meta & 0x8) == 0 || this.slab == null) {
			return Item.getItemFromBlock(this);
		}
		return Item.getItemFromBlock(this.slab);
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
		return this.getItemDropped(world.getBlockMetadata(x, y, z) & 0x7, world.rand, 0);
	}
	
	@Override
	public int fixMeta(int meta)
	{
		return meta & 0x7;
	}
	
	@Override
	public void setSlab(Block slab)
	{
		this.slab = slab;
	}

	@Override
	public Block getSingleSlab()
	{
		return this.slab;
	}
	
	@Override
	public Block getDoubleSlab()
	{
		return this;
	}
}
