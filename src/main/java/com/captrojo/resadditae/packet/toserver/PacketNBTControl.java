package com.captrojo.resadditae.packet.toserver;

import java.io.IOException;

import com.captrojo.resadditae.tileentity.IControlReceiver;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * This is all stolen from HBM's Nuclear Tech Mod
 */
public class PacketNBTControl implements IMessage
{
	private PacketBuffer buffer;
	private int x;
	private int y;
	private int z;
	
	public PacketNBTControl()
	{
	}
	
	public PacketNBTControl(NBTTagCompound tag, int x, int y, int z)
	{
		this.buffer = new PacketBuffer(Unpooled.buffer());
		this.x = x;
		this.y = y;
		this.z = z;
		
		try {
			buffer.writeNBTTagCompoundToBuffer(tag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		
		if (this.buffer == null) {
			this.buffer = new PacketBuffer(Unpooled.buffer());
		}
		
		this.buffer.writeBytes(buf);
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.z);
		
		if (this.buffer == null) {
			this.buffer = new PacketBuffer(Unpooled.buffer());
		}
		
		buf.writeBytes(this.buffer);
	}
	
	public static class HandlerServer implements IMessageHandler<PacketNBTControl, IMessage>
	{
		@Override
		public IMessage onMessage(PacketNBTControl packet, MessageContext ctx)
		{
			World world = ctx.getServerHandler().playerEntity.worldObj;
			TileEntity te = world.getTileEntity(packet.x, packet.y, packet.z);
			
			/* Did I mention how much I dislike exceptions? */
			try {
				NBTTagCompound tag = packet.buffer.readNBTTagCompoundFromBuffer();
				if (tag == null) {
					return null;
				}
				((IControlReceiver) te).receiveControl(tag);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
				packet.buffer.release();
			}
			
			return null;
		}
	}
}
