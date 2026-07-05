package com.captrojo.resadditae.main;

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
}
