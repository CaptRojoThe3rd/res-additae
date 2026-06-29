package com.captrojo.resadditae.sounds;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.block.Block.SoundType;

public class ModSoundType extends SoundType
{
	public static final SoundType METAL_PILE = new ModSoundType("metal_pile", 1.0f, 1.0f);
	
	private ModSoundType(String name, float vol, float freq)
	{
		super(name, vol, freq);
	}
	
	@Override
	public String getBreakSound()
	{
		return ResAdditae.ident(super.getBreakSound());
	}
	
	@Override
	public String getStepResourcePath()
	{
		return ResAdditae.ident(super.getStepResourcePath());
	}
}
