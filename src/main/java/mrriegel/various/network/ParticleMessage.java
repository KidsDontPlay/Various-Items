package mrriegel.various.network;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import mrriegel.various.VariousItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ParticleMessage implements IMessage,
		IMessageHandler<ParticleMessage, IMessage> {
	public static final int JETPACK = 0;
	int id;
	double x, y, z;

	public ParticleMessage() {
	}

	public ParticleMessage(int id, double x, double y, double z) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public IMessage onMessage(final ParticleMessage message,
			final MessageContext ctx) {
		IThreadListener mainThread = Minecraft.getMinecraft();
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				Random ran = new Random();
				if (message.id == JETPACK)
					for (int i = 0; i < 5; i++)
						Minecraft.getMinecraft().theWorld.spawnParticle(
								EnumParticleTypes.FLAME,
								message.x + ran.nextDouble() / 2, message.y
										+ ran.nextDouble() / 2,
								message.z + ran.nextDouble() / 2, 0, -.5, 0, 0);

			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}

}
