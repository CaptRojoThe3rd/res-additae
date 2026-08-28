package com.captrojo.resadditae.tileentity;

import java.util.Random;

import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.sounds.ModSounds;
import com.captrojo.resadditae.util.ItemHlpr;
import com.captrojo.resadditae.util.MiscHlpr;
import com.captrojo.resadditae.util.NBTHlpr;
import com.captrojo.resadditae.world.loot.LootGroup;
import com.captrojo.resadditae.world.loot.LootItem;
import com.captrojo.resadditae.world.loot.LootPool;
import com.captrojo.resadditae.world.loot.ModLoot;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class TEVault extends TileEntity
{
	@SideOnly(Side.CLIENT) public EntityItem render_item;
	@SideOnly(Side.CLIENT) private int render_timer;
	
	public static enum State
	{
		INACTIVE,
		DISPENSING,
		EMPTY
	}
	
	private State state;
	private State prev_state;
	private boolean changed_state;
	
	private LootGroup loot;
	private ItemStack[] items_to_drop;
	private int drop_idx;
	private int drop_delay;
	
	public TEVault()
	{
		this(null);
	}
	
	public TEVault(LootGroup loot)
	{
		this.state = State.INACTIVE;
		this.loot = loot;
	}
	
	public void setState(State state)
	{
		this.state = state;
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	public boolean hasLoot()
	{
		return this.state != State.EMPTY;
	}
	
	public void dispenseItems()
	{
		if (this.loot == null) {
			this.loot = ModLoot.snow_dungeon_vaults[this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)];
		}
		this.items_to_drop = this.loot.getRandomItems(this.worldObj.rand);
		this.drop_idx = 0;
		this.drop_delay = 20;
		this.setState(State.DISPENSING);
		this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, ModSounds.VAULT_OPEN, 1.0f, 1.0f);
	}
	
	protected void updateServer()
	{
		if (this.state != State.DISPENSING) {
			return;
		}

		this.drop_delay--;
		if (this.drop_delay <= 0) {
			if (this.drop_idx >= this.items_to_drop.length) {
				this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, ModSounds.VAULT_CLOSE, 1.0f, 1.0f);
				this.setState(State.EMPTY);
				return;
			}
			ItemStack item = this.items_to_drop[this.drop_idx].copy();
			ItemHlpr.spawnEntityItemFromBlock(item, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			this.drop_idx++;
			this.drop_delay = 20;
			this.worldObj.playSoundEffect(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5, ModSounds.VAULT_DISPENSE, 1.0f, 1.0f);
		}
	}
	
	@SideOnly(Side.CLIENT)
	protected ItemStack getRandomItemForRender(Random rand)
	{
		if (this.loot == null || this.loot.pools.size() == 0) {
			this.loot = ModLoot.snow_dungeon_vaults[this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)];
		}
		for (int q = 0; q < 100; q++) {
			LootPool pool = (LootPool) MiscHlpr.getRandomElementFromList(this.loot.pools, rand);
			if (pool.items.size() == 0) {
				continue;
			}
			LootItem item = (LootItem) MiscHlpr.getRandomElementFromList(pool.items, rand);
			ItemStack stack = item.generateItemStack(rand);
			if (stack == null) {
				continue;
			}
			stack = stack.copy();
			stack.stackSize = 1;
			return stack;
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	protected void updateClient()
	{
		if (this.changed_state) {
			this.changed_state = false;
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		if (this.state == State.EMPTY) {
			return;
		}
		
		this.render_timer--;
		if (this.render_timer <= 0) {
			this.render_timer = 40;
			if (this.render_item == null) {
				this.render_item = new EntityItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
			this.render_item.setEntityItemStack(this.getRandomItemForRender(this.worldObj.rand));
		}
		
		if (this.render_item != null) {
			this.render_item.onUpdate();
		}
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (this.worldObj.isRemote) {
			this.updateClient();
		} else {
			this.updateServer();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		this.prev_state = this.state;
		this.state = State.values()[tag.getByte("State")];
		if (this.prev_state != this.state) {
			this.changed_state = true;
		}
		
		if (tag.hasKey("ItemsToDrop")) {
			NBTTagList list = tag.getTagList("ItemsToDrop", NBT.TAG_COMPOUND);
			this.items_to_drop = new ItemStack[list.tagCount()];
			for (int i = 0; i < this.items_to_drop.length; i++) {
				this.items_to_drop[i] = NBTHlpr.loadItemStackFromNBT(list.getCompoundTagAt(i));
			}
			this.drop_idx = tag.getInteger("DropIdx");
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		tag.setByte("State", (byte) this.state.ordinal());
		
		if (this.state == State.DISPENSING) {
			NBTTagList list = new NBTTagList();
			for (ItemStack stack : this.items_to_drop) {
				list.appendTag(NBTHlpr.saveItemStackToNBT(stack));
			}
			tag.setTag("ItemsToDrop", list);
			tag.setInteger("DropIdx", this.drop_idx);
		}
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
	}
}
