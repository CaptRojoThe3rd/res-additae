package com.captrojo.resadditae.block.special;

import java.util.Random;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

/*
 * Why does Minecraft make so many functions private, and why does Forge not make them public?!
 * At least this lets me name the parameters and variables...
 */
public class BlockDepthFarmland extends BlockFarmland
{
	@SideOnly(Side.CLIENT)
	private IIcon texture_dry;
	@SideOnly(Side.CLIENT)
	private IIcon texture_wet;

	public BlockDepthFarmland()
	{
		this.setBlockName("depth_farmland");
		this.setHardness(0.6f);
		this.setStepSound(soundTypeGravel);
	}

	/* func_149822_e */
	/* Originally had unnecessary for loops. Good job, Mojang. */
	public boolean hasCropGrowingAtop(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y + 1, z);
		return (block instanceof IPlantable && canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) block));
	}

	/* func_149821_m */
	public boolean hasWaterNearby(World world, int x0, int y0, int z0)
	{
		for (int x = x0 - 4; x <= x0 + 4; x++) {
			for (int y = y0; y <= y0 + 1; y++) {
				for (int z = z0 - 4; z <= z0 + 4; z++) {
					if (world.getBlock(x, y, z).getMaterial() == Material.water) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if (!this.hasWaterNearby(world, x, y, z) && !world.canLightningStrikeAt(x, y + 1, z)) {
			int l = world.getBlockMetadata(x, y, z);

			if (l > 0) {
				world.setBlockMetadataWithNotify(x, y, z, l - 1, 2);
			} else if (!this.hasCropGrowingAtop(world, x, y, z)) {
				world.setBlock(x, y, z, ModBlocks.depth_soil);
			}
		} else {
			world.setBlockMetadataWithNotify(x, y, z, 7, 2);
		}
	}

	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity entity, float f)
	{
		if (!world.isRemote && world.rand.nextFloat() < f - 0.5f) {
			if (!(entity instanceof EntityPlayer) && !world.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
				return;
			}
			world.setBlock(x, y, z, ModBlocks.depth_soil);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		super.onNeighborBlockChange(world, x, y, z, block);
		Material material = world.getBlock(x, y + 1, z).getMaterial();

		if (material.isSolid()) {
			world.setBlock(x, y, z, ModBlocks.depth_soil);
		}
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		return ModBlocks.depth_soil.getItemDropped(0, rand, fortune);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{
		EnumPlantType type = plantable.getPlantType(world, x, y + 1, z);
		if (type == EnumPlantType.Crop) {
			return true;
		}
		return super.canSustainPlant(world, x, y, z, direction, plantable);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemFromBlock(ModBlocks.depth_soil);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.texture_dry = reg.registerIcon(ResAdditae.ident("depths/farmland_dry"));
		this.texture_wet = reg.registerIcon(ResAdditae.ident("depths/farmland_wet"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side == 1 ? (meta > 0 ? this.texture_wet : this.texture_dry) : ModBlocks.depth_soil.getBlockTextureFromSide(side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		return ModBlocks.depth_soil.colorMultiplier(world, x, y, z);
	}
}
