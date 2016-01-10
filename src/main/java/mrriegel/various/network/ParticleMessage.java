package mrriegel.various.network;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import mrriegel.various.VariousItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
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
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				Vec3 v = getPointUsingAnglesRange(player.getPositionVector()
						.add(new Vec3(0, .7, 0)), player.rotationYaw - 180f, 0,
						.35f);
				if (message.id == JETPACK)
					for (int i = 0; i < 5; i++)
						Minecraft.getMinecraft().theWorld.spawnParticle(
								EnumParticleTypes.FLAME,
								v.xCoord + ran.nextDouble() / 2, v.yCoord,
								v.zCoord + ran.nextDouble() / 2, 0, -.5, 0, 0);

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

	public Vec3 getPointUsingAnglesRange(Vec3 start, float yaw, float pitch,
			float range) {
		double coordX = start.xCoord
				+ (double) (-MathHelper.sin(yaw / 180.0F * (float) Math.PI)
						* MathHelper.cos(pitch / 180.0F * (float) Math.PI) * range);
		double coordY = start.yCoord
				+ (double) (-MathHelper.sin(pitch / 180.0F * (float) Math.PI) * range);
		double coordZ = start.zCoord
				+ (double) (MathHelper.cos(yaw / 180.0F * (float) Math.PI)
						* MathHelper.cos(pitch / 180.0F * (float) Math.PI) * range);
		return new Vec3(coordX, coordY, coordZ);
	}
}
