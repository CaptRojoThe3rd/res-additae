package com.captrojo.resadditae.tileentity;

import com.captrojo.resadditae.container.ContainerStonecutter;
import com.captrojo.resadditae.gui.IGuiProvider;
import com.captrojo.resadditae.gui.container.GuiStonecutter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TEStonecutter extends TileEntity implements IControlReceiver, IGuiProvider
{
	public int selection;
	public ContainerStonecutter container;
	
	public TEStonecutter()
	{
		this.selection = -1;
	}
	
	public void notifyContainer()
	{
		if (this.container != null) {
			this.container.onCraftMatrixChanged(this.container.craft_input);
		}
	}
	
	@Override
	public void receiveControl(NBTTagCompound tag)
	{
		this.selection = tag.getInteger("selection");
		this.notifyContainer();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.selection = tag.getInteger("selection");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		tag.setInteger("selection", this.selection);
	}

	@Override
	public Object getContainer(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		return new ContainerStonecutter(player.inventory, this);
	}

	@Override
	public Object getGui(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		return new GuiStonecutter(player.inventory, this);
	}
}
