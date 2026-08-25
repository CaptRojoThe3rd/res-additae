package com.captrojo.resadditae.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public interface IItemWithSettings
{
	@SideOnly(Side.CLIENT)
	public GuiScreen getSettingsGui(EntityPlayer player);
}
