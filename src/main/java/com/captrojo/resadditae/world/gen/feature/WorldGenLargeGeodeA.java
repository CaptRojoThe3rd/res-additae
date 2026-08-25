package com.captrojo.resadditae.world.gen.feature;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.world.SimpleCoords;
import com.captrojo.resadditae.world.SimpleNode;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class WorldGenLargeGeodeA extends WorldGenLargeGeodeBase
{
	public WorldGenLargeGeodeA(BlockMeta shiny, BlockMeta shell)
	{
		super(shiny, shell);
	}
	
	@Override
	public void generateSpecific(World world, Random rand, int x0, int y0, int z0)
	{
		ArrayList<SimpleNode> nodes = this.createNodes(world, rand, this.size * 2);
		ArrayList<SimpleNode> nodes2 = new ArrayList<SimpleNode>();
		
		int max_forks = this.size * 3;
		int forks = 0;
		for (int q = 0; q < 100_000 && nodes.size() > 0; q++) {
			for (SimpleNode node : nodes) {
				this.setShinyBlock(world, rand, node.pos.x, node.pos.y, node.pos.z);
				if (rand.nextFloat() < 0.15f) {
					continue;
				}
				if (!node.pos.gotoRandNearby(world, rand, Blocks.air)) {
					continue;
				}
				if (rand.nextFloat() < 0.25f && forks < max_forks) {
					forks++;
					SimpleNode fnode = new SimpleNode(node);
					fnode.pos.gotoRandNearby(world, rand, Blocks.air);
					nodes2.add(fnode);
				}
				nodes2.add(node);
			}
			nodes = (ArrayList<SimpleNode>) nodes2.clone();
			nodes2.clear();
		}
	}
}
