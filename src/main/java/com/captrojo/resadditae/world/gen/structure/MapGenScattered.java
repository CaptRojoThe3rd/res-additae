package com.captrojo.resadditae.world.gen.structure;

import com.captrojo.resadditae.config.common.DebugConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.SpacedThingCheck;
import com.captrojo.resadditae.world.gen.ModWorldGen;
import com.captrojo.resadditae.world.gen.structure.nbt.StructurePieceNBT;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class MapGenScattered extends MapGenStructure
{
	public static StructurePieceNBT loadPiece(String path)
	{
		ResourceLocation rl = ResAdditae.resource("structures/" + path + ".nbt");
		ResAdditae.LOG.info("Loading structure " + rl.toString());
		return new StructurePieceNBT(rl);
	}
	
	final String name;
	final SpacedThingCheck pos_check;
	
	public MapGenScattered(String name, BiomeGenBase[] biomes, int excl_rad, int min_dist, int max_dist)
	{
		this.name = name;
		this.pos_check = new SpacedThingCheck(this.name.hashCode(), biomes, excl_rad, min_dist, max_dist);
	}
	
	@Override
	public String func_143025_a()
	{
		return this.name;
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunk_x, int chunk_z)
	{
		int i = this.pos_check.allChecks(this.worldObj, chunk_x, chunk_z);
		if (
			i > 1 && (ResAdditae.testing_mode || DebugConfig.log_failed_structure_gens) &&
			ModWorldGen.cur_chunk_x == chunk_x && ModWorldGen.cur_chunk_z == chunk_z
		) {
			ResAdditae.LOG.info(String.format(
				"Did not generate structure %s: %s",
				this.name, SpacedThingCheck.FAILMAP_INT_STR.get(i)
			));
		}
		return i == 0;
	}
	
	@Override
	abstract protected StructureStart getStructureStart(int chunk_x, int chunk_z);
}
