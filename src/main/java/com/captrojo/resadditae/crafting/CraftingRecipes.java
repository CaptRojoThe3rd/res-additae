package com.captrojo.resadditae.crafting;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.MultiBlockStacks;
import com.captrojo.resadditae.block.MultiBlocks;
import com.captrojo.resadditae.block.StoneTypes;
import com.captrojo.resadditae.block.WoodTypes;
import com.captrojo.resadditae.block.generic.BlockMultiStair;
import com.captrojo.resadditae.compatibility.CommonItems;
import com.captrojo.resadditae.compatibility.OtherBlocks;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.item.Dyes;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItemStacks;
import com.captrojo.resadditae.item.MultiItems;
import com.captrojo.resadditae.item.generic.ItemVessel;
import com.captrojo.resadditae.item.generic.VesselTypes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CraftingRecipes
{
	public static void register()
	{
		/* Misc. Block Recipes */
		for (int i = 0, m0 = 0; i < ModBlocks.shiny_rocks.length; i++, m0 += 2) {
			for (int m : ModBlocks.shiny_rocks[i].data.getValidMetas()) {
				addShaped(new ItemStack(ModBlocks.shiny_rocks[i], 1, m), "aaa", "aaa", "aaa", 'a', new ItemStack(ModItems.shiny_rocks, 1, m0 + m));
				addSlab(new ItemStack(ModBlocks.shiny_rock_slabs[i], 6, m), new ItemStack(ModBlocks.shiny_rocks[i], 1, m));
				addStair(new ItemStack(ModBlocks.shiny_rock_stairs[i], 8, m << 3), new ItemStack(ModBlocks.shiny_rocks[i], 1, m));
			}
		}
		
		addShaped(new ItemStack(ModBlocks.compacted_snow, 2, 0), "aa", "aa", 'a', Blocks.snow);
		addShaped(new ItemStack(ModBlocks.compacted_snow, 4, 1), "aa", "aa", 'a', new ItemStack(ModBlocks.compacted_snow, 1, 0));
		addShaped(new ItemStack(ModBlocks.compacted_snow, 4, 2), "aa", "aa", 'a', new ItemStack(ModBlocks.compacted_snow, 1, 1));
		addShaped(new ItemStack(ModBlocks.compacted_snow, 2, 3), "ab", "ba", 'a', Blocks.snow, 'b', Blocks.packed_ice);
		addSlab(new ItemStack(ModBlocks.compacted_snow_slab, 6, 0), new ItemStack(ModBlocks.compacted_snow, 1, 0));
		addSlab(new ItemStack(ModBlocks.compacted_snow_slab, 6, 1), new ItemStack(ModBlocks.compacted_snow, 1, 1));
		addSlab(new ItemStack(ModBlocks.compacted_snow_slab, 6, 2), new ItemStack(ModBlocks.compacted_snow, 1, 2));
		addSlab(new ItemStack(ModBlocks.compacted_snow_slab, 6, 3), new ItemStack(ModBlocks.compacted_snow, 1, 3));
		addStair(new ItemStack(ModBlocks.compacted_snow_stair_a, 8, 0), new ItemStack(ModBlocks.compacted_snow, 1, 0));
		addStair(new ItemStack(ModBlocks.compacted_snow_stair_a, 8, 8), new ItemStack(ModBlocks.compacted_snow, 1, 1));
		addStair(new ItemStack(ModBlocks.compacted_snow_stair_b, 8, 0), new ItemStack(ModBlocks.compacted_snow, 1, 2));
		addStair(new ItemStack(ModBlocks.compacted_snow_stair_b, 8, 8), new ItemStack(ModBlocks.compacted_snow, 1, 3));
		
		/* Wood Recipes */
		for (WoodTypes type : WoodTypes.values()) {
			addShaped(type.getWood().stack(3), "aa", "aa", 'a', type.getLog().stack(1));
			addShaped(type.getStrippedWood().stack(3), "aa", "aa", 'a', type.getStrippedLog().stack(1));
			addShapeless(type.getPlanks().stack(4), type.getLog().stack(1));
			addShapeless(type.getPlanks().stack(4), type.getStrippedLog().stack(1));
			addShapeless(type.getPlanks().stack(4), type.getWood().stack(1));
			addShapeless(type.getPlanks().stack(4), type.getStrippedWood().stack(1));
			addShaped(type.getSlab().stack(6), "aaa", 'a', type.getPlanks().stack(1));
			addShaped(type.getStair().stack(8), "a  ", "aa ", "aaa", 'a', type.getPlanks().stack(1));
			addShaped(type.getStair().stack(8), "  a", " aa", "aaa", 'a', type.getPlanks().stack(1));
			addShaped(type.getFence().stack(3), "aba", "aba", 'a', type.getPlanks().stack(1), 'b', "stickWood");
			addShaped(type.getFenceGate().stack(1), "bab", "bab", 'a', type.getPlanks().stack(1), 'b', "stickWood");
			addShaped(type.getPressurePlate().stack(4), "aa", 'a', type.getPlanks().stack(1));
			addShapeless(type.getButton().stack(16), type.getPlanks().stack(1));
			addShaped(type.getDoor().stack(3), "aa", "aa", "aa", 'a', type.getPlanks().stack(1));
			addShaped(type.getTrapdoor().stack(12), "aaa", "aaa", 'a', type.getPlanks().stack(1));
		}
		
		/* Stone Recipes */
		if (CommonConfig.CommonItems.andesite && CommonConfig.CommonItems.polished_andesite) {
			addShaped(StoneTypes.ANDESITE.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.bountiful_stones, 1, 0));
			addSlab(new ItemStack(ModBlocks.bountiful_stone_slabs, 6, 0), new ItemStack(ModBlocks.bountiful_stones, 1, 0));
			addStair(new ItemStack(ModBlocks.bountiful_stone_stairs_a, 8, 0), new ItemStack(ModBlocks.bountiful_stones, 1, 0));
		}
		if (CommonConfig.CommonItems.diorite && CommonConfig.CommonItems.polished_diorite) {
			addShaped(StoneTypes.DIORITE.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.bountiful_stones, 1, 1));
			addSlab(new ItemStack(ModBlocks.bountiful_stone_slabs, 6, 1), new ItemStack(ModBlocks.bountiful_stones, 1, 1));
			addStair(new ItemStack(ModBlocks.bountiful_stone_stairs_a, 8, 8), new ItemStack(ModBlocks.bountiful_stones, 1, 1));
		}
		if (CommonConfig.CommonItems.granite && CommonConfig.CommonItems.polished_granite) {
			addShaped(StoneTypes.GRANITE.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.bountiful_stones, 1, 2));
			addSlab(new ItemStack(ModBlocks.bountiful_stone_slabs, 6, 2), new ItemStack(ModBlocks.bountiful_stones, 1, 2));
			addStair(new ItemStack(ModBlocks.bountiful_stone_stairs_b, 8, 0), new ItemStack(ModBlocks.bountiful_stones, 1, 2));
		}
		
		addShaped(StoneTypes.HELLSTONE.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.nether_stones, 1, 0));
		addSlab(new ItemStack(ModBlocks.nether_stone_slabs, 6, 0), new ItemStack(ModBlocks.nether_stones, 1, 0));
		addStair(new ItemStack(ModBlocks.nether_stone_stairs, 8, 0), new ItemStack(ModBlocks.nether_stones, 1, 0));
		addShaped(StoneTypes.BLOODSTONE.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.nether_stones, 1, 1));
		addSlab(new ItemStack(ModBlocks.nether_stone_slabs, 6, 1), new ItemStack(ModBlocks.nether_stones, 1, 1));
		addStair(new ItemStack(ModBlocks.nether_stone_stairs, 8, 8), new ItemStack(ModBlocks.nether_stones, 1, 1));
		
		addShaped(StoneTypes.DEPTH_STONE.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.depth_stones, 1, 0));
		addSlab(new ItemStack(ModBlocks.depth_stone_slabs, 6, 0), new ItemStack(ModBlocks.depth_stones, 1, 0));
		addStair(new ItemStack(ModBlocks.depth_stone_stairs_a, 8, 0), new ItemStack(ModBlocks.depth_stones, 1, 0));
		addShaped(StoneTypes.DEPTH_STONE_AMBER.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.depth_stones, 1, 1));
		addSlab(new ItemStack(ModBlocks.depth_stone_slabs, 6, 1), new ItemStack(ModBlocks.depth_stones, 1, 1));
		addStair(new ItemStack(ModBlocks.depth_stone_stairs_a, 8, 8), new ItemStack(ModBlocks.depth_stones, 1, 1));
		addShaped(StoneTypes.DEPTH_STONE_JADE.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.depth_stones, 1, 2));
		addSlab(new ItemStack(ModBlocks.depth_stone_slabs, 6, 2), new ItemStack(ModBlocks.depth_stones, 1, 2));
		addStair(new ItemStack(ModBlocks.depth_stone_stairs_b, 8, 0), new ItemStack(ModBlocks.depth_stones, 1, 2));
		addShaped(StoneTypes.DEPTH_STONE_RUBY.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.depth_stones, 1, 3));
		addSlab(new ItemStack(ModBlocks.depth_stone_slabs, 6, 3), new ItemStack(ModBlocks.depth_stones, 1, 3));
		addStair(new ItemStack(ModBlocks.depth_stone_stairs_b, 8, 8), new ItemStack(ModBlocks.depth_stones, 1, 3));
		addShaped(StoneTypes.DEPTH_STONE_SAPPHIRE.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.depth_stones, 1, 4));
		addSlab(new ItemStack(ModBlocks.depth_stone_slabs, 6, 4), new ItemStack(ModBlocks.depth_stones, 1, 4));
		addStair(new ItemStack(ModBlocks.depth_stone_stairs_c, 8, 0), new ItemStack(ModBlocks.depth_stones, 1, 4));
		addShaped(StoneTypes.DEPTH_STONE_TOPAZ.getBlock(4, StoneTypes.M_REGULAR), "aa", "aa", 'a', new ItemStack(ModBlocks.depth_stones, 1, 5));
		addSlab(new ItemStack(ModBlocks.depth_stone_slabs, 6, 5), new ItemStack(ModBlocks.depth_stones, 1, 5));
		addStair(new ItemStack(ModBlocks.depth_stone_stairs_c, 8, 8), new ItemStack(ModBlocks.depth_stones, 1, 5));
		
		for (StoneTypes type : StoneTypes.values()) {
			for (int m : StoneTypes.MLISTSHORT) {
				addShaped(type.getSlab(6, m), "aaa", 'a', type.getBlock(1, m));
				addShaped(type.getStair(8, m), "a  ", "aa ", "aaa", 'a', type.getBlock(1, m));
				addShaped(type.getStair(8, m), "  a", " aa", "aaa", 'a', type.getBlock(1, m));
			}
		}
		
		/* Colored Blocks */
		/* Wool */
		if (CommonConfig.General.wool_slabs_stairs) {
			addVanillaColoredSlabsStairs(ModBlocks.vanilla_wool_slabs, ModBlocks.vanilla_wool_stairs, Blocks.wool);
		}
		if (CommonConfig.General.wool_more_colors) {
			for (int i = 0, j0 = 0, dm0 = 0; i < 9; i++, j0 += 4, dm0 += 0x10) {
				for (int m = 0, ms = 0, j = 0; m < 8; m++, ms = ((m & 0x1) << 3), j = (m >> 1)) {
					addShapeless(new ItemStack(ModBlocks.wools[i], 1, m), new ItemStack(Blocks.wool, 1, 0), new ItemStack(ModItems.dye, 1, m + dm0));
					if (CommonConfig.General.wool_slabs_stairs) {
						addSlab(new ItemStack(ModBlocks.wool_slabs[i], 6, m), new ItemStack(ModBlocks.wools[i], 1, m));
						addStair(new ItemStack(ModBlocks.wool_stairs[j0 + j], 8, ms), new ItemStack(ModBlocks.wools[i], 1, m));
					}
				}
			}
		}
		/* Hardened Clay */
		if (CommonConfig.General.clay_slabs_stairs) {
			addSlab(new ItemStack(ModBlocks.hardened_clay_slab, 6), Blocks.hardened_clay);
			addStair(new ItemStack(ModBlocks.hardened_clay_slab, 8), Blocks.hardened_clay);
			addVanillaColoredSlabsStairs(ModBlocks.vanilla_stained_clay_slabs, ModBlocks.vanilla_stained_clay_stairs, Blocks.stained_hardened_clay);
		}
		if (CommonConfig.General.clay_more_colors) {
			for (int i = 0, j0 = 0, dm0 = 0; i < 9; i++, j0 += 4, dm0 += 0x10) {
				for (int m = 0, ms = 0, j = 0; m < 8; m++, ms = ((m & 0x1) << 3), j = (m >> 1)) {
					addShaped(new ItemStack(ModBlocks.stained_clays[i], 8, m), "aaa", "aba", "aaa", 'a', Blocks.hardened_clay, 'b', new ItemStack(ModItems.dye, 1, m + dm0));
					if (CommonConfig.General.clay_slabs_stairs) {
						addSlab(new ItemStack(ModBlocks.stained_clay_slabs[i], 6, m), new ItemStack(ModBlocks.stained_clays[i], 1, m));
						addStair(new ItemStack(ModBlocks.stained_clay_stairs[j0 + j], 8, ms), new ItemStack(ModBlocks.stained_clays[i], 1, m));
					}
				}
			}
		}
		/* Glass */
		if (CommonConfig.General.glass_slabs_stairs) {
			addSlab(new ItemStack(ModBlocks.glass_slab, 6), Blocks.glass);
			addStair(new ItemStack(ModBlocks.glass_stair, 8), Blocks.glass);
			addVanillaColoredSlabsStairs(ModBlocks.vanilla_stained_glass_slabs, ModBlocks.vanilla_stained_glass_stairs, Blocks.stained_glass);
		}
		if (CommonConfig.General.glass_more_colors) {
			for (int i = 0, j0 = 0, dm0 = 0; i < 9; i++, j0 += 4, dm0 += 0x10) {
				for (int m = 0, ms = 0, j = 0; m < 8; m++, ms = ((m & 0x1) << 3), j = (m >> 1)) {
					addShaped(new ItemStack(ModBlocks.stained_glass[i], 8, m), "aaa", "aba", "aaa", 'a', Blocks.glass, 'b', new ItemStack(ModItems.dye, 1, m + dm0));
					if (CommonConfig.General.glass_slabs_stairs) {
						addSlab(new ItemStack(ModBlocks.stained_glass_slabs[i], 6, m), new ItemStack(ModBlocks.stained_glass[i], 1, m));
						addStair(new ItemStack(ModBlocks.stained_glass_stairs[j0 + j], 8, ms), new ItemStack(ModBlocks.stained_glass[i], 1, m));
					}
					addShaped(new ItemStack(ModBlocks.stained_glass_panes[i >> 1], 16, m + ((i & 0x1) << 3)), "aaa", "aaa", 'a', new ItemStack(ModBlocks.stained_glass[i], 1, m));
				}
			}
		}
		/* Vanilla (Et Futurum) Concrete */
		if (CommonConfig.General.v_concrete_slabs_stairs) {
			addVanillaColoredSlabsStairs(ModBlocks.vanilla_concrete_slabs, ModBlocks.vanilla_concrete_stairs, OtherBlocks.CONCRETE.getBlock());
		}
		if (CommonConfig.General.v_concrete_more_colors) {
			for (int i = 0, j0 = 0, dm0 = 0; i < 9; i++, j0 += 4, dm0 += 0x10) {
				for (int m = 0, ms = 0, j = 0; m < 8; m++, ms = ((m & 0x1) << 3), j = (m >> 1)) {
					addShapeless(new ItemStack(ModBlocks.concrete_powders[i >> 1], 8, m + ((i & 0x1) << 3)), Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, new ItemStack(ModItems.dye, 1, m + dm0));
					if (CommonConfig.General.v_concrete_slabs_stairs) {
						addSlab(new ItemStack(ModBlocks.concrete_slabs[i], 6, m), new ItemStack(ModBlocks.concretes[i], 1, m));
						addStair(new ItemStack(ModBlocks.concrete_stairs[j0 + j], 8, ms), new ItemStack(ModBlocks.concretes[i], 1, m));
					}
				}
			}
		}
		/* HBM's Nuclear Tech Concrete */
		if (CommonConfig.General.hbm_concrete_slabs_stairs) {
			for (int i = 0, m0 = 0; i < 2; i++, m0 += 8) {
				for (int m = 0; m < 8; m++) {
					addSlab(new ItemStack(ModBlocks.hbm_base_concrete_slabs[i], 6, m), new ItemStack(OtherBlocks.HBM_CONCRETE_COLORED.getBlock(), 1, m + m0));
					if (i < 1) {
						addSlab(new ItemStack(ModBlocks.hbm_base_concrete_slabs[2], 6, m), new ItemStack(OtherBlocks.HBM_CONCRETE_COLORED_EXT.getBlock(), 1, m + m0));
					}
				}
			}
			for (int i = 0, m0 = 0; i < 8; i++, m0 += 2) {
				for (int m = 0; m < 2; m++) {
					addStair(new ItemStack(ModBlocks.hbm_base_concrete_stairs[i], 8, m), new ItemStack(OtherBlocks.HBM_CONCRETE_COLORED.getBlock(), 1, m + m0));
					if (i < 4) {
						addStair(new ItemStack(ModBlocks.hbm_base_concrete_stairs[i + 8], 8, m), new ItemStack(OtherBlocks.HBM_CONCRETE_COLORED_EXT.getBlock(), 1, m + m0));
					}
				}
			}
		}
		if (CommonConfig.General.hbm_concrete_more_colors) {
			for (int i = 0, j0 = 0, dm0 = 0; i < 9; i++, j0 += 4, dm0 += 0x10) {
				for (int m = 0, ms = 0, j = 0; m < 8; m++, ms = ((m & 0x1) << 3), j = (m >> 1)) {
					addShaped(new ItemStack(ModBlocks.hbm_concretes[i], 8, m), "aaa", "aba", "aaa", 'a', OtherBlocks.HBM_CONCRETE.stack(1), 'b', new ItemStack(ModItems.dye, 1, m + dm0));
					if (CommonConfig.General.hbm_concrete_slabs_stairs) {
						addSlab(new ItemStack(ModBlocks.hbm_concrete_slabs[i], 6, m), new ItemStack(ModBlocks.hbm_concretes[i], 1, m));
						addStair(new ItemStack(ModBlocks.hbm_concrete_stairs[j0 + j], 8, ms), new ItemStack(ModBlocks.hbm_concretes[i], 1, m));
					}
				}
			}
		}
		
		/* Prismarine */
		final BlockMultiStair[] PRIS_STAIR_A = new BlockMultiStair[] {
			ModBlocks.prismarine_stair_0, 
			ModBlocks.prismarine_stair_1,
			ModBlocks.prismarine_stair_2,
			ModBlocks.prismarine_stair_3
		};
		final BlockMultiStair[] PRIS_STAIR_B = new BlockMultiStair[] {
			ModBlocks.prismarine_stair_4,
			ModBlocks.prismarine_stair_5,
			ModBlocks.prismarine_stair_6
		};
		for (int m : MultiBlocks.PRISMARINE_0.metas) {
			addShaped(new ItemStack(ModBlocks.prismarine_slab_0, 6, m), "aaa", 'a', new ItemStack(ModBlocks.prismarine_0, 1, m));
			addShaped(new ItemStack(PRIS_STAIR_A[m >> 1], 8, (m & 0x1) << 3), "a  ", "aa ", "aaa", 'a', new ItemStack(ModBlocks.prismarine_0, 1, m));
			addShaped(new ItemStack(PRIS_STAIR_A[m >> 1], 8, (m & 0x1) << 3), "  a", " aa", "aaa", 'a', new ItemStack(ModBlocks.prismarine_0, 1, m));
		}
		for (int m : MultiBlocks.PRISMARINE_1.metas) {
			addShaped(new ItemStack(ModBlocks.prismarine_slab_1, 6, m), "aaa", 'a', new ItemStack(ModBlocks.prismarine_1, 1, m));
			addShaped(new ItemStack(PRIS_STAIR_B[m >> 1], 8, (m & 0x1) << 3), "a  ", "aa ", "aaa", 'a', new ItemStack(ModBlocks.prismarine_1, 1, m));
			addShaped(new ItemStack(PRIS_STAIR_B[m >> 1], 8, (m & 0x1) << 3), "  a", " aa", "aaa", 'a', new ItemStack(ModBlocks.prismarine_1, 1, m));
		}
		
		/* Potion Ingredients */
		addShapeless(new ItemStack(ModItems.potion_ingredients, 1, 0), new ItemStack(Items.dye, 1, Dyes.M_BLUE_LAPIS), Items.glowstone_dust);
		
		/* Dye */
		addShapeless(Dyes.CHARCOAL.stack(1), new ItemStack(Items.coal, 1, 1));
		addShapeless(Dyes.SHADOW_GRAY.stack(2), Dyes.CHARCOAL.stack(1), "dyeGray");
		addShapeless(Dyes.SMOKE_GRAY.stack(2), "dyeGray", "dyeLightGray");
		addShapeless(Dyes.PEARL_GRAY.stack(2), "dyeLightGray", "dyeWhite");
		addShapeless(Dyes.WARM_GRAY.stack(2), "dyeLightGray", Dyes.TAN.stack(1));
		addShapeless(Dyes.GRAPHITE.stack(2), "dyeGray", Dyes.FLINT.stack(1));
		addShapeless(Dyes.FLINT.stack(2), "dyeLightGray", Items.flint);
		
		addShapeless(Dyes.MAHOGANY.stack(3), "dyeBlack", "dyeBrown", "dyeRed");
		addShapeless(Dyes.MAROON.stack(2), "dyeBlack", "dyeRed");
		addShapeless(Dyes.WINE.stack(2), "dyeRed", "dyeGray");
		addShapeless(Dyes.REDWOOD.stack(2), "dyeRed", "dyeLightGray");
		addShapeless(Dyes.INDIAN_RED.stack(3), "dyeRed", "dyeLightGray", Dyes.REDWOOD.stack(1));
		addShapeless(Dyes.SALMON.stack(2), "dyeRed", "dyePink");
		addShapeless(Dyes.VERMILION.stack(3), "dyeRed", "dyeRed", "dyeLightGray");
		addShapeless(Dyes.SCARLET.stack(2), "dyeRed", "dyeOrange");
		
		addShapeless(Dyes.UMBER.stack(2), "dyeBlack", "dyeBrown");
		addShapeless(Dyes.RUST.stack(2), "dyeGray", "dyeOrange");
		addShapeless(Dyes.FULVOUS.stack(3), "dyeLightGray", "dyeOrange", "dyeYellow");
		addShapeless(Dyes.FULVOUS.stack(2), Dyes.CARROT_ORANGE.stack(1), "dyeLightGray");
		addShapeless(Dyes.CORAL.stack(2), "dyeOrange", "dyeWhite");
		addShapeless(Dyes.CARROT_ORANGE.stack(2), "dyeOrange", "dyeYellow");
		addShapeless(Dyes.CANTALOUPE.stack(2), Dyes.CORAL.stack(1), "dyeWhite");
		addShapeless(Dyes.APRICOT.stack(2), Dyes.CANTALOUPE.stack(1), "dyeLightGray");
		addShapeless(Dyes.PAPAYA_WHIP.stack(2), Dyes.APRICOT.stack(1), "dyeWhite");
		
		addShapeless(Dyes.MELLOW_YELLOW.stack(2), "dyeYellow", "dyeGray");
		addShapeless(Dyes.CYBER_YELLOW.stack(3), "dyeYellow", "dyeYellow", "dyeLightGray");
		addShapeless(Dyes.SAND.stack(2), "dyeYellow", "dyeLightGray");
		addShapeless(Dyes.TAN.stack(2), Dyes.SAND.stack(1), "dyeLightGray");
		addShapeless(Dyes.SEPIA.stack(2), Dyes.TAN.stack(1), "dyeOrange");
		addShapeless(Dyes.LEMON.stack(2), Dyes.GREEN_YELLOW.stack(1), "dyeYellow");
		addShapeless(Dyes.GREEN_YELLOW.stack(2), Dyes.CHARTREUSE.stack(1), "dyeYellow");
		addShapeless(Dyes.CHARTREUSE.stack(2), "dyeYellow", "dyeLime");
		
		addShapeless(Dyes.DARK_GREEN.stack(2), "dyeGreen", "dyeBlack");
		addShapeless(Dyes.ARMY_GREEN.stack(2), Dyes.DARK_GREEN.stack(1), "dyeBrown");
		addShapeless(Dyes.SHEEN_GREEN.stack(3), "dyeGreen", "dyeLime", "dyeLightGray");
		addShapeless(Dyes.SEA_GREEN.stack(3), "dyeGray", "dyeCyan", "dyeLime");
		addShapeless(Dyes.KELLY_GREEN.stack(1), "dyeGreen", "dyeLime");
		addShapeless(Dyes.SPRING_GREEN.stack(2), "dyeLime", "dyeLightBlue");
		addShapeless(Dyes.TEA_GREEN.stack(3), "dyeLime", "dyeLightGray", "dyeLightGray");
		addShapeless(Dyes.SAGE_GREEN.stack(3), "dyeLime", "dyeGray", "dyeGray");
		
		addShapeless(Dyes.PRUSSIAN_BLUE.stack(2), "dyeCyan", "dyeBlack");
		addShapeless(Dyes.AEGEAN.stack(3), "dyeLightBlue", "dyeGray", "dyeLightGray");
		addShapeless(Dyes.ZYDECO.stack(3), "dyeCyan", "dyeGreen", "dyeBlack");
		addShapeless(Dyes.TURKISH_BLUE.stack(2), Dyes.AEGEAN.stack(1), "dyeLightGray");
		addShapeless(Dyes.TURQUOISE.stack(2), "dyeCyan", "dyeWhite");
		addShapeless(Dyes.AQUAMARINE.stack(2), Dyes.TURQUOISE.stack(1), "dyeWhite");
		addShapeless(Dyes.CELESTE.stack(2), "dyeLightBlue", "dyeWhite");
		addShapeless(Dyes.PEWTER_BLUE.stack(2), "dyeLightBlue", "dyeLightGray");
		
		addShapeless(Dyes.MIDNIGHT_FROST.stack(2), Dyes.NIGHT_BLUE.stack(1), "dyeBlack");
		addShapeless(Dyes.NIGHT_BLUE.stack(2), Dyes.NAVY_BLUE.stack(1), "dyeBlack");
		addShapeless(Dyes.NAVY_BLUE.stack(2), "dyeBlue", "dyeBlack");
		addShapeless(Dyes.CERULEAN.stack(2), "dyeCyan", "dyeLightBlue");
		addShapeless(Dyes.STEEL_BLUE.stack(2), "dyeLightBlue", "dyeGray");
		addShapeless(Dyes.INDEPENDENCE_BLUE.stack(2), "dyeBlue", "dyeGray");
		addShapeless(Dyes.PICOTEE_BLUE.stack(2), Dyes.NAVY_BLUE.stack(1), "dyeLightGray");
		addShapeless(Dyes.ULTRAMARINE.stack(2), Dyes.INDIGO.stack(1), "dyeLightBlue");
		
		addShapeless(Dyes.INDIGO.stack(2), "dyeBlue", "dyePurple");
		addShapeless(Dyes.DEEP_PURPLE.stack(2), "dyePurple", "dyeBlack");
		addShapeless(Dyes.RAISIN.stack(2), Dyes.DEEP_PURPLE.stack(1), "dyeGray");
		addShapeless(Dyes.ROYAL_PURPLE.stack(2), Dyes.MEDIUM_PURPLE.stack(1), "dyeLightGray");
		addShapeless(Dyes.MEDIUM_PURPLE.stack(2), "dyePurple", "dyeWhite");
		addShapeless(Dyes.IRIS.stack(2), Dyes.MEDIUM_PURPLE.stack(1), "dyeLightGray");
		addShapeless(Dyes.PERIWINKLE.stack(2), Dyes.MEDIUM_PURPLE.stack(1), "dyeWhite");
		addShapeless(Dyes.THISTLE.stack(2), Dyes.PERIWINKLE.stack(1), "dyeLightGray");
		
		addShapeless(Dyes.BERRY_MAGENTA.stack(2), "dyeBlack", "dyeMagenta");
		addShapeless(Dyes.BYZANTINE.stack(3), "dyeBlack", "dyePurple", "dyeMagenta");
		addShapeless(Dyes.MULBERRY.stack(2), Dyes.BERRY_MAGENTA.stack(1), "dyeLightGray");
		addShapeless(Dyes.ROSE.stack(2), "dyeRed", "dyeMagenta");
		addShapeless(Dyes.DUSTY_PINK.stack(2), Dyes.THULIAN_PINK.stack(1), "dyeLightGray");
		addShapeless(Dyes.THULIAN_PINK.stack(2), "dyeGray", "dyePink");
		addShapeless(Dyes.FLAMINGO_PINK.stack(2), Dyes.ROSE.stack(1), "dyePink");
		addShapeless(Dyes.LIGHT_ORCHID.stack(2), Dyes.THISTLE.stack(1), "dyePink");
		
		addShapeless(Dyes.THULIAN_PINK.stack(1), MultiBlockStacks.FLOWER_ASTER.stack(1));
		addShapeless(Dyes.CYBER_YELLOW.stack(1), MultiBlockStacks.FLOWER_BLACK_EYED_SUSAN.stack(1));
		addShapeless(Dyes.CARROT_ORANGE.stack(1), MultiBlockStacks.FLOWER_CALENDULA.stack(1));
		addShapeless(Dyes.FULVOUS.stack(1), MultiBlockStacks.FLOWER_DAHLIA.stack(1));
		addShapeless(Dyes.CERULEAN.stack(1), MultiBlockStacks.FLOWER_FORGETMENOT.stack(1));
		addShapeless(Dyes.ULTRAMARINE.stack(1), MultiBlockStacks.FLOWER_HYACINTH.stack(1));
		addShapeless(Dyes.IRIS.stack(1), MultiBlockStacks.FLOWER_IRIS.stack(1));
		addShapeless(Dyes.MELLOW_YELLOW.stack(1), MultiBlockStacks.FLOWER_YELLOW_DAFFODIL.stack(1));
		
		/* Resource Packing/Unpacking */
		if (CommonConfig.CommonItems.iron_nuggets) {
			addShaped(new ItemStack(Items.iron_ingot), "aaa", "aaa", "aaa", 'a', ModItems.iron_nugget);
			addShapeless(new ItemStack(ModItems.iron_nugget, 9), Items.iron_ingot);
		}
		addShaped(MultiBlockStacks.IRON_PILE.stack(1), "aa", 'a', "nuggetIron");
		addShaped(MultiBlockStacks.GOLD_PILE.stack(1), "aa", 'a', Items.gold_nugget);
		addShaped(MultiBlockStacks.DIAMOND_PILE.stack(1), "aa", 'a', Items.diamond);
		addShaped(MultiBlockStacks.EMERALD_PILE.stack(1), "aa", 'a', Items.emerald);
		addShapeless(CommonItems.IRON_NUGGET.stack(2), MultiBlockStacks.IRON_PILE.stack(1));
		addShapeless(new ItemStack(Items.gold_nugget, 2), MultiBlockStacks.GOLD_PILE.stack(1));
		addShapeless(new ItemStack(Items.diamond, 2), MultiBlockStacks.DIAMOND_PILE.stack(1));
		addShapeless(new ItemStack(Items.emerald, 2), MultiBlockStacks.EMERALD_PILE.stack(1));
		for (int m : MultiItems.INGOTS.metas) {
			addShaped(new ItemStack(ModItems.ingots, 1, m), "aaa", "aaa", "aaa", 'a', new ItemStack(ModItems.nuggets, 1, m));
			addShapeless(new ItemStack(ModItems.nuggets, 9, m), new ItemStack(ModItems.ingots, 1, m));
			addShaped(new ItemStack(ModBlocks.metal_blocks, 1, m), "aaa", "aaa", "aaa", 'a', new ItemStack(ModItems.ingots, 1, m));
			addShapeless(new ItemStack(ModItems.ingots, 9, m), new ItemStack(ModBlocks.metal_blocks, 1, m));
			if (!(!CommonConfig.CommonItems.raw_silver && m == 0) || (!CommonConfig.CommonItems.raw_platinum && m == 1)) {
				addShaped(new ItemStack(ModBlocks.raw_metal_blocks, 1, m), "aaa", "aaa", "aaa", 'a', new ItemStack(ModItems.raws, 1, m));
				addShapeless(new ItemStack(ModItems.raws, 9, m), new ItemStack(ModBlocks.raw_metal_blocks, 1, m));
			}
		}
		for (int m : MultiBlocks.METAL_PILES_A.metas) {
			addShaped(new ItemStack(ModBlocks.metal_piles_a, 1, m), "aa", 'a', new ItemStack(ModItems.nuggets, 1, m));
			addShapeless(new ItemStack(ModItems.nuggets, 2, m), new ItemStack(ModBlocks.metal_piles_a, 1, m));
		}
		for (int m : MultiItems.GEMS.metas) {
			addShaped(new ItemStack(ModBlocks.gem_blocks, 1, m), "aaa", "aaa", "aaa", 'a', new ItemStack(ModItems.gems, 1, m));
			addShapeless(new ItemStack(ModItems.gems, 9, m), new ItemStack(ModBlocks.gem_blocks, 1, m));
		}
		for (int m : MultiBlocks.GEM_PILES_A.metas) {
			addShaped(new ItemStack(ModBlocks.gem_piles_a, 1, m), "aa", 'a', new ItemStack(ModItems.gems, 1, m));
			addShapeless(new ItemStack(ModItems.gems, 2, m), new ItemStack(ModBlocks.gem_piles_a, 1, m));
		}

		/* Pickaxes */
		addShaped(new ItemStack(ModItems.silver_pickaxe), "aaa", " b ", " b ", 'a', MultiItemStacks.SILVER_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.platinum_pickaxe), "aaa", " b ", " b ", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.ancient_gem_pickaxe), "aaa", " b ", " b ", 'a', MultiItemStacks.ANCIENT_GEM.stack(1), 'b', "stickWood");

		/* Axes */
		addShaped(new ItemStack(ModItems.silver_axe), "aa", "ab", " b", 'a', MultiItemStacks.SILVER_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.platinum_axe), "aa", "ab", " b", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.ancient_gem_axe), "aa", "ab", " b", 'a', MultiItemStacks.ANCIENT_GEM.stack(1), 'b', "stickWood");

		/* Shovels */
		addShaped(new ItemStack(ModItems.silver_shovel), "a", "b", "b", 'a', MultiItemStacks.SILVER_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.platinum_shovel), "a", "b", "b", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.ancient_gem_shovel), "a", "b", "b", 'a', MultiItemStacks.ANCIENT_GEM.stack(1), 'b', "stickWood");

		/* Swords */
		addShaped(new ItemStack(ModItems.silver_sword), "a", "a", "b", 'a', MultiItemStacks.SILVER_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.platinum_sword), "a", "a", "b", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.ancient_gem_sword), "a", "a", "b", 'a', MultiItemStacks.ANCIENT_GEM.stack(1), 'b', "stickWood");

		/* Hoes */
		addShaped(new ItemStack(ModItems.silver_hoe), "aa", " b", " b", 'a', MultiItemStacks.SILVER_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.platinum_hoe), "aa", " b", " b", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.ancient_gem_hoe), "aa", " b", " b", 'a', MultiItemStacks.ANCIENT_GEM.stack(1), 'b', "stickWood");

		/* Scythes */
		addShaped(new ItemStack(ModItems.wood_scythe), "aaa", "  b", "  b", 'a', "plankWood", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.stone_scythe), "aaa", "  b", "  b", 'a', "cobblestone", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.iron_scythe), "aaa", "  b", "  b", 'a', "ingotIron", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.gold_scythe), "aaa", "  b", "  b", 'a', "ingotGold", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.diamond_scythe), "aaa", "  b", "  b", 'a', "gemDiamond", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.silver_scythe), "aaa", "  b", "  b", 'a', MultiItemStacks.SILVER_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.platinum_scythe), "aaa", "  b", "  b", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.ancient_gem_scythe), "aaa", "  b", "  b", 'a', MultiItemStacks.ANCIENT_GEM.stack(1), 'b', "stickWood");

		/* Halberds */
		addShaped(new ItemStack(ModItems.wood_halberd), "  a", " b ", "b  ", 'a', "plankWood", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.stone_halberd), "  a", " b ", "b  ", 'a', "cobblestone", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.iron_halberd), "  a", " b ", "b  ", 'a', "ingotIron", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.gold_halberd), "  a", " b ", "b  ", 'a', "ingotGold", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.diamond_halberd), "  a", " b ", "b  ", 'a', "gemDiamond", 'b', "stickWood");
		addShaped(new ItemStack(ModItems.silver_halberd), "  a", " b ", "b  ", 'a', MultiItemStacks.SILVER_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.platinum_halberd), "  a", " b ", "b  ", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1), 'b', "stickWood");
		addShaped(new ItemStack(ModItems.ancient_gem_halberd), "  a", " b ", "b  ", 'a', MultiItemStacks.ANCIENT_GEM.stack(1), 'b', "stickWood");

		/* Helmets */
		addShaped(new ItemStack(ModItems.silver_helmet), "aaa", "a a", 'a', MultiItemStacks.SILVER_INGOT.stack(1));
		addShaped(new ItemStack(ModItems.platinum_helmet), "aaa", "a a", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1));
		addShaped(new ItemStack(ModItems.ancient_gem_helmet), "aaa", "a a", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1));

		/* Chestplates */
		addShaped(new ItemStack(ModItems.silver_chestplate), "a a", "aaa", "aaa", 'a', MultiItemStacks.SILVER_INGOT.stack(1));
		addShaped(new ItemStack(ModItems.platinum_chestplate), "a a", "aaa", "aaa", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1));
		addShaped(new ItemStack(ModItems.ancient_gem_chestplate), "a a", "aaa", "aaa", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1));

		/* Leggings */
		addShaped(new ItemStack(ModItems.silver_leggings), "aaa", "a a", "a a", 'a', MultiItemStacks.SILVER_INGOT.stack(1));
		addShaped(new ItemStack(ModItems.platinum_leggings), "aaa", "a a", "a a", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1));
		addShaped(new ItemStack(ModItems.ancient_gem_leggings), "aaa", "a a", "a a", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1));

		/* Boots */
		addShaped(new ItemStack(ModItems.silver_boots), "a a", "a a", 'a', MultiItemStacks.SILVER_INGOT.stack(1));
		addShaped(new ItemStack(ModItems.platinum_boots), "a a", "a a", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1));
		addShaped(new ItemStack(ModItems.ancient_gem_boots), "a a", "a a", 'a', MultiItemStacks.PLATINUM_INGOT.stack(1));
		
		/* Vessels */
		addShaped(new ItemStack(ModItems.vessels, 1, VesselTypes.HEART_CONTAINER.meta), "aa", "aa", 'a', new ItemStack(ModItems.vessels, 1, VesselTypes.HEART_CONTAINER.meta | ItemVessel.VF_PIECE));
		addShaped(new ItemStack(ModItems.vessels, 1, VesselTypes.MANA_VESSEL.meta), "aaa", "aaa", "aaa", 'a', new ItemStack(ModItems.vessels, 1, VesselTypes.MANA_VESSEL.meta | ItemVessel.VF_PIECE));
		
		/* Utility */
		addShaped(new ItemStack(ModBlocks.stonecutter), "aa", "aa", 'a', "cobblestone");
		
		addShaped(new ItemStack(ModItems.keys, 2, 0x1f), " a ", "aba", " a ", 'a', new ItemStack(ModItems.potion_ingredients, 1, 0), 'b', new ItemStack(ModItems.keys, 1, 0x10));
		addShaped(new ItemStack(ModItems.keys, 2, 0x1f), " a ", "aba", " a ", 'a', new ItemStack(ModItems.potion_ingredients, 1, 0), 'b', new ItemStack(ModItems.keys, 1, 0x11));
		addShaped(new ItemStack(ModItems.keys, 2, 0x1f), " a ", "aba", " a ", 'a', new ItemStack(ModItems.potion_ingredients, 1, 0), 'b', new ItemStack(ModItems.keys, 1, 0x12));
		addShaped(new ItemStack(ModItems.keys, 2, 0x1f), " a ", "aba", " a ", 'a', new ItemStack(ModItems.potion_ingredients, 1, 0), 'b', new ItemStack(ModItems.keys, 1, 0x13));
		addShaped(new ItemStack(ModItems.keys, 2, 0x1f), " a ", "aba", " a ", 'a', new ItemStack(ModItems.potion_ingredients, 1, 0), 'b', new ItemStack(ModItems.keys, 1, 0x14));
		addShaped(new ItemStack(ModItems.keys, 2, 0x1f), " a ", "aba", " a ", 'a', new ItemStack(ModItems.potion_ingredients, 1, 0), 'b', new ItemStack(ModItems.keys, 1, 0x15));
		addShaped(new ItemStack(ModItems.keys, 2, 0x1f), " a ", "aba", " a ", 'a', new ItemStack(ModItems.potion_ingredients, 1, 0), 'b', new ItemStack(ModItems.keys, 1, 0x16));
		addShaped(new ItemStack(ModItems.keys, 2, 0x1f), " a ", "aba", " a ", 'a', new ItemStack(ModItems.potion_ingredients, 1, 0), 'b', new ItemStack(ModItems.keys, 1, 0x17));
	}
	
	private static void addVanillaColoredSlabsStairs(Block[] slabs, Block[] stairs, Block block)
	{
		for (int i = 0, m0 = 0; i < 2; i++, m0 += 8) {
			for (int m = 0; m < 8; m++) {
				addSlab(new ItemStack(slabs[i], 6, m), new ItemStack(block, 1, m + m0));
			}
		}
		for (int i = 0, m0 = 0; i < 8; i++, m0 += 2) {
			for (int m = 0; m < 2; m++) {
				addStair(new ItemStack(stairs[i], 8, m), new ItemStack(block, 1, m + m0));
			}
		}
	}
	
	private static void addSlab(ItemStack result, Object ingredient)
	{
		addShaped(result, "aaa", 'a', ingredient);
	}
	
	private static void addStair(ItemStack result, Object ingredient)
	{
		addShaped(result, "a  ", "aa ", "aaa", 'a', ingredient);
		addShaped(result, "  a", " aa", "aaa", 'a', ingredient);
	}

	private static void addShaped(ItemStack result, Object... params)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(result, params));
	}

	private static void addShapeless(ItemStack result, Object... params)
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(result, params));
	}
}
