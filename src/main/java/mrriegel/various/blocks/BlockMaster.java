package mrriegel.various.blocks;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.tile.TileMaster;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMaster extends BlockContainer {

	public BlockMaster() {
		super(Material.iron);
		this.setHardness(3.5F);
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":master");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileMaster(worldIn);
	}

}
