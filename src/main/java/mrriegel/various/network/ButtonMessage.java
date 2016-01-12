package mrriegel.various.network;

import io.netty.buffer.ByteBuf;

import java.util.List;

import mrriegel.various.gui.filter.ContainerFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class ButtonMessage implements IMessage,
		IMessageHandler<ButtonMessage, IMessage> {
	List<Boolean> list;

	public ButtonMessage() {
	}

	public ButtonMessage(List<Boolean> list) {
		this.list = list;
	}

	@Override
	public IMessage onMessage(final ButtonMessage message,
			final MessageContext ctx) {
		IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
		mainThread.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				EntityPlayer player = ctx.getServerHandler().playerEntity;
				ContainerFilter con = (ContainerFilter) player.openContainer;
				int id = 0;
				for (boolean b : message.list) {
					con.metas.put(id, b);
					id++;
				}
				con.slotChanged();
			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.list = new Gson().fromJson(ByteBufUtils.readUTF8String(buf),
				new TypeToken<List<Boolean>>() {
				}.getType());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, new Gson().toJson(this.list));
	}

}
