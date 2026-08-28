package com.captrojo.resadditae.render.block;

import java.util.Random;

import com.captrojo.resadditae.render.DumbIcon;
import com.captrojo.resadditae.tileentity.TEMossLayer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderMossLayer implements ISimpleBlockRenderingHandler
{
	/*
	 * public static int BOTTOM = 0;
	 * public static int TOP = 1;
	 * public static int NORTH = 2;
	 * public static int SOUTH = 3;
	 * public static int WEST = 4;
	 * public static int EAST = 5;
	 */
	
	private static double of0 = 0.025;
	private static double of1 = 0.075;
	
	private Random texture_rand;
	private long texture_rand_seed;
	private DumbIcon iconhack;
	
	public RenderMossLayer()
	{
		this.texture_rand = new Random();
		this.texture_rand_seed = 1234567890;
		this.iconhack = new DumbIcon();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int model_id, RenderBlocks rb)
	{
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int model_id, RenderBlocks rb)
	{
		Tessellator ts = Tessellator.instance;
		
//		int color = world.getBiomeGenForCoords(x, z).getBiomeFoliageColor(x, y, z);
//		ts.setColorOpaque((color >> 16) & 0xff, (color >> 8) & 0xff, color & 0xff);
		ts.setColorOpaque(127, 127, 127);
		rb.setRenderBounds(0, 0, 0, 1, 1, 1);
		
		int meta = world.getBlockMetadata(x, y, z);
		TEMossLayer te = (TEMossLayer) world.getTileEntity(x, y, z);
		
		IIcon icon = block.getIcon(world, x, y, z, 0);
		float u0 = icon.getMinU();
		float u1 = icon.getInterpolatedU(8);
		float u2 = icon.getMaxU();
		float v0 = icon.getMinV();
		float v1 = icon.getInterpolatedV(8);
		float v2 = icon.getMaxV();
		float[] a_umin = {u0, u0, u1, u1};
		float[] a_umax = {u1, u1, u2, u2};
		float[] a_vmin = {v0, v1, v0, v1};
		float[] a_vmax = {v1, v2, v1, v2};
		
		this.texture_rand.setSeed((long) x + (long) y + (long) z + this.texture_rand_seed);
		
		for (int face = 0; face < 6; face++) {
			for (int m = 0; m < te.layer_counts[face]; m++) {
				int texture = this.texture_rand.nextInt(4);
				this.iconhack.set(a_umin[texture], a_umax[texture], a_vmin[texture], a_vmax[texture]);
				
				double off = of0 + (m * of1);
				
				switch (face) {
				case 0:
					rb.renderFaceYNeg(block, x, y - off + 1, z, this.iconhack);
					break;
				case 1:
					rb.renderFaceYPos(block, x, y + off - 1, z, this.iconhack);
					break;
				case 2:
					rb.renderFaceZNeg(block, x, y, z - off + 1, this.iconhack);
					break;
				case 3:
					rb.renderFaceZPos(block, x, y, z + off - 1, this.iconhack);
					break;
				case 4:
					rb.renderFaceXNeg(block, x - off + 1, y, z, this.iconhack);
					break;
				case 5:
					rb.renderFaceXPos(block, x + off - 1, y, z, this.iconhack);
					break;
				}
			}
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		return BlockRenderIDs.MOSS_LAYER.id;
	}
}
