package com.captrojo.resadditae.block.generic;

import java.util.List;
import java.util.Random;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMultiPressurePlate extends BlockPressurePlate implements IMultiBlock
{
	public final IMultiBlockData block_data;

	public BlockMultiPressurePlate(String name, IMultiBlockData block_data)
	{
		super(name, block_data.getMaterial(), Sensitivity.everything);
		this.block_data = block_data;

		this.setBlockName(name);
		this.setCreativeTab(null);
		this.block_data.setBlockData(this);
	}
	
	@Override
	public IMultiBlockData getData(int meta)
	{
		return this.block_data;
	}

	/* setBlockBoundsFromMeta */
	@Override
	protected void func_150063_b(int meta)
	{
		boolean flag = this.func_150060_c(meta) > 0;
		float f = 0.0625F;

		if (flag) {
			this.setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
		} else {
			this.setBlockBounds(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
		}
	}

	/* getMetaFromWeight */
	@Override
	protected int func_150066_d(int weight)
	{
		return (weight > 0) ? 1 : 0;
	}

	/* getRedstoneSignalFromMeta */
	@Override
	protected int func_150060_c(int meta)
	{
		return ((meta & 0x1) != 0) ? 15 : 0;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!world.isRemote) {
			int l = world.getBlockMetadata(x, y, z);

			if ((l & 0x1) != 0) {
				this.func_150062_a(world, x, y, z, l);
			}
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (!world.isRemote) {
			int l = world.getBlockMetadata(x, y, z);

			if ((l & 0x1) == 0) {
				this.func_150062_a(world, x, y, z, l);
			}
		}
	}

	/* updatePressurePlateState */
	@Override
	protected void func_150062_a(World world, int x, int y, int z, int meta)
	{
		int m = meta & 0xe;
		meta &= 0x1;

		int weight = this.func_150065_e(world, x, y, z);
		boolean is_down = meta > 0;
		boolean is_pressed = weight > 0;

		if (is_down != is_pressed) {
			world.setBlockMetadataWithNotify(x, y, z, this.func_150066_d(weight) | m, 2);
			this.func_150064_a_(world, x, y, z);
			world.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
		}

		if (!is_pressed && is_down) {
			world.playSoundEffect((double) x + 0.5D, (double) y + 0.1D, (double) z + 0.5D, "random.click", 0.3F, 0.5F);
		} else if (is_pressed && !is_down) {
			world.playSoundEffect((double) x + 0.5D, (double) y + 0.1D, (double) z + 0.5D, "random.click", 0.3F, 0.6F);
		}

		if (is_pressed) {
			world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
		}
	}

	public int fixMeta(int meta)
	{
		return meta >> 1;
	}
	
	public int getTextureIdx(int meta)
	{
		return meta >> 1;
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.block_data.getHardness(meta);
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double ex, double ey, double ez)
	{
		int meta = this.fixMeta(world.getBlockMetadata(x, y, z));
		return this.block_data.getResistance(meta);
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta & 0xe;
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
			list.add(new ItemStack(item, 1, m * 2));
		}
	}
}
