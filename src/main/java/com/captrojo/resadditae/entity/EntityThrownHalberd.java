package com.captrojo.resadditae.entity;

import java.util.List;

import com.captrojo.resadditae.item.ModItems;
import com.captrojo.resadditae.item.equipment.ItemHalberd;
import com.captrojo.resadditae.main.ModDamageSources;
import com.captrojo.resadditae.sounds.ModSounds;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityThrownHalberd extends EntityArrow
{
	public static final int DW_HALBERD_TYPE = 17;

	protected boolean has_halberd_item;
	protected ItemStack halberd_item;

	protected boolean damaged;

	private int stuck_x = -1;
	private int stuck_y = -1;
	private int stuck_z = -1;
	private Block stuck_block;
	private int stuck_data;
	
	private boolean in_ground;
	private int ticks_in_ground;
	private int ticks_in_air;
	
	private double damage = 2.0d;
	private int knockback_strength;

	public EntityThrownHalberd(World world)
	{
		super(world);
		this.setSize(1f, 1f);

		this.has_halberd_item = false;
		this.canBePickedUp = 0;
	}

	public EntityThrownHalberd(World world, ItemStack stack, EntityLivingBase entity)
	{
		super(world, entity, 1f);
		this.setSize(1f, 1f);

		this.halberd_item = stack;
		this.has_halberd_item = true;
		this.canBePickedUp = 1;
		this.damaged = false;

		ItemHalberd item = (ItemHalberd) stack.getItem();
		this.damage = item.attack_damage * 0.8d;

		//this.setPosition(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
		this.setLocationAndAngles(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ, entity.rotationYaw, entity.rotationPitch);
		this.motionX = (2d / item.weight) * (double) (-MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.motionZ = (2d / item.weight) * (double) (MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI));
		this.motionY = (2d / item.weight) * (double) (-MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI));
	}

	private static byte getHalberdTypeFromItem(Item item)
	{
		if (item == ModItems.wood_halberd) {
			return 0;
		}
		if (item == ModItems.stone_halberd) {
			return 1;
		}
		if (item == ModItems.iron_halberd) {
			return 2;
		}
		if (item == ModItems.gold_halberd) {
			return 3;
		}
		if (item == ModItems.diamond_halberd) {
			return 4;
		}
		if (item == ModItems.silver_halberd) {
			return 5;
		}
		if (item == ModItems.platinum_halberd) {
			return 6;
		}
		return 2;
	}

	public boolean isStuckInGround()
	{
		return this.in_ground;
	}

	public ItemStack getItemStack()
	{
		return this.halberd_item;
	}

	public byte getHalberdTypeForRender()
	{
		return this.dataWatcher.getWatchableObjectByte(DW_HALBERD_TYPE);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(DW_HALBERD_TYPE, (byte) 0);
	}

	@Override
	public void setDead()
	{
		if (this.posY < -64d || this.canBePickedUp != 1) {
			this.isDead = true;
		} else if (this.halberd_item.attemptDamageItem(1, this.rand)) {
			this.setActuallyDead();
		}
	}

	public void setActuallyDead()
	{
		this.isDead = true;
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.in_ground && !this.damaged && this.has_halberd_item) {
			if (this.halberd_item.attemptDamageItem(1, this.rand)) {
				this.setActuallyDead();
			}
			this.damaged = true;
		} else if (!this.in_ground) {
			this.damaged = false;
		}

		if (this.has_halberd_item) {
			this.dataWatcher.updateObject(DW_HALBERD_TYPE, getHalberdTypeFromItem(this.halberd_item.getItem()));
		}

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f) * 180.0D / Math.PI);
		}

		Block block = this.worldObj.getBlock(this.stuck_x, this.stuck_y, this.stuck_z);

		if (block.getMaterial() != Material.air) {
			block.setBlockBoundsBasedOnState(this.worldObj, this.stuck_x, this.stuck_y, this.stuck_z);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.stuck_x, this.stuck_y, this.stuck_z);

			if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
				this.in_ground = true;
			}
		}

		if (this.in_ground) {
			int j = this.worldObj.getBlockMetadata(this.stuck_x, this.stuck_y, this.stuck_z);

			if (block == this.stuck_block && j == this.stuck_data) {
				this.ticks_in_ground++;
			} else {
				this.in_ground = false;
				this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
				this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
				this.ticks_in_ground = 0;
				this.ticks_in_air = 0;
			}
		} else {
			++this.ticks_in_air;
			Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
			vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (movingobjectposition != null) {
				vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
			}

			Entity entity = null;
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
			double d0 = 0.0D;
			int i;
			float f1;

			for (i = 0; i < list.size(); ++i) {
				Entity entity1 = (Entity) list.get(i);

				if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticks_in_air >= 5)) {
					f1 = 0.3F;
					AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double) f1, (double) f1, (double) f1);
					MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

					if (movingobjectposition1 != null) {
						double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

						if (d1 < d0 || d0 == 0.0D) {
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new MovingObjectPosition(entity);
			}

			if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

				if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
					movingobjectposition = null;
				}
			}

			float f2;
			float f4;

			if (movingobjectposition != null) {
				if (movingobjectposition.entityHit != null) {
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					int k = MathHelper.ceiling_double_int((double) f2 * this.damage);

					if (this.getIsCritical()) {
						k += this.rand.nextInt(k / 2 + 2);
					}

					DamageSource damagesource = null;

					if (this.shootingEntity == null) {
						damagesource = ModDamageSources.causeThrownHalberdDamage(this, this);
					} else {
						damagesource = ModDamageSources.causeThrownHalberdDamage(this, this.shootingEntity);
					}

					if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman)) {
						movingobjectposition.entityHit.setFire(5);
					}

					if (movingobjectposition.entityHit.attackEntityFrom(damagesource, (float) k)) {
						if (movingobjectposition.entityHit instanceof EntityLivingBase) {
							EntityLivingBase entitylivingbase = (EntityLivingBase) movingobjectposition.entityHit;

							if (this.knockback_strength > 0) {
								f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

								if (f4 > 0.0F) {
									movingobjectposition.entityHit.addVelocity(this.motionX * (double) this.knockback_strength * 0.6000000238418579D / (double) f4, 0.1D, this.motionZ * (double) this.knockback_strength * 0.6000000238418579D / (double) f4);
								}
							}

							if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase) {
								EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity);
								EnchantmentHelper.func_151385_b((EntityLivingBase) this.shootingEntity, entitylivingbase);
							}

							if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
								((EntityPlayerMP) this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
							}
						}

						this.playSound(ModSounds.THROWN_HALBERD_IMPACT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

						if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
							this.setDead();
						}
					} else {
						this.motionX *= -0.1;
						this.motionY *= -0.1;
						this.motionZ *= -0.1;
						this.rotationYaw += 180.0F;
						this.prevRotationYaw += 180.0F;
						this.ticks_in_air = 0;
					}
				} else {
					this.stuck_x = movingobjectposition.blockX;
					this.stuck_y = movingobjectposition.blockY;
					this.stuck_z = movingobjectposition.blockZ;
					this.stuck_block = this.worldObj.getBlock(this.stuck_x, this.stuck_y, this.stuck_z);
					this.stuck_data = this.worldObj.getBlockMetadata(this.stuck_x, this.stuck_y, this.stuck_z);
					this.motionX = movingobjectposition.hitVec.xCoord - this.posX;
					this.motionY = movingobjectposition.hitVec.yCoord - this.posY;
					this.motionZ = movingobjectposition.hitVec.zCoord - this.posZ;
					f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
					this.posX -= this.motionX / (double) f2 * 0.05;
					this.posY -= this.motionY / (double) f2 * 0.05;
					this.posZ -= this.motionZ / (double) f2 * 0.05;
					this.playSound(ModSounds.THROWN_HALBERD_IMPACT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.in_ground = true;
					this.setIsCritical(false);

					if (this.stuck_block.getMaterial() != Material.air) {
						this.stuck_block.onEntityCollidedWithBlock(this.worldObj, this.stuck_x, this.stuck_y, this.stuck_z, this);
					}
				}
			}

			if (this.getIsCritical()) {
				for (i = 0; i < 4; ++i) {
					this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double) i / 4.0D, this.posY + this.motionY * (double) i / 4.0D, this.posZ + this.motionZ * (double) i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
				}
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

			for (
				this.rotationPitch = (float) (Math.atan2(this.motionY, (double) f2) * 180.0D / Math.PI);
				this.rotationPitch - this.prevRotationPitch < -180.0F;
				this.prevRotationPitch -= 360.0F
			);

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f3 = 0.99F;
			f1 = 0.05F;

			if (this.isInWater()) {
				for (int l = 0; l < 4; ++l) {
					f4 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) f4, this.posY - this.motionY * (double) f4, this.posZ - this.motionZ * (double) f4, this.motionX, this.motionY, this.motionZ);
				}

				f3 = 0.8F;
			}

			if (this.isWet()) {
				this.extinguish();
			}

			this.motionX *= (double) f3;
			this.motionY *= (double) f3;
			this.motionZ *= (double) f3;
			this.motionY -= (double) f1;
			this.setPosition(this.posX, this.posY, this.posZ);
			this.func_145775_I();
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		this.has_halberd_item = tag.getBoolean("has_halberd_item");
		if (this.has_halberd_item) {
			this.halberd_item = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("halberd_item"));
			this.canBePickedUp = 1;
		} else {
			this.canBePickedUp = 0;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		tag.setBoolean("has_halberd_item", this.has_halberd_item);
		if (this.has_halberd_item) {
			tag.setTag("halberd_item", this.halberd_item.writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player)
	{
		if (!this.worldObj.isRemote && this.in_ground) {
			boolean flag = this.canBePickedUp == 1 && this.ticks_in_ground > 20;
			if (!flag) {
				return;
			}
			if (player.inventory.addItemStackToInventory(this.halberd_item)) {
				this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				player.onItemPickup(this, 1);
				this.setActuallyDead();
			}
		}
	}
}
