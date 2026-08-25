package com.captrojo.resadditae.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class I18nHlpr
{
	public static String get(String k)
	{
		return getf(k);
	}
	
	public static String getf(String k, Object...f)
	{
		return I18n.format(k, f);
	}
	
	public static IChatComponent chat(String k)
	{
		return chatf(k);
	}
	
	public static IChatComponent chatf(String k, Object...f)
	{
		return new ChatComponentTranslation(k, f);
	}
	
	public static String bool(boolean b)
	{
		return b ? get("misc.true") : get("misc.false");
	}
	
	public static String galactifyText(String original)
	{
		return (original
			.replace('0', 'a')
			.replace('1', 'b')
			.replace('2', 'c')
			.replace('3', 'd')
			.replace('4', 'e')
			.replace('5', 'f')
			.replace('6', 'g')
			.replace('7', 'h')
			.replace('8', 'i')
			.replace('9', 'j')
		);
	}
}
