package com.captrojo.resadditae.crafting;

import java.util.ArrayList;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.PrismarineRuneMultiBlockData;
import com.captrojo.resadditae.block.StoneTypes;
import com.captrojo.resadditae.compatibility.CommonBlocks;

import net.minecraft.item.ItemStack;

public class StonecutterRecipe
{
	private static ArrayList<StonecutterRecipe> recipes;
	
	public static void registerRecipes()
	{
		recipes = new ArrayList<StonecutterRecipe>();
		
		final StoneTypes[] bountiful_stones = {StoneTypes.ANDESITE, StoneTypes.DIORITE, StoneTypes.GRANITE};
	stoneLoop0:
		for (StoneTypes type : bountiful_stones) {
			ItemStack reg_block;
			ItemStack reg_slab;
			ItemStack reg_stair;
			ItemStack pol_block;
			ItemStack pol_slab;
			ItemStack pol_stair;
			ItemStack brk_block;
			ItemStack brk_slab;
			ItemStack brk_stair;
			
			switch (type) {
			case ANDESITE:
				pol_block = CommonBlocks.POLISHED_ANDESITE.stack(1);
				pol_slab = CommonBlocks.POLISHED_ANDESITE_SLAB.stack(1);
				pol_stair = CommonBlocks.POLISHED_ANDESITE_STAIR.stack(1);
				brk_block = CommonBlocks.ANDESITE_BRICKS.stack(1);
				brk_slab = CommonBlocks.ANDESITE_BRICK_SLAB.stack(1);
				brk_stair = CommonBlocks.ANDESITE_BRICK_STAIR.stack(1);
				break;
			case DIORITE:
				pol_block = CommonBlocks.POLISHED_DIORITE.stack(1);
				pol_slab = CommonBlocks.POLISHED_DIORITE_SLAB.stack(2);
				pol_stair = CommonBlocks.POLISHED_DIORITE_STAIR.stack(1);
				brk_block = CommonBlocks.DIORITE_BRICKS.stack(1);
				brk_slab = CommonBlocks.DIORITE_BRICK_SLAB.stack(2);
				brk_stair = CommonBlocks.DIORITE_BRICK_STAIR.stack(1);
				break;
			case GRANITE:
				pol_block = CommonBlocks.POLISHED_GRANITE.stack(1);
				pol_slab = CommonBlocks.POLISHED_GRANITE_SLAB.stack(2);
				pol_stair = CommonBlocks.POLISHED_GRANITE_STAIR.stack(1);
				brk_block = CommonBlocks.GRANITE_BRICKS.stack(1);
				brk_slab = CommonBlocks.GRANITE_BRICK_SLAB.stack(2);
				brk_stair = CommonBlocks.GRANITE_BRICK_STAIR.stack(1);
				break;
			default:
				continue stoneLoop0;
			}
			
			addRecipe(pol_block, brk_block);
			
			for (int m : StoneTypes.MLIST) {
				if (m == StoneTypes.M_POLISHED || m == StoneTypes.M_BRICKS) {
					 continue;
				}
				addRecipe(pol_block, type.getBlock(1, m));
			}

			addRecipe(brk_block, brk_slab);
			addRecipe(brk_block, brk_stair);
			
			for (int m : StoneTypes.MLISTSHORT) {
				if (m == StoneTypes.M_POLISHED || m == StoneTypes.M_BRICKS) {
					 continue;
				}
				addRecipe(type.getBlock(1, m), type.getSlab(2, m));
				addRecipe(type.getBlock(1, m), type.getStair(1, m));
			}

			addRecipe(pol_block, pol_slab);
			addRecipe(pol_block, brk_slab);
			
			for (int m : StoneTypes.MLISTSHORT) {
				if (m == StoneTypes.M_POLISHED || m == StoneTypes.M_BRICKS) {
					 continue;
				}
				addRecipe(pol_block, type.getSlab(2, m));
			}

			addRecipe(pol_block, pol_stair);
			addRecipe(pol_block, brk_stair);
			
			for (int m : StoneTypes.MLISTSHORT) {
				if (m == StoneTypes.M_POLISHED || m == StoneTypes.M_BRICKS) {
					 continue;
				}
				addRecipe(pol_block, type.getStair(1, m));
			}
		}
	
	stoneLoop1:
		for (StoneTypes type : StoneTypes.values()) {
			switch (type) {
			case ANDESITE:
			case DIORITE:
			case GRANITE:
				continue stoneLoop1;
			default:
				break;
			}
			
			ItemStack block = type.getBlock(1, StoneTypes.M_POLISHED);
			ItemStack slab = type.getSlab(1, StoneTypes.M_POLISHED);
			ItemStack stair = type.getStair(1, StoneTypes.M_POLISHED);
			
			for (int m : StoneTypes.MLIST) {
				if (m == 0) continue;
				addRecipe(block, type.getBlock(1, m));
			}
			for (int m : StoneTypes.MLISTSHORT) {
				if (m == 0) continue;
				addRecipe(type.getBlock(1, m), type.getSlab(2, m));
				addRecipe(type.getBlock(1, m), type.getStair(1, m));
			}
			for (int m : StoneTypes.MLISTSHORT) {
				addRecipe(block, type.getSlab(2, m));
			}
			for (int m : StoneTypes.MLISTSHORT) {
				addRecipe(block, type.getStair(1, m));
			}
		}
		
		for (int i = 0; i < ModBlocks.shiny_rocks.length; i++) {
			for (int m : ModBlocks.shiny_rocks[i].data.getValidMetas()) {
				addRecipes(new ItemStack(ModBlocks.shiny_rocks[i], 1, m), new ItemStack[] {
					new ItemStack(ModBlocks.shiny_rock_slabs[i], 2, m),
					new ItemStack(ModBlocks.shiny_rock_stairs[i], 1, m << 3)
				});
			}
		}
		
		addRecipes(CommonBlocks.PRISMARINE.stack(1), new ItemStack[] {
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
		});
		addRecipes(CommonBlocks.PRISMARINE_BRICKS.stack(1), new ItemStack[] {
			new ItemStack(ModBlocks.prismarine_0, 1, 6),
			new ItemStack(ModBlocks.prismarine_0, 1, 7),
			new ItemStack(ModBlocks.prismarine_pillar, 1, 1),
			CommonBlocks.PRISMARINE_BRICK_SLAB.stack(1),
			new ItemStack(ModBlocks.prismarine_slab_0, 1, 6),
			new ItemStack(ModBlocks.prismarine_slab_0, 1, 7),
			CommonBlocks.PRISMARINE_BRICK_STAIR.stack(1),
			new ItemStack(ModBlocks.prismarine_stair_3, 1, 0),
			new ItemStack(ModBlocks.prismarine_stair_3, 1, 1)
		});
		addRecipes(CommonBlocks.DARK_PRISMARINE.stack(1), new ItemStack[] {
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
		});
		
		ArrayList<ItemStack> stack_list = new ArrayList<ItemStack>();
		for (int m : PrismarineRuneMultiBlockData.RUNES_0.getValidMetas()) {
			if (m == 0) continue;
			addRecipe(new ItemStack(ModBlocks.prismarine_0, 1, 0), new ItemStack(ModBlocks.prismarine_rune_0, 1, m));
		}
		for (int m : PrismarineRuneMultiBlockData.RUNES_1.getValidMetas()) {
			addRecipe(new ItemStack(ModBlocks.prismarine_0, 1, 0), new ItemStack(ModBlocks.prismarine_rune_1, 1, m));
		}
		for (int m : PrismarineRuneMultiBlockData.RUNES_2.getValidMetas()) {
			addRecipe(new ItemStack(ModBlocks.prismarine_0, 1, 0), new ItemStack(ModBlocks.prismarine_rune_2, 1, m));
		}
		for (int m : PrismarineRuneMultiBlockData.RUNES_3.getValidMetas()) {
			addRecipe(new ItemStack(ModBlocks.prismarine_0, 1, 0), new ItemStack(ModBlocks.prismarine_rune_3, 1, m));
		}
	}
	
	private static void addRecipe(ItemStack input, ItemStack output)
	{
		for (StonecutterRecipe r : recipes) {
			if (compareStacks(r.input, input)) {
				r.outputs.add(output);
				return;
			}
		}
		recipes.add(new StonecutterRecipe(input, output));
	}
	
	private static void addRecipes(ItemStack input, ItemStack[] outputs)
	{
		for (ItemStack output : outputs) {
			addRecipe(input, output);
		}
	}
	
	public static ItemStack[] getOutputsFromInput(ItemStack input)
	{
		for (StonecutterRecipe r : recipes) {
			if (compareStacks(r.input, input)) {
				return r.getOutputs();
			}
		}
		return new ItemStack[] {};
	}
	
	public static ItemStack[] getInputsFromOutput(ItemStack output)
	{
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		for (StonecutterRecipe r : recipes) {
			for (ItemStack stack : r.getOutputs()) {
				if (compareStacks(stack, output)) {
					inputs.add(r.input);
				}
			}
		}
		return inputs.toArray(new ItemStack[inputs.size()]);
	}
	
	private static boolean compareStacks(ItemStack stack1, ItemStack stack2)
	{
		if (stack1 == null || stack2 == null) {
			return false;
		}
		if (stack1.getItemDamage() != stack2.getItemDamage()) {
			return false;
		}
		if (stack1.getItem() != stack2.getItem()) {
			return false;
		}
		return true;
	}
	
	private final ItemStack input;
	private final ArrayList<ItemStack> outputs;
	
	private ItemStack[] outputs_arr;
	
	private StonecutterRecipe(ItemStack input, ItemStack output)
	{
		this.input = input;
		this.outputs = new ArrayList<ItemStack>();
		this.outputs.add(output);
	}
	
	private ItemStack[] getOutputs()
	{
		if (this.outputs_arr == null) {
			this.outputs_arr = this.outputs.toArray(new ItemStack[this.outputs.size()]);
		}
		return this.outputs_arr;
	}
}
