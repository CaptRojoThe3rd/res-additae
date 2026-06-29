package com.captrojo.resadditae.item.block;

import java.util.List;

import com.captrojo.resadditae.block.IMultiBlock;
import com.captrojo.resadditae.block.IMultiBlockData;
import com.captrojo.resadditae.block.SlabAssociations;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockMultiSlab extends ItemSlab
{
	protected final IMultiBlockData block_data;
	protected final BlockSlab single_slab;
	protected final Block double_slab;

	public ItemBlockMultiSlab(Block block)
	{
		super(block, (BlockSlab) block, null, false);
		this.block_data = ((IMultiBlock) block).getData(0);
		this.single_slab = (BlockSlab) block;
		this.double_slab = SlabAssociations.getBlockFromSlab(block);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz)
	{
		if (stack.stackSize == 0) {
			return false;
		} else if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else {
			Block block = world.getBlock(x, y, z);
			int i1 = world.getBlockMetadata(x, y, z);
			int meta = i1 & 7;
			boolean upper = (i1 & 8) != 0;

			if ((side == 1 && !upper || side == 0 && upper) && block == this.single_slab && meta == stack.getItemDamage()) {
				if (world.checkNoEntityCollision(this.double_slab.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, this.double_slab, meta | 0x8, 3)) {
					world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this.double_slab.stepSound.func_150496_b(), (this.double_slab.stepSound.getVolume() + 1.0F) / 2.0F, this.double_slab.stepSound.getPitch() * 0.8F);
					--stack.stackSize;
				}

				return true;
			} else {
				return this.func_150946_a(stack, player, world, x, y, z, side) ? true : super.onItemUse(stack, player, world, x, y, z, side, hx, hy, hz);
			}
		}
	}

	protected boolean func_150946_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side)
	{
		switch (side) {
		case 0:
			y--;
			break;
		case 1:
			y++;
			break;
		case 2:
			z--;
			break;
		case 3:
			z++;
			break;
		case 4:
			x--;
			break;
		case 5:
			x++;
			break;
		}

		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z) & 0x7;

		if (block == this.single_slab && meta == stack.getItemDamage()) {
			if (world.checkNoEntityCollision(this.double_slab.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, this.double_slab, meta | 0x8, 3)) {
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this.double_slab.stepSound.func_150496_b(), (this.double_slab.stepSound.getVolume() + 1.0F) / 2.0F, this.double_slab.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}

			return true;
		}
		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getItemDamage() & 7;
		if (!this.block_data.isValidMeta(meta)) {
			return this.getUnlocalizedName() + "." + meta;
		}
		return this.getUnlocalizedName() + "." + this.block_data.getName(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ResAdditae.addItemDescription(stack, list);
	}
}
