package com.captrojo.resadditae.render.spell;

import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;

public class TextureMapSpells extends TextureMap
{
	public TextureMapSpells()
	{
		super(ResAdditae.SPELL_TEXTUREMAP_ID, "textures/spells");
	}
	
	/* We don't actually need to put anything here since IIcon registration is handled by
	 * ClientEventHandler (since the registerIcons() function is private)
	 */
}
