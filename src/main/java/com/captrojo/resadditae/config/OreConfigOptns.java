package com.captrojo.resadditae.config;

public class OreConfigOptns extends StructureConfigOptns
{
	public int min_y;
	public int max_y;
	public int min_size;
	public int max_size;
	public int spawn_chances;
	
	public OreConfigOptns(
		String name,
		
		int def_min_y,
		int def_max_y,
		int def_min_size,
		int def_max_size,
		int def_spawn_chances,
		
		boolean def_enabled,
		int def_excl_rad,
		int def_min_dist,
		int def_max_dist
	)
	{
		super(name, def_enabled, def_excl_rad, def_min_dist, def_max_dist);
		
		this.min_y = def_min_y;
		this.max_y = def_max_y;
		this.min_size = def_min_size;
		this.max_size = def_max_size;
		this.spawn_chances = def_spawn_chances;
	}
	
	public OreConfigOptns(
		String name,
		int def_min_y,
		int def_max_y,
		int def_min_size,
		int def_max_size,
		int def_spawn_chances
	)
	{
		this(name, def_min_y, def_max_y, def_min_size, def_max_size, def_spawn_chances, true, 0, 0, 0);
	}
	
	@Override
	public void load()
	{
		super.load();
		
		this.min_y = ModConfig.getInt(this.name + "_min_y", null, this.min_y);
		this.max_y = ModConfig.getInt(this.name + "_max_y", null, this.max_y);
		this.min_size = ModConfig.getInt(this.name + "_min_size", null, this.min_size);
		this.max_size = ModConfig.getInt(this.name + "_max_size", null, this.max_size);
		this.spawn_chances = ModConfig.getInt(this.name + "_spawn_chances", null, this.spawn_chances);
	}
}
