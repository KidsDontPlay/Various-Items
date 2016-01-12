package mrriegel.various.items;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.entity.EntityPebble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPebble extends Item {
	public ItemPebble() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":pebble");
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn,
			EntityPlayer playerIn, int timeLeft) {
		int i = this.getMaxItemUseDuration(stack) - timeLeft;
		float f = i / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f < 0.3D) {
			return;
		}

		if (!worldIn.isRemote) {
			worldIn.spawnEntityInWorld(new EntityPebble(worldIn, playerIn));
		}
		if (!playerIn.capabilities.isCreativeMode) {
			stack.stackSize--;
			if (stack.stackSize == 0)
				stack = null;
		}
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn,
			EntityPlayer playerIn) {
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		playerIn.setItemInUse(itemStackIn,
				this.getMaxItemUseDuration(itemStackIn));
		return itemStackIn;
	}
}
