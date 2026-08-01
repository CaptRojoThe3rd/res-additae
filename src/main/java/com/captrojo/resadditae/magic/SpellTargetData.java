package com.captrojo.resadditae.magic;

import com.captrojo.resadditae.entity.client.EntityTargetFX;
import com.captrojo.resadditae.main.PlayerHlpr;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class SpellTargetData
{
	public MovingObjectType type;
	
	public int block_x;
	public int block_y;
	public int block_z;
	
	EntityLivingBase entity;
	int entity_id;
	
	@SideOnly(Side.CLIENT)
	public EntityTargetFX targetfx;
	
	public SpellTargetData()
	{
		this.type = MovingObjectType.MISS;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateTarget(World world, EntityPlayer player, double reach)
	{
		MovingObjectPosition mop = PlayerHlpr.getThingLookedAt(world, player, reach);
		if (mop == null) {
			this.type = MovingObjectType.MISS;
			this.entity = null;
			this.entity_id = 0;
			if (this.targetfx != null) {
				this.targetfx.destroy();
				this.targetfx = null;
			}
			return;
		}
		
		this.type = mop.typeOfHit;
		if (this.type == MovingObjectType.BLOCK) {
			this.block_x = mop.blockX;
			this.block_y = mop.blockY;
			this.block_z = mop.blockZ;
		}
		
		if (this.type == MovingObjectType.ENTITY && mop.entityHit instanceof EntityLivingBase) {
			this.entity = (EntityLivingBase) mop.entityHit;
			this.entity_id = this.entity.getEntityId();
			if (this.targetfx == null) {
				this.targetfx = new EntityTargetFX(world, this.entity, player);
				Minecraft.getMinecraft().effectRenderer.addEffect(this.targetfx);
			}
			this.targetfx.target = this.entity;
		} else {
			if (this.targetfx != null) {
				this.targetfx.destroy();
				this.targetfx = null;
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void removeTarget()
	{
		this.type = MovingObjectType.MISS;
		if (this.targetfx != null) {
			this.targetfx.destroy();
			this.targetfx = null;
		}
	}
	
	public EntityLivingBase getEntity(World world)
	{
		if (this.entity == null) {
			Entity e0 = world.getEntityByID(this.entity_id);
			if (e0 instanceof EntityLivingBase) {
				this.entity = (EntityLivingBase) e0;
			}
		}
		return this.entity;
	}
	
	public void serialize(ByteBuf buf)
	{
		buf.writeByte(this.type.ordinal());
		
		if (this.type == MovingObjectType.BLOCK) {
			buf.writeInt(this.block_x);
			buf.writeInt(this.block_y);
			buf.writeInt(this.block_z);
		} else if (this.type == MovingObjectType.ENTITY) {
			buf.writeInt(this.entity_id);
		}
	}
	
	public void deserialize(ByteBuf buf)
	{
		this.type = MovingObjectType.values()[buf.readByte()];
		
		if (this.type == MovingObjectType.BLOCK) {
			this.block_x = buf.readInt();
			this.block_y = buf.readInt();
			this.block_z = buf.readInt();
		} else if (this.type == MovingObjectType.ENTITY) {
			this.entity_id = buf.readInt();
		}
	}
}
