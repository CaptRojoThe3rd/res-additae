package com.captrojo.resadditae.world.structure;

import java.util.Random;

import com.captrojo.resadditae.config.common.DebugConfig;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.world.gen.StaticGenSpacedThing;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class StructureVerySimple extends StaticGenSpacedThing
{
	protected final StructurePiece[] pieces;
	
	protected final Block foundation_block;
	protected final short foundation_meta;
	
	final String name;
	
	public StructureVerySimple(String name, NBTTagCompound tag, int rng_seed, BiomeGenBase[] valid_biomes, int spawn_exclusion_radius, int min_distance, int max_distance)
	{
		super(rng_seed, valid_biomes, spawn_exclusion_radius, min_distance, max_distance);
		
		this.name = name;
		
		this.pieces = new StructurePiece[4];
		this.pieces[0] = new StructurePiece(tag);
		this.pieces[1] = pieces[0].getRotated90();
		this.pieces[2] = pieces[1].getRotated90();
		this.pieces[3] = pieces[2].getRotated90();
		
		if (tag.hasKey("foundation_block")) {
			UniqueIdentifier uidr = new UniqueIdentifier(tag.getString("foundation_block"));
			this.foundation_block = GameRegistry.findBlock(uidr.modId, uidr.name);
			this.foundation_meta = tag.getShort("foundation_meta");
		} else {
			this.foundation_block = null;
			this.foundation_meta = 0;
		}
	}
	
	public StructurePiece getRotationOfStructure(World world, int chunk_x, int chunk_z)
	{
		int r = world.rand.nextInt(4);
		StructurePiece sp = this.pieces[r];
		return sp;
	}
	
	public int getYCoordForGen(World world, int x, int z)
	{
		return world.getHeightValue(x, z);
	}

	@Override
	public void generate(World world, int chunk_x, int chunk_z)
	{
		int x = chunk_x * 16;
		int z = chunk_z * 16;
		int y = this.getYCoordForGen(world, x, z);
		
		if (DebugConfig.log_structure_gens) {
			ResAdditae.LOG.info(String.format("Generated %s at (%d, %d, %d)", this.name, x, y, z));
		}
		
		StructurePiece sp = this.getRotationOfStructure(world, chunk_x, chunk_z);
		sp.placeInWorld(world, world.rand, x, y, z, 0l);
		
		if (this.foundation_block == null) {
			return;
		}
		int bx = x - sp.origin_x;
		int by = y - sp.origin_y;
		int bz = z - sp.origin_z;
		int ex = bx + sp.size_x;
		int ez = bz + sp.size_z;
		for (int y0 = y; y0 > 0; y0--) {
			boolean f = false;
			for (int x0 = bx; x0 < ex; x0++) {
				for (int z0 = bz; z0 < ez; z0++) {
					Block existing = world.getBlock(x0, y0, z0);
					if (existing.isOpaqueCube() && !(existing.getMaterial() == Material.leaves)) {
						continue;
					}
					f = true;
					world.setBlock(x0, y0, z0, this.foundation_block, this.foundation_meta, 2);
				}
			}
			if (!f) {
				break;
			}
		}
	}
}
