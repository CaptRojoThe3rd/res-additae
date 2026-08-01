package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SpellDebug extends Spell
{
	public SpellDebug(String name)
	{
		super(name, ResAdditae.ident("debug_rainbow"));
		
		this.complexity = MagicComplexity.BEGINNER;
		this.skill_requirement = 0;
		this.mana_requirement = 10;
		
		this.is_instant = false;
		this.max_use_time = 72000;
	}

	@Override
	public void onActivated(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
		player.addChatMessage(I18nHlpr.chat("Activated: " + this.unlocalized_name));
	}

	@Override
	public void onTriggered(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
		player.addChatMessage(I18nHlpr.chat("Triggered: " + this.unlocalized_name));
		rpp.useMana(25, true);
	}

	@Override
	public void tickWhileActive(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}

	@Override
	public void onDeactivated(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
		player.addChatMessage(I18nHlpr.chat("Deactivated: " + this.unlocalized_name));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onActivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onTriggeredClient(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void tickWhileActiveClient(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDeactivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp)
	{
	}
}
