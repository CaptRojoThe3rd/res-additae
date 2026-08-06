package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.UseType;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.sounds.ModSounds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SpellBulletTime extends Spell
{
	public SpellBulletTime(String name, String texture_name)
	{
		super(name, texture_name);
		
		this.complexity = MagicComplexity.INTERMEDIATE;
		this.base_skill_requirement = 22;
		this.base_mana_requirement = 1;
		
		this.use_type = UseType.CONTINUOUS;
		this.max_use_time = 72000;
		this.base_cooldown_time = 0;
	}
	
	void modifyVelocity(EntityPlayer player, RAPlayerProperties rpp)
	{
		if (player.motionY <= 0.0) {
			if (player.onGround) {
				return;
			}
			player.motionY = -0.04;
		} else if (player.motionY > 0.0 && rpp.spell_active_time > 3 && rpp.spell_active_time < 15) {
			player.motionY = 1.5 - ((double) rpp.spell_active_time / 10.0);
		}
		player.velocityChanged = true;
	}

	@Override
	public void onActivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
		if (player.onGround) {
			player.addVelocity(0.0, 1.5, 0.0);
			player.velocityChanged = true;
		}
		world.playSoundAtEntity(player, ModSounds.SPELL_BULLET_TIME_ACTIVATE, 1.0f, 1.0f);
		rpp.onSpellUsed(idx, 0, 0);
	}

	@Override
	public void onTriggered(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}

	@Override
	public void tickWhileActive(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
		if (player.motionY <= 0.0) {
			if (rpp.useMana(this.base_mana_requirement, true) != this.base_mana_requirement || player.onGround) {
				rpp.deactivateSpell(idx);
				return;
			}
		}
		this.modifyVelocity(player, rpp);
		
		int resist = Math.min((spell.proficiency + 5) / 10, 5);
		if (resist > 0) {
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 40, resist - 1, true));
		}
	}

	@Override
	public void onDeactivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onActivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
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
		this.modifyVelocity(player, rpp);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDeactivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}
}
