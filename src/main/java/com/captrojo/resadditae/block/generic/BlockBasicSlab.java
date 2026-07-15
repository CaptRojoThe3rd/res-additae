package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IBlockData;
import com.captrojo.resadditae.block.IDoubleSlab;
import com.captrojo.resadditae.block.ISingleSlab;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockBasicSlab extends BlockSlab implements ISingleSlab
{
	public final IBlockData data;
	public final Block double_slab;
	
	public BlockBasicSlab(String name, String texture_name, IBlockData data, IDoubleSlab double_slab)
	{
		super(false, data.getMaterial());
		this.data = data;
		
		this.setBlockName(name);
		this.setBlockTextureName(ResAdditae.ident(texture_name));
		this.setCreativeTab(null);
		this.data.setBlockData(this);
		
		double_slab.setSlab(this);
		this.double_slab = double_slab.getDoubleSlab();
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

	@Override
	public Block getDoubleSlab()
	{
		return this.double_slab;
	}
}
