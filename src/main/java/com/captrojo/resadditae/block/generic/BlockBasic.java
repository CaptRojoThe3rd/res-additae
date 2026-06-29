package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IBlockData;
import com.captrojo.resadditae.block.SlabAssociations;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBasic extends Block
{
	public final IBlockData data;
	
	public BlockBasic(String name, String texture_name, IBlockData data)
	{
		super(data.getMaterial());
		this.data = data;
		
		this.setBlockName(name);
		this.setBlockTextureName(ResAdditae.ident(texture_name));
		this.setCreativeTab(null);
		this.data.setBlockData(this);
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
		if (this.data.doesBlockShatter()) {
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
