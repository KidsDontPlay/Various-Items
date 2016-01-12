package mrriegel.various.gui.food;

import org.lwjgl.opengl.GL11;

import mrriegel.various.VariousItems;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiFood extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			VariousItems.MODID + ":textures/gui/jetpack.png");

	public GuiFood(Container inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 176;
		this.ySize = 127;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		for (int i = 0; i < 9; i++)
			drawTexturedModalRect(k + 7 + 18 * i, l + 14, 176, 34, 18, 18);

	}

}
