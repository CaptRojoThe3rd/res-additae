package com.captrojo.resadditae.item.block;

import java.util.List;

import com.captrojo.resadditae.block.IDumbMultiBlock;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockMultiDumb extends ItemBlockWithMetadata
{
	private final String[] names;
	
	public ItemBlockMultiDumb(Block block)
	{
		super(block, block);
		this.names = ((IDumbMultiBlock) block).getNames();
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		if (this.names == null) {
			return this.getUnlocalizedName();
		}
		
		Block block = Block.getBlockFromItem(stack.getItem());
		int meta = stack.getItemDamage();
		
		if (meta >= this.names.length) {
			meta = 0;
		}
		
		return this.getUnlocalizedName() + "." + this.names[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ResAdditae.addItemDescription(stack, list);
	}
}
