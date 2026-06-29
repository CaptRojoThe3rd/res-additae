package com.captrojo.resadditae.item;

import net.minecraft.item.ItemStack;

public enum Dyes
{
	JET_BLACK(0x00, "jet_black", "dye/0/jet_black"),
	CHARCOAL(0x01, "charcoal", "dye/0/charcoal"),
	SHADOW_GRAY(0x02, "shadow_gray", "dye/0/shadow_gray"),
	SMOKE_GRAY(0x03, "smoke_gray", "dye/0/smoke_gray"),
	PEARL_GRAY(0x04, "pearl_gray", "dye/0/pearl_gray"),
	WARM_GRAY(0x05, "warm_gray", "dye/0/warm_gray"),
	GRAPHITE(0x06, "graphite", "dye/0/graphite"),
	FLINT(0x07, "flint", "dye/0/flint"),
	MAHOGANY(0x10, "mahogany", "dye/1/mahogany"),
	MAROON(0x11, "maroon", "dye/1/maroon"),
	WINE(0x12, "wine", "dye/1/wine"),
	REDWOOD(0x13, "redwood", "dye/1/redwood"),
	INDIAN_RED(0x14, "indian_red", "dye/1/indian_red"),
	SALMON(0x15, "salmon", "dye/1/salmon"),
	VERMILION(0x16, "vermilion", "dye/1/vermilion"),
	SCARLET(0x17, "scarlet", "dye/1/scarlet"),
	UMBER(0x20, "umber", "dye/2/umber"),
	RUST(0x21, "rust", "dye/2/rust"),
	FULVOUS(0x22, "fulvous", "dye/2/fulvous"),
	CORAL(0x23, "coral", "dye/2/coral"),
	CARROT_ORANGE(0x24, "carrot_orange", "dye/2/carrot_orange"),
	CANTALOUPE(0x25, "cantaloupe", "dye/2/cantaloupe"),
	APRICOT(0x26, "apricot", "dye/2/apricot"),
	PAPAYA_WHIP(0x27, "papaya_whip", "dye/2/papaya_whip"),
	MELLOW_YELLOW(0x30, "mellow_yellow", "dye/3/mellow_yellow"),
	CYBER_YELLOW(0x31, "cyber_yellow", "dye/3/cyber_yellow"),
	SAND(0x32, "sand", "dye/3/sand"),
	TAN(0x33, "tan", "dye/3/tan"),
	SEPIA(0x34, "sepia", "dye/3/sepia"),
	LEMON(0x35, "lemon", "dye/3/lemon"),
	GREEN_YELLOW(0x36, "green_yellow", "dye/3/green_yellow"),
	CHARTREUSE(0x37, "chartreuse", "dye/3/chartreuse"),
	DARK_GREEN(0x40, "dark_green", "dye/4/dark_green"),
	ARMY_GREEN(0x41, "army_green", "dye/4/army_green"),
	SHEEN_GREEN(0x42, "sheen_green", "dye/4/sheen_green"),
	SEA_GREEN(0x43, "sea_green", "dye/4/sea_green"),
	KELLY_GREEN(0x44, "kelly_green", "dye/4/kelly_green"),
	SPRING_GREEN(0x45, "spring_green", "dye/4/spring_green"),
	TEA_GREEN(0x46, "tea_green", "dye/4/tea_green"),
	SAGE_GREEN(0x47, "sage_green", "dye/4/sage_green"),
	PRUSSIAN_BLUE(0x50, "prussian_blue", "dye/5/prussian_blue"),
	AEGEAN(0x51, "aegean", "dye/5/aegean"),
	ZYDECO(0x52, "zydeco", "dye/5/zydeco"),
	TURKISH_BLUE(0x53, "turkish_blue", "dye/5/turkish_blue"),
	TURQUOISE(0x54, "turquoise", "dye/5/turquoise"),
	AQUAMARINE(0x55, "aquamarine", "dye/5/aquamarine"),
	CELESTE(0x56, "celeste", "dye/5/celeste"),
	PEWTER_BLUE(0x57, "pewter_blue", "dye/5/pewter_blue"),
	MIDNIGHT_FROST(0x60, "midnight_frost", "dye/6/midnight_frost"),
	NIGHT_BLUE(0x61, "night_blue", "dye/6/night_blue"),
	NAVY_BLUE(0x62, "navy_blue", "dye/6/navy_blue"),
	CERULEAN(0x63, "cerulean", "dye/6/cerulean"),
	STEEL_BLUE(0x64, "steel_blue", "dye/6/steel_blue"),
	INDEPENDENCE_BLUE(0x65, "independence_blue", "dye/6/independence_blue"),
	PICOTEE_BLUE(0x66, "picotee_blue", "dye/6/picotee_blue"),
	ULTRAMARINE(0x67, "ultramarine", "dye/6/ultramarine"),
	INDIGO(0x70, "indigo", "dye/7/indigo"),
	DEEP_PURPLE(0x71, "deep_purple", "dye/7/deep_purple"),
	RAISIN(0x72, "raisin", "dye/7/raisin"),
	ROYAL_PURPLE(0x73, "royal_purple", "dye/7/royal_purple"),
	MEDIUM_PURPLE(0x74, "medium_purple", "dye/7/medium_purple"),
	IRIS(0x75, "iris", "dye/7/iris"),
	PERIWINKLE(0x76, "periwinkle", "dye/7/periwinkle"),
	THISTLE(0x77, "thistle", "dye/7/thistle"),
	BERRY_MAGENTA(0x80, "berry_magenta", "dye/8/berry_magenta"),
	BYZANTINE(0x81, "byzantine", "dye/8/byzantine"),
	MULBERRY(0x82, "mulberry", "dye/8/mulberry"),
	ROSE(0x83, "rose", "dye/8/rose"),
	DUSTY_PINK(0x84, "dusty_pink", "dye/8/dusty_pink"),
	THULIAN_PINK(0x85, "thulian_pink", "dye/8/thulian_pink"),
	FLAMINGO_PINK(0x86, "flamingo_pink", "dye/8/flamingo_pink"),
	LIGHT_ORCHID(0x87, "light_orchid", "dye/8/light_orchid");
	
	public static final int M_BLACK_INK = 0;
	public static final int M_RED = 1;
	public static final int M_GREEN = 2;
	public static final int M_BROWN_COCOA = 3;
	public static final int M_BLUE_LAPIS = 4;
	public static final int M_PURPLE = 5;
	public static final int M_CYAN = 6;
	public static final int M_LIGHT_GRAY = 7;
	public static final int M_GRAY = 8;
	public static final int M_PINK = 9;
	public static final int M_LIME = 10;
	public static final int M_YELLOW = 11;
	public static final int M_LIGHT_BLUE = 12;
	public static final int M_MAGENTA = 13;
	public static final int M_ORANGE = 14;
	public static final int M_WHITE_BONEMEAL = 15;
	
	public final int item_meta;
	public final String name;
	public final String texture_name;
	
	private Dyes(int meta, String name, String texture_name)
	{
		this.item_meta = meta;
		this.name = name;
		this.texture_name = texture_name;
	}
	
	public ItemStack stack(int count)
	{
		return new ItemStack(ModItems.dye, count, this.item_meta);
	}
}
