package com.captrojo.resadditae.compatibility;

import cpw.mods.fml.common.Loader;

public enum ModList
{
	BOTANIA("Botania"),
	CHISEL("chisel"),
	COMPLEX_HUD("complexhud"),
	ET_FUTURUM("etfuturum"),
	GARDEN_STUFF("GardenStuff"),
	HBM_NTM("hbm"),
	HEXCRAFT("hexcraft"),
	NEI("NotEnoughItems"),
	NETHERLICIOUS("netherlicious"),
	THAUMCRAFT("Thaumcraft"),
	TINKERS_CONSTRUCT("TConstruct"),
	TOTEMIC("totemic"),
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
