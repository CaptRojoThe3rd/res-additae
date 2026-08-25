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
		MapGenWoodenHouse.ComponentHouse.piece = MapGenScattered.loadPiece("wooden_house/house");
		MapGenWoodenHouse.ComponentFarm.piece = MapGenScattered.loadPiece("wooden_house/farm");
		
		MapGenStructureIO.registerStructure(MapGenWoodenHouse.Start.class, "RA_WoodenHouse");
		MapGenStructureIO.func_143031_a(MapGenWoodenHouse.ComponentHouse.class, "RA_WH_House");
		MapGenStructureIO.func_143031_a(MapGenWoodenHouse.ComponentFarm.class, "RA_WH_Farm");
	}
	
	public MapGenWoodenHouse()
	{
		super(
			"RAWoodenHouse",
			new BiomeGenBase[] {
				BiomeGenBase.forest,
				BiomeGenBase.birchForest,
				BiomeGenBase.taiga,
				BiomeGenBase.roofedForest
			},
//			null,
			WorldGenConfig.wooden_house_excl_rad,
			WorldGenConfig.wooden_house_min_dist,
			WorldGenConfig.wooden_house_max_dist
//			0,
//			4,
//			6
		);
		
		this.range = 3;
	}

	@Override
	protected StructureStart getStructureStart(int chunk_x, int chunk_z)
	{
		return new MapGenWoodenHouse.Start(this.worldObj, this.rand, chunk_x, chunk_z);
	}
	
	public static class Start extends StructureStartLH
	{
		public Start()
		{
		}
		
		public Start(World world, Random rand, int chunk_x, int chunk_z)
		{
			super(chunk_x, chunk_z);
			int x = chunk_x << 4, z = chunk_z << 4;
			int dir = rand.nextInt(4);
			
			this.components.add(new MapGenWoodenHouse.ComponentHouse(x, 64, z, dir));
			if (rand.nextBoolean()) {
				StructureComponentNBT farm = new MapGenWoodenHouse.ComponentFarm(x, 64, z, dir);
				this.components.add(farm);
			}
			
			this.updateBoundingBox();
		}
	}
	
	public static class ComponentHouse extends StructureComponentNBT
	{
		static StructurePieceNBT piece;
		
		public ComponentHouse()
		{
			super(piece);
		}
		
		public ComponentHouse(int x, int y, int z, int dir)
		{
			super(piece, x, y, z, dir);
		}
	}
	
	public static class ComponentFarm extends StructureComponentNBT
	{
		static StructurePieceNBT piece;
		
		public ComponentFarm()
		{
			super(piece);
		}
		
		public ComponentFarm(int x, int y, int z, int dir)
		{
			super(piece, x, y, z, dir);
		}
	}
}
