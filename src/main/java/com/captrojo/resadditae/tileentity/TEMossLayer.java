package com.captrojo.resadditae.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class TEMossLayer extends TileEntity
{
	public static final int BOTTOM = 0;
	public static final int TOP = 1;
	public static final int NORTH = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;
	public static final int EAST = 5;
	
	public static boolean isSideSupportedAt(IBlockAccess world, int x, int y, int z, int side)
	{
		switch (side) {
		case 0:
			y++;
			break;
		case 1:
			y--;
			break;
		case 2:
			z++;
			break;
		case 3:
			z--;
			break;
		case 4:
			x++;
			break;
		case 5:
			x--;
			break;
		}
		return world.getBlock(x, y, z).isOpaqueCube();
	}

	public int[] layer_counts;

	public TEMossLayer()
	{
		this.layer_counts = new int[6];
		for (int i = 0; i < 6; i++) {
			this.layer_counts[i] = 0;
		}
	}

	public void triggerUpdate()
	{
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		this.markDirty();
	}
	
	public boolean isSideSupported(int side)
	{
		return isSideSupportedAt(this.worldObj, this.xCoord, this.yCoord, this.zCoord, side);
	}
	
	public boolean addLayer(int side)
	{
		if (this.layer_counts[side] >= 3 || !this.isSideSupported(side)) {
			return false;
		}
		this.layer_counts[side]++;
		this.triggerUpdate();
		return true;
	}
	
	public void setLayersAt(int side, int layers)
	{
		this.layer_counts[side] = layers;
		this.triggerUpdate();
	}

	@Override
	public boolean canUpdate()
	{
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		short m = tag.getShort("m");
		this.layer_counts = new int[6];
		for (int i = 0; i < 6; i++) {
			this.layer_counts[i] = m & 0x3;
			m >>= 2;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		short m = 0;
		for (int i = 5; i >= 0; i--) {
			m <<= 2;
			m |= this.layer_counts[i] & 0x3;
		}
		tag.setShort("m", m);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		this.readFromNBT(packet.func_148857_g());
		this.triggerUpdate();
	}
}
