package mrriegel.various.render;

import mrriegel.various.VariousItems;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderDecoy extends RenderPig {

	public RenderDecoy(RenderManager renderManagerIn, ModelBase modelBaseIn,
			float shadowSizeIn) {
		super(renderManagerIn, modelBaseIn, shadowSizeIn);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPig entity) {
		return new ResourceLocation(VariousItems.MODID
				+ ":textures/entity/decoy.png");
	}

}
