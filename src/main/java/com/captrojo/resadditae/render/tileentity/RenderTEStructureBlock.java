package com.captrojo.resadditae.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.tileentity.TEStructureBlock;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderTEStructureBlock extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity tile_entity, double x, double y, double z, float f)
	{
		if (!(tile_entity instanceof TEStructureBlock)) {
			return;
		}
		TEStructureBlock te = (TEStructureBlock) tile_entity;

		Tessellator ts = Tessellator.instance;
		final int meta = te.stored_block_meta;
		RenderBlocks rb = new RenderBlocks(te.getWorldObj()) {
			@Override
			public IIcon getBlockIcon(Block block, IBlockAccess world, int x, int y, int z, int side)
			{
				return this.getIconSafe(block.getIcon(side, meta));
			}
		};

		if (te.stored_block != null) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
			RenderHelper.disableStandardItemLighting();
			GL11.glPushMatrix();
			GL11.glTranslated(x + 0.25d, y + 0.25d, z + 0.25d);
			GL11.glScalef(0.5f, 0.5f, 0.5f);

			ts.startDrawingQuads();
			ts.setTranslation(-te.xCoord, -te.yCoord, -te.zCoord);

			rb.overrideBlockBounds(0d, 0d, 0d, 1d, 1d, 1d);
			rb.renderStandardBlockWithColorMultiplier(te.stored_block, te.xCoord, te.yCoord, te.zCoord, 1f, 1f, 1f);
			rb.unlockBlockBounds();

			ts.draw();
			
			ts.setTranslation(0d, 0d, 0d);
			GL11.glPopMatrix();
			RenderHelper.enableStandardItemLighting();
		}

		int te_block_meta = te.getBlockMetadata();
		if (te_block_meta > 0) {
			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
			RenderHelper.disableStandardItemLighting();
			GL11.glPushMatrix();
			GL11.glTranslated(x - 0.005d, y - 0.005d, z - 0.005d);
			GL11.glScaled(1.01d, 1.01d, 1.01d);
			
			ts.startDrawingQuads();
			ts.setTranslation(-te.xCoord, -te.yCoord, -te.zCoord);
			
			rb.overrideBlockBounds(0d, 0d, 0d, 1d, 1d, 1d);
			ts.setColorOpaque_F(1f, 1f, 1f);
			
			rb.setOverrideBlockTexture(ModBlocks.structure_block.number_textures[te.idx / 16]);
			rb.renderFaceZPos(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			rb.renderFaceZNeg(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			rb.renderFaceYPos(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			rb.renderFaceYNeg(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			rb.renderFaceXPos(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			rb.renderFaceXNeg(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			
			rb.setOverrideBlockTexture(ModBlocks.structure_block.number_textures[te.idx % 16]);
			ts.setTranslation(-te.xCoord + 0.25d, -te.yCoord, -te.zCoord);
			rb.renderFaceZPos(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			rb.renderFaceYPos(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			rb.renderFaceYNeg(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			ts.setTranslation(-te.xCoord - 0.25d, -te.yCoord, -te.zCoord);
			rb.renderFaceZNeg(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			ts.setTranslation(-te.xCoord, -te.yCoord, -te.zCoord - 0.25d);
			rb.renderFaceXPos(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			ts.setTranslation(-te.xCoord, -te.yCoord, -te.zCoord + 0.25d);
			rb.renderFaceXNeg(ModBlocks.structure_block, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, null);
			
			rb.unlockBlockBounds();
			
			ts.draw();
			
			ts.setTranslation(0d, 0d, 0d);
			GL11.glPopMatrix();
			RenderHelper.enableStandardItemLighting();
		}
	}
}
