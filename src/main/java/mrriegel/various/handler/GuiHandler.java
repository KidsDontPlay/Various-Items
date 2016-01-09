package mrriegel.various.handler;

import mrriegel.various.gui.jetpack.ContainerJetPack;
import mrriegel.various.gui.jetpack.GuiJetpack;
import mrriegel.various.gui.jetpack.InventoryJetpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	public static final int JETPACK = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == JETPACK)
			return new ContainerJetPack(player, player.inventory,
					new InventoryJetpack(player.getHeldItem()));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (ID == JETPACK)
			return new GuiJetpack(new ContainerJetPack(player,
					player.inventory,
					new InventoryJetpack(player.getHeldItem())));
		return null;
	}

}
