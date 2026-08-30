package com.captrojo.resadditae.stats;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.MultiItems;
import com.captrojo.resadditae.item.generic.ItemVessel;
import com.captrojo.resadditae.item.generic.VesselTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

/**
 * Achievements are given with EntityPlayer.triggerAchievement
 */
public class ModAchievements
{
	public static Achievement hearts_increased;
	public static Achievement hearts_maxed;
	
	public static Achievement snow_dungeon_find;
	public static Achievement snow_dungeon_key;
	public static Achievement snow_dungeon_vault;
	public static Achievement snow_dungeon_recharge;
	
	public static void initAchievements()
	{
		/* Health Upgrades */
		
		hearts_increased = (new Achievement("achievements.hearts_increased", "hearts_increased", 0, 0, new ItemStack(ModItems.vessels, 1, VesselTypes.HEART_CONTAINER.meta | ItemVessel.VF_PIECE), null)).initIndependentStat().registerStat();
		hearts_maxed = (new Achievement("achievements.hearts_maxed", "hearts_maxed", 2, 0, new ItemStack(ModItems.vessels, 1, VesselTypes.HEART_CONTAINER.meta), hearts_increased)).registerStat();
		
		/* Snow Dungeon */
		
		snow_dungeon_find = (new Achievement("achievements.snow_dungeon_find", "snow_dungeon_find", -2, 2, new ItemStack(ModBlocks.compacted_snow, 1, 2), null)).initIndependentStat().registerStat();
		snow_dungeon_key = (new Achievement("achievements.snow_dungeon_key", "snow_dungeon_key", -2, 4, new ItemStack(ModItems.keys, 1, 0x11), snow_dungeon_find)).registerStat();
		snow_dungeon_vault = (new Achievement( "achievements.snow_dungeon_vault", "snow_dungeon_vault", -2, 6, new ItemStack(ModBlocks.snow_dungeon_vault, 1, 4), snow_dungeon_key)).registerStat();
		snow_dungeon_recharge = (new Achievement("achievements.snow_dungeon_recharge", "snow_dungeon_recharge", -4, 4, new ItemStack(ModItems.keys, 1, 0x1f), snow_dungeon_key)).registerStat();
		
		AchievementPage.registerAchievementPage(new AchievementPage("Res Additae", new Achievement[] {
			hearts_increased,
			hearts_maxed,
			
			snow_dungeon_find,
			snow_dungeon_key,
			snow_dungeon_vault,
			snow_dungeon_recharge
		}));
	}
}
