package com.captrojo.resadditae.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMaterials extends Material
{
	public static final Material METAL_PILE = new BlockMaterials(MapColor.ironColor);
	public static final Material SOFT_PLANT = new BlockMaterials(MapColor.foliageColor).setNoPushMobility();
	
	private BlockMaterials(MapColor color)
	{
		super(color);
	}
}
