package com.captrojo.resadditae.world.gen.structure;

import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.util.Consts;
import com.captrojo.resadditae.world.gen.structure.nbt.StructureComponentNBT;
import com.captrojo.resadditae.world.gen.structure.nbt.StructurePieceNBT;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenDarkDungeon extends MapGenScattered
{
	public static void load()
	{
		MapGenDarkDungeon.Component.piece = MapGenScattered.loadPiece("dark_dungeon");
		
		MapGenStructureIO.registerStructure(MapGenDarkDungeon.Start.class, "RA_DarkDungeon");
		MapGenStructureIO.func_143031_a(MapGenDarkDungeon.Component.class, "RA_DD_Dungeon");
	}
	
	public MapGenDarkDungeon()
	{
		super(
			"RADarkDungeon",
			null,
			WorldGenConfig.dark_dungeon_excl_rad,
			WorldGenConfig.dark_dungeon_min_dist,
			WorldGenConfig.dark_dungeon_max_dist
		);
		
		this.range = 2;
	}

	@Override
	protected StructureStart getStructureStart(int chunk_x, int chunk_z)
	{
		return new MapGenDarkDungeon.Start(chunk_x, chunk_z);
	}
	
	public static class Start extends StructureStart
	{
		public Start()
		{
		}
		
		public Start(int chunk_x, int chunk_z)
		{
			super(chunk_x, chunk_z);
			this.components.add(new MapGenDarkDungeon.Component(chunk_x, chunk_z));
			this.updateBoundingBox();
		}
	}
	
	public static class Component extends StructureComponentNBT
	{
		static StructurePieceNBT piece;
		
		public Component()
		{
			super(piece);
		}
		
		public Component(int chunk_x, int chunk_z)
		{
			super(piece, chunk_x << 4, 20, chunk_z << 4, Consts.NORTH);
		}
	}
}
