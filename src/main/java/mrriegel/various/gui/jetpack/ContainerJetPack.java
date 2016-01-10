package mrriegel.various.gui.jetpack;

import mrriegel.various.VariousItems;
import mrriegel.various.config.ConfigHandler;
import mrriegel.various.gui.CrunchItemInventory;
import mrriegel.various.handler.GuiHandler;
import mrriegel.various.helper.NBTHelper;
import mrriegel.various.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerJetPack extends Container {
	InventoryPlayer playerInv;
	CrunchItemInventory inv;

	public ContainerJetPack(InventoryPlayer playerInv, CrunchItemInventory inv) {
		this.playerInv = playerInv;
		this.inv = inv;
		this.addSlotToContainer(new Slot(inv, 0, 80, 13) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stackAllowed(stack);
			}

			@Override
			public void onSlotChanged() {
				super.onSlotChanged();
				change(this);
			}
		});
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

	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);

		if (!playerIn.worldObj.isRemote) {

			ItemStack itemstack = this.inv.removeStackFromSlot(0);

			if (itemstack != null) {
				playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
			}

		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getCurrentArmor(2) != null
				&& playerIn.getCurrentArmor(2).getItem() == ModItems.jetpack;
	}

	public void change(Slot slot) {
		ItemStack stack = playerInv.armorItemInSlot(2);
		ItemStack lava = slot.getStack();
		if (lava == null)
			return;
		if (lava.getItem() == Items.lava_bucket
				&& NBTHelper.getInt(stack, "fuel")
						+ ConfigHandler.fuelValueLava <= ConfigHandler.jetpackMaxFuel) {
			NBTHelper.setInteger(stack, "fuel", NBTHelper.getInt(stack, "fuel")
					+ ConfigHandler.fuelValueLava);
			slot.inventory.setInventorySlotContents(slot.getSlotIndex(),
					new ItemStack(Items.bucket));
		}
	}

	protected boolean stackAllowed(ItemStack stackInSlot) {
		return stackInSlot.getItem() == Items.lava_bucket;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex == 0) {
				if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
					return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else {
				boolean merged = false;
				if (stackAllowed(itemstack1)
						&& this.mergeItemStack(itemstack1, 0, 1, false)) {
					merged = true;
				}

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
