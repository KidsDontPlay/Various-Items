package mrriegel.various.network;

import io.netty.buffer.ByteBuf;
import mrriegel.various.VariousItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenMessage implements IMessage,
		IMessageHandler<OpenMessage, IMessage> {
	int id, x, y, z;

	public OpenMessage() {
	}

	public OpenMessage(int id, int x, int y, int z) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public IMessage onMessage(final OpenMessage message,
			final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				EntityPlayer player = ctx.getServerHandler().playerEntity;
				player.openGui(VariousItems.instance, message.id,
						player.worldObj, message.x, message.y, message.z);

			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

}
