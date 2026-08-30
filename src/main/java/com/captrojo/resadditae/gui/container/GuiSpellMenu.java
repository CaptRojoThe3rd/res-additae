package com.captrojo.resadditae.gui.container;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.container.ContainerSpellMenu;
import com.captrojo.resadditae.container.slot.SlotDummy;
import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.gui.screen.GuiSpellSelect;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction.Action;
import com.captrojo.resadditae.render.RenderHlpr;
import com.captrojo.resadditae.util.I18nHlpr;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiSpellMenu extends GuiContainer
{
	public static final int WAND_SLOT_X = 12;
	public static final int WAND_SLOT_Y = 106;
	public static final int INV_HOTBAR_X = 45;
	public static final int INV_Y = 152;
	public static final int HOTBAR_Y = 210;
	public static final int SPELLS_X = 40;
	public static final int SPELLS_Y = 110;

	static final ResourceLocation BG_TEXTURE = ResAdditae.resource("textures/gui/container/spell_menu.png");
	
	static final int U_XP = 0;
	static final int V_XP_BG = 246;
	static final int V_XP_FG = 251;
	static final int W_XP = 141;
	static final float WF_XP = 141.0f;
	static final int H_XP = 5;
	
	static final int BTN_VIEW_SPELLS = 0;
	static final int BTN_LVLUP_MANA = 1;
	static final int BTN_LVLUP_SKILL = 2;
	
	RAPlayerProperties rpp;
	int click_cooldown = 0;
	
	public GuiSpellMenu(InventoryPlayer inventory, RAPlayerProperties rpp)
	{
		super(new ContainerSpellMenu(inventory, rpp));
		this.rpp = rpp;

		this.xSize = 248;
		this.ySize = 234;
	}

	@Override
	protected void mouseClicked(int mouse_x, int mouse_y, int mouse_button)
	{
		super.mouseClicked(mouse_x, mouse_y, mouse_button);
		
		Slot slot = this.getSlotAtPosition(mouse_x, mouse_y);
		if (slot != null && slot instanceof SlotDummy) {
			if (mouse_button == 0 && this.click_cooldown <= 0) {
				this.mc.currentScreen = null;
				this.mc.displayGuiScreen(new GuiSpellSelect(this, this.rpp, slot.getSlotIndex(), this.rpp.getWandPower()));
				this.click_cooldown = 5;
			} else if (mouse_button != 0) {
				int idx = slot.getSlotIndex();
				this.rpp.spell_slots[idx] = null;
				this.rpp.active_continuous_spells[idx] = false;
				if (this.rpp.active_spell == idx) {
					this.rpp.active_spell = -1;
				}
				ResAdditae.network.sendToServer(new PacketGuiContainerAction(Action.SELECT, slot.getSlotIndex(), -1));
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float pticks, int mouse_x, int mouse_y)
	{
		int bar_w;
		String lvl_str;
		int lvl_x;
		int lvl_y;
		int font_color;
		int rainbow_color = RenderHlpr.getRainbowCycleColor(this.mc.ingameGUI.getUpdateCounter() * 100);
		
		((GuiButton) this.buttonList.get(1)).enabled = (this.rpp.getManaLvlXPProg() >= 1.0f);
		((GuiButton) this.buttonList.get(2)).enabled = (this.rpp.getMagicSkillLvlXPProg() >= 1.0f);
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
		FontRenderer fr = this.fontRendererObj;
		
		RenderHlpr.bindTexture(this.mc, BG_TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 32, U_XP, V_XP_BG, W_XP, H_XP);
		if (this.rpp.mana_level < RAPlayerProperties.MANA_LVL_MAX) {
			bar_w = (int) (WF_XP * this.rpp.getManaLvlXPProg());
			this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 32, U_XP, V_XP_FG, bar_w, H_XP);
		}
		
		this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 68, U_XP, V_XP_BG, W_XP, H_XP);
		if (this.rpp.magic_skill_level < RAPlayerProperties.MAGIC_SKILL_LVL_MAX) {
			bar_w = (int) (WF_XP * this.rpp.getMagicSkillLvlXPProg());
			this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 68, U_XP, V_XP_FG, bar_w, H_XP);
		}

		RenderHlpr.bindSpellTextureMap(mc);
		for (int i = 0; i < this.rpp.spell_slots.length; i++) {
			LearnedSpell ls = this.rpp.spell_slots[i];
			if (ls == null) {
				continue;
			}
			int x = this.guiLeft + SPELLS_X + (i * 18);
			this.drawTexturedModelRectFromIcon(x, this.guiTop + SPELLS_Y, ls.spell.getIcon(), 16, 16);
		}
		
		fr.drawString(I18nHlpr.get("gui.spell_menu.mana"), this.guiLeft + 8, this.guiTop + 32 - fr.FONT_HEIGHT, 0x404040);
		lvl_str = I18nHlpr.getf("gui.spell_menu.lvl", this.rpp.mana_level);
		fr.drawString(lvl_str, this.guiLeft + 149 - fr.getStringWidth(lvl_str), this.guiTop + 32 - fr.FONT_HEIGHT, 0x404040);
		
		if (this.rpp.mana_level >= RAPlayerProperties.MANA_LVL_MAX) {
			font_color = 0x80ff20;
			lvl_str = I18nHlpr.get("gui.spell_menu.lvl_max");
		} else {
			font_color = (this.rpp.getManaLvlXPProg() >= 1.0f) ? rainbow_color : 0x80ff20;
			lvl_str = String.format("%d/%d", this.rpp.player.experienceLevel, this.rpp.getManaLvlXPReq());
		}
		lvl_x = this.guiLeft + 78 - (fr.getStringWidth(lvl_str) / 2);
		lvl_y = this.guiTop + 31;
		fr.drawString(lvl_str, lvl_x - 1, lvl_y, 0x000000);
		fr.drawString(lvl_str, lvl_x + 1, lvl_y, 0x000000);
		fr.drawString(lvl_str, lvl_x, lvl_y - 1, 0x000000);
		fr.drawString(lvl_str, lvl_x, lvl_y + 1, 0x000000);
		fr.drawString(lvl_str, lvl_x, lvl_y, font_color);
		
		fr.drawString(I18nHlpr.get("gui.spell_menu.skill"), this.guiLeft + 8, this.guiTop + 68 - fr.FONT_HEIGHT, 0x404040);
		lvl_str = I18nHlpr.getf("gui.spell_menu.lvl", this.rpp.magic_skill_level);
		fr.drawString(lvl_str, this.guiLeft + 149 - fr.getStringWidth(lvl_str), this.guiTop + 68 - fr.FONT_HEIGHT, 0x404040);

		if (this.rpp.magic_skill_level >= RAPlayerProperties.MAGIC_SKILL_LVL_MAX) {
			font_color = 0x80ff20;
			lvl_str = I18nHlpr.get("gui.spell_menu.lvl_max");
		} else {
			font_color = (this.rpp.getMagicSkillLvlXPProg() >= 1.0f) ? rainbow_color : 0x80ff20;
			lvl_str = String.format("%d/%d", this.rpp.player.experienceLevel, this.rpp.getMagicSkillLvlXPReq());
		}
		lvl_x = this.guiLeft + 78 - (fr.getStringWidth(lvl_str) / 2);
		lvl_y = this.guiTop + 67;
		fr.drawString(lvl_str, lvl_x - 1, lvl_y, 0x000000);
		fr.drawString(lvl_str, lvl_x + 1, lvl_y, 0x000000);
		fr.drawString(lvl_str, lvl_x, lvl_y - 1, 0x000000);
		fr.drawString(lvl_str, lvl_x, lvl_y + 1, 0x000000);
		fr.drawString(lvl_str, lvl_x, lvl_y, font_color);
		
		fr.drawString(I18nHlpr.get("container.inventory"), this.guiLeft + 44, this.guiTop + 140, 0x404040);
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		
		if (this.click_cooldown > 0) {
			this.click_cooldown--;
		}
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		this.buttonList.add(new GuiButton(
			BTN_VIEW_SPELLS,
			this.guiLeft + 150, this.guiTop + 107,
			91, 20,
			I18nHlpr.get("gui.spell_menu.view_spells")
		));
		
		this.buttonList.add(new GuiButton(
			BTN_LVLUP_MANA,
			this.guiLeft + 152, this.guiTop + 20,
			90, 20,
			I18nHlpr.get("gui.spell_menu.lvlup")
		));
		this.buttonList.add(new GuiButton(
			BTN_LVLUP_SKILL,
			this.guiLeft + 152, this.guiTop + 56,
			90, 20,
			I18nHlpr.get("gui.spell_menu.lvlup")
		));
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == BTN_VIEW_SPELLS) {
			this.mc.currentScreen = null;
			this.mc.displayGuiScreen(new GuiSpellSelect(this, this.rpp, -1, MagicComplexity._MAX));
			this.click_cooldown = 5;
		} else if (button.id == BTN_LVLUP_MANA && this.rpp.mana_level < RAPlayerProperties.MANA_LVL_MAX) {
			ResAdditae.network.sendToServer(new PacketGuiContainerAction(Action.SELECT, -2, 0));
			this.rpp.mana_level++;
		} else if (button.id == BTN_LVLUP_SKILL && this.rpp.magic_skill_level < RAPlayerProperties.MAGIC_SKILL_LVL_MAX) {
			ResAdditae.network.sendToServer(new PacketGuiContainerAction(Action.SELECT, -3, 0));
			this.rpp.magic_skill_level++;
		}
	}
}
