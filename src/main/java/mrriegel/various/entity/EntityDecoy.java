package mrriegel.various.entity;

import java.util.List;

import mrriegel.various.init.ModItems;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

public class EntityDecoy extends EntityPig {

	public EntityDecoy(World worldIn) {
		super(worldIn);
		this.tasks.taskEntries = Lists
				.<EntityAITasks.EntityAITaskEntry> newArrayList();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setBaseValue(10d);
	}

	// public boolean isEntityInvulnerable(DamageSource source) {
	// return true;
	// }

	@Override
	public boolean canBeSteered() {
		return false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		int range = 8;
		AxisAlignedBB f = AxisAlignedBB.fromBounds(posX - range, posY - range,
				posZ - range, posX + range, posY + range, posZ + range);
		List<EntityMob> lis = worldObj
				.getEntitiesWithinAABB(EntityMob.class, f);
		for (EntityMob mob : lis) {
			if (mob.targetTasks.taskEntries.size() > 1) {
				mob.targetTasks.taskEntries = Lists
						.<EntityAITasks.EntityAITaskEntry> newArrayList();
			}
			if (mob.targetTasks.taskEntries.isEmpty())
				mob.targetTasks.addTask(-2, new EntityAIFindEntityNearest(mob,
						EntityDecoy.class));
		}
	}

	@Override
	public boolean interact(EntityPlayer player) {
		if (!worldObj.isRemote && player.isSneaking()) {
			this.setDead();
			worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY,
					posZ, new ItemStack(ModItems.decoy)));
			return true;
		}
		return false;
	}

	@Override
	protected Item getDropItem() {
		return null;
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {

	}

	@Override
	public void onStruckByLightning(EntityLightningBolt lightningBolt) {
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		super.fall(0, damageMultiplier);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	@Override
	protected int getExperiencePoints(EntityPlayer player) {
		return 0;
	}

	@Override
	protected void despawnEntity() {
	}

	// @Override
	// public AxisAlignedBB getCollisionBoundingBox() {
	// return null;
	// }
	//
	// @Override
	// public AxisAlignedBB getCollisionBox(Entity entityIn) {
	// return null;
	// }
	//
	// @Override
	// public void onCollideWithPlayer(EntityPlayer entityIn) {
	// }
	//
	// @Override
	// public float getCollisionBorderSize() {
	// return 0f;
	// }

}
