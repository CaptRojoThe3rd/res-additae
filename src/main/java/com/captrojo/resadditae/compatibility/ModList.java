package com.captrojo.resadditae.compatibility;

import cpw.mods.fml.common.Loader;

public enum ModList
{
	ET_FUTURUM("etfuturum"),
	HBM_NTM("hbm"),
	NEI("NotEnoughItems"),
	VILLAGE_NAMES("VillageNames");
	
	public static boolean isVanillaConcreteProvided()
	{
		return ET_FUTURUM.isLoaded() && !HBM_NTM.isLoaded();
	}
	
	public final String id;
	
	private ModList(String id)
	{
		this.id = id;
	}
	
	public boolean isLoaded()
	{
		return Loader.isModLoaded(this.id);
	}
	
	public String getVersionString()
	{
		return Loader.instance().getIndexedModList().get(this.id).getVersion();
	}
}
