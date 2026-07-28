package com.captrojo.resadditae.compatibility;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.StoneTypes;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public enum CommonBlocks
{
	ANDESITE(
		new ItemStack(ModBlocks.bountiful_stones, 1, 0), CommonConfig.CommonStuff.andesite, "Andesite",
		ModList.BOTANIA, "stone", 0,
		ModList.ET_FUTURUM, "stone", 5
	),
	POLISHED_ANDESITE(
		StoneTypes.ANDESITE.getBlock(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.andesite_polished, "Polished Andesite",
		ModList.BOTANIA, "stone", 4 ,
		ModList.ET_FUTURUM, "stone", 6
	),
	ANDESITE_BRICKS(
		StoneTypes.ANDESITE.getBlock(1, StoneTypes.M_BRICKS), CommonConfig.CommonStuff.andesite_bricks, "Andesite Bricks",
		ModList.BOTANIA, "stone", 8
	),
	ANDESITE_SLAB(
		new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 0), CommonConfig.CommonStuff.andesite_slab, "Andesite Slab",
		ModList.BOTANIA, "stone0Slab", 0,
		ModList.ET_FUTURUM, "stone_slab_2", 4
	),
	POLISHED_ANDESITE_SLAB(
		StoneTypes.ANDESITE.getSlab(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.andesite_polished_slab, "Polished Andesite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 5
	),
	ANDESITE_BRICK_SLAB(
		StoneTypes.ANDESITE.getSlab(1, StoneTypes.M_BRICKS), CommonConfig.CommonStuff.andesite_brick_slab, "Andesite Brick Slab",
		ModList.BOTANIA, "stone8Slab", 0
	),
	ANDESITE_STAIR(
		new ItemStack(ModBlocks.bountiful_stone_stairs_a, 1, 0), CommonConfig.CommonStuff.andesite_stair, "Andesite Stair",
		ModList.BOTANIA, "stone0Stairs", 0,
		ModList.ET_FUTURUM, "andesite_stairs", 0
	),
	POLISHED_ANDESITE_STAIR(
		StoneTypes.ANDESITE.getStair(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.andesite_polished_stair, "Polished Andesite Stair",
		ModList.ET_FUTURUM, "polished_andesite_stairs", 0
	),
	ANDESITE_BRICK_STAIR(
		StoneTypes.ANDESITE.getStair(1, StoneTypes.M_BRICKS), CommonConfig.CommonStuff.andesite_brick_stair, "Andesite Brick Stair",
		ModList.BOTANIA, "stone8Stairs", 0
	),
	
	DIORITE(
		new ItemStack(ModBlocks.bountiful_stones, 1, 1), CommonConfig.CommonStuff.diorite, "Diorite",
		ModList.BOTANIA, "stone", 2,
		ModList.ET_FUTURUM, "stone", 3
	),
	POLISHED_DIORITE(
		StoneTypes.DIORITE.getBlock(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.diorite_polished, "Polished Diorite",
		ModList.BOTANIA, "stone", 6,
		ModList.ET_FUTURUM, "stone", 4
	),
	DIORITE_BRICKS(
		StoneTypes.DIORITE.getBlock(1, StoneTypes.M_BRICKS), CommonConfig.CommonStuff.diorite_bricks, "Diorite Bricks",
		ModList.BOTANIA, "stone", 10
	),
	DIORITE_SLAB(
		new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 1), CommonConfig.CommonStuff.diorite_slab, "Diorite Slab",
		ModList.BOTANIA, "stone2Slab", 0,
		ModList.ET_FUTURUM, "stone_slab_2", 2
	),
	POLISHED_DIORITE_SLAB(
		StoneTypes.DIORITE.getSlab(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.diorite_polished_slab, "Polished Diorite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 3
	),
	DIORITE_BRICK_SLAB(
		StoneTypes.DIORITE.getSlab(1, StoneTypes.M_BRICKS), CommonConfig.CommonStuff.diorite_brick_slab, "Diorite Brick Slab",
		ModList.BOTANIA, "stone10Slab", 0
	),
	DIORITE_STAIR(
		new ItemStack(ModBlocks.bountiful_stone_stairs_a, 1, 8), CommonConfig.CommonStuff.diorite_stair, "Diorite Stair",
		ModList.BOTANIA, "stone2Stairs", 0,
		ModList.ET_FUTURUM, "diorite_stairs", 0
	),
	POLISHED_DIORITE_STAIR(
		StoneTypes.DIORITE.getStair(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.diorite_polished_stair, "Polished Diorite Stair",
		ModList.ET_FUTURUM, "polished_diorite_stairs", 0
	),
	DIORITE_BRICK_STAIR(
		StoneTypes.DIORITE.getStair(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.diorite_brick_stair, "Diorite Brick Stair",
		ModList.BOTANIA, "stone10Stairs", 0
	),
	
	GRANITE(
		new ItemStack(ModBlocks.bountiful_stones, 1, 2), CommonConfig.CommonStuff.granite, "Granite",
		ModList.BOTANIA, "stone", 3,
		ModList.ET_FUTURUM, "stone", 1
	),
	POLISHED_GRANITE(
		StoneTypes.GRANITE.getBlock(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.granite_polished, "Polished Granite",
		ModList.BOTANIA, "stone", 7,
		ModList.ET_FUTURUM, "stone", 2
	),
	GRANITE_BRICKS(
		StoneTypes.GRANITE.getBlock(1, StoneTypes.M_BRICKS), CommonConfig.CommonStuff.granite_bricks, "Granite Bricks",
		ModList.BOTANIA, "stone", 11
	),
	GRANITE_SLAB(
		new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 2), CommonConfig.CommonStuff.granite_slab, "Granite Slab",
		ModList.BOTANIA, "stone3Slab", 0,
		ModList.ET_FUTURUM, "stone_slab_2", 0
	),
	POLISHED_GRANITE_SLAB(
		StoneTypes.GRANITE.getSlab(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.granite_polished_slab, "Polished Granite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 1
	),
	GRANITE_BRICK_SLAB(
		StoneTypes.GRANITE.getSlab(1, StoneTypes.M_BRICKS), CommonConfig.CommonStuff.granite_brick_slab, "Granite Brick Slab",
		ModList.BOTANIA, "stone11Slab", 0
	),
	GRANITE_STAIR(
		new ItemStack(ModBlocks.bountiful_stone_stairs_b, 1, 0), CommonConfig.CommonStuff.granite_stair, "Granite Stair",
		ModList.BOTANIA, "stone3Stairs", 0,
		ModList.ET_FUTURUM, "granite_stairs", 0
	),
	POLISHED_GRANITE_STAIR(
		StoneTypes.GRANITE.getStair(1, StoneTypes.M_REGULAR), CommonConfig.CommonStuff.granite_polished_stair, "Polished Granite Stair",
		ModList.ET_FUTURUM, "polished_granite_stairs", 0
	),
	GRANITE_BRICK_STAIR(
		StoneTypes.GRANITE.getStair(1, StoneTypes.M_BRICKS), CommonConfig.CommonStuff.granite_brick_stair, "Granite Brick Stair",
		ModList.BOTANIA, "stone11Stairs", 0
	),
	
	PRISMARINE(
		ModBlocks.prismarine_0, 0, CommonConfig.CommonStuff.prismarine, "Prismarine",
		ModList.BOTANIA, "prismarine", 0,
		ModList.ET_FUTURUM, "prismarine_block", 0
	),
	PRISMARINE_BRICKS(
		ModBlocks.prismarine_0, 5, CommonConfig.CommonStuff.prismarine_bricks, "Prismarine Bricks",
		ModList.BOTANIA, "prismarine", 1,
		ModList.ET_FUTURUM, "prismarine_block", 1
	),
	DARK_PRISMARINE(
		ModBlocks.prismarine_1, 0, CommonConfig.CommonStuff.dark_prismarine, "Dark Prismarine",
		ModList.BOTANIA, "prismarine", 2,
		ModList.ET_FUTURUM, "prismarine_block", 2
	),
	
	PRISMARINE_SLAB(
		ModBlocks.prismarine_slab_0, 0, CommonConfig.CommonStuff.prismarine, "Prismarine Slab",
		ModList.BOTANIA, "prismarine0Slab", 0,
		ModList.ET_FUTURUM, "prismarine_slab", 0
	),
	PRISMARINE_BRICK_SLAB(
		ModBlocks.prismarine_slab_0, 5, CommonConfig.CommonStuff.prismarine_bricks, "Prismarine Brick Slab",
		ModList.BOTANIA, "prismarine1Slab", 0,
		ModList.ET_FUTURUM, "prismarine_slab", 1
	),
	DARK_PRISMARINE_SLAB(
		ModBlocks.prismarine_slab_1, 0, CommonConfig.CommonStuff.dark_prismarine, "Dark Prismarine Slab",
		ModList.BOTANIA, "prismarine2Slab", 0,
		ModList.ET_FUTURUM, "prismarine_slab", 2
	),
	
	PRISMARINE_STAIR(
		ModBlocks.prismarine_stair_0, 0, CommonConfig.CommonStuff.prismarine, "Prismarine Stairs",
		ModList.BOTANIA, "prismarine0Stairs", 0,
		ModList.ET_FUTURUM, "prismarine_stairs", 0
	),
	PRISMARINE_BRICK_STAIR(
		ModBlocks.prismarine_stair_2, 8, CommonConfig.CommonStuff.prismarine_bricks, "Prismarine Brick Stairs",
		ModList.BOTANIA, "prismarine1Stairs", 0,
		ModList.ET_FUTURUM, "prismarine_stairs_brick", 0
	),
	DARK_PRISMARINE_STAIR(
		ModBlocks.prismarine_stair_4, 0, CommonConfig.CommonStuff.dark_prismarine, "Dark Prismarine Stairs",
		ModList.BOTANIA, "prismarine2Stairs", 0,
		ModList.ET_FUTURUM, "prismarine_stairs_dark", 0
	),
	
	RAW_SILVER(
		ModBlocks.raw_metal_blocks, 0, CommonConfig.CommonStuff.raw_silver, "Block of Raw Silver",
		ModList.ET_FUTURUM, "modded_raw_ore_block", 2
	),
	RAW_PLATINUM(
		ModBlocks.raw_metal_blocks, 1, CommonConfig.CommonStuff.raw_platinum, "Block of Raw Platinum",
		ModList.ET_FUTURUM, "modded_raw_ore_block", 5
	);
	
	private ItemStack stack;
	
	private CommonBlocks(ItemStack ra_stack, boolean enabled, String name, Object...objs)
	{
		this(Block.getBlockFromItem(ra_stack.getItem()), ra_stack.getItemDamage(), enabled, name, objs);
	}
	
	private CommonBlocks(Block ra_block, int meta, boolean enabled, String name, Object...objs)
	{
		if (ra_block != null && enabled) {
			this.stack = new ItemStack(ra_block, 1, meta);
			return;
		}
		
		for (int i = 0; i < objs.length; i += 3) {
			ModList mod = (ModList) objs[i];
			if (!mod.isLoaded()) {
				continue;
			}
			Block block = GameRegistry.findBlock(mod.id, (String) objs[i + 1]);
			if (block == null) {
				continue;
			}
			this.stack = new ItemStack(block, 1, (int) objs[i + 2]);
			return;
		}
		
		ResAdditae.LOG.error(String.format("Failed to find any instance of '%s'.", name));
		ResAdditae.common_items_error = true;
	}
	
	public BlockMeta blkm()
	{
		return new BlockMeta(this.stack);
	}
	
	public ItemStack info()
	{
		return this.stack;
	}
	
	public ItemStack stack(int count)
	{
		ItemStack copy = this.stack.copy();
		copy.stackSize = count;
		return copy;
	}
	
	public Block getBlock()
	{
		return Block.getBlockFromItem(this.stack.getItem());
	}
}
