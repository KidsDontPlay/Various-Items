package mrriegel.various.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.handler.GuiHandler;

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
}
