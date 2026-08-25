package com.captrojo.resadditae.item.block;

import com.captrojo.resadditae.block.ModBlocks;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.tileentity.TEMossLayer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMossLayer extends ItemBlock
{
	public ItemMossLayer(Block block)
	{
		super(block);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz)
	{
		Block existing = world.getBlock(x, y, z);

		if (existing == ModBlocks.moss_layer) {
			TEMossLayer te = (TEMossLayer) world.getTileEntity(x, y, z);
			if (te.addLayer(side)) {
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
				stack.stackSize--;
				return true;
			}
			return false;
		}

		if (existing == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (existing != Blocks.vine && existing != Blocks.tallgrass && existing != Blocks.deadbush && !existing.isReplaceable(world, x, y, z)) {
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
		}

		existing = world.getBlock(x, y, z);
		if (existing == ModBlocks.moss_layer) {
			return this.onItemUse(stack, player, world, x, y, z, side, hx, hy, hz);
		}

		if (stack.stackSize == 0) {
			return false;
		} else if (!player.canPlayerEdit(x, y, z, side, stack)) {
			return false;
		} else if (y == 255 && this.field_150939_a.getMaterial().isSolid()) {
			return false;
		} else if (world.canPlaceEntityOnSide(this.field_150939_a, x, y, z, false, side, player, stack)) {
			if (this.placeBlockAt(stack, player, world, x, y, z, side, hx, hy, hz, side | 0x8)) {
				world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
				stack.stackSize--;
			}

			return true;
		}
		return false;
	}
}
