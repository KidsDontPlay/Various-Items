package mrriegel.various.blocks;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.handler.GuiHandler;
import mrriegel.various.tile.TileTravel;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTravel extends BlockContainer {
	// public static final PropertyBool STATE = PropertyBool.create("state");

	public BlockTravel() {
		super(Material.wood);
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":travelBlock");
		// this.setDefaultState(this.blockState.getBaseState().withProperty(STATE,
		// false));
	}

	// @Override
	// public IBlockState getStateFromMeta(int meta) {
	// return this.getDefaultState().withProperty(STATE, meta == 1);
	// }
	//
	// @Override
	// public int getMetaFromState(IBlockState state) {
	// return state.getValue(STATE) ? 1 : 0;
	// }
	//
	// @Override
	// protected BlockState createBlockState() {
	// return new BlockState(this, new IProperty[] { STATE });
	// }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileTravel();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			playerIn.openGui(VariousItems.instance, GuiHandler.TRAVELBLOCK,
					worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
	}

}
