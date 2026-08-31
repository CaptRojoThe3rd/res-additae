package com.captrojo.resadditae.config;

public class StructureConfigOptns
{
	public final String name;
	
	public boolean enabled;
	public int excl_rad;
	public int min_dist;
	public int max_dist;
	
	public StructureConfigOptns(String name, boolean def_enabled, int def_excl_rad, int def_min_dist, int def_max_dist)
	{
		this.name = name;
		
		this.enabled = def_enabled;
		this.excl_rad = def_excl_rad;
		this.min_dist = def_min_dist;
		this.max_dist = def_max_dist;
	}
	
	public void load()
	{
		this.enabled = ModConfig.getBool(this.name + "_enabled", null, this.enabled);
		this.excl_rad = ModConfig.getInt(this.name + "_exclusion_radius", null, this.excl_rad);
		this.min_dist = ModConfig.getInt(this.name + "_min_distance", null, this.min_dist);
		this.max_dist = ModConfig.getInt(this.name + "_max_distance", null, this.max_dist);
	}
}
