package com.captrojo.resadditae.world.gen.feature;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.world.SimpleCoords;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenCavern extends WorldGenMinable
{
	private final Block air;
	private final BlockMeta replace;
	private final int size;
	
	public ArrayList<SimpleCoords> coords;
	public SimpleCoords min_pos;
	public SimpleCoords max_pos;
	
	public WorldGenCavern(int size, Block air, BlockMeta replace)
	{
		super(air, size);
		this.air = air;
		this.replace = replace;
		this.size = size;
	}
	
	private void updatePos(int x, int y, int z)
	{
		this.min_pos.updateMinPos(x, y, z);
		this.max_pos.updateMaxPos(x, y, z);
	}

	@Override
	public boolean generate(World world, Random rand, int x0, int y0, int z0)
	{
		this.coords = new ArrayList<SimpleCoords>();
		this.min_pos = new SimpleCoords(x0, y0, z0);
		this.max_pos = new SimpleCoords(x0, y0, z0);
		
		float f = rand.nextFloat() * (float) Math.PI;
		double d0 = (double) ((float) (x0 + 8) + MathHelper.sin(f) * (float) this.size / 8.0F);
		double d1 = (double) ((float) (x0 + 8) - MathHelper.sin(f) * (float) this.size / 8.0F);
		double d2 = (double) ((float) (z0 + 8) + MathHelper.cos(f) * (float) this.size / 8.0F);
		double d3 = (double) ((float) (z0 + 8) - MathHelper.cos(f) * (float) this.size / 8.0F);
		double d4 = (double) (y0 + rand.nextInt(3) - 2);
		double d5 = (double) (y0 + rand.nextInt(3) - 2);

		for (int l = 0; l <= this.size; ++l) {
			double d6 = d0 + (d1 - d0) * (double) l / (double) this.size;
			double d7 = d4 + (d5 - d4) * (double) l / (double) this.size;
			double d8 = d2 + (d3 - d2) * (double) l / (double) this.size;
			double d9 = rand.nextDouble() * (double) this.size / 16.0D;
			double d10 = (double) (MathHelper.sin((float) l * (float) Math.PI / (float) this.size) + 1.0f) * d9 + 1.0;
			double d11 = (double) (MathHelper.sin((float) l * (float) Math.PI / (float) this.size) + 1.0f) * d9 + 1.0;
			int i1 = MathHelper.floor_double(d6 - d10 / 2.0);
			int j1 = MathHelper.floor_double(d7 - d11 / 2.0);
			int k1 = MathHelper.floor_double(d8 - d10 / 2.0);
			int l1 = MathHelper.floor_double(d6 + d10 / 2.0);
			int i2 = MathHelper.floor_double(d7 + d11 / 2.0);
			int j2 = MathHelper.floor_double(d8 + d10 / 2.0);

			for (int x1 = i1; x1 <= l1; ++x1) {
				double d12 = ((double) x1 + 0.5 - d6) / (d10 / 2.0);

				if (d12 * d12 < 1.0) {
					for (int y1 = j1; y1 <= i2; ++y1) {
						double d13 = ((double) y1 + 0.5 - d7) / (d11 / 2.0);

						if (d12 * d12 + d13 * d13 < 1.0) {
							for (int z1 = k1; z1 <= j2; ++z1) {
								double d14 = ((double) z1 + 0.5 - d8) / (d10 / 2.0);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0 && world.getBlock(x1, y1, z1).isReplaceableOreGen(world, x1, y1, z1, this.replace.block)) {
									world.setBlock(x1, y1, z1, this.air, 0, 2);
									this.coords.add(new SimpleCoords(x1, y1, z1));
									this.updatePos(x1, y1, z1);
								}
							}
						}
					}
				}
			}
		}
		
//		ResAdditae.LOG.info(String.format(
//			"min: (%d, %d, %d)  max: (%d, %d, %d)",
//			this.min_pos.x,
//			this.min_pos.y,
//			this.min_pos.z,
//			this.max_pos.x,
//			this.max_pos.y,
//			this.max_pos.z
//		));
		return true;
	}
}
