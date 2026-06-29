package com.captrojo.resadditae.block.ore;

import java.util.ArrayList;
import java.util.List;

import com.captrojo.resadditae.compatibility.ModOreDict;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BlockOreFromDict extends BlockOreBase
{
	public static enum FromOredictType
	{
		DROP_MATERIAL,
		SMELT_FOR_MATERIAL
	}
	
	public final String ore_oredict_key;
	public final String material_oredict_key;
	public final FromOredictType type;
	
	private ItemStack material_item;
	
	public BlockOreFromDict(String name, String ore_oredict_key, String material_oredict_key, FromOredictType type, OreStones[] stones)
	{
		super(name, stones);
		
		this.ore_oredict_key = ore_oredict_key;
		this.material_oredict_key = material_oredict_key;
		this.type = type;
	}
	
	public void initAndCheckAvailability()
	{
		this.material_item = ModOreDict.getSingleFromOreDict(this.material_oredict_key);
	}
	
	public boolean isOreAvailable()
	{
		ArrayList<ItemStack> ores = OreDictionary.getOres(this.ore_oredict_key);
		return ores.size() > 0;
	}
	
	public ItemStack getMaterialItem(int count)
	{
		if (this.material_item == null) {
			return null;
		}
		ItemStack ret = this.material_item.copy();
		ret.stackSize = count;
		return ret;
	}

	@Override
	public ItemStack itemDropped(int meta)
	{
		if (this.isOreAvailable() && this.type == FromOredictType.DROP_MATERIAL) {
			return this.material_item;
		}
		return new ItemStack(this, 1, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		if (this.isOreAvailable()) {
			super.getSubBlocks(item, tab, list);
		}
	}
}
