package mrriegel.various.handler;

import mrriegel.various.gui.filter.ContainerFilter;
import mrriegel.various.gui.filter.GuiFilter;
import mrriegel.various.gui.filter.InventoryFilter;
import mrriegel.various.gui.jetpack.ContainerJetPack;
import mrriegel.various.gui.jetpack.GuiJetpack;
import mrriegel.various.gui.jetpack.InventoryJetpack;
import mrriegel.various.gui.travel.ContainerTravel;
import mrriegel.various.gui.travel.GuiTravel;
import mrriegel.various.tile.CrunchTEInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static final int JETPACK = 0;
	public static final int FILTER = 1;
	public static final int TRAVELPORT = 100;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == JETPACK)
			return new ContainerJetPack(player.inventory, new InventoryJetpack(
					player.getCurrentArmor(2)));
		if (ID == TRAVELPORT)
			return new ContainerTravel(player.inventory,
					(CrunchTEInventory) world.getTileEntity(new BlockPos(x, y,
							z)));
		if (ID == FILTER)
			return new ContainerFilter(player.inventory, new InventoryFilter(
					player.getHeldItem()));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == JETPACK)
			return new GuiJetpack(new ContainerJetPack(player.inventory,
					new InventoryJetpack(player.getCurrentArmor(2))));
		if (ID == TRAVELPORT)
			return new GuiTravel(new ContainerTravel(player.inventory,
					(CrunchTEInventory) world.getTileEntity(new BlockPos(x, y,
							z))));
		if (ID == FILTER)
			return new GuiFilter(new ContainerFilter(player.inventory,
					new InventoryFilter(player.getHeldItem())));
		return null;
	}

}
