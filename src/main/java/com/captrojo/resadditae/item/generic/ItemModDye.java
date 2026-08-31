package com.captrojo.resadditae.item.generic;

import com.captrojo.resadditae.entity.passive.EntitySheepMoreColors;
import com.captrojo.resadditae.item.IMultiItemData;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemModDye extends ItemMulti
{
	public ItemModDye(String name, IMultiItemData data)
	{
		super(name, data);
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
	{
		int meta = stack.getItemDamage();
		
		if (entity instanceof EntitySheepMoreColors) {
			EntitySheepMoreColors sheep = (EntitySheepMoreColors) entity;
			
			if (sheep.getSheared() || sheep.getFleeceColorReal() == meta) {
				return true;
			}
			
			int color = ((meta & ~0xf) >> 1) | (meta & 0x7);
			sheep.setFleeceColorReal(color);
			
			stack.stackSize--;
			return true;
		}
		
		if (entity instanceof EntitySheep) {
			if (entity.worldObj.isRemote) {
				return true;
			}
			EntitySheep sheep = EntitySheepMoreColors.replaceVanillaSheepWithModded((EntitySheep) entity);
			this.itemInteractionForEntity(stack, player, sheep);
			return true;
		}
		
		return false;
	}
}
