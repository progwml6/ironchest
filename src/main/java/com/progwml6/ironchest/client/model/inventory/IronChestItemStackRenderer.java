package com.progwml6.ironchest.client.model.inventory;

import com.mojang.blaze3d.vertex.PoseStack;
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
import com.progwml6.ironchest.common.item.IronChestBlockItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronChestItemStackRenderer extends BlockEntityWithoutLevelRenderer {

  private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;

  public IronChestItemStackRenderer() {
    super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

    this.blockEntityRenderDispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
  }

  @Override
  public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
    Item item = stack.getItem();
    if (item instanceof IronChestBlockItem ironChestBlockItem) {
      BlockEntity modelToUse;
      boolean trapped = ironChestBlockItem.getTrapped();
      IronChestsTypes type = ironChestBlockItem.getType();

      switch (type) {
        case GOLD -> modelToUse = trapped ? new TrappedGoldChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_GOLD_CHEST.get().defaultBlockState()) : new GoldChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.GOLD_CHEST.get().defaultBlockState());
        case DIAMOND -> modelToUse = trapped ? new TrappedDiamondChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get().defaultBlockState()) : new DiamondChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.DIAMOND_CHEST.get().defaultBlockState());
        case COPPER -> modelToUse = trapped ? new TrappedCopperChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_COPPER_CHEST.get().defaultBlockState()) : new CopperChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.COPPER_CHEST.get().defaultBlockState());
        case CRYSTAL -> modelToUse = trapped ? new TrappedCrystalChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get().defaultBlockState()) : new CrystalChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.CRYSTAL_CHEST.get().defaultBlockState());
        case OBSIDIAN -> modelToUse = trapped ? new TrappedObsidianChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get().defaultBlockState()) : new ObsidianChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.OBSIDIAN_CHEST.get().defaultBlockState());
        case DIRT -> modelToUse = trapped ? new TrappedDirtChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_DIRT_CHEST.get().defaultBlockState()) : new DirtChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.DIRT_CHEST.get().defaultBlockState());
        default -> modelToUse = trapped ? new TrappedIronChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.TRAPPED_IRON_CHEST.get().defaultBlockState()) : new IronChestBlockEntity(BlockPos.ZERO, IronChestsBlocks.IRON_CHEST.get().defaultBlockState());
      }

      this.blockEntityRenderDispatcher.renderItem(modelToUse, poseStack, buffer, packedLight, packedOverlay);
    } else {
      super.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
    }
  }
}
