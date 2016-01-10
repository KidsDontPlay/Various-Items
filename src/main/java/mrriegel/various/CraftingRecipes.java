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
		GameRegistry.addRecipe(new ItemStack(Items.nether_star), "aaa", "aaa",
				"aaa", 'a', new ItemStack(ModItems.material, 9, 1));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.material, 9, 1),
				new ItemStack(Items.nether_star));
		GameRegistry.addRecipe(new ItemStack(Items.diamond), "aaa", "aaa",
				"aaa", 'a', new ItemStack(ModItems.material, 9, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.material, 9, 2),
				new ItemStack(Items.diamond));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.material, 1, 3),
				new ItemStack(ModItems.material, 1, 0), new ItemStack(
						ModItems.material, 1, 1), new ItemStack(
						ModItems.material, 1, 2));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.material, 9, 3),
				new ItemStack(ModItems.material, 1, 4));
		GameRegistry.addRecipe(new ItemStack(ModItems.material, 1, 4), "aaa",
				"aaa", "aaa", 'a', new ItemStack(ModItems.material, 1, 3));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.material, 1, 4),
				Items.ender_pearl, Items.diamond, Items.nether_star);
	}

}
