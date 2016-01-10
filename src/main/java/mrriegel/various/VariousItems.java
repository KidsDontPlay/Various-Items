package mrriegel.various;

import java.util.List;

import mrriegel.various.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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
	public void up(LivingUpdateEvent e) {
		if (e.entityLiving instanceof EntityPlayer
				&& !e.entityLiving.worldObj.isRemote
				&& e.entityLiving.worldObj.getTotalWorldTime() % 20 == 0) {
			AxisAlignedBB f = AxisAlignedBB.fromBounds(e.entity.posX - 5,
					e.entity.posY - 5, e.entity.posZ - 5, e.entity.posX + 5,
					e.entity.posY + 5, e.entity.posZ + 5);
			List<EntityMob> lis = e.entityLiving.worldObj
					.getEntitiesWithinAABB(EntityMob.class, f);

		}
	}

}
