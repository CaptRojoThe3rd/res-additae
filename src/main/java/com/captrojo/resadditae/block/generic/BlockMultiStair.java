package com.captrojo.resadditae.block.generic;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.render.block.BlockRenderIDs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMultiStair extends BlockStairs implements IMultiBlock
{
	public final IMultiBlockData data;
	public final int meta_0;
	public final int meta_1;
	public final int[] metas;
	
	public BlockMultiStair(String name, Block block, IMultiBlockData data, int meta_0, int meta_1, boolean use_meta_1)
	{
		super(block, use_meta_1 ? meta_1 : meta_0);
		this.data = data;
		this.meta_0 = meta_0 & 0x7;
		this.meta_1 = (meta_1 < 0) ? -1 : meta_1 & 0x7;
		if (use_meta_1) {
			this.metas = new int[] {8};
		} else if (this.meta_1 == -1) {
			this.metas = new int[] {0};
		} else {
			this.metas = new int[] {0, 8};
		}
		
		this.setBlockName(name);
		this.setCreativeTab(null);
		this.data.setBlockData(this);
	}

	public BlockMultiStair(String name, BlockMulti block, int meta_0, int meta_1, boolean use_meta_1)
	{
		this(name, block, block.data, meta_0, meta_1, use_meta_1);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int i1 = world.getBlockMetadata(x, y, z) & 0xc;

		if (l == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2 | i1, 2);
		}

		if (l == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 1 | i1, 2);
		}

		if (l == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3 | i1, 2);
		}

		if (l == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 0 | i1, 2);
		}
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hx, float hy, float hz, int meta)
	{
		return super.onBlockPlaced(world, x, y, z, side, hx, hy, hz, meta) | (meta & 0x8);
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
		return meta & 0x8;
	}
	
	@Override
	public boolean canSilkHarvest()
	{
		return true;
	}

	@Override
	public int getRenderType()
	{
		return BlockRenderIDs.MULTI_STAIR.id;
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
		return this.data.getIcon(side, this.fixMeta(meta));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		if (this.meta_0 != -1) {
			list.add(new ItemStack(item, 1, 0));
		}
		if (this.meta_1 != -1) {
			list.add(new ItemStack(item, 1, 8));
		}
	}

	@Override
	public int fixMeta(int meta)
	{
		return (meta >> 3) + this.meta_0;
	}
	
	@Override
	public int getTextureIdx(int meta)
	{
		return (meta >> 3) + this.meta_0;
	}
	
	@Override
	public IMultiBlockData getData(int meta)
	{
		return this.data;
	}
}
