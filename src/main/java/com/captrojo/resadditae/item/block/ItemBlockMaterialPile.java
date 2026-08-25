package com.captrojo.resadditae.item.block;

import com.captrojo.resadditae.block.MultiBlocks;
import com.captrojo.resadditae.block.generic.BlockMaterialPile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockMaterialPile extends ItemBlockMulti
{
	public ItemBlockMaterialPile(Block block)
	{
		super(block);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz)
	{
		if (side != 1) {
			return super.onItemUse(stack, player, world, x, y, z, side, hx, hy, hz);
		}
		
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);

		if (Item.getItemFromBlock(block) == (Item) this && (meta & 0x1) == stack.getItemDamage()) {
			BlockMaterialPile pile = (BlockMaterialPile) block;

			if (pile.placeLayer(world, x, y, z)) {
				world.playSoundAtEntity(player, block.stepSound.getBreakSound(), block.stepSound.volume, block.stepSound.frequency - 0.2f);
				stack.stackSize--;
				return true;
			}
		}

		return super.onItemUse(stack, player, world, x, y, z, side, hx, hy, hz);
	}
}
