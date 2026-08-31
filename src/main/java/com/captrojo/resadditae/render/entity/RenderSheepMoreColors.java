package com.captrojo.resadditae.render.entity;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.entity.passive.EntitySheepMoreColors;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;

public class RenderSheepMoreColors extends RenderSheep
{
	public RenderSheepMoreColors()
	{
		super(new ModelSheep2(), new ModelSheep1(), 0.7f);
	}

	@Override
	protected int shouldRenderPass(EntitySheep sheep, int pass, float pticks)
	{
		if (pass == 0 && !sheep.getSheared()) {
			this.bindTexture(RenderSheep.sheepTextures);

			if (sheep.hasCustomNameTag() && "jeb_".equals(sheep.getCustomNameTag())) {
				boolean flag = true;
				int k = sheep.ticksExisted / 25 + sheep.getEntityId();
				int col1 = k % EntitySheep.fleeceColorTable.length;
				int col2 = (k + 1) % EntitySheep.fleeceColorTable.length;
				float f1 = ((float) (sheep.ticksExisted % 25) + pticks) / 25.0F;
				GL11.glColor3f(
					EntitySheep.fleeceColorTable[col1][0] * (1.0f - f1) + EntitySheep.fleeceColorTable[col2][0] * f1,
					EntitySheep.fleeceColorTable[col1][1] * (1.0f - f1) + EntitySheep.fleeceColorTable[col2][1] * f1,
					EntitySheep.fleeceColorTable[col1][2] * (1.0f - f1) + EntitySheep.fleeceColorTable[col2][2] * f1
				);
			} else {
				int color = ((EntitySheepMoreColors) sheep).getFleeceColorReal();
				GL11.glColor3f(
					EntitySheepMoreColors.FLEECE_COLORS[color][0],
					EntitySheepMoreColors.FLEECE_COLORS[color][1],
					EntitySheepMoreColors.FLEECE_COLORS[color][2]
				);
			}
			
			return 1;
		}

		return -1;
	}
}
