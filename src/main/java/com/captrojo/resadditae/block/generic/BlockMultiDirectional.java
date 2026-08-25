package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IDirectionalBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.render.block.BlockRenderIDs;
import com.captrojo.resadditae.render.block.RenderDirectionalBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMultiDirectional extends BlockMulti implements IDirectionalBlock
{
	public final boolean special_renderer;
	
	protected int[] dir_map = new int[] {0x0, 0x4, 0x8, 0xc};
	protected int dir_mask = 0xc;
	protected int dir_shift = 2;
	
	public BlockMultiDirectional(String name, IMultiBlockData block_data, boolean special_renderer)
	{
		super(name, block_data);
		this.special_renderer = special_renderer;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, this.dir_map[l] | stack.getItemDamage(), 2);
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
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.data.getIcon(side, this.fixMeta(meta));
	}
	
	@Override
	public int fixMeta(int meta)
	{
		return meta & 0x3;
	}
	
	@Override
	public int getTextureIdx(int meta)
	{
		return meta & 0x3;
	}
	
	@Override
	public int getDirection(IBlockAccess world, int x, int y, int z)
	{
		return (world.getBlockMetadata(x, y, z) & this.dir_mask) >> this.dir_shift;
	}
}
