package com.captrojo.resadditae.main;

import org.lwjgl.opengl.GL11;

import com.captrojo.resadditae.config.ClientConfig;
import com.captrojo.resadditae.config.CommonConfig;
import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.render.RenderHlpr;
import com.captrojo.resadditae.world.WorldProviderDepths;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ClientEventHandler extends CommonEventHandler
{
	@SubscribeEvent
	public void getFogColorEvent(EntityViewRenderEvent.FogColors event)
	{
		if (event.entity.dimension == CommonConfig.WorldGen.depths_dimension_id && event.entity.isPotionActive(Potion.nightVision)) {
			Vec3 color = WorldProviderDepths.FOG_COLOR;
			event.red = (float) color.xCoord;
			event.green = (float) color.yCoord;
			event.blue = (float) color.zCoord;
		}
	}
	
	private void renderXPBar(Minecraft mc, ScaledResolution sr, EntityPlayer player)
	{
		if (!mc.playerController.gameIsSurvivalOrAdventure()) {
			return;
		}
		
		RenderHlpr.bindIcons(mc.getTextureManager());
		RenderHlpr.z_level = -90d;
		FontRenderer fr = mc.fontRenderer;
		
		GL11.glColor4f(1f, 1f, 1f, 1f);

		mc.mcProfiler.startSection("expBar");
		
		int cap = mc.thePlayer.xpBarCap();
		int left = sr.getScaledWidth() / 2 - 92;
		if (cap > 0) {
			short barWidth = 91;
			int filled = (int) (mc.thePlayer.experience * (float) (barWidth + 1));
			int top = sr.getScaledHeight() - 32 + 3;
			RenderHlpr.drawTexturedModalRect(left, top, 0, 0, barWidth, 5);

			if (filled > 0) {
				RenderHlpr.drawTexturedModalRect(left, top, 0, 5, filled, 5);
			}
		}

		mc.mcProfiler.endSection();

		if (mc.thePlayer.experienceLevel == 0) {
			return;
		}
		
		mc.mcProfiler.startSection("expLevel");

		boolean white = false;
		int color = white ? 0xffffff : 0x80ff20;
		String text = "" + mc.thePlayer.experienceLevel;

		int x = (sr.getScaledWidth() - fr.getStringWidth(text)) / 2;
		int y = sr.getScaledHeight() - 38;
		fr.drawString(text, x + 1, y, 0);
		fr.drawString(text, x - 1, y, 0);
		fr.drawString(text, x, y + 1, 0);
		fr.drawString(text, x, y - 1, 0);
		fr.drawString(text, x, y, color);

		mc.mcProfiler.endSection();
	}
	
	private void renderManaBar(Minecraft mc, ScaledResolution sr, EntityPlayer player, RAPlayerProperties rpp, float partial_ticks)
	{
		if (!mc.playerController.gameIsSurvivalOrAdventure()) {
			return;
		}
		
		mc.mcProfiler.startSection("manaBar");
		
		RenderHlpr.bindIcons(mc.getTextureManager());
		RenderHlpr.z_level = -90d;
		GL11.glColor4f(1f, 1f, 1f, 1f);
		
		int left = sr.getScaledWidth() / 2 + 1;
		int top = sr.getScaledHeight() - 29;
		int fill_width = (int) (((float) rpp.mana) / ((float) rpp.mana_max) * 91f);
		
		int x = ClientConfig.HUD.mana_bar_fill_direction == 0 ? left : left + (91 - fill_width);
		int u = ClientConfig.HUD.mana_bar_fill_direction == 0 ? 92 : 92 + (91 - fill_width);
		
		RenderHlpr.drawTexturedModalRect(left, top, 92, 0, 91, 5);
		RenderHlpr.drawTexturedModalRect(x, top, u, 5, fill_width, 5);
		
		mc.mcProfiler.endSection();
	}

	@SubscribeEvent
	public void onHUDRenderPre(RenderGameOverlayEvent.Pre event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		EntityPlayer player = mc.thePlayer;

		if (player == null) {
			return;
		}
		RAPlayerProperties rpp = RAPlayerProperties.get(player);

		if (event.type == ElementType.EXPERIENCE) {
			this.renderXPBar(mc, sr, player);
			this.renderManaBar(mc, sr, player, rpp, event.partialTicks);

			event.setCanceled(true);
			return;
		}
	}
	
	@SubscribeEvent
	public void renderHUDTextEvent(RenderGameOverlayEvent.Text event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (mc.gameSettings.showDebugInfo) {
			String mspt_str = String.format(
				"mspt: [worst: %d, avg: %d, last: %d]",
				CommonEventHandler.mspt_worst,
				CommonEventHandler.mspt_avg,
				CommonEventHandler.mspt_last
			);
			String tps_str = String.format(
				"tps: %.2f",
				(CommonEventHandler.mspt_avg < 50) ? 20d : (1000d / (double) CommonEventHandler.mspt_avg)
			);
			event.left.add(null);
			event.left.add(mspt_str);
			event.left.add(tps_str);
		}
	}
}
