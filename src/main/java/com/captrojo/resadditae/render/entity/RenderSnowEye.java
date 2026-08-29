package com.captrojo.resadditae.render.entity;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.entity.monster.EntitySnowEye;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderSnowEye extends RenderLiving
{
	static final ResourceLocation TEXTURE = ResAdditae.resource("textures/entity/snow_eye.png");
	
	public RenderSnowEye()
	{
		super(new ModelSnowEye(), 1.0f);
	}
	
	@Override
	protected void rotateCorpse(EntityLivingBase entity, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		super.rotateCorpse(entity, p_77043_2_, p_77043_3_, p_77043_4_);
		if (entity.rotationPitch == 0.0f) {
			entity.rotationPitch = 90.0f;
		}
		GL11.glRotatef(entity.rotationPitch - 90.0f, 1.0f, 0.0f, 0.0f);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return RenderSnowEye.TEXTURE;
	}
}
