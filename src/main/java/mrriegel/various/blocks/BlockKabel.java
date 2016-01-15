package mrriegel.various.blocks;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockKabel extends Block {
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool TOP = PropertyBool.create("top");
	public static final PropertyBool BOTTOM = PropertyBool.create("bottom");

	public BlockKabel() {
		super(Material.iron);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(NORTH, Boolean.valueOf(false))
				.withProperty(EAST, Boolean.valueOf(false))
				.withProperty(SOUTH, Boolean.valueOf(false))
				.withProperty(WEST, Boolean.valueOf(false))
				.withProperty(TOP, Boolean.valueOf(false))
				.withProperty(BOTTOM, Boolean.valueOf(false)));
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":kabel");
		System.out.println("zzzzz: " + getDefaultState());
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos,
			EnumFacing side) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos,
			IBlockState state, Block neighborBlock) {
		state = getActualState(state, worldIn, pos);
		super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
			EntityLivingBase placer, ItemStack stack) {
		state = getActualState(state, worldIn, pos);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		System.out.println(block + "  " + pos);
		return block == ModBlocks.kabel
				|| worldIn.getTileEntity(pos) instanceof IInventory;
	}

	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn,
			BlockPos pos) {
		return state
				.withProperty(
						NORTH,
						Boolean.valueOf(this.canConnectTo(worldIn, pos.north())))
				.withProperty(EAST,
						Boolean.valueOf(this.canConnectTo(worldIn, pos.east())))
				.withProperty(
						SOUTH,
						Boolean.valueOf(this.canConnectTo(worldIn, pos.south())))
				.withProperty(WEST,
						Boolean.valueOf(this.canConnectTo(worldIn, pos.west())))
				.withProperty(TOP,
						Boolean.valueOf(this.canConnectTo(worldIn, pos.up())))
				.withProperty(BOTTOM,
						Boolean.valueOf(this.canConnectTo(worldIn, pos.down())));
	}

	@Override
	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { NORTH, EAST, WEST, SOUTH,
				TOP, BOTTOM });
	}

}
