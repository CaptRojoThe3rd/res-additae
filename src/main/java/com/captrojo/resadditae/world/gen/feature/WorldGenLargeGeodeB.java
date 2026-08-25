package com.captrojo.resadditae.world.gen.feature;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.world.SimpleCoords;
import com.captrojo.resadditae.world.SimpleNode;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenLargeGeodeB extends WorldGenLargeGeodeBase
{
	public WorldGenLargeGeodeB(BlockMeta shiny, BlockMeta shell)
	{
		super(shiny, shell);
	}

	@Override
	public void generateSpecific(World world, Random rand, int x0, int y0, int z0)
	{
		ArrayList<SimpleNode> nodes = this.createNodes(world, rand, (int) (this.size * 2.5));
		
		for (SimpleNode node : nodes) {
			int[] vec = SimpleCoords.NEARBIES_MINUS_NEIGHBORS[rand.nextInt(SimpleCoords.NEARBIES_MINUS_NEIGHBORS.length)];
			int[] vec2 = vec.clone();
			vec2[rand.nextInt(3)] = 0;
			boolean f = false;
			
			for (Block existing = Blocks.air; existing == Blocks.air; existing = world.getBlock(node.pos.x, node.pos.y, node.pos.z)) {
				this.setShinyBlock(world, rand, node.pos.x, node.pos.y, node.pos.z);
				node.pos.add(f ? vec2 : vec);
				f = !f;
			}
		}
	}
}
