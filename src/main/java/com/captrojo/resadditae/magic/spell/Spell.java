package com.captrojo.resadditae.magic.spell;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.UseType;
import com.captrojo.resadditae.main.Alerts;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketDisplayAlert;
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
	/* Just put something random here tbh */
	public static final int WHATEVER_RANGE = 32;
	
	int id;
	
	public final String unlocalized_name;
	public final String texture_name;
	protected IIcon icon;
	
	public MagicComplexity complexity;
	public int base_skill_requirement;
	public int base_mana_requirement;
	
	public UseType use_type;
	public int max_use_time;
	public int base_cooldown_time;
	
	public Spell(String name, String texture_name)
	{
		this.unlocalized_name = "spell." + name;
		this.texture_name = texture_name;
	}
	
	/* Called when the spell is activated with its slot's keybind */
	public abstract void onActivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx);
	
	/* Called when the spell is triggered, either with its slot's keybind (if the spell is
	 * an instant-use spell), or with the spell trigger keybind after the spell has been
	 * activated
	 */
	public abstract void onTriggered(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx);
	
	/* Called every tick while the spell is active */
	public abstract void tickWhileActive(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx);
	
	/* Called when the spell is deactivated, either by the user deactivating it with the spell
	 * slot's keybind, or by selecting a different spell
	 */
	public abstract void onDeactivated(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx);
	
	@SideOnly(Side.CLIENT)
	public abstract void onActivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx);

	@SideOnly(Side.CLIENT)
	public abstract void onTriggeredClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx);

	@SideOnly(Side.CLIENT)
	public abstract void tickWhileActiveClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx);

	@SideOnly(Side.CLIENT)
	public abstract void onDeactivatedClient(World world, EntityPlayer player, RAPlayerProperties rpp, LearnedSpell spell, int idx);
	
	/* Get the mana requirement for the spell, adjusting for any other properties.
	 * `spell` may be null.
	 */
	public int getManaRequirement(RAPlayerProperties rpp, LearnedSpell spell)
	{
		return this.base_mana_requirement;
	}
	
	/* Get the skill requirement for the spell, adjusting for any other properties.
	 * `spell` may be null.
	 */
	public int getSkillRequirement(RAPlayerProperties rpp, LearnedSpell spell)
	{
		return this.base_skill_requirement;
	}
	
	/* Get the cooldown time for the spell, adjusting for any other properties.
	 * `spell` may be null.
	 */
	public int getCooldownTime(RAPlayerProperties rpp, LearnedSpell spell)
	{
		return this.base_cooldown_time;
	}
	
	public boolean isManaRequirementMet(RAPlayerProperties rpp, LearnedSpell spell)
	{
		return rpp.mana >= this.getManaRequirement(rpp, spell);
	}
	
	public boolean isSkillRequirementMet(RAPlayerProperties rpp, LearnedSpell spell)
	{
		return rpp.magic_skill_level >= this.getSkillRequirement(rpp, spell);
	}
	
	public boolean isPowerRequirementMet(MagicComplexity complexity_limit)
	{
		if (complexity_limit == null) {
			return false;
		}
		return complexity_limit.ordinal() >= this.complexity.ordinal();
	}
	
	/* Tell the client to do something after a spell is triggered on the server. */
	public void sendFeedback(EntityPlayer player, int idx, PacketSpellFeedback.Feedback...actions)
	{
		ResAdditae.network.sendTo(new PacketSpellFeedback(idx, actions), (EntityPlayerMP) player);
	}
	
	public void sendAlert(EntityPlayer player, Alerts alert)
	{
		ResAdditae.network.sendTo(new PacketDisplayAlert(alert, this.getID()), (EntityPlayerMP) player);
	}
	
	public void sendAlert(EntityPlayer player, Alerts alert, Object a, Object b)
	{
		ResAdditae.network.sendTo(new PacketDisplayAlert(alert, this.getID(), a, b), (EntityPlayerMP) player);
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
