package com.captrojo.resadditae.extprop;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.captrojo.resadditae.world.loot.LootGroup;

import net.minecraft.nbt.NBTTagCompound;

public class DevData
{
	RAPlayerProperties rpp;
	
	public Map<String, LootGroup> loot_groups;
	public String selected_group;
	public int selected_pool;
	
	public DevData(RAPlayerProperties rpp)
	{
		this.loot_groups = new HashMap<String, LootGroup>();
		
		this.selected_group = null;
		this.selected_pool = -1;
	}
	
	public NBTTagCompound saveToNBT(NBTTagCompound nbt)
	{
		NBTTagCompound loots_nbt = new NBTTagCompound();
		for (Entry<String, LootGroup> loot : this.loot_groups.entrySet()) {
			loots_nbt.setTag(loot.getKey(), loot.getValue().saveToNBT(new NBTTagCompound()));
		}
		nbt.setTag("LootGroups", loots_nbt);
		
		return nbt;
	}
	
	public DevData loadFromNBT(NBTTagCompound nbt)
	{
		NBTTagCompound loots_nbt = nbt.getCompoundTag("LootGroups");
		this.loot_groups.clear();
		for (Object obj : loots_nbt.func_150296_c()) {
			Entry entry = (Entry) obj;
			this.loot_groups.put((String) entry.getKey(), (new LootGroup()).loadFromNBT((NBTTagCompound) entry.getValue()));
		}
		
		return this;
	}
}
