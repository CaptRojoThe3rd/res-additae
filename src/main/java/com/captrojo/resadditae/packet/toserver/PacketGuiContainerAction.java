package com.captrojo.resadditae.packet.toserver;

import com.captrojo.resadditae.container.IHasSelectionInput;
import com.captrojo.resadditae.main.ResAdditae;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class PacketGuiContainerAction implements IMessage
{
	public static enum Action
	{
		OPEN,
		SELECT
	}
	
	Action action;
	int idx;
	int val;
	
	public PacketGuiContainerAction()
	{
	}
	
	public PacketGuiContainerAction(Action action, int idx, int val)
	{
		this.action = action;
		this.idx = idx;
		this.val = val;
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.action = Action.values()[buf.readByte()];
		this.idx = buf.readInt();
		this.val = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeByte(this.action.ordinal());
		buf.writeInt(this.idx);
		buf.writeInt(this.val);
	}
	
	public static class HandlerServer implements IMessageHandler<PacketGuiContainerAction, IMessage>
	{
		@Override
		public IMessage onMessage(PacketGuiContainerAction packet, MessageContext ctx)
		{
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			if (packet.action == Action.OPEN) {
				int x = (int) player.posX;
				int y = (int) player.posY;
				int z = (int) player.posZ;
				player.openGui(ResAdditae.instance, packet.val, player.worldObj, x, y, z);
			} else if (packet.action == Action.SELECT) {
				Container container = player.openContainer;
				if (!(container instanceof IHasSelectionInput)) {
					ResAdditae.LOG.error("Tried to make selection in container that does not accept selections!");
					ResAdditae.LOG.error(String.format(
						"%s, %d, %d",
						container.getClass().getSimpleName(),
						packet.idx, packet.val
					));
					return null;
				}
				((IHasSelectionInput) container).makeSelection(packet.idx, packet.val);
			}
			return null;
		}
	}
}
