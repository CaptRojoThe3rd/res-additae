package com.captrojo.resadditae.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler
{
	public static final int SPELL_MENU = 0x000;
	
	public static final int STONECUTTER = 0x100;
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		IGuiProvider prov = this.getProvider(id, player, world, x, y, z);
		if (prov != null) {
			return prov.getContainer(id, player, world, x, y, z);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		IGuiProvider prov = this.getProvider(id, player, world, x, y, z);
		if (prov != null) {
			return prov.getGui(id, player, world, x, y, z);
		}
		return null;
	}
	
	private IGuiProvider getProvider(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		if (id == SPELL_MENU) {
			return ProviderSpellMenu.instance;
		}
		
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof IGuiProvider) {
			return (IGuiProvider) te;
		}
		
		Block block = world.getBlock(x, y, z);
		if (block instanceof IGuiProvider) {
			return (IGuiProvider) block;
		}
		
		ItemStack held = player.getHeldItem();
		if (held != null && held.getItem() instanceof IGuiProvider) {
			return (IGuiProvider) held.getItem();
		}
		
		return null;
	}
}
