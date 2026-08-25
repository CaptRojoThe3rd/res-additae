package com.captrojo.resadditae.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class IconHack implements IIcon
{
	public float umin;
	public float umax;
	public float vmin;
	public float vmax;
	
	public IconHack()
	{
	}
	
	public void set(float umin, float umax, float vmin, float vmax)
	{
		this.umin = umin;
		this.umax = umax;
		this.vmin = vmin;
		this.vmax = vmax;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getIconWidth()
	{
		return 16;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconHeight()
	{
		return 16;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinU()
	{
		return this.umin;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxU()
	{
		return this.umax;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getInterpolatedU(double d)
	{
		return this.umin + ((this.umax - this.umin) * ((float) d / 16.0f));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMinV()
	{
		return this.vmin;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getMaxV()
	{
		return this.vmax;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getInterpolatedV(double d)
	{
		return this.vmin + ((this.vmax - this.vmin) * ((float) d / 16.0f));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getIconName()
	{
		return null;
	}
}
