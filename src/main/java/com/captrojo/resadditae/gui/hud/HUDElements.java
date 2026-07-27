package com.captrojo.resadditae.gui.hud;

import com.captrojo.complexhud.api.HUDAPI;
import com.captrojo.resadditae.compatibility.ModList;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.Optional;
import net.minecraft.client.gui.ScaledResolution;

public class HUDElements
{
	public static GuiMagicDisplay magic_display;
	
	public static void init()
	{
		magic_display = new GuiMagicDisplay();
		
		if (ModList.COMPLEX_HUD.isLoaded()) {
			registerCX();
		}
	}
	
	@Optional.Method(modid="complexhud")
	static void registerCX()
	{
		HUDAPI.registerElement(ResAdditae.MOD_ID, magic_display);
	}
	
	public static void render(ScaledResolution sr)
	{
		if (ModList.COMPLEX_HUD.isLoaded()) {
			return;
		}
		
		magic_display.calcStuff();
		if (magic_display.shouldRender()) {
			int x = sr.getScaledWidth() / 2 - 91 - magic_display.width - 20;
			int y = sr.getScaledHeight() - magic_display.height - 3;
			magic_display.render(x, y, x + magic_display.width, y + magic_display.height);
		}
	}
}
