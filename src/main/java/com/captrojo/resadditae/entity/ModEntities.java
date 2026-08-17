package com.captrojo.resadditae.entity;

import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities
{
	public static void register()
	{
		EntityRegistry.registerModEntity(EntityThrownHalberd.class, "ThrownHalberd", 1, ResAdditae.instance, 128, 1, true);
	}
}
