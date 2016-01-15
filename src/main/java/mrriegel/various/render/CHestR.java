package mrriegel.various.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class CHestR extends TileEntitySpecialRenderer<TileEntityChest> {
	public static Minecraft mc = Minecraft.getMinecraft();
	@Override
	public void renderTileEntityAt(TileEntityChest te, double x, double y,
			double z, float partialTicks, int destroyStage) {
		 GlStateManager.pushMatrix();
	        GlStateManager.translate(x, y, z);
	        this.renderItem(te.getWorld(), new ItemStack(Items.diamond_axe), partialTicks);
	        GlStateManager.popMatrix();
	}

	private void renderItem(World world, ItemStack stack, float partialTicks) {
		RenderItem itemRenderer = mc.getRenderItem();
		if (stack != null) {
			GlStateManager.translate(0.5, 1, 0.5);
			EntityItem entityitem = new EntityItem(world, 0.0D, 0.0D, 0.0D,
					stack);
			entityitem.getEntityItem().stackSize = 1;
			entityitem.hoverStart = 0.0F;
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();

			float rotation = (float) (720.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

			GlStateManager.rotate(rotation, 0.0F, 1.0F, 0);
			GlStateManager.scale(0.5F, 0.5F, 0.5F);
			GlStateManager.pushAttrib();
			RenderHelper.enableStandardItemLighting();
			itemRenderer.renderItem(entityitem.getEntityItem(),
					ItemCameraTransforms.TransformType.FIXED);
			RenderHelper.disableStandardItemLighting();
			GlStateManager.popAttrib();

			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}

}
