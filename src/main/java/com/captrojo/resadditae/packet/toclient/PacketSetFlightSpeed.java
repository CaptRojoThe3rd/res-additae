package com.captrojo.resadditae.packet.toclient;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

public class PacketSetFlightSpeed implements IMessage
{
	protected float flight_speed;
	
	public PacketSetFlightSpeed()
	{
		this.flight_speed = 0.05f;
	}
	
	public PacketSetFlightSpeed(float flight_speed)
	{
		this.flight_speed = flight_speed;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.flight_speed = buf.readFloat();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeFloat(this.flight_speed);
	}
	
	public static class HandlerClient implements IMessageHandler<PacketSetFlightSpeed, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketSetFlightSpeed packet, MessageContext ctx)
		{
			Minecraft.getMinecraft().thePlayer.capabilities.setFlySpeed(packet.flight_speed);
			return null;
		}
	}
}
