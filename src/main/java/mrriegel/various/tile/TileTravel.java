package mrriegel.various.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public class TileTravel extends CrunchTEInventory implements ITickable {

	private int count;
	private boolean start;

	public TileTravel() {
		super(2);
	}

	@Override
	protected void readSyncableDataFromNBT(NBTTagCompound tag) {
		count = tag.getInteger("count");
		start = tag.getBoolean("start");
	}

	@Override
	protected void writeSyncableDataToNBT(NBTTagCompound tag) {
		tag.setInteger("count", count);
		tag.setBoolean("start", start);
	}

	@Override
	public void update() {
		if (getStackInSlot(1) == null)
			return;
		boolean advanced = getStackInSlot(1).getItemDamage() == 1;
		if (worldObj.isBlockIndirectlyGettingPowered(pos) >= 0
				&& getStackInSlot(0) != null && getStackInSlot(0).stackSize > 0) {
			worldObj.spawnParticle(EnumParticleTypes.PORTAL, getPos().getX()
					+ worldObj.rand.nextDouble(), getPos().getY() + .4,
					getPos().getZ() + worldObj.rand.nextDouble(), 0, 0, 0, 0);
		}
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
