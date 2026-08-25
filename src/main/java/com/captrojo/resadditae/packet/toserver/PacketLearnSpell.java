package com.captrojo.resadditae.packet.toserver;

import com.captrojo.resadditae.extprop.RAPlayerProperties;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;
import com.captrojo.resadditae.main.Alerts;
import com.captrojo.resadditae.packet.toclient.PacketDisplayAlert;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketLearnSpell implements IMessage
{
	int spell_id;
	
	public PacketLearnSpell()
	{
	}
	
	public PacketLearnSpell(Spell spell)
	{
		this.spell_id = spell.getID();
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.spell_id = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.spell_id);
	}
	
	public static class HandlerServer implements IMessageHandler<PacketLearnSpell, IMessage>
	{
		@Override
		public IMessage onMessage(PacketLearnSpell packet, MessageContext ctx)
		{
			Spell spell = Spells.getByID(packet.spell_id);
			if (spell == null) {
				return null;
			}
			
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			RAPlayerProperties rpp = RAPlayerProperties.get(player);
			rpp.learnNewSpell(spell);
			
			return new PacketDisplayAlert(Alerts.SPELL_LEARNED, packet.spell_id);
		}
	}
}
