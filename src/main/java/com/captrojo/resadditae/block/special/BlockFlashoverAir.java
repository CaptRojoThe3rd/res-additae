package com.captrojo.resadditae.block.special;

import java.util.Random;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFlashoverAir extends BlockAir
{
	private static final int[][] NEARBY_OFFS = {
		{-1, -1, -1},
		{-1, -1, 0},
		{-1, -1, 1},
		{-1, 0, -1},
		{-1, 0, 0},
		{-1, 0, 1},
		{-1, 1, -1},
		{-1, 1, 0},
		{-1, 1, 1},
		{0, -1, -1},
		{0, -1, 0},
		{0, -1, 1},
		{0, 0, -1},
		{0, 0, 1},
		{0, 1, -1},
		{0, 1, 0},
		{0, 1, 1},
		{1, -1, -1},
		{1, -1, 0},
		{1, -1, 1},
		{1, 0, -1},
		{1, 0, 0},
		{1, 0, 1},
		{1, 1, -1},
		{1, 1, 0},
		{1, 1, 1}
	};
	
	public BlockFlashoverAir()
	{
		super();
		this.setBlockName("flashover_air");
		this.setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == 0) {
			return;
		}
		
		int fo = 5;
		
		for (int[] offs : NEARBY_OFFS) {
			Block block = world.getBlock(offs[0] + x, offs[1] + y, offs[2] + z);
			if (block == Blocks.fire) {
				world.setBlock(x, y, z, Blocks.fire);
				for (int x0 = -fo; x0 <= fo; x0++) {
					for (int y0 = -fo; y0 <= fo; y0++) {
						for (int z0 = -fo; z0 <= fo; z0++) {
							if (rand.nextBoolean()) {
								continue;
							}
							Block block1 = world.getBlock(x + x0, y + y0, z + z0);
							if (block1 == this) {
								if (Math.abs(x0) == fo || Math.abs(y0) == fo || Math.abs(z0) == fo) {
									world.scheduleBlockUpdate(x + x0, y + y0, z + z0, this, 0);
								} else {
									world.setBlock(x + x0, y + y0, z + z0, Blocks.fire);
								}
							}
						}
					}
				}
				break;
			}
		}
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return 300;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return 100;
	}
}
