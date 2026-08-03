package com.captrojo.resadditae.magic;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.Alerts;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toclient.PacketDisplayAlert;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class LearnedSpell implements Comparable<LearnedSpell>
{	
	public Spell spell;
	
	public int proficiency;
	public int proficiency_points;
	
	public LearnedSpell()
	{
	}
	
	public LearnedSpell(Spell spell)
	{
		this.proficiency = 1;
		this.spell = spell;
	}
	
	public void onSpellUsed(RAPlayerProperties rpp)
	{
		this.proficiency_points++;
		if (this.proficiency_points == this.getProfPtRequirement()) {
			this.proficiency_points = 0;
			this.proficiency++;
			this.spell.sendAlert(rpp.player, Alerts.SPELL_PROF_LVLUP);
		}
	}
	
	public float getProfPtProgress()
	{
		return (float) this.proficiency_points / (float) this.getProfPtRequirement();
	}
	
	public int getProfPtRequirement()
	{
		return 10 * this.proficiency;
	}
	
	public void serialize(ByteBuf buf)
	{
		buf.writeInt(this.spell.getID());
		
		buf.writeByte(this.proficiency);
		buf.writeInt(this.proficiency_points);
	}
	
	public void deserialize(ByteBuf buf)
	{
		this.spell = Spells.getByID(buf.readInt());
		
		this.proficiency = buf.readByte();
		this.proficiency_points = buf.readInt();
	}
	
	public void saveToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("spell", this.spell.getID());
		
		nbt.setByte("prof", (byte) this.proficiency);
		nbt.setInteger("prof_pts", this.proficiency_points);
	}
	
	public void loadFromNBT(NBTTagCompound nbt)
	{
		this.spell = Spells.getByID(nbt.getInteger("spell"));
		
		this.proficiency = nbt.getByte("prof");
		this.proficiency_points = nbt.getInteger("prof_pts");
	}

	@Override
	public int compareTo(LearnedSpell o)
	{
		if (o == null) {
			return Integer.MAX_VALUE;
		}
		return this.spell.getLocalizedName().compareTo(o.spell.getLocalizedName());
	}
}
