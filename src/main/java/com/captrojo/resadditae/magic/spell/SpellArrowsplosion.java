package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class SpellArrowsplosion extends Spell
{
	public SpellArrowsplosion(String name)
	{
		super(name, ResAdditae.ident("arrowsplosion"));
		
		this.complexity = MagicComplexity.INTERMEDIATE;
		this.skill_requirement = 20;
		this.mana_requirement = 100;
		
		this.is_instant = true;
	}

	@Override
	public void onActivated(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}

	@Override
	public void onTriggered(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
		for (double pitch_deg = -85; pitch_deg < 85; pitch_deg += 10) {
			for (double yaw_deg = 0; yaw_deg < 360; yaw_deg += 10) {
				double pitch_rad = pitch_deg * Math.PI / 180;
				double yaw_rad = yaw_deg * Math.PI / 180;
				
				double xv = 1 * Math.cos(pitch_rad) * Math.cos(yaw_rad);
				double yv = 1 * Math.sin(pitch_rad);
				double zv = 1 * Math.cos(pitch_rad) * Math.sin(yaw_rad);
				
				EntityArrow arrow = new EntityArrow(world, player, 1f);
				arrow.canBePickedUp = 0;
				arrow.setDamage(30);
				arrow.setVelocity(xv, yv, zv);
				world.spawnEntityInWorld(arrow);
			}
		}
		rpp.useMana(100, true);
	}

	@Override
	public void tickWhileActive(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}

	@Override
	public void onDeactivated(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}
}
