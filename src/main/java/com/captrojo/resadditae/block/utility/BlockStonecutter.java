package com.captrojo.resadditae.block.utility;

import com.captrojo.resadditae.gui.GuiHandler;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.tileentity.TEStonecutter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockStonecutter extends Block implements ITileEntityProvider
{
	private IIcon texture_bottom;
	private IIcon texture_side_0;
	private IIcon texture_side_1;
	private IIcon texture_top;
	
	public BlockStonecutter()
	{
		super(Material.rock);
		
		this.setBlockName("stonecutter");
		this.setCreativeTab(null);
		this.setHardness(2.0f);
		this.setResistance(8.0f);
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta)
	{
		return new TEStonecutter();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEStonecutter();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hx, float hy, float hz)
	{
		if (!player.isSneaking()) {
			((TEStonecutter) world.getTileEntity(x, y, z)).selection = -1;
			player.openGui(ResAdditae.instance, GuiHandler.STONECUTTER, world, x, y, z);
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		final int[] map = new int[] {0, 1, 0, 1};
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, map[l] | stack.getItemDamage(), 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		texture_bottom = register.registerIcon(ResAdditae.ident("utility/stonecutter_bottom"));
		texture_side_0 = register.registerIcon(ResAdditae.ident("utility/stonecutter_side_0"));
		texture_side_1 = register.registerIcon(ResAdditae.ident("utility/stonecutter_side_1"));
		texture_top = register.registerIcon(ResAdditae.ident("utility/stonecutter_top"));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		switch (side) {
		default:
		case 0:
			return texture_bottom;
		case 1:
			return texture_top;
		case 2:
		case 3:
			return (meta == 0) ? texture_side_0 : texture_side_1;
		case 4:
		case 5:
			return (meta == 0) ? texture_side_1 : texture_side_0;
		}
	}
}
