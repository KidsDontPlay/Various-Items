package mrriegel.various.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import mrriegel.various.config.ConfigHandler;
import mrriegel.various.helper.NBTHelper;
import mrriegel.various.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderEvents {
	@SubscribeEvent
	public void onScreenRenderEvent(RenderGameOverlayEvent.Pre event) {
		if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayer player = mc.thePlayer;
			ItemStack stack = player.getCurrentArmor(2);
			if (stack != null && stack.getItem() == ModItems.jetpack) {
				ScaledResolution resolution = new ScaledResolution(mc);
				if (stack.getTagCompound() == null)
					return;
				int percent = (int) ((NBTHelper.getInt(stack, "fuel") * 100f) / ConfigHandler.jetpackMaxFuel);
				int green = (51 / 20) * percent;
				int red = (-51 / 20) * percent + 255;
				Color color = new Color(red, green, 0);
				if (percent > 5
						|| mc.theWorld.getTotalWorldTime() / 10 % 2 == 0)
					mc.fontRendererObj.drawStringWithShadow("Jetpack "
							+ NBTHelper.getInt(stack, "fuel") + "/"
							+ ConfigHandler.jetpackMaxFuel, 15, 15,
							color.getRGB());

			}
		}
	}

}
