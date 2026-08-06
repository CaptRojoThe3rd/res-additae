package com.captrojo.resadditae.packet.toclient;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

public class PacketSpellFeedback implements IMessage
{
	public static enum Feedback
	{
		DEACTIVATE;
		
		final int bit;
		
		private Feedback()
		{
			this.bit = 1 << this.ordinal();
		}
		
		public boolean yes(int actions)
		{
			return (actions & this.bit) != 0;
		}
	}
	
	int idx;
	int actions;
	
	public PacketSpellFeedback()
	{
	}
	
	public PacketSpellFeedback(int idx, Feedback...actions)
	{
		this.idx = idx;
		this.actions = 0;
		for (Feedback action : actions) {
			this.actions |= action.bit;
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.idx = buf.readByte();
		this.actions = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.idx);
		buf.writeInt(this.actions);
	}
	
	public static class HandlerClient implements IMessageHandler<PacketSpellFeedback, IMessage>
	{
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketSpellFeedback packet, MessageContext ctx)
		{
			RAPlayerProperties rpp = RAPlayerProperties.get(Minecraft.getMinecraft().thePlayer);
			
			if (Feedback.DEACTIVATE.yes(packet.actions)) {
				rpp.deactivateSpellClient(packet.idx);
			}
			
			return null;
		}
	}
}
