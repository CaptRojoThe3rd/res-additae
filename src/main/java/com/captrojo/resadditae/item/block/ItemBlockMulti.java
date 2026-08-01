package com.captrojo.resadditae.item.block;

import java.util.List;

import com.captrojo.resadditae.block.IDoubleSlab;
import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.block.PrismarineRuneMultiBlockData;
import com.captrojo.resadditae.block.generic.BlockFenceGate;
import com.captrojo.resadditae.block.generic.BlockMultiButton;
import com.captrojo.resadditae.block.generic.BlockMultiLog;
import com.captrojo.resadditae.block.generic.BlockMultiPressurePlate;
import com.captrojo.resadditae.main.ItemHlpr;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockMulti extends ItemBlockWithMetadata
{
	protected final IMultiBlock block;
	
	public ItemBlockMulti(Block block)
	{
		super(block, block);
		this.block = (IMultiBlock) block;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return this.getUnlocalizedName(stack, false);
	}
	
	public String getUnlocalizedName(ItemStack stack, boolean for_desc)
	{
		Block block = Block.getBlockFromItem(stack.getItem());
		int meta = stack.getItemDamage();
		String append = "";
		
		if (block instanceof IDoubleSlab && (meta & 0x8) != 0) {
			return Item.getItemFromBlock(((IDoubleSlab) block).getSingleSlab()).getUnlocalizedName(stack);
		}
		
		IMultiBlockData data = this.block.getData(meta);
		
		if (block instanceof BlockFenceGate || block instanceof BlockMultiButton) {
			return this.getUnlocalizedName();
		} else if (block instanceof BlockMultiPressurePlate) {
			meta >>= 1;
		} else if (block instanceof BlockMultiLog) {
			if (meta < 0xc && meta > 0x3) {
				meta &= 0x3;
			}
		} else {
			meta = this.block.fixMeta(meta);
		}
		
		if (data instanceof PrismarineRuneMultiBlockData && !for_desc) {
			return this.getUnlocalizedName();
		}
		
		return this.getUnlocalizedName() + "." + data.getName(meta) + append;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ItemHlpr.addItemDescription(stack, list);
	}
}
