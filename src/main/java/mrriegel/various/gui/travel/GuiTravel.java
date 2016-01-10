package mrriegel.various.gui.travel;

import mrriegel.various.VariousItems;
import mrriegel.various.config.ConfigHandler;
import mrriegel.various.gui.jetpack.ContainerJetPack;
import mrriegel.various.helper.NBTHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiTravel extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			VariousItems.MODID + ":textures/gui/jetpack.png");

	public GuiTravel(ContainerTravel containerTravel) {
		super(containerTravel);
		this.xSize = 176;
		this.ySize = 127;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		mc.fontRendererObj.drawString("Travel", 6, 7, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		drawTexturedModalRect(k + 15, l + 20, 176, 34, 18, 18);
		drawTexturedModalRect(k + 79, l + 12, 176, 34, 18, 18);

	}

}