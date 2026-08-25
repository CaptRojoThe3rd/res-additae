package com.captrojo.resadditae.packet.toclient;

import com.captrojo.resadditae.main.ClientEventHandler;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class PacketPerformanceInfo implements IMessage
{
	public static enum Type
	{
		DATA,
		STOP
	}
	
	private Type type;
	
	private long mspt_avg;
	private long mspt_last;
	private long mspt_worst;
	
	public PacketPerformanceInfo()
	{
		this.type = Type.STOP;
	}
	
	public PacketPerformanceInfo(long mspt_avg, long mspt_last, long mspt_worst)
	{
		this.type = Type.DATA;
		this.mspt_avg = mspt_avg;
		this.mspt_last = mspt_last;
		this.mspt_worst = mspt_worst;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.type = Type.values()[buf.readByte()];
		if (this.type == Type.DATA) {
			this.mspt_avg = buf.readLong();
			this.mspt_last = buf.readLong();
			this.mspt_worst = buf.readLong();
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte((byte) this.type.ordinal());
		if (this.type == Type.DATA) {
			buf.writeLong(this.mspt_avg);
			buf.writeLong(this.mspt_last);
			buf.writeLong(this.mspt_worst);
		}
	}

	public static class HandlerClient implements IMessageHandler<PacketPerformanceInfo, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketPerformanceInfo packet, MessageContext ctx)
		{
			if (packet.type == Type.DATA) {
				ClientEventHandler.mspt_avg = packet.mspt_avg;
				ClientEventHandler.mspt_last = packet.mspt_last;
				ClientEventHandler.mspt_worst = packet.mspt_worst;
				ClientEventHandler.mspt_valid = true;
			} else {
				ClientEventHandler.mspt_valid = false;
			}
			ClientEventHandler.last_tick_time = Minecraft.getSystemTime();
			return null;
		}
	}
}
