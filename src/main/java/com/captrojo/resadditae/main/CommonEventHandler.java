package com.captrojo.resadditae.main;

import java.util.Random;

import com.captrojo.resadditae.block.generic.BlockBasic;
import com.captrojo.resadditae.block.generic.BlockMultiWithDoubleSlab;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.entity.properties.MobDropDataBase;
import com.captrojo.resadditae.entity.properties.PlayerAttributes;
import com.captrojo.resadditae.entity.properties.RAMobProperties;
import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.entity.properties.SpawnSource;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.tileentity.TESnowDungeonSpawner;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

public class CommonEventHandler
{
	public static long tick_time_start;
	public static long tick_time_end;
	public static volatile long mspt_last;
	public static volatile long mspt_worst;
	public static volatile long mspt_avg;
	public static long[] mspt_avg_arr = new long[20];
	public static int mspt_avg_arr_idx;
	
	@SubscribeEvent
	public void onBlockDropping(BlockEvent.HarvestDropsEvent event)
	{
		if (!event.isSilkTouching) {
			return;
		}
		if (!(event.block instanceof BlockMultiWithDoubleSlab || event.block instanceof BlockBasic)) {
			return;
		}
		if ((event.blockMetadata & 0x8) == 0) {
			return;
		}
		event.drops.clear();
		Item item = event.block.getItemDropped(event.blockMetadata, event.world.rand, event.fortuneLevel);
		int meta = event.block.damageDropped(event.blockMetadata);
		event.drops.add(new ItemStack(item, 2, meta));
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityEvent.EntityConstructing event)
	{
		if (event.entity instanceof EntityLiving) {
			RAMobProperties.get((EntityLiving) event.entity);
		} else if (event.entity instanceof EntityPlayer) {
			RAPlayerProperties.get((EntityPlayer) event.entity);
		}
	}
	
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		World world = event.entity.worldObj;
		
		if (!(event.entity instanceof EntityLiving)) {
			return;
		}
		RAMobProperties rmp = RAMobProperties.get((EntityLiving) event.entity);
		
		if (rmp.getSpawnSrc() == SpawnSource.SNOW_DUNGEON) {
			int[] pos = rmp.getSpawnSrcPos();
			TESnowDungeonSpawner te = (TESnowDungeonSpawner) event.entity.worldObj.getTileEntity(pos[0], pos[1], pos[2]);
			if (te != null) {
				te.onEntityKilled();
			}
		}
	}
	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent event)
	{
		if (!(event.entityLiving instanceof EntityLiving)) {
			return;
		}
		EntityLiving entity = (EntityLiving) event.entityLiving;
		Random rand = entity.worldObj.rand;
		
		RAMobProperties rmp = RAMobProperties.get(entity);
		for (MobDropDataBase drop : rmp.getSpecialDrops()) {
			if (!drop.shouldDrop(rand, event.lootingLevel)) {
				continue;
			}
			ItemStack stack = drop.getDrop(rand, event.lootingLevel);
			ItemHlpr.spawnEntityItemAt(stack, entity);
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		if (event.entity.worldObj.isRemote) {
			return;
		}
		PlayerAttributes.updatePlayerHealthMod(event.original, event.entityPlayer, true);
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
		if (CommonConfig.General.enable_motd) {
			event.player.addChatMessage(new ChatComponentText("Loaded world with " + ResAdditae.NAME + " " + ResAdditae.VERSION_NAME));
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (event.side == Side.CLIENT) {
			return;
		}
		
		RAPlayerProperties.get(event.player).tick(event.player);
	}
	
	@SubscribeEvent
	public void onPotionBrewAttempt(PotionBrewEvent.Pre event)
	{
		/* Stacks 0, 1, and 2 are the potions; stack 3 is the ingredient */
		ItemStack ingred = event.getItem(3);
		
		if (ingred.getItem() == ModItems.potion_ingredients) {
			for (int i = 0; i < 3; i++) {
				ItemStack result = event.getItem(i);
				if (result == null) {
					continue;
				}
				if (result.getItemDamage() == 0x10) {
					event.setItem(i, this.getPotionBrewResult(ingred.getItemDamage()));
				} else {
					event.setItem(i, new ItemStack(Items.potionitem, 1, 0x40));
				}
			}
			ingred.stackSize--;
			if (ingred.stackSize <= 0) {
				event.setItem(3, null);
			}
			event.setCanceled(true);
			return;
		}
		
		if (ingred.getItem() == Items.glowstone_dust) {
			boolean f = false;
			for (int i = 0; i < 3; i++) {
				ItemStack result = event.getItem(i);
				if (result == null) {
					continue;
				}
				if (result.getItem() == ModItems.mana_potion) {
					if (result.getItemDamage() <= 5) {
						result.setItemDamage(10);
					}
					f = true;
				}
			}
			if (f) {
				ingred.stackSize--;
				if (ingred.stackSize <= 0) {
					event.setItem(3, null);
				}
				event.setCanceled(true);
				return;
			}
		}
	}
	
	private ItemStack getPotionBrewResult(int meta)
	{
		switch (meta) {
		case 0:
			return new ItemStack(ModItems.mana_potion, 1, 5);
		default:
			return null;
		}
	}
	
	@SubscribeEvent
	public void onServerTickEvent(TickEvent.ServerTickEvent event)
	{
		long time_ms = MinecraftServer.getSystemTimeMillis();
		
		if (event.phase == TickEvent.Phase.START) {
			tick_time_start = time_ms;
		} else if (event.phase == TickEvent.Phase.END) {
			tick_time_end = time_ms;
			
			mspt_last = tick_time_end - tick_time_start;
			if (mspt_last > mspt_worst) {
				mspt_worst = mspt_last;
			}
			
			mspt_avg_arr[mspt_avg_arr_idx] = mspt_last;
			mspt_avg_arr_idx = (mspt_avg_arr_idx + 1) % 20;
			mspt_avg = 0;
			for (long l : mspt_avg_arr) {
				mspt_avg += l;
			}
			mspt_avg /= 20;
		}
	}
}
