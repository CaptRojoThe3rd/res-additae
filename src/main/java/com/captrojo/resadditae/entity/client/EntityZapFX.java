package com.captrojo.resadditae.entity.client;

import com.captrojo.resadditae.util.MiscHlpr;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntityZapFX extends EntityModFX
{
	public EntityZapFX(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		
		this.particleMaxAge = 4;
		this.particleGravity = 0.0f;
		this.particleScale = this.rand.nextFloat() * 0.5f + 1.5f;
	}

	@Override
	public void setupModParticle(Tessellator ts, float pticks, float rot_x, float rot_xz, float rot_z, float rot_yz, float rot_xy)
	{
		this.particleIcon = MiscHlpr.getRandomElement(EntityModFX.icons_zap, this.rand);
		ts.setBrightness(0xf00000);
	}
}
