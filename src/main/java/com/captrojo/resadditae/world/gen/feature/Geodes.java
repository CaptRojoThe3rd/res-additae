package com.captrojo.resadditae.world.gen.feature;

import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.block.ModBlocks;

public enum Geodes
{
	ILMENITE,
	CARNELIAN,
	PERIDOT,
	CHAROITE,
	UNAKITE,
	KUNZITE,
	ZOISITE,
	APATITE,
	AMAZONITE,
	RHODOCHROSITE,
	CORUNDUM,
	DUMORTIERITE,
	HOWLITE,
	PURPURITE,
	LOLITE,
	VARIOLITE,
	STROMATOLITE,
	PORPHYRITE,
	LABRADORITE,
	SKARN,
	NEPHRITE;
	
	public static WorldGenLargeGeodeBase getRandGeode(Random rand, BlockMeta[] blocks)
	{
		switch (rand.nextInt(4)) {
		case 0:
			return new WorldGenLargeGeodeA(blocks[0], blocks[1]);
		case 1:
			return new WorldGenLargeGeodeB(blocks[0], blocks[1]);
		case 2:
			return new WorldGenLargeGeodeC(blocks[0], blocks[1]);
		case 3:
			return new WorldGenLargeGeodeD2(blocks[0], blocks[1]);
		}
		return null;
	}
	
	public static BlockMeta[] getBlocks(Geodes geode)
	{
		int id = geode.ordinal();
		BlockMeta[] blocks = new BlockMeta[2];
		blocks[0] = new BlockMeta(ModBlocks.shiny_rocks[id >> 1], id & 0x1);
		blocks[1] = new BlockMeta(ModBlocks.geode_shells[id >> 4], id & 0xf);
		return blocks;
	}
}
