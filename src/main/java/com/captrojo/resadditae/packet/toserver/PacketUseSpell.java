package com.captrojo.resadditae.packet.toserver;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.SpellTargetData;
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
		ACTIVATE_OTHER,
		TRIGGER_WHILE_ACTIVE,
		DEACTIVATE,
	}
	
	UseType use_type;
	int slot;
	SpellTargetData target;
	
	public PacketUseSpell()
	{
	}
	
	public PacketUseSpell(RAPlayerProperties rpp, UseType type)
	{
		this(rpp, type, 0);
	}
	
	public PacketUseSpell(RAPlayerProperties rpp, UseType type, int slot)
	{
		this.use_type = type;
		this.slot = slot;
		this.target = rpp.spell_target;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.use_type = UseType.values()[buf.readByte()];
		this.slot = buf.readByte();
		
		this.target = new SpellTargetData();
		this.target.deserialize(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.use_type.ordinal());
		buf.writeByte(this.slot);
		
		this.target.serialize(buf);
	}
	
	public static class HandlerServer implements IMessageHandler<PacketUseSpell, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUseSpell packet, MessageContext ctx)
		{
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			World world = player.worldObj;
			RAPlayerProperties rpp = RAPlayerProperties.get(player);
			rpp.spell_target = packet.target;
			
			Spell spell = rpp.spell_slots[packet.slot];
			if (spell == null) {
				return null;
			}
			
			switch (packet.use_type) {
			case ACTIVATE_OTHER:
				rpp.deactivateSpell();
				/* fallthrough */
			case ACTIVATE:
				rpp.activateSpell(packet.slot);
				break;
			case TRIGGER_WHILE_ACTIVE:
				rpp.triggerSpell();
				break;
			case DEACTIVATE:
				rpp.deactivateSpell();
				break;
			}
			
			return null;
		}
	}
}
