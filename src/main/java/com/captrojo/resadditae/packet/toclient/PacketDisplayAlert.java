package com.captrojo.resadditae.packet.toclient;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.main.I18nHlpr;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class PacketDisplayAlert implements IMessage
{
	private Type type;
	private String string;
	
	public PacketDisplayAlert()
	{
	}

	public PacketDisplayAlert(Type type, String string)
	{
		this.type = type;
		this.string = string;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.type = Type.values()[buf.readByte()];
		this.string = "";
		for (byte b = buf.readByte(); b != 0; b = buf.readByte()) {
			this.string += (char) b;
		}
		buf.release();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.type.ordinal());
		for (char c : this.string.toCharArray()) {
			buf.writeByte(c);
		}
		buf.writeByte(0);
	}
	
	public static enum Type
	{
		HOTBAR_LOW
	}
	
	public static class HandlerClient implements IMessageHandler<PacketDisplayAlert, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketDisplayAlert packet, MessageContext ctx)
		{
			switch (packet.type) {
			case HOTBAR_LOW:
				Minecraft.getMinecraft().ingameGUI.func_110326_a(I18nHlpr.get(packet.string), false);
				break;
			}
			
			return null;
		}
	}
}
