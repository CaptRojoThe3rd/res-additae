package com.captrojo.resadditae.compatibility;

public class ModNameMeta
{
	public static ModNameMeta create(String full)
	{
		if (full == null) {
			return null;
		}
		String[] arr = full.split(":");
		if (arr.length == 2) {
			return new ModNameMeta(arr[0], arr[1], 0);
		} else if (arr.length == 3) {
			return new ModNameMeta(arr[0], arr[1], Integer.valueOf(arr[2]));
		}
		return null;
	}
	
	public String mod;
	public String name;
	public int meta;
	
	private ModNameMeta(String mod, String name, int meta)
	{
		this.mod = mod;
		this.name = name;
		this.meta = meta;
	}
}
