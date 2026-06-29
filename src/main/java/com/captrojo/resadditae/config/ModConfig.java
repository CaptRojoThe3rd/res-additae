package com.captrojo.resadditae.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ModConfig
{
	private static Configuration _config;
	protected static String _category;
	
	public static void load(File file)
	{
		_config = new Configuration(file);
	}
	
	public static void save()
	{
		_config.save();
	}
	
	protected static void setCategoryComment(String comment)
	{
		_config.setCategoryComment(_category, comment);
	}
	
	protected static int getInt(String name, String comment, int default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getInt();
	}

	protected static double getDouble(String name, String comment, double default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getDouble();
	}

	protected static boolean getBool(String name, String comment, boolean default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getBoolean();
	}

	protected static String getString(String name, String comment, String default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getString();
	}
	
	protected static String[] getStringList(String name, String comment, String[] default_value)
	{
		Property prop = _config.get(_category, name, default_value);
		prop.comment = comment;
		return prop.getStringList();
	}
}
