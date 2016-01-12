package mrriegel.various.handler;

import mrriegel.various.VariousItems;
import mrriegel.various.init.ModItems;
import mrriegel.various.network.OpenMessage;
import mrriegel.various.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import org.lwjgl.input.Keyboard;

public class KeyHandler {
	private static final String kb = ".keybinding.";
	public static final KeyBinding jetpack = new KeyBinding(VariousItems.MODID
			+ kb + "jetpack", Keyboard.KEY_J, VariousItems.MODNAME);

	@SubscribeEvent
	public void onKey(InputEvent.KeyInputEvent e) {
		if (jetpack.isPressed()) {
			if (Minecraft.getMinecraft().thePlayer.getCurrentArmor(2) != null
					&& Minecraft.getMinecraft().thePlayer.getCurrentArmor(2)
							.getItem() == ModItems.jetpack)
				PacketHandler.INSTANCE.sendToServer(new OpenMessage(
						GuiHandler.JETPACK, 0, 0, 0));
		}
	}

	public static void init() {
		ClientRegistry.registerKeyBinding(jetpack);
	}
}
