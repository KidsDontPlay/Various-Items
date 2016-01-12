package mrriegel.various.init;

import mrriegel.various.VariousItems;
import mrriegel.various.blocks.BlockTravel;
import mrriegel.various.tile.TileTravel;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(value = VariousItems.MODID)
public class ModBlocks {

	public static final Block travelPort = new BlockTravel();

	public static void init() {
		GameRegistry.registerBlock(travelPort, "travelPort");
		
		GameRegistry.registerTileEntity(TileTravel.class, "tileTravelPort");
	}

}
