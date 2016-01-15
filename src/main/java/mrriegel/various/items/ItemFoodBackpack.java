package mrriegel.various.items;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.gui.CrunchItemInventory;
import mrriegel.various.gui.food.ContainerFood;
import mrriegel.various.gui.food.InventoryFood;
import mrriegel.various.handler.GuiHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemFoodBackpack extends Item {
	public ItemFoodBackpack() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(VariousItems.MODID + ":foodBackpack");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		playerIn.openGui(VariousItems.instance, GuiHandler.FOOD, worldIn, 0, 0,
				0);
		return super.onItemRightClick(itemStackIn, worldIn, playerIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn,
			int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if (worldIn.getTotalWorldTime() % 20 != 0)
			return;
		CrunchItemInventory inv = new InventoryFood(stack);
		EntityPlayer player = (EntityPlayer) entityIn;
		if (!player.capabilities.isCreativeMode
				&& player.getFoodStats().needFood()
				&& !(player.openContainer instanceof ContainerFood)) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stackfood = inv.getStackInSlot(i);
				if (stackfood != null
						&& stackfood.getItem() instanceof ItemFood) {
					ItemFood iff = (ItemFood) stackfood.getItem();
					iff.onItemUseFinish(stackfood, worldIn, player);
					if (stackfood.stackSize == 0)
						inv.setInventorySlotContents(i, null);
					break;

				}
			}
			NBTTagList invList = new NBTTagList();
			for (int i = 0; i < inv.getInv().length; i++) {
				if (inv.getStackInSlot(i) != null) {
					NBTTagCompound stackTag = new NBTTagCompound();
					stackTag.setByte("Slot", (byte) i);
					inv.getStackInSlot(i).writeToNBT(stackTag);
					invList.appendTag(stackTag);
				}
			}
			stack.getTagCompound().setTag("crunchItem", invList);
		}
	}

}
