package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.UseType;
import com.captrojo.resadditae.main.Alerts;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class SpellHalt extends Spell
{
	public SpellHalt(String name, String texture_name)
	{
		super(name, texture_name);

		this.complexity = MagicComplexity.BEGINNER;
		this.base_skill_requirement = 5;
		this.base_mana_requirement = 25;
		
		this.use_type = UseType.TRIGGER;
		this.max_use_time = 72000;
		this.base_cooldown_time = 20 * 8;
	}

	@Override
	public void onActivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}

	@Override
	public void onTriggered(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
		EntityLivingBase target = rpp.spell_target.getEntity(world);
		if (target == null) {
			this.sendAlert(player, Alerts.NO_TARGET);
			return;
		}
		
		target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20 * 10, 4));
		target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(target, player), 1.0f);
		
		rpp.useMana(this.base_mana_requirement, true);
		rpp.onSpellUsed(idx, this.base_cooldown_time, 20);
	}

	@Override
	public void tickWhileActive(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}

	@Override
	public void onDeactivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onActivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
		rpp.spell_target.req_target = MovingObjectType.ENTITY;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onTriggeredClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void tickWhileActiveClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
		rpp.spell_target.updateTarget(world, player, WHATEVER_RANGE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDeactivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
		rpp.spell_target.removeTarget();
	}
}
