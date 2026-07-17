package com.captrojo.resadditae.block.special;

import java.util.Random;

import com.captrojo.resadditae.block.IDirectionalBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.block.generic.BlockMulti;
import com.captrojo.resadditae.render.block.BlockRenderIDs;
import com.captrojo.resadditae.render.block.RenderDirectionalBlock;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGeodeShell extends BlockMulti implements IDirectionalBlock
{
	private Random render_rand = new Random();
	
	public BlockGeodeShell(String name, IMultiBlockData block_data)
	{
		super(name, block_data);
	}
	
	@Override
	public int getRenderType()
	{
		if (RenderDirectionalBlock.hack) {
			return 0;
		}
		return BlockRenderIDs.DIRECTIONAL.id;
	}

	@Override
	public int getDirection(IBlockAccess world, int x, int y, int z)
	{
		this.render_rand.setSeed(((long) x) * 12452745l + ((long) y) * 54179547l + ((long) z) * 691375913l + 12485942l);
		return this.render_rand.nextInt(4);
	}
}
