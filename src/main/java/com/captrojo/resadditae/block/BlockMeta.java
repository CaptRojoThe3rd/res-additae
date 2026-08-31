package com.captrojo.resadditae.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;

public class BlockMeta
{
	public static final BlockMeta AIR = new BlockMeta(Blocks.air, 0);
	
	public Block block;
	public int meta;
	
	public BlockMeta(Block block, int meta)
	{
		this.block = block;
		this.meta = meta;
	}
	
	public BlockMeta(IBlockAccess world, int x, int y, int z)
	{
		this.block = world.getBlock(x, y, z);
		this.meta = world.getBlockMetadata(x, y, z);
	}
	
	@Deprecated
	public BlockMeta(ItemStack stack)
	{
		this.block = Block.getBlockFromItem(stack.getItem());
		this.meta = stack.getItemDamage();
	}
	
	public ItemStack stack(int count)
	{
		return new ItemStack(this.block, count, this.meta);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof BlockMeta)) {
			return false;
		}
		BlockMeta other = (BlockMeta) obj;
		if (other.block != this.block) {
			return false;
		}
		return other.meta == this.meta;
	}
	
	@Override
	public int hashCode()
	{
		return Block.getIdFromBlock(this.block) | (this.meta << 16); 
	}
}
