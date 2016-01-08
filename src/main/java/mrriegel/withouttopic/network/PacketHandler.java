package mrriegel.withouttopic.network;

import mrriegel.withouttopic.WithoutTopic;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(
			WithoutTopic.MODID);

	public static void init() {
		int id = 0;
	}

}
