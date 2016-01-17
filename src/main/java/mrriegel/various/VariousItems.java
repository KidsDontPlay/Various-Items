package mrriegel.various;

import mrriegel.various.proxy.CommonProxy;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = VariousItems.MODID, name = VariousItems.MODNAME, version = VariousItems.VERSION, guiFactory = "mrriegel.various.GuiFactory")
public class VariousItems {
	public static final String MODID = "various";
	public static final String VERSION = "1.0";
	public static final String MODNAME = "Various Items";

	@Instance(VariousItems.MODID)
	public static VariousItems instance;

	@SidedProxy(clientSide = "mrriegel.various.proxy.ClientProxy", serverSide = "mrriegel.various.proxy.CommonProxy")
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
