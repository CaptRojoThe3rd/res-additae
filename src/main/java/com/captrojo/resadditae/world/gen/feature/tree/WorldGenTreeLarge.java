package com.captrojo.resadditae.world.gen.feature.tree;

import java.util.Random;

import com.captrojo.resadditae.block.WoodTypes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenTreeLarge extends WorldGenAbstractTree
{
	static final byte[] OTHER_COORD_PAIRS = new byte[] {(byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1};
	
	final Block wood_block;
	final int wood_meta;
	final Block leaf_block;
	final int leaf_meta;
	
	int min_height_limit;
	int max_height_limit;
	int leaf_distance_limit = 4;
	int trunk_size = 1;
	double leaf_density = 1.0D;
	
	Random rand = new Random();
	World world;
	int[] base_pos = new int[] {0, 0, 0};
	int height_limit;
	int height;
	double height_attenuation = 0.618D;
	double branch_density = 1.0D;
	double branch_slope = 0.381D;
	double scale_width = 1.0D;
	int[][] leaf_nodes;
	
	final Block sapling;

	public WorldGenTreeLarge(boolean notify, WoodTypes wood_type, int min_height, int max_height,
		int max_leaf_distance, int trunk_size, double leaf_density)
	{
		super(notify);
		
		this.wood_block = wood_type.getLog().block;
		this.wood_meta = wood_type.getLog().meta;
		this.leaf_block = wood_type.getLeaves().block;
		this.leaf_meta = wood_type.getLeaves().meta;
		
		this.min_height_limit = min_height;
		this.max_height_limit = max_height;
		this.leaf_distance_limit = max_leaf_distance;
		this.trunk_size = trunk_size;
		this.leaf_density = leaf_density;
		
		this.sapling = wood_type.getSapling().block;
	}

	/**
	 * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
	 */
	void generateLeafNodeList()
	{
		this.height = (int) ((double) this.height_limit * this.height_attenuation);

		if (this.height >= this.height_limit) {
			this.height = this.height_limit - 1;
		}

		int i = (int) (1.382D + Math.pow(this.leaf_density * (double) this.height_limit / 13.0D, 2.0D));

		if (i < 1) {
			i = 1;
		}

		int[][] aint = new int[i * this.height_limit][4];
		int j = this.base_pos[1] + this.height_limit - this.leaf_distance_limit;
		int k = 1;
		int l = this.base_pos[1] + this.height;
		int i1 = j - this.base_pos[1];
		aint[0][0] = this.base_pos[0];
		aint[0][1] = j;
		aint[0][2] = this.base_pos[2];
		aint[0][3] = l;
		--j;

		while (i1 >= 0) {
			int j1 = 0;
			float f = this.layerSize(i1);

			if (f < 0.0F) {
				--j;
				--i1;
			} else {
				for (double d0 = 0.5D; j1 < i; ++j1) {
					double d1 = this.scale_width * (double) f * ((double) this.rand.nextFloat() + 0.328D);
					double d2 = (double) this.rand.nextFloat() * 2.0D * Math.PI;
					int k1 = MathHelper.floor_double(d1 * Math.sin(d2) + (double) this.base_pos[0] + d0);
					int l1 = MathHelper.floor_double(d1 * Math.cos(d2) + (double) this.base_pos[2] + d0);
					int[] aint1 = new int[] {k1, j, l1};
					int[] aint2 = new int[] {k1, j + this.leaf_distance_limit, l1};

					if (this.checkBlockLine(aint1, aint2) == -1) {
						int[] aint3 = new int[] {this.base_pos[0], this.base_pos[1], this.base_pos[2]};
						double d3 = Math.sqrt(Math.pow((double) Math.abs(this.base_pos[0] - aint1[0]), 2.0D) + Math.pow((double) Math.abs(this.base_pos[2] - aint1[2]), 2.0D));
						double d4 = d3 * this.branch_slope;

						if ((double) aint1[1] - d4 > (double) l) {
							aint3[1] = l;
						} else {
							aint3[1] = (int) ((double) aint1[1] - d4);
						}

						if (this.checkBlockLine(aint3, aint1) == -1) {
							aint[k][0] = k1;
							aint[k][1] = j;
							aint[k][2] = l1;
							aint[k][3] = aint3[1];
							++k;
						}
					}
				}

				--j;
				--i1;
			}
		}

		this.leaf_nodes = new int[k][4];
		System.arraycopy(aint, 0, this.leaf_nodes, 0, k);
	}

	void func_150529_a(int x, int y, int z, float p_150529_4_, byte p_150529_5_, Block block, int meta)
	{
		int l = (int) ((double) p_150529_4_ + 0.618D);
		byte b1 = OTHER_COORD_PAIRS[p_150529_5_];
		byte b2 = OTHER_COORD_PAIRS[p_150529_5_ + 3];
		int[] pos1 = new int[] {x, y, z};
		int[] pos2 = new int[] {0, 0, 0};
		int i1 = -l;
		int j1 = -l;

		for (pos2[p_150529_5_] = pos1[p_150529_5_]; i1 <= l; ++i1) {
			pos2[b1] = pos1[b1] + i1;
			j1 = -l;

			while (j1 <= l) {
				double d0 = Math.pow((double) Math.abs(i1) + 0.5D, 2.0D) + Math.pow((double) Math.abs(j1) + 0.5D, 2.0D);

				if (d0 > (double) (p_150529_4_ * p_150529_4_)) {
					++j1;
				} else {
					pos2[b2] = pos1[b2] + j1;
					Block block1 = this.world.getBlock(pos2[0], pos2[1], pos2[2]);

					if (!block1.isAir(world, pos2[0], pos2[1], pos2[2]) && !block1.isLeaves(world, pos2[0], pos2[1], pos2[2])) {
						++j1;
					} else {
						this.setBlockAndNotifyAdequately(this.world, pos2[0], pos2[1], pos2[2], block, meta);
						++j1;
					}
				}
			}
		}
	}

	/**
	 * Gets the rough size of a layer of the tree.
	 */
	float layerSize(int p_76490_1_)
	{
		if ((double) p_76490_1_ < (double) ((float) this.height_limit) * 0.3D) {
			return -1.618F;
		} else {
			float f = (float) this.height_limit / 2.0F;
			float f1 = (float) this.height_limit / 2.0F - (float) p_76490_1_;
			float f2;

			if (f1 == 0.0F) {
				f2 = f;
			} else if (Math.abs(f1) >= f) {
				f2 = 0.0F;
			} else {
				f2 = (float) Math.sqrt(Math.pow((double) Math.abs(f), 2.0D) - Math.pow((double) Math.abs(f1), 2.0D));
			}

			f2 *= 0.5F;
			return f2;
		}
	}

	float leafSize(int p_76495_1_)
	{
		return p_76495_1_ >= 0 && p_76495_1_ < this.leaf_distance_limit ? (p_76495_1_ != 0 && p_76495_1_ != this.leaf_distance_limit - 1 ? 3.0F : 2.0F) : -1.0F;
	}

	/**
	 * Generates the leaves surrounding an individual entry in the leafNodes list.
	 */
	void generateLeafNode(int p_76491_1_, int p_76491_2_, int p_76491_3_)
	{
		int l = p_76491_2_;

		for (int i1 = p_76491_2_ + this.leaf_distance_limit; l < i1; ++l) {
			float f = this.leafSize(l - p_76491_2_);
			this.func_150529_a(p_76491_1_, l, p_76491_3_, f, (byte) 1, this.leaf_block, this.leaf_meta);
		}
	}

	void func_150530_a(int[] p_150530_1_, int[] p_150530_2_, Block block, int meta)
	{
		int[] aint2 = new int[] {0, 0, 0};
		byte b0 = 0;
		byte b1;

		for (b1 = 0; b0 < 3; ++b0) {
			aint2[b0] = p_150530_2_[b0] - p_150530_1_[b0];

			if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
				b1 = b0;
			}
		}

		if (aint2[b1] != 0) {
			byte b2 = OTHER_COORD_PAIRS[b1];
			byte b3 = OTHER_COORD_PAIRS[b1 + 3];
			byte b4;

			if (aint2[b1] > 0) {
				b4 = 1;
			} else {
				b4 = -1;
			}

			double d0 = (double) aint2[b2] / (double) aint2[b1];
			double d1 = (double) aint2[b3] / (double) aint2[b1];
			int[] aint3 = new int[] {0, 0, 0};
			int i = 0;

			for (int j = aint2[b1] + b4; i != j; i += b4) {
				aint3[b1] = MathHelper.floor_double((double) (p_150530_1_[b1] + i) + 0.5D);
				aint3[b2] = MathHelper.floor_double((double) p_150530_1_[b2] + (double) i * d0 + 0.5D);
				aint3[b3] = MathHelper.floor_double((double) p_150530_1_[b3] + (double) i * d1 + 0.5D);
				byte rotation = 0;
				int k = Math.abs(aint3[0] - p_150530_1_[0]);
				int l = Math.abs(aint3[2] - p_150530_1_[2]);
				int i1 = Math.max(k, l);

				if (i1 > 0) {
					if (k == i1) {
						rotation = 4;
					} else if (l == i1) {
						rotation = 8;
					}
				}

				this.setBlockAndNotifyAdequately(this.world, aint3[0], aint3[1], aint3[2], block, rotation | meta);
			}
		}
	}

	/**
	 * Generates the leaf portion of the tree as specified by the leafNodes list.
	 */
	void generateLeaves()
	{
		int i = 0;

		for (int j = this.leaf_nodes.length; i < j; ++i) {
			int k = this.leaf_nodes[i][0];
			int l = this.leaf_nodes[i][1];
			int i1 = this.leaf_nodes[i][2];
			this.generateLeafNode(k, l, i1);
		}
	}

	/**
	 * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
	 */
	boolean leafNodeNeedsBase(int p_76493_1_)
	{
		return (double) p_76493_1_ >= (double) this.height_limit * 0.2D;
	}

	/**
	 * Places the trunk for the big tree that is being generated. Able to generate double-sized trunks by changing a
	 * field that is always 1 to 2.
	 */
	void generateTrunk()
	{
		int x = this.base_pos[0];
		int y = this.base_pos[1];
		int yh = this.base_pos[1] + this.height;
		int z = this.base_pos[2];
		int[] pos1 = new int[] {x, y, z};
		int[] pos2 = new int[] {x, yh, z};
		this.func_150530_a(pos1, pos2, this.wood_block, this.wood_meta);

		if (this.trunk_size == 2) {
			pos1[0]++;
			pos2[0]++;
			this.func_150530_a(pos1, pos2, this.wood_block, this.wood_meta);
			pos1[2]++;
			pos2[2]++;
			this.func_150530_a(pos1, pos2, this.wood_block, this.wood_meta);
			pos1[0]--;
			pos2[0]--;
			this.func_150530_a(pos1, pos2, this.wood_block, this.wood_meta);
		}
	}

	/**
	 * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
	 */
	void generateLeafNodeBases()
	{
		int i = 0;
		int j = this.leaf_nodes.length;

		for (int[] pos1 = new int[] {this.base_pos[0], this.base_pos[1], this.base_pos[2]}; i < j; ++i) {
			int[] node = this.leaf_nodes[i];
			int[] pos2 = new int[] {node[0], node[1], node[2]};
			pos1[1] = node[3];
			int k = pos1[1] - this.base_pos[1];

			if (this.leafNodeNeedsBase(k)) {
				this.func_150530_a(pos1, pos2, this.wood_block, this.wood_meta);
			}
		}
	}

	/**
	 * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
	 * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
	 */
	int checkBlockLine(int[] p_76496_1_, int[] p_76496_2_)
	{
		int[] aint2 = new int[] {0, 0, 0};
		byte b0 = 0;
		byte b1;

		for (b1 = 0; b0 < 3; ++b0) {
			aint2[b0] = p_76496_2_[b0] - p_76496_1_[b0];

			if (Math.abs(aint2[b0]) > Math.abs(aint2[b1])) {
				b1 = b0;
			}
		}

		if (aint2[b1] == 0) {
			return -1;
		} else {
			byte b2 = OTHER_COORD_PAIRS[b1];
			byte b3 = OTHER_COORD_PAIRS[b1 + 3];
			byte b4;

			if (aint2[b1] > 0) {
				b4 = 1;
			} else {
				b4 = -1;
			}

			double d0 = (double) aint2[b2] / (double) aint2[b1];
			double d1 = (double) aint2[b3] / (double) aint2[b1];
			int[] aint3 = new int[] {0, 0, 0};
			int i = 0;
			int j;

			for (j = aint2[b1] + b4; i != j; i += b4) {
				aint3[b1] = p_76496_1_[b1] + i;
				aint3[b2] = MathHelper.floor_double((double) p_76496_1_[b2] + (double) i * d0);
				aint3[b3] = MathHelper.floor_double((double) p_76496_1_[b3] + (double) i * d1);
				Block block = this.world.getBlock(aint3[0], aint3[1], aint3[2]);

				if (!this.isReplaceable(world, aint3[0], aint3[1], aint3[2])) {
					break;
				}
			}

			return i == j ? -1 : Math.abs(i);
		}
	}

	boolean isValidSoilBlock(Block block, int x, int y, int z)
	{
		return block.canSustainPlant(this.world, x, y, z, ForgeDirection.UP, (BlockSapling) this.sapling);
	
	}
	
	boolean canSustainPlant(int xo, int zo)
	{
		int x = this.base_pos[0] + xo;
		int y = this.base_pos[1] - 1;
		int z = this.base_pos[2] + zo;
		Block block = this.world.getBlock(x, y, z);
		return this.isValidSoilBlock(block, x, y, z);
	}
	
	/**
	 * Returns a boolean indicating whether or not the current location for the tree, spanning basePos to to the height
	 * limit, is valid.
	 */
	boolean validTreeLocation()
	{
		int[] aint = new int[] {this.base_pos[0], this.base_pos[1], this.base_pos[2]};
		int[] aint1 = new int[] {this.base_pos[0], this.base_pos[1] + this.height_limit - 1, this.base_pos[2]};
		Block block = this.world.getBlock(this.base_pos[0], this.base_pos[1] - 1, this.base_pos[2]);

		boolean isSoil = this.canSustainPlant(0, 0);
		if (this.trunk_size == 2) {
			isSoil &= this.canSustainPlant(1, 0);
			isSoil &= this.canSustainPlant(0, 1);
			isSoil &= this.canSustainPlant(1, 1);
		}
		
		if (!isSoil) {
			return false;
		} else {
			int i = this.checkBlockLine(aint, aint1);

			if (i == -1) {
				return true;
			} else if (i < 6) {
				return false;
			} else {
				this.height_limit = i;
				return true;
			}
		}
	}

	/**
	 * Rescales the generator settings, only used in WorldGenBigTree
	 */
	public void setScale(double p_76487_1_, double p_76487_3_, double p_76487_5_)
	{
		this.max_height_limit = (int) (p_76487_1_ * this.max_height_limit);

		if (p_76487_1_ > 0.5D) {
			this.leaf_distance_limit = 5;
		}

		this.scale_width = p_76487_3_;
		this.leaf_density = p_76487_5_;
	}

	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		this.world = world;
		long l = rand.nextLong();
		this.rand.setSeed(l);
		this.base_pos[0] = x;
		this.base_pos[1] = y;
		this.base_pos[2] = z;

		if (this.height_limit == 0) {
			this.height_limit = this.min_height_limit + this.rand.nextInt(this.max_height_limit - this.min_height_limit + 1);
		}

		if (!this.validTreeLocation()) {
			this.world = null; //Fix vanilla Mem leak, holds latest world
			return false;
		} else {
			this.generateLeafNodeList();
			this.generateLeaves();
			this.generateTrunk();
			this.generateLeafNodeBases();
			this.world = null; //Fix vanilla Mem leak, holds latest world
			return true;
		}
	}
}
