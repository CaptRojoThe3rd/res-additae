package com.captrojo.resadditae.world.loot;

import com.captrojo.resadditae.main.NBTHlpr;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ModLoot
{
	public static LootGroup[] snow_dungeon_vaults;
	
	public static void load()
	{
		snow_dungeon_vaults = new LootGroup[8];
		for (int i = 1; i < 9; i++) {
			ResourceLocation rl = ResAdditae.resource("loot/snow_dungeon_vault_" + i + ".nbt");
			NBTTagCompound nbt = NBTHlpr.loadNBTFromResource(rl);
			snow_dungeon_vaults[i - 1] = new LootGroup(nbt);
		}
	}
}
