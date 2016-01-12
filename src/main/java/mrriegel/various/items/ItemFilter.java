package mrriegel.various.items;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.handler.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemFilter extends Item {
	public ItemFilter() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":filter");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		playerIn.openGui(VariousItems.instance, GuiHandler.FILTER, worldIn, 0,
				0, 0);
		return super.onItemRightClick(itemStackIn, worldIn, playerIn);
	}

	public static boolean getMeta(ItemStack stack, int slot) {
		return stack.getTagCompound()
				.getTagList("metas", Constants.NBT.TAG_COMPOUND)
				.getCompoundTagAt(slot).getBoolean("meta");
	}
}
