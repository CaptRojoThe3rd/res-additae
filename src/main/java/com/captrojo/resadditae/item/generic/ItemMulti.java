package com.captrojo.resadditae.item.generic;

import java.util.List;

import com.captrojo.resadditae.item.IMultiItemData;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMulti extends Item
{
	public final IMultiItemData data;
	
	public ItemMulti(String name, IMultiItemData data)
	{
		this.setUnlocalizedName(name);
		this.data = data;
		
		this.setHasSubtypes(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		return data.getIcon(meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.data.registerIcons(reg);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i : this.data.getValidMetas()) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		if (this.data.getNames().length <= meta) {
			return this.getUnlocalizedName();
		}
		return this.getUnlocalizedName() + "." + this.data.getNames()[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ResAdditae.addItemDescription(stack, list);
	}
}
