package com.captrojo.resadditae.world.gen.structure.nbt;

import java.util.Random;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public abstract class StructureComponentNBT extends StructureComponent
{
	public StructurePieceNBT piece;

	/* Used for determining average ground level. */
	public boolean use_h_pos;
	int h_pos;

	public StructureComponentNBT()
	{
	}

	public StructureComponentNBT(StructurePieceNBT piece, int x, int y, int z, Random rand)
	{
		this(piece, x, y, z, rand.nextInt(4));
	}

	public StructureComponentNBT(StructurePieceNBT piece, int x, int y, int z, int rotation)
	{
		super(0);
		
		this.piece = piece;
		this.h_pos = -1;

		this.coordBaseMode = rotation;

		int min_x = x - this.piece.origin_x;
		int min_y = y - this.piece.origin_y;
		int min_z = z - this.piece.origin_z;
		int max_x = min_x + this.piece.size_x;
		int max_y = min_y + this.piece.size_y;
		int max_z = min_z + this.piece.size_z;
		this.boundingBox = new StructureBoundingBox(min_x, min_y, min_z, max_x, max_y, max_z);
	}

	protected boolean updateHPos(World world, StructureBoundingBox bb)
	{
		if (this.use_h_pos) {
			this.h_pos = this.getAverageGroundLevel(world, bb);
			if (this.h_pos < 0) {
				return false;
			}
			this.boundingBox.offset(0, this.h_pos - this.boundingBox.minY, 0);
		}
		return true;
	}

	protected int getAverageGroundLevel(World world, StructureBoundingBox bb)
	{
		int yt = 0;
		int yd = 0;

		for (int z = this.boundingBox.minZ; z <= this.boundingBox.maxZ; ++z) {
			for (int x = this.boundingBox.minX; x <= this.boundingBox.maxX; ++x) {
				if (!bb.isVecInside(x, 64, z)) {
					continue;
				}
				yt += Math.max(world.getTopSolidOrLiquidBlock(x, z), world.provider.getAverageGroundLevel());
				++yd;
			}
		}

		if (yd == 0) {
			return -1;
		}
		return yt / yd;
	}

	/* saveSpecificToNBT */
	@Override
	protected void func_143012_a(NBTTagCompound nbt)
	{
		if (this.use_h_pos) {
			nbt.setInteger("HPos", this.h_pos);
		}
	}

	/* loadSpecificFromNBT */
	@Override
	protected void func_143011_b(NBTTagCompound nbt)
	{
		if (this.use_h_pos) {
			this.h_pos = nbt.getInteger("HPos");
		}
	}

	@Override
	public boolean addComponentParts(World world, Random rand, StructureBoundingBox bb)
	{
		if (!this.updateHPos(world, bb)) {
			return true;
		}
		
		ResAdditae.LOG.info("Generated NBT structure");

		for (int phase = 0; phase <= 1; phase++) {
			for (PaletteIndex index : this.piece.pal_indices) {
				int x = this.getXWithOffset(index.x, index.z);
				int y = this.getYWithOffset(index.y);
				int z = this.getZWithOffset(index.x, index.z);
				if (!bb.isVecInside(x, y, z)) {
					continue;
				}
				PaletteEntry entry = this.piece.palette.getEntry(index.idx);
				entry.place(world, rand, x, y, z, this.piece, this.coordBaseMode, phase, -1);
			}
		}

		return true;
	}
}
