package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.main.Alerts;
import com.captrojo.resadditae.main.ModDamageSources;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketSpellFeedback.Feedback;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SpellLifesteal extends Spell
{
	public SpellLifesteal(String name)
	{
		super(name, ResAdditae.ident("lifesteal"));
		
		this.complexity = MagicComplexity.BASIC;
		this.base_skill_requirement = 20;
		this.base_mana_requirement = 50;
		
		this.is_instant = false;
		this.max_use_time = 72000;
		this.base_cooldown_time = 100;
	}

	@Override
	public void onActivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell)
	{
	}

	@Override
	public void onTriggered(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell)
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
			rpp.onSpellUsed(this.base_cooldown_time);
			this.sendAlert(player, Alerts.LIFESTEAL_USED, delta_health, target.getCommandSenderName());
		} else {
			this.sendAlert(player, Alerts.HEALTH_ALREADY_FULL);
		}
		
		if (player.getHealth() >= player.getMaxHealth()) {
			this.sendFeedback(player, Feedback.DEACTIVATE);
			rpp.deactivateSpell();
		}
	}

	@Override
	public void tickWhileActive(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell)
	{
	}

	@Override
	public void onDeactivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onActivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell)
	{
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void tickWhileActiveClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell)
	{
		rpp.spell_target.updateTarget(world, player, 32);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onTriggeredClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDeactivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell)
	{
		rpp.spell_target.removeTarget();
	}
}
