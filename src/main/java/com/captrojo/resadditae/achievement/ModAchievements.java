package com.captrojo.resadditae.achievement;

import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.generic.ItemVessel;
import com.captrojo.resadditae.item.generic.VesselTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class ModAchievements
{
	public static Achievement hearts_increased;
	public static Achievement hearts_maxed;
	
	public static void initAchievements()
	{
		/* Health Upgrades */
		
		hearts_increased = new Achievement(
			"achievements.hearts_increased", "hearts_increased", 0, 0,
			new ItemStack(ModItems.vessels, 1, VesselTypes.HEART_CONTAINER.meta | ItemVessel.VF_PIECE),
			null
		).initIndependentStat().registerStat();
		
		hearts_maxed = new Achievement(
			"achievements.hearts_maxed", "hearts_maxed", 2, 0,
			new ItemStack(ModItems.vessels, 1, VesselTypes.HEART_CONTAINER.meta),
			hearts_increased
		).initIndependentStat().registerStat();
	}
	
	public static void registerAchievements()
	{
		AchievementPage.registerAchievementPage(new AchievementPage("Res Additae", new Achievement[] {
			hearts_increased,
			hearts_maxed
		}));
	}
}
