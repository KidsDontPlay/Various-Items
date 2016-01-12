package mrriegel.various.items;

import java.util.List;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.config.ConfigHandler;
import mrriegel.various.helper.NBTHelper;
import mrriegel.various.network.JetpackMessage;
import mrriegel.various.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

import org.lwjgl.input.Keyboard;

public class ItemJetpack extends ItemArmor {
	public static ArmorMaterial ARMOR = EnumHelper.addArmorMaterial("jet",
			VariousItems.MODID + ":textures/armor/jetpack.png", 1000,
			new int[] { 2, 2, 2, 2 }, 0);

	public ItemJetpack() {
		super(ARMOR, 0, 1);
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":jetpack");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			String type) {
		return VariousItems.MODID + ":textures/armor/jetpack.png";
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
	public void onArmorTick(World world, EntityPlayer player,
			ItemStack itemStack) {
		itemStack.setItemDamage(0);
		if (player.isInLava()
				&& world.provider.getDimensionId() == -1
				&& NBTHelper.getInt(player.getCurrentArmor(2), "fuel") < ConfigHandler.jetpackMaxFuel
				&& world.getTotalWorldTime() % 2 == 0) {
			NBTHelper.setInteger(player.getCurrentArmor(2), "fuel",
					NBTHelper.getInt(player.getCurrentArmor(2), "fuel") + 1);
		}
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
