package com.captrojo.resadditae.render.block;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RenderMultiFence implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int meta, int model, RenderBlocks rb)
	{
		Tessellator ts = Tessellator.instance;

		for (int k = 0; k < 4; ++k) {
			float f2 = 0.125F;

			if (k == 0) {
				rb.setRenderBounds((double) (0.5F - f2), 0.0D, 0.0D, (double) (0.5F + f2), 1.0D, (double) (f2 * 2.0F));
			}

			if (k == 1) {
				rb.setRenderBounds((double) (0.5F - f2), 0.0D, (double) (1.0F - f2 * 2.0F), (double) (0.5F + f2), 1.0D, 1.0D);
			}

			f2 = 0.0625F;

			if (k == 2) {
				rb.setRenderBounds((double) (0.5F - f2), (double) (1.0F - f2 * 3.0F), (double) (-f2 * 2.0F), (double) (0.5F + f2), (double) (1.0F - f2), (double) (1.0F + f2 * 2.0F));
			}

			if (k == 3) {
				rb.setRenderBounds((double) (0.5F - f2), (double) (0.5F - f2 * 3.0F), (double) (-f2 * 2.0F), (double) (0.5F + f2), (double) (0.5F - f2), (double) (1.0F + f2 * 2.0F));
			}

			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			ts.startDrawingQuads();
			ts.setNormal(0.0F, -1.0F, 0.0F);
			rb.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(block, 0, meta));
			ts.draw();
			ts.startDrawingQuads();
			ts.setNormal(0.0F, 1.0F, 0.0F);
			rb.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(block, 1, meta));
			ts.draw();
			ts.startDrawingQuads();
			ts.setNormal(0.0F, 0.0F, -1.0F);
			rb.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(block, 2, meta));
			ts.draw();
			ts.startDrawingQuads();
			ts.setNormal(0.0F, 0.0F, 1.0F);
			rb.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(block, 3, meta));
			ts.draw();
			ts.startDrawingQuads();
			ts.setNormal(-1.0F, 0.0F, 0.0F);
			rb.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(block, 4, meta));
			ts.draw();
			ts.startDrawingQuads();
			ts.setNormal(1.0F, 0.0F, 0.0F);
			rb.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, rb.getBlockIconFromSideAndMetadata(block, 5, meta));
			ts.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}

		rb.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int model, RenderBlocks rb)
	{
		BlockFence fence = (BlockFence) block;
		
		boolean flag = false;
		float f = 0.375F;
		float f1 = 0.625F;
		rb.setRenderBounds((double) f, 0.0D, (double) f, (double) f1, 1.0D, (double) f1);
		rb.renderStandardBlock(block, x, y, z);
		flag = true;
		boolean flag1 = false;
		boolean flag2 = false;

		if (fence.canConnectFenceTo(rb.blockAccess, x - 1, y, z) || fence.canConnectFenceTo(rb.blockAccess, x + 1, y, z)) {
			flag1 = true;
		}

		if (fence.canConnectFenceTo(rb.blockAccess, x, y, z - 1) || fence.canConnectFenceTo(rb.blockAccess, x, y, z + 1)) {
			flag2 = true;
		}

		boolean flag3 = fence.canConnectFenceTo(rb.blockAccess, x - 1, y, z);
		boolean flag4 = fence.canConnectFenceTo(rb.blockAccess, x + 1, y, z);
		boolean flag5 = fence.canConnectFenceTo(rb.blockAccess, x, y, z - 1);
		boolean flag6 = fence.canConnectFenceTo(rb.blockAccess, x, y, z + 1);

		if (!flag1 && !flag2) {
			flag1 = true;
		}

		f = 0.4375F;
		f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = flag3 ? 0.0F : f;
		float f5 = flag4 ? 1.0F : f1;
		float f6 = flag5 ? 0.0F : f;
		float f7 = flag6 ? 1.0F : f1;
		rb.field_152631_f = true;

		if (flag1) {
			rb.setRenderBounds((double) f4, (double) f2, (double) f, (double) f5, (double) f3, (double) f1);
			rb.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		if (flag2) {
			rb.setRenderBounds((double) f, (double) f2, (double) f6, (double) f1, (double) f3, (double) f7);
			rb.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		f2 = 0.375F;
		f3 = 0.5625F;

		if (flag1) {
			rb.setRenderBounds((double) f4, (double) f2, (double) f, (double) f5, (double) f3, (double) f1);
			rb.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		if (flag2) {
			rb.setRenderBounds((double) f, (double) f2, (double) f6, (double) f1, (double) f3, (double) f7);
			rb.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		rb.field_152631_f = false;
		block.setBlockBoundsBasedOnState(rb.blockAccess, x, y, z);
		return flag;
	}

	@Override
	public boolean shouldRender3DInInventory(int model)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return BlockRenderIDs.MULTI_FENCE.id;
	}
}
