package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlockData;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockConcretePowder extends BlockFallingMulti
{
	public final Block conc0;
	public final Block conc1;
	
	public BlockConcretePowder(String name, IMultiBlockData block_data, Block conc0, Block conc1)
	{
		super(name, block_data);
		this.conc0 = conc0;
		this.conc1 = conc1;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		if (world.isRemote) {
			return;
		}
		
		Block[] blocks = new Block[6];
		blocks[0] = world.getBlock(x - 1, y, z);
		blocks[1] = world.getBlock(x + 1, y, z);
		blocks[2] = world.getBlock(x, y - 1, z);
		blocks[3] = world.getBlock(x, y + 1, z);
		blocks[4] = world.getBlock(x, y, z - 1);
		blocks[5] = world.getBlock(x, y, z + 1);
		for (Block block : blocks) {
			if (!(block == Blocks.water || block == Blocks.flowing_water)) {
				continue;
			}
			int meta = world.getBlockMetadata(x, y, z);
			world.setBlock(x, y, z, (meta & 0x8) == 0 ? this.conc0 : this.conc1, meta & 0x7, 3);
			return;
		}
	}
}
