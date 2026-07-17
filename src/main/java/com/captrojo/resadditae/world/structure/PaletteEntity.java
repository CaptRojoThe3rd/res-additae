package com.captrojo.resadditae.world.structure;

import net.minecraft.nbt.NBTTagCompound;

public class PaletteEntity
{
	public final String id;
	public final double x_offs;
	public final double y_offs;
	public final double z_offs;
	
	public PaletteEntity(NBTTagCompound tag)
	{
		this.id = tag.getString("id");
		this.x_offs = tag.getDouble("x_offs");
		this.y_offs = tag.getDouble("y_offs");
		this.z_offs = tag.getDouble("z_offs");
	}
}
