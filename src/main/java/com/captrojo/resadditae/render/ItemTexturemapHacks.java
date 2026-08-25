package com.captrojo.resadditae.render;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

/* This class only exists because EffectRenderer doesn't seem to have an easy way to add
 * a custom particle texture.
 */
public class ItemTexturemapHacks
{
	public static IIcon particle_target_arrow;
	
	public static void registerIcons(IIconRegister reg)
	{
		particle_target_arrow = reg.registerIcon(ResAdditae.ident("../particle/target_arrow"));
	}
}
