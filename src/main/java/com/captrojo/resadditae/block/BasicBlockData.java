package com.captrojo.resadditae.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class BasicBlockData implements IBlockData
{
	public static BasicBlockData whatever = new BasicBlockData(Material.rock, Block.soundTypeStone, 0f, 0f, null, 0);
	public static BasicBlockData cobblestone = new BasicBlockData(Material.rock, Block.soundTypeStone, 2.0f, 6.0f, "pickaxe", 0);
	
	private final Material material;
	private final SoundType sound_type;
	private final float hardness;
	private final float resistance;
	
	private final String harvest_tool;
	private final int harvest_level;
	
	public BasicBlockData(Material material, SoundType sound_type, float hardness, float resistance, String harvest_tool, int harvest_level)
	{
		this.material = material;
		this.sound_type = sound_type;
		this.hardness = hardness;
		this.resistance = resistance;
		
		this.harvest_tool = harvest_tool;
		this.harvest_level = harvest_level;
	}
	
	@Override
	public Material getMaterial()
	{
		return this.material;
	}

	@Override
	public SoundType getSoundType()
	{
		return this.sound_type;
	}
	
	@Override
	public boolean doesBlockShatter()
	{
		return this.getMaterial() == Material.glass;
	}

	@Override
	public float getHardness(int meta)
	{
		return this.hardness;
	}

	@Override
	public float getResistance(int meta)
	{
		return this.resistance;
	}

	@Override
	public void setBlockData(Block block)
	{
		block.setStepSound(this.sound_type);
		block.setHardness(this.hardness);
		block.setResistance(this.resistance);
		if (this.harvest_tool != null) {
			block.setHarvestLevel(this.harvest_tool, this.harvest_level);
		}
	}
}
