package mrriegel.various.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPebble extends EntityThrowable {

	public EntityPebble(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityPebble(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	public EntityPebble(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		if (p_70184_1_.entityHit != null) {
			int i = 1;

			p_70184_1_.entityHit.attackEntityFrom(
					DamageSource.causeThrownDamage(this, this.getThrower()),
					i);
		}

		if (!this.worldObj.isRemote) {
			this.setDead();
		}
	}
}
