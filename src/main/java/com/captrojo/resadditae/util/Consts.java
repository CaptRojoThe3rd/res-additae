package com.captrojo.resadditae.util;

import net.minecraft.world.gen.structure.StructureBoundingBox;

public class Consts
{
	public static final int[] IDENTITY_ARR = {0, 1, 2, 3};
	
	public static final int SOUTH = 0;
	public static final int WEST = 1;
	public static final int NORTH = 2;
	public static final int EAST = 3;
	
	public static final int STAIR_EAST = 0;
	public static final int STAIR_WEST = 1;
	public static final int STAIR_SOUTH = 2;
	public static final int STAIR_NORTH = 3;
	public static final int[] STAIR_ROT_CW = {STAIR_SOUTH, STAIR_NORTH, STAIR_WEST, STAIR_EAST};
	public static final int[] STAIR_ROT_CCW = {STAIR_NORTH, STAIR_SOUTH, STAIR_EAST, STAIR_WEST};
	public static final int[] STAIR_ROT_180 = {STAIR_WEST, STAIR_EAST, STAIR_NORTH, STAIR_SOUTH};
	
	public static final int LADDER_BTN_NORTH = 2;
	public static final int LADDER_BTN_SOUTH = 3;
	public static final int LADDER_BTN_WEST = 4;
	public static final int LADDER_BTN_EAST = 5;
	public static final int[] LADDER_BTN_ROT_CW = {0, 1, LADDER_BTN_EAST, LADDER_BTN_WEST, LADDER_BTN_NORTH, LADDER_BTN_SOUTH};
	public static final int[] LADDER_BTN_ROT_CCW = {0, 1, LADDER_BTN_WEST, LADDER_BTN_EAST, LADDER_BTN_SOUTH, LADDER_BTN_NORTH};
	public static final int[] LADDER_BTN_ROT_180 = {0, 1, LADDER_BTN_SOUTH, LADDER_BTN_NORTH, LADDER_BTN_EAST, LADDER_BTN_WEST};
	
	public static final int TORCH_WEST = 1;
	public static final int TORCH_EAST = 2;
	public static final int TORCH_NORTH = 3;
	public static final int TORCH_SOUTH = 4;
	public static final int[] TORCH_ROT_CW = {0, TORCH_NORTH, TORCH_SOUTH, TORCH_EAST, TORCH_WEST, 5};
	public static final int[] TORCH_ROT_CCW = {0, TORCH_SOUTH, TORCH_NORTH, TORCH_WEST, TORCH_EAST, 5};
	public static final int[] TORCH_ROT_180 = {0, TORCH_EAST, TORCH_WEST, TORCH_SOUTH, TORCH_NORTH, 5};
	
	public static final int TRAPDOOR_WEST = 0;
	public static final int TRAPDOOR_EAST = 1;
	public static final int TRAPDOOR_NORTH = 2;
	public static final int TRAPDOOR_SOUTH = 3;
	public static final int[] TRAPDOOR_ROT_CW = {TRAPDOOR_NORTH, TRAPDOOR_SOUTH, TRAPDOOR_EAST, TRAPDOOR_WEST};
	public static final int[] TRAPDOOR_ROT_CCW = {TRAPDOOR_SOUTH, TRAPDOOR_NORTH, TRAPDOOR_WEST, TRAPDOOR_EAST};
	public static final int[] TRAPDOOR_ROT_180 = {TRAPDOOR_EAST, TRAPDOOR_WEST, TRAPDOOR_SOUTH, TRAPDOOR_NORTH};
	
	public static final StructureBoundingBox SBB_ZERO = new StructureBoundingBox(0, 0, 0, 0, 0, 0);
	public static final StructureBoundingBox SBB_MINMAX = new StructureBoundingBox(
		Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE,
		Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE
	);
}
