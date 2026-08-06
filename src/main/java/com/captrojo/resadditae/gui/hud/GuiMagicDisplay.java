package com.captrojo.resadditae.gui.hud;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.main.InputEventHandler;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.render.RenderHlpr;

import cpw.mods.fml.common.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@Optional.Interface(
	iface = "com.captrojo.complexhud.api.IComplexHUDElement",
	modid = "complexhud",
	striprefs = true
)
public class GuiMagicDisplay extends Gui implements IComplexHUDElement
{
	static final ResourceLocation MOD_ICONS = ResAdditae.resource("textures/gui/icons.png");
	
	static final int U_COOLDN = 0;
	static final int V_COOLDN = 0;
	static final int W_COOLDN = 16;
	static final int H_COOLDN = 16;
	
	static final int U_BAR_BG = 0;
	static final int U_BAR_FG = 121;
	static final int V_BARS = 156;
	static final int W_BAR = 121;
	static final int H_BAR = 5;
	
	static final int U_ACT = 242;
	static final int V_ACT_LO = 252;
	static final int V_ACT_HI = 254;
	static final int W_ACT = 14;
	static final int H_ACT = 2;
	
	static final int U_CONT = 242;
	static final int V_CONT_LO = 248;
	static final int V_CONT_HI = 250;
	static final int W_CONT = 14;
	static final int H_CONT = 2;
	
	static final int W_WAND = 17;
	
	static final int H_SPELLS = 18;
	
	Minecraft mc;
	
	boolean should_render;
	int width;
	int height;
	
	public GuiMagicDisplay()
	{
		this.mc = Minecraft.getMinecraft();
	}
	
	void bindModIcons()
	{
		this.mc.getTextureManager().bindTexture(MOD_ICONS);
	}
	
	public int getSpellXOffs(int xl, int idx)
	{
		return xl + W_WAND + 1 + (idx * 17);
	}
	
	public boolean shouldRender()
	{
		return this.should_render;
	}
	
	public void calcStuff()
	{
		RAPlayerProperties rpp = RAPlayerProperties.get(this.mc.thePlayer);
		
		this.should_render = rpp.mana_level > 0 && rpp.wand_item != null;
		if (!this.should_render) {
			return;
		}
		
		this.width = W_BAR;
		this.height = H_BAR + H_SPELLS;
	}
	
	public void render(int xl, int yt, int xr, int yb)
	{
		this.mc.mcProfiler.startSection("magic_display");
		GL11.glEnable(GL11.GL_BLEND);
		FontRenderer fr = this.mc.fontRenderer;
		
		RAPlayerProperties rpp = RAPlayerProperties.get(this.mc.thePlayer);
		
		/* ITEM TEXTURE MAP */
		if (rpp.wand_item != null) {
			IIcon icon = rpp.wand_item.getIconIndex();
			RenderHlpr.bindItemTextureMap(this.mc);
			this.drawTexturedModelRectFromIcon(xl, yt, icon, 16, 16);
		}
		
		/* SPELL TEXTURE MAP */
		RenderHlpr.bindSpellTextureMap(this.mc);
		for (int i = 0; i < rpp.spell_slots.length; i++) {
			LearnedSpell ls = rpp.spell_slots[i];
			if (ls == null) {
				continue;
			}
			IIcon icon = ls.spell.getIcon();
			int x = this.getSpellXOffs(xl, i);
			this.drawTexturedModelRectFromIcon(x, yt, icon, 16, 16);
		}
		
		/* MOD ICONS TEXTURE */
		this.bindModIcons();
		int bar_offs = (rpp.mana_level - 1) * H_BAR;
		int bar_size = MathHelper.ceiling_float_int((float) rpp.mana / (float) rpp.mana_max * (float) W_BAR);
		this.drawTexturedModalRect(xr - W_BAR, yb - H_BAR, U_BAR_BG, V_BARS + bar_offs, W_BAR, H_BAR);
		this.drawTexturedModalRect(xr - W_BAR, yb - H_BAR, U_BAR_FG, V_BARS + bar_offs, bar_size, H_BAR);
		
		if (rpp.isSpellActive()) {
			int x = this.getSpellXOffs(xl, rpp.active_spell);
			int v = ((this.mc.ingameGUI.getUpdateCounter() & 0x2) == 0) ? V_ACT_LO : V_ACT_HI;
			this.drawTexturedModalRect(x, yb - H_BAR - H_ACT, U_ACT, v, W_ACT, H_ACT);
		}
		for (int i = 0; i < rpp.active_continuous_spells.length; i++) {
			if (!rpp.active_continuous_spells[i]) {
				continue;
			}
			int x = this.getSpellXOffs(xl, i);
			int v = ((this.mc.ingameGUI.getUpdateCounter() & 0x4) == 0) ? V_CONT_LO : V_CONT_HI;
			this.drawTexturedModalRect(x, yb - H_BAR - H_CONT, U_CONT, v, W_CONT, H_CONT);
		}
		
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.333f);
		for (int i = 0; i < rpp.spell_cooldowns.length; i++) {
			int cur = rpp.spell_cooldowns[i];
			int max = rpp.spell_cooldown_starts[i];
			if (cur == 0 || max == 0) {
				continue;
			}
			
			int anim_idx = 55 - ((56 * cur) / max);
			int horz_idx = anim_idx % 14;
			int vert_idx = anim_idx / 14;
			
			int x = this.getSpellXOffs(xl, i);
			int u = U_COOLDN + (W_COOLDN * horz_idx);
			int v = V_COOLDN + (H_COOLDN * vert_idx);
			
			this.drawTexturedModalRect(x, yt, u, v, W_COOLDN, H_COOLDN);
		}
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		/* FONT TEXTURES */
		for (int i = 0; i < rpp.spell_slots.length; i++) {
			if (rpp.spell_slots[i] == null) {
				continue;
			}

			int str_y = yt + 10;
			
			int font_color = 0xffffff;
			if (rpp.active_spell == i) {
				font_color = 0x808080;
			}
			
			KeyBinding kb = InputEventHandler.spell_keys[i];
			String str = InputEventHandler.getShortStringFor(kb);
			int str_w = fr.getStringWidth(str);
			int str_x = this.getSpellXOffs(xl, i) + 17 - str_w;
			RenderHlpr.renderTextBacked(fr, str, str_x, str_y, font_color);
			
			if (rpp.active_spell == i) {
				KeyBinding kb1 = InputEventHandler.spell_trigger;
				String str1 = InputEventHandler.getShortStringFor(kb1);
				int str_w1 = fr.getStringWidth(str1);
				int str_x1 = this.getSpellXOffs(xl, i) + 17 - str_w1;
				RenderHlpr.renderTextBacked(fr, str1, str_x1, str_y - 8, 0xffffff);
			}
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		this.mc.mcProfiler.endSection();
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return "hud.magic_display";
	}

	@Override
	public int getDefaultPriority()
	{
		return 0;
	}

	@Override
	public boolean getDefaultFixedSetting()
	{
		return false;
	}

	@Override
	public PositionOrigin getDefaultPosOrigin()
	{
		return PositionOrigin.HOTBAR_SIDE_LEFT;
	}

	@Override
	public PositionOperation getDefaultPosOperation()
	{
		return PositionOperation.LEFT;
	}

	@Override
	public int getDefaultXOffs()
	{
		return 0;
	}

	@Override
	public int getDefaultYOffs()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferTopSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferBottomSize()
	{
		return 2;
	}

	@Override
	public int getDefaultBufferLeftSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferRightSize()
	{
		return 10;
	}

	@Override
	public boolean getDefaultRenderInF3Setting()
	{
		return true;
	}

	@Override
	public IConfigEntry[] getConfigOptions()
	{
		return null;
	}

	@Override
	public void onConfigUpdated()
	{
	}

	@Override
	public int getWidth()
	{
		return this.width;
	}

	@Override
	public int getHeight()
	{
		return this.height;
	}

	@Override
	public boolean isToBeRendered()
	{
		return this.shouldRender();
	}

	@Override
	public void updateTick()
	{
	}

	@Override
	public void doPreRenderWork()
	{
		this.calcStuff();
	}

	@Override
	public void render(ScaledResolution sr, int mouse_x, int mouse_y, float partial_ticks, PositionInfoXY2 pos)
	{
		this.render(pos.left_x, pos.top_y, pos.right_x, pos.bottom_y);
	}
}
