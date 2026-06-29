package com.captrojo.resadditae.world.structure;

import java.util.ArrayList;
import java.util.Random;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.tileentity.TEStructureBlock;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class PaletteIndex
{	
	public final short idx;
	public final int x;
	public final int y;
	public final int z;
	
	public PaletteIndex(short idx, int x, int y, int z)
	{
		this.idx = idx;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PaletteIndex(ArrayList<PaletteObject> palette, World world, int x, int y, int z, int sx, int sy, int sz)
	{
		PaletteObject o = new PaletteObject(world, x, y, z);
		
		if (o.block == ModBlocks.structure_block) {
			TEStructureBlock te = (TEStructureBlock) world.getTileEntity(x, y, z);
			switch (o.meta) {
			case 0:
				o.block = Blocks.air;
				o.meta = 0;
				o.te_data = null;
				break;
			case 1:
			case 2:
				o.ref_group = o.meta;
				o.ref_idx = (short) te.idx;
				o.block = te.stored_block;
				o.meta = te.stored_block_meta;
				o.te_data = null;
				break;
			case 3:
				o.ref_group = RefGroups.ENTITY;
				o.ref_idx = (short) te.idx;
				o.block = Blocks.air;
				o.meta = 0;
				o.te_data = null;
				break;
			default:
				break;
			}
		}
		
		short idx;
		for (idx = 0; idx < palette.size(); idx++) {
			if (palette.get(idx).equals(o)) {
				break;
			}
		}
		if (idx == palette.size()) {
			palette.add(o);
		}
		
		this.idx = idx;
		this.x = x - sx;
		this.y = y - sy;
		this.z = z - sz;
	}
	
	public PaletteIndex(NBTTagCompound tag)
	{
		this.idx = tag.getShort("idx");
		this.x = tag.getShort("x");
		this.y = tag.getShort("y");
		this.z = tag.getShort("z");
	}
	
	public NBTTagCompound saveToNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setShort("idx", this.idx);
		tag.setShort("x", (short) this.x);
		tag.setShort("y", (short) this.y);
		tag.setShort("z", (short) this.z);
		
		return tag;
	}
	
	public void placeWithStartPos(StructurePiece piece, int phase, Random rand, World world, int sx, int sy, int sz, long optionals)
	{
		piece.palette.get(this.idx).placeAtPos(piece, phase, rand, world, this.x + sx, this.y + sy, this.z + sz, optionals);
	}
}
