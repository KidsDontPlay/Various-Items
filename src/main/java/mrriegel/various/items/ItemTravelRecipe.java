package mrriegel.various.items;

import java.util.Iterator;
import java.util.List;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.helper.NBTHelper;
import mrriegel.various.init.ModItems;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTravelRecipe extends Item {

	public ItemTravelRecipe() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(VariousItems.MODID + ":travelRecipe");
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		if (NBTHelper.getBoolean(stack, "bound")) {
			tooltip.add(MinecraftServer.getServer().worldServerForDimension(
					NBTHelper.getInt(stack, "dimension")).provider
					.getDimensionName()
					+ " x: "
					+ (int) NBTHelper.getDouble(stack, "x")
					+ ", y: "
					+ (int) NBTHelper.getDouble(stack, "y")
					+ ", z: "
					+ (int) NBTHelper.getDouble(stack, "z"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < 2; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn,
			int itemSlot, boolean isSelected) {
		if (NBTHelper.getBoolean(stack, "start")) {
			NBTHelper.setInteger(stack, "count",
					NBTHelper.getInt(stack, "count") + 1);
			for (int i = 0; i < 15; i++)
				worldIn.spawnParticle(EnumParticleTypes.PORTAL, entityIn.posX
						+ worldIn.rand.nextGaussian() / 2. - .25,
						entityIn.posY + 1.2,
						entityIn.posZ + worldIn.rand.nextGaussian() / 2. - .25,
						0, 0, 0, 0);
		}
		if (NBTHelper.getInt(stack, "count") >= 60) {
			NBTHelper.setInteger(stack, "count", 0);
			NBTHelper.setBoolean(stack, "start", false);
			if (worldIn.isRemote)
				return;
			World end = MinecraftServer.getServer().worldServerForDimension(
					NBTHelper.getInt(stack, "dimension"));
			teleportPlayerEntity(
					entityIn,
					worldIn,
					end,
					new BlockPos(NBTHelper.getDouble(stack, "x"), NBTHelper
							.getDouble(stack, "y") + .1, NBTHelper.getDouble(
							stack, "z")));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		boolean advanced = itemStackIn.getItemDamage() == 1;
		if (playerIn.isSneaking()) {
			NBTHelper.setBoolean(itemStackIn, "bound", true);
			NBTHelper.setDouble(itemStackIn, "x", playerIn.posX);
			NBTHelper.setDouble(itemStackIn, "y", playerIn.posY);
			NBTHelper.setDouble(itemStackIn, "z", playerIn.posZ);
			NBTHelper.setInteger(itemStackIn, "dimension",
					worldIn.provider.getDimensionId());
		} else if (NBTHelper.getBoolean(itemStackIn, "bound")) {
			if ((advanced || worldIn.provider.getDimensionId() == NBTHelper
					.getInt(itemStackIn, "dimension"))
					&& consume(playerIn, new ItemStack(ModItems.material))) {
				NBTHelper.setBoolean(itemStackIn, "start", true);
				if (worldIn.isRemote)
					playerIn.addChatMessage(new ChatComponentText("Start..."));

			}
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn);
	}

	public static Entity teleportPlayerEntity(Entity entity, World start,
			World end, BlockPos exitLoc) {
		WorldServer startWorld = (WorldServer) start, endWorld = (WorldServer) end;
		boolean dimensionalTransport = startWorld.provider.getDimensionId() != endWorld.provider
				.getDimensionId();
		EntityPlayerMP player = (EntityPlayerMP) entity;
		float yaw = player.rotationYaw;
		ServerConfigurationManager config = null;
		double exitX = exitLoc.getX();
		double exitY = exitLoc.getY();
		double exitZ = exitLoc.getZ();

		player.closeScreen();

		if (dimensionalTransport) {
			config = player.mcServer.getConfigurationManager();
			player.dimension = endWorld.provider.getDimensionId();
			player.playerNetServerHandler.sendPacket(new S07PacketRespawn(
					player.dimension, player.worldObj.getDifficulty(), endWorld
							.getWorldInfo().getTerrainType(),
					player.theItemInWorldManager.getGameType()));

			startWorld.removeEntity(player);
			player.isDead = false;
			player.setLocationAndAngles(exitX, exitY, exitZ, yaw,
					player.rotationPitch);
			endWorld.spawnEntityInWorld(player);
			player.setWorld(endWorld);
			config.preparePlayer(player, startWorld);
			player.playerNetServerHandler.setPlayerLocation(exitX, exitY,
					exitZ, yaw, entity.rotationPitch);
			player.theItemInWorldManager.setWorld(endWorld);

			config.updateTimeAndWeatherForPlayer(player, endWorld);
			config.syncPlayerInventory(player);

			player.worldObj.theProfiler.endSection();
			startWorld.resetUpdateEntityTick();
			endWorld.resetUpdateEntityTick();
			player.worldObj.theProfiler.endSection();

			for (Iterator<PotionEffect> potion = player
					.getActivePotionEffects().iterator(); potion.hasNext();)
				player.playerNetServerHandler
						.sendPacket(new S1DPacketEntityEffect(player
								.getEntityId(), potion.next()));

			player.playerNetServerHandler
					.sendPacket(new S1FPacketSetExperience(player.experience,
							player.experienceTotal, player.experienceLevel));

			FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player,
					startWorld.provider.getDimensionId(), player.dimension);
		} else {
			player.rotationYaw = yaw;
			player.setPositionAndUpdate(exitX, exitY, exitZ);
			player.worldObj.updateEntityWithOptionalForce(player, false);
		}

		return player;
	}

	boolean consume(EntityPlayer player, ItemStack stack) {
		InventoryPlayer inv = player.inventory;
		int i = getInventorySlotContainItem(stack, inv.mainInventory);

		if (i < 0) {
			return false;
		} else {
			if (--inv.mainInventory[i].stackSize <= 0) {
				inv.mainInventory[i] = null;
			}

			return true;
		}
	}

	private int getInventorySlotContainItem(ItemStack stack,
			ItemStack[] mainInventory) {
		for (int i = 0; i < mainInventory.length; ++i) {
			if (mainInventory[i] != null && mainInventory[i].isItemEqual(stack)) {
				return i;
			}
		}

		return -1;
	}
}
