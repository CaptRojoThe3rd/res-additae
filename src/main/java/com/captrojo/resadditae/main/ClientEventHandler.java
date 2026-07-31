package com.captrojo.resadditae.main;

import com.captrojo.resadditae.config.common.WorldGenConfig;
import com.captrojo.resadditae.gui.hud.HUDElements;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.world.WorldProviderDepths;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientEventHandler extends CommonEventHandler
{
	public static ClientEventHandler instance;
	
	public static long mspt_avg;
	public static long mspt_last;
	public static long mspt_worst;
	public static boolean mspt_valid = false;
	public static long last_tick_time = 0;
	
	@SubscribeEvent
	public void getFogColorEvent(EntityViewRenderEvent.FogColors event)
	{
		if (event.entity.dimension == WorldGenConfig.depths_dimension_id && event.entity.isPotionActive(Potion.nightVision)) {
			Vec3 color = WorldProviderDepths.FOG_COLOR;
			event.red = (float) color.xCoord;
			event.green = (float) color.yCoord;
			event.blue = (float) color.zCoord;
		}
	}
	
	@SubscribeEvent
	public void renderGameOverlayEvent(RenderGameOverlayEvent event)
	{
		if (event.type == ElementType.CROSSHAIRS) {
			HUDElements.render(event.resolution);
		}
	}
	
	@SubscribeEvent
	public void renderHUDTextEvent(RenderGameOverlayEvent.Text event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		long since_last_tick = Minecraft.getSystemTime() - last_tick_time;
		
		if (mc.gameSettings.showDebugInfo && mspt_valid) {
			String mspt_str = String.format(
				"mspt: [avg: %d, last: %d, worst: %d]",
				mspt_avg,
				mspt_last,
				mspt_worst
			);
			String tps_str = String.format(
				"tps: %.2f, slt: %.1f",
				((mspt_avg < 50) ? 20d : (1000d / (double) mspt_avg)),
				((float) since_last_tick) / 1000f
			);
			int i;
			for (i = 0; i < event.left.size(); i++) {
				if (event.left.get(i).contains("MultiplayerChunkCache")) {
					break;
				}
			}
			i++;
			event.left.add(i, tps_str);
			event.left.add(i, mspt_str);
			event.left.add(i, null);
		}
	}
	
	@SubscribeEvent
	public void onTextureStitching(TextureStitchEvent.Pre event)
	{
		if (event.map.getTextureType() == ResAdditae.SPELL_TEXTUREMAP_ID) {
			Spells.registerIcons(event.map);
		}
	}
}
