package com.captrojo.resadditae.main;

import com.captrojo.resadditae.entity.EntityThrownHalberd;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class ModDamageSource extends DamageSource
{
	public static DamageSource causeThrownHalberdDamage(EntityThrownHalberd halberd, Entity entity)
	{
		return new EntityDamageSourceIndirect("thrown_halberd", halberd, entity).setProjectile();
	}
	
	public ModDamageSource(String name)
	{
		super(name);
	}
}
