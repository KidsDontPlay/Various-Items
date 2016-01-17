package mrriegel.various.tile;

import java.util.List;

import mrriegel.various.blocks.BlockTravel;
import mrriegel.various.helper.NBTHelper;
import mrriegel.various.init.ModItems;
import mrriegel.various.items.ItemTravelRecipe;
import mrriegel.various.network.PacketHandler;
import mrriegel.various.network.ParticleMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

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
		if (getStackInSlot(1) == null) {
			// if (worldObj.getBlockState(pos).getValue(BlockTravel.STATE))
			// BlockTravel.setState(worldObj, pos,
			// worldObj.getBlockState(pos), false);
			return;
		}
		if (worldObj.isRemote)
			return;
		boolean advanced = getStackInSlot(1).getItemDamage() == 1;
		if (worldObj.isBlockPowered(pos) && getStackInSlot(0) != null
				&& getStackInSlot(0).stackSize > 0) {
			if (!worldObj.getBlockState(pos).getValue(BlockTravel.STATE))
				BlockTravel.setState(worldObj, pos,
						worldObj.getBlockState(pos), true);
			if (isPlayerOn()) {
				EntityPlayer player = getPlayer();
				if (!start
						&& NBTHelper.getBoolean(getStackInSlot(1), "bound")
						&& (advanced || worldObj.provider.getDimensionId() == NBTHelper
								.getInt(getStackInSlot(1), "dimension"))) {
					start = true;
					player.addChatMessage(new ChatComponentText("Start..."));
					worldObj.markBlockForUpdate(pos);

				}
			}
		} else if (worldObj.getBlockState(pos).getValue(BlockTravel.STATE))
			BlockTravel.setState(worldObj, pos, worldObj.getBlockState(pos),
					false);

		if (start) {
			if (!isPlayerOn()) {
				start = false;
				count = 0;
				return;
			}
			EntityPlayer player = getPlayer();
			count++;
			PacketHandler.INSTANCE.sendToAllAround(new ParticleMessage(
					ParticleMessage.TRAVEL, player.posX, player.posY,
					player.posZ),
					new TargetPoint(player.worldObj.provider.getDimensionId(),
							player.posX, player.posY, player.posZ, 20));
		}
		if (count >= 60) {
			count = 0;
			start = false;
			if (!isPlayerOn())
				return;
			World end = MinecraftServer.getServer().worldServerForDimension(
					NBTHelper.getInt(getStackInSlot(1), "dimension"));
			getStackInSlot(0).stackSize--;
			if (getStackInSlot(0).stackSize == 0)
				setInventorySlotContents(0, null);
			worldObj.markBlockForUpdate(pos);
			ItemTravelRecipe.teleportPlayerEntity(getPlayer(), worldObj, end,
					new BlockPos(NBTHelper.getDouble(getStackInSlot(1), "x"),
							NBTHelper.getDouble(getStackInSlot(1), "y") + .1,
							NBTHelper.getDouble(getStackInSlot(1), "z")));
		}

	}

	boolean isPlayerOn() {
		AxisAlignedBB f = AxisAlignedBB.fromBounds(pos.getX(), pos.getY() + 1,
				pos.getZ(), pos.getX() + 1, pos.getY() + 1.1, pos.getZ() + 1);
		List<EntityPlayer> lis = worldObj.getEntitiesWithinAABB(
				EntityPlayer.class, f);
		return !lis.isEmpty();
	}

	EntityPlayer getPlayer() {
		AxisAlignedBB f = AxisAlignedBB.fromBounds(pos.getX(), pos.getY() + 1,
				pos.getZ(), pos.getX() + 1, pos.getY() + 1.1, pos.getZ() + 1);
		List<EntityPlayer> lis = worldObj.getEntitiesWithinAABB(
				EntityPlayer.class, f);
		return lis.get(0);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0)
			return stack.isItemEqual(new ItemStack(ModItems.material));
		if (slot == 1)
			return stack.getItem() == ModItems.travelRecipe;
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
