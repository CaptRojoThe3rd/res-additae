package com.captrojo.resadditae.item.equipment;

import java.util.List;

import com.captrojo.resadditae.entity.EntityThrownHalberd;
import com.captrojo.resadditae.item.ItemActions;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.material.ExtendedToolMaterial;
import com.google.common.collect.Sets;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemHalberd extends ItemSword
{
	public static final byte ACTION_POINT = 0;
	public static final byte ACTION_READY = 1;

	public final double attack_damage;
	public final double weight;
	
	public final ResourceLocation model_texture;

	public ItemHalberd(String name, String texture_name, String model_texture_name, ToolMaterial mat, ExtendedToolMaterial extmat)
	{
		super(name, texture_name, mat);
		
		ReflectionHelper.setPrivateValue(net.minecraft.item.ItemSword.class, this, mat.getDamageVsEntity() + 3.0f, "field_150934_a");
		this.attack_damage = mat.getDamageVsEntity() + 2.0f;
		this.weight = (double) extmat.weight;
		
		this.model_texture = new ResourceLocation(ResAdditae.ident(model_texture_name));
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		if (stack.getTagCompound() == null) {
			return EnumAction.none;
		}
		if (stack.getTagCompound().getByte("action") == ACTION_POINT) {
			return ItemActions.POINT_HALBERD;
		} else {
			return ItemActions.READY_HALBERD;
		}
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
		}

		if (player.isRiding()) {
			tag.setByte("action", ACTION_POINT);
		} else {
			tag.setByte("action", ACTION_READY);
		}

		stack.setTagCompound(tag);

		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int slot)
	{
		if (world.isRemote) {
			return;
		}
		if (stack.getTagCompound() == null) {
			return;
		}
		if (stack.getTagCompound().getByte("action") != ACTION_READY) {
			return;
		}

		Entity halberd = new EntityThrownHalberd(world, stack.copy(), player);
		player.inventory.mainInventory[player.inventory.currentItem] = null;
		world.spawnEntityInWorld(halberd);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean held)
	{
		if (!(entity instanceof EntityPlayer)) {
			return;
		}
		if (world.isRemote) {
			return;
		}

		EntityPlayer player = (EntityPlayer) entity;
		if (!player.isUsingItem() || !player.isRiding()) {
			return;
		}
		
		if (stack.getTagCompound() == null) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		byte action = stack.getTagCompound().getByte("action");
		if (action == ACTION_POINT) {
			this.executePointDamage(stack, world, player, player.rotationYaw + 20d);
			this.executePointDamage(stack, world, player, player.rotationYaw);
		}
	}

	public void executePointDamage(ItemStack stack, World world, EntityPlayer player, double p_yaw)
	{
		double yaw = p_yaw + 180d;
		for (; yaw < 0d; yaw += 360d)
			;
		yaw = yaw % 360d;
		double angle = yaw % 90d;
		int quad = (int) (yaw / 90d);
		quad = quad % 4;

		double o = 0d;
		double a = 0d;

		switch (quad) {
		case 0:
			o = 0d - Math.sin(Math.toRadians(90d - angle));
			a = Math.cos(Math.toRadians(90d - angle));
			break;
		case 1:
			o = Math.sin(Math.toRadians(angle));
			a = Math.cos(Math.toRadians(angle));
			break;
		case 2:
			o = Math.sin(Math.toRadians(90d - angle));
			a = 0d - Math.cos(Math.toRadians(90d - angle));
			break;
		case 3:
			o = 0d - Math.sin(Math.toRadians(angle));
			a = 0d - Math.cos(Math.toRadians(angle));
			break;
		default:
			ResAdditae.LOG.warn(String.format("ItemSpear: bad angle: %f, %d", angle, quad));
			break;
		}

		o *= 2.5;
		a *= 2.5;

		double px = player.posX + a;
		double py = player.posY + 0.1d;
		double pz = player.posZ + o;

		double dx = Math.pow(player.posX - player.lastTickPosX, 2);
		double dy = Math.pow(player.posY - player.lastTickPosY, 2);
		double dz = Math.pow(player.posZ - player.lastTickPosZ, 2);
		double dw = Math.pow(player.rotationYaw - player.prevRotationYaw, 2);
		double dist = Math.sqrt(dx + dy + dz + dw);
		float damage = (float) (dist * this.attack_damage * 4.0f);

		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(px - 0.5d, py - 1.0d, pz - 0.5d, px + 0.5d, py + 1.0d, pz + 0.5d);
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, aabb);
		for (Entity e : entities) {
			if (!(e instanceof EntityLivingBase)) {
				continue;
			}
			EntityLivingBase living = (EntityLivingBase) e;
			living.attackEntityAsMob(player);
			living.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
			stack.damageItem(1, player);
		}
	}
}
