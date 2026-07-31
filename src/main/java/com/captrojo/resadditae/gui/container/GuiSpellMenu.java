package com.captrojo.resadditae.gui.container;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.container.ContainerSpellMenu;
import com.captrojo.resadditae.container.slot.SlotDummy;
import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.gui.screen.GuiSpellSelect;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction;
import com.captrojo.resadditae.packet.toserver.PacketGuiContainerAction.Action;
import com.captrojo.resadditae.render.RenderHlpr;

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
				this.mc.displayGuiScreen(new GuiSpellSelect(this, this.rpp, slot.getSlotIndex()));
				this.click_cooldown = 5;
			} else if (mouse_button != 0) {
				this.rpp.spell_slots[slot.getSlotIndex()] = null;
				ResAdditae.network.sendToServer(new PacketGuiContainerAction(Action.SELECT, slot.getSlotIndex(), -1));
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		GL11.glColor4f(1f, 1f, 1f, 1f);
		
		RenderHlpr.bindTexture(this.mc, BG_TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		RenderHlpr.bindSpellTextureMap(mc);
		for (int i = 0; i < this.rpp.spell_slots.length; i++) {
			Spell spell = this.rpp.spell_slots[i];
			if (spell == null) {
				continue;
			}
			int x = this.guiLeft + SPELLS_X + (i * 18);
			this.drawTexturedModelRectFromIcon(x, this.guiTop + SPELLS_Y, spell.getIcon(), 16, 16);
		}
	}
	
	@Override
	public void updateScreen()
	{
		super.updateScreen();
		
		if (this.click_cooldown > 0) {
			this.click_cooldown--;
		}
	}
}
