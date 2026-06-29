package com.captrojo.resadditae.compatibility;

import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum CommonItems
{
	IRON_NUGGET(
		ModItems.iron_nugget, 0, CommonConfig.CommonItems.iron_nuggets, "Iron Nugget",
		ModList.ET_FUTURUM, "nugget_iron", 0
	),
	
	RAW_SILVER(
		ModItems.raws, 0, CommonConfig.CommonItems.raw_silver, "Raw Silver",
		ModList.ET_FUTURUM, "modded_raw_ore", 2
	),
	RAW_PLATINUM(
		ModItems.raws, 1, CommonConfig.CommonItems.raw_platinum, "Raw Platinum",
		ModList.ET_FUTURUM, "modded_raw_ore", 5
	);
	
	
	
	private ItemStack stack;
	
	private CommonItems(Item ra_item, int meta, boolean enabled, String name, Object...objs)
	{
		if (ra_item != null && enabled) {
			this.stack = new ItemStack(ra_item, 1, meta);
			return;
		}
		
		for (int i = 0; i < objs.length; i += 3) {
			ModList mod = (ModList) objs[i];
			if (!mod.isLoaded()) {
				continue;
			}
			Item item = GameRegistry.findItem(mod.id, (String) objs[i + 1]);
			if (item == null) {
				continue;
			}
			this.stack = new ItemStack(item, 1, (int) objs[i + 2]);
			return;
		}
		
		ResAdditae.LOG.error(String.format("Failed to find any instance of '%s'.", name));
		ResAdditae.common_items_error = true;
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
}
