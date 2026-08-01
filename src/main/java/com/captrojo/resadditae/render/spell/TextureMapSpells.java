package com.captrojo.resadditae.render.spell;

import com.captrojo.resadditae.render.RenderHlpr;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;

@SideOnly(Side.CLIENT)
public class TextureMapSpells extends TextureMap
{
	public TextureMapSpells()
	{
		super(RenderHlpr.SPELL_TEXTUREMAP_ID, "textures/spells");
	}
	
	/* We don't actually need to put anything here since IIcon registration is handled by
	 * ClientEventHandler (since the registerIcons() function is private)
	 */
}
