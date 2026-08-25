package com.captrojo.resadditae.config.common;

import com.captrojo.resadditae.config.ModConfig;

public class CommonStuffConfig
{
	public static boolean andesite = true;
	public static boolean andesite_polished = true;
	public static boolean andesite_bricks = true;
	public static boolean andesite_slab = true;
	public static boolean andesite_polished_slab = true;
	public static boolean andesite_brick_slab = true;
	public static boolean andesite_stair = true;
	public static boolean andesite_polished_stair = true;
	public static boolean andesite_brick_stair = true;
	
	public static boolean diorite = true;
	public static boolean diorite_polished = true;
	public static boolean diorite_bricks = true;
	public static boolean diorite_slab = true;
	public static boolean diorite_polished_slab = true;
	public static boolean diorite_brick_slab = true;
	public static boolean diorite_stair = true;
	public static boolean diorite_polished_stair = true;
	public static boolean diorite_brick_stair = true;
	
	public static boolean granite = true;
	public static boolean granite_polished = true;
	public static boolean granite_bricks = true;
	public static boolean granite_slab = true;
	public static boolean granite_polished_slab = true;
	public static boolean granite_brick_slab = true;
	public static boolean granite_stair = true;
	public static boolean granite_polished_stair = true;
	public static boolean granite_brick_stair = true;
	
	public static boolean prismarine = true;
	public static boolean prismarine_bricks = true;
	public static boolean dark_prismarine = true;
	
	public static boolean iron_nuggets = true;
	public static boolean raw_silver = true;
	public static boolean raw_platinum = true;
	
	public static void load()
	{
		ModConfig._category = "common_stuff";
		ModConfig.setCategoryComment("Enable/disable items that are often added by multiple mods.");
		
		andesite = ModConfig.getBool("andesite", null, true);
		andesite_polished = ModConfig.getBool("andesite_polished", null, true);
		andesite_bricks = ModConfig.getBool("andesite_bricks", null, true);
		andesite_slab = ModConfig.getBool("andesite_slab", null, true);
		andesite_polished_slab = ModConfig.getBool("andesite_polished_slab", null, true);
		andesite_brick_slab = ModConfig.getBool("andesite_brick_slab", null, true);
		andesite_stair = ModConfig.getBool("andesite_stair", null, true);
		andesite_polished_stair = ModConfig.getBool("andesite_polished_stair", null, true);
		andesite_brick_stair = ModConfig.getBool("andesite_brick_stair", null, true);
		
		diorite = ModConfig.getBool("diorite", null, true);
		diorite_polished = ModConfig.getBool("diorite_polished", null, true);
		diorite_bricks = ModConfig.getBool("diorite_bricks", null, true);
		diorite_slab = ModConfig.getBool("diorite_slab", null, true);
		diorite_polished_slab = ModConfig.getBool("diorite_polished_slab", null, true);
		diorite_brick_slab = ModConfig.getBool("diorite_brick_slab", null, true);
		diorite_stair = ModConfig.getBool("diorite_stair", null, true);
		diorite_polished_stair = ModConfig.getBool("diorite_polished_stair", null, true);
		diorite_brick_stair = ModConfig.getBool("diorite_brick_stair", null, true);
		
		granite = ModConfig.getBool("granite", null, true);
		granite_polished = ModConfig.getBool("granite_polished", null, true);
		granite_bricks = ModConfig.getBool("granite_bricks", null, true);
		granite_slab = ModConfig.getBool("granite_slab", null, true);
		granite_polished_slab = ModConfig.getBool("granite_polished_slab", null, true);
		granite_brick_slab = ModConfig.getBool("granite_brick_slab", null, true);
		granite_stair = ModConfig.getBool("granite_stair", null, true);
		granite_polished_stair = ModConfig.getBool("granite_polished_stair", null, true);
		granite_brick_stair = ModConfig.getBool("granite_brick_stair", null, true);
		
		prismarine = ModConfig.getBool("prismarine", null, true);
		prismarine_bricks = ModConfig.getBool("prismarine_bricks", null, true);
		dark_prismarine = ModConfig.getBool("dark_prismarine", null, true);
		
		iron_nuggets = ModConfig.getBool("iron_nuggets", null, true);
		raw_silver = ModConfig.getBool("raw_silver", null, true);
		raw_platinum = ModConfig.getBool("raw_platinum", null, true);
	}
}