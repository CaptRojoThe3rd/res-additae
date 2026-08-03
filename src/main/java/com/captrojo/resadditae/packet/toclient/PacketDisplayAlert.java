package com.captrojo.resadditae.packet.toclient;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.Alerts;
import com.captrojo.resadditae.main.I18nHlpr;
import com.captrojo.resadditae.packet.DynamicTypeRW;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class PacketDisplayAlert implements IMessage
{
	Alerts alert;
	Object[] data;
	
	public PacketDisplayAlert()
	{
	}
	
	public PacketDisplayAlert(Alerts alert, Object...data)
	{
		this.alert = alert;
		this.data = data;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.alert = Alerts.values()[buf.readByte()];
		this.data = new Object[buf.readByte()];
		for (int i = 0; i < this.data.length; i++) {
			this.data[i] = DynamicTypeRW.read(buf);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.alert.ordinal());
		buf.writeByte(this.data.length);
		for (Object obj : this.data) {
			DynamicTypeRW.write(buf, obj);
		}
	}
	
	public static class HandlerClient implements IMessageHandler<PacketDisplayAlert, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketDisplayAlert packet, MessageContext ctx)
		{
			Alerts.display(packet.alert, packet.data);
			return null;
		}
	}
}
