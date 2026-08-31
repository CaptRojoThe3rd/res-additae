package com.captrojo.resadditae.world.gen.feature;

import java.util.Map;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenMinableDynamic extends WorldGenMinable
{
	private final int number_of_blocks;
	private final Map<BlockMeta, BlockMeta> tgt_ore_map;
	
	public WorldGenMinableDynamic(int number, Map<BlockMeta, BlockMeta> tgt_ore_map)
	{
		super(Blocks.stone, 0);
		this.number_of_blocks = number;
		this.tgt_ore_map = tgt_ore_map;
	}
	
	private BlockMeta getOreForGen(BlockMeta tgt)
	{
		/* Why is the HashMap not doing its job?!?! */
		for (BlockMeta key : this.tgt_ore_map.keySet()) {
			if (key.equals(tgt)) {
				return this.tgt_ore_map.get(key);
			}
		}
		return tgt;
	}
	
	private void placeOre(World world, int x, int y, int z)
	{
		BlockMeta tgt = new BlockMeta(world, x, y, z);
		BlockMeta ore = this.getOreForGen(tgt);
		this.setBlockAndNotifyAdequately(world, x, y, z, ore.block, ore.meta);
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		float f = rand.nextFloat() * (float) Math.PI;
		double d0 = (double) ((float) (x + 8) + MathHelper.sin(f) * (float) this.number_of_blocks / 8.0f);
		double d1 = (double) ((float) (x + 8) - MathHelper.sin(f) * (float) this.number_of_blocks / 8.0f);
		double d2 = (double) ((float) (z + 8) + MathHelper.cos(f) * (float) this.number_of_blocks / 8.0f);
		double d3 = (double) ((float) (z + 8) - MathHelper.cos(f) * (float) this.number_of_blocks / 8.0f);
		double d4 = (double) (y + rand.nextInt(3) - 2);
		double d5 = (double) (y + rand.nextInt(3) - 2);

		for (int l = 0; l <= this.number_of_blocks; ++l) {
			double d6 = d0 + (d1 - d0) * (double) l / (double) this.number_of_blocks;
			double d7 = d4 + (d5 - d4) * (double) l / (double) this.number_of_blocks;
			double d8 = d2 + (d3 - d2) * (double) l / (double) this.number_of_blocks;
			double d9 = rand.nextDouble() * (double) this.number_of_blocks / 16.0;
			double d10 = (double) (MathHelper.sin((float) l * (float) Math.PI / (float) this.number_of_blocks) + 1.0f) * d9 + 1.0;
			double d11 = (double) (MathHelper.sin((float) l * (float) Math.PI / (float) this.number_of_blocks) + 1.0f) * d9 + 1.0;
			int x0 = MathHelper.floor_double(d6 - d10 / 2.0);
			int y0 = MathHelper.floor_double(d7 - d11 / 2.0);
			int z0 = MathHelper.floor_double(d8 - d10 / 2.0);
			int xf = MathHelper.floor_double(d6 + d10 / 2.0);
			int yf = MathHelper.floor_double(d7 + d11 / 2.0);
			int zf = MathHelper.floor_double(d8 + d10 / 2.0);

			for (int xp = x0; xp <= xf; xp++) {
				double d12 = ((double) xp + 0.5 - d6) / (d10 / 2.0);
				if (d12 * d12 >= 1.0) {
					continue;
				}
				for (int yp = y0; yp <= yf; yp++) {
					double d13 = ((double) yp + 0.5 - d7) / (d11 / 2.0);
					if (d12 * d12 + d13 * d13 >= 1.0) {
						continue;
					}
					for (int zp = z0; zp <= zf; zp++) {
						double d14 = ((double) zp + 0.5 - d8) / (d10 / 2.0);
						if (d12 * d12 + d13 * d13 + d14 * d14 >= 1.0) {
							continue;
						}
						this.placeOre(world, xp, yp, zp);
					}
				}
			}
		}

		return true;
	}
}
