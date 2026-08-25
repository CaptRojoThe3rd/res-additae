package com.captrojo.resadditae.gui.screen;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.item.magic.ItemSpellbook;
import com.captrojo.resadditae.magic.LearnedSpell;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.UseType;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toserver.PacketLearnSpell;
import com.captrojo.resadditae.render.RenderHlpr;
import com.captrojo.resadditae.util.I18nHlpr;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiSpellbook extends GuiScreen
{
	static final ResourceLocation BG_TEXTURE = ResAdditae.resource("textures/gui/screen/spellbook.png");
	
	static final int BTN_LEARN_SPELL = 0;
	static final int BTN_DONE = 1;
	
	protected int x_size;
	protected int y_size;
	protected int x_pos;
	protected int y_pos;
	
	RAPlayerProperties rpp;
	Spell spell;
	boolean learned;
	
	String str_spell_title;
	String str_spell_desc;
	
	String str_wand_level_1;
	String str_wand_level_2;
	String str_skill_1;
	String str_skill_2;
	String str_mana_1;
	String str_mana_2;
	String str_cooldown_1;
	String str_cooldown_2;
	
	int strw_wand_level_2;
	int strw_skill_2;
	int strw_mana_2;
	int strw_cooldown_2;
	
	int skill_offs;
	int mana_offs;
	int cooldown_offs;
	
	boolean show_cooldown;
	boolean skill_req_met;
	
	String str_learned_msg_1;
	String str_learned_msg_2;
	
	public GuiSpellbook(ItemStack stack, EntityPlayer player)
	{
		this.x_size = 320;
		this.y_size = 200;
		
		this.rpp = RAPlayerProperties.get(player);
		this.spell = ItemSpellbook.getSpell(stack);
		LearnedSpell ls = this.rpp.getLearnedFromSpell(this.spell);
		this.learned = (ls != null);
		
		this.str_wand_level_1 = I18nHlpr.get("gui.spellbook.wand_level");
		this.str_skill_1 = I18nHlpr.get("gui.spellbook.skill_req");
		this.str_mana_1 = I18nHlpr.get("gui.spellbook.mana_req");
		this.str_cooldown_1 = I18nHlpr.get("gui.spellbook.cooldown");
		
		if (this.spell != null) {
			MagicComplexity mc = this.spell.complexity;
			this.str_spell_title = I18nHlpr.getf("gui.spellbook.title." + mc.name, this.spell.getLocalizedName());
			this.str_spell_desc = I18nHlpr.get(this.spell.unlocalized_name + ".book").replace("\\n", "\n");

			int skill = this.spell.getSkillRequirement(this.rpp, ls);
			this.skill_offs = this.spell.base_skill_requirement - skill;
			int mana = this.spell.getManaRequirement(this.rpp, ls);
			this.mana_offs = this.spell.base_mana_requirement - mana;
			int cooldown = this.spell.getCooldownTime(this.rpp, ls);
			this.cooldown_offs = this.spell.base_cooldown_time - cooldown;
			this.show_cooldown = (cooldown > 0);
			
			this.skill_req_met = skill <= this.rpp.magic_skill_level;
			
			this.str_wand_level_2 = I18nHlpr.get("gui.spellbook.wand_level." + mc.name);
			this.str_skill_2 = I18nHlpr.getf("gui.spellbook.lvl", skill);
			if (spell.use_type == UseType.CONTINUOUS) {
				this.str_mana_2 = I18nHlpr.getf("gui.spellbook.mana_per_sec", mana * this.spell.mana_uses_per_second);
			} else {
				this.str_mana_2 = Integer.toString(mana);
			}
			this.str_cooldown_2 = I18nHlpr.getf("gui.spellbook.sec", ((float) cooldown) / 20.0f);
		} else {
			this.str_spell_title = "";
			this.str_spell_desc = "";
			
			this.str_wand_level_2 = "";
			this.str_skill_2 = "";
			this.str_mana_2 = "";
			this.str_cooldown_2 = "";
			
			this.skill_req_met = true;
		}
		
		if (!this.skill_req_met) {
			this.str_mana_2 = I18nHlpr.galactifyText(this.str_mana_2);
			this.str_cooldown_2 = I18nHlpr.galactifyText(this.str_cooldown_2);
		}
		
		this.str_learned_msg_1 = this.learned ? "gui.spellbook.learned_msg_1b" : "gui.spellbook.learned_msg_1a";
		this.str_learned_msg_1 = I18nHlpr.get(this.str_learned_msg_1);
		this.str_learned_msg_2 = I18nHlpr.get("gui.spellbook.learned_msg_2");
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == BTN_LEARN_SPELL && this.spell != null && !this.learned) {
			ResAdditae.network.sendToServer(new PacketLearnSpell(this.spell));
			this.learned = true;
			((GuiButton) this.buttonList.get(BTN_LEARN_SPELL)).enabled = false;
		} else if (button.id == BTN_DONE) {
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	public void initGui()
	{
		this.x_pos = (this.width - this.x_size) / 2;
		this.y_pos = (this.height - this.y_size) / 2 - 40;
		
		this.buttonList.add(new GuiButton(
			BTN_LEARN_SPELL,
			this.width / 2 - 132, this.y_pos + 250,
			130, 20,
			I18nHlpr.get("gui.spellbook.learn_spell")
		));
		((GuiButton) this.buttonList.get(BTN_LEARN_SPELL)).enabled = !this.learned && this.skill_req_met;
		
		this.buttonList.add(new GuiButton(
			BTN_DONE,
			this.width / 2 + 2, this.y_pos + 250,
			130, 20,
			I18nHlpr.get("gui.done")
		));
		
		this.strw_wand_level_2 = this.fontRendererObj.getStringWidth(this.str_wand_level_2);
		this.strw_skill_2 = this.fontRendererObj.getStringWidth(this.str_skill_2);
		this.strw_mana_2 = this.fontRendererObj.getStringWidth(this.str_mana_2);
		this.strw_cooldown_2 = this.fontRendererObj.getStringWidth(this.str_cooldown_2);
	}
	
	@Override
	public void drawScreen(int mouse_x, int mouse_y, float f)
	{
		final FontRenderer fr_regular = this.fontRendererObj;
		final FontRenderer fr_galactic = this.mc.standardGalacticFontRenderer;
		FontRenderer fr1 = this.skill_req_met ? fr_regular : fr_galactic;
		FontRenderer fr2 = fr_regular;
		
		this.drawDefaultBackground();
		RenderHlpr.bindTexture(this.mc, BG_TEXTURE, 320, 200);
		RenderHlpr.drawTexturedModalRect(this.x_pos, this.y_pos, 0, 0, this.x_size, this.y_size);
		
		final int base_color = 0x404040;
		final int positive_color = 0x40c040;
		final int negative_color = 0xc04040;
		
		int skill_lbl_color;
		int skill_color;
		int mana_color;
		int cooldown_color;
		if (this.skill_req_met) {
			skill_lbl_color = base_color;
			skill_color = (this.skill_offs == 0) ? base_color : ((this.skill_offs > 0) ? positive_color : negative_color);
			mana_color = (this.mana_offs == 0) ? base_color : ((this.mana_offs > 0) ? positive_color : negative_color);
			cooldown_color = (this.cooldown_offs == 0) ? base_color : ((this.cooldown_offs > 0) ? positive_color : negative_color);
		} else {
			skill_lbl_color = negative_color;
			skill_color = negative_color;
			mana_color = base_color;
			cooldown_color = base_color;
		}
		
		fr1.drawString(this.str_spell_title, this.x_pos + 18, this.y_pos + 16, 0x000000);
		fr1.drawSplitString(this.str_spell_desc, this.x_pos + 18, this.y_pos + 36, 134, base_color);
		
		fr1.drawString(this.str_wand_level_1, this.x_pos + 168, this.y_pos + 26, base_color);
		fr1.drawString(this.str_wand_level_2, this.x_pos + 302 - this.strw_wand_level_2, this.y_pos + 35, 0x404040);
		fr2.drawString(this.str_skill_1, this.x_pos + 168, this.y_pos + 53, skill_lbl_color);
		fr2.drawString(this.str_skill_2, this.x_pos + 302 - this.strw_skill_2, this.y_pos + 62, skill_color);
		fr1.drawString(this.str_mana_1, this.x_pos + 168, this.y_pos + 80, base_color);
		fr1.drawString(this.str_mana_2, this.x_pos + 302 - this.strw_mana_2, this.y_pos + 89, mana_color);
		if (this.show_cooldown) {
			fr1.drawString(this.str_cooldown_1, this.x_pos + 168, this.y_pos + 107, base_color);
			fr1.drawString(this.str_cooldown_2, this.x_pos + 302 - this.strw_cooldown_2, this.y_pos + 116, cooldown_color);
		}
		
		if (this.learned) {
			fr2.drawSplitString(this.str_learned_msg_1, this.x_pos + 1, this.y_pos + 209, 320, 0x202020);
			fr2.drawSplitString(this.str_learned_msg_1, this.x_pos, this.y_pos + 208, 320, 0xffffff);

			fr2.drawSplitString(this.str_learned_msg_2, this.x_pos + 1, this.y_pos + 222, 320, 0x202020);
			fr2.drawSplitString(this.str_learned_msg_2, this.x_pos, this.y_pos + 221, 320, 0xffffff);
		}

		super.drawScreen(mouse_x, mouse_y, f);
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
