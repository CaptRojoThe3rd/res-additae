package com.captrojo.resadditae.gui.container;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.captrojo.resadditae.container.ContainerStonecutter;
import com.captrojo.resadditae.crafting.StonecutterRecipes;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.packet.toserver.PacketNBTControl;
import com.captrojo.resadditae.tileentity.TEStonecutter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class GuiStonecutter extends GuiContainer
{
	private static final ResourceLocation BG_TEXTURE = ResAdditae.resource("textures/gui/container/stonecutter.png");

	private TEStonecutter te;
	private int page;

	public GuiStonecutter(InventoryPlayer player_inventory, TEStonecutter tile_entity)
	{
		super(new ContainerStonecutter(player_inventory, tile_entity));

		this.te = tile_entity;
		this.te.container = (ContainerStonecutter) this.inventorySlots;
		this.page = 0;

		this.xSize = 230;
		this.ySize = 246;
	}

	@Override
	protected void mouseClicked(int x, int y, int i)
	{
		super.mouseClicked(x, y, i);
		
		ItemStack input = (ItemStack) this.inventorySlots.inventoryItemStacks.get(ContainerStonecutter.INPUT_SLOT);
		ItemStack[] outputs = StonecutterRecipes.getOutputsFromInput(input);
		
		if (this.te.selection >= outputs.length) {
			this.te.selection = -1;
		}

		int sx = x - this.guiLeft;
		int sy = y - this.guiTop;
		if (sy >= 109 && sy <= 124) {
			int old_page = this.page;
			if (sx >= 188 && sx <= 203) {
				this.page--;
				if (this.page < 0) {
					this.page = 0;
				}
			} else if (sx >= 206 && sx <= 221) {
				this.page++;
				if ((this.page * 60) >= outputs.length) {
					this.page = 0;
				}
			}
			if (old_page != this.page) {
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
			}
		}

		sx -= 8;
		sy -= 18;
		if (sx >= 0 && sx < 214 && sy >= 0 && sy < 88) {
			int old_selection = this.te.selection;
			this.te.selection = (sy / 18 * 12) + (sx / 18);
			this.te.selection += (this.page * 60);
			if (this.te.selection >= outputs.length) {
				this.te.selection = old_selection;
			}
			if (this.te.selection != old_selection) {
				this.te.notifyContainer();
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
				NBTTagCompound ctrl = new NBTTagCompound();
				ctrl.setInteger("selection", this.te.selection);
				ResAdditae.network.sendToServer(new PacketNBTControl(ctrl, this.te.xCoord, this.te.yCoord, this.te.zCoord));
			}
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f1, int i1, int i2)
	{
		GL11.glColor4f(1f, 1f, 1f, 1f);
		Minecraft.getMinecraft().getTextureManager().bindTexture(BG_TEXTURE);

		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

		if (this.te.selection != -1) {
			int s = this.te.selection % 60;
			int sx = (s % 12) * 18 + this.guiLeft + 8;
			int sy = (s / 12) * 18 + this.guiTop + 18;
			this.drawTexturedModalRect(sx, sy, 239, 239, 17, 17);
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int i1, int i2)
	{
		this.fontRendererObj.drawString(I18n.format("gui.stonecutter.title"), 7, 6, 0x373737);
		this.fontRendererObj.drawString(I18n.format("container.inventory"), 34, 152, 0x373737);

		ItemStack input = (ItemStack) this.inventorySlots.inventoryItemStacks.get(ContainerStonecutter.INPUT_SLOT);
		ItemStack[] outputs = StonecutterRecipes.getOutputsFromInput(input);
		if ((this.page * 60) >= outputs.length) {
			this.page = 0;
		}
		
		for (int i = (this.page * 60); i < outputs.length; i++) {
			int x = ((i % 12) * 18) + 8;
			int y = ((i / 12) * 18) + 18;
			this.renderItem(outputs[i], x, y);
		}
	}

	public void renderItem(ItemStack stack, int x, int y)
	{
		FontRenderer font = stack.getItem().getFontRenderer(stack);
		if (font == null) font = fontRendererObj;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) 240 / 1.0F, (float) 240 / 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		itemRender.zLevel = 100.0F;
		itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
		itemRender.zLevel = 0.0F;

		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
	}
}
