package com.captrojo.resadditae.world.structure;

import net.minecraft.world.World;

public interface IStructureActor
{
	public void onPlacedInStructure(World world, int x, int y, int z);
}
