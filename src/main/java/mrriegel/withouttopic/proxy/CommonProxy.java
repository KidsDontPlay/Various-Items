package mrriegel.withouttopic.proxy;

import mrriegel.withouttopic.ConfigurationHandler;
import mrriegel.withouttopic.CraftingRecipes;
import mrriegel.withouttopic.WithoutTopic;
import mrriegel.withouttopic.blocks.ModBlocks;
import mrriegel.withouttopic.handler.GuiHandler;
import mrriegel.withouttopic.items.ModItems;
import mrriegel.withouttopic.network.PacketHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.refreshConfig(event
				.getSuggestedConfigurationFile());
		PacketHandler.init();
	}

	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(WithoutTopic.instance,
				new GuiHandler());
		ModBlocks.init();
		ModItems.init();
		CraftingRecipes.init();
	}

	public void postInit(FMLPostInitializationEvent event) {

	}

}
