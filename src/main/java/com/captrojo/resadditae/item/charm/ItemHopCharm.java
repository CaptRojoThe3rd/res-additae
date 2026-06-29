package com.captrojo.resadditae.item.charm;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHopCharm extends ItemCharmBase
{
	public ItemHopCharm()
	{
		super("charm_hop", 50, EnumRarity.common, 30, 30, 100);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!this.onItemRightClickPre(stack, world, player)) {
			return stack;
		}
		
		player.addVelocity(0, 1, 0);
		player.velocityChanged = true;
		
		return this.onItemRightClickPost(stack, world, player);
	}
}
