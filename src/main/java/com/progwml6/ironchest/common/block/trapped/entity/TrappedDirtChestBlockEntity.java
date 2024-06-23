package com.progwml6.ironchest.common.block.trapped.entity;

import com.progwml6.ironchest.common.Util;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.datacomponents.IronChestsDataComponents;
import com.progwml6.ironchest.common.inventory.IronChestMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TrappedDirtChestBlockEntity extends AbstractTrappedIronChestBlockEntity {

  private static final ItemStack DIRT_CHEST_BOOK = Util.createDirtGuideBook();

  public TrappedDirtChestBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(IronChestsBlockEntityTypes.TRAPPED_DIRT_CHEST.get(), blockPos, blockState, IronChestsTypes.DIRT, IronChestsBlocks.TRAPPED_DIRT_CHEST::get);
  }

  @Override
  protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
    return IronChestMenu.createDirtContainer(containerId, playerInventory, this);
  }

  @Override
  public void removeAdornments() {
    if (!this.getItems().get(0).isEmpty() && ItemStack.isSameItem(this.getItems().get(0), DIRT_CHEST_BOOK)) {
      this.getItems().set(0, ItemStack.EMPTY);
    }
  }

  @Override
  public void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
    super.saveAdditional(pTag, pRegistries);

    pTag.putBoolean("chest_placed_already", true);
  }


  @Override
  protected void applyImplicitComponents(BlockEntity.DataComponentInput pComponentInput) {
    super.applyImplicitComponents(pComponentInput);

    if (!pComponentInput.getOrDefault(IronChestsDataComponents.CHEST_PLACED_ALREADY.get(), false)) {
      this.setItem(0, DIRT_CHEST_BOOK.copy());
    }
  }

  @Override
  protected void collectImplicitComponents(DataComponentMap.Builder pComponents) {
    super.collectImplicitComponents(pComponents);
    pComponents.set(IronChestsDataComponents.CHEST_PLACED_ALREADY.get(), true);
  }

  @Override
  public void removeComponentsFromTag(CompoundTag pTag) {
    super.removeComponentsFromTag(pTag);
    pTag.remove("chest_placed_already");
  }
}
