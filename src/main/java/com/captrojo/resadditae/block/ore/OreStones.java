package com.captrojo.resadditae.block.ore;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum OreStones
{
	STONE("", 3.0f, 3.0f),
	NETHER("_nether", 2.5f, 3.0f),
	END("_end", 3.75f, 3.0f),
	DEPTHS("_depths", 4.5f, 3.0f),
	DEPTHS_AMBER("_depths_amber", 4.5f, 3.0f),
	DEPTHS_JADE("_depths_jade", 4.5f, 3.0f),
	DEPTHS_RUBY("_depths_ruby", 4.5f, 3.0f),
	DEPTHS_SAPPHIRE("_depths_sapphire", 4.5f, 3.0f),
	DEPTHS_TOPAZ("_depths_topaz", 4.5f, 3.0f);
	
	public static final OreStones[] A_ALL = OreStones.values();
	public static final OreStones[] A_EARTH = {STONE, DEPTHS, DEPTHS_AMBER, DEPTHS_JADE, DEPTHS_RUBY, DEPTHS_SAPPHIRE, DEPTHS_TOPAZ};
	public static final OreStones[] A_VANILLA = {DEPTHS, DEPTHS_AMBER, DEPTHS_JADE, DEPTHS_RUBY, DEPTHS_SAPPHIRE, DEPTHS_TOPAZ};
	
	public final String texture_suffix;
	public final float hardness;
	public final float resistance;
	
	private OreStones(String texture_suffix, float hardness, float resistance)
	{
		this.texture_suffix = texture_suffix;
		this.hardness = hardness;
		this.resistance = resistance;
	}
}
