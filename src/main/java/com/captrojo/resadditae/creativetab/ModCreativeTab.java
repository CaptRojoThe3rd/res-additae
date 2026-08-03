package com.captrojo.resadditae.creativetab;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ModCreativeTab extends CreativeTabs
{
	/**
	 * Because unorganized inventories annoy me, I have elected to put items into creative tabs
	 * manually. However, this breaks the search tab, as it hides items that have their
	 * creative tab set to null. So, we will go through all items this mod adds and set their
	 * creative tab to something other than null.
	 * 
	 * (Creative tabs can become disorganized when you either add a mod to an existing world,
	 * or update a mod in an existing world. Since the item IDs no longer line up between the
	 * global registry and the specific world, Forge attempts to map them, appending new item
	 * IDs to the end of the list (higher item ids). This alone already causes disorganization
	 * in the creative tabs, as the order of items no longer reflects the registration order.
	 * However, Forge apparently makes no effort to ensure the new items being appended will
	 * remain in their registration order, so the new items are added in a completely screwed-up
	 * order.)
	 */
	public static void fixSearchTab()
	{
		Iterator it = Item.itemRegistry.iterator();
		
		while (it.hasNext()) {
			Item item = (Item) it.next();
			if (item == null) {
				continue;
			}
			
			String name = Item.itemRegistry.getNameForObject(item);
			
			if (name.contains("resadditae") && !name.contains("double_slab")) {
				if (item instanceof ItemBlock) {
					(Block.getBlockFromItem(item)).setCreativeTab(tabAllSearch);
					continue;
				}
				item.setCreativeTab(tabAllSearch);
			}
		}
	}
	
	private final Item icon_item;
	protected final ArrayList<ItemStack> items;
	
	protected ModCreativeTab(String name, Item icon_item)
	{
		super(name);
		this.icon_item = icon_item;
		this.items = new ArrayList<ItemStack>();
	}
	
	protected void add(ItemStack stack)
	{
		this.items.add(stack);
	}
	
	protected void add(Item item)
	{
		this.items.add(new ItemStack(item, 1, 0));
	}
	
	protected void add(Item item, int...metas)
	{
		for (int m : metas) {
			this.items.add(new ItemStack(item, 1, m));
		}
	}
	
	protected void add(Block block)
	{
		this.items.add(new ItemStack(block, 1, 0));
	}
	
	protected void add(Block block, int...metas)
	{
		for (int m : metas) {
			this.items.add(new ItemStack(block, 1, m));
		}
	}
	
	protected void add(ArrayList<ItemStack> stacks)
	{
		for (ItemStack stack : stacks) {
			this.add(stack);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem()
	{
		return this.icon_item;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void displayAllReleventItems(List list)
	{
		for (ItemStack stack : this.items) {
			list.add(stack);
		}
	}
}
