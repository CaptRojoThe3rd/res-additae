package com.captrojo.resadditae.render.block;

import cpw.mods.fml.client.registry.RenderingRegistry;

public enum BlockRenderIDs
{
	DIRECTIONAL,
	MULTI_STAIR,
	MULTI_FENCE,
	MOSS_LAYER;
	
	public final int id;
	
	private BlockRenderIDs()
	{
		this.id = RenderingRegistry.getNextAvailableRenderId();
	}
}
