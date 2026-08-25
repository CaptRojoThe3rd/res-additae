package com.captrojo.resadditae.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public interface IMultiBlockData
{
	public Material getMaterial();
	public SoundType getSoundType();
	
	public String getName(int meta);
	public String[] getNames();
	
	public boolean doesBlockShatter(int meta);
	public float getHardness(int meta);
	public float getResistance(int meta);
	
	public int getFlammability(int meta);
	public int getFireSpreadSpeed(int meta);
	
	public void setBlockData(Block block);
	
	public int[] getValidMetas();
	public boolean isValidMeta(int meta);
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register);
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta);
	@SideOnly(Side.CLIENT)
	public IIcon getIconFast(int side, int meta);
}
