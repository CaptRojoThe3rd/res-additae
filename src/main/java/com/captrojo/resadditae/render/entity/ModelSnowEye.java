package com.captrojo.resadditae.render.entity;

import java.util.Random;

import com.captrojo.resadditae.render.DumbIcon;
import com.captrojo.resadditae.render.ModelBox2;
import com.captrojo.resadditae.util.Consts;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ModelSnowEye extends ModelBase
{
	Random rand;
	
	ModelRenderer model_eye;
	ModelRenderer[] model_orbiting_things;
	
	IIcon tx_eye_top;
	IIcon tx_eye_bottom;
	IIcon tx_eye_left;
	IIcon tx_eye_right;
	IIcon tx_eye_front;
	IIcon tx_eye_back;
	IIcon tx_orbiting_thing;
	
	public ModelSnowEye()
	{
		this.rand = new Random();
		
		DumbIcon.setCurTxSize(64, 32);
		this.tx_eye_top = new DumbIcon(16, 0, 16, 16);
		this.tx_eye_bottom = new DumbIcon(32, 0, 16, 16);
		this.tx_eye_left = new DumbIcon(0, 16, 16, 16);
		this.tx_eye_right = new DumbIcon(32, 16, 16, 16);
		this.tx_eye_front = new DumbIcon(16, 16, 16, 16);
		this.tx_eye_back = new DumbIcon(48, 16, 16, 16);
		this.tx_orbiting_thing = new DumbIcon(0, 0, 8, 8);
		
		this.model_eye = new ModelRenderer(this, 0, 0);
		this.model_eye.cubeList.add(new ModelBox2(
			this.model_eye,
			-8.0f, 8.0f, -8.0f,
			16, 16, 16,
			0f,
			this.tx_eye_top,
			this.tx_eye_bottom,
			this.tx_eye_left,
			this.tx_eye_right,
			this.tx_eye_front,
			this.tx_eye_back,
			64, 32
		));
		
		this.model_orbiting_things = new ModelRenderer[12];
		for (int i = 0; i < this.model_orbiting_things.length; i++) {
			ModelRenderer mr = new ModelRenderer(this, 0, 0);
			this.model_orbiting_things[i] = mr;
			mr.cubeList.add(new ModelBox2(
				mr,
				-0.625f, 1.25f, 0.625f,
				4, 4, 4,
				0f,
				this.tx_orbiting_thing,
				64, 32
			));
		}
	}
	
	@Override
	public void render(Entity entity, float f1, float f2, float time, float f4, float f5, float f6)
	{
		this.setRotationAngles(f1, f2, time, f4, f5, f6, entity);
		
		this.model_eye.render(f6);
		for (ModelRenderer mr : this.model_orbiting_things) {
			mr.render(f6);
		}
	}

	@Override
	public void setRotationAngles(float f1, float f2, float time, float f4, float f5, float f6, Entity entity)
	{
		super.setRotationAngles(f1, f2, time, f4, f5, f6, entity);
		
		this.rand.setSeed(entity.hashCode());
		time += this.rand.nextInt(100) + this.rand.nextFloat();
		
		this.model_eye.offsetY = MathHelper.sin(time * 0.25f) * 0.033f;
		
		for (int i = 0; i < this.model_orbiting_things.length; i++) {
			ModelRenderer mr = this.model_orbiting_things[i];
			float angle = (((time * 15.0f) % 360.0f) + ((float) i * 30.0f)) * Consts.RECIP_180_PI;
			float angle2 = ((time * 12.0f) + ((float) (i ^ 0x1) * 30.0f)) * Consts.RECIP_180_PI;
			if ((i & 0x2) == 0) {
				mr.offsetX = MathHelper.cos(angle) - 0.125f;
				mr.offsetY = MathHelper.sin(angle2) * 1.0f + 1.0f;
				mr.offsetZ = MathHelper.sin(angle) - 0.125f;
				mr.rotateAngleY = angle;
				mr.rotateAngleZ = angle2;
			} else {
				mr.offsetX = MathHelper.sin(angle) - 0.125f;
				mr.offsetY = MathHelper.sin(angle2) * 1.0f + 1.0f;
				mr.offsetZ = MathHelper.cos(angle) - 0.125f;
				mr.rotateAngleY = angle2;
				mr.rotateAngleZ = angle;
			}
		}
	}
}
