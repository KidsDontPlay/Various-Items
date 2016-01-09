package mrriegel.various;

import mrriegel.various.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipes {

	public static void init() {
		GameRegistry.addRecipe(new ItemStack(ModItems.material, 9, 0), "aaa",
				"aaa", "aaa", 'a', Items.ender_pearl);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.ender_pearl),
				new ItemStack(ModItems.material, 9, 0));
		GameRegistry.addRecipe(new ItemStack(ModItems.material, 9, 1), "aaa",
				"aaa", "aaa", 'a', Items.nether_star);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.nether_star),
				new ItemStack(ModItems.material, 9, 1));
		GameRegistry.addRecipe(new ItemStack(ModItems.material, 9, 2), "aaa",
				"aaa", "aaa", 'a', Items.diamond);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond),
				new ItemStack(ModItems.material, 9, 2));
	}

}
