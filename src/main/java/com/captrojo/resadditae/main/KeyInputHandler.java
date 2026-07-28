package com.captrojo.resadditae.main;

import org.lwjgl.input.Keyboard;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.item.IItemWithSettings;
import com.captrojo.resadditae.magic.spell.Spell;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/* Dedicated event handler for keyboard/mouse input. */
public class KeyInputHandler
{
	public static final String MODCAT = "key.categories.resadditae";
	
	public static KeyBinding open_item_settings = new KeyBinding("key.item_settings", Keyboard.KEY_LMENU, MODCAT);
	
	public static KeyBinding spell_menu = new KeyBinding("key.spell_menu", Keyboard.KEY_APOSTROPHE, MODCAT);
	public static KeyBinding spell_0 = new KeyBinding("key.spell_0", Keyboard.KEY_I, MODCAT);
	public static KeyBinding spell_1 = new KeyBinding("key.spell_1", Keyboard.KEY_O, MODCAT);
	public static KeyBinding spell_2 = new KeyBinding("key.spell_2", Keyboard.KEY_P, MODCAT);
	public static KeyBinding spell_3 = new KeyBinding("key.spell_3", Keyboard.KEY_K, MODCAT);
	public static KeyBinding spell_4 = new KeyBinding("key.spell_4", Keyboard.KEY_L, MODCAT);
	public static KeyBinding spell_5 = new KeyBinding("key.spell_5", Keyboard.KEY_SEMICOLON, MODCAT);
	public static KeyBinding[] spell_keys = {spell_0, spell_1, spell_2, spell_3, spell_4, spell_5};
	public static boolean[] active_spell_keys = {false, false, false, false, false, false};
	
	public static void registerKeybinds()
	{
		ClientRegistry.registerKeyBinding(open_item_settings);
		
		ClientRegistry.registerKeyBinding(spell_menu);
		ClientRegistry.registerKeyBinding(spell_0);
		ClientRegistry.registerKeyBinding(spell_1);
		ClientRegistry.registerKeyBinding(spell_2);
		ClientRegistry.registerKeyBinding(spell_3);
		ClientRegistry.registerKeyBinding(spell_4);
		ClientRegistry.registerKeyBinding(spell_5);
	}
	
	public void activateSpell(int idx)
	{
		RAPlayerProperties rpp = RAPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
		Spell spell = rpp.spell_slots[idx];
		if (spell == null) {
			return;
		}
		
		
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
		
		for (int i = 0; i < spell_keys.length; i++) {
			if (spell_keys[i].isPressed()) {
				if (active_spell_keys[i]) {
					continue;
				}
				active_spell_keys[i] = true;
				this.activateSpell(i);
			} else {
				active_spell_keys[i] = false;
			}
		}
	}
}
