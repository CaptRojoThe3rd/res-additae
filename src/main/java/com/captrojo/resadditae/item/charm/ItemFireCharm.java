package com.captrojo.resadditae.item.charm;

import java.util.List;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;

import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemFireCharm extends ItemCharmBase
{
	public ItemFireCharm()
	{
		super("charm_fire", 100, EnumRarity.rare, 100, 200, 20);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (!this.onItemRightClickPre(stack, world, player)) {
			return stack;
		}
		RAPlayerProperties rpp = RAPlayerProperties.get(player);
		
		if (player.isSneaking()) {
			for (int x0 = -10; x0 <= 10; x0++) {
				for (int y0 = -10; y0 <= 10; y0++) {
					for (int z0 = -10; z0 <= 10; z0++) {
						double d = Math.sqrt(x0 * x0 + y0 * y0 + z0 * z0);
						if (d < 4 || d > 10) {
							continue;
						}
						int x = x0 + (int) player.posX;
						int y = y0 + (int) player.posY;
						int z = z0 + (int) player.posZ;
						Block existing = world.getBlock(x, y, z);
						if (!existing.isAir(world, x, y, z)) {
							continue;
						}
						world.setBlock(x, y, z, Blocks.fire);
					}
				}
			}
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 600));
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
				entity.setFire(5);
			}
			if (entity_count == 0) {
				this.noEntitiesNearby(player);
				return stack;
			}
			
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 600));
		}
		
		return this.onItemRightClickPost(stack, world, player);
	}
}
