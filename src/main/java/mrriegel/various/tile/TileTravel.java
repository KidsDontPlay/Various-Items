package mrriegel.various.tile;

import java.util.List;

import mrriegel.various.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public class TileTravel extends CrunchTEInventory implements ITickable {

	private int count;
	private boolean start;
	private String player;

	public TileTravel() {
		super(2);
		player = ".........";
	}

	@Override
	protected void readSyncableDataFromNBT(NBTTagCompound tag) {
		count = tag.getInteger("count");
		start = tag.getBoolean("start");
		player = tag.getString("player");
	}

	@Override
	protected void writeSyncableDataToNBT(NBTTagCompound tag) {
		tag.setInteger("count", count);
		tag.setBoolean("start", start);
		tag.setString("player", player);
	}

	@Override
	public void update() {
		if (getStackInSlot(1) == null)
			return;
		boolean advanced = getStackInSlot(1).getItemDamage() == 1;
		redstone funzt nich
		if (worldObj.isBlockPowered(pos) && getStackInSlot(0) != null
				&& getStackInSlot(0).stackSize > 0) {
			worldObj.spawnParticle(EnumParticleTypes.PORTAL, getPos().getX()
					+ worldObj.rand.nextDouble(), getPos().getY() + .4,
					getPos().getZ() + worldObj.rand.nextDouble(), 0, 0, 0, 0);
		}

	}

	boolean isPlayerOn() {
		AxisAlignedBB f = AxisAlignedBB.fromBounds(pos.getX(), pos.getY() + 1,
				pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
		List<EntityPlayer> lis = worldObj.getEntitiesWithinAABB(
				EntityPlayer.class, f);
		return !lis.isEmpty();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0)
			return stack.isItemEqual(new ItemStack(ModItems.material));
		if (slot == 1)
			return stack.getItem() == ModItems.travel;
		return false;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

}
