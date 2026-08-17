package com.captrojo.resadditae.world.gen.structure;

import java.util.Random;

import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.gen.structure.nbt.StructureComponentNBT;
import com.captrojo.resadditae.world.gen.structure.nbt.StructurePieceNBT;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenWoodenHouse extends MapGenScattered
{
	public static void load()
	{
		MapGenWoodenHouse.ComponentHouse.piece = new StructurePieceNBT(ResAdditae.resource("structures/wooden_house/house.nbt"));
		
		MapGenStructureIO.registerStructure(MapGenWoodenHouse.Start.class, "RA_WoodenHouse");
		MapGenStructureIO.func_143031_a(MapGenWoodenHouse.ComponentHouse.class, "RA_WH_House");
	}
	
	public MapGenWoodenHouse()
	{
		super(
			"RAWoodenHouse",
//			new BiomeGenBase[] {
//				BiomeGenBase.forest,
//				BiomeGenBase.birchForest,
//				BiomeGenBase.taiga,
//				BiomeGenBase.roofedForest
//			},
			null,
//			WorldGenConfig.wooden_house_excl_rad,
//			WorldGenConfig.wooden_house_min_dist,
//			WorldGenConfig.wooden_house_max_dist
			0,
			4,
			6
		);
	}

	@Override
	protected StructureStart getStructureStart(int chunk_x, int chunk_z)
	{
		return new MapGenWoodenHouse.Start(this.worldObj, this.rand, chunk_x, chunk_z);
	}
	
	public static class Start extends StructureStart
	{
		public Start()
		{
		}
		
		public Start(World world, Random rand, int chunk_x, int chunk_z)
		{
			super(chunk_x, chunk_z);
			this.components.add(new MapGenWoodenHouse.ComponentHouse(chunk_x << 4, 64, chunk_z << 4, rand));
			this.updateBoundingBox();
		}
	}
	
	public static class ComponentHouse extends StructureComponentNBT
	{
		static StructurePieceNBT piece;
		
		public ComponentHouse()
		{
		}
		
		public ComponentHouse(int x, int y, int z, Random rand)
		{
			super(piece, x, y, z, rand);
		}
		
		{
			this.use_h_pos = true;
		}
	}
}
