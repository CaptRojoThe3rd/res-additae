package com.captrojo.resadditae.render;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.util.IIcon;

/**
 * Just some extra constructors for ModelBox that take IIcon arguments.
 */
public class ModelBox2 extends ModelBox
{
	static TexturedQuad createTexturedQuad(PositionTextureVertex[] ptv, IIcon icon, int map_w, int map_h)
	{
		ptv[0] = ptv[0].setTexturePosition(icon.getMaxU(), icon.getMinV());
		ptv[1] = ptv[1].setTexturePosition(icon.getMinU(), icon.getMinV());
		ptv[2] = ptv[2].setTexturePosition(icon.getMinU(), icon.getMaxV());
		ptv[3] = ptv[3].setTexturePosition(icon.getMaxU(), icon.getMaxV());
		return new TexturedQuad(ptv);
	}
	
	public ModelBox2(
		ModelRenderer mr,
		float x1, float y1, float z1,
		int xs, int ys, int zs,
		float scale,
		IIcon icon,
		int map_w,
		int map_h
	)
	{
		this(mr, x1, y1, z1, xs, ys, zs, scale, icon, icon, icon, icon, icon, icon, map_w, map_h);
	}
	
	public ModelBox2(
		ModelRenderer mr,
		float x1, float y1, float z1,
		int xs, int ys, int zs,
		float scale,
		IIcon side_top,
		IIcon side_bottom,
		IIcon side_left,
		IIcon side_right,
		IIcon side_front,
		IIcon side_back,
		int map_w,
		int map_h
	)
	{
		super(mr, 0, 0, x1, y1, z1, xs, ys, zs, scale);
		float x2 = x1 + (float) xs;
		float y2 = y1 + (float) ys;
		float z2 = z1 + (float) zs;
		x1 -= scale;
		y1 -= scale;
		z1 -= scale;
		x2 += scale;
		y2 += scale;
		z2 += scale;
		
		this.vertexPositions = new PositionTextureVertex[8];
		this.quadList = new TexturedQuad[6];
		
		PositionTextureVertex ptv7 = new PositionTextureVertex(x1, y1, z1, 0.0f, 0.0f);
		PositionTextureVertex ptv0 = new PositionTextureVertex(x2, y1, z1, 0.0f, 8.0f);
		PositionTextureVertex ptv1 = new PositionTextureVertex(x2, y2, z1, 8.0f, 8.0f);
		PositionTextureVertex ptv2 = new PositionTextureVertex(x1, y2, z1, 8.0f, 0.0f);
		PositionTextureVertex ptv3 = new PositionTextureVertex(x1, y1, z2, 0.0f, 0.0f);
		PositionTextureVertex ptv4 = new PositionTextureVertex(x2, y1, z2, 0.0f, 8.0f);
		PositionTextureVertex ptv5 = new PositionTextureVertex(x2, y2, z2, 8.0f, 8.0f);
		PositionTextureVertex ptv6 = new PositionTextureVertex(x1, y2, z2, 8.0f, 0.0f);
		this.vertexPositions[0] = ptv7;
		this.vertexPositions[1] = ptv0;
		this.vertexPositions[2] = ptv1;
		this.vertexPositions[3] = ptv2;
		this.vertexPositions[4] = ptv3;
		this.vertexPositions[5] = ptv4;
		this.vertexPositions[6] = ptv5;
		this.vertexPositions[7] = ptv6;
		this.quadList[0] = ModelBox2.createTexturedQuad(
			new PositionTextureVertex[] {ptv4, ptv0, ptv1, ptv5},
			side_left, map_w, map_h
		);
		this.quadList[1] = ModelBox2.createTexturedQuad(
			new PositionTextureVertex[] {ptv7, ptv3, ptv6, ptv2},
			side_right, map_w, map_h
		);
		this.quadList[2] = ModelBox2.createTexturedQuad(
			new PositionTextureVertex[] {ptv4, ptv3, ptv7, ptv0},
			side_top, map_w, map_h
		);
		this.quadList[3] = ModelBox2.createTexturedQuad(
			new PositionTextureVertex[] {ptv1, ptv2, ptv6, ptv5},
			side_bottom, map_w, map_h
		);
		this.quadList[4] = ModelBox2.createTexturedQuad(
			new PositionTextureVertex[] {ptv0, ptv7, ptv2, ptv1},
			side_front, map_w, map_h
		);
		this.quadList[5] = ModelBox2.createTexturedQuad(
			new PositionTextureVertex[] {ptv3, ptv4, ptv5, ptv6},
			side_back, map_w, map_h
		);
	}
}
