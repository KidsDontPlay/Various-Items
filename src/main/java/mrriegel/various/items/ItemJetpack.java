package mrriegel.various.items;

import java.util.List;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.config.ConfigHandler;
import mrriegel.various.gui.jetpack.ContainerJetPack;
import mrriegel.various.handler.GuiHandler;
import mrriegel.various.helper.NBTHelper;
import mrriegel.various.network.JetpackMessage;
import mrriegel.various.network.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import org.lwjgl.input.Keyboard;

public class ItemJetpack extends ItemArmor {

	public ItemJetpack() {
		super(ArmorMaterial.IRON, 0, 1);
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":jetpack");
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}

	@Override
	public boolean isDamageable() {
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add("Fuel: " + NBTHelper.getInt(stack, "fuel"));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		if (!playerIn.isSneaking())
			return super.onItemRightClick(itemStackIn, worldIn, playerIn);
		else {
			playerIn.openGui(VariousItems.instance, GuiHandler.JETPACK,
					worldIn, 0, 0, 0);
			return itemStackIn;
		}
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return 0;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn,
			int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		ItemStack lava = ItemStack.loadItemStackFromNBT(stack.getTagCompound()
				.getCompoundTag("lava"));
		if (lava == null)
			return;
		if (lava.getItem() == Items.lava_bucket
				&& NBTHelper.getInt(stack, "fuel")
						+ ConfigHandler.fuelValueLava <= ConfigHandler.jetpackMaxFuel) {
			NBTHelper.setInteger(stack, "fuel", NBTHelper.getInt(stack, "fuel")
					+ ConfigHandler.fuelValueLava);
			NBTTagCompound n = new NBTTagCompound();
			new ItemStack(Items.bucket).writeToNBT(n);
			stack.getTagCompound().setTag("lava", n);
			EntityPlayer player = (EntityPlayer) entityIn;
			player.inventory.mainInventory[player.inventory.currentItem] = stack;
			player.openGui(VariousItems.instance, GuiHandler.JETPACK, worldIn,
					0, 0, 0);
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player,
			ItemStack itemStack) {
		if (world.isRemote && Keyboard.isKeyDown(Keyboard.KEY_SPACE)
				&& NBTHelper.getInt(player.getCurrentArmor(2), "fuel") > 0
				&& Minecraft.getMinecraft().inGameHasFocus) {
			boolean left = Keyboard.isKeyDown(Keyboard.KEY_A);
			boolean right = Keyboard.isKeyDown(Keyboard.KEY_D);
			boolean forward = Keyboard.isKeyDown(Keyboard.KEY_W);
			boolean backward = Keyboard.isKeyDown(Keyboard.KEY_S);
			if (player.motionY <= ConfigHandler.jetpackMaxVerticalSpeed) {
				player.motionY += ConfigHandler.jetpackAcceleration;
				NBTHelper
						.setInteger(player.getCurrentArmor(2), "fuel",
								NBTHelper.getInt(player.getCurrentArmor(2),
										"fuel") - 1);
			}

			float thrust = (float) ConfigHandler.jetpackMaxHorizontalSpeed;
			if (forward)
				moveFlying(player, 0, thrust, thrust);
			if (backward)
				moveFlying(player, 0, -thrust, thrust);
			if (left)
				moveFlying(player, thrust, 0, thrust);
			if (right)
				moveFlying(player, -thrust, 0, thrust);
			player.fallDistance = -1;
			PacketHandler.INSTANCE.sendToServer(new JetpackMessage(NBTHelper
					.getInt(player.getCurrentArmor(2), "fuel")));

		}
		if (world.isRemote && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)
				&& !Keyboard.isKeyDown(Keyboard.KEY_SPACE)
				&& NBTHelper.getInt(player.getCurrentArmor(2), "fuel") > 0
				&& Minecraft.getMinecraft().inGameHasFocus) {
			while (player.motionY <= -0.1) {
				player.motionY += 0.05;

			}
			player.fallDistance = -1;
			PacketHandler.INSTANCE.sendToServer(new JetpackMessage(NBTHelper
					.getInt(player.getCurrentArmor(2), "fuel")));
			NBTHelper.setInteger(player.getCurrentArmor(2), "fuel",
					NBTHelper.getInt(player.getCurrentArmor(2), "fuel") - 1);
		}
	}

	private void moveFlying(EntityPlayer player, float p_70060_1_,
			float p_70060_2_, float p_70060_3_) {
		float f3 = p_70060_1_ * p_70060_1_ + p_70060_2_ * p_70060_2_;

		if (f3 >= 1.0E-4F) {
			f3 = MathHelper.sqrt_float(f3);

			if (f3 < 1.0F) {
				f3 = 1.0F;
			}

			f3 = p_70060_3_ / f3;
			p_70060_1_ *= f3;
			p_70060_2_ *= f3;
			float f4 = MathHelper.sin(player.rotationYaw * (float) Math.PI
					/ 180.0F);
			float f5 = MathHelper.cos(player.rotationYaw * (float) Math.PI
					/ 180.0F);
			player.motionX += p_70060_1_ * f5 - p_70060_2_ * f4;
			player.motionZ += p_70060_2_ * f5 + p_70060_1_ * f4;
			NBTHelper.setInteger(player.getCurrentArmor(2), "fuel",
					NBTHelper.getInt(player.getCurrentArmor(2), "fuel") - 1);
		}
	}

}
