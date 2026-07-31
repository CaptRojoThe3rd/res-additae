package com.captrojo.resadditae.main;

import java.io.IOException;
import java.io.InputStream;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class NBTHlpr
{
	/**
	 * Load NBT compound tag from given ResourceLocation.
	 */
	public static NBTTagCompound loadNBTFromResource(ResourceLocation r)
	{
		String name = "/assets/" + r.getResourceDomain() + "/" + r.getResourcePath();
		InputStream stream = NBTHlpr.class.getResourceAsStream(name);
		if (stream == null) {
			return null;
		}
		
		try {
			NBTTagCompound tag = CompressedStreamTools.readCompressed(stream);
			return tag;
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Does the same thing as ItemStack.writeToNBT, but stores the item as a string
	 * ('modid:itemname') instead of a numeric ID.
	 * 
	 * Use this when not saving something world-specific.
	 */
	public static void saveItemStackToNBT(ItemStack stack, NBTTagCompound tag)
	{
		stack.writeToNBT(tag);
		UniqueIdentifier uidr = GameRegistry.findUniqueIdentifierFor(stack.getItem());
		tag.setString("item", uidr.toString());
	}
	
	public static NBTTagCompound saveItemStackToNBT(ItemStack stack)
	{
		NBTTagCompound tag = new NBTTagCompound();
		saveItemStackToNBT(stack, tag);
		return tag;
	}
	
	/**
	 * Does the same thing as ItemStack.loadItemStackFromNBT, but loads the item from a
	 * string ('modid:itemname') instead of a numeric ID.
	 * 
	 * Use this when not saving something world-specific.
	 */
	public static ItemStack loadItemStackFromNBT(NBTTagCompound tag)
	{
		UniqueIdentifier uidr = new UniqueIdentifier(tag.getString("item"));
		Item item = GameRegistry.findItem(uidr.modId, uidr.name);
		if (item == null) {
			return null;
		}
		/* kind of a hack but whatever */
		tag.setInteger("id", Item.getIdFromItem(item));
		return ItemStack.loadItemStackFromNBT(tag);
	}
	
	/**
	 * Get the NBT tag of an ItemStack, creating a new tag if one doesn't exist.
	 */
	public static NBTTagCompound getItemStackTag(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
		}
		return tag;
	}
}
