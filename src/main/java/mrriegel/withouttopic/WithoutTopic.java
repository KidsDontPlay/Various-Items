package mrriegel.withouttopic;

import mrriegel.withouttopic.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = WithoutTopic.MODID, name = WithoutTopic.MODNAME, version = WithoutTopic.VERSION)
public class WithoutTopic {
	public static final String MODID = "withouttopic";
	public static final String VERSION = "1.0";
	public static final String MODNAME = "Without Topic";

	@Instance(WithoutTopic.MODID)
	public static WithoutTopic instance;

	@SidedProxy(clientSide = "mrriegel.withouttopic.proxy.ClientProxy", serverSide = "mrriegel.withouttopic.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}
