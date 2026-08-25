package com.captrojo.resadditae.block;

import com.captrojo.resadditae.render.block.BlockTexture;
import com.captrojo.resadditae.render.block.BlockTexture.Type;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class StoneData
{
	public static final int M0_REGULAR = 0;
	public static final int M0_BRICKS = 1;
	public static final int M0_BUBBLES = 2;
	public static final int M0_CHISELED = 3;
	public static final int M0_DIAMONDS_LARGE = 4;
	public static final int M0_DIAMONDS_SMALL = 5;
	public static final int M0_LAYERED = 6;
	public static final int M0_OCTAGON = 7;
	
	public static final int M1_PEBBLES = 0;
	public static final int M1_SHARDS = 1;
	public static final int M1_SPIRAL = 2;
	public static final int M1_SQUARES = 3;
	public static final int M1_TILES = 4;
	public static final int M1_TRIANGLES = 5;
	
	public static final int MP_BRICK_PILLAR = 0;
	public static final int MP_LAYERED_PILLAR = 1;
	public static final int MP_TILE_PILLAR = 2;
	
	public static final int MR_ARROW = 0;
	public static final int MR_HEXAGON = 1;
	
	private static final String[] BLOCK_0_STRS = {
		"polished",
		"bricks",
		"bubbles",
		"chiseled",
		"diamonds_large",
		"diamonds_small",
		"layered",
		"octagon"
	};
	private static final String[] BLOCK_1_STRS = {
		"pebbles",
		"shards",
		"spiral",
		"squares",
		"tiles",
		"triangles"
	};
	private static final String[] BLOCK_P_STRS = {
		"brick_pillar",
		"layered_pillar",
		"tile_pillar"
	};
	private static final String[] BLOCK_D_STRS = {
		"arrow",
		"hexagon"
	};
	
	public static GenericMultiBlockData createBlock0Data(StoneTypes type)
	{
		GenericMultiBlockData data = new GenericMultiBlockData(Material.rock, Block.soundTypeStone);
		
		for (int i = 0; i < 8; i++) {
			data.metas_list.add(i);
			data.names_map.put(i, BLOCK_0_STRS[i]);
			if (type.is_bountiful_stone && i == 0) {
				data.texture_map.put(i, new BlockTexture(Type.STANDARD, "minecraft:polished_" + type.name));
			} else if (i == M0_LAYERED) {
				data.texture_map.put(i, new BlockTexture(Type.PILLAR, data.texture_map.get(0).paths[0], type.texture_path + "/layered"));
			} else {
				data.texture_map.put(i, new BlockTexture(Type.STANDARD, type.texture_path + "/" + BLOCK_0_STRS[i]));
			}
			data.hardnesses_map.put(i, type.hardness);
			data.resistances_map.put(i, type.resistance);
			data.harvest_tools_map.put(i, "pickaxe");
			data.harvest_levels_map.put(i, 0);
		}
		
		data.finalizeMaps();
		return data;
	}
	
	public static GenericMultiBlockData createBlock1Data(StoneTypes type)
	{
		GenericMultiBlockData data = new GenericMultiBlockData(Material.rock, Block.soundTypeStone);
		
		for (int i = 0; i < 6; i++) {
			data.metas_list.add(i);
			data.names_map.put(i, BLOCK_1_STRS[i]);
			data.texture_map.put(i, new BlockTexture(Type.STANDARD, type.texture_path + "/" + BLOCK_1_STRS[i]));
			data.hardnesses_map.put(i, type.hardness);
			data.resistances_map.put(i, type.resistance);
			data.harvest_tools_map.put(i, "pickaxe");
			data.harvest_levels_map.put(i, 0);
		}

		data.finalizeMaps();
		return data;
	}
	
	public static GenericMultiBlockData createBlockPData(StoneTypes type)
	{
		GenericMultiBlockData data = new GenericMultiBlockData(Material.rock, Block.soundTypeStone);
		
		for (int i = 0; i < 3; i++) {
			data.metas_list.add(i);
			data.names_map.put(i, BLOCK_P_STRS[i]);
			if (i == MP_LAYERED_PILLAR) {
				data.texture_map.put(i, new BlockTexture(
					Type.LAYERED_PILLAR,
					type.texture_path + "/" + BLOCK_P_STRS[i] + "_end",
					type.texture_path + "/" + BLOCK_P_STRS[i] + "_side_0",
					type.texture_path + "/" + BLOCK_P_STRS[i] + "_side_1"
				));
			} else {
				data.texture_map.put(i, new BlockTexture(
					Type.PILLAR,
					type.texture_path + "/" + BLOCK_P_STRS[i] + "_end",
					type.texture_path + "/" + BLOCK_P_STRS[i] + "_side"
				));
			}
			data.hardnesses_map.put(i, type.hardness);
			data.resistances_map.put(i, type.resistance);
			data.harvest_tools_map.put(i, "pickaxe");
			data.harvest_levels_map.put(i, 0);
		}

		data.finalizeMaps();
		return data;
	}
	
	public static GenericMultiBlockData createBlockDData(StoneTypes type)
	{
		GenericMultiBlockData data = new GenericMultiBlockData(Material.rock, Block.soundTypeStone);
		
		for (int i = 0; i < 2; i++) {
			data.metas_list.add(i);
			data.names_map.put(i, BLOCK_D_STRS[i]);
			data.texture_map.put(i, new BlockTexture(
				Type.DIRECTIONAL,
				type.texture_path + "/" + BLOCK_D_STRS[i] + "_down",
				type.texture_path + "/" + BLOCK_D_STRS[i] + "_left",
				type.texture_path + "/" + BLOCK_D_STRS[i] + "_up",
				type.texture_path + "/" + BLOCK_D_STRS[i] + "_right"
			));
			data.hardnesses_map.put(i, type.hardness);
			data.resistances_map.put(i, type.resistance);
			data.harvest_tools_map.put(i, "pickaxe");
			data.harvest_levels_map.put(i, 0);
		}
		
		data.finalizeMaps();
		return data;
	}
}
