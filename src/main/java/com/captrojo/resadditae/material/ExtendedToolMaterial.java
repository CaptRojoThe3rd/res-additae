package com.captrojo.resadditae.material;

public class ExtendedToolMaterial
{
	public static final ExtendedToolMaterial WOOD = new ExtendedToolMaterial(1.1f, 0.6f);
	public static final ExtendedToolMaterial STONE = new ExtendedToolMaterial(1.1f, 1.4f);
	public static final ExtendedToolMaterial IRON = new ExtendedToolMaterial(1.2f, 1.0f);
	public static final ExtendedToolMaterial GOLD = new ExtendedToolMaterial(1.5f, 2.0f);
	public static final ExtendedToolMaterial DIAMOND = new ExtendedToolMaterial(2.1f, 0.8f);
	
	public static final ExtendedToolMaterial NETHERITE = new ExtendedToolMaterial(2.6f, 1.1f);
	
	public static final ExtendedToolMaterial HBM_STEEL = new ExtendedToolMaterial(1.3f, 1.0f);
	public static final ExtendedToolMaterial HBM_TITANIUM = new ExtendedToolMaterial(1.95f, 1.3f);
	public static final ExtendedToolMaterial HBM_COBALT = new ExtendedToolMaterial(2.4f, 1.0f);
	public static final ExtendedToolMaterial HBM_STARMETAL = new ExtendedToolMaterial(3.0f, 1.3f);
	public static final ExtendedToolMaterial HBM_CMB = new ExtendedToolMaterial(3.5f, 1.3f);
	
	public static final ExtendedToolMaterial SILVER = new ExtendedToolMaterial(1.3f, 1.1f);
	public static final ExtendedToolMaterial PLATINUM = new ExtendedToolMaterial(1.8f, 1.3f);
	public static final ExtendedToolMaterial ANCIENT_GEM = new ExtendedToolMaterial(4.0f, 1.4f);
	
	/* For scythes */
	public final float crop_multiplier;
	/* For halberds */
	public final float weight;
	
	public ExtendedToolMaterial(float crop_multiplier, float weight)
	{
		this.crop_multiplier = crop_multiplier;
		this.weight = weight;
	}
}
