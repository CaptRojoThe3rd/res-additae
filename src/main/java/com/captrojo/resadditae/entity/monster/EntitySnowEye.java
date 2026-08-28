package com.captrojo.resadditae.entity.monster;

import com.captrojo.resadditae.entity.client.EntityModFX;
import com.captrojo.resadditae.entity.client.EntityZapFX;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySnowEye extends EntityMob
{
	public static final int DW_TARGET = 12;
	public static final int DW_ATTACK_FX_FLAG = 13;
	
	@SideOnly(Side.CLIENT)
	private byte prev_attack_fx_flag = 0;
	
	public EntitySnowEye(World world)
	{
		super(world);
		
		this.setSize(1.0f, 1.0f);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateClient()
	{
		/* Don't ask me why this is necessary. I walked back through the rendering code until I found
		 * that this variable wasn't being updated with the entity's yaw.
		 */
		this.renderYawOffset = this.rotationYaw;
		
		int id = this.dataWatcher.getWatchableObjectInt(EntitySnowEye.DW_TARGET);
		if (id != -1) {
			this.entityToAttack = this.worldObj.getEntityByID(id);
		} else {
			this.entityToAttack = null;
		}
		
		if (this.entityToAttack != null) {
			byte attack_fx_flag = this.dataWatcher.getWatchableObjectByte(EntitySnowEye.DW_ATTACK_FX_FLAG);
			if (attack_fx_flag != this.prev_attack_fx_flag) {
				this.prev_attack_fx_flag = attack_fx_flag;
				this.createLaserFX();
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void createLaserFX()
	{
		Vec3 vec1 = Vec3.createVectorHelper(this.entityToAttack.posX, this.entityToAttack.posY - 0.25, this.entityToAttack.posZ);
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
	
	public void updateAttackTarget()
	{
		if (this.entityToAttack == null) {
			this.entityToAttack = this.worldObj.getClosestPlayerToEntity(this, 16.0);
		}
		
		if (this.entityToAttack != null) {
			this.dataWatcher.updateObject(EntitySnowEye.DW_TARGET, this.entityToAttack.getEntityId());
		} else {
			this.dataWatcher.updateObject(EntitySnowEye.DW_TARGET, -1);
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
		
		byte attack_fx_flag = this.dataWatcher.getWatchableObjectByte(EntitySnowEye.DW_ATTACK_FX_FLAG);
		
		if (this.entityToAttack != null) {
			if (this.attackTime <= 0) {
				if (this.attackEntityAsMob(this.entityToAttack)) {
					attack_fx_flag++;
				}
			}
		}
		
		this.dataWatcher.updateObject(EntitySnowEye.DW_ATTACK_FX_FLAG, attack_fx_flag);
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
		super.onLivingUpdate();
		
		this.setVelocity(0, 0, 0);
		
		if (this.worldObj.isRemote) {
			this.updateClient();
		}
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
		return entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.entityToAttack, this), 4.0f);
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
		return "resadditae:mob.snow_eye.hit";
	}

	@Override
	protected String getDeathSound()
	{
		return "resadditae:mob.snow_eye.death";
	}
}
