package mrriegel.various.gui.jetpack;

import java.util.ArrayList;

import mrriegel.various.gui.CrunchItemContainer;
import mrriegel.various.gui.CrunchItemInventory;
import mrriegel.various.items.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

public class ContainerJetPack extends Container {
	ItemStack storedInv;
	InventoryPlayer playerInv;
	CrunchItemInventory inv;

	public ContainerJetPack(EntityPlayer player, InventoryPlayer playerInv,
			CrunchItemInventory inv) {
		storedInv = playerInv.getCurrentItem();
		this.playerInv = playerInv;
		this.inv = inv;
		if (storedInv != null && storedInv.getTagCompound() != null) {
			inv.setInventorySlotContents(0, ItemStack
					.loadItemStackFromNBT(storedInv.getTagCompound()
							.getCompoundTag("lava")));
		}
		this.addSlotToContainer(new Slot(inv, 0, 80, 13) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stackAllowed(stack);
			}

			@Override
			public void onSlotChanged() {
				super.onSlotChanged();
				change();
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

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getHeldItem() != null
				&& playerIn.getHeldItem().getItem() == ModItems.jetpack;
	}

	public void change() {
		if (!storedInv.hasTagCompound())
			storedInv.setTagCompound(new NBTTagCompound());
		NBTTagCompound n = new NBTTagCompound();
		if (inv.getStackInSlot(0) != null) {
			inv.getStackInSlot(0).writeToNBT(n);
			storedInv.getTagCompound().setTag("lava", n);
		}

		playerInv.mainInventory[playerInv.currentItem] = storedInv;
	}

	protected boolean stackAllowed(ItemStack stackInSlot) {
		return stackInSlot.getItem() == Items.lava_bucket;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return null;
	}

}
