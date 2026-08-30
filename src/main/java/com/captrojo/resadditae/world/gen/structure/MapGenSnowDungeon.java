package com.captrojo.resadditae.world.gen.structure;

import java.util.Random;

import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.util.Consts;
import com.captrojo.resadditae.util.MiscHlpr;
import com.captrojo.resadditae.world.ModWorldData;
import com.captrojo.resadditae.world.gen.structure.nbt.StructureComponentNBT;
import com.captrojo.resadditae.world.gen.structure.nbt.StructurePieceNBT;
import com.captrojo.resadditae.world.snowdungeon.SnowDungeon;

import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenSnowDungeon extends MapGenScattered
{
	static final int[][] UGND_START_NODES = {
		{0, -18, 7, Consts.SOUTH},
		{-7, -19, 0, Consts.WEST},
		{0, -20, -7, Consts.NORTH},
		{7, -21, 0, Consts.EAST},
		{0, -34, 7, Consts.SOUTH},
		{-7, -35, 0, Consts.WEST},
		{0, -36, -7, Consts.NORTH},
		{7, -37, 0, Consts.EAST}
	};
	
	public static void load()
	{
		MapGenSnowDungeon.Pyramid.piece = MapGenScattered.loadPiece("snow_dungeon/pyramid");
		MapGenSnowDungeon.Basin.pieces = new StructurePieceNBT[12];
		for (int i = 1; i <= 12; i++) {
			MapGenSnowDungeon.Basin.pieces[i - 1] = MapGenScattered.loadPiece("snow_dungeon/basin" + Integer.toString(i));
		}
		MapGenSnowDungeon.Staircase.piece = MapGenScattered.loadPiece("snow_dungeon/staircase");
		MapGenSnowDungeon.CrossHallway.piece = MapGenScattered.loadPiece("snow_dungeon/cross_hallway");
		MapGenSnowDungeon.StraightHallway.piece = MapGenScattered.loadPiece("snow_dungeon/straight_hallway");
		MapGenSnowDungeon.Treasure.pieces = new StructurePieceNBT[6];
		MapGenSnowDungeon.ChallengeRoomA.pieces = new StructurePieceNBT[6];
		MapGenSnowDungeon.ChallengeRoomB.pieces = new StructurePieceNBT[6];
		MapGenSnowDungeon.ChallengeRoomC.pieces = new StructurePieceNBT[6];
		for (int i = 3; i <= 8; i++) {
			MapGenSnowDungeon.Treasure.pieces[i - 3] = MapGenScattered.loadPiece("snow_dungeon/treasure_" + Integer.toString(i));
			MapGenSnowDungeon.ChallengeRoomA.pieces[i - 3] = MapGenScattered.loadPiece("snow_dungeon/room_a_" + Integer.toString(i));
			MapGenSnowDungeon.ChallengeRoomB.pieces[i - 3] = MapGenScattered.loadPiece("snow_dungeon/room_b_" + Integer.toString(i));
			MapGenSnowDungeon.ChallengeRoomC.pieces[i - 3] = MapGenScattered.loadPiece("snow_dungeon/room_c_" + Integer.toString(i));
		}
		
		MapGenStructureIO.registerStructure(MapGenSnowDungeon.Start.class, "RA_SnowDungeon");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.Pyramid.class, "RA_SD_Pyramid");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.Basin.class, "RA_SD_Basin");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.Staircase.class, "RA_SD_Staircase");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.CrossHallway.class, "RA_SD_CrossHallway");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.StraightHallway.class, "RA_SD_StraightHallway");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.Treasure.class, "RA_SD_Treasure");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.ChallengeRoomA.class, "RA_SD_RoomA");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.ChallengeRoomB.class, "RA_SD_RoomB");
		MapGenStructureIO.func_143031_a(MapGenSnowDungeon.ChallengeRoomC.class, "RA_SD_RoomC");
	}
	
	public MapGenSnowDungeon()
	{
		super(
			"RASnowDungeon",
			new BiomeGenBase[] {
				BiomeGenBase.icePlains
			},
//			null,
//			0,
//			16,
//			24
			WorldGenConfig.snow_dungeon_excl_rad,
			WorldGenConfig.snow_dungeon_min_dist,
			WorldGenConfig.snow_dungeon_max_dist
		);
		
		this.range = 16;
	}

	@Override
	protected StructureStart getStructureStart(int chunk_x, int chunk_z)
	{
		return new MapGenSnowDungeon.Start(this.worldObj, this.rand, chunk_x, chunk_z);
	}
	
	public static class Start extends StructureStart
	{
		static final int[][] HALLWAY_ROTS = {
			Consts.IDENTITY_ARR,
			Direction.rotateLeft,
			Direction.rotateRight
		};
		
		public Start()
		{
		}
		
		public Start(World world, Random rand, int chunk_x, int chunk_z)
		{
			super(chunk_x, chunk_z);
			int x = chunk_x << 4, y = 72, z = chunk_z << 4;

			MapGenSnowDungeon.Pyramid pyramid = new MapGenSnowDungeon.Pyramid(x, y, z);
			this.components.add(pyramid);
			
			Integer[] arr = MiscHlpr.getUniqueRandomInts(rand, 4, MapGenSnowDungeon.Basin.pieces.length - 2);
			this.components.add(new MapGenSnowDungeon.Basin(x - 11, y + 1, z - 11, arr[0]));
			this.components.add(new MapGenSnowDungeon.Basin(x + 11, y + 1, z - 11, arr[1]));
			this.components.add(new MapGenSnowDungeon.Basin(x - 11, y + 1, z + 11, arr[2]));
			this.components.add(new MapGenSnowDungeon.Basin(x + 11, y + 1, z + 11, arr[3]));
			
			this.components.add(new MapGenSnowDungeon.Staircase(x, y, z));
			
			int d = rand.nextInt(2);
			this.generateFromStartNode(rand, MapGenSnowDungeon.UGND_START_NODES[0 + d], 0, x, y, z);
			this.generateFromStartNode(rand, MapGenSnowDungeon.UGND_START_NODES[2 + d], 0, x, y, z);
			this.generateFromStartNode(rand, MapGenSnowDungeon.UGND_START_NODES[5 - d], 3, x, y, z);
			this.generateFromStartNode(rand, MapGenSnowDungeon.UGND_START_NODES[7 - d], 3, x, y, z);
			
			this.updateBoundingBox();
			
			ModWorldData mwd = ModWorldData.getForWorld(world);
			SnowDungeon sd = new SnowDungeon(x, y, z, pyramid.getBoundingBox());
			mwd.addSnowDungeon(sd);
		}
		
		void generateFromStartNode(Random rand, int[] start_node, int base_lvl, int x, int y, int z)
		{
			int xn = start_node[0] + x;
			int yn = start_node[1] + y;
			int zn = start_node[2] + z;
			int dn = start_node[3];
			
			for (int i = 0; i < 3; i++) {
				this.components.add(new MapGenSnowDungeon.CrossHallway(xn, yn, zn, dn));
				Integer[] rots = MiscHlpr.getUniqueRandomInts(rand, 2, MapGenSnowDungeon.Start.HALLWAY_ROTS.length);
				int[] rot_t = MapGenSnowDungeon.Start.HALLWAY_ROTS[rots[0]];
				int[] rot_r = MapGenSnowDungeon.Start.HALLWAY_ROTS[rots[1]];
				
				int xt = xn + this.getHallwayXOffs(dn, rot_t[dn]);
				int zt = zn + this.getHallwayZOffs(dn, rot_t[dn]);
				this.components.add(new MapGenSnowDungeon.Treasure(base_lvl + i, xt, yn, zt, rot_t[dn]));
				
				int xr = xn + this.getHallwayXOffs(dn, rot_r[dn]);
				int zr = zn + this.getHallwayZOffs(dn, rot_r[dn]);
				StructureComponentNBT d_room = this.createRandomDungeonRoom(rand, base_lvl + i, xr, yn, zr, rot_r[dn]);
				this.components.add(d_room);
				
				xn = xr;
				zn = zr;
				dn = rot_r[dn];
				switch (dn) {
				case Consts.NORTH:
					zn -= (d_room.getBoundingBox().getZSize() - 2);
					break;
				case Consts.EAST:
					xn += (d_room.getBoundingBox().getXSize() - 2);
					break;
				case Consts.SOUTH:
					zn += (d_room.getBoundingBox().getZSize() - 2);
					break;
				case Consts.WEST:
					xn -= (d_room.getBoundingBox().getXSize() - 2);
					break;
				}
			}
		}
		
		StructureComponentNBT createRandomDungeonRoom(Random rand, int lvl, int x, int y, int z, int dir)
		{
			switch (rand.nextInt(3)) {
			default:
			case 0:
				return new MapGenSnowDungeon.ChallengeRoomA(lvl, x, y, z, dir);
			case 1:
				return new MapGenSnowDungeon.ChallengeRoomB(lvl, x, y, z, dir);
			case 2:
				return new MapGenSnowDungeon.ChallengeRoomC(lvl, x, y, z, dir);
			}
		}
		
		int getHallwayXOffs(int olddir, int newdir)
		{
			switch (olddir) {
			case Consts.NORTH:
				switch (newdir) {
				case Consts.WEST:
					return -8;
				case Consts.EAST:
					return 8;
				default:
					return 0;
				}
			case Consts.EAST:
				switch (newdir) {
				case Consts.EAST:
					return 15;
				case Consts.NORTH:
				case Consts.SOUTH:
					return 8;
				default:
					return 0;
				}
			case Consts.SOUTH:
				switch (newdir) {
				case Consts.EAST:
					return 7;
				case Consts.WEST:
					return -8;
				default:
					return 0;
				}
			case Consts.WEST:
				switch (newdir) {
				case Consts.SOUTH:
				case Consts.NORTH:
					return -8;
				case Consts.WEST:
					return -15;
				default:
					return 0;
				}
			default:
				return 0;
			}
		}
		
		int getHallwayZOffs(int olddir, int newdir)
		{
			switch (olddir) {
			case Consts.NORTH:
				switch (newdir) {
				case Consts.NORTH:
					return -15;
				case Consts.EAST:
				case Consts.WEST:
					return -8;
				default:
					return 0;
				}
			case Consts.EAST:
				switch (newdir) {
				case Consts.NORTH:
					return -7;
				case Consts.SOUTH:
					return 8;
				default:
					return 0;
				}
			case Consts.SOUTH:
				switch (newdir) {
				case Consts.EAST:
				case Consts.WEST:
					return 8;
				case Consts.SOUTH:
					return 15;
				default:
					return 0;
				}
			case Consts.WEST:
				switch (newdir) {
				case Consts.SOUTH:
					return 7;
				case Consts.NORTH:
					return -8;
				default:
					return 0;
				}
			default:
				return 0;
			}
		}
	}
	
	public static class Pyramid extends StructureComponentNBT
	{
		static StructurePieceNBT piece;
		
		public Pyramid()
		{
			super(piece);
		}
		
		public Pyramid(int x, int y, int z)
		{
			super(piece, x, y, z, Consts.NORTH);
		}
	}
	
	public static class Basin extends StructureComponentMulti
	{
		static StructurePieceNBT[] pieces;
		
		public Basin()
		{
			super(MapGenSnowDungeon.Basin.pieces);
		}
		
		public Basin(int x, int y, int z, int idx)
		{
			super(MapGenSnowDungeon.Basin.pieces, idx, x, y, z, Consts.NORTH);
		}
	}
	
	public static class Staircase extends StructureComponentNBT
	{
		static StructurePieceNBT piece;
		
		public Staircase()
		{
			super(MapGenSnowDungeon.Staircase.piece);
		}
		
		public Staircase(int x, int y, int z)
		{
			super(MapGenSnowDungeon.Staircase.piece, x, y, z, Consts.NORTH);
		}
	}
	
	public static class CrossHallway extends StructureComponentNBT
	{
		static StructurePieceNBT piece;
		
		public CrossHallway()
		{
			super(MapGenSnowDungeon.CrossHallway.piece);
		}
		
		public CrossHallway(int x, int y, int z, int dir)
		{
			super(MapGenSnowDungeon.CrossHallway.piece, x, y, z, dir);
		}
	}
	
	public static class StraightHallway extends StructureComponentNBT
	{
		static StructurePieceNBT piece;
		
		public StraightHallway()
		{
			super(MapGenSnowDungeon.StraightHallway.piece);
		}
		
		public StraightHallway(int x, int y, int z, int dir)
		{
			super(MapGenSnowDungeon.StraightHallway.piece, x, y, z, dir);
		}
	}
	
	public static class Treasure extends StructureComponentMulti
	{
		static StructurePieceNBT[] pieces;
		
		public Treasure()
		{
			super(MapGenSnowDungeon.Treasure.pieces);
		}
		
		public Treasure(int idx, int x, int y, int z, int dir)
		{
			super(MapGenSnowDungeon.Treasure.pieces, idx, x, y, z, dir);
		}
	}
	
	public static class ChallengeRoomA extends StructureComponentMulti
	{
		static StructurePieceNBT[] pieces;
		
		public ChallengeRoomA()
		{
			super(MapGenSnowDungeon.ChallengeRoomA.pieces);
		}
		
		public ChallengeRoomA(int idx, int x, int y, int z, int dir)
		{
			super(MapGenSnowDungeon.ChallengeRoomA.pieces, idx, x, y, z, dir);
		}
	}
	
	public static class ChallengeRoomB extends StructureComponentMulti
	{
		static StructurePieceNBT[] pieces;
		
		public ChallengeRoomB()
		{
			super(MapGenSnowDungeon.ChallengeRoomB.pieces);
		}
		
		public ChallengeRoomB(int idx, int x, int y, int z, int dir)
		{
			super(MapGenSnowDungeon.ChallengeRoomB.pieces, idx, x, y, z, dir);
		}
	}
	
	public static class ChallengeRoomC extends StructureComponentMulti
	{
		static StructurePieceNBT[] pieces;
		
		public ChallengeRoomC()
		{
			super(MapGenSnowDungeon.ChallengeRoomC.pieces);
		}
		
		public ChallengeRoomC(int idx, int x, int y, int z, int dir)
		{
			super(MapGenSnowDungeon.ChallengeRoomC.pieces, idx, x, y, z, dir);
		}
	}
}
