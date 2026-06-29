package com.captrojo.resadditae.item.charm;

import java.util.List;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWindCharm extends ItemCharmBase
{
	public ItemWindCharm()
	{
		super("charm_wind", 100, EnumRarity.uncommon, 60, 120, 20);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!this.onItemRightClickPre(stack, world, player)) {
			return stack;
		}
		RAPlayerProperties rpp = RAPlayerProperties.get(player);
		
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
			if (!rpp.canCharmHarmEntity(entity, player)) {
				continue;
			}
			
			double xd = player.posX - entity.posX;
			double zd = player.posZ - entity.posZ;
			
			if (!player.isSneaking()) {
				double theta = Math.atan2(zd, xd) * 180 / Math.PI;
				theta += 90;
				if (theta > 360) {
					theta -= 360;
				}
				
				double alpha = player.rotationYawHead;
				if (alpha < 0) {
					alpha += 360;
				}
				
				theta -= alpha;
				if (theta > 30 || theta < -30) {
					continue;
				}
			}
			
			double h = Math.sqrt(xd * xd + zd * zd);
			double xv = (xd / h) * -2;
			double zv = (zd / h) * -2;
			
			entity.addVelocity(xv, 1, zv);
			entity.velocityChanged = true;
			entity_count++;
		}
		if (entity_count == 0) {
			this.noEntitiesNearby(player);
			return stack;
		}
		
		return this.onItemRightClickPost(stack, world, player);
	}
}
