package com.captrojo.resadditae.extprop;

import java.util.UUID;

import com.captrojo.resadditae.config.common.PlayerConfig;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldSettings.GameType;

public class PlayerAttributes
{
	public static final UUID HEART_CONTAINER_UUID = UUID.fromString("52b4060a-ddcf-47c4-86c3-16767676768e");
	public static final String HEART_CONTAINER_NAME = "resadditae.heart_container_mod";
	
	public static void updatePlayerHealthMod(EntityPlayer original, EntityPlayer player, boolean set_max_health)
	{
		float health = original.getHealth();
		
		IAttributeInstance att_inst = original.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		AttributeModifier health_mod = att_inst.getModifier(PlayerAttributes.HEART_CONTAINER_UUID);
		if (health_mod == null) {
			health_mod = new AttributeModifier(PlayerAttributes.HEART_CONTAINER_UUID, PlayerAttributes.HEART_CONTAINER_NAME, ((double) PlayerConfig.health_base * 2) - 20d, 0);
		} else {
			att_inst.removeModifier(health_mod);
		}
		att_inst = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		att_inst.applyModifier(health_mod);
		
		if (set_max_health) {
			player.setHealth(20f + (float) health_mod.getAmount());
		} else {
			player.setHealth(health);
		}
	}
	
	public static boolean isInCreativeMode(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP) {
			if (((EntityPlayerMP) player).theItemInWorldManager.getGameType() == GameType.CREATIVE) {
				return true;
			}
		}
		return false;
	}
}
