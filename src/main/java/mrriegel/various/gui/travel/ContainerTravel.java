package mrriegel.various.gui.travel;

import mrriegel.various.init.ModItems;
import mrriegel.various.tile.CrunchTEInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTravel extends Container {
	InventoryPlayer playerInv;
	CrunchTEInventory inv;

	public ContainerTravel(InventoryPlayer playerInv, CrunchTEInventory inv) {
		this.playerInv = playerInv;
		this.inv = inv;
		this.addSlotToContainer(new Slot(inv, 0, 16, 21));
		this.addSlotToContainer(new Slot(inv, 1, 80, 13));
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9,
						8 + j * 18, 84 - 39 + i * 18));
			}
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142 - 39));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getCurrentArmor(2) != null
				&& playerIn.getCurrentArmor(2).getItem() == ModItems.jetpack;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex <= 1) {
				if (!this.mergeItemStack(itemstack1, 2, 36, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else {
				boolean merged = false;
				if (itemstack1.isItemEqual(new ItemStack(ModItems.material))
						&& this.mergeItemStack(itemstack1, 0, 1, false)) {
					merged = true;
				}

				if (!merged && itemstack1.getItem() == ModItems.travelRecipe
						&& this.mergeItemStack(itemstack1, 1, 2, false))
					merged = true;
				if (!merged)
					return null;

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
