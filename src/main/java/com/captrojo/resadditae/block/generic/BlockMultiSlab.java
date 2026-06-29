package com.captrojo.resadditae.block.generic;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMultiSlab extends BlockSlab implements IMultiBlock
{
	public final IMultiBlockData data;
	
	public BlockMultiSlab(String name, IMultiBlockData block_data)
	{
		super(false, block_data.getMaterial());
		this.data = block_data;
		
		this.setBlockName(name);
		this.setCreativeTab(null);
		this.data.setBlockData(this);
	}

	@Override
	public String func_150002_b(int p_150002_1_)
	{
		return this.getUnlocalizedName();
	}
	
	@Override
	public IMultiBlockData getData(int meta)
	{
		return this.data;
	}
	
	public int fixMeta(int meta)
	{
		return meta & 0x7;
	}
	
	public int getTextureIdx(int meta)
	{
		return meta & 0x7;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.data.getHardness(meta);
	}
	
	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.data.getResistance(meta);
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return this.data.getFlammability(this.fixMeta(world.getBlockMetadata(x, y, z)));
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return this.data.getFireSpreadSpeed(this.fixMeta(world.getBlockMetadata(x, y, z)));
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		if (this.data.doesBlockShatter(meta)) {
			return 0;
		}
		return super.quantityDropped(meta, fortune, rand);
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return this.fixMeta(meta);
	}
	
	@Override
	public boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.data.getIcon(side, this.fixMeta(meta));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int m : this.data.getValidMetas()) {
			list.add(new ItemStack(item, 1, m));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemFromBlock(this);
	}
}
