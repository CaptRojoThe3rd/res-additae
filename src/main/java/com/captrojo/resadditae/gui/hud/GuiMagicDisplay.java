package com.captrojo.resadditae.gui.hud;

import org.lwjgl.opengl.GL11;

import com.captrojo.complexhud.api.IComplexHUDElement;
import com.captrojo.complexhud.api.IConfigEntry;
import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.api.PositionOperation;
import com.captrojo.complexhud.api.PositionOrigin;
import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
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
	
	static final int U_BAR_BG = 0;
	static final int U_BAR_FG = 121;
	
	static final int V_BARS = 156;
	
	static final int W_BAR = 121;
	static final int H_BAR = 5;
	
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
	
	void bindSpellTextureMap()
	{
		this.mc.renderEngine.bindTexture(this.mc.renderEngine.getResourceLocation(ResAdditae.SPELL_TEXTUREMAP_ID));
	}
	
	public boolean shouldRender()
	{
		return this.should_render;
	}
	
	public void calcStuff()
	{
		RAPlayerProperties rpp = RAPlayerProperties.get(this.mc.thePlayer);
		
		this.should_render = rpp.mana_level > 0;
		if (!this.should_render) {
			return;
		}
		
		this.width = W_BAR;
		this.height = H_BAR;
	}
	
	public void render(int xl, int yt, int xr, int yb)
	{
		this.mc.mcProfiler.startSection("magic_display");
		GL11.glEnable(GL11.GL_BLEND);
		
		this.bindModIcons();
		
		RAPlayerProperties rpp = RAPlayerProperties.get(this.mc.thePlayer);
		int bar_size = MathHelper.ceiling_float_int((float) rpp.mana / (float) rpp.mana_max * (float) W_BAR);
		
		this.drawTexturedModalRect(xl, yt, U_BAR_BG, V_BARS, W_BAR, H_BAR);
		this.drawTexturedModalRect(xl, yt, U_BAR_FG, V_BARS, bar_size, H_BAR);
		
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
		return -3;
	}

	@Override
	public int getDefaultBufferLeftSize()
	{
		return 0;
	}

	@Override
	public int getDefaultBufferRightSize()
	{
		return 20;
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
