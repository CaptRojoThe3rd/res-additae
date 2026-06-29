package com.captrojo.resadditae.item.charm;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemArrowsplosionCharm extends ItemCharmBase
{
	public ItemArrowsplosionCharm()
	{
		super("charm_arrowsplosion", 20, EnumRarity.epic, 3000, 3000, 60);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!this.onItemRightClickPre(stack, world, player)) {
			return stack;
		}
		
		for (double yaw = -180; yaw < 180; yaw += 10) {
			for (double pitch = -45; pitch < 75; pitch += 10) {
				EntityArrow arrow = new EntityArrow(world, player.posX, player.posY + 3, player.posZ);
				arrow.setDamage(30);
				double xv = Math.cos(yaw + 180) * 1;
				double zv = Math.sin(yaw + 180) * 1;
				double yv = Math.cos(pitch + 90) * -1;
				arrow.setVelocity(xv, yv, zv);
				world.spawnEntityInWorld(arrow);
			}
		}

		return this.onItemRightClickPost(stack, world, player);
	}
}
