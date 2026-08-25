package com.captrojo.resadditae.item.generic;

import com.captrojo.resadditae.item.IMultiItemData;
import com.captrojo.resadditae.main.ClientProxy;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;

public class ItemMultiPotionIngredient extends ItemMulti
{
	public ItemMultiPotionIngredient(String name, IMultiItemData data)
	{
		super(name, data);
	}
	
	@Override
	public boolean isPotionIngredient(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public String getPotionEffect(ItemStack stack)
	{
		/* NEI finds potion ingredients by calling a vanilla method to determine the effect
		 * an ingredient will have. This means that returning some random potion effect will
		 * cause NEI to think this ingredient will brew something else. So, we will just not
		 * return any effect on the client.
		 * 
		 * Oh, and because the only argument we get is an ItemStack, we have to call a
		 * method that checks the current thread name to figure out whether we are on the
		 * client or server.
		 */
		
		/* This is the worst hack I have ever written. */
		if (ResAdditae.getSideUnsafely(Side.SERVER) == Side.CLIENT) {
			return "";
		}
		return PotionHelper.sugarEffect;
	}
}
