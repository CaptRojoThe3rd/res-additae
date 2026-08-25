package com.captrojo.resadditae.gui.screen;

import java.util.ArrayList;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.gui.GuiScrollingList2;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.render.RenderHlpr;
import com.captrojo.resadditae.util.I18nHlpr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class GuiSpellSelectList extends GuiScrollingList2
{
	static final int U_PROF = 0;
	static final int V_PROF_BG = 192;
	static final int V_PROF_FG = 197;
	static final int W_PROF = 96;
	static final float WF_PROF = 96.0f;
	static final int H_PROF = 5;
	
	GuiSpellSelect gui;
	ArrayList<LearnedSpell> spell_list;
	
	public GuiSpellSelectList(RAPlayerProperties rpp, GuiSpellSelect gui, int x, int y, MagicComplexity level)
	{
		super(Minecraft.getMinecraft(), 252, 184, y + 4, y + 188, x, 20);
		this.draw_list_background = false;
		this.draw_gradient_rect = false;
		this.draw_edge_gradient = false;
		
		this.gui = gui;
		
		this.spell_list = new ArrayList<LearnedSpell>();
		for (LearnedSpell ls : rpp.learned_spells) {
			if (ls.spell.isPowerRequirementMet(level)) {
				this.spell_list.add(ls);
			}
		}
		this.spell_list.sort(null);
	}

	@Override
	protected int getSize()
	{
		return this.spell_list.size();
	}

	@Override
	protected void elementClicked(int idx, boolean double_click)
	{
		this.gui.selectSpell(this.spell_list.get(idx).spell);
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
		LearnedSpell ls = this.spell_list.get(idx);
		
		boolean hover = this.checkMousePos(this.left_x, element_right_x, element_y, element_y + 20);
		int font_color = hover ? 0xffffc0 : 0xffffff;

		RenderHlpr.bindTexture(this.mc, GuiSpellSelect.BG_TEXTURE);
		if (hover) {
			this.gui.drawTexturedModalRect(this.left_x + 3, element_y, 0, 236, 250, 20);
		}
		this.gui.drawTexturedModalRect(element_right_x - 106, element_y + 12, U_PROF, V_PROF_BG, W_PROF, H_PROF);
		int prog_w = (int) (WF_PROF * ls.getProfPtProgress());
		this.gui.drawTexturedModalRect(element_right_x - 106, element_y + 12, U_PROF, V_PROF_FG, prog_w, H_PROF);
		
		RenderHlpr.bindSpellTextureMap(this.mc);
		IIcon icon = ls.spell.getIcon();
		this.gui.drawTexturedModelRectFromIcon(this.left_x + 4, element_y + 2, icon, 16, 16);
		
		fr.drawString(I18nHlpr.get("gui.spell_list.proficiency"), element_right_x - 106, element_y + 3, font_color);
		String str_lv = I18nHlpr.getf("gui.spell_list.lvl", ls.proficiency);
		fr.drawString(str_lv, element_right_x - 10 - fr.getStringWidth(str_lv), element_y + 3, font_color);
		
		String name = ls.spell.getLocalizedName();
		fr.drawString(name, this.left_x + 24, element_y + 5, font_color);
	}
	
	@Override
	protected void drawOverlayBackground(int y1, int y2, int alpha_1, int alpha_2)
	{
	}
}
