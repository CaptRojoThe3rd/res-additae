package com.captrojo.resadditae.world.gen.structure;

import com.captrojo.resadditae.world.gen.structure.nbt.StructureComponentNBT;
import com.captrojo.resadditae.world.gen.structure.nbt.StructurePieceNBT;

import net.minecraft.nbt.NBTTagCompound;

public class StructureComponentMulti extends StructureComponentNBT
{
	StructurePieceNBT[] pieces;
	int piece_idx;
	
	public StructureComponentMulti(StructurePieceNBT[] pieces)
	{
		super(null);
		this.pieces = pieces;
	}
	
	public StructureComponentMulti(StructurePieceNBT[] pieces, int idx, int x, int y, int z, int dir)
	{
		super(pieces[idx], x, y, z, dir);
		this.pieces = pieces;
		this.piece_idx = idx;
	}

	/* saveSpecificToNBT */
	@Override
	protected void func_143012_a(NBTTagCompound nbt)
	{
		nbt.setByte("Piece", (byte) this.piece_idx);
	}

	/* loadSpecificFromNBT */
	@Override
	protected void func_143011_b(NBTTagCompound nbt)
	{
		this.piece = this.pieces[nbt.getByte("Piece")];
	}
}
