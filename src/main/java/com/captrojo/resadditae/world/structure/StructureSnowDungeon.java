package com.captrojo.resadditae.world.structure;

import java.util.Random;

import com.captrojo.resadditae.config.common.DebugConfig;
import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.main.MiscHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.gen.StaticGenSpacedThing;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class StructureSnowDungeon extends StaticGenSpacedThing
{
	static final int[] BASIN_NW_OFFS = {-11, 0, -11};
	static final int[] BASIN_NE_OFFS = {11, 0, -11};
	static final int[] BASIN_SW_OFFS = {-11, 0, 11};
	static final int[] BASIN_SE_OFFS = {11, 0, 11};
	static final int[][] BASIN_OFFS = {BASIN_NW_OFFS, BASIN_NE_OFFS, BASIN_SW_OFFS, BASIN_SE_OFFS};
	
	static final int[][] NORTH_ROOM_OFFS = {{0, -20, -7}, {0, -36, -7}};
	static final int[][] EAST_ROOM_OFFS = {{7, -21, 0}, {7, -37, 0}};
	static final int[][] SOUTH_ROOM_OFFS = {{0, -18, 7}, {0, -34, 7}};
	static final int[][] WEST_ROOM_OFFS = {{-7, -19, 0}, {-7, -35, 0}};
	static final int[][][] ROOM_START_OFFS = {NORTH_ROOM_OFFS, EAST_ROOM_OFFS, SOUTH_ROOM_OFFS, WEST_ROOM_OFFS};
	
	static final long OP_HALLWAY = 1l << 0;
	
	StructurePiece pyramid;
	StructurePiece[] pyramid_basins;
	
	StructurePiece[] hallway_straight;
	StructurePiece[] hallway_left;
	StructurePiece[] hallway_right;
	StructurePiece[][] hallways;
	
	StructurePiece[][] room_1;
	StructurePiece[][] room_2;
	StructurePiece[][] room_3;
	StructurePiece[][][] rooms;
	
	StructurePiece[][] treasures;
	
	public StructureSnowDungeon()
	{
		super(
			"Snow Dungeon".hashCode(),
			new BiomeGenBase[] {
				BiomeGenBase.icePlains
			},
			WorldGenConfig.snow_dungeon_excl_rad,
			WorldGenConfig.snow_dungeon_min_dist,
			WorldGenConfig.snow_dungeon_max_dist
		);
		
		this.pyramid = ModStructures.loadPiece("snow_dungeon/pyramid");
		this.pyramid_basins = new StructurePiece[12];
		for (int i = 1; i <= 12; i++) {
			this.pyramid_basins[i - 1] = ModStructures.loadPiece("snow_dungeon/pyramid_basin_" + i);
		}
		
		this.hallway_straight = ModStructures.loadPieceAndRotations("snow_dungeon/hallway_straight");
		this.hallway_left = ModStructures.loadPieceAndRotations("snow_dungeon/hallway_left");
		this.hallway_right = ModStructures.loadPieceAndRotations("snow_dungeon/hallway_right");
		this.hallways = new StructurePiece[][] {this.hallway_straight, this.hallway_left, this.hallway_right};
		
		this.room_1 = new StructurePiece[6][4];
		this.room_2 = new StructurePiece[6][4];
		this.room_3 = new StructurePiece[6][4];
		this.treasures = new StructurePiece[6][4];
		for (int lvl = 3; lvl <= 8; lvl++) {
			this.room_1[lvl - 3] = ModStructures.loadPieceAndRotations("snow_dungeon/room_1_lvl_" + lvl);
			this.room_2[lvl - 3] = ModStructures.loadPieceAndRotations("snow_dungeon/room_2_lvl_" + lvl);
			this.room_3[lvl - 3] = ModStructures.loadPieceAndRotations("snow_dungeon/room_3_lvl_" + lvl);
			this.treasures[lvl - 3] = ModStructures.loadPieceAndRotations("snow_dungeon/treasure_lvl_" + lvl);
		}
		this.rooms = new StructurePiece[][][] {this.room_1, this.room_2, this.room_3};
	}
	
	private StructurePiece getRandomRoom(Random rand, int dir, int dif)
	{
		return this.rooms[rand.nextInt(this.rooms.length)][dif][dir];
	}
	
	private int[] getRoomEnd(StructurePiece sp, int dir, int[] pos)
	{
		switch (dir) {
		case 0:
			pos[2] -= sp.size_z;
			break;
		case 1:
			pos[0] += sp.size_x;
			break;
		case 2:
			pos[2] += sp.size_z;
			break;
		case 3:
			pos[0] -= sp.size_x;
			break;
		}
		return pos;
	}
	
	private int[] getHallwayEnd(int hw_idx, int dir, int[] pos)
	{
		int forward, side;
		if (hw_idx == 1) {
			forward = this.hallway_left[0].size_z - 2;
			side = -this.hallway_left[0].size_x + 2;
		} else if (hw_idx == 2) {
			forward = this.hallway_right[0].size_z - 3;
			side = this.hallway_left[0].size_x - 3;
		} else {
			forward = this.hallway_straight[0].size_z;
			side = 0;
		}
		
		switch (dir) {
		case 0:
			pos[0] += side;
			pos[2] -= forward;
			break;
		case 1:
			pos[0] += forward;
			pos[2] += side;
			break;
		case 2:
			pos[0] -= side;
			pos[2] += forward;
			break;
		case 3:
			pos[0] -= forward;
			pos[2] -= side;
			break;
		}
		
		return pos;
	}
	
	private int rotateDirCntClk(int dir)
	{
		return (dir - 1) & 0x3;
	}
	
	private int rotateDirClk(int dir)
	{
		return (dir + 1) & 0x3;
	}
	
	/* Returns {hallway, dir} */
	private int[] getRandomHallway(Random rand, int dir0, int dir)
	{
		int hallway = rand.nextInt(3), newdir = 0;
		if (hallway == 0) {
			return new int[] {hallway, dir};
		}
		
		if (hallway == 1) {
			newdir = this.rotateDirCntClk(dir);
		} else if (hallway == 2) {
			newdir = this.rotateDirClk(dir);
		}
		
		return new int[] {hallway, newdir};
	}
	
	@Override
	public void generate(World world, int chunk_x, int chunk_z)
	{
		Random rand = world.rand;
		int x = chunk_x * 16;
		int z = chunk_z * 16;
		int y = world.getHeightValue(x, z);
//		int y = 200;
		
		if (DebugConfig.log_structure_gens) {
			ResAdditae.LOG.info(String.format("Generated snow dungeon at (%d, %d, %d)", x, y, z));
		}
		
		this.pyramid.placeInWorld(world, rand, x, y, z, 0l);
		Integer[] basin_indices = MiscHlpr.getUniqueRandomInts(rand, 4, this.pyramid_basins.length);
		for (int i = 0; i < 4; i++) {
			/* I was lazy and didn't put any structure voids in the basins... */
			for (int x0 = -7; x0 <= 7; x0++) {
				for (int y0 = -2; y0 <= 1; y0++) {
					for (int z0 = -7; z0 <= 7; z0++) {
						world.setBlock(x + x0 + BASIN_OFFS[i][0], y + y0 + BASIN_OFFS[i][1], z + z0 + BASIN_OFFS[i][2], Blocks.air);
					}
				}
			}
			StructurePiece sp = this.pyramid_basins[basin_indices[i]];
			sp.placeInWorld(world, rand, x + BASIN_OFFS[i][0], y + BASIN_OFFS[i][1], z + BASIN_OFFS[i][2], 0l);
		}
		
		for (int dir0 = 0; dir0 < 4; dir0++) {
			for (int dif = 0; dif < 2; dif++) {
				int dir = dir0;
				int[] start_pos = ROOM_START_OFFS[dir][dif];
				int[] pos = {start_pos[0] + x, start_pos[1] + y, start_pos[2] + z};
				for (int i = 0; i < 3; i++) {
					int[] ret = (i > 0) ? this.getRandomHallway(rand, dir0, dir) : new int[] {0, dir};
					int hw_idx = ret[0];
					StructurePiece hw = this.hallways[hw_idx][dir];
					hw.placeInWorld(world, rand, pos[0], pos[1], pos[2], OP_HALLWAY);
					pos = this.getHallwayEnd(hw_idx, dir, pos);
					dir = ret[1];
					
					StructurePiece rm = this.getRandomRoom(rand, dir, dif * 3 + i);
					rm.placeInWorld(world, rand, pos[0], pos[1], pos[2], 0l);
					pos = this.getRoomEnd(rm, dir, pos);
					
					StructurePiece tr = this.treasures[dif * 3 + i][dir];
					tr.placeInWorld(world, rand, pos[0], pos[1], pos[2], 0l);
					pos = this.getRoomEnd(tr, dir, pos);
				}

				StructurePiece hw = this.hallway_straight[dir];
				hw.placeInWorld(world, rand, pos[0], pos[1], pos[2], 0l);
			}
		}
	}
}
