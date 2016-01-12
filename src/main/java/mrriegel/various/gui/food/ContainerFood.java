package mrriegel.various.gui.food;

import mrriegel.various.gui.CrunchItemContainer;
import mrriegel.various.gui.CrunchItemInventory;
import mrriegel.various.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ContainerFood extends CrunchItemContainer {

	public ContainerFood(EntityPlayer player, InventoryPlayer playerInv,
			CrunchItemInventory inv) {
		super(player, playerInv, inv);
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(inv, i, 8 + 18 * i, 15) {

				@Override
				public void onSlotChanged() {
					super.onSlotChanged();
					slotChanged();
				}
			});
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9,
						8 + j * 18, 84 - 39 + i * 18));
			}
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18,
					142 - 39));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getHeldItem() != null
				&& playerIn.getHeldItem().getItem() == ModItems.foodBack;
	}

	@Override
	protected boolean stackAllowed(ItemStack stackInSlot) {
		return stackInSlot.getItem() instanceof ItemFood;
	}

}
