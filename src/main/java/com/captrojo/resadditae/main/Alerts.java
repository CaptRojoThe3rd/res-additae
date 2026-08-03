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
	
	SPELL_PROF_LVLUP("alert.spell_prof_lvl_up");
	
	/* Send or display alert depending on the side. Don't do anything if side is null */
	public static void alertOnSide(EntityPlayer player, Side side, Alerts alert, int...data)
	{
		if (side == Side.CLIENT) {
			display(alert, data);
		} else if (side == Side.SERVER) {
			ResAdditae.network.sendTo(new PacketDisplayAlert(alert, data), (EntityPlayerMP) player);
		}
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