package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockMultiDirectional extends BlockMulti
{
	public BlockMultiDirectional(String name, IMultiBlockData block_data)
	{
		super(name, block_data);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		final int[] map = new int[] {0, 4, 8, 12};
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, map[l] | stack.getItemDamage(), 2);
	}
	
	@Override
	public int fixMeta(int meta)
	{
		return meta & 0x3;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.data.getIcon(side, meta);
	}
}
