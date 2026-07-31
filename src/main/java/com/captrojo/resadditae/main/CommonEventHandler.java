package com.captrojo.resadditae.main;

import java.util.Random;

import com.captrojo.resadditae.block.IDoubleSlab;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.config.common.GeneralConfig;
import com.captrojo.resadditae.entity.properties.MobDropDataBase;
import com.captrojo.resadditae.entity.properties.PlayerAttributes;
import com.captrojo.resadditae.entity.properties.RAMobProperties;
import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.entity.properties.SpawnSource;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItems;
import com.captrojo.resadditae.tileentity.TESnowDungeonSpawner;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent;

public class CommonEventHandler
{
	@SubscribeEvent
	public void onBlockDropping(BlockEvent.HarvestDropsEvent event)
	{
		if (event.isSilkTouching) {
			if (event.block instanceof IDoubleSlab) {
				if ((event.blockMetadata & 0x8) == 0) {
					return;
				}
				event.drops.clear();
				Item item = event.block.getItemDropped(event.blockMetadata, event.world.rand, event.fortuneLevel);
				int meta = event.block.damageDropped(event.blockMetadata);
				event.drops.add(new ItemStack(item, 2, meta));
			}
			return;
		}
		
		if (event.block == Blocks.gravel) {
			ItemStack first = event.drops.get(0);
			/* Override the gravel drop code, unless another mod replaced the drop already */
			if (first.getItem() != Item.getItemFromBlock(Blocks.gravel) && first.getItem() != Items.flint) {
				return;
			}
			event.drops.clear();
			
			int gem_num = 9 - (event.fortuneLevel * 2);
			int flint_num = gem_num - 1 - event.fortuneLevel;
			int r = event.world.rand.nextInt(10);
			
			if (r >= gem_num) {
				int m = MiscHlpr.getRandomElement(MultiItems.SHINY_ROCKS.getValidMetas(), event.world.rand);
				event.drops.add(new ItemStack(ModItems.shiny_rocks, 1, m));
			} else if (r >= flint_num) {
				event.drops.add(new ItemStack(Items.flint));
			} else {
				event.drops.add(new ItemStack(Blocks.gravel));
			}
			return;
		}
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
		RAPlayerProperties.transfer(event.original, event.entityPlayer);
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
		if (event.side == Side.CLIENT) {
			return;
		}
		
		RAPlayerProperties.get(event.player).tick(event.player);
	}
	
	@SubscribeEvent
	public void onServerTickEvent(TickEvent.ServerTickEvent event)
	{
		long time_ms = MinecraftServer.getSystemTimeMillis();
		if (event.phase == TickEvent.Phase.START) {
			PerformanceInfo.onTickStart(time_ms);
		} else if (event.phase == TickEvent.Phase.END) {
			PerformanceInfo.onTickEnd(time_ms);
		}
	}
	
	@SubscribeEvent
	public void useHoeEvent(UseHoeEvent event)
	{
		Block block = event.world.getBlock(event.x, event.y, event.z);
		Block block1 = ModBlocks.depth_farmland;
		if (block == ModBlocks.depth_soil) {
			if (!event.world.isRemote) {
				event.world.setBlock(event.x, event.y, event.z, ModBlocks.depth_farmland);
			}
			double dx = ((double) event.x) + 0.5;
			double dy = ((double) event.y) + 0.5;
			double dz = ((double) event.z) + 0.5; 
			event.world.playSoundEffect(dx, dy, dz, block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0f) / 2.0f, block1.stepSound.getPitch() * 0.8f);
			event.setResult(Result.ALLOW);
		}
	}
}
