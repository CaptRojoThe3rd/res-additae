package com.captrojo.resadditae.world.gen.feature.tree;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.WoodTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenTreePalm extends WorldGenAbstractTree
{
	private final Block wood_block;
	private final int wood_meta;
	private final Block leaf_block;
	private final int leaf_meta;
	
	private final int min_height;
	private final int max_height;
	
	private final Block sapling;

	public WorldGenTreePalm(boolean notify, WoodTypes wood_type, int min_height, int max_height)
	{
		super(notify);

		this.wood_block = wood_type.getLog().block;
		this.wood_meta = wood_type.getLog().meta;
		this.leaf_block = wood_type.getLeaves().block;
		this.leaf_meta = wood_type.getLeaves().meta;
		
		this.min_height = min_height;
		this.max_height = max_height;
		
		this.sapling = wood_type.getSapling().block;
	}
	
	public boolean isValidSoilBlock(World world, Block soil, int x, int y, int z)
	{
		return (!soil.canSustainPlant(world, x, y, z, ForgeDirection.UP, (BlockSapling) this.sapling));
	}

	public void buildBlock(World world, int x, int y, int z, Block block, int meta)
	{
		Block old = world.getBlock(x, y, z);
		if (world.isAirBlock(x, y, z) || old.isLeaves(world, x, y, z) || old instanceof BlockSapling) {
			world.setBlock(x, y, z, block, meta, 2);
		}
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		Block soil = world.getBlock(x, y, z);
		while (soil == Blocks.air || soil instanceof BlockSapling) {
			y--;
			soil = world.getBlock(x, y, z);
		}
		if (!this.isValidSoilBlock(world, soil, x, y, z)) {
			return false;
		}

		int height = this.min_height + world.rand.nextInt(this.max_height - this.min_height + 1);
		
		if (world.getBlock(x, y + height, z) != Blocks.air) {
			return false;
		}

		for (int h = 0; h < height; h++) {
			this.buildBlock(world, x, y + 1 + h, z, this.wood_block, this.wood_meta);
		}

		this.buildBlock(world, x, y + height + 2, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 1, y + height + 2, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height + 2, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height + 2, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height + 2, z + 1, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x, y + height + 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 1, y + height + 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 2, y + height + 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 3, y + height + 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height + 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 2, y + height + 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 3, y + height + 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height + 1, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height + 1, z - 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height + 1, z - 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height + 1, z + 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height + 1, z + 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height + 1, z + 3, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x - 1, y + height, z, this.wood_block, 0);
		this.buildBlock(world, x - 2, y + height, z, this.wood_block, 0);
		this.buildBlock(world, x + 1, y + height, z, this.wood_block, 0);
		this.buildBlock(world, x + 2, y + height, z, this.wood_block, 0);
		this.buildBlock(world, x, y + height, z - 1, this.wood_block, 0);
		this.buildBlock(world, x, y + height, z - 2, this.wood_block, 0);
		this.buildBlock(world, x, y + height, z + 1, this.wood_block, 0);
		this.buildBlock(world, x, y + height, z + 2, this.wood_block, 0);
		this.buildBlock(world, x - 3, y + height, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 4, y + height, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 5, y + height, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 3, y + height, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 4, y + height, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 5, y + height, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height, z - 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height, z - 4, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height, z - 5, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height, z + 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height, z + 4, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height, z + 5, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x - 1, y + height + 1, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 1, y + height, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height + 1, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 1, y + height + 1, z + 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 1, y + height, z + 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height + 1, z + 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height, z + 1, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x - 2, y + height + 1, z - 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 2, y + height + 1, z - 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 2, y + height + 1, z + 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 2, y + height + 1, z + 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 2, y + height, z - 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 2, y + height, z - 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 2, y + height, z + 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 2, y + height, z + 2, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x - 3, y + height, z - 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 3, y + height, z - 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 3, y + height, z + 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 3, y + height, z + 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 3, y + height - 1, z - 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 3, y + height - 1, z - 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 3, y + height - 1, z + 3, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 3, y + height - 1, z + 3, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x - 4, y + height - 1, z - 4, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 4, y + height - 1, z - 4, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 4, y + height - 1, z + 4, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 4, y + height - 1, z + 4, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x - 5, y + height - 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 5, y + height - 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height - 1, z - 5, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height - 1, z + 5, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 6, y + height - 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 6, y + height - 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height - 1, z - 6, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height - 1, z + 6, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x - 1, y + height - 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 2, y + height - 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height - 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 2, y + height - 1, z, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height - 1, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height - 1, z - 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height - 1, z + 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x, y + height - 1, z + 2, this.leaf_block, this.leaf_meta);

		this.buildBlock(world, x - 2, y + height, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 2, y + height, z + 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 2, y + height, z - 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 2, y + height, z + 1, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 1, y + height, z - 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height, z - 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x - 1, y + height, z + 2, this.leaf_block, this.leaf_meta);
		this.buildBlock(world, x + 1, y + height, z + 2, this.leaf_block, this.leaf_meta);

		return true;
	}
}
