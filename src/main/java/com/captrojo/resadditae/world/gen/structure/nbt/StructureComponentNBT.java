package com.captrojo.resadditae.world.gen.structure.nbt;

import java.util.Random;

import com.captrojo.resadditae.config.common.DebugConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.util.Consts;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public abstract class StructureComponentNBT extends StructureComponent
{
	public static int getAverageGroundLevel(World world, StructureBoundingBox sbb, StructureBoundingBox cbb)
	{
		int yt = 0;
		int yd = 0;

		for (int z = sbb.minZ; z <= sbb.maxZ; ++z) {
			for (int x = sbb.minX; x <= sbb.maxX; ++x) {
				if (!cbb.isVecInside(x, 64, z)) {
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
	
	public StructurePieceNBT piece;

	/* Used for determining average ground level. */
	public boolean use_h_pos = false;
	int h_pos;
	
	public StructureComponentNBT(StructurePieceNBT piece)
	{
		this.piece = piece;
	}

	public StructureComponentNBT(StructurePieceNBT piece, int x, int y, int z, Random rand)
	{
		this(piece, x, y, z, rand.nextInt(4));
	}

	public StructureComponentNBT(StructurePieceNBT piece, int x, int y, int z, int dir)
	{
		super(0);
		
		this.piece = piece;
		this.h_pos = -1;

		this.coordBaseMode = dir;

		if (this.piece != null) {
			this.createBB(x, y, z);
		}
	}
	
	public void createBB(int x, int y, int z)
	{
		/* Initially create the BB to just be the size of the piece */
		int sy = this.piece.size_y;
		int sx, sz;
		if (this.coordBaseMode == Consts.WEST || this.coordBaseMode == Consts.EAST) {
			sx = this.piece.size_z;
			sz = this.piece.size_x;
		} else {
			sx = this.piece.size_x;
			sz = this.piece.size_z;
		}
		this.boundingBox = new StructureBoundingBox(0, 0, 0, sx, sy, sz);
		
		/* Calculate origin */
		int ox = this.getXWithOffset(this.piece.origin_x, this.piece.origin_z);
		int oy = this.piece.origin_y;
		int oz = this.getZWithOffset(this.piece.origin_x, this.piece.origin_z);
		
		/* Calculate final position */
		int ax = x - ox;
		int ay = y - oy;
		int az = z - oz;
		this.boundingBox.offset(ax, ay, az);
	}
	
	public void offset(int x, int y, int z)
	{
		this.boundingBox.offset(x, y, z);
	}
	
	public void setHPos(int h_pos)
	{
		this.h_pos = h_pos;
		this.boundingBox.offset(0, -this.boundingBox.minY, 0);
		this.boundingBox.offset(0, this.h_pos - this.piece.origin_y, 0);
	}

	protected boolean updateHPos(World world, StructureBoundingBox bb)
	{
		if (this.use_h_pos && this.h_pos < 0) {
			this.h_pos = this.getAverageGroundLevel(world, bb);
			if (this.h_pos < 0) {
				return false;
			}
			this.setHPos(this.h_pos);
		}
		return true;
	}

	protected int getAverageGroundLevel(World world, StructureBoundingBox bb)
	{
		return StructureComponentNBT.getAverageGroundLevel(world, this.boundingBox, bb);
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
		
		if (this.piece.foundation != null) {
			for (int x1 = 0; x1 < this.piece.size_x; x1++) {
				for (int z1 = 0; z1 < this.piece.size_z; z1++) {
					int x = this.getXWithOffset(x1, z1);
					int z = this.getZWithOffset(x1, z1);
				yloop:
					for (int y = this.getYWithOffset(-1); y > 0; y--) {
						if (!bb.isVecInside(x, y, z)) {
							break yloop;
						}
						Block existing = world.getBlock(x, y, z);
						if (existing.isOpaqueCube()) {
							break yloop;
						}
						this.piece.foundation.place(world, rand, x, y, z, this.piece, this.coordBaseMode, 0, null);
					}
				}
			}
		}

		for (int phase = 0; phase <= 1; phase++) {
			for (PaletteIndex index : this.piece.pal_indices) {
				int x = this.getXWithOffset(index.x, index.z);
				int y = this.getYWithOffset(index.y);
				int z = this.getZWithOffset(index.x, index.z);
				if (!bb.isVecInside(x, y, z)) {
					continue;
				}
				PaletteEntry entry = this.piece.palette.getEntry(index.idx);
				entry.place(world, rand, x, y, z, this.piece, this.coordBaseMode, phase, null);
			}
		}
		
		if (ResAdditae.testing_mode || DebugConfig.log_structure_gens) {
			ResAdditae.LOG.info(String.format(
				"Generated NBT structure %s at (%d, %d, %d) (dir %d)",
				MapGenStructureIO.func_143036_a(this),
				this.getXWithOffset(0, 0),
				this.getYWithOffset(0),
				this.getZWithOffset(0, 0),
				this.coordBaseMode
			));
		}
		if (ResAdditae.testing_mode) {
			this.placeBlockAtCurrentPosition(world, Blocks.diamond_block, 0, 0, 0, 0, bb);
			this.placeBlockAtCurrentPosition(world, Blocks.diamond_block, 0, this.piece.size_x - 1, 0, 0, bb);
			this.placeBlockAtCurrentPosition(world, Blocks.diamond_block, 0, 0, this.piece.size_y - 1, 0, bb);
			this.placeBlockAtCurrentPosition(world, Blocks.diamond_block, 0, this.piece.size_x - 1, this.piece.size_y - 1, 0, bb);
			this.placeBlockAtCurrentPosition(world, Blocks.diamond_block, 0, 0, 0, this.piece.size_z - 1, bb);
			this.placeBlockAtCurrentPosition(world, Blocks.diamond_block, 0, this.piece.size_x - 1, 0, this.piece.size_z - 1, bb);
			this.placeBlockAtCurrentPosition(world, Blocks.diamond_block, 0, 0, this.piece.size_y - 1, this.piece.size_z - 1, bb);
			this.placeBlockAtCurrentPosition(world, Blocks.diamond_block, 0, this.piece.size_x - 1, this.piece.size_y - 1, this.piece.size_z - 1, bb);
			this.placeBlockAtCurrentPosition(world, Blocks.emerald_block, 0, this.piece.origin_x, this.piece.origin_y, this.piece.origin_z, bb);
		}

		return true;
	}
	
	@Override
	protected int getXWithOffset(int x, int z)
	{
		return this.getXWithOffset(this.boundingBox, x, z);
	}
	
	@Override
	protected int getZWithOffset(int x, int z)
	{
		return this.getZWithOffset(this.boundingBox, x, z);
	}
	
	protected int getXWithOffset(StructureBoundingBox bb, int x, int z)
	{
		switch (this.coordBaseMode) {
		case Consts.NORTH:
			return bb.minX + x;
		case Consts.SOUTH:
			return bb.maxX - x;
		case Consts.EAST:
			return bb.maxX - z;
		case Consts.WEST:
			return bb.minX + z;
		default:
			return x;
		}
	}
	
	protected int getZWithOffset(StructureBoundingBox bb, int x, int z)
	{
		switch (this.coordBaseMode) {
		case Consts.NORTH:
			return bb.minZ + z;
		case Consts.SOUTH:
			return bb.maxZ - z;
		case Consts.EAST:
			return bb.minZ + x;
		case Consts.WEST:
			return bb.maxZ - x;
		default:
			return z;
		}
	}
}
