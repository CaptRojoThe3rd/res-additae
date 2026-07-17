package com.captrojo.resadditae.world.gen.feature;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.world.SimpleNode;

import net.minecraft.world.World;

public class WorldGenLargeGeodeD extends WorldGenLargeGeodeBase
{
	public WorldGenLargeGeodeD(BlockMeta shiny, BlockMeta shell)
	{
		super(shiny, shell);
	}

	@Override
	public void generateSpecific(World world, Random rand, int x0, int y0, int z0)
	{
		ArrayList<SimpleNode> nodes = this.createNodes(world, rand, this.size);
		
		for (SimpleNode node : nodes) {
			int r = rand.nextInt(3) + 1;
			int rc = rand.nextBoolean() ? r : (r - 1);
			for (int x1 = -r; x1 <= rc; x1++) {
				int x = node.pos.x + x1;
				for (int y1 = -r; y1 <= rc; y1++) {
					int y = node.pos.y + y1;
					for (int z1 = -r; z1 <= rc; z1++) {
						int z = node.pos.z + z1;
						if (!world.isAirBlock(x, y, z)) {
							continue;
						}
						this.setShinyBlock(world, rand, x, y, z);
					}
				}
			}
		}
	}
}
