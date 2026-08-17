package com.captrojo.resadditae.world.gen.structure.nbt;

import net.minecraft.nbt.NBTTagCompound;

/* An index into a structure's palette.
 * Specifies the XYZ offset inside the structure's bounding box.
 */
public class PaletteIndex
{
	public int idx;
	public int x;
	public int y;
	public int z;
	
	public PaletteIndex()
	{
	}
	
	public PaletteIndex(int idx, int x, int y, int z)
	{
		this.idx = idx;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public NBTTagCompound saveToNBT(NBTTagCompound nbt)
	{
		nbt.setShort("I", (short) this.idx);
		nbt.setByte("x", (byte) this.x);
		nbt.setByte("y", (byte) this.y);
		nbt.setByte("z", (byte) this.z);
		
		return nbt;
	}
	
	public PaletteIndex loadFromNBT(NBTTagCompound nbt)
	{
		this.idx = nbt.getShort("I");
		this.x = nbt.getByte("x");
		this.y = nbt.getByte("y");
		this.z = nbt.getByte("z");
		
		return this;
	}
}
