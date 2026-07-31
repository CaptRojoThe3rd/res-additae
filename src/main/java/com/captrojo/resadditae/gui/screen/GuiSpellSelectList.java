package com.captrojo.resadditae.gui.screen;

import java.util.ArrayList;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.gui.GuiScrollingList2;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.main.ResAdditae;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class GuiSpellSelectList extends GuiScrollingList2
{
	GuiSpellSelect gui;
	ArrayList<Spell> spell_list;
	
	public GuiSpellSelectList(RAPlayerProperties rpp, GuiSpellSelect gui, int x, int y)
	{
		super(Minecraft.getMinecraft(), 184, 120, y + 4, y + 124, x + 4, 20);
		this.draw_list_background = false;
		this.draw_gradient_rect = false;
		this.draw_edge_gradient = false;
		
		this.gui = gui;
		
		this.spell_list = new ArrayList<Spell>();
		MagicComplexity wand_power = rpp.getWandPower();
		for (LearnedSpell ls : rpp.learned_spells) {
			if (ls.spell.isComplexityRequirementMet(wand_power)) {
				this.spell_list.add(ls.spell);
			}
		}
	}

	@Override
	protected int getSize()
	{
		return this.spell_list.size();
	}

	@Override
	protected void elementClicked(int idx, boolean double_click)
	{
		this.gui.selectSpell(this.spell_list.get(idx));
	}

	@Override
	protected boolean isSelected(int idx)
	{
		return false;
	}

	@Override
	protected void drawBackground()
	{
	}

	@Override
	protected void drawSlot(int idx, int element_right_x, int element_y, int element_h, Tessellator ts)
	{
		FontRenderer fr = this.mc.fontRenderer;
		Spell spell = this.spell_list.get(idx);
		
		String name = I18nHlpr.get(spell.unlocalized_name);
		fr.drawString(name, this.left_x + 24, element_y + 5, 0xffffff);
		
		this.mc.getTextureManager().bindTexture(this.mc.renderEngine.getResourceLocation(ResAdditae.SPELL_TEXTUREMAP_ID));
		IIcon icon = spell.getIcon();
		this.gui.drawTexturedModelRectFromIcon(this.left_x + 4, element_y + 2, icon, 16, 16);
	}
	
	@Override
	protected void drawOverlayBackground(int y1, int y2, int alpha_1, int alpha_2)
	{
	}
}
