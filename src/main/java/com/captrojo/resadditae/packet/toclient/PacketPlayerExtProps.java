package com.captrojo.resadditae.packet.toclient;

import com.captrojo.resadditae.extprop.RAPlayerProperties;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class PacketPlayerExtProps implements IMessage
{
	private RAPlayerProperties rpp;
	private ByteBuf buf;
	
	public PacketPlayerExtProps()
	{
	}
	
	public PacketPlayerExtProps(RAPlayerProperties rpp)
	{
		this.rpp = rpp;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.buf = buf;
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		this.rpp.serialize(buf);
	}

	public static class HandlerClient implements IMessageHandler<PacketPlayerExtProps, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketPlayerExtProps packet, MessageContext ctx)
		{
			if (Minecraft.getMinecraft().theWorld == null) {
				return null;
			}
			
			RAPlayerProperties rpp = RAPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
			rpp.deserialize(packet.buf);

			packet.buf.release();
			return null;
		}
	}
}
