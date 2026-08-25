package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IBlockData;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockFenceGate extends net.minecraft.block.BlockFenceGate
{
	private IIcon icon;
	
	public BlockFenceGate(String name, String texture_name, IBlockData data)
	{
		this.setBlockName(name);
		this.setBlockTextureName(ResAdditae.ident(texture_name));
		this.setCreativeTab(null);
		
		data.setBlockData(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icon = reg.registerIcon(this.textureName);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.icon;
	}
}
