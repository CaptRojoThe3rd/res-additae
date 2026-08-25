package com.captrojo.resadditae.item;

import java.util.ArrayList;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum MultiItems implements IMultiItemData
{
	INGOTS(
		0, "silver", "silver/silver_ingot",
		1, "platinum", "platinum/platinum_ingot"
	),
	NUGGETS(
		0, "silver", "silver/silver_nugget",
		1, "platinum", "platinum/platinum_nugget"
	),
	RAWS(
		0, "silver", "silver/silver_raw",
		1, "platinum", "platinum/platinum_raw"
	),
	
	GEMS(
		0, "ancient_gem", "ancient_gem/ancient_gem"
	),
	
	DYE(
		0x00, "jet_black", "dye/0/jet_black",
		0x01, "charcoal", "dye/0/charcoal",
		0x02, "shadow_gray", "dye/0/shadow_gray",
		0x03, "smoke_gray", "dye/0/smoke_gray",
		0x04, "pearl_gray", "dye/0/pearl_gray",
		0x05, "warm_gray", "dye/0/warm_gray",
		0x06, "graphite", "dye/0/graphite",
		0x07, "flint", "dye/0/flint",
		
		0x10, "mahogany", "dye/1/mahogany",
		0x11, "maroon", "dye/1/maroon",
		0x12, "wine", "dye/1/wine",
		0x13, "redwood", "dye/1/redwood",
		0x14, "indian_red", "dye/1/indian_red",
		0x15, "salmon", "dye/1/salmon",
		0x16, "vermilion", "dye/1/vermilion",
		0x17, "scarlet", "dye/1/scarlet",
		
		0x20, "umber", "dye/2/umber",
		0x21, "rust", "dye/2/rust",
		0x22, "fulvous", "dye/2/fulvous",
		0x23, "coral", "dye/2/coral",
		0x24, "carrot_orange", "dye/2/carrot_orange",
		0x25, "cantaloupe", "dye/2/cantaloupe",
		0x26, "apricot", "dye/2/apricot",
		0x27, "papaya_whip", "dye/2/papaya_whip",
		
		0x30, "mellow_yellow", "dye/3/mellow_yellow",
		0x31, "cyber_yellow", "dye/3/cyber_yellow",
		0x32, "sand", "dye/3/sand",
		0x33, "tan", "dye/3/tan",
		0x34, "sepia", "dye/3/sepia",
		0x35, "lemon", "dye/3/lemon",
		0x36, "green_yellow", "dye/3/green_yellow",
		0x37, "chartreuse", "dye/3/chartreuse",
		
		0x40, "dark_green", "dye/4/dark_green",
		0x41, "army_green", "dye/4/army_green",
		0x42, "sheen_green", "dye/4/sheen_green",
		0x43, "sea_green", "dye/4/sea_green",
		0x44, "kelly_green", "dye/4/kelly_green",
		0x45, "spring_green", "dye/4/spring_green",
		0x46, "tea_green", "dye/4/tea_green",
		0x47, "sage_green", "dye/4/sage_green",
		
		0x50, "prussian_blue", "dye/5/prussian_blue",
		0x51, "aegean", "dye/5/aegean",
		0x52, "zydeco", "dye/5/zydeco",
		0x53, "turkish_blue", "dye/5/turkish_blue",
		0x54, "turquoise", "dye/5/turquoise",
		0x55, "aquamarine", "dye/5/aquamarine",
		0x56, "celeste", "dye/5/celeste",
		0x57, "pewter_blue", "dye/5/pewter_blue",

		0x60, "midnight_frost", "dye/6/midnight_frost",
		0x61, "night_blue", "dye/6/night_blue",
		0x62, "navy_blue", "dye/6/navy_blue",
		0x63, "cerulean", "dye/6/cerulean",
		0x64, "steel_blue", "dye/6/steel_blue",
		0x65, "independence_blue", "dye/6/independence_blue",
		0x66, "picotee_blue", "dye/6/picotee_blue",
		0x67, "ultramarine", "dye/6/ultramarine",

		0x70, "indigo", "dye/7/indigo",
		0x71, "deep_purple", "dye/7/deep_purple",
		0x72, "raisin", "dye/7/raisin",
		0x73, "royal_purple", "dye/7/royal_purple",
		0x74, "medium_purple", "dye/7/medium_purple",
		0x75, "iris", "dye/7/iris",
		0x76, "periwinkle", "dye/7/periwinkle",
		0x77, "thistle", "dye/7/thistle",

		0x80, "berry_magenta", "dye/8/berry_magenta",
		0x81, "byzantine", "dye/8/byzantine",
		0x82, "mulberry", "dye/8/mulberry",
		0x83, "rose", "dye/8/rose",
		0x84, "dusty_pink", "dye/8/dusty_pink",
		0x85, "thulian_pink", "dye/8/thulian_pink",
		0x86, "flamingo_pink", "dye/8/flamingo_pink",
		0x87, "light_orchid", "dye/8/light_orchid"
	),
	
	SHINY_ROCKS(
		0, "ilmenite", "shiny_rocks/ilmenite",
		1, "carnelian", "shiny_rocks/carnelian",
		2, "peridot", "shiny_rocks/peridot",
		3, "charoite", "shiny_rocks/charoite",
		4, "unakite", "shiny_rocks/unakite",
		5, "kunzite", "shiny_rocks/kunzite",
		6, "zoisite", "shiny_rocks/zoisite",
		7, "apatite", "shiny_rocks/apatite",
		
		8, "amazonite", "shiny_rocks/amazonite",
		9, "rhodochrosite", "shiny_rocks/rhodochrosite",
		10, "corundum", "shiny_rocks/corundum",
		11, "dumortierite", "shiny_rocks/dumortierite",
		12, "howlite", "shiny_rocks/howlite",
		13, "purpurite", "shiny_rocks/purpurite",
		14, "lolite", "shiny_rocks/lolite",
		15, "variolite", "shiny_rocks/variolite",
		
		16, "stromatolite", "shiny_rocks/stromatolite",
		17, "porphyrite", "shiny_rocks/porphyrite",
		18, "labradorite", "shiny_rocks/labradorite",
		19, "skarn", "shiny_rocks/skarn",
		20, "nephrite", "shiny_rocks/nephrite"
	),
	
	KEYS(
		0x00, "everything", "keys/everything",
		
		0x10, "snow_dungeon_vault_1", "keys/snow_dungeon_vault_1",
		0x11, "snow_dungeon_vault_2", "keys/snow_dungeon_vault_2",
		0x12, "snow_dungeon_vault_3", "keys/snow_dungeon_vault_3",
		0x13, "snow_dungeon_vault_4", "keys/snow_dungeon_vault_4",
		0x14, "snow_dungeon_vault_5", "keys/snow_dungeon_vault_5",
		0x15, "snow_dungeon_vault_6", "keys/snow_dungeon_vault_6",
		0x16, "snow_dungeon_vault_7", "keys/snow_dungeon_vault_7",
		0x17, "snow_dungeon_vault_8", "keys/snow_dungeon_vault_8",
		0x1f, "snow_dungeon_spawner_activator", "keys/snow_dungeon_spawner_activator"
	);
	
	public static final int KEY_TYPE_MASK = 0xf0;
	public static final int KEYTYPE_SNOW_DUNGEON_VAULT = 0x10;

	public final int[] metas;
	public final String[] names;
	private final String[] texture_names;
	private IIcon[] textures;
	
	private MultiItems(Object...data)
	{
		this.metas = new int[data.length / 3];
		this.names = new String[(Integer) data[data.length - 3] + 1];
		this.texture_names = new String[(Integer) data[data.length - 3] + 1];
		
		for (int i = 0, m = 0; i < data.length; i += 3, m++) {
			int meta = (int) data[i];
			this.metas[m] = meta;
			this.names[meta] = (String) data[i + 1];
			this.texture_names[meta] = ResAdditae.ident((String) data[i + 2]);
		}
	}
	
	public String[] getNames()
	{
		return this.names;
	}
	
	public int[] getValidMetas()
	{
		return this.metas;
	}
	
	public IIcon getIcon(int meta)
	{
		if (meta >= this.textures.length) {
			return null;
		}
		return this.textures[meta];
	}
	
	public void registerIcons(IIconRegister reg)
	{
		this.textures = new IIcon[this.texture_names.length];
		
		for (int i = 0; i < this.texture_names.length; i++) {
			if (this.texture_names[i] == null) {
				continue;
			}
			this.textures[i] = reg.registerIcon(this.texture_names[i]);
		}
	}
}
