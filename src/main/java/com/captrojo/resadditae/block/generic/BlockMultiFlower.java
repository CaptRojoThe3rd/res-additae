package com.captrojo.resadditae.block.generic;

import java.util.List;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockMultiFlower extends BlockFlower implements IMultiBlock
{
	public final IMultiBlockData data;
	
	public BlockMultiFlower(String name, IMultiBlockData data, float size)
	{
		super(0);
		this.data = data;
		
		this.setBlockName(name);
		this.setCreativeTab(null);
		this.setBlockBounds(0.5f - size, 0.0f, 0.5f - size, 0.5f + size, size * 2, 0.5f + size);
		this.data.setBlockData(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.data.registerIcons(reg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.data.getIcon(side, this.getTextureIdx(meta));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int m : this.data.getValidMetas()) {
			list.add(new ItemStack(this, 1, m));
		}
	}

	@Override
	public int fixMeta(int meta)
	{
		return meta;
	}

	@Override
	public int getTextureIdx(int meta)
	{
		return meta;
	}

	@Override
	public IMultiBlockData getData(int meta)
	{
		return this.data;
	}
}
