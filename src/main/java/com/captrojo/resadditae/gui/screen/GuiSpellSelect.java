package com.captrojo.resadditae.gui.screen;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.MagicComplexity;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction.Action;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiSpellSelect extends GuiScreen
{
	static final ResourceLocation BG_TEXTURE = ResAdditae.resource("textures/gui/screen/spell_select.png");

	GuiScreen parent;
	RAPlayerProperties rpp;
	int idx;
	MagicComplexity level;

	GuiSpellSelectList scroll_list;

	protected int x_size;
	protected int y_size;
	protected int x_pos;
	protected int y_pos;

	public GuiSpellSelect(GuiScreen parent, RAPlayerProperties rpp, int idx, MagicComplexity level)
	{
		this.parent = parent;
		this.rpp = rpp;
		this.idx = idx;
		this.level = level;

		this.x_size = 256;
		this.y_size = 192;
	}

	public void selectSpell(Spell spell)
	{
		int val = spell.getID();
		ResAdditae.network.sendToServer(new PacketGuiContainerAction(Action.SELECT, this.idx, val));
		this.close();
	}

	public void close()
	{
		this.mc.currentScreen = this.parent;
	}
	
	@Override
	public void initGui()
	{
		this.x_pos = (this.width - this.x_size) / 2;
		this.y_pos = (this.height - this.y_size) / 2;

		this.scroll_list = new GuiSpellSelectList(this.rpp, this, this.x_pos, this.y_pos, this.level);
	}

	@Override
	public void drawScreen(int mouse_x, int mouse_y, float f)
	{
		this.drawDefaultBackground();

		this.mc.getTextureManager().bindTexture(BG_TEXTURE);
		FontRenderer fr = this.fontRendererObj;
		GL11.glColor4f(1f, 1f, 1f, 1f);

		this.drawTexturedModalRect(this.x_pos, this.y_pos, 0, 0, this.x_size, this.y_size);

		this.scroll_list.drawScreen(mouse_x, mouse_y, f);
	}

	@Override
	protected void keyTyped(char c, int n)
	{
		if (n == 1) {
			this.mc.displayGuiScreen(this.parent);
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
