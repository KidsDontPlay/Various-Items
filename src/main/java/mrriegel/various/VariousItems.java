package mrriegel.various;

import mrriegel.various.entity.EntityPlate;
import mrriegel.various.proxy.CommonProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@SubscribeEvent
	public void lic(PlayerInteractEvent e) {
		if (e.action == Action.RIGHT_CLICK_BLOCK
				&& e.entityPlayer.getHeldItem() != null
				&& e.entityPlayer.getHeldItem().getItem() == Items.stick
				&& !e.world.isRemote) {
			boolean x = e.world.spawnEntityInWorld(new EntityPlate(e.world,
					e.pos.up()));
		}
	}

	@SubscribeEvent
	public void lic(LivingUpdateEvent e) {
		// if (e.entityLiving instanceof EntityPlayer)
		// System.out.println(FMLCommonHandler.instance().getEffectiveSide()+" "+e.entityLiving.posY);
	}

}
