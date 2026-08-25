package com.captrojo.resadditae.world;

public class SimpleNode
{
	public SimpleCoords pos;
	public int[] wall_vec;
	
	public int val;
	
	public SimpleNode(SimpleCoords coords, int[] wall_vec)
	{
		this.pos = coords;
		this.wall_vec = wall_vec;
	}
	
	public SimpleNode(SimpleNode node)
	{
		this.pos = new SimpleCoords(node.pos);
		this.wall_vec = node.wall_vec;
	}
}
