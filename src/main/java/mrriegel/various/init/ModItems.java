package mrriegel.various.init;

import mrriegel.various.VariousItems;
import mrriegel.various.items.ItemFilter;
import mrriegel.various.items.ItemJetpack;
import mrriegel.various.items.ItemMaterial;
import mrriegel.various.items.ItemPebble;
import mrriegel.various.items.ItemSigmaPick;
import mrriegel.various.items.ItemTravelRecipe;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(value = VariousItems.MODID)
public class ModItems {

	public static final Item jetpack = new ItemJetpack();
	public static final Item sigmaPick = new ItemSigmaPick();
	public static final Item material = new ItemMaterial();
	public static final Item travelRecipe = new ItemTravelRecipe();
	public static final Item pebble = new ItemPebble();
	public static final Item filter = new ItemFilter();

	public static void init() {
		GameRegistry.registerItem(jetpack, "jetpack");
		GameRegistry.registerItem(sigmaPick, "sigmaPick");
		GameRegistry.registerItem(material, "material");
		GameRegistry.registerItem(travelRecipe, "travelRecipe");
		GameRegistry.registerItem(pebble, "pebble");
		GameRegistry.registerItem(filter, "filter");
	}

}
