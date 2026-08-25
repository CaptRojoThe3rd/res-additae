package com.captrojo.resadditae.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IGuiProvider
{
	public Object getContainer(int id, EntityPlayer player, World world, int x, int y, int z);
	public Object getGui(int id, EntityPlayer player, World world, int x, int y, int z);
}
