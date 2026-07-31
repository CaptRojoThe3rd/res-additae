package com.captrojo.resadditae.block.generic;

import java.util.List;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.config.common.GeneralConfig;
import com.captrojo.resadditae.sounds.ModSounds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMultiLog extends BlockLog implements IMultiBlock
{
	public final IMultiBlockData data;

	public BlockMultiLog(String name, IMultiBlockData block_data)
	{
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
		return meta & 0x1;
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
	public int onBlockPlaced(World world, int x, int y, int z, int side, float sx, float sy, float sz, int meta)
	{
		if ((meta & 0xc) == 0xc) {
			return meta;
		}

		return super.onBlockPlaced(world, x, y, z, side, sx, sy, sz, meta);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float sx, float sy, float sz)
	{
		if (!GeneralConfig.strip_logs) {
			return false;
		}
		
		int meta = world.getBlockMetadata(x, y, z);
		if ((meta & 0x2) == 2) return false;

		if (player.getHeldItem() == null) {
			return false;
		}

		Item held = player.getHeldItem().getItem();
		if (held instanceof ItemAxe) {
			meta |= 0x2;
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			world.playSound((double) x + 0.5d, (double) y + 0.5d, (double) z + 0.5d, ModSounds.LOG_STRIPPED, 1.0f, 0.8f, true);
			return true;
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.data.registerIcons(reg);
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
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 12));
		list.add(new ItemStack(item, 1, 2));
		list.add(new ItemStack(item, 1, 14));
		if (this.data.getValidMetas().length > 1) {
			list.add(new ItemStack(item, 1, 1));
			list.add(new ItemStack(item, 1, 13));
			list.add(new ItemStack(item, 1, 3));
			list.add(new ItemStack(item, 1, 15));
		}
	}
}
