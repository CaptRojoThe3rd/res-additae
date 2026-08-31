package com.captrojo.resadditae.main;

import com.captrojo.resadditae.config.common.GeneralConfig;
import com.captrojo.resadditae.extprop.PlayerAttributes;
import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItems;
import com.captrojo.resadditae.stats.ModAchievements;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerEvents
{
	public static PlayerEvents instance;
	
	@SubscribeEvent
	public void onItemPickup(ItemPickupEvent event)
	{
		if (event.player.worldObj.isRemote) {
			return;
		}
		
		ItemStack stack = event.pickedUp.getEntityItem();
		Item item = stack.getItem();
		int meta = stack.getItemDamage();
		
		if (item == ModItems.keys && (meta & MultiItems.KEY_TYPE_MASK) == 0x10) {
			event.player.triggerAchievement(ModAchievements.snow_dungeon_key);
		}
	}
	
	@SubscribeEvent
	public void onPlayerChangingDimension(PlayerChangedDimensionEvent event)
	{
		if (event.player.worldObj.isRemote) {
			return;
		}
		PlayerAttributes.updatePlayerHealthMod(event.player, event.player, false);
	}
	
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if (event.player.worldObj.isRemote) {
			return;
		}
		if (GeneralConfig.enable_motd) {
			event.player.addChatMessage(new ChatComponentText("Loaded world with " + ResAdditae.NAME + " " + ResAdditae.VERSION_NAME));
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		RAPlayerProperties rpp = RAPlayerProperties.get(event.player);
		if (event.side == Side.CLIENT) {
			rpp.tickClient();
			return;
		}
		rpp.tick();
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		if (event.entity.worldObj.isRemote) {
			return;
		}
		PlayerAttributes.updatePlayerHealthMod(event.original, event.entityPlayer, true);
		RAPlayerProperties.transfer(event.original, event.entityPlayer);
	}
}
