package com.captrojo.resadditae.main;

import java.util.ArrayList;

import com.captrojo.resadditae.packet.toclient.PacketPerformanceInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;

public class PerformanceInfo
{
	static ArrayList<EntityPlayerMP> listeners = new ArrayList<EntityPlayerMP>();
	static long tick_time_start;
	static long tick_time_end;
	static long[] mspt_avg_arr = new long[20];
	static int mspt_avg_arr_idx;
	static long mspt_avg;
	static long mspt_last;
	static long mspt_worst;

	public static void addListener(EntityPlayerMP player)
	{
		listeners.add(player);
	}

	public static void removeListener(EntityPlayerMP player)
	{
		ResAdditae.network.sendTo(new PacketPerformanceInfo(), player);
		listeners.remove(player);
	}

	public static void resetInfo()
	{
		mspt_avg_arr = new long[20];
		mspt_worst = 0;
	}

	public static void onTickStart(long time_ms)
	{
		tick_time_start = time_ms;
	}

	public static void onTickEnd(long time_ms)
	{
		tick_time_end = time_ms;

		mspt_last = tick_time_end - tick_time_start;
		if (mspt_last > mspt_worst) {
			mspt_worst = mspt_last;
		}

		mspt_avg_arr[mspt_avg_arr_idx] = mspt_last;
		mspt_avg_arr_idx = (mspt_avg_arr_idx + 1) % 20;
		mspt_avg = 0;
		for (long l : mspt_avg_arr) {
			mspt_avg += l;
		}
		mspt_avg /= 20;

		for (EntityPlayerMP player : listeners) {
			ResAdditae.network.sendTo(new PacketPerformanceInfo(mspt_avg, mspt_last, mspt_worst), player);
		}
		if (ClientEventHandler.instance != null) {
			ClientEventHandler.mspt_avg = mspt_avg;
			ClientEventHandler.mspt_last = mspt_last;
			ClientEventHandler.mspt_worst = mspt_worst;
			ClientEventHandler.mspt_valid = true;
			ClientEventHandler.last_tick_time = Minecraft.getSystemTime();
		}
	}
}
