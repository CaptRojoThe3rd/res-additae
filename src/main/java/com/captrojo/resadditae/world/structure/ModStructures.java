package com.captrojo.resadditae.world.structure;

import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.main.NBTHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.SpacedThingCheck;

import net.minecraft.world.biome.BiomeGenBase;

public class ModStructures
{
	public static StructureVerySimple dark_dungeon;
	public static StructureSnowDungeon snow_dungeon;
	public static StructureVerySimple wood_house;
	
	public static StructureEndAirship end_airship;
	
	public static void load()
	{
		dark_dungeon = new StructureFixedUnderground(
			"dark dungeon",
			NBTHlpr.loadNBTFromResource(ResAdditae.resource("structures/dark_dungeon.nbt")),
			"Dark Dungeon".hashCode(),
			null,
			CommonConfig.WorldGen.dark_dungeon_excl_rad,
			CommonConfig.WorldGen.dark_dungeon_min_dist,
			CommonConfig.WorldGen.dark_dungeon_max_dist,
			5
		);
		
		snow_dungeon = new StructureSnowDungeon();
		
		wood_house = new StructureVerySimple(
			"wood house",
			NBTHlpr.loadNBTFromResource(ResAdditae.resource("structures/wood_house.nbt")),
			"Wood House".hashCode(),
			new BiomeGenBase[] {
				BiomeGenBase.forest,
				BiomeGenBase.birchForest,
				BiomeGenBase.taiga,
				BiomeGenBase.roofedForest
			},
			CommonConfig.WorldGen.wooden_house_excl_rad,
			CommonConfig.WorldGen.wooden_house_min_dist,
			CommonConfig.WorldGen.wooden_house_max_dist
		);
		
		end_airship = new StructureEndAirship(
			"end airship",
			NBTHlpr.loadNBTFromResource(ResAdditae.resource("structures/end_airship.nbt")),
			"End Airship".hashCode(),
			null,
			CommonConfig.WorldGen.end_airship_excl_rad,
			CommonConfig.WorldGen.end_airship_min_dist,
			CommonConfig.WorldGen.end_airship_max_dist
		);
	}
	
	protected static StructurePiece loadPiece(String path)
	{
		return new StructurePiece(NBTHlpr.loadNBTFromResource(ResAdditae.resource("structures/" + path + ".nbt")));
	}
	
	protected static StructurePiece[] loadPieceAndRotations(String path)
	{
		StructurePiece[] pieces = new StructurePiece[4];
		pieces[0] = loadPiece(path);
		pieces[1] = pieces[0].getRotated90();
		pieces[2] = pieces[1].getRotated90();
		pieces[3] = pieces[2].getRotated90();
		return pieces;
	}
}
