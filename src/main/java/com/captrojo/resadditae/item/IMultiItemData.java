package com.captrojo.resadditae.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IMultiItemData
{
	public int[] getValidMetas();
	public String[] getNames();
	
	public IIcon getIcon(int meta);
	public void registerIcons(IIconRegister reg);
}
