package com.captrojo.resadditae.main;

import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.packet.toclient.PacketDisplayAlert;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public enum Alerts
{
	NOT_ENOUGH_MANA("alert.not_enough_mana"),
	ON_COOLDOWN("alert.on_cooldown"),
	NO_TARGET("alert.no_target"),
	HEALTH_ALREADY_FULL("alert.health_already_full"),
	
	LIFESTEAL_USED("alert.lifesteal_used"),
	
	SPELL_LEARNED("alert.spell_learned"),
	SPELL_PROF_LVLUP("alert.spell_prof_lvl_up");
	
	public static void send(EntityPlayer player, Alerts alert, Object...data)
	{
		ResAdditae.network.sendTo(new PacketDisplayAlert(alert, data), (EntityPlayerMP) player);
	}
	
	@SideOnly(Side.CLIENT)
	public static void display(Alerts alert, Object...data)
	{
		String str = "";
		switch (alert) {
		case NOT_ENOUGH_MANA:
		case ON_COOLDOWN:
		case NO_TARGET:
		case HEALTH_ALREADY_FULL:
			str = I18nHlpr.get(alert.key);
			break;
			
		case LIFESTEAL_USED:
			str = I18nHlpr.getf(alert.key, (float) data[1] / 2.0f, (String) data[2]);
			break;
			
		case SPELL_LEARNED:
		case SPELL_PROF_LVLUP:
			str = I18nHlpr.getf(alert.key, Spells.getByID((int) data[0]).getLocalizedName());
			break;
		}
		
		Minecraft.getMinecraft().thePlayer.addChatMessage(I18nHlpr.chat(str));
	}
	
	String key;
	
	private Alerts(String key)
	{
		this.key = key;
	}
}