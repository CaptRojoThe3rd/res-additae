package com.captrojo.resadditae.item.generic;

import com.captrojo.resadditae.block.IUnlockable;
import com.captrojo.resadditae.item.MultiItems;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemKey extends ItemMulti
{
	public ItemKey()
	{
		super("key", MultiItems.KEYS);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz)
	{
		Block block = world.getBlock(x, y, z);
		if (!(block instanceof IUnlockable)) {
			return false;
		}
		IUnlockable lblock = (IUnlockable) block;
		
		if (world.isRemote) {
			return true;
		}
		
		int meta = stack.getItemDamage();
		if (lblock.isCorrectKey(world, player, x, y, z, meta) && lblock.canBeUnlocked(world, player, x, y, z, meta)) {
			lblock.unlockBlock(world, player, x, y, z, meta);
		}
		if (lblock.consumesKey(world, player, x, y, z, meta)) {
			stack.stackSize--;
		}
		return true;
	}
}
