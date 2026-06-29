package com.captrojo.resadditae.item;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class DyeItemData implements IMultiItemData
{
	private static int[] metas;
	private static String[] names;
	private static String[] texture_names;
	private static IIcon[] textures;
	
	static
	{
		metas = new int[Dyes.values().length];
		names = new String[0x88];
		texture_names = new String[0x88];
		textures = new IIcon[0x88];
		
		for (int i = 0; i < Dyes.values().length; i++) {
			Dyes dye = Dyes.values()[i];
			metas[i] = dye.item_meta;
			names[dye.item_meta] = dye.name;
			texture_names[dye.item_meta] = dye.texture_name; 
		}
	}
	
	@Override
	public int[] getValidMetas()
	{
		return metas;
	}
	
	@Override
	public String[] getNames()
	{
		return names;
	}
	
	@Override
	public IIcon getIcon(int meta)
	{
		return textures[meta];
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		for (int m : this.metas) {
			textures[m] = reg.registerIcon(ResAdditae.ident(texture_names[m]));
		}
	}
}
