package com.captrojo.resadditae.util;

import java.io.IOException;

import com.captrojo.resadditae.main.ResAdditae;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class SerialHlpr
{
	public static boolean serializeItemStack(ItemStack stack, ByteBuf buf)
	{
		NBTTagCompound nbt = stack.writeToNBT(new NBTTagCompound());
		try {
			(new PacketBuffer(buf)).writeNBTTagCompoundToBuffer(nbt);
			return true;
		} catch (IOException e) {
			ResAdditae.LOG.error("Failed to serialize ItemStack");
			e.printStackTrace();
			return false;
		}
	}
	
	public static ItemStack deserializeItemStack(ByteBuf buf)
	{
		try {
			NBTTagCompound nbt = (new PacketBuffer(buf)).readNBTTagCompoundFromBuffer();
			return ItemStack.loadItemStackFromNBT(nbt);
		} catch (IOException e) {
			ResAdditae.LOG.error("Failed to deserialize ItemStack");
			e.printStackTrace();
			return null;
		}
	}
}
