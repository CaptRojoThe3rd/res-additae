package com.captrojo.resadditae.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

public class DumbIcon implements IIcon
{
	public static int _cur_tx_w;
	public static int _cur_tx_h;
	
	public static void setCurTxSize(int w, int h)
	{
		DumbIcon._cur_tx_w = w;
		DumbIcon._cur_tx_h = h;
	}
	
	public float umin;
	public float umax;
	public float vmin;
	public float vmax;
	public int width;
	public int height;
	
	public DumbIcon()
	{
	}
	
	public DumbIcon(int u, int v, int w, int h)
	{
		this(u, v, w, h, DumbIcon._cur_tx_w, DumbIcon._cur_tx_h);
	}
	
	public DumbIcon(int u, int v, int w, int h, int tx_w, int tx_h)
	{
		int u2 = u + w, v2 = v + h;
		float u_min = (float) u / (float) tx_w;
		float u_max = (float) u2 / (float) tx_w;
		float v_min = (float) v / (float) tx_h;
		float v_max = (float) v2 / (float) tx_h;
		this.set(u_min, u_max, v_min, v_max);
		this.width = w;
		this.height = h;
	}
	
	public DumbIcon set(float umin, float umax, float vmin, float vmax)
	{
		this.umin = umin;
		this.umax = umax;
		this.vmin = vmin;
		this.vmax = vmax;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getIconWidth()
	{
		return this.width;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getIconHeight()
	{
		return this.height;
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
