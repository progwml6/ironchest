package cpw.ironchest.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import cpw.ironchest.common.IronChest;
import cpw.ironchest.common.blocks.BlockIronChest;
import cpw.ironchest.common.lib.ChestChangerType;
import cpw.ironchest.common.lib.IronChestType;
import cpw.ironchest.common.tiles.TileEntityIronChest;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemChestChanger extends Item {
	private ChestChangerType type;

	public ItemChestChanger(ChestChangerType type) {
		super();
		setMaxStackSize(1);
		this.type = type;
		setUnlocalizedName("ironchest:" + type.name());
		setCreativeTab(IronChest.tabIronChest);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("ironchest:" + type.itemName);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int X, int Y, int Z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return false;
		TileEntity te = world.getTileEntity(X, Y, Z);
		TileEntityIronChest newchest;
		if (te != null && te instanceof TileEntityIronChest) {
			TileEntityIronChest ironchest = (TileEntityIronChest) te;
			newchest = ironchest.applyUpgradeItem(this);
			if (newchest == null) {
				return false;
			}
		} else if (te != null && te instanceof TileEntityChest) {
			TileEntityChest tec = (TileEntityChest) te;
			if (tec.numPlayersUsing > 0) {
				return false;
			}
			if (!getType().canUpgrade(IronChestType.WOOD)) {
				return false;
			}
			// force the old tileentity out of the world so that adjacent chests can update
			newchest = IronChestType.makeEntity(getTargetChestOrdinal(IronChestType.WOOD.ordinal()));
			int newSize = newchest.chestContents.length;
			ItemStack[] chestContents = ObfuscationReflectionHelper.getPrivateValue(TileEntityChest.class, tec, 0);
			System.arraycopy(chestContents, 0, newchest.chestContents, 0, Math.min(newSize, chestContents.length));
			BlockIronChest block = IronChest.ironChestBlock;
			block.dropContent(newSize, tec, world, tec.xCoord, tec.yCoord, tec.zCoord);
			newchest.setFacing((byte) tec.getBlockMetadata());
			newchest.sortTopStacks();
			for (int i = 0; i < Math.min(newSize, chestContents.length); i++) {
				chestContents[i] = null;
			}
			world.setBlock(X, Y, Z, Blocks.air, 0, 3); // clear the old block out
			tec.updateContainingBlockInfo(); // reset neighboring block knowledge
			tec.checkForAdjacentChests(); // update neighboring blocks
			world.setBlock(X, Y, Z, block, newchest.getType().ordinal(), 3); // add in the iron chest
		} else {
			return false;
		}
		world.setTileEntity(X, Y, Z, newchest);
		world.setBlockMetadataWithNotify(X, Y, Z, newchest.getType().ordinal(), 3);
		stack.stackSize = 0;
		return true;
	}

	public int getTargetChestOrdinal(int sourceOrdinal) {
		return type.getTarget();
	}

	public ChestChangerType getType() {
		return type;
	}
}