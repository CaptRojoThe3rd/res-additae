package com.captrojo.resadditae.block.generic;

import java.util.List;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMultiButton extends BlockButton implements IMultiBlock
{
	public final IMultiBlockData block_data;
	public final int meta;
	
	public BlockMultiButton(String name, IMultiBlockData block_data, int meta, boolean is_stone)
	{
		super(is_stone);
		this.block_data = block_data;
		this.meta = meta;
		
		this.setBlockName(name);
		this.setCreativeTab(null);
		this.block_data.setBlockData(this);
	}
	
	@Override
	public IMultiBlockData getData(int meta)
	{
		return this.block_data;
	}
	
	public int fixMeta(int meta)
	{
		return this.meta;
	}
	
	public int getTextureIdx(int meta)
	{
		return this.meta;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.block_data.getHardness(meta);
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double ex, double ey, double ez)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.block_data.getResistance(meta);
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.block_data.registerIcons(reg);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.block_data.getIcon(side, this.fixMeta(meta));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item, 1, 0));
	}
}
