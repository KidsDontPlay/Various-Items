package mrriegel.various.blocks;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.tile.TileTotem;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTotem extends BlockContainer {
	public BlockTotem() {
		super(Material.wood);
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":totem");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileTotem();
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos,
			IBlockState state, EntityPlayer playerIn, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		((TileTotem) worldIn.getTileEntity(pos)).showBorder();
		return super.onBlockActivated(worldIn, pos, state, playerIn, side,
				hitX, hitY, hitZ);
	}

}
