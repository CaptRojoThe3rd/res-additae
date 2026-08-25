package com.captrojo.resadditae.world.gen.structure;

import java.util.Iterator;
import java.util.Random;

import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.gen.structure.nbt.StructureComponentNBT;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * "Linked Height"
 */
public class StructureStartLH extends StructureStart
{
	int h_pos = -1;
	
	public StructureStartLH()
	{
	}
	
	public StructureStartLH(int chunk_x, int chunk_z)
	{
		super(chunk_x, chunk_z);
	}
	
	public void generateStructure(World world, Random rand, StructureBoundingBox bb)
	{
		if (this.h_pos == -1) {
			this.h_pos = StructureComponentNBT.getAverageGroundLevel(world, this.boundingBox, bb);
			if (this.h_pos == -1) {
				return;
			}
			Iterator it = this.components.iterator();
			while (it.hasNext()) {
				StructureComponentNBT sc = (StructureComponentNBT) it.next();
				sc.setHPos(this.h_pos);
			}
		}
		super.generateStructure(world, rand, bb);
	}
	
	/* saveSpecificToNBT */
	@Override
	public void func_143022_a(NBTTagCompound nbt)
	{
		if (this.h_pos != -1) {
			nbt.setInteger("HPos", this.h_pos);
		}
	}
	
	/* loadSpecificFromNBT */
	@Override
	public void func_143017_b(NBTTagCompound nbt)
	{
		this.h_pos = nbt.hasKey("HPos") ? nbt.getInteger("HPos") : -1;
	}
}
