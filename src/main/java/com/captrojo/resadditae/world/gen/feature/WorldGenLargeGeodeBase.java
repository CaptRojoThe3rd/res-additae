package com.captrojo.resadditae.world.gen.feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.main.BlockHlpr;
import com.captrojo.resadditae.world.SimpleCoords;
import com.captrojo.resadditae.world.SimpleNode;
import com.captrojo.resadditae.world.SpacedThingCheck;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class WorldGenLargeGeodeBase extends WorldGenerator
{
	public static final SpacedThingCheck PLACEMENT_CHK = new SpacedThingCheck(
		"WorldGenLargeGeodeBase".hashCode(),
		null,
		0,
		8,
		16
	);
	
	private static HashMap<BlockMeta, BlockMeta> minable_map;
	private static int[] boundary_offs = {5, 5, 5};
	
	static
	{
		minable_map = new HashMap<BlockMeta, BlockMeta>();
		minable_map.put(new BlockMeta(ModBlocks.depth_stones_special, 2), BlockMeta.AIR);
	}
	
	protected final BlockMeta shiny;
	protected final BlockMeta shell;
	
	protected WorldGenCavern cavern;
	protected int size;
	
	public WorldGenLargeGeodeBase(BlockMeta shiny, BlockMeta shell)
	{
		this.shiny = shiny;
		this.shell = shell;
	}
	
	public abstract void generateSpecific(World world, Random rand, int x0, int y0, int z0);
	
	protected void setShinyBlock(World world, Random rand, int x, int y, int z)
	{
		this.setBlockAndNotifyAdequately(world, x, y, z, this.shiny.block, this.shiny.meta | (rand.nextInt(4) << 1));
	}
	
	protected void setShellBlock(World world, Random rand, int x, int y, int z)
	{
		this.setBlockAndNotifyAdequately(world, x, y, z, this.shell.block, this.shell.meta);
	}
	
	protected ArrayList<SimpleNode> createNodes(World world, Random rand, int count)
	{
		ArrayList<SimpleNode> nodes = new ArrayList<SimpleNode>();
		
		for (int i = 0; i < count; i++) {
			SimpleCoords p = this.cavern.coords.get(rand.nextInt(this.cavern.coords.size()));
			int rd = rand.nextInt(6);
			Block first = world.getBlock(p.x, p.y, p.z);
			
			while (true) {
				if (world.getBlock(p.x, p.y, p.z) != first) {
					p.subtract(SimpleCoords.NEIGHBORS[rd]);
					nodes.add(new SimpleNode(p, SimpleCoords.NEIGHBORS[rd]));
					break;
				}
				p.add(SimpleCoords.NEIGHBORS[rd]);
			}
		}
		return nodes;
	}
	
	@Override
	public boolean generate(World world, Random rand, int x0, int y0, int z0)
	{
		if (world.getBlock(x0, y0, z0) != ModBlocks.depth_stones_special) {
			return false;
		}
		
		this.size = 300 + rand.nextInt(500);
		this.cavern = new WorldGenCavern(size, new BlockMeta(ModBlocks.depth_stones_special, 2));
		this.cavern.generate(world, rand, x0, y0, z0);
		this.cavern.min_pos.subtract(boundary_offs);
		this.cavern.max_pos.add(boundary_offs);
		
		for (int x = this.cavern.min_pos.x; x < this.cavern.max_pos.x; x++) {
			for (int y = this.cavern.min_pos.y; y < this.cavern.max_pos.y; y++) {
				for (int z = this.cavern.min_pos.z; z < this.cavern.max_pos.z; z++) {
					if (!world.getBlock(x, y, z).isOpaqueCube()) {
						continue;
					}
					if (!BlockHlpr.isAnyBlockNearby(world, x, y, z, 4, Blocks.air)) {
						continue;
					}
					this.setShellBlock(world, rand, x, y, z);
				}
			}
		}
		
		this.generateSpecific(world, rand, x0, y0, z0);
		return true;
	}
}
