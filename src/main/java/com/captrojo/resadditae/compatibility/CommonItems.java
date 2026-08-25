package com.captrojo.resadditae.compatibility;

import com.captrojo.resadditae.config.JsonConfig;
import com.captrojo.resadditae.config.common.CommonStuffConfig;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum CommonItems
{
	IRON_NUGGET(
		ModItems.iron_nugget, 0, CommonStuffConfig.iron_nuggets, "Iron Nugget",
		ModList.ET_FUTURUM, "nugget_iron", 0,
		ModList.GARDEN_STUFF, "iron_nugget", 0,
		ModList.HEXCRAFT, "itemIronNugget", 0,
		ModList.NETHERLICIOUS, "Nugget", 0,
		ModList.THAUMCRAFT, "ItemNugget", 0,
		ModList.TINKERS_CONSTRUCT, "materials", 19,
		ModList.TOTEMIC, "subItems", 0
	),
	
	RAW_SILVER(
		ModItems.raws, 0, CommonStuffConfig.raw_silver, "Raw Silver",
		ModList.ET_FUTURUM, "modded_raw_ore", 2
	),
	RAW_PLATINUM(
		ModItems.raws, 1, CommonStuffConfig.raw_platinum, "Raw Platinum",
		ModList.ET_FUTURUM, "modded_raw_ore", 5
	);
	
	private ItemStack stack;
	
	private CommonItems(Item ra_item, int meta, boolean enabled, String name, Object...objs)
	{
		CommonStuffStatus.beginNew(name);
		
		if (ra_item != null && enabled) {
			this.stack = new ItemStack(ra_item, 1, meta);
			return;
		}
		
		for (int i = 0; i < objs.length; i += 3) {
			String o_mod = ((ModList) objs[i]).id;
			String o_name = (String) objs[i + 1];
			int o_meta = (int) objs[i + 2];
			
			if (this.findItem(o_mod, o_name, o_meta)) {
				return;
			}
		}
		
		ModNameMeta m = ModNameMeta.create(JsonConfig.getString(JsonConfig.obj_common_stuff, name));
		if (m != null) {
			if (this.findItem(m.mod, m.name, m.meta)) {
				return;
			}
		}
		
		CommonStuffStatus.reportError();
	}
	
	private boolean findItem(String mod, String name, int meta)
	{
		Item item = GameRegistry.findItem(mod, name);
		if (item == null) {
			CommonStuffStatus.addAttempted(mod, name, meta);
			return false;
		}
		this.stack = new ItemStack(item, 1, meta);
		return true;
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
