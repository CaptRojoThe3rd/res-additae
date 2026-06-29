package com.captrojo.resadditae.block;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;

public class CopiedGenericMultiBlockData extends GenericMultiBlockData
{	
	protected final int meta_limit;
	protected final GenericMultiBlockData[] datas;
	
	public CopiedGenericMultiBlockData(GenericMultiBlockData data)
	{
		super(data.getMaterial(), data.getSoundType());
		this.meta_limit = 16;
		this.datas = new GenericMultiBlockData[] {data};
	}
	
	public CopiedGenericMultiBlockData(Material material, SoundType sound_type, int meta_limit, GenericMultiBlockData...datas)
	{
		super(material, sound_type);
		this.meta_limit = meta_limit;
		this.datas = datas;
	}
	
	@Override
	public float getHardness(int meta)
	{
		int i;
		for (i = 0; meta >= this.meta_limit; meta -= 8, i++);
		return this.datas[i].getHardness(meta);
	}
	
	@Override
	public float getResistance(int meta)
	{
		int i;
		for (i = 0; meta >= this.meta_limit; meta -=8, i++);
		return this.datas[i].getResistance(meta);
	}
	
	@Override
	public void setBlockData(Block block)
	{
		block.setStepSound(this.getSoundType());
		for (int m : this.metas) {
			int i;
			int m2 = m;
			for (i = 0; m2 >= this.meta_limit; m2 -=8, i++);
			String tool = this.datas[i].harvest_tools[m2];
			if (tool == null) {
				continue;
			}
			int level = this.datas[i].harvest_levels[m2];
			block.setHarvestLevel(tool, level, m);
		}
	}
}
