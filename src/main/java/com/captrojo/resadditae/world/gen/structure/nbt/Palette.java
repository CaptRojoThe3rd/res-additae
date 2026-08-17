package com.captrojo.resadditae.world.gen.structure.nbt;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class Palette
{
	private ArrayList<PaletteEntry> entries;
	
	public Palette()
	{
		this.entries = new ArrayList<PaletteEntry>();
	}
	
	/**
	 * Get the index for the palette entry for the block at the specified location.
	 * Creates a new palette entry if necessary.
	 */
	public int getPaletteEntryIdxFor(World world, int x, int y, int z)
	{
		PaletteEntry new_entry = new PaletteEntry(world, x, y, z);
		
		for (int i = 0; i < this.entries.size(); i++) {
			if (new_entry.equals(this.entries.get(i))) {
				return i;
			}
		}
		
		this.entries.add(new_entry);
		return this.entries.size() - 1;
	}
	
	public PaletteEntry getEntry(int idx)
	{
		return this.entries.get(idx);
	}
	
	public NBTTagList saveToNBT(NBTTagList nbtlist)
	{
		for (PaletteEntry entry : this.entries) {
			nbtlist.appendTag(entry.saveToNBT(new NBTTagCompound()));
		}
		
		return nbtlist;
	}
	
	public Palette loadFromNBT(NBTTagList nbtlist)
	{
		this.entries.clear();
		for (int i = 0; i < nbtlist.tagCount(); i++) {
			this.entries.add(new PaletteEntry().loadFromNBT(nbtlist.getCompoundTagAt(i)));
		}
		
		return this;
	}
}
