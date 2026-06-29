package com.captrojo.resadditae.item.equipment;

import java.util.List;

import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemArmor extends net.minecraft.item.ItemArmor
{
	private final String armor_texture;
	private final ItemStack repair_item;
	
	public ItemArmor(String name, String texture_name, String armor_texture, ArmorMaterial material, ItemStack repair_item, int piece)
	{
		super(material, 0, piece);
		this.setUnlocalizedName(name);
		this.setTextureName(ResAdditae.ident(texture_name));
		this.setCreativeTab(null);
		
		this.armor_texture = ResAdditae.ident("textures/armor/" + armor_texture + (piece == ModItems.LEGGINGS ? ".2" : ".1") + ".png");
		this.repair_item = repair_item;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ResAdditae.addItemDescription(stack, list);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return this.armor_texture;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2)
	{
		return (stack2.getItem() == this.repair_item.getItem()) && (stack2.getItemDamage() == this.repair_item.getItemDamage());
	}
}
