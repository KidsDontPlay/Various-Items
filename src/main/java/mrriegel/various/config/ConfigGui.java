package mrriegel.various.config;

import mrriegel.various.VariousItems;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class ConfigGui extends GuiConfig {
	public ConfigGui(GuiScreen parent) {
		super(parent, new ConfigElement(
				ConfigHandler.config
						.getCategory(Configuration.CATEGORY_GENERAL))
				.getChildElements(), VariousItems.MODID, false, false,
				VariousItems.MODNAME + " Configs");
	}

}
