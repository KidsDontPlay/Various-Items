package mrriegel.various.proxy;

import mrriegel.various.VariousItems;
import mrriegel.various.entity.EntityDecoy;
import mrriegel.various.entity.EntityPebble;
import mrriegel.various.handler.KeyHandler;
import mrriegel.various.init.ModBlocks;
import mrriegel.various.init.ModItems;
import mrriegel.various.items.ItemMaterial;
import mrriegel.various.render.PebbleRender;
import mrriegel.various.render.RenderDecoy;
import mrriegel.various.render.RenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		registerRenderers();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		KeyHandler.init();
		registerItemModels();

		MinecraftForge.EVENT_BUS.register(new RenderEvents());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	public void registerItemModels() {
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem()
				.getItemModelMesher();
		mesher.register(ModItems.jetpack, 0, new ModelResourceLocation(
				VariousItems.MODID + ":jetpack", "inventory"));
		mesher.register(ModItems.sigmaPick, 0, new ModelResourceLocation(
				VariousItems.MODID + ":sigmaPick", "inventory"));
		mesher.register(ModItems.travelRecipe, 0, new ModelResourceLocation(
				VariousItems.MODID + ":travelSheet", "inventory"));
		mesher.register(Item.getItemFromBlock(ModBlocks.travelPort), 0,
				new ModelResourceLocation(VariousItems.MODID + ":travelPort",
						"inventory"));
		mesher.register(Item.getItemFromBlock(ModBlocks.totem), 0,
				new ModelResourceLocation(VariousItems.MODID + ":totem",
						"inventory"));
		mesher.register(Item.getItemFromBlock(ModBlocks.glowsand), 0,
				new ModelResourceLocation(VariousItems.MODID + ":glowsand",
						"inventory"));
		mesher.register(ModItems.pebble, 0, new ModelResourceLocation(
				VariousItems.MODID + ":pebble", "inventory"));
		for (int i = 0; i < ItemMaterial.NUMBER; i++) {
			ModelBakery.registerItemVariants(ModItems.material,
					new ResourceLocation(VariousItems.MODID + ":" + "material_"
							+ i));
			mesher.register(ModItems.material, i, new ModelResourceLocation(
					VariousItems.MODID + ":material_" + i, "inventory"));
		}
		for (int i = 0; i < 2; i++) {
			ModelBakery.registerItemVariants(ModItems.travelRecipe,
					new ResourceLocation(VariousItems.MODID + ":"
							+ "travelRecipe_" + i));
			mesher.register(ModItems.travelRecipe, i,
					new ModelResourceLocation(VariousItems.MODID
							+ ":travelRecipe_" + i, "inventory"));

		}
	}

	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityPebble.class,
				(IRenderFactory) new PebbleRender(Minecraft.getMinecraft()
						.getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityDecoy.class,
				new IRenderFactory() {
					@Override
					public Render createRenderFor(RenderManager manager) {
						return new RenderDecoy(manager, new ModelPig(), 0.7f);
					}
				});
		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChest.class,
		// new CHestR());

	}

}
