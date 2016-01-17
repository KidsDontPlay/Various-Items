package mrriegel.various.tile;

import java.util.HashSet;
import java.util.Set;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import mrriegel.various.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileKabel extends TileEntity {
	private Set<EnumFacing> connections;
	private Kind kind;
	private BlockPos master;

	public enum Kind {
		kabel, exKabel, imKabel;
	}

	public TileKabel() {
		connections = new HashSet<EnumFacing>();
		kind = getKind();
	}

	private Kind getKind(World world, BlockPos pos) {
		Block b = world.getBlockState(pos).getBlock();
		if (b == ModBlocks.kabel)
			return Kind.kabel;
		if (b == ModBlocks.exKabel)
			return Kind.exKabel;
		if (b == ModBlocks.imKabel)
			return Kind.imKabel;
		return null;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		connections = new Gson().fromJson(compound.getString("connections"),
				new TypeToken<Set<EnumFacing>>() {
				}.getType());
		kind = Kind.valueOf(compound.getString("kind"));
		master = new BlockPos(compound.getInteger("X"),
				compound.getInteger("Y"), compound.getInteger("Z"));

	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setString("connections", new Gson().toJson(connections));
		if(kind==null)
			kind=getKind();
		compound.setString("kind", kind.toString());
		if (master != null) {
			compound.setInteger("X", master.getX());
			compound.setInteger("Y", master.getY());
			compound.setInteger("Z", master.getZ());
		}
	}

	public Set<EnumFacing> getConnections() {
		return connections;
	}

	public void setConnections(Set<EnumFacing> connections) {
		this.connections = connections;
	}

	public Kind getKind() {
		return kind;
	}

}
