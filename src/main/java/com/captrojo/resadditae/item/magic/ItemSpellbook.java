package com.captrojo.resadditae.item.magic;

import java.util.List;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.gui.screen.GuiSpellbook;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ItemHlpr;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSpellbook extends Item
{
	public static ItemStack createStack(Spell spell)
	{
		ItemStack stack = new ItemStack(ModItems.spellbook, 1, spell.complexity.ordinal());
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("spell", spell.getID());
		stack.setTagCompound(nbt);
		return stack;
	}
	
	public static Spell getSpell(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
			stack.setTagCompound(tag);
			return null;
		}
		int id = tag.getInteger("spell");
		return Spells.getByID(id);
	}
	
	IIcon[] textures;
	
	public ItemSpellbook()
	{
		this.setUnlocalizedName("spellbook");
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	public void displayGui(ItemStack stack, EntityPlayer player)
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiSpellbook(stack, player));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote) {
			this.displayGui(stack, player);
		}
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (Spell spell : Spells.SPELL_LIST) {
			list.add(createStack(spell));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.textures = new IIcon[MagicComplexity.values().length];
		for (int i = 0; i < this.textures.length; i++) {
			this.textures[i] = reg.registerIcon(ResAdditae.ident("spellbook/" + MagicComplexity.values()[i].name));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		int t = Math.min(meta, MagicComplexity.values().length - 1);
		return this.textures[t];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack)
	{
		Spell spell = getSpell(stack);
		if (spell == null) {
			return this.textures[0];
		}
		return this.textures[spell.complexity.ordinal()];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ItemHlpr.addItemDescription(stack, list);
		
		RAPlayerProperties rpp = RAPlayerProperties.get(player);
		Spell spell = getSpell(stack);
		if (rpp.hasLearnedSpell(spell)) {
			list.add("§7" + spell.getLocalizedName());
		} else {
			list.add(I18nHlpr.get("item.spellbook.desc.not_yet_learned"));
		}
	}
}
