package com.captrojo.resadditae.block.special;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.material.BlockMaterials;
import com.captrojo.resadditae.render.block.BlockRenderIDs;
import com.captrojo.resadditae.tileentity.TEMossLayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMossLayer extends Block implements ITileEntityProvider, IShearable, IGrowable
{
	private static final String ICON_NAME = ResAdditae.ident("moss_layer");
	
	public static void placeOnAllSupportingSides(World world, int x, int y, int z, int thickness)
	{
		int sides = 0;
		for (int s = 0; s < 6; s++) {
			if (TEMossLayer.isSideSupportedAt(world, x, y, z, s)) {
				sides++;
			}
		}
		if (sides == 0) {
			return;
		}
		
		world.setBlock(x, y, z, ModBlocks.moss_layer, 0x4 | thickness, 0);
		ModBlocks.moss_layer.onBlockPlacedBy(world, x, y, z, null, null);
	}
	
	public BlockMossLayer()
	{
		super(BlockMaterials.SOFT_PLANT);
		
		this.setBlockName("moss_layer");
		this.setBlockTextureName(ICON_NAME);
		this.setHardness(0.0f);
		this.setResistance(0.0f);
		this.setStepSound(soundTypeGrass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return ICON_NAME;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		if (world.isRemote) {
			return;
		}
		
		TileEntity te0 = world.getTileEntity(x, y, z);
		if (te0 == null || !(te0 instanceof TEMossLayer)) {
			return;
		}
		TEMossLayer te = (TEMossLayer) te0;
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if (meta == 0) {
			return;
		}
		
		if ((meta & 0x8) != 0) {
			te.addLayer(meta & 0x7);
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
			return;
		}
		
		if ((meta & 0x4) != 0) {
			for (int side = 0; side < 6; side++) {
				if (TEMossLayer.isSideSupportedAt(world, x, y, z, side)) {
					te.setLayersAt(side, meta & 0x3);
				}
			}
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
			return;
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		if (world.isRemote) {
			return;
		}
		
		TileEntity te0 = world.getTileEntity(x, y, z);
		if (te0 == null || !(te0 instanceof TEMossLayer)) {
			return;
		}
		TEMossLayer te = (TEMossLayer) te0;
		
		int sides = 0;
		for (int side = 0; side < 6; side++) {
			if (te.isSideSupported(side) && te.layer_counts[side] > 0) {
				sides++;
				continue;
			}
			te.layer_counts[side] = 0;
		}
		
		if (sides == 0) {
			world.setBlock(x, y, z, Blocks.air, 0, 3);
			return;
		}
		te.triggerUpdate();
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		TileEntity te0 = world.getTileEntity(x, y, z);
		if (te0 == null || !(te0 instanceof TEMossLayer)) {
			this.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
		}
		TEMossLayer te = (TEMossLayer) te0;
		
		int side = 0, sides = 0;
		for (int s = 0; s < 6; s++) {
			if (te.layer_counts[s] > 0) {
				side = s;
				sides++;
			}
		}
		if (sides != 1) {
			this.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
			return;
		}
		
		switch (side) {
		case 0:
			this.setBlockBounds(0f, 0.75f, 0f, 1f, 1f, 1f);
			break;
		case 1:
			this.setBlockBounds(0f, 0f, 0f, 1f, 0.25f, 1f);
			break;
		case 2:
			this.setBlockBounds(0f, 0f, 0.75f, 1f, 1f, 1f);
			break;
		case 3:
			this.setBlockBounds(0f, 0f, 0f, 1f, 1f, 0.25f);
			break;
		case 4:
			this.setBlockBounds(0.75f, 0f, 0f, 1f, 1f, 1f);
			break;
		case 5:
			this.setBlockBounds(0f, 0f, 0f, 0.25f, 1f, 1f);
			break;
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		return 0;
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
	public int getRenderType()
	{
		return BlockRenderIDs.MOSS_LAYER.id;
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return 60;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return 30;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEMossLayer();
	}

	@Override
	public boolean isShearable(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack stack, IBlockAccess world, int x, int y, int z, int fortune)
	{
		TileEntity te0 = world.getTileEntity(x, y, z);
		if (te0 == null || !(te0 instanceof TEMossLayer)) {
			return null;
		}
		TEMossLayer te = (TEMossLayer) te0;
		
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		for (int s = 0; s < 6; s++) {
			for (int c = 0; c < te.layer_counts[s]; c++) {
				list.add(new ItemStack(this));
			}
		}
		return list;
	}

	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean remote)
	{
		return true;
	}

	@Override
	public boolean func_149852_a(World world, Random rand, int x, int y, int z)
	{
		return true;
	}

	@Override
	public void func_149853_b(World world, Random rand, int x, int y, int z)
	{
		TileEntity te0 = world.getTileEntity(x, y, z);
		if (te0 == null || !(te0 instanceof TEMossLayer)) {
			return;
		}
		TEMossLayer te = (TEMossLayer) te0;
		
		for (int q = 0, i = 0; q < 50 && i < 3; q++) {
			int rside = rand.nextInt(6);
			if (te.addLayer(rside)) {
				i++;
				continue;
			}
			int x1 = x + (rand.nextInt(3) - 1);
			int y1 = y + (rand.nextInt(3) - 1);
			int z1 = z + (rand.nextInt(3) - 1);
			if (world.getBlock(x1, y1, z1).isAir(world, x1, y1, z1)) {
				for (int q2 = 0; q2 < 5; q2++) {
					int nside = rand.nextInt(6);
					if (!TEMossLayer.isSideSupportedAt(world, x1, y1, z1, nside)) {
						continue;
					}
					world.setBlock(x1, y1, z1, this, 0x8 | nside, 2);
					this.onBlockPlacedBy(world, x1, y1, z1, null, null);
					break;
				}
			} else if (world.getBlock(x1, y1, z1) == this) {
				te0 = world.getTileEntity(x1, y1, z1);
				if (te0 == null || !(te0 instanceof TEMossLayer)) {
					return;
				}
				TEMossLayer te_a = (TEMossLayer) te0;
				for (int q2 = 0; q2 < 10; q2++) {
					int nside = rand.nextInt(6);
					if (te_a.layer_counts[nside] != 0) {
						continue;
					}
					te_a.addLayer(nside);
					break;
				}
			}
		}
	}
}
