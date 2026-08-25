package com.captrojo.resadditae.render.block;

import com.captrojo.resadditae.block.IDirectionalBlock;
import com.captrojo.resadditae.block.generic.BlockMultiDirectional;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderDirectionalBlock implements ISimpleBlockRenderingHandler
{
	public static boolean hack = false;
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int model_id, RenderBlocks rb)
	{
		hack = true;
		rb.renderBlockAsItem(block, meta, 1f);
		hack = false;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int model_id, RenderBlocks rb)
	{
		block.setBlockBoundsBasedOnState(world, x, y, z);

		int dir = ((IDirectionalBlock) block).getDirection(world, x, y, z);

		rb.uvRotateTop = dir > 1 ? 5 - dir : dir;
		rb.uvRotateNorth = dir > 0 ? (dir % 3) + 1 : 0;
		rb.uvRotateEast = dir < 2 ? 1 - dir : dir;
		int uvRotateSouthAndBottom = dir > 1 ? 2 * dir - 4 : 3 - 2 * dir;
		rb.uvRotateSouth = uvRotateSouthAndBottom;
		rb.uvRotateWest = dir > 1 ? 3 - dir : dir + 2;
		rb.uvRotateBottom = uvRotateSouthAndBottom;

		boolean flag = rb.renderStandardBlock(block, x, y, z);

		rb.uvRotateTop = 0;
		rb.uvRotateNorth = 0;
		rb.uvRotateEast = 0;
		rb.uvRotateSouth = 0;
		rb.uvRotateWest = 0;
		rb.uvRotateBottom = 0;

		return flag;
	}

	@Override
	public boolean shouldRender3DInInventory(int model_id)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return BlockRenderIDs.DIRECTIONAL.id;
	}
}
