package com.captrojo.resadditae.item.block;

import java.util.List;

import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBlockDoor extends ItemBlock
{
	protected final Block block;
	
	private IIcon texture;

	public ItemBlockDoor(Block block, String name)
	{
		super(block);
		this.block = block;
		
		this.setTextureName(ResAdditae.ident("doors/" + name));
		this.setCreativeTab(null);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hx, float hy, float hz)
	{
		if (side != 1) {
			return false;
		}
		y++;
		Block block = this.block;

		if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack)) {
			if (!block.canPlaceBlockAt(world, x, y, z)) {
				return false;
			}
			int i1 = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
			placeDoorBlock(world, x, y, z, i1, block);
			stack.stackSize--;
			return true;
		}
		return false;
	}
	
	public static void placeDoorBlock(World world, int x, int y, int z, int side, Block b)
	{
		byte b0 = 0;
		byte b1 = 0;

		if (side == 0) {
			b1 = 1;
		}

		if (side == 1) {
			b0 = -1;
		}

		if (side == 2) {
			b1 = -1;
		}

		if (side == 3) {
			b0 = 1;
		}

		int i1 = (world.getBlock(x - b0, y, z - b1).isNormalCube() ? 1 : 0) + (world.getBlock(x
				- b0, y + 1, z - b1).isNormalCube() ? 1 : 0);
		int j1 = (world.getBlock(x + b0, y, z + b1).isNormalCube() ? 1 : 0) + (world.getBlock(x
				+ b0, y + 1, z + b1).isNormalCube() ? 1 : 0);
		boolean flag = world.getBlock(x - b0, y, z - b1) == b || world.getBlock(x - b0,
				y + 1, z - b1) == b;
		boolean flag1 = world.getBlock(x + b0, y, z + b1) == b || world.getBlock(x + b0,
				y + 1, z + b1) == b;
		boolean flag2 = false;

		if (flag && !flag1) {
			flag2 = true;
		} else if (j1 > i1) {
			flag2 = true;
		}

		world.setBlock(x, y, z, b, side, 2);
		world.setBlock(x, y + 1, z, b, 8 | (flag2 ? 1 : 0), 2);
		world.notifyBlocksOfNeighborChange(x, y, z, b);
		world.notifyBlocksOfNeighborChange(x, y + 1, z, b);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ResAdditae.addItemDescription(stack, list);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getSpriteNumber()
	{
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.texture = reg.registerIcon(this.getIconString());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		return texture;
	}
}
