package mrriegel.various.entity;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class EntityPlate extends Entity implements IEntityAdditionalSpawnData {

	Vec3 origin;
	boolean entityOn;
	List<String> entityNames;
	List<EntityLivingBase> entities;

	public EntityPlate(World world) {
		super(world);
		entityOn = false;
		preventEntitySpawning = true;
		this.setSize(1.0F, 0.1F);
	}

	public EntityPlate(World worldIn, BlockPos pos) {
		this(worldIn);
		posX = pos.getX() + .5;
		posY = pos.getY() + .9;
		posZ = pos.getZ() + .5;
		origin = new Vec3(posX, posY, posZ);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

	}

	@Override
	protected void entityInit() {
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		origin = new Gson().fromJson(tagCompund.getString("origin"),
				new TypeToken<Vec3>() {
				}.getType());

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		tagCompound.setString("origin", new Gson().toJson(origin));

	}

	@Override
	public void onUpdate() {
		this.worldObj.theProfiler.startSection("entityBaseTick");

		this.prevDistanceWalkedModified = this.distanceWalkedModified;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;

		this.handleWaterMovement();

		if (this.posY < -64.0D) {
			this.kill();
		}

		this.firstUpdate = false;
		this.worldObj.theProfiler.endSection();
		AxisAlignedBB x = AxisAlignedBB.fromBounds(posX - .5, posY, posZ - .5,
				posX + .5, posY + .101, posZ + .5);
		List<EntityLivingBase> lis = worldObj.getEntitiesWithinAABB(
				EntityLivingBase.class, x);
		entities = new ArrayList<EntityLivingBase>(lis);
		if (entities.isEmpty() && origin.yCoord != posY) {
			setPositionAndUpdate(posX, posY - .08, posZ);
			if (origin.yCoord >= posY) {
				setPositionAndUpdate(origin.xCoord, origin.yCoord,
						origin.zCoord);
			}
		}
		if (!entities.isEmpty()) {
			double speed = .08;
			double max = .15;
			double acc = .13;
			if (motionY <= max)
				motionY += acc;
			for (EntityLivingBase e : entities) {
				if (e.motionY <= max)
					e.motionY += acc;
				// e.setPosition(e.posX, e.posY + speed, e.posZ);
			}
			 setPositionAndUpdate(posX, posY + speed, posZ);

		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getEntityBoundingBox();
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.getCollisionBoundingBox();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		setDead();
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeDouble(origin.xCoord);
		buffer.writeDouble(origin.yCoord);
		buffer.writeDouble(origin.zCoord);

	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		origin = new Vec3(additionalData.readDouble(),
				additionalData.readDouble(), additionalData.readDouble());

	}

	@Override
	public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {
		// TODO Auto-generated method stub

	}

	@Override
	public ItemStack[] getInventory() {
		// TODO Auto-generated method stub
		return null;
	}
}
