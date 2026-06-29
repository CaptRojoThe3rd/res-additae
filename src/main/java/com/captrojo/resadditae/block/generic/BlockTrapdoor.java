package com.captrojo.resadditae.block.generic;

import com.captrojo.resadditae.block.IBlockData;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockTrapdoor extends BlockTrapDoor
{
	public final IBlockData block_data;
	
	private IIcon texture;
	private IIcon texture_rotated;
	
	public BlockTrapdoor(String name, IBlockData block_data, String texture_name)
	{
		super(block_data.getMaterial());
		this.block_data = block_data;
		
		this.setBlockName(name);
		this.setBlockTextureName(ResAdditae.ident(texture_name));
		this.setCreativeTab(null);
		this.block_data.setBlockData(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.texture = reg.registerIcon(this.getTextureName());
		this.texture_rotated = reg.registerIcon(this.getTextureName() + "_rotated");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return (((meta >> 1) & ((meta >> 2) ^ 1)) & 1) == 1 ? this.texture_rotated : this.texture;
	}
}
