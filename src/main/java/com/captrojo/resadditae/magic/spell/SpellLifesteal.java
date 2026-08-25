package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.UseType;
import com.captrojo.resadditae.main.Alerts;
import com.captrojo.resadditae.main.ModDamageSources;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketSpellFeedback.Feedback;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class SpellLifesteal extends Spell
{
	public SpellLifesteal(String name, String texture_name)
	{
		super(name, texture_name);
		
		this.complexity = MagicComplexity.BASIC;
		this.base_skill_requirement = 13;
		this.base_mana_requirement = 50;
		
		this.use_type = UseType.TRIGGER;
		this.max_use_time = 72000;
		this.base_cooldown_time = 20 * 5;
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
		
		float old_health = player.getHealth();
		player.heal(4.0f);
		float delta_health = player.getHealth() - old_health;
		
		if (delta_health > 0.0f) {
			target.attackEntityFrom(ModDamageSources.causeMagicLifestealDamage(player), delta_health);
			rpp.useMana(this.base_mana_requirement, true);
			rpp.onSpellUsed(idx, this.base_cooldown_time, 20);
			this.sendAlert(player, Alerts.LIFESTEAL_USED, delta_health, target.getCommandSenderName());
		} else {
			this.sendAlert(player, Alerts.HEALTH_ALREADY_FULL);
		}
		
		if (player.getHealth() >= player.getMaxHealth()) {
			this.sendFeedback(player, idx, Feedback.DEACTIVATE);
			rpp.deactivateSpell(idx);
		}
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
