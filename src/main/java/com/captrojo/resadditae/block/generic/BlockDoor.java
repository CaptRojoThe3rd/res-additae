package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IBlockData;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.item.Item;

public class BlockDoor extends net.minecraft.block.BlockDoor
{
	public final String name;
	public final IBlockData block_data;
	
	public BlockDoor(String name, IBlockData block_data, String texture)
	{
		super(block_data.getMaterial());
		this.name = name;
		this.block_data = block_data;
		
		this.setBlockName(name);
		this.setBlockTextureName(ResAdditae.ident(texture));
		this.setCreativeTab(null);
		this.block_data.setBlockData(this);
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}
}
