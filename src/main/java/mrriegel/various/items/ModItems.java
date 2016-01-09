package mrriegel.various.items;

import mrriegel.various.VariousItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(value = VariousItems.MODID)
public class ModItems {

	public static final Item jetpack = new ItemJetpack();
	public static final Item material = new ItemMaterial();

	public static void init() {
		GameRegistry.registerItem(jetpack, "jetpack");
		GameRegistry.registerItem(material, "material");
	}

}
