package com.captrojo.resadditae.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public enum MultiBlockStacks
{
	IRON_PILE(ModBlocks.material_piles_vanilla_a, 0),
	GOLD_PILE(ModBlocks.material_piles_vanilla_a, 1),
	DIAMOND_PILE(ModBlocks.material_piles_vanilla_b, 0),
	EMERALD_PILE(ModBlocks.material_piles_vanilla_b, 1),
	
	SILVER_BLOCK(ModBlocks.metal_blocks, 0),
	PLATINUM_BLOCK(ModBlocks.metal_blocks, 1),
	
	RAW_SILVER_BLOCK(ModBlocks.raw_metal_blocks, 0),
	RAW_PLATINUM_BLOCK(ModBlocks.raw_metal_blocks, 1),
	
	SILVER_PILE(ModBlocks.metal_piles_a, 0),
	PLATINUM_PILE(ModBlocks.metal_piles_a, 1),
	
	ANCIENT_GEM_BLOCK(ModBlocks.gem_blocks, 0),
	ANCIENT_GEM_PILE(ModBlocks.gem_piles_a, 0),
	
	FLOWER_ASTER(ModBlocks.flowers_a, 0),
	FLOWER_BLACK_EYED_SUSAN(ModBlocks.flowers_a, 1),
	FLOWER_CALENDULA(ModBlocks.flowers_a, 2),
	FLOWER_DAHLIA(ModBlocks.flowers_a, 3),
	FLOWER_FORGETMENOT(ModBlocks.flowers_a, 4),
	FLOWER_HYACINTH(ModBlocks.flowers_a, 5),
	FLOWER_IRIS(ModBlocks.flowers_a, 6),
	FLOWER_MIMOSA(ModBlocks.flowers_a, 7),
	FLOWER_YELLOW_DAFFODIL(ModBlocks.flowers_a, 8);

	public final Block block;
	public final int meta;
	
	private ItemStack stack;
	
	private MultiBlockStacks(Block block, int meta)
	{
		if (block == null) {
			throw new NullPointerException("MultiBlockStacks was called too early");
		}
		this.block = block;
		this.meta = meta;
		this.stack = new ItemStack(block, 1, meta);
	}
	
	/**
	 * Only use to inform of item type and metadata.
	 * Don't use as an actual item stack.
	 */
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
	
	public boolean isEqual(Block block, int meta)
	{
		return Block.getBlockFromItem(this.stack.getItem()) == block && this.stack.getItemDamage() == meta;
	}
}
