package com.captrojo.resadditae.container;

import com.captrojo.resadditae.crafting.StonecutterRecipes;
import com.captrojo.resadditae.main.ResAdditae;
import com.captrojo.resadditae.tileentity.TEStonecutter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerStonecutter extends Container
{
	public static final int INPUT_SLOT = 0;
	public static final int RESULT_SLOT = 1;
	public static final int INVENTORY_SLOT_START = 2;
	public static final int INVENTORY_SLOT_END = 38;

	private TEStonecutter te;
	private World world;

	public InventoryCrafting craft_input;
	public InventoryCraftResult craft_result;

	public ContainerStonecutter(InventoryPlayer player_inventory, TEStonecutter tile_entity)
	{
		this.te = tile_entity;
		this.te.container = this;
		this.world = te.getWorldObj();

		this.craft_input = new InventoryCrafting(this, 1, 1);
		this.craft_result = new InventoryCraftResult();

		this.addSlotToContainer(new Slot(this.craft_input, INPUT_SLOT, 76, 116));
		this.addSlotToContainer(new SlotCrafting(player_inventory.player, this.craft_input, this.craft_result, RESULT_SLOT, 138, 116));

		/* Inventory */
		for (int h = 0; h < 3; h++) {
			for (int w = 0; w < 9; w++) {
				this.addSlotToContainer(new Slot(player_inventory, 9 + w + (h * 9), 35 + (w * 18), 164 + (h * 18)));
			}
		}

		/* Hotbar */
		for (int w = 0; w < 9; w++) {
			this.addSlotToContainer(new Slot(player_inventory, w, 35 + (w * 18), 222));
		}

		this.onCraftMatrixChanged(this.craft_input);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventory)
	{
		if (this.te.selection == -1) {
			this.craft_result.setInventorySlotContents(0, null);
			this.detectAndSendChanges();
			return;
		}

		ItemStack[] outputs = StonecutterRecipes.getOutputsFromInput(this.getSlot(INPUT_SLOT).getStack());
		if (this.te.selection >= outputs.length) {
			this.craft_result.setInventorySlotContents(0, null);
			this.detectAndSendChanges();
			return;
		}

		this.craft_result.setInventorySlotContents(0, outputs[this.te.selection].copy());
		this.detectAndSendChanges();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);

		if (this.world.isRemote) {
			return;
		}

		ItemStack stack = this.craft_input.getStackInSlot(0);
		if (stack != null) {
			player.dropPlayerItemWithRandomChoice(stack, false);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int src_slot)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(src_slot);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (src_slot == INPUT_SLOT) {
				if (!this.mergeItemStack(itemstack1, INVENTORY_SLOT_START, INVENTORY_SLOT_END, false)) {
					return null;
				}
			} else if (src_slot == RESULT_SLOT) {
				if (!this.mergeItemStack(itemstack1, INVENTORY_SLOT_START, INVENTORY_SLOT_END, true)) {
					return null;
				}
			} else if (INVENTORY_SLOT_START <= src_slot && src_slot < INVENTORY_SLOT_END) {
				if (!this.mergeItemStack(itemstack1, INPUT_SLOT, INPUT_SLOT + 1, false)) {
					return null;
				}
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}
}
