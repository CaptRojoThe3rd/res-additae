package com.captrojo.resadditae.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.tileentity.TEMultiSpawner;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class RenderTEMultiSpawner extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		renderSpinningEntity((TEMultiSpawner) te, x, y, z, f);
		GL11.glPopMatrix();
	}

	public static void renderSpinningEntity(TEMultiSpawner te, double x, double y, double z, float f)
	{
		Entity entity = te.render_entity;

		if (entity != null) {
			entity.setWorld(te.getWorldObj());
			
			float f1 = 0.4375F;
			GL11.glTranslatef(0.0f, 0.4f, 0.0f);
			GL11.glRotatef((float) (te.render_angle + f) * 40f, 0f, 1f, 0f);
			GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
			GL11.glTranslatef(0.0f, -0.4f, 0.0f);
			GL11.glScalef(f1, f1, f1);
			
			entity.setLocationAndAngles(x, y, z, 0.0f, 0.0f);
			RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0f, f);
		}
	}
}
