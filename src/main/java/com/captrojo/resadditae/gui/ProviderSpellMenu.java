package com.captrojo.resadditae.gui;

import com.captrojo.resadditae.container.ContainerSpellMenu;
import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.gui.container.GuiSpellMenu;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ProviderSpellMenu implements IGuiProvider
{
	public static IGuiProvider instance = new ProviderSpellMenu();
	
	@Override
	public Object getContainer(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerSpellMenu(player.inventory, RAPlayerProperties.get(player));
	}

	@Override
	public Object getGui(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiSpellMenu(player.inventory, RAPlayerProperties.get(player));
	}
}
