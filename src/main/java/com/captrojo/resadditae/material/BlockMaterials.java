package com.captrojo.resadditae.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockMaterials extends Material
{
	public static final Material METAL_PILE = new BlockMaterials(MapColor.ironColor);
	
	private BlockMaterials(MapColor color)
	{
		super(color);
	}
}
