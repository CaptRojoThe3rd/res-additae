package com.captrojo.resadditae.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.captrojo.resadditae.main.ResAdditae;

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
	 * Load NBT compound tag from a file.
	 * Returns null upon failure.
	 */
	public static NBTTagCompound loadFromDisk(String path)
	{
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		try {
			NBTTagCompound tag = CompressedStreamTools.readCompressed(new FileInputStream(file));
			return tag;
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Save NBT compound tag to a file.
	 * Returns whether the file was sucessfully saved.
	 */
	public static boolean saveToDisk(String path, NBTTagCompound nbt)
	{
		File file = new File(path);
		try {
			CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(file));
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Does the same thing as ItemStack.writeToNBT, but stores the item as a string
	 * ('modid:itemname') instead of a numeric ID.
	 * 
	 * Use this when saving something non-world-specific.
	 */
	public static NBTTagCompound saveItemStackToNBT(ItemStack stack, NBTTagCompound nbt)
	{
		stack.writeToNBT(nbt);
		UniqueIdentifier uidr = GameRegistry.findUniqueIdentifierFor(stack.getItem());
		nbt.setString("Item", uidr.toString());
		nbt.removeTag("id");
		return nbt;
	}
	
	public static NBTTagCompound saveItemStackToNBT(ItemStack stack)
	{
		return saveItemStackToNBT(stack, new NBTTagCompound());
	}
	
	/**
	 * Does the same thing as ItemStack.loadItemStackFromNBT, but loads the item from a
	 * string ('modid:itemname') instead of a numeric ID.
	 * 
	 * Use this when saving something non-world-specific.
	 */
	public static ItemStack loadItemStackFromNBT(NBTTagCompound tag)
	{
		UniqueIdentifier uidr = new UniqueIdentifier(tag.getString("Item"));
		Item item = GameRegistry.findItem(uidr.modId, uidr.name);
		if (item == null && uidr.modId.equals(ResAdditae.MOD_ID)) {
			ResAdditae.LOG.error(String.format("Didn't load item %s from NBT", uidr.toString()));
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
	
	/**
	 * Load a float stored as a fixed-point (8.8) short.
	 */
	public static float getFixedPointShort(NBTTagCompound nbt, String key)
	{
		return ((float) nbt.getShort(key)) / 256.0f;
	}
	
	/*
	 * Save a float as a fixed-point (8.8) short.
	 */
	public static void setFixedPointShort(NBTTagCompound nbt, String key, float f)
	{
		nbt.setShort(key, (short) (f * 256.0f));
	}
	
	/**
	 * Save an XYZ position to a new NBTTagCompound
	 */
	public static NBTTagCompound savePosB(byte[] pos)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByte("x", pos[0]);
		nbt.setByte("y", pos[1]);
		nbt.setByte("z", pos[2]);
		return nbt;
	}
	
	/**
	 * Load an XYZ position from an NBTTagCompound
	 */
	public static byte[] loadPosB(NBTTagCompound nbt)
	{
		return new byte[] {
			nbt.getByte("x"),
			nbt.getByte("y"),
			nbt.getByte("z")
		};
	}
}
