package cpw.ironchest.common.tiles;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import cpw.ironchest.common.IronChest;
import cpw.ironchest.common.blocks.BlockIronChest;
import cpw.ironchest.common.containers.ContainerIronChest;
import cpw.ironchest.common.items.ItemChestChanger;
import cpw.ironchest.common.lib.IronChestType;
import cpw.ironchest.common.lib.network.PacketHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityIronChest extends TileEntity implements IInventory {
	private int ticksSinceSync = -1, numUsingPlayers, facing;
	public float prevLidAngle, lidAngle;
	private IronChestType type;
	public ItemStack[] chestContents, topStacks;
	private boolean inventoryTouched, hadStuff;

	public TileEntityIronChest() {
		this(IronChestType.IRON);
	}

	protected TileEntityIronChest(IronChestType type) {
		super();
		this.type = type;
		chestContents = new ItemStack[getSizeInventory()];
		topStacks = new ItemStack[8];
	}

	public ItemStack[] getContents() {
		return chestContents;
	}

	@Override
	public int getSizeInventory() {
		return type.size;
	}

	public int getFacing() {
		return facing;
	}

	@Override
	public String getInventoryName() {
		return type.name();
	}

	public IronChestType getType() {
		return type;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		inventoryTouched = true;
		return chestContents[i];
	}

	@Override
	public void markDirty() {
		super.markDirty();
		sortTopStacks();
	}

	public void sortTopStacks() {
		if (!type.isTransparent() || (worldObj != null && worldObj.isRemote)) {
			return;
		}
		ItemStack[] tempCopy = new ItemStack[getSizeInventory()];
		boolean hasStuff = false;
		int compressedIdx = 0;
		mainLoop: for (int i = 0; i < getSizeInventory(); i++) {
			if (chestContents[i] != null) {
				for (int j = 0; j < compressedIdx; j++) {
					if (tempCopy[j].isItemEqual(chestContents[i])) {
						tempCopy[j].stackSize += chestContents[i].stackSize;
						continue mainLoop;
					}
				}
				tempCopy[compressedIdx++] = chestContents[i].copy();
				hasStuff = true;
			}
		}
		if (!hasStuff && hadStuff) {
			hadStuff = false;
			for (int i = 0; i < topStacks.length; i++) {
				topStacks[i] = null;
			}
			if (worldObj != null) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			return;
		}
		hadStuff = true;
		Arrays.sort(tempCopy, new Comparator<ItemStack>() {
			@Override
			public int compare(ItemStack o1, ItemStack o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				} else {
					return o2.stackSize - o1.stackSize;
				}
			}
		});
		int p = 0;
		for (int i = 0; i < tempCopy.length; i++) {
			if (tempCopy[i] != null && tempCopy[i].stackSize > 0) {
				topStacks[p++] = tempCopy[i];
				if (p == topStacks.length) {
					break;
				}
			}
		}
		for (int i = p; i < topStacks.length; i++) {
			topStacks[i] = null;
		}
		if (worldObj != null) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (chestContents[i] != null) {
			if (chestContents[i].stackSize <= j) {
				ItemStack itemstack = chestContents[i];
				chestContents[i] = null;
				markDirty();
				return itemstack;
			}
			ItemStack itemstack1 = chestContents[i].splitStack(j);
			if (chestContents[i].stackSize == 0) {
				chestContents[i] = null;
			}
			markDirty();
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		chestContents[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		chestContents = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			int j = nbt1.getByte("Slot") & 0xff;
			if (j >= 0 && j < chestContents.length) {
				chestContents[j] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		facing = nbt.getByte("facing");
		sortTopStacks();
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < chestContents.length; i++) {
			if (chestContents[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("Slot", (byte) i);
				chestContents[i].writeToNBT(nbt1);
				nbttaglist.appendTag(nbt1);
			}
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setByte("facing", (byte) facing);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (worldObj != null && !worldObj.isRemote && numUsingPlayers != 0 && (ticksSinceSync + xCoord + yCoord + zCoord) % 200 == 0) {
			numUsingPlayers = 0;
			float var1 = 5.0F;

			List<EntityPlayer> playerlist = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB((double) ((float) xCoord - var1), (double) ((float) yCoord - var1), (double) ((float) zCoord - var1), (double) ((float) (xCoord + 1) + var1), (double) ((float) (yCoord + 1) + var1), (double) ((float) (zCoord + 1) + var1)));
			Iterator<EntityPlayer> player = playerlist.iterator();

			while (player.hasNext()) {
				if (player.next().openContainer instanceof ContainerIronChest) {
					++numUsingPlayers;
				}
			}
		}

		if (worldObj != null && !worldObj.isRemote && ticksSinceSync < 0) {
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, IronChest.ironChestBlock, 3, ((numUsingPlayers << 3) & 0xF8) | (facing & 0x7));
		}
		if (!worldObj.isRemote && inventoryTouched) {
			inventoryTouched = false;
			sortTopStacks();
		}

		ticksSinceSync++;
		prevLidAngle = lidAngle;
		float f = 0.1F;
		if (numUsingPlayers > 0 && lidAngle == 0.0F) {
			double d = (double) xCoord + 0.5D;
			double d1 = (double) zCoord + 0.5D;
			worldObj.playSoundEffect(d, (double) yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0
				&& lidAngle < 1.0F) {
			float f1 = lidAngle;
			if (numUsingPlayers > 0) {
				lidAngle += f;
			} else {
				lidAngle -= f;
			}
			if (lidAngle > 1.0F) {
				lidAngle = 1.0F;
			}
			float f2 = 0.5F;
			if (lidAngle < f2 && f1 >= f2) {
				double d2 = (double) xCoord + 0.5D;
				double d3 = (double) zCoord + 0.5D;
				worldObj.playSoundEffect(d2, (double) yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if (lidAngle < 0.0F) {
				lidAngle = 0.0F;
			}
		}
	}

	@Override
	public boolean receiveClientEvent(int i, int j) {
		if (i == 1) {
			numUsingPlayers = j;
			return true;
		} else {
			return super.receiveClientEvent(i, j);
		}
	}

	@Override
	public void openInventory() {
		++numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, IronChest.ironChestBlock, 1, numUsingPlayers);
	}

	@Override
	public void closeInventory() {
		--numUsingPlayers;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, IronChest.ironChestBlock, 1, numUsingPlayers);
	}

	public void setFacing(int facing2) {
		facing = facing2;
	}

	public TileEntityIronChest applyUpgradeItem(ItemChestChanger itemChestChanger) {
		if (numUsingPlayers > 0 || !itemChestChanger.getType().canUpgrade(getType())) {
			return null;
		}
		TileEntityIronChest newEntity = IronChestType.makeEntity(itemChestChanger.getTargetChestOrdinal(getType().ordinal()));
		int newSize = newEntity.chestContents.length;
		System.arraycopy(chestContents, 0, newEntity.chestContents, 0, Math.min(newSize, chestContents.length));
		BlockIronChest block = IronChest.ironChestBlock;
		block.dropContent(newSize, this, worldObj, xCoord, yCoord, zCoord);
		newEntity.setFacing(facing);
		newEntity.sortTopStacks();
		newEntity.ticksSinceSync = -1;
		return newEntity;
	}

	public ItemStack[] getTopItemStacks() {
		return topStacks;
	}

	public TileEntityIronChest updateFromMetadata(int l) {
		if (worldObj != null && worldObj.isRemote) {
			if (l != type.ordinal()) {
				worldObj.setTileEntity(xCoord, yCoord, zCoord, IronChestType.makeEntity(l));
				return (TileEntityIronChest) worldObj.getTileEntity(xCoord, yCoord, zCoord);
			}
		}
		return this;
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketHandler.getPacket(this);
	}

	public void handlePacketData(int typeData, int[] intData) {
		TileEntityIronChest chest = this;
		if (type.ordinal() != typeData) {
			chest = updateFromMetadata(typeData);
		}
		if (IronChestType.values()[typeData].isTransparent() && intData != null) {
			int pos = 0;
			if (intData.length < chest.topStacks.length * 3) {
				return;
			}
			for (int i = 0; i < chest.topStacks.length; i++) {
				if (intData[pos + 2] != 0) {
					Item it = Item.getItemById(intData[pos]);
					ItemStack is = new ItemStack(it, intData[pos + 2], intData[pos + 1]);
					chest.topStacks[i] = is;
				} else {
					chest.topStacks[i] = null;
				}
				pos += 3;
			}
		}
	}

	public int[] buildIntDataList() {
		if (type.isTransparent()) {
			int[] sortList = new int[topStacks.length * 3];
			int pos = 0;
			for (ItemStack is : topStacks) {
				if (is != null) {
					sortList[pos++] = Item.getIdFromItem(is.getItem());
					sortList[pos++] = is.getItemDamage();
					sortList[pos++] = is.stackSize;
				} else {
					sortList[pos++] = 0;
					sortList[pos++] = 0;
					sortList[pos++] = 0;
				}
			}
			return sortList;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (chestContents[par1] != null) {
			ItemStack var2 = chestContents[par1];
			chestContents[par1] = null;
			return var2;
		} else {
			return null;
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return type.acceptsStack(itemstack);
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	public void rotateAround(ForgeDirection axis) {
		setFacing((byte) ForgeDirection.getOrientation(facing).getRotation(axis).ordinal());
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, IronChest.ironChestBlock, 2, getFacing());
	}

	public void wasPlaced(EntityLivingBase entityliving, ItemStack itemStack) {}

	public void removeAdornments() {}
}