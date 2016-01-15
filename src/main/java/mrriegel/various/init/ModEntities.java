package mrriegel.various.init;

import mrriegel.various.VariousItems;
import mrriegel.various.entity.EntityPebble;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	public static void init() {
		int id = 0;
		EntityRegistry.registerModEntity(EntityPebble.class, "pebble", id++,
				VariousItems.instance, 128, 5, true);
		// EntityRegistry.registerModEntity(EntityDecoy.class, "decoy", id++,
		// VariousItems.instance, 128, 5, true);
	}
}
