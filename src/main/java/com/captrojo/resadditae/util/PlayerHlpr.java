package com.captrojo.resadditae.util;

import java.util.List;

import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PlayerHlpr
{
	public static MovingObjectPosition getBlockLookedAt(World world, EntityPlayer player, double range)
	{
		boolean boat = false;
		
		float pos_mult = 1.0f;
		float pitch_deg = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * pos_mult;
		float yaw_deg = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * pos_mult;

		double x0 = player.prevPosX + (player.posX - player.prevPosX) * (double) pos_mult;
		double y0 = player.prevPosY + (player.posY - player.prevPosY) * (double) pos_mult + (double) (world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight());
		double z0 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) pos_mult;
		Vec3 vec_a = Vec3.createVectorHelper(x0, y0, z0);

		float f3 = MathHelper.cos(-yaw_deg * 0.017453292f - (float) Math.PI);
		float f4 = MathHelper.sin(-yaw_deg * 0.017453292f - (float) Math.PI);
		float f5 = -MathHelper.cos(-pitch_deg * 0.017453292f);
		float vy = MathHelper.sin(-pitch_deg * 0.017453292f);
		float vx = f4 * f5;
		float vz = f3 * f5;

		Vec3 vec_b = vec_a.addVector((double) vx * range, (double) vy * range, (double) vz * range);
		return world.func_147447_a(vec_a, vec_b, boat, !boat, false);
	}

	/* Looks a bit more expensive than getBlockLookedAt, so use that if you only care about blocks. */
	public static MovingObjectPosition getThingLookedAt(World world, EntityPlayer player, double range)
	{
		Entity pointed_entity = null;
		MovingObjectPosition obj_mouse_over = null;
		
		float p_78473_1_ = 1f;
		
		if (ResAdditae.getSideUnsafely(Side.CLIENT) == Side.SERVER) {
			ResAdditae.LOG.info(String.format("pitch: %f, yaw: %f", player.rotationPitch, player.rotationYaw));
		}
		
		double reach = range;
		double d1 = reach;
		
		obj_mouse_over = player.rayTrace(reach, p_78473_1_);
		Vec3 vec_pos = player.getPosition(p_78473_1_);

		if (obj_mouse_over != null) {
			d1 = obj_mouse_over.hitVec.distanceTo(vec_pos);
		}

		Vec3 vec_look = player.getLook(p_78473_1_);
		Vec3 vec_tgt = vec_pos.addVector(vec_look.xCoord * reach, vec_look.yCoord * reach, vec_look.zCoord * reach);
		Vec3 vec_hit = null;
		double expand_amount = 1.0;
		List list = world.getEntitiesWithinAABBExcludingEntity(
			player,
			player.boundingBox.addCoord(
				vec_look.xCoord * reach,
				vec_look.yCoord * reach,
				vec_look.zCoord * reach
			).expand(expand_amount, expand_amount, expand_amount)
		);
		double d2 = d1;

		for (int i = 0; i < list.size(); ++i) {
			Entity entity = (Entity) list.get(i);

			if (!entity.canBeCollidedWith()) {
				continue;
			}

			float f2 = entity.getCollisionBorderSize();
			AxisAlignedBB aabb = entity.boundingBox.expand((double) f2, (double) f2, (double) f2);
			MovingObjectPosition mop = aabb.calculateIntercept(vec_pos, vec_tgt);

			if (aabb.isVecInside(vec_pos)) {
				if (0.0 < d2 || d2 == 0.0) {
					pointed_entity = entity;
					vec_hit = mop == null ? vec_pos : mop.hitVec;
					d2 = 0.0;
				}
			} else if (mop != null) {
				double d3 = vec_pos.distanceTo(mop.hitVec);

				if (d3 < d2 || d2 == 0.0) {
					if (entity == player.ridingEntity && !entity.canRiderInteract()) {
						if (d2 == 0.0) {
							pointed_entity = entity;
							vec_hit = mop.hitVec;
						}
					} else {
						pointed_entity = entity;
						vec_hit = mop.hitVec;
						d2 = d3;
					}
				}
			}
		}

		if (pointed_entity != null && (d2 < d1 || obj_mouse_over == null)) {
			obj_mouse_over = new MovingObjectPosition(pointed_entity, vec_hit);
		}
		
		return obj_mouse_over;
	}
}
