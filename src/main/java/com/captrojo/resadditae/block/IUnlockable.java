package com.captrojo.resadditae.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IUnlockable
{
	public boolean isCorrectKey(World world, EntityPlayer player, int x, int y, int z, int key);
	public boolean consumesKey(World world, EntityPlayer player, int x, int y, int z, int key);
	public boolean canBeUnlocked(World world, EntityPlayer player, int x, int y, int z, int key);
	public void unlockBlock(World world, EntityPlayer player, int x, int y, int z, int key);
}
