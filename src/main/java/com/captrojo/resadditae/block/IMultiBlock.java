package com.captrojo.resadditae.block;

public interface IMultiBlock
{
	public int fixMeta(int meta);
	public int getTextureIdx(int meta);
	public IMultiBlockData getData(int meta);
}
