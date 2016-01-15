package mrriegel.various.blocks;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGlowsand extends Block {
	public BlockGlowsand() {
		super(Material.sand, MapColor.sandColor);
		this.setCreativeTab(CreativeTab.tab1);
		this.setLightLevel(0.8F);
		this.setHardness(0.4F);
		this.setUnlocalizedName(VariousItems.MODID + ":glowsand");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos,
			IBlockState state) {
		float f = 0.125F;
		return new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(),
				pos.getX() + 1, pos.getY() + 1 - f, pos.getZ() + 1);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos,
			IBlockState state, Entity entity) {
		double velocity = Math.sqrt(entity.motionX * entity.motionX
				+ entity.motionZ * entity.motionZ);
		double velo = 0.35;
		if (!(entity instanceof EntityPlayerSP) || velocity == 0
				|| velocity >= velo)
			return;

		EntityPlayerSP player = (EntityPlayerSP) entity;

		if (Math.abs(player.movementInput.moveForward) < 0.75f
				&& Math.abs(player.movementInput.moveStrafe) < 0.75f)
			return;

		entity.motionX = velo * entity.motionX / velocity;
		entity.motionZ = velo * entity.motionZ / velocity;
	}
}
