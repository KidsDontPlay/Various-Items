package mrriegel.various.blocks;

import mrriegel.various.VariousItems;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(value = VariousItems.MODID)
public class ModBlocks {

	public static final Block travel = new BlockTravel();

	public static void init() {
		GameRegistry.registerBlock(travel, "travel");
	}

}
