package com.captrojo.resadditae.item.charm;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFloodCharm extends ItemCharmBase
{
	public ItemFloodCharm()
	{
		super("charm_flood", 100, EnumRarity.rare, 300, 300, 60);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!this.onItemRightClickPre(stack, world, player)) {
			return stack;
		}
		
		int x0 = (int) player.posX;
		int y0 = (int) player.posY;
		int z0 = (int) player.posZ;
		for (int x1 = -7; x1 < 7; x1++) {
			for (int z1 = -7; z1 < 7; z1++) {
				if (Math.sqrt(x1 * x1 + z1 * z1) > 7) {
					continue;
				}
				int x = x0 + x1;
				int z = z0 + z1;
				if (world.getBlock(x, y0 - 3, z).isAir(world, x, y0 - 3, z)) {
					continue;
				}
				for (int y = (y0 - 2); y < (y0 + 2); y++) {
					if (!world.getBlock(x, y, z).isAir(world, x, y, z)) {
						continue;
					}
					world.setBlock(x, y, z, Blocks.flowing_water, 1, 2);
					break;
				}
			}
		}
		
		return this.onItemRightClickPost(stack, world, player);
	}
}
