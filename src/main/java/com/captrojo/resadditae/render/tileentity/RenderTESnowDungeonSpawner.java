package com.captrojo.resadditae.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.tileentity.TESnowDungeonSpawner;
import com.captrojo.resadditae.tileentity.TESnowDungeonSpawner.State;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

public class RenderTESnowDungeonSpawner extends RenderTEMultiSpawner
{
	@Override
	public void renderTileEntityAt(TileEntity te0, double x, double y, double z, float f)
	{
		TESnowDungeonSpawner te = (TESnowDungeonSpawner) te0;
		State state = te.getState();
		if (state == State.INACTIVE) {
			renderBlockInside(te, Blocks.snow, x, y, z);
			return;
		}
		if (state == State.SPENT) {
			renderBlockInside(te, Blocks.coal_block, x, y, z);
			return;
		}
		
		super.renderTileEntityAt(te, x, y, z, f);
	}
	
	public static void renderBlockInside(TileEntity te, Block block, double x, double y, double z)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		Tessellator ts = Tessellator.instance;
		RenderBlocks rb = new RenderBlocks(te.getWorldObj());
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		RenderHelper.disableStandardItemLighting();
		
		ts.startDrawingQuads();
		ts.setTranslation(-te.xCoord, -te.yCoord, -te.zCoord);
		
		rb.setOverrideBlockTexture(block.getIcon(0, 0));
		rb.overrideBlockBounds(0.0625d, 0.0625d, 0.0625d, 0.9375d, 0.9375d, 0.9375d);
		rb.renderStandardBlockWithColorMultiplier(block, te.xCoord, te.yCoord, te.zCoord, 1f, 1f, 1f);
		rb.clearOverrideBlockTexture();
		rb.unlockBlockBounds();
		
		ts.draw();
		ts.setTranslation(0, 0, 0);
		
		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
	}
}
