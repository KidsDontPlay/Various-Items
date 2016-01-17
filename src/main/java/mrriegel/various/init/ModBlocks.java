package mrriegel.various.init;

import mrriegel.various.VariousItems;
import mrriegel.various.blocks.BlockGlowsand;
import mrriegel.various.blocks.BlockKabel;
import mrriegel.various.blocks.BlockTotem;
import mrriegel.various.blocks.BlockTravel;
import mrriegel.various.tile.TileTotem;
import mrriegel.various.tile.TileTravel;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(value = VariousItems.MODID)
public class ModBlocks {

	public static final Block travelPort = new BlockTravel();
	public static final Block totem = new BlockTotem();
	public static final Block glowsand = new BlockGlowsand();
	public static final Block kabel = new BlockKabel();

	public static void init() {
		GameRegistry.registerBlock(travelPort, "travelPort");
		GameRegistry.registerBlock(totem, "totem");
		GameRegistry.registerBlock(glowsand, "glowsand");
		GameRegistry.registerBlock(kabel, "kabel");

		GameRegistry.registerTileEntity(TileTravel.class, "tileTravelPort");
		GameRegistry.registerTileEntity(TileTotem.class, "tileTotem");
	}

}
