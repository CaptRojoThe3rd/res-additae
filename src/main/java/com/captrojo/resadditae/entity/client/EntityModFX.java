package com.captrojo.resadditae.entity.client;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.render.DumbIcon;
import com.captrojo.resadditae.render.RenderHlpr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class EntityModFX extends EntityFX
{
	public static IIcon[] icons_zap;
	
	public static IIcon icon_target;
	
	static
	{
		DumbIcon._cur_tx_w = 128;
		DumbIcon._cur_tx_h = 128;
		
		icons_zap = new IIcon[] {
			new DumbIcon(0, 0, 8, 8),
			new DumbIcon(8, 0, 8, 8),
			new DumbIcon(16, 0, 8, 8),
			new DumbIcon(24, 0, 8, 8),
			new DumbIcon(32, 0, 8, 8),
			new DumbIcon(40, 0, 8, 8),
			new DumbIcon(48, 0, 8, 8),
			new DumbIcon(56, 0, 8, 8)
		};
		
		icon_target = new DumbIcon(0, 16, 16, 16);
	}
	
	public static void spawn(EntityFX effect)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(effect);
	}
	
	public EntityModFX(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	public EntityModFX(World world, double x, double y, double z, double vel_x, double vel_y, double vel_z)
	{
		super(world, x, y, z, vel_x, vel_y, vel_z);
	}
	
	public abstract void setupModParticle(
		Tessellator ts,
		float pticks,
		float rot_x,
		float rot_xz,
		float rot_z,
		float rot_yz,
		float rot_xy
	);
	
	@Override
	public void renderParticle(Tessellator ts, float pticks, float rot_x, float rot_xz, float rot_z, float rot_yz, float rot_xy)
	{
		ts.draw();
		GL11.glPushMatrix();
		RenderHlpr.bindModParticles(Minecraft.getMinecraft());
		ts.startDrawingQuads();
		
		this.setupModParticle(ts, pticks, rot_x, rot_xz, rot_z, rot_yz, rot_xy);
		super.renderParticle(ts, pticks, rot_x, rot_xz, rot_z, rot_yz, rot_xy);
		
		ts.draw();
		GL11.glPopMatrix();
		RenderHlpr.bindTexture(Minecraft.getMinecraft(), EffectRenderer.particleTextures);
		ts.startDrawingQuads();
		ts.setBrightness(0);
	}
}
