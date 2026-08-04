package com.captrojo.resadditae.render;

import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class RenderHlpr
{
	private static final ResourceLocation MOD_ICONS = ResAdditae.resource("textures/gui/icons.png");
	private static final ResourceLocation MOD_PARTICLES = ResAdditae.resource("textures/particle/particles.png");
	
	public static final int SPELL_TEXTUREMAP_ID = 37;
	@SideOnly(Side.CLIENT)
	public static TextureMap texturemap_spells;
	
	private static double px_div_w;
	private static double px_div_h;
	
	public static double z_level;
	
	public static void bindTexture(Minecraft mc, ResourceLocation resource)
	{
		bindTexture(mc, resource, 256, 256);
	}
	
	public static void bindTexture(Minecraft mc, ResourceLocation resource, int w, int h)
	{
		mc.renderEngine.bindTexture(resource);
		px_div_w = 1d / (double) w;
		px_div_h = 1d / (double) h;
	}
	
	public static void bindModIcons(Minecraft mc)
	{
		bindTexture(mc, MOD_ICONS);
		px_div_w = (1d / 256d);
		px_div_h = (1d / 256d);
	}
	
	public static void bindModParticles(Minecraft mc)
	{
		bindTexture(mc, MOD_PARTICLES);
		px_div_w = (1d / 128d);
		px_div_h = (1d / 128d);
	}
	
	public static void bindVanillaParticles(Minecraft mc)
	{
		bindTexture(mc, EffectRenderer.particleTextures);
		px_div_w = (1d / 128d);
		px_div_h = (1d / 128d);
	}
	
	public static void bindItemTextureMap(Minecraft mc)
	{
		bindTexture(mc, TextureMap.locationItemsTexture);
	}
	
	public static void bindSpellTextureMap(Minecraft mc)
	{
		bindTexture(mc, mc.renderEngine.getResourceLocation(SPELL_TEXTUREMAP_ID));
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
	
	public static void drawTexturedModalRect(int x, int y, int w, int h, int u, int v, int uw, int vh)
	{
		Tessellator ts = Tessellator.instance;
		ts.startDrawingQuads();
		ts.addVertexWithUV((double) (x + 0), (double) (y + h), z_level, (double) (u + 0) * px_div_w, (double) (v + vh) * px_div_h);
	        ts.addVertexWithUV((double) (x + w), (double) (y + h), z_level, (double) (u + uw) * px_div_w, (double) (v + vh) * px_div_h);
	        ts.addVertexWithUV((double) (x + w), (double) (y + 0), z_level, (double) (u + uw) * px_div_w, (double) (v + 0) * px_div_h);
	        ts.addVertexWithUV((double) (x + 0), (double) (y + 0), z_level, (double) (u + 0) * px_div_w, (double) (v + 0) * px_div_h);
		ts.draw();
	}

	public static void drawTexturedModelRect(int x, int y, IIcon icon, int w, int h)
	{
		Tessellator ts = Tessellator.instance;
		ts.startDrawingQuads();
		ts.addVertexWithUV((double) (x + 0), (double) (y + h), (double) z_level, (double) icon.getMinU(), (double) icon.getMaxV());
		ts.addVertexWithUV((double) (x + w), (double) (y + h), (double) z_level, (double) icon.getMaxU(), (double) icon.getMaxV());
		ts.addVertexWithUV((double) (x + w), (double) (y + 0), (double) z_level, (double) icon.getMaxU(), (double) icon.getMinV());
		ts.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) z_level, (double) icon.getMinU(), (double) icon.getMinV());
		ts.draw();
	}
	
	public static IIcon createIcon(int u, int v, int w, int h, int map_w, int map_h)
	{
		float mw = 1f / (float) map_w;
		float mh = 1f / (float) map_h;
		float uf = (float) u * mw;
		float vf = (float) v * mh;
		float wf = (float) w * mw;
		float hf = (float) h * mh;
		IconHack icon = new IconHack();
		icon.set(uf, uf + wf, vf, vf + hf);
		return icon;
	}
	
	public static int getRainbowCycleColor(int ticks)
	{
		int r = 0;
		int g = 0;
		int b = 0;
		
		int step = (ticks / 256) % 5;
		int count = ticks & 0xff;
		
		if (step == 0) {
			r = 255;
			g = count;
		} else if (step == 1) {
			r = 255 - count;
			g = 255;
		} else if (step == 2) {
			g = 255;
			b = count;
		} else if (step == 3) {
			g = 255 - count;
			b = 255;
		} else if (step == 4) {
			b = 255;
			r = count;
		}
		
		return (r << 16) | (g << 8) | b;
	}
}
