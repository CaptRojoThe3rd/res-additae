package com.captrojo.resadditae.compatibility.helper;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.StoneTypes;
import com.captrojo.resadditae.block.ore.BlockOreFromDict;
import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.handler.nei.StonecutterRecipeHandler;
import com.captrojo.resadditae.main.ResAdditae;

import codechicken.nei.api.API;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class NEIHlpr
{
	public static void registerHandlers()
	{
		StonecutterRecipeHandler stonecutter_recipe_handler = new StonecutterRecipeHandler();
		API.registerRecipeHandler(stonecutter_recipe_handler);
		API.registerUsageHandler(stonecutter_recipe_handler);
	}
	
	public static void sendInfoToGTNH()
	{
		sendHandler(StonecutterRecipeHandler.class.getName(), "stonecutter", ModBlocks.stonecutter.getUnlocalizedName(), 4, 170, 42);
		sendCatalyst(StonecutterRecipeHandler.class.getName(), ModBlocks.stonecutter.getUnlocalizedName(), 0);
	}
	
	public static void hideItems()
	{
		if (!CommonConfig.CommonStuff.andesite) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stones, 1, 0));
		}
		if (!CommonConfig.CommonStuff.andesite_polished) {
			API.hideItem(StoneTypes.ANDESITE.getBlock(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.andesite_bricks) { 
			API.hideItem(StoneTypes.ANDESITE.getBlock(1, StoneTypes.M_BRICKS));
		}
		if (!CommonConfig.CommonStuff.andesite_slab) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 0));
		}
		if (!CommonConfig.CommonStuff.andesite_polished_slab) {
			API.hideItem(StoneTypes.ANDESITE.getSlab(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.andesite_brick_slab) {
			API.hideItem(StoneTypes.ANDESITE.getSlab(1, StoneTypes.M_BRICKS));
		}
		if (!CommonConfig.CommonStuff.andesite_stair) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stone_stairs_a, 1, 0));
		}
		if (!CommonConfig.CommonStuff.andesite_polished_stair) {
			API.hideItem(StoneTypes.ANDESITE.getStair(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.andesite_brick_stair) {
			API.hideItem(StoneTypes.ANDESITE.getStair(1, StoneTypes.M_BRICKS));
		}
		
		if (!CommonConfig.CommonStuff.diorite) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stones, 1, 1));
		}
		if (!CommonConfig.CommonStuff.diorite_polished) {
			API.hideItem(StoneTypes.DIORITE.getBlock(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.diorite_bricks) { 
			API.hideItem(StoneTypes.DIORITE.getBlock(1, StoneTypes.M_BRICKS));
		}
		if (!CommonConfig.CommonStuff.diorite_slab) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 1));
		}
		if (!CommonConfig.CommonStuff.diorite_polished_slab) {
			API.hideItem(StoneTypes.DIORITE.getSlab(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.diorite_brick_slab) {
			API.hideItem(StoneTypes.DIORITE.getSlab(1, StoneTypes.M_BRICKS));
		}
		if (!CommonConfig.CommonStuff.diorite_stair) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stone_stairs_a, 1, 8));
		}
		if (!CommonConfig.CommonStuff.diorite_polished_stair) {
			API.hideItem(StoneTypes.DIORITE.getStair(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.diorite_brick_stair) {
			API.hideItem(StoneTypes.DIORITE.getStair(1, StoneTypes.M_BRICKS));
		}
		
		if (!CommonConfig.CommonStuff.granite) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stones, 1, 2));
		}
		if (!CommonConfig.CommonStuff.granite_polished) {
			API.hideItem(StoneTypes.GRANITE.getBlock(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.granite_bricks) { 
			API.hideItem(StoneTypes.GRANITE.getBlock(1, StoneTypes.M_BRICKS));
		}
		if (!CommonConfig.CommonStuff.granite_slab) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stone_slabs, 1, 2));
		}
		if (!CommonConfig.CommonStuff.granite_polished_slab) {
			API.hideItem(StoneTypes.GRANITE.getSlab(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.granite_brick_slab) {
			API.hideItem(StoneTypes.GRANITE.getSlab(1, StoneTypes.M_BRICKS));
		}
		if (!CommonConfig.CommonStuff.granite_stair) {
			API.hideItem(new ItemStack(ModBlocks.bountiful_stone_stairs_b, 1, 0));
		}
		if (!CommonConfig.CommonStuff.granite_polished_stair) {
			API.hideItem(StoneTypes.GRANITE.getStair(1, StoneTypes.M_POLISHED));
		}
		if (!CommonConfig.CommonStuff.granite_brick_stair) {
			API.hideItem(StoneTypes.GRANITE.getStair(1, StoneTypes.M_BRICKS));
		}
		
		API.hideItem(new ItemStack(ModBlocks.vanilla_wool_double_slabs[0], 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(ModBlocks.vanilla_wool_double_slabs[1], 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(ModBlocks.glass_double_slab, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(ModBlocks.vanilla_stained_glass_double_slabs[0], 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(ModBlocks.vanilla_stained_glass_double_slabs[1], 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(ModBlocks.hardened_clay_double_slab, 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(ModBlocks.vanilla_stained_clay_double_slabs[0], 1, OreDictionary.WILDCARD_VALUE));
		API.hideItem(new ItemStack(ModBlocks.vanilla_stained_clay_double_slabs[1], 1, OreDictionary.WILDCARD_VALUE));
		if (CommonConfig.General.v_concrete_more_colors) {
			API.hideItem(new ItemStack(ModBlocks.vanilla_concrete_double_slabs[0], 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModBlocks.vanilla_concrete_double_slabs[1], 1, OreDictionary.WILDCARD_VALUE));
		}
		if (CommonConfig.General.hbm_concrete_more_colors) {
			API.hideItem(new ItemStack(ModBlocks.hbm_base_concrete_double_slabs[0], 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModBlocks.hbm_base_concrete_double_slabs[1], 1, OreDictionary.WILDCARD_VALUE));
			API.hideItem(new ItemStack(ModBlocks.hbm_base_concrete_double_slabs[2], 1, OreDictionary.WILDCARD_VALUE));
		}
		
		for (BlockOreFromDict block : ModBlocks.oredict_ores) {
			if (!block.isOreAvailable()) {
				API.hideItem(new ItemStack(block, 1, OreDictionary.WILDCARD_VALUE));
			}
		}
	}
	
	private static void sendHandler(String handler_name, String handler_id, String item, int max_recipes_per_page, int width, int height)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("handler", handler_name);
		tag.setString("handlerID", handler_id);
		tag.setString("modName", ResAdditae.NAME);
		tag.setString("modId", ResAdditae.MOD_ID);
		tag.setBoolean("modRequired", true);
		tag.setString("itemName", ResAdditae.ident(item));
		tag.setInteger("handlerHeight", height);
		tag.setInteger("handlerWidth", width);
		tag.setInteger("maxRecipesPerPage", max_recipes_per_page);
		tag.setInteger("yShift", 6);
		FMLInterModComms.sendMessage(ModList.NEI.id, "registerHandlerInfo", tag);
	}
	
	private static void sendCatalyst(String handler_id, String item, int priority)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("handlerID", handler_id);
		tag.setString("catalystHandlerID", handler_id);
		tag.setString("itemName", ResAdditae.ident(item));
		tag.setInteger("priority", priority);
		FMLInterModComms.sendMessage(ModList.NEI.id, "registerCatalystInfo", tag);
	}
}
