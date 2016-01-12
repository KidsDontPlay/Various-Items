package mrriegel.various.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mrriegel.various.init.ModBlocks;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.Vec3;

import com.google.common.collect.Lists;

public class TileTotem extends TileEntity implements ITickable {
	int range = 0;

	@Override
	public void update() {
		if (worldObj.getBlockState(pos.add(0, -1, 0)).getBlock() == ModBlocks.totem
				|| worldObj.getTotalWorldTime() % 10 != 0)
			return;
		range = getNum() * getNum() + 2;
		AxisAlignedBB f = AxisAlignedBB.fromBounds(pos.getX() + .5 - range,
				pos.getY() + .5 - range, pos.getZ() + .5 - range, pos.getX()
						+ .5 + range, pos.getY() + .5 + range, pos.getZ() + .5
						+ range);
		List<EntityCreature> lis = worldObj.getEntitiesWithinAABB(
				EntityCreature.class, f);
		for (EntityCreature mob : lis){
			if (!mob.targetTasks.taskEntries.isEmpty())
				mob.targetTasks.taskEntries = Lists
						.<EntityAITasks.EntityAITaskEntry> newArrayList();
			mob.setAttackTarget(null);
		}

	}

	int getNum() {
		int num = 0;
		for (int i = 0; i < 0 + 4; i++) {
			if (worldObj.getBlockState(pos.add(0, i, 0)).getBlock() == ModBlocks.totem)
				num++;
			else
				return num;
		}
		return num;
	}

	public void showBorder() {
		Random ran = new Random();
		Vec3 a = new Vec3(pos.getX() + range + .5, pos.getY() + .5, pos.getZ()
				+ range + .5);
		Vec3 b = new Vec3(pos.getX() + range + .5, pos.getY() + .5, pos.getZ()
				- range + .5);
		Vec3 c = new Vec3(pos.getX() - range + .5, pos.getY() + .5, pos.getZ()
				- range + .5);
		Vec3 d = new Vec3(pos.getX() - range + .5, pos.getY() + .5, pos.getZ()
				+ range + .5);
		for (int i = 0; i < 6; i++) {
			for (Vec3 m : points(a, b, 10 * getNum())) {
				worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
						m.xCoord + ran.nextDouble() / 2, m.yCoord, m.zCoord
								+ ran.nextDouble() / 2, 0, 0, 0, 0);
			}
			for (Vec3 m : points(b, c, 10 * getNum())) {
				worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
						m.xCoord + ran.nextDouble() / 2, m.yCoord, m.zCoord
								+ ran.nextDouble() / 2, 0, 0, 0, 0);
			}
			for (Vec3 m : points(c, d, 10 * getNum())) {
				worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
						m.xCoord + ran.nextDouble() / 2, m.yCoord, m.zCoord
								+ ran.nextDouble() / 2, 0, 0, 0, 0);
			}
			for (Vec3 m : points(d, a, 10 * getNum())) {
				worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
						m.xCoord + ran.nextDouble() / 2, m.yCoord, m.zCoord
								+ ran.nextDouble() / 2, 0, 0, 0, 0);
			}
		}
	}

	Vec3 divi(Vec3 v, int i) {
		return new Vec3(v.xCoord / i, v.yCoord / i, v.zCoord / i);
	}

	List<Vec3> points(Vec3 a, Vec3 b, int n) {
		List<Vec3> lis = new ArrayList<Vec3>();
		Vec3 tel = divi(b.subtract(a), n);
		Vec3 m = a;
		for (int i = 0; i < n; i++) {
			lis.add(m);
			m = m.add(tel);

		}
		return lis;
	}
}
