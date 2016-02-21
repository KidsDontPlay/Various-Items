package mrriegel.various.init;

import mrriegel.various.VariousItems;
import mrriegel.various.entity.EntityPebble;
import mrriegel.various.entity.EntityPlate;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	public static void init() {
		int id = 0;
		EntityRegistry.registerModEntity(EntityPebble.class, "pebble", id++,
				VariousItems.instance, 128, 5, true);
		EntityRegistry.registerModEntity(EntityPlate.class, "plate", id++,
				VariousItems.instance, 128, 5, true);
	}
}
