package mrriegel.various.gui.filter;

import java.util.ArrayList;
import java.util.List;

import mrriegel.various.config.ConfigHandler;
import mrriegel.various.gui.CrunchItemInventory;
import mrriegel.various.helper.NBTHelper;
import mrriegel.various.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class ContainerFilter extends Container {

	InventoryPlayer playerInv;
	CrunchItemInventory inv;
	ItemStack storedInv;
	List<Integer> metas;

	class FilterSlot extends Slot {

		public FilterSlot(IInventory inventoryIn, int index, int xPosition,
				int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public void onSlotChanged() {
			super.onSlotChanged();
			slotChanged();
		}
	}

	public ContainerFilter(InventoryPlayer playerInv, CrunchItemInventory inv) {
		this.playerInv = playerInv;
		this.inv = inv;
		this.storedInv = playerInv.getCurrentItem();
		metas=new ArrayList<Integer>();
		if (storedInv != null && storedInv.getTagCompound() != null) {
			NBTTagList invList = storedInv.getTagCompound().getTagList(
					"crunchItem", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < invList.tagCount(); i++) {
				NBTTagCompound stackTag = invList.getCompoundTagAt(i);
				int slot = stackTag.getByte("Slot");
				if (slot >= 0 && slot < inv.getInv().length) {
					inv.setInventorySlotContents(slot,
							ItemStack.loadItemStackFromNBT(stackTag));
				}
			}
			NBTTagList metaList = storedInv.getTagCompound().getTagList(
					"metas", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < invList.tagCount(); i++) {
				NBTTagCompound meta=invList.getCompoundTagAt(i);
				metas.set(i, meta.getInteger("meta"));
			}
		}
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(inv, i, 4 + 18 * i, 13));
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9,
						8 + j * 18, 84 - 39 + 15 + i * 18));
			}
		}
		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18,
					142 - 39 + 15));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getHeldItem() != null
				&& playerIn.getHeldItem().getItem() == ModItems.filter;
	}

	public void slotChanged() {
		writeInv(storedInv);
		playerInv.mainInventory[playerInv.currentItem] = storedInv;
	}

	private void writeInv(ItemStack con2) {
		if (!con2.hasTagCompound())
			con2.setTagCompound(new NBTTagCompound());
		NBTTagList invList = new NBTTagList();
		for (int i = 0; i < inv.getInv().length; i++) {
			if (inv.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				inv.getStackInSlot(i).writeToNBT(stackTag);
				invList.appendTag(stackTag);
			}
		}
		con2.getTagCompound().setTag("crunchItem", invList);

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		Slot slot = this.inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();

			if (slotIndex <= 8) {
				slot.putStack(null);
			} else {
				for(int i=0;i<9;i++){
					ItemStack s=inventorySlots.get(i).getStack();
					
				}

			}
		}

		return null;
	}

}
