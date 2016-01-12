package mrriegel.various.items;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.entity.EntityDecoy;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemDecoy extends Item {
	public ItemDecoy() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":decoy");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn,
			World worldIn, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else if (!playerIn.canPlayerEdit(pos.offset(side), side, stack)) {
			return false;
		} else {
			IBlockState iblockstate = worldIn.getBlockState(pos);

			pos = pos.offset(side);
			double d0 = 0.0D;

			if (side == EnumFacing.UP
					&& iblockstate.getBlock() instanceof BlockFence) {
				d0 = 0.5D;
			}
			EntityDecoy entity = spawnCreature(worldIn,
					pos.getX() + 0.5D, pos.getY() + d0,
					pos.getZ() + 0.5D);

			if (entity != null) {
				if (!playerIn.capabilities.isCreativeMode) {
					--stack.stackSize;
					if (stack.stackSize == 0)
						stack = null;
				}
			}

			return true;
		}
	}

	EntityDecoy spawnCreature(World worldIn, double x, double y, double z) {

		EntityDecoy entity = null;

		entity = new EntityDecoy(worldIn);

		if (entity instanceof EntityLivingBase) {
			EntityLiving entityliving = entity;
			entity.setLocationAndAngles(x, y, z, MathHelper
					.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F),
					0.0F);
			entityliving.rotationYawHead = entityliving.rotationYaw;
			entityliving.renderYawOffset = entityliving.rotationYaw;
			entityliving.onInitialSpawn(worldIn
					.getDifficultyForLocation(new BlockPos(entityliving)),
					(IEntityLivingData) null);
			worldIn.spawnEntityInWorld(entity);
			entityliving.playLivingSound();
		}

		return entity;

	}

}
