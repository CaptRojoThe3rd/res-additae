package com.captrojo.resadditae.main;

import org.lwjgl.input.Keyboard;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.gui.GuiHandler;
import com.captrojo.resadditae.item.IItemWithSettings;
import com.captrojo.resadditae.magic.UseType;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction.Action;
import com.captrojo.resadditae.packet.toserver.PacketUseSpell;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/* Dedicated event handler for keyboard/mouse input. */
public class InputEventHandler
{
	public static InputEventHandler instance;
	
	public static final String MODCAT = "key.categories.resadditae";
	private static final int LMB = -100;
	private static final int RMB = -99;
	private static final int MMB = -98;
	
	public static KeyBinding open_item_settings = new KeyBinding("key.item_settings", Keyboard.KEY_LMENU, MODCAT);
	
	public static KeyBinding spell_menu = new KeyBinding("key.spell_menu", Keyboard.KEY_APOSTROPHE, MODCAT);
	public static KeyBinding spell_trigger = new KeyBinding("key.spell_trigger", Keyboard.KEY_F, MODCAT);
	public static KeyBinding spell_0 = new KeyBinding("key.spell_0", Keyboard.KEY_I, MODCAT);
	public static KeyBinding spell_1 = new KeyBinding("key.spell_1", Keyboard.KEY_O, MODCAT);
	public static KeyBinding spell_2 = new KeyBinding("key.spell_2", Keyboard.KEY_P, MODCAT);
	public static KeyBinding spell_3 = new KeyBinding("key.spell_3", Keyboard.KEY_K, MODCAT);
	public static KeyBinding spell_4 = new KeyBinding("key.spell_4", Keyboard.KEY_L, MODCAT);
	public static KeyBinding spell_5 = new KeyBinding("key.spell_5", Keyboard.KEY_SEMICOLON, MODCAT);
	
	public static KeyBinding[] spell_keys = {spell_0, spell_1, spell_2, spell_3, spell_4, spell_5};
	public static boolean[] active_spell_keys = {false, false, false, false, false, false};
	
	static int spell_trigger_cooldown = 0;
	static int test = 0;
	
	public static void registerKeybinds()
	{
		ClientRegistry.registerKeyBinding(open_item_settings);
		
		ClientRegistry.registerKeyBinding(spell_menu);
		ClientRegistry.registerKeyBinding(spell_trigger);
		ClientRegistry.registerKeyBinding(spell_0);
		ClientRegistry.registerKeyBinding(spell_1);
		ClientRegistry.registerKeyBinding(spell_2);
		ClientRegistry.registerKeyBinding(spell_3);
		ClientRegistry.registerKeyBinding(spell_4);
		ClientRegistry.registerKeyBinding(spell_5);
	}
	
	public static String getShortStringFor(KeyBinding kb)
	{
		int code = kb.getKeyCode();
		if (code == Keyboard.KEY_SEMICOLON) {
			return ";";
		}
		if (code == Keyboard.KEY_APOSTROPHE) {
			return "'";
		}
		if (code == LMB) {
			return "LMB";
		}
		if (code == MMB) {
			return "MMB";
		}
		if (code == RMB) {
			return "RMB";
		}
		return GameSettings.getKeyDisplayString(code);
	}
	
	void activateSpell(int idx)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		RAPlayerProperties rpp = RAPlayerProperties.get(player);
		if (rpp.spell_slots[idx] == null) {
			return;
		}
		Spell spell = rpp.spell_slots[idx].spell;
		
		if (spell.use_type == UseType.CONTINUOUS) {
			if (rpp.active_continuous_spells[idx]) {
				this.sendPacketUseSpell(rpp, PacketUseSpell.UseType.DEACTIVATE, idx);
				rpp.deactivateSpellClient(idx);
			} else {
				this.sendPacketUseSpell(rpp, PacketUseSpell.UseType.ACTIVATE, idx);
				rpp.activateSpellClient(idx);
			}
		} else {
			if (rpp.active_spell >= 0) {
				if (rpp.active_spell == idx) {
					this.sendPacketUseSpell(rpp, PacketUseSpell.UseType.DEACTIVATE, idx);
					rpp.deactivateSpellClient(idx);
				} else {
					this.sendPacketUseSpell(rpp, PacketUseSpell.UseType.ACTIVATE_OTHER, idx);
					rpp.activateSpellClient(idx);
				}
			} else {
				this.sendPacketUseSpell(rpp, PacketUseSpell.UseType.ACTIVATE, idx);
				rpp.activateSpellClient(idx);
			}
		}
	}
	
	void triggerSpell()
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		RAPlayerProperties rpp = RAPlayerProperties.get(player);
		if (rpp.isSpellActive()) {
			this.sendPacketUseSpell(rpp, PacketUseSpell.UseType.TRIGGER_WHILE_ACTIVE, rpp.active_spell);
			rpp.triggerSpellClient(rpp.active_spell);
		}
	}
	
	void sendPacketUseSpell(RAPlayerProperties rpp, PacketUseSpell.UseType type, int idx)
	{
		ResAdditae.network.sendToServer(new PacketUseSpell(rpp, type, idx));
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.currentScreen != null) {
			return;
		}
		
		EntityPlayer player = mc.thePlayer;
		World world = player.worldObj;
		int x = (int) player.posX;
		int y = (int) player.posY;
		int z = (int) player.posZ;
		
		if (open_item_settings.isPressed()) {
			ItemStack held = player.getHeldItem();
			if (held == null || !(held.getItem() instanceof IItemWithSettings)) {
				return;
			}
			mc.displayGuiScreen(((IItemWithSettings) held.getItem()).getSettingsGui(player));
		}
		
		if (spell_menu.isPressed()) {
			ResAdditae.network.sendToServer(new PacketGuiContainerAction(Action.OPEN, 0, GuiHandler.SPELL_MENU));
		}
		
		if (spell_trigger.isPressed()) {
			this.triggerSpell();
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
