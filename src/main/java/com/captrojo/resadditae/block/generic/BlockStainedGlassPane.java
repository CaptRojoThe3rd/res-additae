package com.captrojo.resadditae.block.generic;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockStainedGlassPane extends net.minecraft.block.BlockStainedGlassPane implements IMultiBlock
{
	public final IMultiBlock parent0;
	public final IMultiBlock parent1;

	public BlockStainedGlassPane(String name, IMultiBlock parent0, IMultiBlock parent1)
	{
		super();
		this.parent0 = parent0;
		this.parent1 = parent1;

		this.setBlockName(name);
		this.setCreativeTab(null);
		this.parent0.getData(0).setBlockData(this);
	}
	
	public IMultiBlockData getData(int meta)
	{
		return (meta >= 8 && this.parent1 != null) ? this.parent1.getData(meta & 0x7) : this.parent0.getData(meta & 0x7);
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
		return this.getData(meta).getHardness(meta & 0x7);
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double ex, double ey, double ez)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.getData(meta).getResistance(meta & 0x7);
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		if (this.getData(meta).doesBlockShatter(meta)) {
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
		return this.getData(meta).getIcon(side, meta & 0x7);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon func_150104_b(int meta)
	{
		return this.getIcon(0, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int m : this.parent0.getData(0).getValidMetas()) {
			list.add(new ItemStack(item, 1, m));
		}
		if (this.parent1 == null) {
			return;
		}
		for (int m : this.parent1.getData(0).getValidMetas()) {
			list.add(new ItemStack(item, 1, m + 8));
		}
	}
}
