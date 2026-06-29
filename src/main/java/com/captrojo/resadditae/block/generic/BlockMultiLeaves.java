package com.captrojo.resadditae.block.generic;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.GenericMultiBlockData;
import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.block.WoodTypes;
import com.captrojo.resadditae.world.feature.tree.ModTrees;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMultiLeaves extends BlockLeaves implements IMultiBlock
{
	public final GenericMultiBlockData data;
	public final int[] leaf_colors;
	public final boolean[] color_flags;

	private int[] decay_arr;

	public BlockMultiLeaves(String name, GenericMultiBlockData data, int[] leaf_colors, boolean[] color_flags)
	{
		this.data = data;
		this.leaf_colors = leaf_colors;
		this.color_flags = color_flags;

		this.setBlockName(name);
		this.setCreativeTab(null);
		this.data.setBlockData(this);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		WoodTypes type = WoodTypes.getWoodFromLeaf(this, meta & 0x3);
		return Item.getItemFromBlock(type.getSapling().block);
	}

	@Override
	public int damageDropped(int meta)
	{
		WoodTypes type = WoodTypes.getWoodFromLeaf(this, meta & 0x3);
		return type.getSapling().meta;
	}

	@Override
	public String[] func_150125_e()
	{
		return this.data.getNames();
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (world.isRemote) {
			return;
		}
		int meta = world.getBlockMetadata(x, y, z);
		
		WoodTypes wood_type = WoodTypes.getWoodFromLeaf(this, meta);
		boolean is_laggy_tree = (wood_type == WoodTypes.ENCHANTED_ASH || wood_type == WoodTypes.CHESTNUT);
		if (is_laggy_tree && (rand.nextInt(10) < 9)) {
			return;
		}

		if ((meta & 8) != 0 && (meta & 4) == 0) {
			byte b0 = is_laggy_tree ? (byte) 10 : (byte) 4;
			int i1 = b0 + 1;
			byte b1 = 32;
			int j1 = b1 * b1;
			int k1 = b1 / 2;

			if (this.decay_arr == null) {
				this.decay_arr = new int[b1 * b1 * b1];
			}

			int x1;

			if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
				int y1;
				int z1;

				for (x1 = -b0; x1 <= b0; ++x1) {
					for (y1 = -b0; y1 <= b0; ++y1) {
						for (z1 = -b0; z1 <= b0; ++z1) {
							Block block = world.getBlock(x + x1, y + y1, z + z1);

							if (!block.canSustainLeaves(world, x + x1, y + y1, z + z1)) {
								if (block.isLeaves(world, x + x1, y + y1, z + z1)) {
									this.decay_arr[(x1 + k1) * j1 + (y1 + k1) * b1 + z1 + k1] = -2;
								} else {
									this.decay_arr[(x1 + k1) * j1 + (y1 + k1) * b1 + z1 + k1] = -1;
								}
							} else {
								this.decay_arr[(x1 + k1) * j1 + (y1 + k1) * b1 + z1 + k1] = 0;
							}
						}
					}
				}

				for (x1 = 1; x1 <= 4; ++x1) {
					for (y1 = -b0; y1 <= b0; ++y1) {
						for (z1 = -b0; z1 <= b0; ++z1) {
							for (int k2 = -b0; k2 <= b0; ++k2) {
								if (this.decay_arr[(y1 + k1) * j1 + (z1 + k1) * b1 + k2 + k1] == x1 - 1) {
									if (this.decay_arr[(y1 + k1 - 1) * j1 + (z1 + k1) * b1 + k2 + k1] == -2) {
										this.decay_arr[(y1 + k1 - 1) * j1 + (z1 + k1) * b1 + k2 + k1] = x1;
									}

									if (this.decay_arr[(y1 + k1 + 1) * j1 + (z1 + k1) * b1 + k2 + k1] == -2) {
										this.decay_arr[(y1 + k1 + 1) * j1 + (z1 + k1) * b1 + k2 + k1] = x1;
									}

									if (this.decay_arr[(y1 + k1) * j1 + (z1 + k1 - 1) * b1 + k2 + k1] == -2) {
										this.decay_arr[(y1 + k1) * j1 + (z1 + k1 - 1) * b1 + k2 + k1] = x1;
									}

									if (this.decay_arr[(y1 + k1) * j1 + (z1 + k1 + 1) * b1 + k2 + k1] == -2) {
										this.decay_arr[(y1 + k1) * j1 + (z1 + k1 + 1) * b1 + k2 + k1] = x1;
									}

									if (this.decay_arr[(y1 + k1) * j1 + (z1 + k1) * b1 + (k2 + k1 - 1)] == -2) {
										this.decay_arr[(y1 + k1) * j1 + (z1 + k1) * b1 + (k2 + k1 - 1)] = x1;
									}

									if (this.decay_arr[(y1 + k1) * j1 + (z1 + k1) * b1 + k2 + k1 + 1] == -2) {
										this.decay_arr[(y1 + k1) * j1 + (z1 + k1) * b1 + k2 + k1 + 1] = x1;
									}
								}
							}
						}
					}
				}
			}

			x1 = this.decay_arr[k1 * j1 + k1 * b1 + k1];

			if (x1 >= 0) {
				world.setBlockMetadataWithNotify(x, y, z, meta & -9, 4);
			} else {
				this.removeLeaves(world, x, y, z);
			}
		}
	}

	private void removeLeaves(World world, int x, int y, int z)
	{
		this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockToAir(x, y, z);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return !((boolean) ReflectionHelper.getPrivateValue(BlockLeavesBase.class, Blocks.leaves, "field_150121_P"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z) & 0x3;

		int r = leaf_colors[meta] >> 16;
		int g = (leaf_colors[meta] >> 8) & 0xff;
		int b = leaf_colors[meta] & 0xff;

		if (color_flags[meta]) {
			return this.leaf_colors[meta];
		}

		for (int k1 = -1; k1 <= 1; ++k1) {
			for (int l1 = -1; l1 <= 1; ++l1) {
				int foliage_color = world.getBiomeGenForCoords(x + l1, z + k1).getBiomeFoliageColor(x + l1, y, z + k1);
				r += (foliage_color & 0xff0000) >> 16;
				g += (foliage_color & 0x00ff00) >> 8;
				b += foliage_color & 0x0000ff;
			}
		}

		return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta)
	{
		if (this.color_flags[meta & 0x3]) {
			return this.leaf_colors[meta & 0x3];
		}
		return ColorizerFoliage.getFoliageColorBasic();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		Block block = world.getBlock(x, y, z);
		boolean fancy = (boolean) ReflectionHelper.getPrivateValue(BlockLeavesBase.class, Blocks.leaves, "field_150121_P");
		return (!fancy && block == this) ? false : this.shouldSideBeRenderedSuper(world, x, y, z, side);
	}

	/* Because super.super.method() isn't allowed in Java */
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRenderedSuper(IBlockAccess world, int x, int y, int z, int side)
	{
		return side == 0 && this.minY > 0.0D ? true : (side == 1 && this.maxY < 1.0D ? true : (side == 2 && this.minZ > 0.0D ? true : (side == 3 && this.maxZ < 1.0D ? true : (side == 4 && this.minX > 0.0D ? true : (side == 5 && this.maxX < 1.0D ? true : !world.getBlock(x, y, z).isOpaqueCube())))));
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.data.getHardness(meta);
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double ex, double ey, double ez)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.data.getResistance(meta);
	}
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return this.data.getFlammability(this.fixMeta(world.getBlockMetadata(x, y, z)));
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return this.data.getFireSpreadSpeed(this.fixMeta(world.getBlockMetadata(x, y, z)));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.data.registerIcons(reg);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		boolean fancy = ReflectionHelper.getPrivateValue(BlockLeavesBase.class, Blocks.leaves, "field_150121_P");
		if (!fancy) {
			return this.data.getIconFast(side, this.fixMeta(meta));
		}
		return this.data.getIcon(side, this.fixMeta(meta));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int m : this.data.getValidMetas()) {
			list.add(new ItemStack(item, 1, m));
		}
	}

	public int fixMeta(int meta)
	{
		return meta & 0x3;
	}
	
	public int getTextureIdx(int meta)
	{
		return meta & 0x3;
	}
	
	@Override
	public IMultiBlockData getData(int meta)
	{
		return this.data;
	}
}
