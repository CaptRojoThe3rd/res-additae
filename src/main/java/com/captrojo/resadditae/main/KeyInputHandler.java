package com.captrojo.resadditae.main;

import org.lwjgl.input.Keyboard;

import com.captrojo.resadditae.item.IItemWithSettings;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class KeyInputHandler
{
	public static final String MODCAT = "key.categories.resadditae";
	
	public static KeyBinding open_item_settings = new KeyBinding("key.item_settings", Keyboard.KEY_LMENU, MODCAT);
	
	public static void registerKeybinds()
	{
		ClientRegistry.registerKeyBinding(open_item_settings);
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		
		if (open_item_settings.isPressed() && mc.currentScreen == null) {
			ItemStack held = player.getHeldItem();
			if (held == null || !(held.getItem() instanceof IItemWithSettings)) {
				return;
			}
			mc.displayGuiScreen(((IItemWithSettings) held.getItem()).getSettingsGui(player));
		}
	}
}
