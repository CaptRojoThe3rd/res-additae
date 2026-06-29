package com.captrojo.resadditae.render;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class RenderHlpr
{
	private static final ResourceLocation icons = ResAdditae.resource("textures/gui/icons.png");
	
	private static double px_div_w;
	private static double px_div_h;
	
	public static double z_level;
	
	public static void bindIcons(TextureManager tm)
	{
		tm.bindTexture(icons);
		px_div_w = (1d / 256d);
		px_div_h = (1d / 256d);
	}
	
	public static void drawTexturedModalRect(int x, int y, int u, int v, int w, int h)
	{
		Tessellator ts = Tessellator.instance;
		ts.startDrawingQuads();
		ts.addVertexWithUV((double) (x + 0), (double) (y + h), z_level, (double) (u + 0) * px_div_w, (double) (v + h) * px_div_h);
	        ts.addVertexWithUV((double) (x + w), (double) (y + h), z_level, (double) (u + w) * px_div_w, (double) (v + h) * px_div_h);
	        ts.addVertexWithUV((double) (x + w), (double) (y + 0), z_level, (double) (u + w) * px_div_w, (double) (v + 0) * px_div_h);
	        ts.addVertexWithUV((double) (x + 0), (double) (y + 0), z_level, (double) (u + 0) * px_div_w, (double) (v + 0) * px_div_h);
		ts.draw();
	}
}
