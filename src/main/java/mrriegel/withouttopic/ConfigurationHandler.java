package mrriegel.withouttopic;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler {

	public static Configuration config;

	public static void refreshConfig(File file) {
		config = new Configuration(file);
		config.load();

		if (config.hasChanged()) {
			config.save();
		}
	}

}
