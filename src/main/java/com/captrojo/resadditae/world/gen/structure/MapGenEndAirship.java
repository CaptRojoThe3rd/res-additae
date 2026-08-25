package com.captrojo.resadditae.world.gen.structure;

import java.util.Random;

import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.gen.structure.nbt.StructureComponentNBT;
import com.captrojo.resadditae.world.gen.structure.nbt.StructurePieceNBT;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenEndAirship extends MapGenScattered
{
	public static void load()
	{
 		MapGenEndAirship.Component.piece = MapGenScattered.loadPiece("end_airship/airship");
		
		MapGenStructureIO.registerStructure(MapGenEndAirship.Start.class, "RA_EndAirship");
		MapGenStructureIO.func_143031_a(MapGenEndAirship.Component.class, "RA_EA_Airship");
	}
	
	public static int getDirection(int chunk_x, int chunk_z)
	{
		double theta = Math.atan2(chunk_z, chunk_x) * 180 / Math.PI;
		if (theta < 0) {
			theta += 360;
		}
		
		/* We swap north/south because the structure is saved facing south */
		if (theta > 315 || theta <= 45) {
			return 3;
		}
		if (theta > 45 && theta <= 135) {
			return 0;
		}
		if (theta > 135 && theta <= 225) {
			return 1;
		}
		if (theta > 225 && theta <= 315) {
			return 2;
		}
		return 0;
	}
	
	public MapGenEndAirship()
	{
		super(
			"RAEndAirship",
			null,
			WorldGenConfig.end_airship_excl_rad,
			WorldGenConfig.end_airship_min_dist,
			WorldGenConfig.end_airship_max_dist
		);
		
		this.range = 6;
	}

	@Override
	protected StructureStart getStructureStart(int chunk_x, int chunk_z)
	{
		return new Start(this.worldObj, this.rand, chunk_x, chunk_z);
	}
	
	public static class Start extends StructureStart
	{
		public Start()
		{
		}
		
		public Start(World world, Random rand, int chunk_x, int chunk_z)
		{
			super(chunk_x, chunk_z);
			
			int x = chunk_x << 4, z = chunk_z << 4;
			int y = 140 + rand.nextInt(60);
			int dir = MapGenEndAirship.getDirection(chunk_x, chunk_z);
			
			this.components.add(new MapGenEndAirship.Component(x, y, z, dir));
			
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
		
		public Component(int x, int y, int z, int dir)
		{
			super(piece, x, y, z, dir);
		}
	}
}
