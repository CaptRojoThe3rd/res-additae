package com.captrojo.resadditae.item.charm;

import java.util.ArrayList;
import java.util.List;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
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

public class ItemFreezeCharm extends ItemCharmBase
{
	public ItemFreezeCharm()
	{
		super("charm_freeze", 60, EnumRarity.epic, 2500, 1250, 1200);
	}

	@Override
	public void onUseClient(ItemStack stack, World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}

	@Override
	public boolean onUseServer(ItemStack stack, World world, EntityPlayer player, RAPlayerProperties rpp)
	{
		if (player.isSneaking()) {
			int x0 = (int) player.posX;
			int y0 = (int) player.posY;
			int z0 = (int) player.posZ;
			for (int x1 = -22; x1 < 22; x1++) {
				for (int y1 = -22; y1 < 22; y1++) {
					for (int z1 = -22; z1 < 22; z1++) {
						if (Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1) > 15) {
							continue;
						}
						int x = x0 + x1;
						int y = y0 + y1;
						int z = z0 + z1;
						Block existing = world.getBlock(x, y, z);
						if (existing == Blocks.lava || existing == Blocks.flowing_lava) {
							world.setBlock(x, y, z, Blocks.cobblestone);
						} else if (existing == Blocks.water || existing == Blocks.flowing_water) {
							world.setBlock(x, y, z, Blocks.ice);
						} else if (existing == Blocks.grass) {
							world.setBlock(x, y, z, Blocks.dirt);
						} else if (existing instanceof BlockBush) {
							world.setBlock(x, y, z, Blocks.deadbush);
						} else if (existing == Blocks.fire) {
							world.setBlock(x, y, z, Blocks.air);
						}
					}
				}
			}
		} else {
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(
				player.posX - 15, player.posY - 15, player.posZ - 15, 
				player.posX + 15, player.posY + 15, player.posZ + 15
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
				entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 300, 5));
				entity.addPotionEffect(new PotionEffect(Potion.wither.id, 600, 0));
				entity_count++;
			}
			if (entity_count == 0) {
				this.noEntitiesNearby(world, player);
				return false;
			}
		}
		return true;
	}
}
