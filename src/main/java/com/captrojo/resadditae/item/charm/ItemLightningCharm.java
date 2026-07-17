package com.captrojo.resadditae.item.charm;

import java.util.List;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemLightningCharm extends ItemCharmBase
{
	private static final double[] AOE_LIGHTNING_XS = {-6, -4.2, 0, 4.2, 6, 4.2, 0, -4.2};
	private static final double[] AOE_LIGHTNING_ZS = {0, -4.2, -6, -4.2, 0, 4.2, 6, 4.2};
	
	public ItemLightningCharm()
	{
		super("charm_lightning", 100, EnumRarity.rare, 140, 280, 20);
	}

	@Override
	public void onUseClient(ItemStack stack, World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}
	
	@Override
	public boolean onUseServer(ItemStack stack, World world, EntityPlayer player, RAPlayerProperties rpp)
	{
		if (player.isSneaking()) {
			for (int i = 0; i < AOE_LIGHTNING_XS.length; i++) {
				double x = player.posX + AOE_LIGHTNING_XS[i];
				double z = player.posZ + AOE_LIGHTNING_ZS[i];
				double y = (double) world.getHeightValue((int) x, (int) z);
				y = (player.posY < y) ? player.posY : y; 
					
				EntityLightningBolt lb = new EntityLightningBolt(world, x, y, z);
				world.addWeatherEffect(lb);
				world.spawnEntityInWorld(lb);
			}
		} else {
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
				entity_count++;
				EntityLightningBolt lb = new EntityLightningBolt(world, entity.posX, entity.posY, entity.posZ);
				world.addWeatherEffect(lb);
				world.spawnEntityInWorld(lb);
			}
			if (entity_count == 0) {
				this.noEntitiesNearby(world, player);
				return false;
			}
		}
		return true;
	}
}
