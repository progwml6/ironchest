package cpw.mods.ironchest;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.UP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIronChest extends BlockContainer {
	private Random random = new Random();

	public BlockIronChest() {
		super(Material.iron);
		setBlockName("IronChest");
		setHardness(3.0F);
		setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	/**
	 * Overridden by {@link #createTileEntity(World, int)}
	 */
	@Override
	public TileEntity createNewTileEntity(World w, int i) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 22;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return IronChestType.makeEntity(metadata);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		if (j < IronChestType.values().length) {
			IronChestType type = IronChestType.values()[j];
			return type.getIcon(i);
		}
		return null;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> items = Lists.newArrayList();
		ItemStack stack = new ItemStack(this, 1, metadata);
		IronChestType.values()[IronChestType.validateMeta(metadata)].adornItemDrop(stack);
		items.add(stack);
		return items;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int i1, float f1, float f2, float f3) {
		if (world.isRemote)
			return true;
		else {
			IInventory iinventory = chestInventory(world, i, j, k);

			if (iinventory != null)
				player.openGui(IronChest.instance, ((TileEntityIronChest) world.getTileEntity(i, j, k)).getType().ordinal(), world, i, j, k);

			return true;
		}
	}

	public IInventory chestInventory(World world, int i, int j, int k) {
		TileEntity object = (TileEntityIronChest) world.getTileEntity(i, j, k);

		if (object == null) {
			return null;
		} else if (world.isSideSolid(i, j + 1, k, DOWN)) {
			return null;
		} else if (isOcelotOnChest(world, i, j, k)) {
			return null;
		} else if (world.getBlock(i - 1, j, k) == this && (world.isSideSolid(i - 1, j + 1, k, DOWN) || isOcelotOnChest(world, i - 1, j, k))) {
			return null;
		} else if (world.getBlock(i + 1, j, k) == this && (world.isSideSolid(i + 1, j + 1, k, DOWN) || isOcelotOnChest(world, i + 1, j, k))) {
			return null;
		} else if (world.getBlock(i, j, k - 1) == this && (world.isSideSolid(i, j + 1, k - 1, DOWN) || isOcelotOnChest(world, i, j, k - 1))) {
			return null;
		} else if (world.getBlock(i, j, k + 1) == this && (world.isSideSolid(i, j + 1, k + 1, DOWN) || isOcelotOnChest(world, i, j, k + 1))) {
			return null;
		} else
			return (IInventory) object;
	}

	private static boolean isOcelotOnChest(World world, int i, int j, int k) {
		Iterator iterator = world.getEntitiesWithinAABB(EntityOcelot.class, AxisAlignedBB.getAABBPool().getAABB((double) i, (double) (j + 1), (double) k, (double) (i + 1), (double) (j + 2), (double) (k + 1))).iterator();
		EntityOcelot entityocelot1;

		do {
			if (!iterator.hasNext())
				return false;

			EntityOcelot entityocelot = (EntityOcelot) iterator.next();
			entityocelot1 = (EntityOcelot) entityocelot;
		} while (!entityocelot1.isSitting());

		return true;
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		world.markBlockForUpdate(i, j, k);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack itemStack) {
		byte chestFacing = 0;
		int facing = MathHelper.floor_double((double) ((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		if (facing == 0) {
			chestFacing = 2;
		}
		if (facing == 1) {
			chestFacing = 5;
		}
		if (facing == 2) {
			chestFacing = 3;
		}
		if (facing == 3) {
			chestFacing = 4;
		}
		TileEntity te = world.getTileEntity(i, j, k);
		if (te != null && te instanceof TileEntityIronChest) {
			TileEntityIronChest teic = (TileEntityIronChest) te;
			teic.wasPlaced(entityliving, itemStack);
			teic.setFacing(chestFacing);
			world.markBlockForUpdate(i, j, k);
		}
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block b, int l) {
		TileEntityIronChest te = (TileEntityIronChest) world.getTileEntity(i, j, k);
		if (te != null) {
			te.removeAdornments();
			dropContent(0, te, world, te.xCoord, te.yCoord, te.zCoord);
		}
		super.breakBlock(world, i, j, k, b, l);
	}

	public void dropContent(int newSize, IInventory chest, World world, int xCoord, int yCoord, int zCoord) {
		for (int l = newSize; l < chest.getSizeInventory(); l++) {
			ItemStack itemstack = chest.getStackInSlot(l);
			if (itemstack == null) {
				continue;
			}
			float f = random.nextFloat() * 0.8F + 0.1F;
			float f1 = random.nextFloat() * 0.8F + 0.1F;
			float f2 = random.nextFloat() * 0.8F + 0.1F;
			while (itemstack.stackSize > 0) {
				int i1 = random.nextInt(21) + 10;
				if (i1 > itemstack.stackSize) {
					i1 = itemstack.stackSize;
				}
				itemstack.stackSize -= i1;
				EntityItem entityitem = new EntityItem(world, (float) xCoord + f, (float) yCoord + (newSize > 0 ? 1 : 0) + f1, (float) zCoord + f2, new ItemStack(itemstack.getItem(), i1, itemstack.getItemDamage()));
				float f3 = 0.05F;
				entityitem.motionX = (float) random.nextGaussian() * f3;
				entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float) random.nextGaussian() * f3;
				if (itemstack.hasTagCompound()) {
					entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
				}
				world.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (IronChestType type : IronChestType.values()) {
			if (type.isValidForCreativeMode()) {
				par3List.add(new ItemStack(this, 1, type.ordinal()));
			}
		}
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityIronChest) {
			TileEntityIronChest teic = (TileEntityIronChest) te;
			if (teic.getType().isExplosionResistant()) {
				return 10000F;
			}
		}
		return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	@Override
	public int getComparatorInputOverride(World world, int i, int j, int k, int l) {
		return Container.calcRedstoneFromInventory((TileEntityIronChest) world.getTileEntity(i, j, k));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		for (IronChestType typ : IronChestType.values()) {
			typ.makeIcons(par1IconRegister);
		}
	}

	private static final ForgeDirection[] validRotationAxes = new ForgeDirection[] { UP, DOWN };

	@Override
	public ForgeDirection[] getValidRotations(World world, int x, int y, int z) {
		return validRotationAxes;
	}

	@Override
	public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
		if (worldObj.isRemote) {
			return false;
		}
		if (axis == UP || axis == DOWN) {
			TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityIronChest) {
				TileEntityIronChest icte = (TileEntityIronChest) tileEntity;
				icte.rotateAround(axis);
			}
			return true;
		}
		return false;
	}
}