package com.captrojo.resadditae.render.block;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockTexture
{
	public static enum Type
	{
		STANDARD(1),
		PILLAR(2),
		LAYERED_PILLAR(3),
		DIRECTIONAL(4),
		LEAF(2),
		LOG(4);
		
		public final int icon_cnt;
		
		private Type(int ic)
		{
			this.icon_cnt = ic;
		}
	}
	
	public final Type type;
	public final String[] paths;
	public final IIcon[] icons;
	
	public BlockTexture(Type type, String...paths)
	{
		this.type = type;
		this.paths = paths;
		this.icons = new IIcon[type.icon_cnt];
	}
	
	public void registerIcons(IIconRegister reg)
	{
		for (int i = 0; i < this.paths.length; i++) {
			this.icons[i] = reg.registerIcon(ResAdditae.ident(this.paths[i]));
		}
	}
	
	public IIcon getIconFast(int side, int meta)
	{
		if (this.type == Type.LEAF) {
			return this.icons[1];
		}
		return this.getIcon(side, meta);
	}
	
	public IIcon getIcon(int side, int meta)
	{
		switch (this.type) {
		case STANDARD:
			return this.icons[0];
		case PILLAR:
			if (side >= 2) {
				return this.icons[1];
			}
			return this.icons[0];
		case LAYERED_PILLAR:
			if (side == 2 || side == 3) {
				return this.icons[1];
			}
			if (side == 4 || side == 5) {
				return this.icons[2];
			}
			return this.icons[0];
		case DIRECTIONAL:
			return this.icons[(meta & 0xc) >> 2];
		case LEAF:
			return this.icons[0];
		case LOG:
			if ((meta & 0x2) != 0) {
				return (side >= 2) ? this.icons[3] : this.icons[2];
			}
			return (side >= 2) ? this.icons[1] : this.icons[0];
		}
		return null; /* unreachable */
	}
}
