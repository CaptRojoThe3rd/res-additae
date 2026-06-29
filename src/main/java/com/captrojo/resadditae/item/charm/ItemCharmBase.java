package com.captrojo.resadditae.item.charm;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.gui.screen.GuiScreenCharmSettings;
import com.captrojo.resadditae.item.IItemWithSettings;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.KeyInputHandler;
import com.captrojo.resadditae.main.NBTHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketDisplayAlert;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class ItemCharmBase extends Item implements IItemWithSettings
{
	public static final ArrayList<ItemCharmBase> all_charms = new ArrayList<ItemCharmBase>();
	public static final ArrayList<ItemCharmBase> common_charms = new ArrayList<ItemCharmBase>();
	public static final ArrayList<ItemCharmBase> uncommon_charms = new ArrayList<ItemCharmBase>();
	public static final ArrayList<ItemCharmBase> rare_charms = new ArrayList<ItemCharmBase>();
	public static final ArrayList<ItemCharmBase> epic_charms = new ArrayList<ItemCharmBase>();
	
	public final EnumRarity rarity;
	public final int prim_mana_req;
	public final int sec_mana_req;
	public final int cooldown_time;
	
	public ItemCharmBase(String name, int max_damage, EnumRarity rarity, int prim_mana_req, int sec_mana_req, int cooldown_time)
	{
		super();
		this.rarity = rarity;
		this.prim_mana_req = prim_mana_req;
		this.sec_mana_req = sec_mana_req;
		this.cooldown_time = cooldown_time;
		
		this.setUnlocalizedName(name);
		this.setTextureName(ResAdditae.ident("charms/" + name));
		this.setMaxDamage(max_damage);
		this.setMaxStackSize(1);
		this.setCreativeTab(null);
		
		all_charms.add(this);
		switch (rarity) {
		case common:
			common_charms.add(this);
			break;
		case uncommon:
			uncommon_charms.add(this);
			break;
		case rare:
			rare_charms.add(this);
			break;
		case epic:
			epic_charms.add(this);
			break;
		default:
			break;
		}
	}
	
	public void notEnoughMana(EntityPlayer player)
	{
		ResAdditae.network.sendTo(new PacketDisplayAlert(PacketDisplayAlert.Type.HOTBAR_LOW, "alert.not_enough_mana"), (EntityPlayerMP) player);
	}
	
	public void noEntitiesNearby(EntityPlayer player)
	{
		ResAdditae.network.sendTo(new PacketDisplayAlert(PacketDisplayAlert.Type.HOTBAR_LOW, "alert.no_entities_nearby"), (EntityPlayerMP) player);
	}
	
	public boolean isOnCooldown(ItemStack stack)
	{
		NBTTagCompound tag = NBTHlpr.getItemStackTag(stack);
		short cooldown = tag.getShort("cooldown");
		return cooldown > 0;
	}
	
	public int getCurrentCooldownTime(ItemStack stack)
	{
		NBTTagCompound tag = NBTHlpr.getItemStackTag(stack);
		return tag.getShort("cooldown");
	}
	
	public void triggerCooldown(ItemStack stack)
	{
		NBTTagCompound tag = NBTHlpr.getItemStackTag(stack);
		tag.setShort("cooldown", (short) this.cooldown_time);
	}
	
	public boolean onItemRightClickPre(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote) {
			return false;
		}
		if (this.isOnCooldown(stack)) {
			return false;
		}
		RAPlayerProperties rpp = RAPlayerProperties.get(player);
		if (!rpp.hasEnoughMana(player.isSneaking() ? this.sec_mana_req : this.prim_mana_req)) {
			this.notEnoughMana(player);
			return false;
		}
		return true;
	}
	
	public ItemStack onItemRightClickPost(ItemStack stack, World world, EntityPlayer player)
	{
		RAPlayerProperties.get(player).useMana(player.isSneaking() ? this.sec_mana_req : this.prim_mana_req);
		this.triggerCooldown(stack);
		stack.attemptDamageItem(1, itemRand);
		return stack;
	}
	
	@Override
	public abstract ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player);
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
	{
		if (world.isRemote) {
			return;
		}
		NBTTagCompound tag = NBTHlpr.getItemStackTag(stack);
		short cooldown = tag.getShort("cooldown");
		if (cooldown > 0) {
			cooldown--;
		}
		tag.setShort("cooldown", cooldown);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return this.rarity;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return 10;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ResAdditae.addItemDescription(stack, list);
		
		String k, v;
		k = this.getUnlocalizedName() + ".desc_prim";
		v = I18nHlpr.get(k);
		if (!v.equals(k)) {
			list.add("");
			list.add(I18nHlpr.get("item.charm.prim_desc_title"));
			list.add(v);
			list.add(I18nHlpr.getf("item.charm.mana_req", this.prim_mana_req));
		}
		k = this.getUnlocalizedName() + ".desc_sec";
		v = I18nHlpr.get(k);
		if (!v.equals(k)) {
			list.add("");
			list.add(I18nHlpr.get("item.charm.sec_desc_title"));
			list.add(v);
			list.add(I18nHlpr.getf("item.charm.mana_req", this.sec_mana_req));
		}
		k = this.getUnlocalizedName() + ".desc_active_ability";
		v = I18nHlpr.get(k);
		if (!v.equals(k)) {
			list.add("");
			list.add(I18nHlpr.get("item.charm.active_ability_desc_title"));
			list.add(v);
		}
		
		list.add("");
		list.add(I18nHlpr.getf("item.charm.settings_keybind_hint", Keyboard.getKeyName(KeyInputHandler.open_item_settings.getKeyCode())));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getSettingsGui(EntityPlayer player)
	{
		return new GuiScreenCharmSettings(player);
	}
}
