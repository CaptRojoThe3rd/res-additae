package com.captrojo.resadditae.world.snowdungeon;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class SnowDungeon
{
	public int x;
	public int y;
	public int z;
	public StructureBoundingBox pyramid_bb;
	
	public SnowDungeon()
	{
	}
	
	public SnowDungeon(int x, int y, int z, StructureBoundingBox pyramid_bb)
	{
		this.pyramid_bb = pyramid_bb;
	}
	
	public boolean isPlayerInsidePyramid(EntityPlayer player)
	{
		int x = (int) player.posX;
		int y = (int) player.posY;
		int z = (int) player.posZ;
		return this.pyramid_bb.isVecInside(x, y, z);
	}
	
	public SnowDungeon loadFromNBT(NBTTagCompound nbt)
	{
		this.x = nbt.getInteger("x");
		this.y = nbt.getInteger("y");
		this.z = nbt.getInteger("z");
		this.pyramid_bb = new StructureBoundingBox(nbt.getIntArray("PyramidBB"));
		return this;
	}
	
	public NBTTagCompound saveToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("x", this.x);
		nbt.setInteger("y", this.y);
		nbt.setInteger("z", this.z);
		nbt.setTag("PyramidBB", this.pyramid_bb.func_151535_h());
		return nbt;
	}
}
