package com.captrojo.resadditae.block;

import java.util.ArrayList;

import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.render.block.BlockTexture;
import com.captrojo.resadditae.render.block.BlockTexture.Type;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class PrismarineRuneMultiBlockData extends GenericMultiBlockData
{
	public static final PrismarineRuneMultiBlockData RUNES_0 = new PrismarineRuneMultiBlockData(
		0, "blank", "blank",
		1, "sga_a", "sga/a",
		2, "sga_b", "sga/b",
		3, "sga_c", "sga/c",
		4, "sga_d", "sga/d",
		5, "sga_e", "sga/e",
		6, "sga_f", "sga/f",
		7, "sga_g", "sga/g",
		8, "sga_h", "sga/h",
		9, "sga_i", "sga/i",
		10, "sga_j", "sga/j",
		11, "sga_k", "sga/k",
		12, "sga_l", "sga/l",
		13, "sga_m", "sga/m",
		14, "sga_n", "sga/n",
		15, "sga_o", "sga/o"
	);
	public static final PrismarineRuneMultiBlockData RUNES_1 = new PrismarineRuneMultiBlockData(
		0, "sga_p", "sga/p",
		1, "sga_q", "sga/q",
		2, "sga_r", "sga/r",
		3, "sga_s", "sga/s",
		4, "sga_t", "sga/t",
		5, "sga_u", "sga/u",
		6, "sga_v", "sga/v",
		7, "sga_w", "sga/w",
		8, "sga_x", "sga/x",
		9, "sga_y", "sga/y",
		10, "sga_z", "sga/z",
		11, "num_0", "numbers/0",
		12, "num_1", "numbers/1",
		13, "num_2", "numbers/2",
		14, "num_3", "numbers/3",
		15, "num_4", "numbers/4"
	);
	public static final PrismarineRuneMultiBlockData RUNES_2 = new PrismarineRuneMultiBlockData(
		0, "num_5", "numbers/5",
		1, "num_6", "numbers/6",
		2, "num_7", "numbers/7",
		3, "num_8", "numbers/8",
		4, "num_9", "numbers/9",
		5, "rmn_1", "roman_numerals/1",
		6, "rmn_2", "roman_numerals/2",
		7, "rmn_3", "roman_numerals/3",
		8, "rmn_4", "roman_numerals/4",
		9, "rmn_5", "roman_numerals/5",
		10, "rmn_6", "roman_numerals/6",
		11, "rmn_7", "roman_numerals/7",
		12, "rmn_8", "roman_numerals/8",
		13, "rmn_9", "roman_numerals/9",
		14, "rmn_10", "roman_numerals/10",
		15, "rmn_11", "roman_numerals/11"
	);
	public static final PrismarineRuneMultiBlockData RUNES_3 = new PrismarineRuneMultiBlockData(
		0, "rmn_12", "roman_numerals/12",
		1, "rmn_13", "roman_numerals/13",
		2, "rmn_14", "roman_numerals/14",
		3, "rmn_15", "roman_numerals/15",
		4, "rmn_16", "roman_numerals/16",
		5, "rmn_17", "roman_numerals/17"
	);
	
	private PrismarineRuneMultiBlockData(Object...data)
	{
		super(Material.rock, Block.soundTypeStone);
		
		for (int i = 0; i < data.length; i += 3) {
			int meta = (int) data[i];
			this.metas_list.add(meta);
			this.names_map.put(meta, (String) data[i + 1]);
			this.texture_map.put(meta, new BlockTexture(Type.PILLAR, "prismarine/runes/end", "prismarine/runes/" + (String) data[i + 2]));
			this.hardnesses_map.put(meta, 1.5f);
			this.resistances_map.put(meta, 6.0f);
			this.harvest_tools_map.put(meta, "pickaxe");
			this.harvest_levels_map.put(meta, 0);
		}
		this.finalizeMaps();
	}
}
