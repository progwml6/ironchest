package com.progwml6.ironchest.common.block.regular.entity;

import com.progwml6.ironchest.common.Util;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.inventory.IronChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class DirtChestBlockEntity extends AbstractIronChestBlockEntity {

  private static final ItemStack DIRT_CHEST_BOOK = Util.createDirtGuideBook();

  public DirtChestBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(IronChestsBlockEntityTypes.DIRT_CHEST.get(), blockPos, blockState, IronChestsTypes.DIRT, IronChestsBlocks.DIRT_CHEST::get);
  }

  @Override
  protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
    return IronChestMenu.createDirtContainer(containerId, playerInventory, this);
  }

  @Override
  public void wasPlaced(@Nullable LivingEntity livingEntity, ItemStack itemStack) {
    if (itemStack.hasTag() && itemStack.getTag() != null) {
      CompoundTag tag = itemStack.getTagElement("BlockEntityTag");

      if (tag != null) {
        if (!tag.contains("ChestPlacedAlready")) this.setItem(0, DIRT_CHEST_BOOK.copy());
      }
    } else {
      this.setItem(0, DIRT_CHEST_BOOK.copy());
    }
  }

  @Override
  public void removeAdornments() {
    if (!this.getItems().get(0).isEmpty() && this.getItems().get(0).sameItem(DIRT_CHEST_BOOK)) {
      this.getItems().set(0, ItemStack.EMPTY);
    }
  }

  @Override
  public void saveAdditional(CompoundTag compoundTag) {
    super.saveAdditional(compoundTag);

    compoundTag.putBoolean("ChestPlacedAlready", true);
  }
}
