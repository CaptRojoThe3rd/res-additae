package com.captrojo.resadditae.render.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.captrojo.resadditae.entity.EntityThrownHalberd;
import com.captrojo.resadditae.item.equipment.ItemHalberd;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderThrownHalberd extends Render
{
	private static final ResourceLocation[] TEXTURES = {
		ResAdditae.resource("textures/tools/wood_halberd.png"),
		ResAdditae.resource("textures/tools/stone_halberd.png"),
		ResAdditae.resource("textures/tools/copper_halberd.png"),
		ResAdditae.resource("textures/tools/iron_halberd.png"),
		ResAdditae.resource("textures/tools/gold_halberd.png"),
		ResAdditae.resource("textures/tools/diamond_halberd.png"),
		ResAdditae.resource("textures/tools/netherite_halberd.png"),
		
		ResAdditae.resource("textures/tools/silver_halberd.png"),
		ResAdditae.resource("textures/tools/platinum_halberd.png"),
		ResAdditae.resource("textures/tools/ancient_gem_halberd.png"),
		
		ResAdditae.resource("textures/tools/steel_halberd.png"),
		ResAdditae.resource("textures/tools/titanium_halberd.png"),
		ResAdditae.resource("textures/tools/cobalt_halberd.png"),
		ResAdditae.resource("textures/tools/starmetal_halberd.png"),
		ResAdditae.resource("textures/tools/cmb_halberd.png"),
	};
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		byte b = entity.getDataWatcher().getWatchableObjectByte(17);
		return TEXTURES[b];
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_)
	{
		EntityThrownHalberd entityhalberd = (EntityThrownHalberd) entity;
		
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * p_76986_9_ - 90.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_76986_9_ - 45f, 0.0f, 0.0f, 1.0f);
		GL11.glRotatef(0f, 1f, 0f, 0f);
		Tessellator tessellator = Tessellator.instance;
		byte b0 = 0;
		float scale = 0.125f;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float f11 = 0.0f - p_76986_9_;

		if (f11 > 0.0f) {
			float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
			GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
		}
		
		
		GL11.glRotatef(-45f, 0f, 1f, 0f);
		
		/* I don't pretend to know what the hell is going on */
		GL11.glRotatef(62.8318f, 1f, 0.314159f, 0f);
		
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(-5.0f, 0.0f, 5.0f);

		GL11.glRotatef(90.0f, 1f, 0f, 0f);
		GL11.glNormal3f(0.0f, 0.0f, scale);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-8.0d, -8.0d, 0.0d, 0d, 0d);
		tessellator.addVertexWithUV(8.0d, -8.0d, 0.0d, 1d, 0d);
		tessellator.addVertexWithUV(8.0d, 8.0d, 0.0d, 1d, 1d);
		tessellator.addVertexWithUV(-8.0d, 8.0d, 0.0d, 0d, 1d);
		tessellator.draw();
		
		GL11.glRotatef(-90f, 0f, 0f, 1f);
		GL11.glRotatef(180.0f, 1f, 0f, 0f);
		GL11.glNormal3f(0.0f, 0.0f, scale);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-8.0d, -8.0d, 0.0d, 0d, 0d);
		tessellator.addVertexWithUV(8.0d, -8.0d, 0.0d, 1d, 0d);
		tessellator.addVertexWithUV(8.0d, 8.0d, 0.0d, 1d, 1d);
		tessellator.addVertexWithUV(-8.0d, 8.0d, 0.0d, 0d, 1d);
		tessellator.draw();
		
		GL11.glRotatef(-45f, 0f, 0f, 1f);
		GL11.glRotatef(-45f, 0f, 1f, 0f);
		GL11.glRotatef(90.0f, 1f, 0f, 0f);
		GL11.glNormal3f(0.0f, 0.0f, scale);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-8.0d, -8.0d, 0.0d, 0d, 0d);
		tessellator.addVertexWithUV(8.0d, -8.0d, 0.0d, 1d, 0d);
		tessellator.addVertexWithUV(8.0d, 8.0d, 0.0d, 1d, 1d);
		tessellator.addVertexWithUV(-8.0d, 8.0d, 0.0d, 0d, 1d);
		tessellator.draw();
		
		GL11.glRotatef(180.0f, 1f, 0f, 0f);
		GL11.glRotatef(90f, 0f, 0f, 1f);
		GL11.glNormal3f(0.0f, 0.0f, scale);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-8.0d, -8.0d, 0.0d, 0d, 0d);
		tessellator.addVertexWithUV(8.0d, -8.0d, 0.0d, 1d, 0d);
		tessellator.addVertexWithUV(8.0d, 8.0d, 0.0d, 1d, 1d);
		tessellator.addVertexWithUV(-8.0d, 8.0d, 0.0d, 0d, 1d);
		tessellator.draw();

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}
}
