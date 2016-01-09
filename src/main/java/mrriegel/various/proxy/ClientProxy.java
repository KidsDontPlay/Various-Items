package mrriegel.various.proxy;

import mrriegel.various.VariousItems;
import mrriegel.various.handler.KeyHandler;
import mrriegel.various.items.ModItems;
import mrriegel.various.render.RenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(
				ModItems.jetpack,
				0,
				new ModelResourceLocation(VariousItems.MODID + ":jetpack",
						"inventory"));
		for (int i = 0; i < ModItems.material.number; i++) {
			ModelBakery.registerItemVariants(ModItems.material,
					new ResourceLocation(VariousItems.MODID + ":" + "material_"
							+ i));
			renderItem.getItemModelMesher().register(
					ModItems.material,
					i,
					new ModelResourceLocation(VariousItems.MODID + ":material_"
							+ i, "inventory"));

		}
	}

}
