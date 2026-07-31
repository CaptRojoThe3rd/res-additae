package com.captrojo.resadditae.magic;

import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class LearnedSpell
{
	public Spell spell;
	public int level;
	
	public int proficiency;
	public int proficiency_points;
	
	public LearnedSpell()
	{
	}
	
	public LearnedSpell(Spell spell)
	{
		this.spell = spell;
	}
	
	public void onSpellUsed()
	{
	}
	
	public void serialize(ByteBuf buf)
	{
		buf.writeInt(this.spell.getID());
		buf.writeByte(this.level);
		
		buf.writeByte(this.proficiency);
		buf.writeInt(this.proficiency_points);
	}
	
	public void deserialize(ByteBuf buf)
	{
		this.spell = Spells.getByID(buf.readInt());
		this.level = buf.readByte();
		
		this.proficiency = buf.readByte();
		this.proficiency_points = buf.readInt();
	}
	
	public void saveToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("spell", this.spell.getID());
		nbt.setByte("level", (byte) this.level);
		
		nbt.setByte("proficiency", (byte) this.proficiency);
		nbt.setInteger("proficiency_pts", this.proficiency_points);
	}
	
	public void loadFromNBT(NBTTagCompound nbt)
	{
		this.spell = Spells.getByID(nbt.getInteger("spell"));
		this.level = nbt.getByte("level");
		
		this.proficiency = nbt.getByte("proficiency");
		this.proficiency_points = nbt.getInteger("proficiency_pts");
	}
}
