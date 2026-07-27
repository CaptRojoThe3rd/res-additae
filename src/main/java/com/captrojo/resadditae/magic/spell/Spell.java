package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.magic.MagicComplexity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public abstract class Spell
{
	int id;
	
	public final String unlocalized_name;
	public final String texture_name;
	protected IIcon icon;
	
	public MagicComplexity complexity;
	public int skill_requirement;
	public int mana_requirement;
	
	public boolean is_instant;
	public int max_use_time;
	
	public Spell(String name, String texture_name)
	{
		this.unlocalized_name = "spell." + name;
		this.texture_name = texture_name;
	}
	
	public abstract void onActivated();
	
	public boolean isManaRequirementMet(int available_mana)
	{
		return available_mana >= this.mana_requirement;
	}
	
	public boolean isSkillRequirementMet(int skill_level)
	{
		return skill_level >= this.skill_requirement;
	}
	
	public boolean isComplexityRequirementMet(MagicComplexity complexity_limit)
	{
		return complexity_limit.ordinal() >= this.complexity.ordinal();
	}
	
	public boolean canCastSpell(RAPlayerProperties rpp)
	{
		if (!this.isManaRequirementMet(rpp.mana)) {
			return false;
		}
		return true;
	}
	
	public final int getID()
	{
		return this.id;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcon(IIconRegister reg)
	{
		this.icon = reg.registerIcon(this.texture_name);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon()
	{
		return this.icon;
	}
}
