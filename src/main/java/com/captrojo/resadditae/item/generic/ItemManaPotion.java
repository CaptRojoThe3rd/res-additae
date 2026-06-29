package com.captrojo.resadditae.item.generic;

import java.util.List;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemManaPotion extends ItemPotion
{
	public ItemManaPotion()
	{
		super();

		this.setUnlocalizedName("mana_potion");
		this.setTextureName(ResAdditae.ident("mana_potion"));
		this.setCreativeTab(null);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player)
	{
		RAPlayerProperties rpp = RAPlayerProperties.get(player);
		rpp.replenishMana(stack.getItemDamage() * 10);

		if (!player.capabilities.isCreativeMode) {
			stack.stackSize--;
			if (stack.stackSize <= 0) {
				return new ItemStack(Items.glass_bottle);
			}
			player.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
		}
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.drink;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item, 1, 5));
		list.add(new ItemStack(item, 1, 10));
		list.add(new ItemStack(item, 1, 1000));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		return this.itemIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass)
	{
		return this.itemIcon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.itemIcon = reg.registerIcon(this.iconString);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		return 0xffffff;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack)
	{
		return I18nHlpr.get(this.getUnlocalizedName() + ".name");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ResAdditae.addItemDescription(stack, list);
		list.add("");

		int amount = stack.getItemDamage() * 10;
		list.add(EnumChatFormatting.DARK_PURPLE + I18nHlpr.getf("potion.effects.whenDrank"));
		list.add(EnumChatFormatting.BLUE + I18nHlpr.getf("item.mana_potion.effect", amount));
	}
}
