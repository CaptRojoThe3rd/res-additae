package com.captrojo.resadditae.block;

import java.util.ArrayList;

import com.captrojo.resadditae.item.Dyes;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public enum ColoredBlocks implements IMultiBlockData
{
	WOOL_0(0x00, "wool_0", "wool", Material.cloth, 0.8f, 0.8f),
	WOOL_1(0x10, "wool_1", "wool", Material.cloth, 0.8f, 0.8f),
	WOOL_2(0x20, "wool_2", "wool", Material.cloth, 0.8f, 0.8f),
	WOOL_3(0x30, "wool_3", "wool", Material.cloth, 0.8f, 0.8f),
	WOOL_4(0x40, "wool_4", "wool", Material.cloth, 0.8f, 0.8f),
	WOOL_5(0x50, "wool_5", "wool", Material.cloth, 0.8f, 0.8f),
	WOOL_6(0x60, "wool_6", "wool", Material.cloth, 0.8f, 0.8f),
	WOOL_7(0x70, "wool_7", "wool", Material.cloth, 0.8f, 0.8f),
	WOOL_8(0x80, "wool_8", "wool", Material.cloth, 0.8f, 0.8f),
	
	STAINED_GLASS_0(0x00, "stained_glass_0", "stained_glass", Material.glass, 0.3f, 0.3f),
	STAINED_GLASS_1(0x10, "stained_glass_1", "stained_glass", Material.glass, 0.3f, 0.3f),
	STAINED_GLASS_2(0x20, "stained_glass_2", "stained_glass", Material.glass, 0.3f, 0.3f),
	STAINED_GLASS_3(0x30, "stained_glass_3", "stained_glass", Material.glass, 0.3f, 0.3f),
	STAINED_GLASS_4(0x40, "stained_glass_4", "stained_glass", Material.glass, 0.3f, 0.3f),
	STAINED_GLASS_5(0x50, "stained_glass_5", "stained_glass", Material.glass, 0.3f, 0.3f),
	STAINED_GLASS_6(0x60, "stained_glass_6", "stained_glass", Material.glass, 0.3f, 0.3f),
	STAINED_GLASS_7(0x70, "stained_glass_7", "stained_glass", Material.glass, 0.3f, 0.3f),
	STAINED_GLASS_8(0x80, "stained_glass_8", "stained_glass", Material.glass, 0.3f, 0.3f),
	
	STAINED_CLAY_0(0x00, "stained_clay_0", "stained_clay", Material.glass, 1.25f, 4.2f),
	STAINED_CLAY_1(0x10, "stained_clay_1", "stained_clay", Material.glass, 1.25f, 4.2f),
	STAINED_CLAY_2(0x20, "stained_clay_2", "stained_clay", Material.glass, 1.25f, 4.2f),
	STAINED_CLAY_3(0x30, "stained_clay_3", "stained_clay", Material.glass, 1.25f, 4.2f),
	STAINED_CLAY_4(0x40, "stained_clay_4", "stained_clay", Material.glass, 1.25f, 4.2f),
	STAINED_CLAY_5(0x50, "stained_clay_5", "stained_clay", Material.glass, 1.25f, 4.2f),
	STAINED_CLAY_6(0x60, "stained_clay_6", "stained_clay", Material.glass, 1.25f, 4.2f),
	STAINED_CLAY_7(0x70, "stained_clay_7", "stained_clay", Material.glass, 1.25f, 4.2f),
	STAINED_CLAY_8(0x80, "stained_clay_8", "stained_clay", Material.glass, 1.25f, 4.2f),
	
	CONCRETE_POWDER_0(0x00, 16, "concrete_powder_0", "concrete_powder", Material.sand, 0.5f, 0.5f),
	CONCRETE_POWDER_1(0x20, 16, "concrete_powder_1", "concrete_powder", Material.sand, 0.5f, 0.5f),
	CONCRETE_POWDER_2(0x40, 16, "concrete_powder_2", "concrete_powder", Material.sand, 0.5f, 0.5f),
	CONCRETE_POWDER_3(0x60, 16, "concrete_powder_3", "concrete_powder", Material.sand, 0.5f, 0.5f),
	CONCRETE_POWDER_4(0x80, 8, "concrete_powder_4", "concrete_powder", Material.sand, 0.5f, 0.5f),
	
	CONCRETE_0(0x00, "concrete_0", "concrete", Material.rock, 1.5f, 6.0f),
	CONCRETE_1(0x10, "concrete_1", "concrete", Material.rock, 1.5f, 6.0f),
	CONCRETE_2(0x20, "concrete_2", "concrete", Material.rock, 1.5f, 6.0f),
	CONCRETE_3(0x30, "concrete_3", "concrete", Material.rock, 1.5f, 6.0f),
	CONCRETE_4(0x40, "concrete_4", "concrete", Material.rock, 1.5f, 6.0f),
	CONCRETE_5(0x50, "concrete_5", "concrete", Material.rock, 1.5f, 6.0f),
	CONCRETE_6(0x60, "concrete_6", "concrete", Material.rock, 1.5f, 6.0f),
	CONCRETE_7(0x70, "concrete_7", "concrete", Material.rock, 1.5f, 6.0f),
	CONCRETE_8(0x80, "concrete_8", "concrete", Material.rock, 1.5f, 6.0f),
	
	HBM_CONCRETE_0(0x00, "hbm_concrete_0", "hbm_concrete", Material.rock, 15.0f, 140.0f),
	HBM_CONCRETE_1(0x10, "hbm_concrete_1", "hbm_concrete", Material.rock, 15.0f, 140.0f),
	HBM_CONCRETE_2(0x20, "hbm_concrete_2", "hbm_concrete", Material.rock, 15.0f, 140.0f),
	HBM_CONCRETE_3(0x30, "hbm_concrete_3", "hbm_concrete", Material.rock, 15.0f, 140.0f),
	HBM_CONCRETE_4(0x40, "hbm_concrete_4", "hbm_concrete", Material.rock, 15.0f, 140.0f),
	HBM_CONCRETE_5(0x50, "hbm_concrete_5", "hbm_concrete", Material.rock, 15.0f, 140.0f),
	HBM_CONCRETE_6(0x60, "hbm_concrete_6", "hbm_concrete", Material.rock, 15.0f, 140.0f),
	HBM_CONCRETE_7(0x70, "hbm_concrete_7", "hbm_concrete", Material.rock, 15.0f, 140.0f),
	HBM_CONCRETE_8(0x80, "hbm_concrete_8", "hbm_concrete", Material.rock, 15.0f, 140.0f);
	
	private final int c;
	private final String name;
	private final String texture_path;
	private final Material material;
	private final float hardness;
	private final float resistance;
	
	private final String[] names;
	private final String[] texture_names;
	private IIcon[] textures;
	
	private ArrayList<Block> blocks;
	
	private ColoredBlocks(int i0, String name, String texture_path, Material material, float hardness, float resistance)
	{
		this(i0, 8, name, texture_path, material, hardness, resistance);
	}
	
	private ColoredBlocks(int i0, int c, String name, String texture_path, Material material, float hardness, float resistance)
	{
		i0 >>= 1;
		
		this.c = c;
		this.name = name;
		this.texture_path = texture_path;
		this.material = material;
		this.hardness = hardness;
		this.resistance = resistance;
		
		this.names = new String[c];
		this.texture_names = new String[c];
		for (int i = 0; i < c; i++) {
			this.names[i] = Dyes.values()[i + i0].name;
			this.texture_names[i] = texture_path + "/" + ((i0 >> 3) + (i >> 3)) + "/" + Dyes.values()[i + i0].name;
		}
		
		this.blocks = new ArrayList<Block>();
	}

	@Override
	public Material getMaterial()
	{
		return this.material;
	}

	@Override
	public SoundType getSoundType()
	{
		if (this.material == Material.cloth) {
			return Block.soundTypeCloth;
		}
		if (this.material == Material.sand) {
			return Block.soundTypeSand;
		}
		if (this.material == Material.glass) {
			return Block.soundTypeGlass;
		}
		return Block.soundTypeStone;
	}

	@Override
	public String getName(int meta)
	{
		return this.names[meta];
	}

	@Override
	public String[] getNames()
	{
		return this.names;
	}
	
	@Override
	public boolean doesBlockShatter(int meta)
	{
		return this.getMaterial() == Material.glass;
	}

	@Override
	public float getHardness(int meta)
	{
		return this.hardness;
	}

	@Override
	public float getResistance(int meta)
	{
		return this.resistance;
	}
	
	@Override
	public int getFlammability(int meta)
	{
		return this.getMaterial() == Material.cloth ? 60 : 0;
	}
	
	@Override
	public int getFireSpreadSpeed(int meta)
	{
		return this.getMaterial() == Material.cloth ? 30 : 0;
	}

	@Override
	public void setBlockData(Block block)
	{
		this.blocks.add(block);
		
		block.setStepSound(this.getSoundType());
		if (this.material == Material.rock) {
			block.setHarvestLevel("pickaxe", 0);
		} else if (this.material == Material.sand) {
			block.setHarvestLevel("shovel", 0);
		}
	}

	@Override
	public int[] getValidMetas()
	{
		int[] metas = new int[this.c];
		for (int i = 0; i < c; i++) {
			metas[i] = i;
		}
		return metas;
	}

	@Override
	public boolean isValidMeta(int meta)
	{
		return (meta < this.c);
	}

	@Override
	public void registerIcons(IIconRegister reg)
	{
		this.textures = new IIcon[this.c];
		for (int i = 0; i < this.texture_names.length; i++) {
			this.textures[i] = reg.registerIcon(ResAdditae.ident(this.texture_names[i]));
		}
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (meta >= this.textures.length) {
			meta = 0;
		}
		return this.textures[meta];
	}

	@Override
	public IIcon getIconFast(int side, int meta)
	{
		if (meta >= this.textures.length) {
			meta = 0;
		}
		return this.textures[meta];
	}
}
