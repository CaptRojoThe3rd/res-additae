package com.captrojo.resadditae.stats;

import com.captrojo.resadditae.util.I18nHlpr;

import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatBasic;

/**
 * Stats are added to with player.triggerAchievement or player.addStat
 */
public class ModStats
{
	public static StatBase spells_learned;
	public static StatBase spells_cast;
	public static StatBase mana_used;
	
	public static void initStats()
	{
		/* Spells */
		
		spells_learned = (new StatBasic("stat.spells_learned", I18nHlpr.chat("stat.spells_learned"))).initIndependentStat().registerStat();
		spells_cast = (new StatBasic("stat.spells_cast", I18nHlpr.chat("stat.spells_cast"))).initIndependentStat().registerStat();
		mana_used = (new StatBasic("stat.mana_used", I18nHlpr.chat("stat.mana_used"))).initIndependentStat().registerStat();
	}
}
