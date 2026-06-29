package com.captrojo.resadditae.render.block;

import cpw.mods.fml.client.registry.RenderingRegistry;

public enum RenderIDs
{
	MULTI_STAIR,
	MULTI_FENCE;
	
	public final int id;
	
	private RenderIDs()
	{
		this.id = RenderingRegistry.getNextAvailableRenderId();
	}
}
