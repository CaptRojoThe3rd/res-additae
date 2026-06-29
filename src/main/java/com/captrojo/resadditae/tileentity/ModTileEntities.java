package com.captrojo.resadditae.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TEStonecutter.class, "resadditae.stonecutter");
		GameRegistry.registerTileEntity(TEMultiSpawner.class, "resadditae.multi_spawner");
		GameRegistry.registerTileEntity(TESnowDungeonSpawner.class, "resadditae.snow_dungeon_spawner");
		GameRegistry.registerTileEntity(TEVault.class, "resadditae.vault");
		GameRegistry.registerTileEntity(TEStructureBlock.class, "resadditae.structure_block");
	}
}
