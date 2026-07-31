package com.captrojo.resadditae.container;

import com.captrojo.resadditae.container.slot.SlotDummy;
import com.captrojo.resadditae.container.slot.SlotWand;
import com.captrojo.resadditae.entity.properties.RAPlayerProperties;
import com.captrojo.resadditae.gui.container.GuiSpellMenu;
import com.captrojo.resadditae.magic.spell.Spell;
import com.captrojo.resadditae.magic.spell.Spells;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSpellMenu extends Container implements IHasSelectionInput
{
	/* The 'END' slots are the index of the next slot.
	 * This is for the item stack shift-click code
	 */
	static final int INV_SLOT_FIRST = 0;
	static final int INV_SLOT_LAST = 35;
	static final int WAND_SLOT = 36;
	static final int SPELL_SLOT_FIRST = 37;
	static final int SPELL_SLOT_LAST = 42;
	
	RAPlayerProperties rpp;
	IInventory wand_inv;
	
	public ContainerSpellMenu(InventoryPlayer inventory, RAPlayerProperties rpp)
	{
		this.rpp = rpp;
		this.wand_inv = new InventoryBasic("Wand", false, 1);
		this.wand_inv.setInventorySlotContents(0, rpp.wand_item);
		
		/* Inventory */
		for (int h = 0; h < 3; h++) {
			for (int w = 0; w < 9; w++) {
				this.addSlotToContainer(new Slot(
					inventory, 9 + w + (h * 9),
					GuiSpellMenu.INV_HOTBAR_X + (w * 18),
					GuiSpellMenu.INV_Y + (h * 18)
				));
			}
		}
		
		/* Hotbar */
		for (int w = 0; w < 9; w++) {
			this.addSlotToContainer(new Slot(
				inventory, w,
				GuiSpellMenu.INV_HOTBAR_X + (w * 18),
				GuiSpellMenu.HOTBAR_Y
			));
		}
		
		/* Wand Slot */
		this.addSlotToContainer(new SlotWand(this.wand_inv, 0, 12, 106));
		
		/* Spell Slots */
		for (int i = 0; i < 6; i++) {
			this.addSlotToContainer(new SlotDummy(i, 40 + (i * 18), 110));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int src_slot)
	{
		return null;
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		if (player.worldObj.isRemote) {
			return;
		}
		this.rpp.onWandChanged(this.wand_inv.getStackInSlot(0));
	}

	@Override
	public void makeSelection(int idx, int val)
	{
		Spell spell = Spells.getByID(val);
		this.rpp.spell_slots[idx] = spell;
	}
}
