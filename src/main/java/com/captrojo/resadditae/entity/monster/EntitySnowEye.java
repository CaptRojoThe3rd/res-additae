package com.captrojo.resadditae.entity.monster;

import java.util.List;

import com.captrojo.resadditae.entity.client.EntityModFX;
import com.captrojo.resadditae.entity.client.EntityZapFX;
import com.captrojo.resadditae.main.ModDamageSources;
import com.captrojo.resadditae.sounds.ModSounds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySnowEye extends EntityMob
{
	public static final int DW_TARGET = 12;
	public static final int DW_ATTACK_FX_FLAG = 13;
	
	public static final AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0);
	public static final IEntitySelector selector = new IEntitySelector() {
		@Override
		public boolean isEntityApplicable(Entity entity)
		{
			if (entity instanceof EntityPlayerMP) {
				return !((EntityPlayerMP) entity).theItemInWorldManager.isCreative();
			}
			if (entity instanceof EntityAgeable) {
				return true;
			}
			if (entity instanceof EntityGolem) {
				return true;
			}
			if (entity instanceof EntityCreeper) {
				EntityCreeper creeper = (EntityCreeper) entity;
				return !creeper.getPowered();
			}
			return false;
		}
	};
	
	@SideOnly(Side.CLIENT)
	private byte prev_attack_fx_flag;
	
	public EntitySnowEye(World world)
	{
		super(world);
		
		this.setSize(1.0f, 1.0f);
		this.attackTime = this.rand.nextInt(10);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateClient()
	{
		/* Don't ask me why this is necessary. I walked back through the rendering code until I found
		 * that this variable wasn't being updated with the entity's yaw.
		 */
		this.renderYawOffset = this.rotationYaw;
		
		this.entityToAttack = this.dwGetTarget();
		
		if (this.entityToAttack != null) {
			byte attack_fx_flag = this.dwGetAttackFXFlag();
			if (attack_fx_flag != this.prev_attack_fx_flag) {
				this.prev_attack_fx_flag = attack_fx_flag;
				this.createLaserFX();
			}
		} else {
			this.rotationYaw += 2.0;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void createLaserFX()
	{
		Vec3 vec1 = Vec3.createVectorHelper(this.entityToAttack.posX, this.entityToAttack.posY, this.entityToAttack.posZ);
		vec1.yCoord += this.entityToAttack.getEyeHeight();
		if (this.entityToAttack instanceof EntityPlayer) {
			vec1.yCoord -= 0.25;
		}
		Vec3 vec2 = Vec3.createVectorHelper(this.posX, this.posY + 0.5, this.posZ);
		Vec3 vec3 = vec1.subtract(vec2);
		vec3 = vec3.normalize();
		vec3.xCoord = vec3.xCoord * -0.2;
		vec3.yCoord = vec3.yCoord * -0.2;
		vec3.zCoord = vec3.zCoord * -0.2;
		double dist = vec1.distanceTo(vec2);
		for (double d = 0; d < dist; d += 0.2) {
			EntityModFX.spawn(new EntityZapFX(this.worldObj, vec2.xCoord, vec2.yCoord, vec2.zCoord));
			vec2.xCoord += vec3.xCoord;
			vec2.yCoord += vec3.yCoord;
			vec2.zCoord += vec3.zCoord;
		}
	}
	
	public Entity getEntityToAttack()
	{
		EntitySnowEye.aabb.minX = this.posX - 16.0;
		EntitySnowEye.aabb.minY = this.posY - 16.0;
		EntitySnowEye.aabb.minZ = this.posZ - 16.0;
		EntitySnowEye.aabb.maxX = this.posX + 16.0;
		EntitySnowEye.aabb.maxY = this.posY + 16.0;
		EntitySnowEye.aabb.maxZ = this.posZ + 16.0;
		
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, EntitySnowEye.aabb, EntitySnowEye.selector);
		Entity closest = null;
		double dist = 999.0;
		for (Object o : list) {
			Entity e = (Entity) o;
			double d = e.getDistanceToEntity(this);
			if (d < dist) {
				dist = d;
				closest = e;
			}
		}
		
		return closest;
	}
	
	public void updateAttackTarget()
	{
		if (this.entityToAttack == null) {
			this.entityToAttack = this.getEntityToAttack();
		}

		this.dwSetTarget(this.entityToAttack);
		if (this.entityToAttack == null) {
			this.rotationYaw += 2.0;
			return;
		}
		
		if (!this.entityToAttack.isEntityAlive()) {
			this.entityToAttack = null;
		} else if (this.getDistanceToEntity(this.entityToAttack) > 32.0) {
			this.entityToAttack = null;
		} else if (this.entityToAttack instanceof EntityPlayerMP) {
			if (((EntityPlayerMP) this.entityToAttack).theItemInWorldManager.isCreative()) {
				this.entityToAttack = null;
			}
		}
		
		byte attack_fx_flag = this.dwGetAttackFXFlag();
		
		if (this.entityToAttack != null) {
			if (this.attackTime <= 0) {
				if (this.attackEntityAsMob(this.entityToAttack)) {
					attack_fx_flag++;
				}
			}
		}
		
		this.dwSetAttackFXFlag(attack_fx_flag);
	}
	
	protected Entity dwGetTarget()
	{
		int id = this.dataWatcher.getWatchableObjectInt(EntitySnowEye.DW_TARGET);
		return (id == -1) ? null : this.worldObj.getEntityByID(id);
	}
	
	protected void dwSetTarget(Entity entity)
	{
		int id = (entity == null) ? -1 : entity.getEntityId();
		this.dataWatcher.updateObject(EntitySnowEye.DW_TARGET, id);
	}
	
	protected byte dwGetAttackFXFlag()
	{
		return this.dataWatcher.getWatchableObjectByte(EntitySnowEye.DW_ATTACK_FX_FLAG);
	}
	
	protected void dwSetAttackFXFlag(byte b)
	{
		this.dataWatcher.updateObject(EntitySnowEye.DW_ATTACK_FX_FLAG, b);
	}
	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(EntitySnowEye.DW_TARGET, -1);
		this.dataWatcher.addObject(EntitySnowEye.DW_ATTACK_FX_FLAG, (byte) 0);
	}
	
	@Override
	public void onLivingUpdate()
	{
		if (this.worldObj.isRemote) {
			this.updateClient();
		}
		
		this.motionY = 0.0;

		super.onLivingUpdate();
	}
	
	@Override
	protected void updateEntityActionState()
	{
		this.updateAttackTarget();
		
		if (this.entityToAttack != null) {
			this.faceEntity(this.entityToAttack, 100.0f, 100.0f);
			double xdist = this.posX - this.entityToAttack.posX;
			double zdist = this.posZ - this.entityToAttack.posZ;
			double dist = Math.sqrt(xdist * xdist + zdist * zdist);
			double angle = Math.atan2(dist, this.posY - this.entityToAttack.posY);
			this.rotationPitch = (float) (angle * 180 / Math.PI);
		} else {
			this.rotationPitch = 90.0f;
		}
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity)
	{
		this.attackTime = 10;
		if (entity instanceof EntityCreeper) {
			entity.onStruckByLightning(null);
			this.entityToAttack = null;
			return true;
		}
		float pitch = 1.0f + (rand.nextFloat() * 0.4f) - 0.2f;
		this.worldObj.playSoundAtEntity(this, ModSounds.SNOW_EYE_ATTACK, 1.0f, pitch);
		this.worldObj.playSoundAtEntity(this.entityToAttack, ModSounds.SNOW_EYE_ATTACK, 1.0f, pitch);
		return entity.attackEntityFrom(ModDamageSources.causeIndirectMagicDamageAA(this.entityToAttack, this), 6.0f);
	}
	
	@Override
	public boolean isAIEnabled()
	{
		return false;
	}

	@Override
	protected String getLivingSound()
	{
		return null;
	}

	@Override
	protected String getHurtSound()
	{
		return ModSounds.SNOW_EYE_HIT;
	}

	@Override
	protected String getDeathSound()
	{
		return ModSounds.SNOW_EYE_HIT;
	}
}
