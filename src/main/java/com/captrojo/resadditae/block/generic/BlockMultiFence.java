package com.captrojo.resadditae.block.generic;

import java.util.List;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.render.block.RenderIDs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFence;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMultiFence extends BlockFence implements IMultiBlock
{
	public final IMultiBlockData data;
	
	public BlockMultiFence(String name, IMultiBlockData block_data)
	{
		super(null, block_data.getMaterial());
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
		return meta;
	}
	
	public int getTextureIdx(int meta)
	{
		return meta;
	}
	
	@Override
	public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z)
	{
		return super.canConnectFenceTo(world, x, y, z) || world.getBlock(x, y, z) instanceof BlockFence;
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
	public int damageDropped(int meta)
	{
		return this.fixMeta(meta);
	}
	
	@Override
	public int getRenderType()
	{
		return RenderIDs.MULTI_FENCE.id;
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
		for (int m : this.data.getValidMetas()) {
			list.add(new ItemStack(item, 1, m));
		}
	}
}
