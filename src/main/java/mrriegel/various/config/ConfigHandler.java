package mrriegel.various.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static Configuration config;

	public static double jetpackMaxVerticalSpeed, jetpackAcceleration,
			jetpackMaxHorizontalSpeed;
	public static int jetpackMaxFuel,fuelValueLava;

	public static void refreshConfig(File file) {
		config = new Configuration(file);
		config.load();
		jetpackMaxVerticalSpeed = config.get(Configuration.CATEGORY_GENERAL,
				"jetpackMaxVerticalSpeed", 0.5).getDouble();
		jetpackAcceleration = config.get(Configuration.CATEGORY_GENERAL,
				"jetpackAcceleration", 0.2).getDouble();
		jetpackMaxHorizontalSpeed = config.get(Configuration.CATEGORY_GENERAL,
				"jetpackMaxHorizontalSpeed", 0.3).getDouble();
		jetpackMaxFuel = config.get(Configuration.CATEGORY_GENERAL,
				"jetpackMaxFuel", 4800).getInt();
		fuelValueLava = config.get(Configuration.CATEGORY_GENERAL,
				"fuelValueLava", 1200).getInt();
		if (config.hasChanged()) {
			config.save();
		}
	}

}
