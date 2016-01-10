package mrriegel.various.items;

import java.util.List;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.helper.NBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemSigmaPick extends ItemPickaxe {
	public static ToolMaterial MATERIAL = EnumHelper.addToolMaterial(
			"MATERIAL", 3, 2222, 10.0F, 5.0F, 1);

	public ItemSigmaPick() {
		super(MATERIAL);
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":sigmaPick");
	}

	enum Mode {
		NORMAL, VEIN, DETECT;
		private static Mode[] vals = values();

		public Mode next() {
			return vals[(this.ordinal() + 1) % vals.length];
		}
	}

	@Override
	public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
		if (p_82789_2_.isItemEqual(new ItemStack(ModItems.material, 1, 3))) {
			return true;
		}
		return false;
	}

	@Override
	public int getItemEnchantability() {
		return 0;
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn,
			int itemSlot, boolean isSelected) {
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (NBTHelper.getString(stack, "mode").equals(""))
			NBTHelper.setString(stack, "mode", Mode.NORMAL.toString());
		// if(worldIn.getTotalWorldTime()%200==0)

	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return StatCollector.translateToLocalFormatted("item.various:sigmaPick.name", NBTHelper.getString(stack, "mode"));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		if (player.isSneaking()) {
			// NBTHelper.setString(stack, "mode", Mode.NORMAL.toString());
			NBTHelper.setString(stack, "mode",
					Mode.valueOf(NBTHelper.getString(stack, "mode")).next()
							.toString());
		}
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list,
			boolean boo) {
		list.add(NBTHelper.getString(stack, "mode"));

	}

}
