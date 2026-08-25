package com.captrojo.resadditae.block.generic;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMultiPillar extends BlockRotatedPillar implements IMultiBlock
{
	public final IMultiBlockData data;

	public BlockMultiPillar(String name, IMultiBlockData block_data)
	{
		super(block_data.getMaterial());
		this.data = block_data;

		this.setBlockName(name);
		this.setCreativeTab(null);
		this.data.setBlockData(this);
	}
	
	@Override
	public IMultiBlockData getData(int meta)
	{
		return this.data;
	}

	public int fixMeta(int meta)
	{
		return meta & 0x3;
	}
	
	public int getTextureIdx(int meta)
	{
		return meta & 0x3;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.data.getHardness(meta);
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double ex, double ey, double ez)
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
		if ((meta & 0xc) == 0xc) {
			return meta;
		}
		return meta & 0x3;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float sx, float sy, float sz, int meta)
	{
		if ((meta & 0xc) == 0xc) {
			return meta;
		}

		return super.onBlockPlaced(world, x, y, z, side, sx, sy, sz, meta);
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
		int rot = meta & 12;
		int type = meta & 3;
		return rot == 0 && (side == 1 || side == 0) ? this.getTopIcon(type)
			: (rot == 4 && (side == 5 || side == 4) ? this.getTopIcon(type)
			: (rot == 8 && (side == 2 || side == 3) ? this.getTopIcon(type)
			: (rot == 12) ? this.getSideIcon(meta)
			: this.getSideIconWithSide(side, meta)));
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getSideIconWithSide(int side, int meta)
	{
		if ((meta & 0xc) == 0x4) {
			side += 2;
		}
		if ((meta & 0xc) == 0x8) {
			if (side < 2) {
				side += 2;
			}
		}
		return this.data.getIcon(side, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getSideIcon(int meta)
	{
		return this.data.getIcon(2, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getTopIcon(int meta)
	{
		return this.data.getIcon(0, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int meta : this.data.getValidMetas()) {
			list.add(new ItemStack(item, 1, meta));
		}
	}
}
