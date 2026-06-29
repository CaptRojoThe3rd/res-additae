package com.captrojo.resadditae.block.special;

import com.captrojo.resadditae.block.BasicBlockData;
import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.block.generic.BlockBasic;
import com.captrojo.resadditae.world.biome.depths.BiomeDepthsBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockDepthSoil extends BlockBasic
{
	public BlockDepthSoil()
	{
		super("depth_soil", "depths/soil", new BasicBlockData(Material.ground, Block.soundTypeGravel, 0.7f, 0.7f, "shovel", 0));
	}
	
	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{
		return (plantable.getPlantType(world, x, y, z) == EnumPlantType.Plains);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		int r = 255 * 12;
		int g = 255 * 12;
		int b = 255 * 12;
		int avg_div = 12 + 9;

		for (int zoff = -1; zoff <= 1; zoff++) {
			for (int xoff = -1; xoff <= 1; xoff++) {
				BiomeGenBase biome = world.getBiomeGenForCoords(x + xoff, z + zoff);
				int bcol;
				if (biome instanceof BiomeDepthsBase) {
					bcol = ((BiomeDepthsBase) biome).getBiomeSoilColor(x + xoff, y, z + zoff);
				} else {
					bcol = biome.getBiomeGrassColor(x + xoff, y, z + zoff);
				}
				r += (bcol & 0xff0000) >> 16;
				g += (bcol & 0x00ff00) >> 8;
				b += bcol & 0x0000ff;
			}
		}

		int color = ((r / avg_div & 0xff) << 16) | ((g / avg_div & 0xff) << 8) | (b / avg_div & 0xff);
		return color;
	}
}
