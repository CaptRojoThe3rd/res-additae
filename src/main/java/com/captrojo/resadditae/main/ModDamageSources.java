package com.captrojo.resadditae.main;

import com.captrojo.resadditae.entity.EntityThrownHalberd;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class ModDamageSources
{
	public static DamageSource causeThrownHalberdDamage(EntityThrownHalberd halberd, Entity attacker)
	{
		return new EntityDamageSourceIndirect("thrown_halberd", halberd, attacker).setProjectile();
	}
	
	public static DamageSource causeMagicLifestealDamage(Entity attacker)
	{
		return new EntityDamageSource("magic_lifesteal", attacker);
	}
}
