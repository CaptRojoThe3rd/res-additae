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
	public void renderInventoryBlock(Block block, int meta, int model, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;

		for (int k = 0; k < 4; ++k) {
			float f2 = 0.125F;

			if (k == 0) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.0D, 0.0D, (double) (0.5F + f2), 1.0D, (double) (f2 * 2.0F));
			}

			if (k == 1) {
				renderer.setRenderBounds((double) (0.5F - f2), 0.0D, (double) (1.0F - f2 * 2.0F), (double) (0.5F + f2), 1.0D, 1.0D);
			}

			f2 = 0.0625F;

			if (k == 2) {
				renderer.setRenderBounds((double) (0.5F - f2), (double) (1.0F - f2 * 3.0F), (double) (-f2 * 2.0F), (double) (0.5F + f2), (double) (1.0F - f2), (double) (1.0F + f2 * 2.0F));
			}

			if (k == 3) {
				renderer.setRenderBounds((double) (0.5F - f2), (double) (0.5F - f2 * 3.0F), (double) (-f2 * 2.0F), (double) (0.5F + f2), (double) (0.5F - f2), (double) (1.0F + f2 * 2.0F));
			}

			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
			tessellator.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int model, RenderBlocks renderer)
	{
		BlockFence fence = (BlockFence) block;
		
		boolean flag = false;
		float f = 0.375F;
		float f1 = 0.625F;
		renderer.setRenderBounds((double) f, 0.0D, (double) f, (double) f1, 1.0D, (double) f1);
		renderer.renderStandardBlock(block, x, y, z);
		flag = true;
		boolean flag1 = false;
		boolean flag2 = false;

		if (fence.canConnectFenceTo(renderer.blockAccess, x - 1, y, z) || fence.canConnectFenceTo(renderer.blockAccess, x + 1, y, z)) {
			flag1 = true;
		}

		if (fence.canConnectFenceTo(renderer.blockAccess, x, y, z - 1) || fence.canConnectFenceTo(renderer.blockAccess, x, y, z + 1)) {
			flag2 = true;
		}

		boolean flag3 = fence.canConnectFenceTo(renderer.blockAccess, x - 1, y, z);
		boolean flag4 = fence.canConnectFenceTo(renderer.blockAccess, x + 1, y, z);
		boolean flag5 = fence.canConnectFenceTo(renderer.blockAccess, x, y, z - 1);
		boolean flag6 = fence.canConnectFenceTo(renderer.blockAccess, x, y, z + 1);

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
		renderer.field_152631_f = true;

		if (flag1) {
			renderer.setRenderBounds((double) f4, (double) f2, (double) f, (double) f5, (double) f3, (double) f1);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		if (flag2) {
			renderer.setRenderBounds((double) f, (double) f2, (double) f6, (double) f1, (double) f3, (double) f7);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		f2 = 0.375F;
		f3 = 0.5625F;

		if (flag1) {
			renderer.setRenderBounds((double) f4, (double) f2, (double) f, (double) f5, (double) f3, (double) f1);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		if (flag2) {
			renderer.setRenderBounds((double) f, (double) f2, (double) f6, (double) f1, (double) f3, (double) f7);
			renderer.renderStandardBlock(block, x, y, z);
			flag = true;
		}

		renderer.field_152631_f = false;
		block.setBlockBoundsBasedOnState(renderer.blockAccess, x, y, z);
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
