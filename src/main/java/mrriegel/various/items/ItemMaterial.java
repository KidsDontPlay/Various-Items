package mrriegel.various.items;

import java.util.List;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMaterial extends Item {
	private final int number = 6;

	public ItemMaterial() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(VariousItems.MODID + ":material");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < number; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}
}
