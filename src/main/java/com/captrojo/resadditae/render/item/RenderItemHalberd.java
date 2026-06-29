package com.captrojo.resadditae.render.item;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.captrojo.resadditae.item.ItemActions;
import com.captrojo.resadditae.item.equipment.ItemHalberd;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemHalberd implements IItemRenderer
{
	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return (type == ItemRenderType.EQUIPPED) || 
			(type == ItemRenderType.EQUIPPED_FIRST_PERSON);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data)
	{
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();

		EntityPlayer player = null;
		for (Object obj : data) {
			if (obj instanceof EntityPlayer) {
				player = (EntityPlayer) obj;
			}
		}
		
		ResourceLocation texture = ((ItemHalberd) stack.getItem()).model_texture;

		texturemanager.bindTexture(texture);
		TextureUtil.func_152777_a(false, false, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		
		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
			GL11.glTranslatef(0.0f, -0.2f, 0.0f);
		} else if (type == ItemRenderType.EQUIPPED) {
			GL11.glTranslatef(0.6f, 0.0f, 0.0f);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
		}
		
		EnumAction action = stack.getItem().getItemUseAction(stack);
		
		if (player != null && player.isUsingItem()) {
			if (action == ItemActions.POINT_HALBERD) {
				if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
					GL11.glRotatef(75.0f, 1.0f, 0.0f, 0.0f);
					GL11.glRotatef(-40.0f, 0.0f, 0.0f, 1.0f);
				} else if (type == ItemRenderType.EQUIPPED) {
					GL11.glRotatef(75.0f, 1.0f, 0.0f, 0.0f);
					GL11.glRotatef(-40.0f, 0.0f, 0.0f, 1.0f);
					GL11.glRotatef(-15.0f, 1.0f, 0.0f, 1.0f);
					GL11.glTranslatef(-0.1f, 0.0f, 0.0f);
				}
			} else if (action == ItemActions.READY_HALBERD) {
				if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
					GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
					GL11.glTranslatef(-0.7f, 0.5f, 0.0f);
				}
			}
		}
		
		ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 32, 32, 0.03125F);

		if (stack.hasEffect(0)) {
			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);
			texturemanager.bindTexture(RES_ITEM_GLINT);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(768, 1, 1, 0);
			float f7 = 0.76F;
			GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			float f8 = 0.125F;
			GL11.glScalef(f8, f8, f8);
			float f9 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
			GL11.glTranslatef(f9, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(f8, f8, f8);
			f9 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
			GL11.glTranslatef(-f9, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
			GL11.glPopMatrix();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		texturemanager.bindTexture(texturemanager.getResourceLocation(stack.getItemSpriteNumber()));
		TextureUtil.func_147945_b();
	}
}
