package com.captrojo.resadditae.packet.toserver;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketPlayerSettings implements IMessage
{
	private RAPlayerProperties rpp;
	
	public PacketPlayerSettings()
	{
	}
	
	public PacketPlayerSettings(RAPlayerProperties rpp)
	{
		this.rpp = rpp;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.rpp = new RAPlayerProperties(null);
		this.rpp.allow_charm_helping_players = buf.readBoolean();
		this.rpp.allow_charm_helping_entities = buf.readBoolean();
		this.rpp.allow_charm_harming_players = buf.readBoolean();
		this.rpp.allow_charm_harming_entities = buf.readBoolean();
		this.rpp.charm_tamed_mob_behavior = buf.readByte();
		buf.release();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeBoolean(this.rpp.allow_charm_helping_players);
		buf.writeBoolean(this.rpp.allow_charm_helping_entities);
		buf.writeBoolean(this.rpp.allow_charm_harming_players);
		buf.writeBoolean(this.rpp.allow_charm_harming_entities);
		buf.writeByte(this.rpp.charm_tamed_mob_behavior);
	}
	
	public static class HandlerServer implements IMessageHandler<PacketPlayerSettings, IMessage>
	{
		@Override
		public IMessage onMessage(PacketPlayerSettings packet, MessageContext ctx)
		{
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			RAPlayerProperties rpp = RAPlayerProperties.get(player);
			
			rpp.allow_charm_helping_players = packet.rpp.allow_charm_helping_players;
			rpp.allow_charm_helping_entities = packet.rpp.allow_charm_helping_entities;
			rpp.allow_charm_harming_players = packet.rpp.allow_charm_harming_players;
			rpp.allow_charm_harming_entities = packet.rpp.allow_charm_harming_entities;
			rpp.charm_tamed_mob_behavior = packet.rpp.charm_tamed_mob_behavior;
			
			return null;
		}
	}
}
