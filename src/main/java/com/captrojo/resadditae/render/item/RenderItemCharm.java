package com.captrojo.resadditae.render.item;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.item.charm.ItemCharmBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemCharm implements IItemRenderer
{
	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data)
	{
		Minecraft mc = Minecraft.getMinecraft();
		TextureManager tm = mc.getTextureManager();
		RenderItem ri = new RenderItem();

		ri.renderItemIntoGUI(mc.fontRenderer, tm, stack, 0, 0);

		ItemCharmBase item = (ItemCharmBase) stack.getItem();
		double health = ((double) item.getCurrentCooldownTime(stack)) / ((double) item.cooldown_time);
		if (health == 0) {
			return;
		}
		
		int width = (int) Math.round(13 - health * 13);
		int health256 = (int) Math.round(255 - health * 255);
		int health64 = health256 / 4;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		Tessellator tessellator = Tessellator.instance;
		int color0 = (health64 << 16) | (health64 << 8) | 0x00007f;
		int color1 = 0xbfbfff;
		this.renderQuad(tessellator, 0 + 2, 0 + 10, 13, 2, 0x00003f);
		this.renderQuad(tessellator, 0 + 2, 0 + 10, 12, 1, color0);
		this.renderQuad(tessellator, 0 + 2, 0 + 10, width, 1, color1);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	private void renderQuad(Tessellator ts, int x, int y, int w, int h, int color)
	{
		ts.startDrawingQuads();
		ts.setColorOpaque_I(color);
		ts.addVertex((double) (x + 0), (double) (y + 0), 0.0D);
		ts.addVertex((double) (x + 0), (double) (y + h), 0.0D);
		ts.addVertex((double) (x + w), (double) (y + h), 0.0D);
		ts.addVertex((double) (x + w), (double) (y + 0), 0.0D);
		ts.draw();
	}
}
