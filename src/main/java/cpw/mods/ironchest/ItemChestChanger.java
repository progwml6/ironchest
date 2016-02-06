/*******************************************************************************
 * Copyright (c) 2012 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *     cpw - initial API and implementation
 ******************************************************************************/
package cpw.mods.ironchest;

import java.util.List;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChestChanger extends Item {
	private ChestChangerType type;
	
	public ItemChestChanger(ChestChangerType type) {
		this.type = type;
		
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("ironchest:" + type.name());
		this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return false;
		if (this.type.canUpgrade(IronChestTypeSimple.WOOD)) {
			if (!(world.getBlockState(pos).getBlock() instanceof BlockChest)) { return false; }
		} else {
			if (world.getBlockState(pos) != IronChest.ironChestBlock.getStateFromMeta(IronChestType.valueOf(type.getSource().getName().toUpperCase()).ordinal())) { return false; }
		}
		TileEntity te = world.getTileEntity(pos);
		TileEntityIronChest newchest = new TileEntityIronChest();
		ItemStack[] chestContents = new ItemStack[27];
		int chestFacing = 0;
		if (te != null) {
			if (te instanceof TileEntityIronChest) {
				chestContents = ((TileEntityIronChest) te).chestContents;
				chestFacing = ((TileEntityIronChest) te).getFacing();
				newchest = IronChestType.makeEntity(this.getTargetChestOrdinal(this.type.ordinal(), stack.getItemDamage()));
				if (newchest == null) return false;
			} else if (te instanceof TileEntityChest) {
				IBlockState chestState = world.getBlockState(pos);
				EnumFacing facing = chestState.getValue(BlockChest.FACING);
				if (facing == EnumFacing.NORTH) chestFacing = 2;
				if (facing == EnumFacing.EAST) chestFacing = 5;
				if (facing == EnumFacing.SOUTH) chestFacing = 3;
				if (facing == EnumFacing.WEST) chestFacing = 4;
				if (((TileEntityChest) te).numPlayersUsing > 0) return false;
				if (!getType().canUpgrade(IronChestTypeSimple.WOOD)) return false;
				chestContents = new ItemStack[((TileEntityChest) te).getSizeInventory()];
				for (int i = 0; i < chestContents.length; i ++)
					chestContents[i] = ((TileEntityChest) te).getStackInSlot(i);
				newchest = IronChestType.makeEntity(this.getTargetChestOrdinal(this.type.ordinal(), stack.getItemDamage()));
			}
		}
		
		te.updateContainingBlockInfo();
		if (te instanceof TileEntityChest) ((TileEntityChest) te).checkForAdjacentChests();
		
		world.removeTileEntity(pos);
		world.setBlockToAir(pos);
		
		world.setTileEntity(pos, newchest);
		world.setBlockState(pos, IronChest.ironChestBlock.getStateFromMeta(newchest.getType().ordinal()), 3);
		
		world.markBlockForUpdate(pos);
		
		TileEntity te2 = world.getTileEntity(pos);
		if (te2 instanceof TileEntityIronChest) {
			((TileEntityIronChest) te2).setContents(chestContents);
			((TileEntityIronChest) te2).setFacing((byte) chestFacing);
		}
		
		stack.stackSize = player.capabilities.isCreativeMode ? stack.stackSize : stack.stackSize - 1;
		return true;
	}
	
	@Override @SideOnly(Side.CLIENT) public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
		for (int i = 0; i < this.type.upgradeChain.length; i ++) list.add(new ItemStack(item, 1, i));
	}
	
	@Override public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "_" + this.type.getTargetName(stack.getItemDamage()).toUpperCase();
	}
	
	public int getTargetChestOrdinal(int sourceOrdinal, int meta) {
		return type.getTarget(meta);
	}
	
	public ChestChangerType getType() {
		return type;
	}
}
