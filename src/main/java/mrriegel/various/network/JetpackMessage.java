package mrriegel.various.network;

import io.netty.buffer.ByteBuf;
import mrriegel.various.helper.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class JetpackMessage implements IMessage,
		IMessageHandler<JetpackMessage, IMessage> {
	int fuel;

	public JetpackMessage() {
	}

	public JetpackMessage(int fuel) {
		this.fuel = fuel;
	}

	@Override
	public IMessage onMessage(final JetpackMessage message,
			final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				EntityPlayer player = ctx.getServerHandler().playerEntity;
				NBTHelper.setInteger(player.getCurrentArmor(2), "fuel",
						message.fuel);
				player.fallDistance = -1;

			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.fuel = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.fuel);
	}

}
