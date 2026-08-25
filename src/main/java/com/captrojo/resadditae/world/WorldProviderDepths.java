package com.captrojo.resadditae.world;

import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.world.WorldChunkManagerDepths.BiomeGenLayers;
import com.captrojo.resadditae.world.gen.layer.GenLayerDepthsBiomes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

public class WorldProviderDepths extends WorldProvider
{
	public static final Vec3 FOG_COLOR = Vec3.createVectorHelper(0.05, 0.05, 0.0525);
	
	private static BiomeGenLayers createBiomeGens(long seed)
	{
		GenLayer biome_gen = new GenLayerDepthsBiomes(seed);
		biome_gen = new GenLayerFuzzyZoom(2000l, biome_gen);
		biome_gen = new GenLayerZoom(2001l, biome_gen);
		biome_gen = new GenLayerZoom(1000l, biome_gen);
		biome_gen = new GenLayerZoom(1001l, biome_gen);
		biome_gen = new GenLayerZoom(1003l, biome_gen);
		biome_gen = new GenLayerSmooth(700l, biome_gen);
		biome_gen = new GenLayerZoom(1006l, biome_gen);
		
		GenLayer biome_detail_gen = new GenLayerVoronoiZoom(10l, biome_gen);
		
		return new BiomeGenLayers(biome_gen, biome_detail_gen, seed);
	}
	
	@Override
	public void registerWorldChunkManager()
	{
		this.dimensionId = WorldGenConfig.depths_dimension_id;
		this.hasNoSky = true;

		BiomeGenLayers biome_gens = createBiomeGens(this.worldObj.getSeed());
		this.worldChunkMgr = new WorldChunkManagerDepths(biome_gens);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float celestial_angle, float z)
	{
		return FOG_COLOR;
	}

	@Override
	protected void generateLightBrightnessTable()
	{
		float f = 0.0125f;

		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0f - (float) i / 15.0f;
			this.lightBrightnessTable[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * (1.0f - f) + f;
		}
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderDepths(this.worldObj, this.worldObj.getSeed());
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		return false;
	}

	@Override
	public float calculateCelestialAngle(long time, float f)
	{
		return 0.0f;
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int x, int z)
	{
		return false;
	}

	@Override
	public String getDimensionName()
	{
		return "The Depths";
	}
}
