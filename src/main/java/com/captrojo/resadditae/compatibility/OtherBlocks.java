package com.captrojo.resadditae.compatibility;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public enum OtherBlocks
{
	CONCRETE("Concrete", ModList.ET_FUTURUM, "concrete", 0),
	HBM_CONCRETE("NTM Concrete", ModList.HBM_NTM, "tile.concrete_smooth", 0),
	HBM_CONCRETE_COLORED("NTM Concrete (Colored)", ModList.HBM_NTM, "tile.concrete_colored", 0),
	HBM_CONCRETE_COLORED_EXT("NTM Concrete (Colored EXT)", ModList.HBM_NTM, "tile.concrete_colored_ext", 0);
	
	private ItemStack stack;
	
	private OtherBlocks(String name, Object...objs)
	{
		for (int i = 0; i < objs.length; i += 3) {
			ModList mod = (ModList) objs[i];
			if (!mod.isLoaded()) {
				continue;
			}
			Block block = GameRegistry.findBlock(mod.id, (String) objs[i + 1]);
			if (block == null) {
				continue;
			}
			this.stack = new ItemStack(block, 1, (int) objs[i + 2]);
			break;
		}
	}
	
	public boolean exists()
	{
		return this.stack != null;
	}
	
	public ItemStack info()
	{
		return this.stack;
	}
	
	public ItemStack stack(int count)
	{
		ItemStack copy = this.stack.copy();
		copy.stackSize = count;
		return copy;
	}
	
	public Block getBlock()
	{
		return Block.getBlockFromItem(this.stack.getItem());
	}
	
	public int getMeta()
	{
		return this.stack.getItemDamage();
	}
}
