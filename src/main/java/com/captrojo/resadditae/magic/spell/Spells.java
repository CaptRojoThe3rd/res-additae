package com.captrojo.resadditae.magic.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.texture.IIconRegister;

public class Spells
{
	public static final Spell[][] SPELL_ARRAY = new Spell[0x10000][];
	public static final List<Spell> SPELL_LIST = new ArrayList<Spell>();
	private static int _current_hi_word;
	
	public static Spell arrowsplosion = new SpellArrowsplosion("arrowsplosion", ResAdditae.ident("arrowsplosion"));
	public static Spell lifesteal = new SpellLifesteal("lifesteal", ResAdditae.ident("lifesteal"));
	public static Spell bullet_time = new SpellBulletTime("bullet_time", ResAdditae.ident("bullet_time"));
	public static Spell avada_kedavra = new SpellAvadaKedavra("avada_kedavra", ResAdditae.ident("avada_kedavra"));
	
	public static void init()
	{
		_current_hi_word = 0x0000;
		
		registerSpell(0x0000, arrowsplosion);
		registerSpell(0x0001, lifesteal);
		registerSpell(0x0002, bullet_time);
		registerSpell(0x0003, avada_kedavra);
	}
	
	public static void setSpellHiWord(int id)
	{
		if (id < 0x10000) {
			_current_hi_word = id;
		} else {
			_current_hi_word = id >> 16;
		}
	}
	
	public static Spell registerSpell(int id, Spell spell)
	{
		return registerSpellL(id | (_current_hi_word << 16), spell);
	}
	
	public static Spell registerSpellL(int id, Spell spell)
	{
		int hi_word = id >> 16;
		int lo_word = id & 0xffff;
		
		Spell[] arr = SPELL_ARRAY[hi_word];
		if (arr == null) {
			arr = new Spell[0x10000];
			SPELL_ARRAY[hi_word] = arr;
		}
		
		if (arr[lo_word] != null) {
			String str = String.format(
				"Spell ID %04x %04x (%d) already taken!\n" +
				"\tExisting: '%s' (%s)\n" +
				"\tNew: '%s' (%s)",
				lo_word, hi_word, id,
				arr[lo_word].unlocalized_name, arr[lo_word].getClass().toString(),
				spell.unlocalized_name, spell.getClass().toString()
			);
			ResAdditae.LOG.error(str);
			throw new IllegalArgumentException(str);
		}
		
		arr[lo_word] = spell;
		SPELL_LIST.add(spell);
		spell.id = id;
		return spell;
	}
	
	public static Spell getByID(int id)
	{
		if (id < 0) {
			return null;
		}
		int hi_word = id >> 16;
		int lo_word = id & 0xffff;
		Spell[] arr = SPELL_ARRAY[hi_word];
		if (arr == null) {
			return null;
		}
		return arr[lo_word];
	}
	
	public static void registerIcons(IIconRegister reg)
	{
		for (Spell spell : SPELL_LIST) {
			if (spell == null) {
				continue;
			}
			spell.registerIcon(reg);
		}
	}
}
