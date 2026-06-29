package com.captrojo.resadditae.entity.properties;

import java.util.Random;

import com.captrojo.resadditae.item.charm.ItemCharmBase;
import com.captrojo.resadditae.main.MiscHlpr;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MobDropDataCharms extends MobDropDataBase
{
	private EnumRarity rarity;
	
	public MobDropDataCharms(EnumRarity rarity, float drop_chance, boolean drop_chance_looting, int min_amount, int max_amount, float amount_skew, boolean amount_looting)
	{
		super(drop_chance, drop_chance_looting, min_amount, max_amount, amount_skew, amount_looting);
		this.rarity = rarity;
	}
	
	public MobDropDataCharms(NBTTagCompound tag)
	{
		super(tag);
		this.rarity = EnumRarity.values()[tag.getByte("rarity")];
	}

	@Override
	public NBTTagCompound saveToNBT(NBTTagCompound tag)
	{
		super.saveToNBT(tag);
		tag.setByte("rarity", (byte) this.rarity.ordinal());
		return tag;
	}

	@Override
	protected ItemStack getItem(Random rand)
	{
		Item item;
		switch (this.rarity) {
		case common:
			return new ItemStack((Item) MiscHlpr.getRandomElement(ItemCharmBase.common_charms, rand));
		case uncommon:
			return new ItemStack((Item) MiscHlpr.getRandomElement(ItemCharmBase.uncommon_charms, rand));
		case rare:
			return new ItemStack((Item) MiscHlpr.getRandomElement(ItemCharmBase.rare_charms, rand));
		case epic:
			return new ItemStack((Item) MiscHlpr.getRandomElement(ItemCharmBase.epic_charms, rand));
		default:
			return new ItemStack((Item) MiscHlpr.getRandomElement(ItemCharmBase.all_charms, rand));
		}
	}
}
