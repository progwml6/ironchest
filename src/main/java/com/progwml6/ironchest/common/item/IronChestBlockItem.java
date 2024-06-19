package com.progwml6.ironchest.common.item;

import com.progwml6.ironchest.client.model.inventory.IronChestItemStackRenderer;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.regular.entity.CopperChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.CrystalChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.DiamondChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.DirtChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.GoldChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.IronChestBlockEntity;
import com.progwml6.ironchest.common.block.regular.entity.ObsidianChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedCopperChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedCrystalChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDiamondChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDirtChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedGoldChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedIronChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedObsidianChestBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class IronChestBlockItem extends BlockItem {

  protected IronChestsTypes type;

  protected Boolean trapped;

  public IronChestBlockItem(Block block, Properties properties, IronChestsTypes type, Boolean trapped) {
    super(block, properties);

    this.type = type;
    this.trapped = trapped;
  }

  @Override
  public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    super.initializeClient(consumer);

    consumer.accept(new IClientItemExtensions() {
      @Override
      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        Supplier<BlockEntity> modelToUse;

        if (trapped) {
          switch (type) {
            case GOLD -> modelToUse = () -> new TrappedGoldChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_GOLD_CHEST.get().defaultBlockState());
            case DIAMOND -> modelToUse = () -> new TrappedDiamondChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get().defaultBlockState());
            case COPPER -> modelToUse = () -> new TrappedCopperChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_COPPER_CHEST.get().defaultBlockState());
            case CRYSTAL -> modelToUse = () -> new TrappedCrystalChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get().defaultBlockState());
            case OBSIDIAN -> modelToUse = () -> new TrappedObsidianChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get().defaultBlockState());
            case DIRT -> modelToUse = () -> new TrappedDirtChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_DIRT_CHEST.get().defaultBlockState());
            default -> modelToUse = () -> new TrappedIronChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_IRON_CHEST.get().defaultBlockState());
          }
        } else {
          switch (type) {
            case GOLD -> modelToUse = () -> new GoldChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.GOLD_CHEST.get().defaultBlockState());
            case DIAMOND -> modelToUse = () -> new DiamondChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.DIAMOND_CHEST.get().defaultBlockState());
            case COPPER -> modelToUse = () -> new CopperChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.COPPER_CHEST.get().defaultBlockState());
            case CRYSTAL -> modelToUse = () -> new CrystalChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.CRYSTAL_CHEST.get().defaultBlockState());
            case OBSIDIAN -> modelToUse = () -> new ObsidianChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.OBSIDIAN_CHEST.get().defaultBlockState());
            case DIRT -> modelToUse = () -> new DirtChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.DIRT_CHEST.get().defaultBlockState());
            default -> modelToUse = () -> new IronChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.IRON_CHEST.get().defaultBlockState());
          }
        }

        return new IronChestItemStackRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels(), modelToUse);
      }
    });
  }
}
