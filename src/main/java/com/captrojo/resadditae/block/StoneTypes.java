package com.captrojo.resadditae.block;

import java.util.ArrayList;

import com.captrojo.resadditae.block.generic.BlockMulti;
import com.captrojo.resadditae.block.generic.BlockMultiDirectional;
import com.captrojo.resadditae.block.generic.BlockMultiPillar;
import com.captrojo.resadditae.block.generic.BlockMultiSlab;
import com.captrojo.resadditae.block.generic.BlockMultiStair;
import com.captrojo.resadditae.block.generic.BlockMultiWDblSlab;
import com.captrojo.resadditae.item.block.ItemBlockMulti;
import com.captrojo.resadditae.item.block.ItemBlockMultiSlab;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public enum StoneTypes
{
	ANDESITE("andesite", "andesite", true, 1.5f, 6.0f),
	DIORITE("diorite", "diorite", true, 1.5f, 6.0f),
	GRANITE("granite", "granite", true, 1.5f, 6.0f),
	HELLSTONE("hellstone", "hellstone", false, 1.5f, 4.5f),
	BLOODSTONE("bloodstone", "bloodstone", false, 1.5f, 4.5f),
	DEPTH_STONE("depth_stone", "depths/stone", false, 3.0f, 6.0f),
	DEPTH_STONE_AMBER("depth_stone_amber", "depths/stone_amber", false, 3.0f, 6.0f),
	DEPTH_STONE_JADE("depth_stone_jade", "depths/stone_jade", false, 3.0f, 6.0f),
	DEPTH_STONE_RUBY("depth_stone_ruby", "depths/stone_ruby", false, 3.0f, 6.0f),
	DEPTH_STONE_SAPPHIRE("depth_stone_sapphire", "depths/stone_sapphire", false, 3.0f, 6.0f),
	DEPTH_STONE_TOPAZ("depth_stone_topaz", "depths/stone_topaz", false, 3.0f, 6.0f);
	
	public static final int M_REGULAR = 0x000;
	public static final int M_BRICKS = 0x001;
	public static final int M_BUBBLES = 0x002;
	public static final int M_CHISELED = 0x003;
	public static final int M_DIAMONDS_LARGE = 0x004;
	public static final int M_DIAMONDS_SMALL = 0x005;
	public static final int M_LAYERED = 0x006;
	public static final int M_OCTAGON = 0x007;
	public static final int M_PEBBLES = 0x010;
	public static final int M_SHARDS = 0x011;
	public static final int M_SPIRAL = 0x012;
	public static final int M_SQUARES = 0x013;
	public static final int M_TILES = 0x014;
	public static final int M_TRIANGLES = 0x015;
	
	public static final int M_BRICK_PILLAR = 0x100;
	public static final int M_LAYERED_PILLAR = 0x101;
	public static final int M_TILE_PILLAR = 0x102;
	
	public static final int M_ARROW = 0x200;
	public static final int M_HEXAGON = 0x201;
	
	public static final int MLIST[] = {
		M_REGULAR,
		M_BRICKS,
		M_BUBBLES,
		M_CHISELED,
		M_DIAMONDS_LARGE,
		M_DIAMONDS_SMALL,
		M_LAYERED,
		M_OCTAGON,
		M_PEBBLES,
		M_SHARDS,
		M_SPIRAL,
		M_SQUARES,
		M_TILES,
		M_TRIANGLES,
		
		M_BRICK_PILLAR,
		M_LAYERED_PILLAR,
		M_TILE_PILLAR,
		
		M_ARROW,
		M_HEXAGON
	};
	
	public static final int MLISTSHORT[] = {
		M_REGULAR,
		M_BRICKS,
		M_BUBBLES,
		M_CHISELED,
		M_DIAMONDS_LARGE,
		M_DIAMONDS_SMALL,
		M_LAYERED,
		M_OCTAGON,
		M_PEBBLES,
		M_SHARDS,
		M_SPIRAL,
		M_SQUARES,
		M_TILES,
		M_TRIANGLES
	};
	
	protected static void registerBlocks()
	{
		for (StoneTypes type : StoneTypes.values()) {
			type.register();
		}
	}
	
	public static Block getSlabFromBlock(Block block)
	{
		for (StoneTypes type : StoneTypes.values()) {
			for (int i = 0; i < 2; i++) {
				if (type.blocks[i] == block) {
					return type.slabs[i];
				}
			}
		}
		return null;
	}
	
	public static Block getBlockFromSlab(Block block)
	{
		for (StoneTypes type : StoneTypes.values()) {
			for (int i = 0; i < 2; i++) {
				if (type.slabs[i] == block) {
					return type.blocks[i];
				}
			}
		}
		return null;
	}
	
	public final String name;
	public final String texture_path;
	public final boolean is_bountiful_stone;
	public final float hardness;
	public final float resistance;
	
	public BlockMultiWDblSlab[] blocks;
	public BlockMultiSlab[] slabs;
	public BlockMultiStair[] stairs;
	public BlockMultiPillar pillar_block;
	public BlockMulti directional_block;
	
	private StoneTypes(String name, String texture_path, boolean is_bountiful_stone, float hardness, float resistance)
	{
		this.name = name;
		this.texture_path = texture_path;
		this.is_bountiful_stone = is_bountiful_stone;
		this.hardness = hardness;
		this.resistance = resistance;
		
		this.blocks = new BlockMultiWDblSlab[2];
		this.slabs = new BlockMultiSlab[2];
		this.stairs = new BlockMultiStair[7];
	
		IMultiBlockData data0 = StoneData.createBlock0Data(this);
		IMultiBlockData data1 = StoneData.createBlock1Data(this);
		IMultiBlockData datap = StoneData.createBlockPData(this);
		IMultiBlockData datad = StoneData.createBlockDData(this);
		
		this.blocks[0] = new BlockMultiWDblSlab(name + "_0", data0);
		this.blocks[1] = new BlockMultiWDblSlab(name + "_1", data1);
		this.slabs[0] = new BlockMultiSlab(name + "_slab_0", data0, this.blocks[0]);
		this.slabs[1] = new BlockMultiSlab(name + "_slab_1", data1, this.blocks[1]);
		this.stairs[0] = new BlockMultiStair(name + "_stairs_0", this.blocks[0], 0, 1, false);
		this.stairs[1] = new BlockMultiStair(name + "_stairs_1", this.blocks[0], 2, 3, false);
		this.stairs[2] = new BlockMultiStair(name + "_stairs_2", this.blocks[0], 4, 5, false);
		this.stairs[3] = new BlockMultiStair(name + "_stairs_3", this.blocks[0], 6, 7, false);
		this.stairs[4] = new BlockMultiStair(name + "_stairs_4", this.blocks[1], 0, 1, false);
		this.stairs[5] = new BlockMultiStair(name + "_stairs_5", this.blocks[1], 2, 3, false);
		this.stairs[6] = new BlockMultiStair(name + "_stairs_6", this.blocks[1], 4, 5, false);
		this.pillar_block = new BlockMultiPillar(name + "_pillar", datap);
		this.directional_block = new BlockMultiDirectional(name + "_directional", datad, false);
	}
	
	private void register()
	{
		for (BlockMulti block : this.blocks) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		for (BlockMultiSlab block : this.slabs) {
			GameRegistry.registerBlock(block, ItemBlockMultiSlab.class, block.getUnlocalizedName());
		}
		for (BlockMultiStair block : this.stairs) {
			GameRegistry.registerBlock(block, ItemBlockMulti.class, block.getUnlocalizedName());
		}
		GameRegistry.registerBlock(this.pillar_block, ItemBlockMulti.class, this.pillar_block.getUnlocalizedName());
		GameRegistry.registerBlock(this.directional_block, ItemBlockMulti.class, this.directional_block.getUnlocalizedName());
	}
	
	public ItemStack getBlock(int count, int m)
	{
		int idx = (m & 0xf0) >> 4;
		int meta = m & 0x07;
		
		switch (m & 0xf00) {
		case 0x000:
			return new ItemStack(this.blocks[idx], count, meta);
		case 0x100:
			return new ItemStack(this.pillar_block, count, meta);
		case 0x200:
			return new ItemStack(this.directional_block, count, meta);
		}
		
		return null;
	}
	
	public ItemStack getSlab(int count, int m)
	{
		int idx = (m & 0xf0) >> 4;
		int meta = m & 0x07;
		
		return new ItemStack(this.slabs[idx], count, meta);
	}
	
	public ItemStack getStair(int count, int m)
	{
		int idx = (m & 0xf0) >> 4;
		int meta = m & 0x07;
		
		/* since stairs can't hold as many subtypes */
		idx <<= 2;
		idx |= (meta >> 1);
		meta &= 0x1;
		meta <<= 3;
		
		return new ItemStack(this.stairs[idx], count, meta);
	}
	
	public ItemStack[] getAllBlocks()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		
		for (int m : MLIST) {
			stacks.add(this.getBlock(1, m));
		}
		for (int m : MLISTSHORT) {
			stacks.add(this.getSlab(1, m));
		}
		for (int m : MLISTSHORT) {
			stacks.add(this.getStair(1, m));
		}
		
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
}
