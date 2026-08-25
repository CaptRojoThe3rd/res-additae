package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IDirectionalBlock;
import com.captrojo.resadditae.block.IDoubleSlab;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.render.block.BlockRenderIDs;
import com.captrojo.resadditae.render.block.RenderDirectionalBlock;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMultiDirectionalSlab extends BlockMultiSlab implements IDirectionalBlock
{
	public final boolean special_renderer;
	
	protected int dir_mask = 0x6;
	protected int dir_shift = 1;
	
	public BlockMultiDirectionalSlab(String name, IMultiBlockData block_data, boolean special_renderer, IDoubleSlab double_slab)
	{
		super(name, block_data, double_slab);
		this.special_renderer = special_renderer;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		final int[] map = new int[] {0x0, 0x2, 0x4, 0x6};
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, map[l] | world.getBlockMetadata(x, y, z), 2);
	}
	
	@Override
	public int getRenderType()
	{
		if (this.special_renderer && !RenderDirectionalBlock.hack) {
			return BlockRenderIDs.DIRECTIONAL.id;
		}
		return super.getRenderType();
	}
	
	@Override
	public int fixMeta(int meta)
	{
		return meta & 0x1;
	}
	
	@Override
	public int getTextureIdx(int meta)
	{
		return meta & 0x1;
	}
	
	@Override
	public int getDirection(IBlockAccess world, int x, int y, int z)
	{
		return (world.getBlockMetadata(x, y, z) & this.dir_mask) >> this.dir_shift;
	}
}
