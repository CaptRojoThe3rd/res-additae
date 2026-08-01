package com.captrojo.resadditae.item.equipment;

import java.util.List;

import com.captrojo.resadditae.main.ItemHlpr;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;

public class ItemShovel extends net.minecraft.item.ItemSpade
{
	public ItemShovel(String name, String texture_name, ToolMaterial material)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setTextureName(ResAdditae.ident(texture_name));
		this.setCreativeTab(null);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ItemHlpr.addItemDescription(stack, list);
	}
}
