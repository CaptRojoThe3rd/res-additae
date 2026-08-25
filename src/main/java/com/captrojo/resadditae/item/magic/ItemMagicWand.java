package com.captrojo.resadditae.item.magic;

import java.util.List;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.WandCore;
import com.captrojo.resadditae.magic.WandMaterial;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.util.I18nHlpr;
import com.captrojo.resadditae.util.ItemHlpr;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/* Wand metadata bit descriptions:
 * 
 * 15      bit       0
 * .... .ppp cccc mmmm
 *       ||| |||| ++++-- Material
 *       ||| ++++------- Core
 *       +++------------ Power (MagicComplexity)
 */
public class ItemMagicWand extends Item
{
	IIcon[] textures;
	
	public ItemMagicWand()
	{
		this.setUnlocalizedName("magic_wand");
		this.setHasSubtypes(true);
	}
	
	public WandMaterial getWandMaterial(int meta)
	{
		int a = meta & 0xf;
		if (a >= WandMaterial.values().length) {
			return WandMaterial.PLAIN_WOOD;
		}
		return WandMaterial.values()[a];
	}
	
	public WandCore getWandCore(int meta)
	{
		int a = (meta >> 4) & 0xf;
		if (a >= WandCore.values().length) {
			return WandCore.LAPIS_LAZULI;
		}
		return WandCore.values()[a];
	}
	
	public MagicComplexity getWandLevel(int meta)
	{
		int a = (meta >> 8) & 0x7;
		if (a >= MagicComplexity.values().length) {
			return MagicComplexity.BEGINNER;
		}
		return MagicComplexity.values()[a];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote) {
			return stack;
		}
		RAPlayerProperties rpp = RAPlayerProperties.get(player);
		if (rpp.wand_item == null) {
			rpp.onWandChanged(stack.copy());
			stack.stackSize = 0;
			return stack;
		}
		ItemStack old_wand = rpp.wand_item;
		rpp.onWandChanged(stack.copy());
		stack.func_150996_a(old_wand.getItem());
		stack.setItemDamage(old_wand.getItemDamage());
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (WandMaterial wm : WandMaterial.values()) {
			for (WandCore wc : WandCore.values()) {
				for (MagicComplexity mc : MagicComplexity.values()) {
					int meta = wm.ordinal() + (wc.ordinal() << 4) + (mc.ordinal() << 8);
					list.add(new ItemStack(this, 1, meta));
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.textures = new IIcon[WandMaterial.values().length];
		for (int i = 0; i < this.textures.length; i++) {
			this.textures[i] = reg.registerIcon(ResAdditae.ident("wands/" + WandMaterial.values()[i].name));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		int t = this.getWandMaterial(meta).ordinal();
		return this.textures[t];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ItemHlpr.addItemDescription(stack, list);
		
		int meta = stack.getItemDamage();
		WandMaterial wand_material = this.getWandMaterial(meta);
		WandCore wand_core = this.getWandCore(meta);
		MagicComplexity wand_level = this.getWandLevel(meta);
		
		String key_material = this.getUnlocalizedName() + ".material";
		String key_core = this.getUnlocalizedName() + ".core";
		String key_level = this.getUnlocalizedName() + ".level";
		
		list.add("");
		list.add(I18nHlpr.getf(key_material, I18nHlpr.get(key_material + "." + wand_material.name)));
		list.add(I18nHlpr.getf(key_core, I18nHlpr.get(key_core + "." + wand_core.name)));
		list.add(I18nHlpr.getf(key_level, I18nHlpr.get(key_level + "." + wand_level.name)));
	}
}
