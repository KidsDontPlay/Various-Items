package mrriegel.various.proxy;

import mrriegel.various.VariousItems;
import mrriegel.various.blocks.ModBlocks;
import mrriegel.various.handler.KeyHandler;
import mrriegel.various.items.ItemMaterial;
import mrriegel.various.items.ModItems;
import mrriegel.various.render.RenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		KeyHandler.init();
		MinecraftForge.EVENT_BUS.register(new RenderEvents());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@Override
	public void registerRenderers() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem()
				.getItemModelMesher();
		mesher.register(ModItems.jetpack, 0, new ModelResourceLocation(
				VariousItems.MODID + ":jetpack", "inventory"));
		mesher.register(ModItems.sigmaPick, 0, new ModelResourceLocation(
				VariousItems.MODID + ":sigmaPick", "inventory"));
		mesher.register(ModItems.travel, 0, new ModelResourceLocation(
				VariousItems.MODID + ":travelSheet", "inventory"));
		mesher.register(Item.getItemFromBlock(ModBlocks.travel), 0,
				new ModelResourceLocation(VariousItems.MODID + ":travel",
						"inventory"));
		for (int i = 0; i < ItemMaterial.NUMBER; i++) {
			ModelBakery.registerItemVariants(ModItems.material,
					new ResourceLocation(VariousItems.MODID + ":" + "material_"
							+ i));
			mesher.register(ModItems.material, i, new ModelResourceLocation(
					VariousItems.MODID + ":material_" + i, "inventory"));

		}
	}

}
