package mrriegel.various.render;

import mrriegel.various.VariousItems;
import mrriegel.various.entity.EntityPebble;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class PebbleRender extends Render<EntityPebble> implements
		IRenderFactory<EntityPebble> {

	public PebbleRender(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public Render<? super EntityPebble> createRenderFor(RenderManager manager) {
		return this;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPebble entity) {
		return new ResourceLocation(VariousItems.MODID
				+ ":textures/items/pebble.png");
	}

}
