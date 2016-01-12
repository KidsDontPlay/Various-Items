package mrriegel.various.items;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.helper.NBTHelper;
import net.minecraft.block.BlockTorch;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemInfiTorch extends Item {
	public ItemInfiTorch() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(VariousItems.MODID + ":infiTorch");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		if (world.isRemote)
			return stack;
		MovingObjectPosition mop = Minecraft.getMinecraft()
				.getRenderViewEntity().rayTrace(30, 1.0F);
		if (mop == null)
			return stack;
		if (player.getDistance(mop.getBlockPos().getX(), mop.getBlockPos()
				.getY(), mop.getBlockPos().getZ()) > 20
				|| !world.isAirBlock(mop.getBlockPos().offset(mop.sideHit)))
			return stack;
		if (mop.sideHit == EnumFacing.UP
				&& world.getBlockState(mop.getBlockPos()).getBlock()
						.canPlaceTorchOnTop(world, mop.getBlockPos())) {
			world.setBlockState(mop.getBlockPos().offset(mop.sideHit),
					Blocks.torch.getDefaultState());
		}
		if (mop.sideHit != EnumFacing.DOWN
				&& world.isSideSolid(mop.getBlockPos(), mop.sideHit)) {
			world.setBlockState(
					mop.getBlockPos().offset(mop.sideHit),
					Blocks.torch.getDefaultState().withProperty(
							BlockTorch.FACING, mop.sideHit));
		}
		return stack;
	}

}
