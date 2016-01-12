package mrriegel.various.gui.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mrriegel.various.VariousItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiFilter extends GuiContainer {
	private static final ResourceLocation texture = new ResourceLocation(
			VariousItems.MODID + ":textures/gui/jetpack.png");

	public GuiFilter(ContainerFilter inventorySlotsIn) {
		super(inventorySlotsIn);
		this.xSize = 176;
		this.ySize = 142;
	}

	@Override
	public void initGui() {
		super.initGui();
		for (int i = 0; i < 9; i++) {
			buttonList
					.add(new Button(i, guiLeft + 8 + 18 * i, guiTop + 35, ""));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,
			int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l + 15, 0, 0, xSize, ySize);
		drawTexturedModalRect(k, l, 0, 0, xSize, 20);
		for (int i = 0; i < 9; i++)
			drawTexturedModalRect(k + 7 + 18 * i, l + 16, 176, 34, 18, 18);

	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		((Button) button).meta = !((Button) button).meta;
	}

	class Button extends GuiButton {
		boolean meta = true;

		public Button(int p_i1021_1_, int p_i1021_2_, int p_i1021_3_,
				boolean meta) {
			super(p_i1021_1_, p_i1021_2_, p_i1021_3_, 16, 16, "");
			this.meta = meta;
		}

		@Override
		public void drawButton(Minecraft p_146112_1_, int p_146112_2_,
				int p_146112_3_) {
			if (this.visible) {
				FontRenderer fontrenderer = p_146112_1_.fontRendererObj;
				p_146112_1_.getTextureManager().bindTexture(texture);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = p_146112_2_ >= this.xPosition
						&& p_146112_3_ >= this.yPosition
						&& p_146112_2_ < this.xPosition + this.width
						&& p_146112_3_ < this.yPosition + this.height;
				int k = this.getHoverState(this.hovered);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				GlStateManager.blendFunc(770, 771);
				this.drawTexturedModalRect(this.xPosition, this.yPosition,
						160 + 16 * k, 52, 16, 16);
				this.drawTexturedModalRect(this.xPosition + 2,
						this.yPosition + 2, 178, 69, 14, 14);
				if (!meta)
					this.drawTexturedModalRect(this.xPosition + 2,
							this.yPosition + 2, 194, 69, 14, 14);
				this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
				int l = 14737632;

				if (packedFGColour != 0) {
					l = packedFGColour;
				} else if (!this.enabled) {
					l = 10526880;
				} else if (this.hovered) {
					l = 16777120;
				}

				this.drawCenteredString(fontrenderer, this.displayString,
						this.xPosition + this.width / 2, this.yPosition
								+ (this.height - 8) / 2, l);
			}
		}
	}

}
