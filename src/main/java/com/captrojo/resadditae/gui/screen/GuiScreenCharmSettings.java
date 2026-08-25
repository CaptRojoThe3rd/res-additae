package com.captrojo.resadditae.gui.screen;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toserver.PacketPlayerSettings;
import com.captrojo.resadditae.util.I18nHlpr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiScreenCharmSettings extends GuiScreen
{
	private static final ResourceLocation BG_TEXTURE = ResAdditae.resource("textures/gui/screen/charm_settings.png");
	private static final String[] CTMB_STRS = {"all", "yours"};
	
	protected EntityPlayer player;
	protected RAPlayerProperties rpp;
	
	protected int x_size;
	protected int y_size;
	protected int x_pos;
	protected int y_pos;
	
	public GuiScreenCharmSettings(EntityPlayer player)
	{
		super();
		this.player = player;
		this.rpp = RAPlayerProperties.get(player);
		
		this.x_size = 248;
		this.y_size = 178;
	}
	
	@Override
	public void initGui()
	{
		this.x_pos = (this.width - this.x_size) / 2;
		this.y_pos = (this.height - this.y_size) / 2;
		
		this.buttonList.add(new GuiButton(0, this.x_pos + (this.x_size / 2), this.y_pos + 24, (this.x_size / 2) - 10, 20, ""));
		this.buttonList.add(new GuiButton(1, this.x_pos + (this.x_size / 2), this.y_pos + 48, (this.x_size / 2) - 10, 20, ""));
		this.buttonList.add(new GuiButton(2, this.x_pos + (this.x_size / 2), this.y_pos + 72, (this.x_size / 2) - 10, 20, ""));
		this.buttonList.add(new GuiButton(3, this.x_pos + (this.x_size / 2), this.y_pos + 96, (this.x_size / 2) - 10, 20, ""));
		this.buttonList.add(new GuiButton(4, this.x_pos + (this.x_size / 2), this.y_pos + 120, (this.x_size / 2) - 10, 20, ""));
		this.updateButtonText(false);
	}
	
	@Override
	public void actionPerformed(GuiButton button)
	{
		switch (button.id) {
		case 0:
			this.rpp.allow_charm_helping_players = !this.rpp.allow_charm_helping_players;
			break;
		case 1:
			this.rpp.allow_charm_helping_entities = !this.rpp.allow_charm_helping_entities;
			break;
		case 2:
			this.rpp.allow_charm_harming_players = !this.rpp.allow_charm_harming_players;
			break;
		case 3:
			this.rpp.allow_charm_harming_entities = !this.rpp.allow_charm_harming_entities;
			break;
		case 4:
			this.rpp.charm_tamed_mob_behavior ^= 1;
			break;
		}
		this.updateButtonText(true);
	}
	
	public void updateButtonText(boolean send_packet)
	{
		((GuiButton) this.buttonList.get(0)).displayString = I18nHlpr.bool(this.rpp.allow_charm_helping_players);
		((GuiButton) this.buttonList.get(1)).displayString = I18nHlpr.bool(this.rpp.allow_charm_helping_entities);
		((GuiButton) this.buttonList.get(2)).displayString = I18nHlpr.bool(this.rpp.allow_charm_harming_players);
		((GuiButton) this.buttonList.get(3)).displayString = I18nHlpr.bool(this.rpp.allow_charm_harming_entities);
		((GuiButton) this.buttonList.get(4)).displayString = I18nHlpr.get("misc." + CTMB_STRS[this.rpp.charm_tamed_mob_behavior]);
		if (send_packet) {
			ResAdditae.network.sendToServer(new PacketPlayerSettings(this.rpp));
		}
	}
	
	@Override
	public void drawScreen(int mouse_x, int mouse_y, float f)
	{
		this.drawDefaultBackground();

		this.mc.getTextureManager().bindTexture(BG_TEXTURE);
		FontRenderer fr = this.fontRendererObj;
		GL11.glColor4f(1f, 1f, 1f, 1f);
		
		this.drawTexturedModalRect(this.x_pos, this.y_pos, 0, 0, this.x_size, this.y_size);
		
		String title = I18nHlpr.get("gui.charm_settings.title");
		fr.drawString(title, this.x_pos + ((this.x_size - fr.getStringWidth(title)) / 2), this.y_pos + 6, 0x373737);
		
		fr.drawString(I18nHlpr.get("gui.charm_settings.help_players"), this.x_pos + 10, this.y_pos + 30, 0x373737);
		fr.drawString(I18nHlpr.get("gui.charm_settings.help_entities"), this.x_pos + 10, this.y_pos + 54, 0x373737);
		fr.drawString(I18nHlpr.get("gui.charm_settings.hurt_players"), this.x_pos + 10, this.y_pos + 78, 0x373737);
		fr.drawString(I18nHlpr.get("gui.charm_settings.hurt_entities"), this.x_pos + 10, this.y_pos + 102, 0x373737);
		fr.drawString(I18nHlpr.get("gui.charm_settings.tamed_mob_behavior"), this.x_pos + 10, this.y_pos + 126, 0x373737);
		
		fr.drawSplitString(I18nHlpr.get("gui.charm_settings.tamed_mob_info." + CTMB_STRS[this.rpp.charm_tamed_mob_behavior]), 
			this.x_pos + 10, this.y_pos + 150, this.x_size - 20, 0x373737);
	
		super.drawScreen(mouse_x, mouse_y, f);
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}
}
