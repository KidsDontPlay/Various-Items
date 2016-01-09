package mrriegel.various.network;

import mrriegel.various.VariousItems;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(
			VariousItems.MODID);

	public static void init() {
		int id = 0;
		 INSTANCE.registerMessage(JetpackMessage.class, JetpackMessage.class, id++,
		 Side.SERVER);
	}

}
