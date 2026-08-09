package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.UseType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SpellHop extends Spell
{
	public SpellHop(String name, String texture_name)
	{
		super(name, texture_name);
		
		this.complexity = MagicComplexity.BEGINNER;
		this.base_skill_requirement = 1;
		this.base_mana_requirement = 15;
		
		this.use_type = UseType.INSTANT;
		this.base_cooldown_time = 20 * 3;
	}

	@Override
	public void onActivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}

	@Override
	public void onTriggered(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
		if (!player.onGround) {
			return;
		}
		player.addVelocity(0.0, 1.0, 0.0);
		player.velocityChanged = true;
		rpp.onSpellUsed(idx, this.base_cooldown_time, 2);
		rpp.useMana(this.base_mana_requirement, true);
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
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDeactivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx)
	{
	}
}
