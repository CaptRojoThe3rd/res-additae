package com.captrojo.resadditae.block;

import java.util.ArrayList;
import java.util.HashMap;

import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.render.block.BlockTexture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class GenericMultiBlockData implements IMultiBlockData
{
	private IMultiBlock block;
	
	private final Material material;
	private final SoundType sound_type;
	
	protected String[] names;
	protected int[] metas;
	protected BlockTexture[] textures;
	
	protected float[] hardnesses;
	protected float[] resistances;
	protected String[] harvest_tools;
	protected int[] harvest_levels;
	protected int[] fire_spread_speeds;
	protected int[] flammabilities;
	
	protected final ArrayList<Integer> metas_list;
	protected final HashMap<Integer, String> names_map;
	protected final HashMap<Integer, BlockTexture> texture_map;
	
	protected final HashMap<Integer, Float> hardnesses_map;
	protected final HashMap<Integer, Float> resistances_map;
	protected final HashMap<Integer, String> harvest_tools_map;
	protected final HashMap<Integer, Integer> harvest_levels_map;
	protected final HashMap<Integer, Integer> fire_spread_speed_map;
	protected final HashMap<Integer, Integer> flammability_map;
	
	public GenericMultiBlockData(Material material, SoundType sound_type)
	{
		this.material = material;
		this.sound_type = sound_type;
		
		this.metas_list = new ArrayList<Integer>();
		this.names_map = new HashMap<Integer, String>();
		this.texture_map = new HashMap<Integer, BlockTexture>();
		this.hardnesses_map = new HashMap<Integer, Float>();
		this.resistances_map = new HashMap<Integer, Float>();
		this.harvest_tools_map = new HashMap<Integer, String>();
		this.harvest_levels_map = new HashMap<Integer, Integer>();
		this.fire_spread_speed_map = new HashMap<Integer, Integer>();
		this.flammability_map = new HashMap<Integer, Integer>();
	}
	
	public void finalizeMaps()
	{
		int max_meta = this.metas_list.get(this.metas_list.size() - 1) + 1;
		this.metas = new int[max_meta];
		this.names = new String[16];
		this.textures = new BlockTexture[max_meta];
		this.hardnesses = new float[max_meta];
		this.resistances = new float[max_meta];
		this.harvest_tools = new String[max_meta];
		this.harvest_levels = new int[max_meta];
		this.fire_spread_speeds = new int[max_meta];
		this.flammabilities = new int[max_meta];
		
		for (int m : this.names_map.keySet()) {
			this.names[m] = this.names_map.get(m);
		}
		
		for (int i = 0; i < this.metas_list.size(); i++) {
			int m = this.metas_list.get(i);
			this.metas[i] = m;
			this.textures[m] = this.texture_map.get(m);
			if (this.hardnesses_map.size() != 0) {
				this.hardnesses[m] = this.hardnesses_map.get(m);
				this.resistances[m] = this.resistances_map.get(m);
			}
			if (this.harvest_tools_map.size() != 0) {
				this.harvest_tools[m] = this.harvest_tools_map.get(m);
				this.harvest_levels[m] = this.harvest_levels_map.get(m);
			}
			if (this.fire_spread_speed_map.size() != 0) {
				this.fire_spread_speeds[m] = this.fire_spread_speed_map.get(m);
				this.flammabilities[m] = this.flammability_map.get(m);
			}
		}
	}
	
	@Override
	public Material getMaterial()
	{
		return this.material;
	}

	@Override
	public SoundType getSoundType()
	{
		return this.sound_type;
	}
	
	@Override
	public String getName(int meta) {
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
		return this.hardnesses[meta];
	}

	@Override
	public float getResistance(int meta)
	{
		return this.resistances[meta];
	}
	
	@Override
	public int getFlammability(int meta)
	{
		return this.flammabilities[meta];
	}
	
	@Override
	public int getFireSpreadSpeed(int meta)
	{
		return this.fire_spread_speeds[meta];
	}

	@Override
	public void setBlockData(Block block)
	{
		if (this.block == null) {
			this.block = (IMultiBlock) block;
		}
		block.setStepSound(this.sound_type);
		for (int m : this.metas) {
			if (this.harvest_tools[m] == null) {
				continue;
			}
			block.setHarvestLevel(this.harvest_tools[m], this.harvest_levels[m], m);
		}
	}

	@Override
	public int[] getValidMetas()
	{
		return this.metas;
	}
	
	@Override
	public boolean isValidMeta(int meta)
	{
		return this.metas_list.contains(meta);
	}

	@Override
	public void registerIcons(IIconRegister reg)
	{
		for (BlockTexture texture : this.textures) {
			if (texture != null) {
				texture.registerIcons(reg);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		int idx = meta;
		if (this.block != null) {
			idx = this.block.getTextureIdx(meta);
		}
		BlockTexture texture = this.textures[idx];
		if (texture == null) {
			texture = this.textures[this.metas[0]];
		}
		return texture.getIcon(side, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFast(int side, int meta)
	{
		int idx = meta;
		if (this.block != null) {
			idx = this.block.getTextureIdx(meta);
		}
		BlockTexture texture = this.textures[idx];
		if (texture == null) {
			texture = this.textures[this.metas[0]];
		}
		return texture.getIconFast(side, meta);
	}
}
