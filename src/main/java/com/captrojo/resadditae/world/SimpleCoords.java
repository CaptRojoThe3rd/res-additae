package com.captrojo.resadditae.world;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SimpleCoords
{
	public static final int[][] NEIGHBORS = {
		{-1, 0, 0},
		{1, 0, 0},
		{0, -1, 0},
		{0, 1, 0},
		{0, 0, -1},
		{0, 0, 1}
	};
	public static final int[][] NEARBIES = {
		{-1, -1, -1},
		{-1, -1, 0},
		{-1, -1, 1},
		{-1, 0, -1},
		{-1, 0, 0},
		{-1, 0, 1},
		{-1, 1, -1},
		{-1, 1, 0},
		{-1, 1, 1},
		{0, -1, -1},
		{0, -1, 0},
		{0, -1, 1},
		{0, 0, -1},
		{0, 0, 1},
		{0, 1, -1},
		{0, 1, 0},
		{0, 1, 1},
		{1, -1, -1},
		{1, -1, 0},
		{1, -1, 1},
		{1, 0, -1},
		{1, 0, 0},
		{1, 0, 1},
		{1, 1, -1},
		{1, 1, 0},
		{1, 1, 1}
	};
	public static final int[][] NEARBIES_MINUS_NEIGHBORS = {
		{-1, -1, -1},
		{-1, -1, 0},
		{-1, -1, 1},
		{-1, 0, -1},
		{-1, 0, 1},
		{-1, 1, -1},
		{-1, 1, 0},
		{-1, 1, 1},
		{0, -1, -1},
		{0, -1, 1},
		{0, 1, -1},
		{0, 1, 1},
		{1, -1, -1},
		{1, -1, 0},
		{1, -1, 1},
		{1, 0, -1},
		{1, 0, 1},
		{1, 1, -1},
		{1, 1, 0},
		{1, 1, 1}
	};
	
	public int x;
	public int y;
	public int z;
	
	public SimpleCoords(SimpleCoords old)
	{
		this.x = old.x;
		this.y = old.y;
		this.z = old.z;
	}
	
	public SimpleCoords(int x, int z)
	{
		this(x, -1, z);
	}
	
	public SimpleCoords(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public SimpleCoords add(int[] offset)
	{
		this.x += offset[0];
		this.y += offset[1];
		this.z += offset[2];
		return this;
	}
	
	public SimpleCoords subtract(int[] offset)
	{
		this.x -= offset[0];
		this.y -= offset[1];
		this.z -= offset[2];
		return this;
	}
	
	public void updateMinPos(int x, int y, int z)
	{
		if (x < this.x) {
			this.x = x;
		}
		if (y < this.y) {
			this.y = y;
		}
		if (z < this.z) {
			this.z = z;
		}
	}
	
	public void updateMaxPos(int x, int y, int z)
	{
		if (x > this.x) {
			this.x = x;
		}
		if (y > this.y) { 
			this.y = y;
		}
		if (z > this.z) { 
			this.z = z;
		}
	}
	
	public boolean isNearAir(IBlockAccess world)
	{
		for (int[] off : NEARBIES) {
			SimpleCoords sc = new SimpleCoords(this).add(off);
			if (world.isAirBlock(sc.x, sc.y, sc.z)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInRange(SimpleCoords start, SimpleCoords end)
	{
		if (start.x > this.x || start.y > this.y || start.z > this.z) {
			return false;
		}
		if (end.x < this.x || end.y < this.y || end.z < this.z) {
			return false;
		}
		return true;
	}
	
	public void gotoRandNeighbor(Random rand)
	{
		this.add(NEIGHBORS[rand.nextInt(NEIGHBORS.length)]);
	}
	
	public boolean gotoRandNearby(World world, Random rand, Block block)
	{
		ArrayList<int[]> offs = new ArrayList<int[]>();
		for (int[] off : NEARBIES) {
			SimpleCoords sc = new SimpleCoords(this).add(off);
			if (world.getBlock(sc.x, sc.y, sc.z) == block) {
				offs.add(off);
			}
		}
		if (offs.size() == 0) {
			return false;
		}
		int[] off = offs.get(rand.nextInt(offs.size()));
		this.add(off);
		return true;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SimpleCoords)) {
			return false;
		}
		SimpleCoords sc = (SimpleCoords) obj;
		return (sc.x == this.x) && (sc.y == this.y) && (sc.z == this.z);
	}
}
