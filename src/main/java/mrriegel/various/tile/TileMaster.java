package mrriegel.various.tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mrriegel.various.blocks.BlockKabel;
import mrriegel.various.blocks.BlockMaster;
import mrriegel.various.init.ModBlocks;
import mrriegel.various.tile.TileKabel.Kind;
import net.minecraft.block.BlockGlass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileMaster extends TileEntity implements ITickable {
	Set<BlockPos> cables, storageInventorys, imInventorys, exInventorys;
	boolean active;

	private void addCables(BlockPos pos, int num) {
		if (cables == null)
			cables = new HashSet<BlockPos>();
		if (num >= 200)
			return;
		for (BlockPos bl : getSides(pos)) {
			if (worldObj.getBlockState(bl).getBlock() == ModBlocks.master
					&& !bl.equals(pos)) {
				active = false;
				return;
			}
			if (worldObj.getBlockState(bl).getBlock() instanceof BlockKabel
					&& !cables.contains(bl)
					&& worldObj.getChunkFromBlockCoords(bl).isLoaded()) {
				cables.add(bl);
				addCables(bl, num++);
			}
		}
		active = true;
	}

	private void addInventorys() {
		for (BlockPos cable : cables) {
			TileKabel tile = (TileKabel) worldObj.getTileEntity(cable);
			for (EnumFacing face : tile.getConnections()) {
				BlockGlass g;
			}
		}

	}

	public static List<BlockPos> getSides(BlockPos pos) {
		List<BlockPos> lis = new ArrayList<BlockPos>();
		lis.add(pos.up());
		lis.add(pos.down());
		lis.add(pos.east());
		lis.add(pos.west());
		lis.add(pos.north());
		lis.add(pos.south());
		return lis;

	}

	@Override
	public void onLoad() {
		System.out.println("loadzzzz");
		// refreshNetwork();
	}

	public void refreshNetwork() {
		cables=null;
		addCables(pos, 0);
		for (BlockPos c : cables)
			((TileKabel) worldObj.getTileEntity(c)).setMaster(pos);
		addInventorys();
		System.out.println("size: " + cables.size());
	}

	public void vacuum() {
		if (worldObj.getTotalWorldTime() % 20 != 0)
			return;
		for (BlockPos p : cables) {
			if (((TileKabel) worldObj.getTileEntity(p)).getKind() == Kind.vacuumKabel) {
				boolean pulledAny = false;
				int range = 6;

				int x = getPos().getX();
				int y = getPos().getY();
				int z = getPos().getZ();

				List<EntityItem> items = worldObj.getEntitiesWithinAABB(
						EntityItem.class,
						AxisAlignedBB.fromBounds(x - range, y - range, z
								- range, x + range + 1, y + range + 1, z
								+ range + 1));

				for (EntityItem item : items) {
					if (item.getAge() < (60) || item.getAge() >= 105
							&& item.getAge() < 110 || item.isDead)
						continue;
					ItemStack stack = item.getEntityItem();

				}
			}
		}
	}

	@Override
	public void update() {
	}

}
