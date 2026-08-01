package com.captrojo.resadditae.entity.client;

import com.captrojo.resadditae.render.ItemTexturemapHacks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityTargetFX extends EntityFX
{
	private static double getScale(Entity e1, Entity e2)
	{
		double dx = e1.posX - e2.posX;
		double dy = e1.posY - e2.posY;
		double dz = e1.posZ - e2.posZ;
		double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
		double scale = Math.max(d / 2, 3);
		return scale;
	}
	
	public Entity target;
	public Entity cam;
	
	public EntityTargetFX(World world, Entity target, Entity cam)
	{
		super(world, target.posX, target.posY + target.height + (getScale(target, cam) / 6), target.posZ);
		this.target = target;
		this.cam = cam;
		
		this.particleMaxAge = Integer.MAX_VALUE;
		this.particleGravity = 0.0f;
		this.particleIcon = ItemTexturemapHacks.particle_target_arrow;
	}
	
	public void destroy()
	{
		this.particleMaxAge = 0;
	}
	
	@Override
	public void renderParticle(Tessellator ts, float pticks, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
	{
		double time = this.particleAge + pticks;
		double scale = getScale(this.target, this.cam);
		
		double x = this.target.posX;
		double y = this.target.posY + this.target.height + (scale / 8) + 0.125;
		double z = this.target.posZ;
		
		double pitch_deg = 90 - Math.abs(this.cam.rotationPitch);
		double yaw_deg = this.cam.rotationYaw + 90;
		double pitch_rad = pitch_deg * Math.PI / 180;
		double yaw_rad = yaw_deg * Math.PI / 180;

		double offset = MathHelper.sin((float) (time / 2)) / 12;
		offset *= (scale / 4);
		
		double xz_offs = (90 - pitch_deg) / 110;
		
		double xv = Math.cos(pitch_rad) * Math.cos(yaw_rad);
		double yv = Math.sin(pitch_rad);
		double zv = Math.cos(pitch_rad) * Math.sin(yaw_rad);
		
		x += (offset + xz_offs) * xv;
		y += offset * yv;
		z += (offset + xz_offs) * zv;
		
		this.setPosition(x, y, z);
		this.particleScale = (float) scale;
		
		super.renderParticle(ts, pticks, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
	}

	@Override
	public int getFXLayer()
	{
		return 2;
	}

	@Override
	public int getBrightnessForRender(float pticks)
	{
		return 0xf00000;
	}
}
