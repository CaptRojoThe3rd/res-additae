package com.captrojo.resadditae.block.generic;

import java.util.Random;

import com.captrojo.resadditae.block.IDoubleSlab;
import com.captrojo.resadditae.block.IMultiBlockData;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;

public class BlockMultiDirectionalWDblSlab extends BlockMultiDirectional implements IDoubleSlab
{
	private Block slab;
	
	public BlockMultiDirectionalWDblSlab(String name, IMultiBlockData block_data, boolean special_renderer)
	{
		super(name, block_data, special_renderer);
		
		this.dir_map = new int[] {0x0, 0x2, 0x4, 0x6};
		this.dir_mask = 0x6;
		this.dir_shift = 1;
	}
	
	@Override
	public Item getItemDropped(int meta, Random rand, int fortune)
	{
		if ((meta & 0x8) == 0 || this.slab == null) {
			return Item.getItemFromBlock(this);
		}
		return Item.getItemFromBlock(this.slab);
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random rand)
	{
		if (this.data.doesBlockShatter(meta)) {
			return 0;
		}
		return ((meta & 0x8) == 0) ? 1 : 2;
	}
	
	@Override
	public int fixMeta(int meta)
	{
		return meta & 0x1;
	}
	
	@Override
	public int getTextureIdx(int meta)
	{
		return meta & 0x1;
	}

	@Override
	public void setSlab(Block slab)
	{
		this.slab = slab;
	}

	@Override
	public Block getSingleSlab()
	{
		return this.slab;
	}

	@Override
	public Block getDoubleSlab()
	{
		return this;
	}
}
