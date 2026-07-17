package com.captrojo.resadditae.world.gen.feature;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.main.MiscHlpr;
import com.captrojo.resadditae.world.SimpleCoords;
import com.captrojo.resadditae.world.SimpleNode;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenLargeGeodeC extends WorldGenLargeGeodeBase
{
	public WorldGenLargeGeodeC(BlockMeta shiny, BlockMeta shell)
	{
		super(shiny, shell);
	}

	@Override
	public void generateSpecific(World world, Random rand, int x0, int y0, int z0)
	{
		ArrayList<SimpleNode> nodes = this.createNodes(world, rand, this.size * 2);

		for (SimpleNode node : nodes) {
			int[][] vecs = SimpleCoords.NEIGHBORS.clone();
			vecs[rand.nextInt(vecs.length)] = new int[] {0, 0, 0};
			vecs[rand.nextInt(vecs.length)] = SimpleCoords.NEARBIES_MINUS_NEIGHBORS[rand.nextInt(SimpleCoords.NEARBIES_MINUS_NEIGHBORS.length)];
			for (node.val = 0; node.val < 100; node.val++) {
				if (world.isAirBlock(node.pos.x, node.pos.y, node.pos.z)) {
					this.setShinyBlock(world, rand, node.pos.x, node.pos.y, node.pos.z);
				}
				node.pos.add(vecs[node.val % 6]);
			}
		}
	}
}
