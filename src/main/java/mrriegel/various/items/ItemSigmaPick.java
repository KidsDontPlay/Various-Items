package mrriegel.various.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mrriegel.various.CreativeTab;
import mrriegel.various.VariousItems;
import mrriegel.various.helper.NBTHelper;
import mrriegel.various.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class ItemSigmaPick extends ItemPickaxe {
	public static ToolMaterial MATERIAL = EnumHelper.addToolMaterial(
			"MATERIAL", 3, 2222, 10.0F, 3.0F, 12);

	public ItemSigmaPick() {
		super(MATERIAL);
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(VariousItems.MODID + ":sigmaPick");
	}

	enum Mode {
		NORMAL, VEIN, DETECT, TELE;
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
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn,
			int itemSlot, boolean isSelected) {
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
		if (NBTHelper.getString(stack, "mode").equals(""))
			NBTHelper.setString(stack, "mode", Mode.NORMAL.toString());
		if (worldIn.getTotalWorldTime() % 200 == 0
				&& stack.getItemDamage() < stack.getMaxDamage())
			stack.setItemDamage(stack.getItemDamage() - 1);
		// if (!NBTHelper.getString(stack, "mode").equals(
		// NBTHelper.getString(stack, "last"))) {
		// NBTHelper
		// .setString(stack, "last",
		// Mode.valueOf(NBTHelper.getString(stack, "mode"))
		// .toString());
		// System.out.println("change");
		// if (entityIn instanceof EntityPlayer) {
		// EntityPlayer player = (EntityPlayer) entityIn;
		// player.inventory.mainInventory[player.inventory.currentItem] = stack;
		// }
		// }
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if (NBTHelper.getString(stack, "mode").equals(""))
			return super.getItemStackDisplayName(stack);
		return StatCollector.translateToLocalFormatted(
				"item.various:sigmaPick.name",
				StatCollector.translateToLocal("item.various:sigmaPick.mode."
						+ NBTHelper.getString(stack, "mode").toLowerCase()));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		if (player.isSneaking()) {
			// NBTHelper
			// .setString(stack, "last",
			// Mode.valueOf(NBTHelper.getString(stack, "mode"))
			// .toString());
			NBTHelper.setString(stack, "mode",
					Mode.valueOf(NBTHelper.getString(stack, "mode")).next()
							.toString());
		}
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world,
			BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(pos) instanceof IInventory
				&& !(world.getTileEntity(pos) instanceof ISidedInventory)) {
			BlockPos inv = pos;
			NBTHelper.setInteger(stack, "x", inv.getX());
			NBTHelper.setInteger(stack, "y", inv.getY());
			NBTHelper.setInteger(stack, "z", inv.getZ());
			NBTHelper.setInteger(stack, "dimension",
					world.provider.getDimensionId());
			NBTHelper.setBoolean(stack, "bound", true);
			if (world.isRemote)
				player.addChatMessage(new ChatComponentText("Pickaxe bound to "
						+ inv.getX() + ", " + inv.getY() + ", " + inv.getZ()));
			return true;
		}
		return false;
	}

	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state) {
		if (stack.getTagCompound() == null)
			return super.getDigSpeed(stack, state);
		if (Mode.valueOf(NBTHelper.getString(stack, "mode")) == Mode.DETECT)
			return super.getDigSpeed(stack, state) / 7f;
		return super.getDigSpeed(stack, state);
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos,
			EntityPlayer player) {
		if (stack.getTagCompound() == null || player.worldObj.isRemote)
			return false;
		Block block = player.worldObj.getBlockState(pos).getBlock();
		IBlockState state = player.worldObj.getBlockState(pos);
		switch (Mode.valueOf(NBTHelper.getString(stack, "mode"))) {
		case NORMAL:
			return false;
		case VEIN:
			if (!isOreInDictionary(block))
				return false;
			if (!block.canSilkHarvest(player.worldObj, pos, state, player)
					&& EnchantmentHelper.getSilkTouchModifier(player))
				return false;
			vein(stack, pos, player, block, state, 0);
			return true;
		case DETECT:
			if (ForgeHooks.isToolEffective(player.worldObj, pos, stack))
				return detect(pos, player, block);
			else
				return false;
		case TELE:
			return remote(pos, player, block, state);
		default:
			break;

		}

		return super.onBlockStartBreak(stack, pos, player);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List<String> tooltip, boolean advanced) {
		if (NBTHelper.getBoolean(stack, "bound")
				&& Mode.valueOf(NBTHelper.getString(stack, "mode")) == Mode.TELE)
			tooltip.add("Bound to x:" + NBTHelper.getInt(stack, "x") + ", y:"
					+ NBTHelper.getInt(stack, "y") + ", z:"
					+ NBTHelper.getInt(stack, "z"));
	}

	private boolean remote(BlockPos pos, EntityPlayer player, Block block,
			IBlockState state) {
		ItemStack stack = player.getHeldItem();
		if (!NBTHelper.getBoolean(stack, "bound"))
			return false;
		World world = MinecraftServer.getServer().worldServerForDimension(
				NBTHelper.getInt(stack, "dimension"));
		BlockPos invPos = new BlockPos(NBTHelper.getInt(stack, "x"),
				NBTHelper.getInt(stack, "y"), NBTHelper.getInt(stack, "z"));
		if (!world.getChunkFromBlockCoords(invPos).isLoaded()) {
			player.addChatMessage(new ChatComponentText(
					"Inventory is not loaded."));
			return false;
		}
		for (ItemStack s : getDrops(player, pos, block, state)) {
			IInventory inv = (IInventory) world.getTileEntity(invPos);
			ItemStack rest = inv != null ? TileEntityHopper
					.putStackInInventoryAllSlots(inv, s, null) : s;
			world.setBlockToAir(pos);
			if (!(rest == null || rest.stackSize == 0)) {
				world.spawnEntityInWorld(new EntityItem(world, pos.getX() + .5,
						pos.getY() + .5, pos.getZ() + .5, rest.copy()));
			}
		}
		return true;

	}

	private boolean detect(BlockPos pos, EntityPlayer player, Block block) {
		World world = player.worldObj;
		for (int y2 = pos.getY(); y2 > 0; y2--) {
			for (BlockPos bl : getAround(new BlockPos(pos.getX(), y2,
					pos.getZ()))) {
				Block b = player.worldObj.getBlockState(bl).getBlock();
				if (isOreInDictionary(b)) {
					List<ItemStack> ls = getDrops(player, bl, world
							.getBlockState(bl).getBlock(),
							world.getBlockState(bl));
					for (ItemStack s : ls) {
						EntityItem ei = new EntityItem(world, player.posX,
								player.posY, player.posZ, s.copy());
						world.spawnEntityInWorld(ei);
					}
					player.worldObj.setBlockState(bl,
							Blocks.stone.getStateFromMeta(0));

					player.getHeldItem().attemptDamageItem(1, world.rand);
					return true;

				}
			}
		}
		return false;

	}

	private void vein(ItemStack stack, BlockPos pos, EntityPlayer player,
			Block block, IBlockState state, int num) {
		if (num >= 30)
			return;
		World world = player.worldObj;
		for (BlockPos bl : getCube(pos)) {
			if (Block.isEqualTo(world.getBlockState(bl).getBlock(), block)) {
				List<ItemStack> ls = getDrops(player, bl,
						world.getBlockState(bl).getBlock(),
						world.getBlockState(bl));
				for (ItemStack s : ls) {
					EntityItem ei = new EntityItem(world, player.posX,
							player.posY, player.posZ, s.copy());
					world.spawnEntityInWorld(ei);
				}
				world.setBlockToAir(bl);
				IBlockState l = world.getBlockState(pos);
				world.playAuxSFX(2001, pos, Block.getStateId(l));
				stack.attemptDamageItem(1, world.rand);
				vein(stack, bl, player, block, state, num++);
			}

		}
	}

	private List<ItemStack> getDrops(EntityPlayer player, BlockPos pos,
			Block block, IBlockState state) {
		List<ItemStack> lis;
		World world = player.worldObj;
		if (!EnchantmentHelper.getSilkTouchModifier(player))
			lis = block.getDrops(world, pos, state,
					EnchantmentHelper.getFortuneModifier(player));
		else if (block.canSilkHarvest(world, pos, state, player))
			lis = Arrays.asList(new ItemStack[] { new ItemStack(block, 1, block
					.getMetaFromState(state)) });
		else
			lis = block.getDrops(world, pos, state,
					EnchantmentHelper.getFortuneModifier(player));
		return lis;
	}

	private static boolean isOreInDictionary(Block block) {
		for (String ore : OreDictionary.getOreNames()) {
			if (ore.startsWith("ore"))
				for (ItemStack s : OreDictionary.getOres(ore))
					if (Block.getBlockFromItem(s.getItem()) == block)
						return true;
		}
		return false;
	}

	private List<BlockPos> getCube(BlockPos pos) {
		List<BlockPos> lis = new ArrayList<BlockPos>();
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				for (int k = -1; k <= 1; k++)
					lis.add(new BlockPos(pos.getX() + i, pos.getY() + j, pos
							.getZ() + k));
		return lis;

	}

	private List<BlockPos> getAround(BlockPos pos) {
		List<BlockPos> lis = new ArrayList<BlockPos>();
		for (int i = -1; i <= 1; i++)
			for (int k = -1; k <= 1; k++)
				lis.add(new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + k));
		return lis;

	}
}
