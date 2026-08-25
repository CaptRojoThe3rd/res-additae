package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.MultiBlockStacks;
import com.captrojo.resadditae.compatibility.CommonItems;
import com.captrojo.resadditae.item.MultiItemStacks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMaterialPile extends BlockMulti
{
	public final int meta_offset;
	
	public BlockMaterialPile(String name, IMultiBlockData block_data, int meta_offset)
	{
		super(name, block_data);
		this.meta_offset = meta_offset;
		
		this.setCreativeTab(null);
	}
	
	public boolean placeLayer(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta >= 14) {
			return false;
		}
		world.setBlockMetadataWithNotify(x, y, z, meta + 2, 2);
		return true;
	}
	
	private void destroyBlockIfNeeded(World world, int x, int y, int z)
	{
		if (!canBlockStay(world, x, y, z)) {
			int meta = world.getBlockMetadata(x, y, z);
			this.dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlock(x, y, z, Blocks.air, 0, 3);
		}
	}
	
	@Override
	public int fixMeta(int meta)
	{
		return (meta & 0x1) + this.meta_offset;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		meta &= 0x1;
		
		if (MultiBlockStacks.IRON_PILE.isEqual(this, meta)) {
			return CommonItems.IRON_NUGGET.info().getItem();
		}
		if (MultiBlockStacks.GOLD_PILE.isEqual(this, meta)) {
			return Items.gold_nugget;
		}
		if (MultiBlockStacks.DIAMOND_PILE.isEqual(this, meta)) {
			return Items.diamond;
		}
		if (MultiBlockStacks.EMERALD_PILE.isEqual(this, meta)) {
			return Items.emerald;
		}
		if (MultiBlockStacks.SILVER_PILE.isEqual(this, meta)) {
			return MultiItemStacks.SILVER_NUGGET.info().getItem();
		}
		if (MultiBlockStacks.PLATINUM_PILE.isEqual(this, meta)) {
			return MultiItemStacks.PLATINUM_NUGGET.info().getItem();
		}
		if (MultiBlockStacks.ANCIENT_GEM_PILE.isEqual(this, meta)) {
			return MultiItemStacks.ANCIENT_GEM.info().getItem();
		}
		
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		return (meta & 0xe) + 2;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		int metaf = this.fixMeta(meta);
		if (this == ModBlocks.material_piles_vanilla_a || this == ModBlocks.material_piles_vanilla_b) {
			return 0;
		}
		return meta & 0x1;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z) & 0xe;
		double dx = (double) x;
		double dy = (double) y;
		double dz = (double) z;
		return AxisAlignedBB.getBoundingBox(
			dx + this.minX, dy + this.minY, dz + this.minZ, 
			dx + this.maxX, dy + (0.0625d * (float) meta), dz + this.maxZ
		);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		int meta = (world.getBlockMetadata(x, y, z) & 0xe) + 2;
		float height = 0.0625f * (float) meta;
		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, height, 1.0f);
	}
	
	@Override
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y - 1, z);
		int meta = world.getBlockMetadata(x, y - 1, z);
		return block.isOpaqueCube() || (block == this && meta >= 0xe);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return this.canBlockStay(world, x, y, z) || world.getBlock(x, y - 1, z) == this;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		destroyBlockIfNeeded(world, x, y, z);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		destroyBlockIfNeeded(world, x, y, z);	
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return side == 1 ? true : super.shouldSideBeRendered(world, x, y, z, side);
	}
	
	@Override
	public String getHarvestTool(int meta)
	{
		return "shovel";
	}
	
	@Override
	public int getHarvestLevel(int meta)
	{
		return 0;
	}
}
