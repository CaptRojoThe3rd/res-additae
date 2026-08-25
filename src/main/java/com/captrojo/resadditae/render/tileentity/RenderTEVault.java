package com.captrojo.resadditae.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.tileentity.TEVault;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTEVault extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity te0, double x, double y, double z, float f)
	{
		TEVault te = (TEVault) te0;
		if (te.render_item == null) {
			return;
		}
		if (!te.hasLoot()) {
			return;
		}
		
		final float scale = 1.0f;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.4, z + 0.5);
		GL11.glScalef(scale, scale, scale);
		RenderManager.instance.renderEntityWithPosYaw(te.render_item, 0, 0, 0, 0, f);
		GL11.glPopMatrix();
	}
}
