package com.captrojo.resadditae.world.gen.feature.tree;

import java.util.Random;

import com.captrojo.resadditae.block.WoodTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenTreeTallPine extends WorldGenAbstractTree
{
	private final Block wood_block;
	private final int wood_meta;
	private final Block leaf_block;
	private final int leaf_meta;
	
	private final int min_height;
	private final int max_height;
	
	public WorldGenTreeTallPine(boolean notify, WoodTypes wood_type, int min_height, int max_height)
	{
		super(notify);
		
		this.wood_block = wood_type.getLog().block;
		this.wood_meta = wood_type.getLog().meta;
		this.leaf_block = wood_type.getLeaves().block;
		this.leaf_meta = wood_type.getLeaves().meta;
		
		this.min_height = min_height;
		this.max_height = max_height;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int height = this.min_height + rand.nextInt(this.max_height - this.min_height);
		int current_len = (height - 10) / 5;
		int current_step = 8;
		int noleaf_buffer = 8;
		int leaf_width = 0;
		
		Block soil = world.getBlock(x, y - 1, z);
		if (!soil.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling)) {
			return false;
		}

		int h;
		for (h = 0; h < height; h++) {
			buildBlock(world, x, y + h, z, this.wood_block, this.wood_meta);
			
			if (h == current_step) {
				current_step += 6;
				if (current_len <= 2) {
					current_step--;
				}
				
				for (int l = current_len; l > 0; l--) {
					buildBlock(world, x - l, y + h, z, this.wood_block, this.wood_meta | 4);
					buildBlock(world, x + l, y + h, z, this.wood_block, this.wood_meta | 4);
					buildBlock(world, x, y + h, z - l, this.wood_block, this.wood_meta | 8);
					buildBlock(world, x, y + h, z + l, this.wood_block, this.wood_meta | 8);
				}
				
				current_len--;
				if (current_len == 1) {
					height = h + 5;
				}
			}
			
			if ((h & 0xfe) != h) {
				leaf_width--;
			} else if ((h & 0xfd) != h) {
				leaf_width--;
			}
			if (leaf_width == 0) {
				leaf_width++;
			}
			if (h == (current_step - 1)) {
				leaf_width = current_len + 2;
			}
			
			noleaf_buffer--;
			if (noleaf_buffer <= 0) {
				for (int lw = leaf_width; lw > 0; lw--) {
					int px = lw;
					int pz = 0;
					while (px >= 0) {
						buildBlock(world, x + px, y + h, z + pz, this.leaf_block, this.leaf_meta);
						buildBlock(world, x - px, y + h, z - pz, this.leaf_block, this.leaf_meta);
						buildBlock(world, x + px, y + h, z - pz, this.leaf_block, this.leaf_meta);
						buildBlock(world, x - px, y + h, z + pz, this.leaf_block, this.leaf_meta);
						pz++;
						px--;
					}
				}
			}
			if (noleaf_buffer == 1) {
				for (int lw = current_len; lw > 0; lw--) {
					int px = lw;
					int pz = 0;
					while (px >= 0) {
						buildBlock(world, x + px, y + h, z + pz, this.leaf_block, this.leaf_meta);
						buildBlock(world, x - px, y + h, z - pz, this.leaf_block, this.leaf_meta);
						buildBlock(world, x + px, y + h, z - pz, this.leaf_block, this.leaf_meta);
						buildBlock(world, x - px, y + h, z + pz, this.leaf_block, this.leaf_meta);
						pz++;
						px--;
					}
				}
			}
		}
		
		while (leaf_width > 0) {
			if ((h & 0xfe) != h) {
				leaf_width--;
			} else if ((h & 0xfd) != h) {
				leaf_width--;
			}
			
			for (int lw = leaf_width; lw > 0; lw--) {
				int px = lw;
				int pz = 0;
				while (px >= 0) {
					buildBlock(world, x + px, y + h, z + pz, this.leaf_block, this.leaf_meta);
					buildBlock(world, x - px, y + h, z - pz, this.leaf_block, this.leaf_meta);
					buildBlock(world, x + px, y + h, z - pz, this.leaf_block, this.leaf_meta);
					buildBlock(world, x - px, y + h, z + pz, this.leaf_block, this.leaf_meta);
					pz++;
					px--;
				}
			}
			
			h++;
		}

		for (int hh = h - 3; hh < h + 4; hh++) {
			buildBlock(world, x, y + hh, z, this.leaf_block, this.leaf_meta);
		}
		buildBlock(world, x + 1, y + h, z, this.leaf_block, this.leaf_meta);
		buildBlock(world, x - 1, y + h, z, this.leaf_block, this.leaf_meta);
		buildBlock(world, x, y + h, z + 1, this.leaf_block, this.leaf_meta);
		buildBlock(world, x, y + h, z - 1, this.leaf_block, this.leaf_meta);
		buildBlock(world, x + 1, y + h, z, this.leaf_block, this.leaf_meta);
		buildBlock(world, x - 1, y + h, z, this.leaf_block, this.leaf_meta);
		buildBlock(world, x, y + h, z + 1, this.leaf_block, this.leaf_meta);
		buildBlock(world, x, y + h, z - 1, this.leaf_block, this.leaf_meta);

		return true;
	}
	
	public void buildBlock(World world, int x, int y, int z, Block block, int meta)
	{
		Block old = world.getBlock(x, y, z);
		if (world.isAirBlock(x, y, z) || old.isLeaves(world, x, y, z) || old instanceof BlockSapling) {
			world.setBlock(x, y, z, block, meta, 2);
		}
	}
}
