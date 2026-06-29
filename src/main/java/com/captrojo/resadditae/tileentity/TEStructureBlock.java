package com.captrojo.resadditae.tileentity;

import com.captrojo.resadditae.block.ModBlocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TEStructureBlock extends TileEntity
{
	/* 0-63 */
	public int idx;
	
	public Block stored_block;
	public short stored_block_meta;
	
	public TEStructureBlock()
	{
		this.idx = 0;
	}
	
	private void update()
	{
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		this.markDirty();
	}
	
	public void reset()
	{
		this.idx = 0;
		this.stored_block = null;
		this.stored_block_meta = 0;
		this.update();
	}
	
	public void incIdx()
	{
		this.idx = (this.idx + 1) & 0x3f;
		this.update();
	}
	
	public void setStoredBlock(ItemStack stack, int sb_meta, int side)
	{
		this.stored_block = Block.getBlockFromItem(stack.getItem());
		if (sb_meta == 2 && side >= 2) {
			this.stored_block_meta = (short) side;
		} else {
			this.stored_block_meta = (short) stack.getItemDamage();
		}
		this.update();
	}
	
	public void readDataFromNBT(NBTTagCompound tag)
	{
		this.idx = tag.getByte("data_idx");
		
		if (tag.hasKey("stored_block_name")) {
			UniqueIdentifier uidr = new UniqueIdentifier(tag.getString("stored_block_name"));
			this.stored_block = GameRegistry.findBlock(uidr.modId, uidr.name);
			this.stored_block_meta = tag.getShort("stored_block_meta");
		}
	}
	
	public void writeDataToNBT(NBTTagCompound tag)
	{
		tag.setByte("data_idx", (byte) this.idx);
		
		if (this.stored_block != null) {
			tag.setString("stored_block_name", GameRegistry.findUniqueIdentifierFor(this.stored_block).toString());
			tag.setShort("stored_block_meta", (short) this.stored_block_meta);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.readDataFromNBT(tag);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		this.writeDataToNBT(tag);
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		this.writeDataToNBT(tag);
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tag);
		return packet;
	}
	
	@Override
	public void onDataPacket(NetworkManager netmgr, S35PacketUpdateTileEntity packet)
	{
		this.readDataFromNBT(packet.func_148857_g());
	}
}
