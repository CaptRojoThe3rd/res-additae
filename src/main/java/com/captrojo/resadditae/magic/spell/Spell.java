package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketSpellFeedback;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

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
	
	public abstract void onActivated(World world, EntityPlayer player, RAPlayerProperties rpp);
	
	public abstract void onTriggered(World world, EntityPlayer player, RAPlayerProperties rpp);
	
	public abstract void tickWhileActive(World world, EntityPlayer player, RAPlayerProperties rpp);
	
	public abstract void onDeactivated(World world, EntityPlayer player, RAPlayerProperties rpp);
	
	@SideOnly(Side.CLIENT)
	public abstract void onActivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp);

	@SideOnly(Side.CLIENT)
	public abstract void onTriggeredClient(World world, EntityPlayer player, RAPlayerProperties rpp);

	@SideOnly(Side.CLIENT)
	public abstract void tickWhileActiveClient(World world, EntityPlayer player, RAPlayerProperties rpp);

	@SideOnly(Side.CLIENT)
	public abstract void onDeactivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp);
	
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
		if (complexity_limit == null) {
			return false;
		}
		return complexity_limit.ordinal() >= this.complexity.ordinal();
	}
	
	public boolean canCastSpell(RAPlayerProperties rpp)
	{
		if (!this.isManaRequirementMet(rpp.mana)) {
			return false;
		}
		return true;
	}
	
	/* Tell the client to do something after a spell is triggered on the server. */
	public void sendFeedback(EntityPlayer player, PacketSpellFeedback.Feedback...actions)
	{
		ResAdditae.network.sendTo(new PacketSpellFeedback(actions), (EntityPlayerMP) player);
	}
	
	public final int getID()
	{
		return this.id;
	}
	
	@SideOnly(Side.CLIENT)
	public String getLocalizedName()
	{
		return I18nHlpr.get(this.unlocalized_name + ".name");
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
