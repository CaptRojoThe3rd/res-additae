package com.captrojo.resadditae.material;

import com.captrojo.resadditae.item.MultiItemStacks;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class ToolMaterials
{
	public static final ToolMaterial NETHERITE = EnumHelper.addToolMaterial("RESADDITAE_COMPAT_NETHERITE", 4, 2031, 9.0f, 4.0f, 15);
	
	public static final ToolMaterial HBM_STEEL = EnumHelper.addToolMaterial("RESADDITAE_COMPAT_HBM_STEEL", 3, 750, 8.0f, 2.0f, 10);
	public static final ToolMaterial HBM_TITANIUM = EnumHelper.addToolMaterial("RESADDITAE_COMPAT_HBM_TITANIUM", 3, 1000, 9.0f, 2.5f, 15);
	public static final ToolMaterial HBM_COBALT = EnumHelper.addToolMaterial("RESADDITAE_COMPAT_HBM_COBALT", 3, 750, 9.0f, 2.5f, 60);
	public static final ToolMaterial HBM_STARMETAL = EnumHelper.addToolMaterial("RESADDITAE_COMPAT_HBM_STARMETAL", 3, 3000, 20.0f, 2.5f, 100);
	public static final ToolMaterial HBM_CMB = EnumHelper.addToolMaterial("RESADDITAE_COMPAT_HBM_CMB", 3, 8500, 40.0f, 55.0f, 100);
	
	public static final ToolMaterial SILVER = EnumHelper.addToolMaterial("RESADDITAE_SILVER", 2, 375, 6.6f, 2.3f, 17)
		.setRepairItem(MultiItemStacks.SILVER_INGOT.info());
	public static final ToolMaterial PLATINUM = EnumHelper.addToolMaterial("RESADDITAE_PLATINUM", 3, 960, 7.5f, 2.8f, 19)
		.setRepairItem(MultiItemStacks.PLATINUM_INGOT.info());
	public static final ToolMaterial ANCIENT_GEM = EnumHelper.addToolMaterial("RESADDITAE_ANCIENT_GEM", 4, 4321, 13.0f, 5.25f, 27)
		.setRepairItem(MultiItemStacks.ANCIENT_GEM.info());
}
