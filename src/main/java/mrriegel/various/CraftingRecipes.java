package mrriegel.various;

import mrriegel.various.items.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingRecipes {

	public static void init() {
		GameRegistry.addRecipe(new ItemStack(Items.ender_pearl), "aaa", "aaa",
				"aaa", 'a', new ItemStack(ModItems.material, 9, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.material, 9, 0),
				new ItemStack(Items.ender_pearl));
		fix
		GameRegistry.addRecipe(new ItemStack(ModItems.material, 9, 1), "aaa",
				"aaa", "aaa", 'a', Items.nether_star);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.nether_star),
				new ItemStack(ModItems.material, 9, 1));
		GameRegistry.addRecipe(new ItemStack(ModItems.material, 9, 2), "aaa",
				"aaa", "aaa", 'a', Items.diamond);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond),
				new ItemStack(ModItems.material, 9, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.material, 1, 3),
				new ItemStack(ModItems.material, 1, 0), new ItemStack(
						ModItems.material, 1, 1), new ItemStack(
						ModItems.material, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond),
				new ItemStack(ModItems.material, 9, 2));
	}

}
