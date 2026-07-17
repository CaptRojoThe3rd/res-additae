package com.captrojo.resadditae.item.charm;

import java.util.List;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemExtinguishCharm extends ItemCharmBase
{
	public ItemExtinguishCharm()
	{
		super("charm_extinguish", 100, EnumRarity.uncommon, 40, 80, 20);
	}

	@Override
	public void onUseClient(ItemStack stack, World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}
	
	@Override
	public boolean onUseServer(ItemStack stack, World world, EntityPlayer player, RAPlayerProperties rpp)
	{
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
			player.posX - 10, player.posY - 10, player.posZ - 10, 
			player.posX + 10, player.posY + 10, player.posZ + 10
		);
		List<EntityLivingBase> entities = world.getEntitiesWithinAABBExcludingEntity(player, aabb, new IEntitySelector() {
			public boolean isEntityApplicable(Entity entity)
			{
				return (entity instanceof EntityLivingBase);
			}
		});
		
		int entity_count = 0;
		for (EntityLivingBase entity : entities) {
			if (!rpp.canCharmHelpEntity(entity)) {
				continue;
			}
			
			if (player.isSneaking()) {
				entity.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1200));
			} else {
				entity.extinguish();
			}
			
			entity_count++;
		}
		if (entity_count == 0) {
			this.noEntitiesNearby(world, player);
			return false;
		}
		return true;
	}
}
