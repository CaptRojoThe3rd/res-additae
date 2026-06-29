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
		new ItemStack(ModBlocks.bountiful_stones, 1, 0), CommonConfig.CommonItems.andesite, "Andesite",
		ModList.ET_FUTURUM, "stone", 5
	),
	POLISHED_ANDESITE(
		StoneTypes.ANDESITE.getBlock(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_andesite, "Polished Andesite",
		ModList.ET_FUTURUM, "stone", 6
	),
	DIORITE(
		new ItemStack(ModBlocks.bountiful_stones, 1, 1), CommonConfig.CommonItems.diorite, "Diorite",
		ModList.ET_FUTURUM, "stone", 3
	),
	POLISHED_DIORITE(
		StoneTypes.DIORITE.getBlock(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_diorite, "Polished Diorite",
		ModList.ET_FUTURUM, "stone", 4
	),
	GRANITE(
		new ItemStack(ModBlocks.bountiful_stones, 1, 2), CommonConfig.CommonItems.granite, "Granite",
		ModList.ET_FUTURUM, "stone", 1
	),
	POLISHED_GRANITE(
		StoneTypes.GRANITE.getBlock(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_granite, "Polished Granite",
		ModList.ET_FUTURUM, "stone", 2
	),
	
	ANDESITE_SLAB(
		new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 0), CommonConfig.CommonItems.andesite, "Andesite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 4
	),
	POLISHED_ANDESITE_SLAB(
		StoneTypes.ANDESITE.getSlab(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_andesite, "Polished Andesite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 5
	),
	DIORITE_SLAB(
		new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 1), CommonConfig.CommonItems.diorite, "Diorite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 2
	),
	POLISHED_DIORITE_SLAB(
		StoneTypes.DIORITE.getSlab(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_diorite, "Polished Diorite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 3
	),
	GRANITE_SLAB(
		new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 2), CommonConfig.CommonItems.granite, "Granite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 0
	),
	POLISHED_GRANITE_SLAB(
		StoneTypes.GRANITE.getSlab(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_granite, "Polished Granite Slab",
		ModList.ET_FUTURUM, "stone_slab_2", 1
	),
	
	ANDESITE_STAIR(
		new ItemStack(ModBlocks.bountiful_stone_stairs_a, 1, 0), CommonConfig.CommonItems.andesite, "Andesite Stair",
		ModList.ET_FUTURUM, "andesite_stairs", 0
	),
	POLISHED_ANDESITE_STAIR(
		StoneTypes.ANDESITE.getStair(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_andesite, "Polished Andesite Stair",
		ModList.ET_FUTURUM, "polished_andesite_stairs", 0
	),
	DIORITE_STAIR(
		new ItemStack(ModBlocks.bountiful_stone_stairs_a, 1, 8), CommonConfig.CommonItems.diorite, "Diorite Stair",
		ModList.ET_FUTURUM, "diorite_stairs", 0
	),
	POLISHED_DIORITE_STAIR(
		StoneTypes.DIORITE.getStair(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_diorite, "Polished Diorite Stair",
		ModList.ET_FUTURUM, "polished_diorite_stairs", 0
	),
	GRANITE_STAIR(
		new ItemStack(ModBlocks.bountiful_stone_stairs_b, 1, 0), CommonConfig.CommonItems.granite, "Granite Stair",
		ModList.ET_FUTURUM, "granite_stairs", 0
	),
	POLISHED_GRANITE_STAIR(
		StoneTypes.GRANITE.getStair(1, StoneTypes.M_REGULAR), CommonConfig.CommonItems.polished_granite, "Polished Granite Stair",
		ModList.ET_FUTURUM, "polished_granite_stairs", 0
	),
	
	PRISMARINE(
		ModBlocks.prismarine_0, 0, CommonConfig.CommonItems.prismarine, "Prismarine",
		ModList.ET_FUTURUM, "prismarine_block", 0
	),
	PRISMARINE_BRICKS(
		ModBlocks.prismarine_0, 5, CommonConfig.CommonItems.prismarine_bricks, "Prismarine Bricks",
		ModList.ET_FUTURUM, "prismarine_block", 1
	),
	DARK_PRISMARINE(
		ModBlocks.prismarine_1, 0, CommonConfig.CommonItems.dark_prismarine, "Dark Prismarine",
		ModList.ET_FUTURUM, "prismarine_block", 2
	),
	
	PRISMARINE_SLAB(
		ModBlocks.prismarine_slab_0, 0, CommonConfig.CommonItems.prismarine, "Prismarine Slab",
		ModList.ET_FUTURUM, "prismarine_slab", 0
	),
	PRISMARINE_BRICK_SLAB(
		ModBlocks.prismarine_slab_0, 5, CommonConfig.CommonItems.prismarine_bricks, "Prismarine Brick Slab",
		ModList.ET_FUTURUM, "prismarine_slab", 1
	),
	DARK_PRISMARINE_SLAB(
		ModBlocks.prismarine_slab_1, 0, CommonConfig.CommonItems.dark_prismarine, "Dark Prismarine Slab",
		ModList.ET_FUTURUM, "prismarine_slab", 2
	),
	
	PRISMARINE_STAIR(
		ModBlocks.prismarine_stair_0, 0, CommonConfig.CommonItems.prismarine, "Prismarine Stairs",
		ModList.ET_FUTURUM, "prismarine_stairs", 0
	),
	PRISMARINE_BRICK_STAIR(
		ModBlocks.prismarine_stair_2, 8, CommonConfig.CommonItems.prismarine_bricks, "Prismarine Brick Stairs",
		ModList.ET_FUTURUM, "prismarine_stairs_brick", 0
	),
	DARK_PRISMARINE_STAIR(
		ModBlocks.prismarine_stair_4, 0, CommonConfig.CommonItems.dark_prismarine, "Dark Prismarine Stairs",
		ModList.ET_FUTURUM, "prismarine_stairs_dark", 0
	),
	
	RAW_SILVER(
		ModBlocks.raw_metal_blocks, 0, CommonConfig.CommonItems.raw_silver, "Block of Raw Silver",
		ModList.ET_FUTURUM, "modded_raw_ore_block", 2
	),
	RAW_PLATINUM(
		ModBlocks.raw_metal_blocks, 1, CommonConfig.CommonItems.raw_platinum, "Block of Raw Platinum",
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
