package com.captrojo.resadditae.crafting;

import java.util.ArrayList;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.PrismarineRuneMultiBlockData;
import com.captrojo.resadditae.block.StoneTypes;
import com.captrojo.resadditae.compatibility.CommonBlocks;

import net.minecraft.item.ItemStack;

public class StonecutterRecipes
{
	private static ArrayList<StonecutterRecipes> recipes;
	
	public static void registerRecipes()
	{
		recipes = new ArrayList<StonecutterRecipes>();
	
		for (StoneTypes type : StoneTypes.values()) {
			ItemStack block;
			ItemStack slab;
			ItemStack stair;
			
			switch (type) {
			case ANDESITE:
				block = CommonBlocks.POLISHED_ANDESITE.stack(1);
				slab = CommonBlocks.POLISHED_ANDESITE_SLAB.stack(1);
				stair = CommonBlocks.POLISHED_ANDESITE_STAIR.stack(1);
				break;
			case DIORITE:
				block = CommonBlocks.POLISHED_DIORITE.stack(1);
				slab = CommonBlocks.POLISHED_DIORITE_SLAB.stack(1);
				stair = CommonBlocks.POLISHED_DIORITE_STAIR.stack(1);
				break;
			case GRANITE:
				block = CommonBlocks.POLISHED_GRANITE.stack(1);
				slab = CommonBlocks.POLISHED_GRANITE_SLAB.stack(1);
				stair = CommonBlocks.POLISHED_GRANITE_STAIR.stack(1);
				break;
			default:
				block = type.getBlock(1, StoneTypes.M_REGULAR);
				slab = type.getSlab(1, StoneTypes.M_REGULAR);
				stair = type.getStair(1, StoneTypes.M_REGULAR);
				break;
			}
			
			recipes.add(new StonecutterRecipes(block, createStoneTypeOutputList(type, slab, stair)));
		}
		
		recipes.add(new StonecutterRecipes(CommonBlocks.PRISMARINE.stack(1), new ItemStack[] {
			new ItemStack(ModBlocks.prismarine_0, 1, 1),
			new ItemStack(ModBlocks.prismarine_0, 1, 2),
			new ItemStack(ModBlocks.prismarine_0, 1, 3),
			new ItemStack(ModBlocks.prismarine_0, 1, 4),
			new ItemStack(ModBlocks.prismarine_pillar, 1, 0),
			new ItemStack(ModBlocks.prismarine_rune_0, 1, 0),
			CommonBlocks.PRISMARINE_SLAB.stack(1),
			new ItemStack(ModBlocks.prismarine_slab_0, 1, 1),
			new ItemStack(ModBlocks.prismarine_slab_0, 1, 2),
			new ItemStack(ModBlocks.prismarine_slab_0, 1, 3),
			new ItemStack(ModBlocks.prismarine_slab_0, 1, 4),
			CommonBlocks.PRISMARINE_STAIR.stack(1),
			new ItemStack(ModBlocks.prismarine_stair_0, 1, 1),
			new ItemStack(ModBlocks.prismarine_stair_1, 1, 0),
			new ItemStack(ModBlocks.prismarine_stair_1, 1, 1),
			new ItemStack(ModBlocks.prismarine_stair_2, 1, 0)
		}));
		recipes.add(new StonecutterRecipes(CommonBlocks.PRISMARINE_BRICKS.stack(1), new ItemStack[] {
			new ItemStack(ModBlocks.prismarine_0, 1, 6),
			new ItemStack(ModBlocks.prismarine_0, 1, 7),
			new ItemStack(ModBlocks.prismarine_pillar, 1, 1),
			CommonBlocks.PRISMARINE_BRICK_SLAB.stack(1),
			new ItemStack(ModBlocks.prismarine_slab_0, 1, 6),
			new ItemStack(ModBlocks.prismarine_slab_0, 1, 7),
			CommonBlocks.PRISMARINE_BRICK_STAIR.stack(1),
			new ItemStack(ModBlocks.prismarine_stair_3, 1, 0),
			new ItemStack(ModBlocks.prismarine_stair_3, 1, 1)
		}));
		recipes.add(new StonecutterRecipes(CommonBlocks.DARK_PRISMARINE.stack(1), new ItemStack[] {
			new ItemStack(ModBlocks.prismarine_1, 1, 1),
			new ItemStack(ModBlocks.prismarine_1, 1, 2),
			new ItemStack(ModBlocks.prismarine_1, 1, 3),
			new ItemStack(ModBlocks.prismarine_1, 1, 4),
			new ItemStack(ModBlocks.prismarine_pillar, 1, 2),
			new ItemStack(ModBlocks.prismarine_pillar, 1, 3),
			CommonBlocks.DARK_PRISMARINE_SLAB.stack(1),
			new ItemStack(ModBlocks.prismarine_slab_1, 1, 1),
			new ItemStack(ModBlocks.prismarine_slab_1, 1, 2),
			new ItemStack(ModBlocks.prismarine_slab_1, 1, 3),
			new ItemStack(ModBlocks.prismarine_slab_1, 1, 4),
			CommonBlocks.DARK_PRISMARINE_STAIR.stack(1),
			new ItemStack(ModBlocks.prismarine_stair_4, 1, 1),
			new ItemStack(ModBlocks.prismarine_stair_5, 1, 0),
			new ItemStack(ModBlocks.prismarine_stair_5, 1, 1),
			new ItemStack(ModBlocks.prismarine_stair_6, 1, 0)
		}));
		
		ArrayList<ItemStack> stack_list = new ArrayList<ItemStack>();
		for (int m : PrismarineRuneMultiBlockData.RUNES_0.getValidMetas()) {
			if (m == 0) {
				continue;
			}
			stack_list.add(new ItemStack(ModBlocks.prismarine_rune_0, 1, m));
		}
		for (int m : PrismarineRuneMultiBlockData.RUNES_1.getValidMetas()) {
			stack_list.add(new ItemStack(ModBlocks.prismarine_rune_1, 1, m));
		}
		for (int m : PrismarineRuneMultiBlockData.RUNES_2.getValidMetas()) {
			stack_list.add(new ItemStack(ModBlocks.prismarine_rune_2, 1, m));
		}
		for (int m : PrismarineRuneMultiBlockData.RUNES_3.getValidMetas()) {
			stack_list.add(new ItemStack(ModBlocks.prismarine_rune_3, 1, m));
		}
		ItemStack[] stacks = stack_list.toArray(new ItemStack[stack_list.size()]);
		recipes.add(new StonecutterRecipes(new ItemStack(ModBlocks.prismarine_rune_0, 1, 0), stacks));
	}
	
	private static ItemStack[] createStoneTypeOutputList(StoneTypes type, ItemStack slab, ItemStack stair)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(type.getBlock(1, StoneTypes.M_BRICKS));
		stacks.add(type.getBlock(1, StoneTypes.M_BUBBLES));
		stacks.add(type.getBlock(1, StoneTypes.M_CHISELED));
		stacks.add(type.getBlock(1, StoneTypes.M_DIAMONDS_LARGE));
		stacks.add(type.getBlock(1, StoneTypes.M_DIAMONDS_SMALL));
		stacks.add(type.getBlock(1, StoneTypes.M_LAYERED));
		stacks.add(type.getBlock(1, StoneTypes.M_OCTAGON));
		stacks.add(type.getBlock(1, StoneTypes.M_PEBBLES));
		stacks.add(type.getBlock(1, StoneTypes.M_SHARDS));
		stacks.add(type.getBlock(1, StoneTypes.M_SPIRAL));
		stacks.add(type.getBlock(1, StoneTypes.M_SQUARES));
		stacks.add(type.getBlock(1, StoneTypes.M_TILES));
		stacks.add(type.getBlock(1, StoneTypes.M_TRIANGLES));
		stacks.add(type.getBlock(1, StoneTypes.M_BRICK_PILLAR));
		stacks.add(type.getBlock(1, StoneTypes.M_LAYERED_PILLAR));
		stacks.add(type.getBlock(1, StoneTypes.M_ARROW));
		stacks.add(type.getBlock(1, StoneTypes.M_HEXAGON));
		stacks.add(slab);
		stacks.add(type.getSlab(2, StoneTypes.M_BRICKS));
		stacks.add(type.getSlab(2, StoneTypes.M_BUBBLES));
		stacks.add(type.getSlab(2, StoneTypes.M_CHISELED));
		stacks.add(type.getSlab(2, StoneTypes.M_DIAMONDS_LARGE));
		stacks.add(type.getSlab(2, StoneTypes.M_DIAMONDS_SMALL));
		stacks.add(type.getSlab(2, StoneTypes.M_LAYERED));
		stacks.add(type.getSlab(2, StoneTypes.M_OCTAGON));
		stacks.add(type.getSlab(2, StoneTypes.M_PEBBLES));
		stacks.add(type.getSlab(2, StoneTypes.M_SHARDS));
		stacks.add(type.getSlab(2, StoneTypes.M_SPIRAL));
		stacks.add(type.getSlab(2, StoneTypes.M_SQUARES));
		stacks.add(type.getSlab(2, StoneTypes.M_TILES));
		stacks.add(type.getSlab(2, StoneTypes.M_TRIANGLES));
		stacks.add(stair);
		stacks.add(type.getStair(1, StoneTypes.M_BRICKS));
		stacks.add(type.getStair(1, StoneTypes.M_BUBBLES));
		stacks.add(type.getStair(1, StoneTypes.M_CHISELED));
		stacks.add(type.getStair(1, StoneTypes.M_DIAMONDS_LARGE));
		stacks.add(type.getStair(1, StoneTypes.M_DIAMONDS_SMALL));
		stacks.add(type.getStair(1, StoneTypes.M_LAYERED));
		stacks.add(type.getStair(1, StoneTypes.M_OCTAGON));
		stacks.add(type.getStair(1, StoneTypes.M_PEBBLES));
		stacks.add(type.getStair(1, StoneTypes.M_SHARDS));
		stacks.add(type.getStair(1, StoneTypes.M_SPIRAL));
		stacks.add(type.getStair(1, StoneTypes.M_SQUARES));
		stacks.add(type.getStair(1, StoneTypes.M_TILES));
		stacks.add(type.getStair(1, StoneTypes.M_TRIANGLES));
		return stacks.toArray(new ItemStack[stacks.size()]);
	}
	
	public static ItemStack[] getOutputsFromInput(ItemStack input)
	{
		if (input == null) {
			return new ItemStack[] {};
		}
		for (StonecutterRecipes r : recipes) {
			if (r.input.getItem() != input.getItem()) {
				continue;
			}
			if (r.input.getItemDamage() != input.getItemDamage()) {
				continue;
			}
			return r.outputs;
		}
		return new ItemStack[] {};
	}
	
	public static ItemStack getInputFromOutput(ItemStack output)
	{
		for (StonecutterRecipes r : recipes) {
			for (ItemStack o : r.outputs) {
				if (o.getItem() != output.getItem()) {
					continue;
				}
				if (o.getItemDamage() != output.getItemDamage()) {
					continue;
				}
				return r.input;
			}
		}
		return null;
	}
	
	public final ItemStack input;
	public final ItemStack[] outputs;
	
	private StonecutterRecipes(ItemStack input, ItemStack[] outputs)
	{
		this.input = input;
		this.outputs = outputs;
	}
}
