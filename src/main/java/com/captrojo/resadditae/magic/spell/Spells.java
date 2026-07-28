package com.captrojo.resadditae.magic.spell;

import java.util.HashMap;
import java.util.Map;

import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.renderer.texture.IIconRegister;

public class Spells
{
	public static final Map<Integer, Spell[]> SPELL_REGISTRY = new HashMap<Integer, Spell[]>();
	private static int current_hi_word;
	
	public static void init()
	{
		current_hi_word = 0x0000;
	}
	
	public static void registerIcons(IIconRegister reg)
	{
		for (Spell[] arr : SPELL_REGISTRY.values()) {
			for (Spell spell : arr) {
				if (spell == null) {
					continue;
				}
				spell.registerIcon(reg);
			}
		}
	}
	
	public static void setSpellHiWord(int id)
	{
		if (id < 0x10000) {
			current_hi_word = id;
		} else {
			current_hi_word = id >> 16;
		}
	}
	
	public static Spell registerSpell(int id, Spell spell)
	{
		return registerSpellL(id | (current_hi_word << 16), spell);
	}
	
	public static Spell registerSpellL(int id, Spell spell)
	{
		int hi_word = id >> 16;
		int lo_word = id & 0xffff;
		
		Spell[] arr = SPELL_REGISTRY.get(hi_word);
		if (arr == null) {
			arr = new Spell[0x10000];
			SPELL_REGISTRY.put(hi_word, arr);
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
		Spell[] arr = SPELL_REGISTRY.get(hi_word);
		if (arr == null) {
			return null;
		}
		return arr[lo_word];
	}
}
