package com.captrojo.resadditae.world.gen;

import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.terraingen.BiomeEvent;

public class WorldGenEventHandler
{
	/*
	 * It would be nice if this actually worked.
	 * Unfortunately, event.biome is usually null for some reason.
	 */
	@SubscribeEvent
	public void getVillageBlock(BiomeEvent.GetVillageBlockID event)
	{
		if (ModList.VILLAGE_NAMES.isLoaded()) {
			return;
		}
		if (event.biome == BiomeGenBase.taiga) {
			if (event.original == Blocks.oak_stairs) {
				event.replacement = Blocks.spruce_stairs;
				event.setResult(Result.DENY);
				return;
			}
		}
	}
	
	@SubscribeEvent
	public void getVillageBlockMeta(BiomeEvent.GetVillageBlockMeta event)
	{
		if (ModList.VILLAGE_NAMES.isLoaded()) {
			return;
		}
		if (event.biome == BiomeGenBase.taiga) {
			if (event.original == Blocks.planks) {
				event.replacement = 1;
				event.setResult(Result.DENY);
				return;
			}
			if (event.original == Blocks.log) {
				event.replacement = 1;
				event.setResult(Result.DENY);
				return;
			}
		}
	}
}
