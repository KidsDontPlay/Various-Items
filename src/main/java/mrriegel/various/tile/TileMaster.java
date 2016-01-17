package mrriegel.various.tile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mrriegel.various.blocks.BlockKabel;
import mrriegel.various.helper.Network;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileMaster extends TileEntity implements ITickable {
	Set<BlockPos> cables;
	Set<BlockPos> inventorys;

	public TileMaster(World w) {
		super();
		cables = new HashSet<BlockPos>();
		addCables(pos, w, 0);
		System.out.println("w: " + worldObj);
		System.out.println(cables.size() + "");
	}

	private void addCables(BlockPos pos, World world, int num) {
		if (num >= 100)
			return;
		for (BlockPos bl : getCube(pos)) {
			System.out.println(world.getBlockState(bl).getBlock());
			if (1 == 1)
				continue;
			if (world.getBlockState(bl).getBlock() instanceof BlockKabel
					&& !cables.contains(bl)) {
				System.out.println(world.getBlockState(bl).getBlock());
				cables.add(bl);
				addCables(bl, world, num++);
			}

		}
	}

	public static List<BlockPos> getCube(BlockPos pos) {
		List<BlockPos> lis = new ArrayList<BlockPos>();
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				for (int k = -1; k <= 1; k++)
					lis.add(new BlockPos(pos.getX() + i, pos.getY() + j, pos
							.getZ() + k));
		return lis;

	}

	@Override
	public void update() {

	}

}
