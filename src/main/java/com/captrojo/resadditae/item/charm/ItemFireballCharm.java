package com.captrojo.resadditae.item.charm;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFireballCharm extends ItemCharmBase
{
	public ItemFireballCharm()
	{
		super("charm_fireball", 10, EnumRarity.epic, 3250, 6500, 1200);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!this.onItemRightClickPre(stack, world, player)) {
			return stack;
		}
		
		if (player.isSneaking()) {
			world.createExplosion(player, player.posX, player.posY, player.posZ, 50, true);
		}
		int x0 = (int) player.posX;
		int y0 = (int) player.posY;
		int z0 = (int) player.posZ;
		for (int phase = 0; phase < 2; phase++) {
			for (int x1 = -30; x1 < 30; x1++) {
				for (int y1 = -30; y1 < 30; y1++) {
					for (int z1 = -30; z1 < 30; z1++) {
						if (Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1) > 30) {
							continue;
						}
						int x = x0 + x1;
						int y = y0 + y1;
						int z = z0 + z1;
						Block existing = world.getBlock(x, y, z);
						if (phase == 0) {
							if (existing == Blocks.water || existing == Blocks.flowing_water) {
								world.setBlock(x, y, z, Blocks.snow, 0, 4);
							}
						} else {
							
							if (existing == Blocks.air || existing == Blocks.snow_layer || existing == Blocks.snow || existing == Blocks.ice || existing == Blocks.packed_ice) {
								world.setBlock(x, y, z, Blocks.fire);
							} else if (existing == Blocks.obsidian || existing == Blocks.gravel || existing == Blocks.cobblestone) {
								world.setBlock(x, y, z, Blocks.lava);
							} else if (existing == Blocks.grass || existing == Blocks.dirt) {
								world.setBlock(x, y, z, Blocks.dirt);
							}
						}
					}
				}
			}
		}
		
		return this.onItemRightClickPost(stack, world, player);
	}
}
