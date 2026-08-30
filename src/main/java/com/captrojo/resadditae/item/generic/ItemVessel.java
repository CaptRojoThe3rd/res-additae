package com.captrojo.resadditae.item.generic;

import java.util.List;

import com.captrojo.resadditae.config.common.PlayerConfig;
import com.captrojo.resadditae.extprop.ManaUpgrades;
import com.captrojo.resadditae.extprop.PlayerAttributes;
import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.stats.ModAchievements;
import com.captrojo.resadditae.util.ItemHlpr;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemVessel extends Item
{
	public static final int MMASK = 0xff;
	public static final int VF_PIECE = 0x4000;
	
	private static ItemStack executeHeartContainerOp(ItemStack stack, EntityPlayer player, boolean anti)
	{
		IAttributeInstance att_inst = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth);
		AttributeModifier health_mod = att_inst.getModifier(PlayerAttributes.HEART_CONTAINER_UUID);
		if (health_mod == null) {
			health_mod = new AttributeModifier(PlayerAttributes.HEART_CONTAINER_UUID, PlayerAttributes.HEART_CONTAINER_NAME, 0.0d, 0);
			att_inst.applyModifier(health_mod);
		}
		
		if (anti && health_mod.getAmount() <= ((double) PlayerConfig.health_minimum * 2d) - 20d) {
			return stack;
		} else if (health_mod.getAmount() >= (double) (PlayerConfig.health_maximum - PlayerConfig.health_base) * 2d) {
			return stack;
		}
		
		double new_amount = health_mod.getAmount() + (anti ? -2d : 2d);
		
		att_inst.removeModifier(health_mod);
		health_mod = new AttributeModifier(PlayerAttributes.HEART_CONTAINER_UUID, PlayerAttributes.HEART_CONTAINER_NAME, new_amount, 0);
		att_inst.applyModifier(health_mod);
		
		if (!anti) {
			player.triggerAchievement(ModAchievements.hearts_increased);
		}
		if (health_mod.getAmount() >= (double) (PlayerConfig.health_maximum - PlayerConfig.health_base) * 2d) {
			player.triggerAchievement(ModAchievements.hearts_maxed);
		}
		
		stack.stackSize--;
		return stack;
	}
	
	private IIcon[] base_icons;
	private IIcon[] piece_icons;
	
	public ItemVessel()
	{
		this.setUnlocalizedName("vessel");
		this.setHasSubtypes(true);
	}
	
	private int maxMeta()
	{
		return VesselTypes.values()[VesselTypes.values().length - 1].meta + 1;
	}
	
	private VesselTypes getType(int meta)
	{
		return VesselTypes.values()[meta & MMASK];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (player.isSneaking() || world.isRemote) {
			return stack;
		}
		
		int meta = stack.getItemDamage();
		if ((meta & VF_PIECE) != 0) {
			return stack;
		}
		
		if ((meta & MMASK) >= VesselTypes.values().length) {
			return stack;
		}
		VesselTypes type = VesselTypes.values()[meta & MMASK];
		
		switch (type) {
		case HEART_CONTAINER:
			return executeHeartContainerOp(stack, player, false);
		}
		
		return stack;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getItemDamage();
		String name = this.getUnlocalizedName() + "." + this.getType(meta).name;
		
		if ((meta & VF_PIECE) != 0) {
			name += "_piece";
		}
		
		return name;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (VesselTypes type : VesselTypes.values()) {
			list.add(new ItemStack(ModItems.vessels, 1, type.meta));
			if ((type.flags & ItemVessel.VF_PIECE) != 0) {
				list.add(new ItemStack(ModItems.vessels, 1, type.meta | VF_PIECE));
			}
			//if ((type.flags & ItemVessel.VF_ANTI) != 0) {
			//	list.add(new ItemStack(ModItems.vessels, 1, type.meta | VF_ANTI));
			//}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.base_icons = new IIcon[this.maxMeta()];
		this.piece_icons = new IIcon[this.maxMeta()];
		
		for (VesselTypes type : VesselTypes.values()) {
			String name = ResAdditae.ident("vessels/" + type.name);
			this.base_icons[type.meta] = reg.registerIcon(name);
			if (type.hasFlag(VF_PIECE)) {
				this.piece_icons[type.meta] = reg.registerIcon(name + "_piece"); 
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta)
	{
		if ((meta & MMASK) >= this.maxMeta()) {
			meta &= ~MMASK;
		}
		
		if ((meta & VF_PIECE) != 0) {
			return this.piece_icons[meta & MMASK];
		}
		
		return this.base_icons[meta & MMASK];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean b1)
	{
		ItemHlpr.addItemDescription(stack, list);
	}
}
