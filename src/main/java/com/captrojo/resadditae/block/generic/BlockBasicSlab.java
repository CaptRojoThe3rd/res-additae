package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IBlockData;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockBasicSlab extends BlockSlab
{
	public final IBlockData data;
	
	public BlockBasicSlab(String name, String texture_name, IBlockData data)
	{
		super(false, data.getMaterial());
		this.data = data;
		
		this.setBlockName(name);
		this.setBlockTextureName(ResAdditae.ident(texture_name));
		this.setCreativeTab(null);
		this.data.setBlockData(this);
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		if (this.data.doesBlockShatter()) {
			return 0;
		}
		return super.quantityDropped(meta, fortune, rand);
	}
	
	@Override
	public boolean canSilkHarvest()
	{
		return true;
	}

	@Override
	public String func_150002_b(int p_150002_1_)
	{
		return this.getUnlocalizedName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemFromBlock(this);
	}
}
