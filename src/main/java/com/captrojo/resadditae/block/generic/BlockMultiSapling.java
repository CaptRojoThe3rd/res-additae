package com.captrojo.resadditae.block.generic;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.block.WoodTypes;
import com.captrojo.resadditae.world.gen.feature.tree.ModTrees;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BlockMultiSapling extends BlockSapling implements IMultiBlock
{
	private static final int[][] SAPLING_2X2_OFFS_X = {
		{0, 1, 0, 1},
		{-1, 0, -1, 0},
		{0, 1, 0, 1},
		{-1, 0, -1, 0}
	};
	private static final int[][] SAPLING_2X2_OFFS_Z = {
		{0, 0, 1, 1},
		{0, 0, 1, 1},
		{-1, -1, 0, 0},
		{-1, -1, 0, 0}
	};
	private static final int[][] SAPLING_OFFS = {
		{0, 0},
		{-1, 0},
		{0, -1},
		{-1, -1}
	};
	
	/**
	 * Returns an (X, Z) offset from the growing if there is a 2x2 sapling grid. Otherwise returns null.
	 */
	private static int[] is2x2SaplingPresent(World world, int x, int y, int z, Block block, int meta)
	{
		meta &= 0x7;
	outer:
		for (int t = 0; t < 4; t++) {
			for (int i = 0; i < 4; i++) {
				int ox = SAPLING_2X2_OFFS_X[t][i];
				int oz = SAPLING_2X2_OFFS_Z[t][i];
				Block test_block = world.getBlock(x + ox, y, z + oz);
				int test_meta = world.getBlockMetadata(x + ox, y, z + oz) & 0x7;
				if (block != test_block || meta != test_meta) {
					continue outer;
				}
			}
			return SAPLING_OFFS[t];
		}
		return null;
	}
	
	public final IMultiBlockData block_data;
	
	public BlockMultiSapling(String name, IMultiBlockData data)
	{
		super();
		this.block_data = data;
		
		this.setBlockName(name);
		this.setCreativeTab(null);
		this.setStepSound(soundTypeGrass);
	}
	
	@Override
	public IMultiBlockData getData(int meta)
	{
		return this.block_data;
	}
	
	@Override
	public void func_149878_d(World world, int x, int y, int z, Random rand)
	{
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z)) return;
		
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		WoodTypes type = WoodTypes.getWoodFromSapling(this, meta);
		WorldGenAbstractTree tree = ModTrees.getGenFromWood(type);
		
		if (type == WoodTypes.ENCHANTED_ASH) {
			int[] offs = is2x2SaplingPresent(world, x, y, z, this, meta);
			if (offs == null) {
				return;
			}
			x += offs[0];
			z += offs[1];
		}

		world.setBlock(x, y, z, Blocks.air);
		if (!tree.generate(world, rand, x, y, z)) {
			world.setBlock(x, y, z, block, meta, 2);
		}
	}
	
	public int fixMeta(int meta)
	{
		return meta & 0x7;
	}
	
	public int getTextureIdx(int meta)
	{
		return meta & 0x7;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return this.fixMeta(meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.block_data.registerIcons(reg);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.block_data.getIcon(side, this.fixMeta(meta));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int m : this.block_data.getValidMetas()) {
			list.add(new ItemStack(item, 1, m));
		}
	}
}
