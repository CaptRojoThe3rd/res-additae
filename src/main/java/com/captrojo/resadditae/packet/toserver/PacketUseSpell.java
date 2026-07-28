package com.captrojo.resadditae.packet.toserver;

import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.magic.spell.Spell;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class PacketUseSpell implements IMessage
{
	public static enum UseType
	{
		ACTIVATE,
		TRIGGER_WHILE_ACTIVE,
		DEACTIVATE
	}
	
	UseType use_type;
	int slot;
	
	public PacketUseSpell()
	{
	}
	
	public PacketUseSpell(UseType type, int slot)
	{
		this.use_type = type;
		this.slot = slot;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.use_type = UseType.values()[buf.readByte()];
		this.slot = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.use_type.ordinal());
		buf.writeByte(this.slot);
	}
	
	public static class HandlerServer implements IMessageHandler<PacketUseSpell, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUseSpell packet, MessageContext ctx)
		{
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			World world = player.worldObj;
			RAPlayerProperties rpp = RAPlayerProperties.get(player);
			
			Spell spell = rpp.spell_slots[packet.slot];
			if (spell == null) {
				return null;
			}
			
			if (!spell.canCastSpell(rpp)) {
				return null;
			}
			
			switch (packet.use_type) {
			case ACTIVATE:
				spell.onActivated(world, player, rpp);
				break;
			case TRIGGER_WHILE_ACTIVE:
				spell.onTriggeredWhileActive(world, player, rpp);
				break;
			case DEACTIVATE:
				spell.onDeactivated(world, player, rpp);
				break;
			}
			
			return null;
		}
	}
}
