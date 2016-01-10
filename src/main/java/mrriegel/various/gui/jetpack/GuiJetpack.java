package mrriegel.various.gui.jetpack;

import mrriegel.various.VariousItems;
import mrriegel.various.config.ConfigHandler;
import mrriegel.various.helper.NBTHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiJetpack extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			VariousItems.MODID + ":textures/gui/jetpack.png");

	public GuiJetpack(ContainerJetPack inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 176;
		this.ySize = 127;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		mc.fontRendererObj.drawString("Jetpack", 6, 7, 4210752);
		mc.fontRendererObj.drawString(
				"Fuel: "
						+ NBTHelper.getInt(mc.thePlayer.getCurrentArmor(2),
								"fuel"), 6, 21, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		drawTexturedModalRect(k + 110, l + 12, 176, 0, 54, 18);
		int percent = (int) ((NBTHelper.getInt(mc.thePlayer.getCurrentArmor(2),
				"fuel") * 100f) / ConfigHandler.jetpackMaxFuel);
		drawTexturedModalRect(k + 111, l + 13, 176, 18,
				(int) (52 * (percent / 100.)), 16);

	}

}
