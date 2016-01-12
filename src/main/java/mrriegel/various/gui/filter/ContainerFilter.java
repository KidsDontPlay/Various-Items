package mrriegel.various.gui.filter;

import java.util.HashMap;
import java.util.Map;

import mrriegel.various.gui.CrunchItemInventory;
import mrriegel.various.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
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
	public Map<Integer, Boolean> metas;

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
		metas = new HashMap<Integer, Boolean>();
		for (int i = 0; i < 9; i++)
			metas.put(i, true);
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
			for (int i = 0; i < metaList.tagCount(); i++) {
				NBTTagCompound meta = metaList.getCompoundTagAt(i);
				metas.put(i, meta.getBoolean("meta"));
			}
		}
		for (int i = 0; i < 9; i++)
			this.addSlotToContainer(new Slot(inv, i, 8 + 18 * i, 15));
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
		NBTTagList metaList = new NBTTagList();
		for (int i = 0; i < 9; i++) {
			NBTTagCompound meta = new NBTTagCompound();
			meta.setBoolean("meta", metas.get(i));
			metaList.appendTag(meta);
		}
		con2.getTagCompound().setTag("metas", metaList);

	}

	@Override
	public ItemStack slotClick(int slotId, int clickedButton, int mode,
			EntityPlayer playerIn) {
		System.out.println(String.format("%d %d %d", slotId, clickedButton,
				mode));
		if (slotId >= 9 || slotId == -999)
			return super.slotClick(slotId, clickedButton, mode, playerIn);
		if (playerInv.getItemStack() == null) {
			getSlot(slotId).putStack(null);
		} else {
			ItemStack s = playerInv.getItemStack().copy();
			if (!in(s)) {
				s.stackSize = 1;
				getSlot(slotId).putStack(s);
			}
		}
		return playerInv.getItemStack();

	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		Slot slot = this.inventorySlots.get(slotIndex);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();

			if (slotIndex <= 8) {
				slot.putStack(null);
			} else {
				int s = getSlot(itemstack1);
				if (s != -1) {
					ItemStack in = itemstack1.copy();
					in.stackSize = 1;
					getSlot(s).putStack(in);
					getSlot(s).onSlotChanged();
					slotChanged();
				}

			}
		}
		return null;
	}

	int getSlot(ItemStack stack) {
		if (in(stack))
			return -1;
		for (int i = 0; i < 9; i++) {
			if (!getSlot(i).getHasStack())
				return i;
		}
		return -1;
	}

	boolean in(ItemStack stack) {
		for (int i = 0; i < 9; i++) {
			if (getSlot(i).getHasStack()
					&& getSlot(i).getStack().isItemEqual(stack))
				return true;
		}
		return false;
	}
}
