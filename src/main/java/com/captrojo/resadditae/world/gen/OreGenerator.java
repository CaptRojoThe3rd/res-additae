package com.captrojo.resadditae.world.gen;

import java.util.Map;
import java.util.Random;

import com.captrojo.resadditae.block.BlockMeta;
import com.captrojo.resadditae.config.OreConfigOptns;
import com.captrojo.resadditae.world.gen.feature.WorldGenMinableDynamic;

import net.minecraft.world.World;

public class OreGenerator
{
	Map<BlockMeta, BlockMeta> tgt_ore_map;
	OreConfigOptns options;
	
	SpacedThingCheck pos_check;
	
	public OreGenerator(Map<BlockMeta, BlockMeta> tgt_ore_map, OreConfigOptns options)
	{
		this.tgt_ore_map = tgt_ore_map;
		this.options = options;
		
		this.pos_check = new SpacedThingCheck(this.options);
	}
	
	public void generate(World world, Random rand, int chunk_x, int chunk_z)
	{
		if (!this.options.enabled) {
			return;
		}
		if (!this.pos_check.canPlaceAt(world, chunk_x, chunk_z)) {
			return;
		}
		
		int block_x = chunk_x << 4;
		int block_z = chunk_z << 4;
		
		for (int i = 0; i < this.options.spawn_chances; i++) {
			int x = block_x + rand.nextInt(16);
			int y = this.options.min_y + rand.nextInt(Math.max(1, this.options.max_y - this.options.min_y + 1));
			int z = block_z + rand.nextInt(16);
			
			int vein_size = this.options.min_size + rand.nextInt(Math.max(1, this.options.max_size - this.options.min_size + 1));
			
			(new WorldGenMinableDynamic(vein_size, this.tgt_ore_map)).generate(world, rand, x, y, z);
		}
	}
}
