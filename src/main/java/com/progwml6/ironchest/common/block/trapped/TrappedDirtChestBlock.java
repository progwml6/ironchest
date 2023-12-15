package com.progwml6.ironchest.common.block.trapped;

import com.mojang.serialization.MapCodec;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedDirtChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TrappedDirtChestBlock extends AbstractTrappedIronChestBlock {

  public static final MapCodec<TrappedDirtChestBlock> CODEC = simpleCodec(TrappedDirtChestBlock::new);

  public TrappedDirtChestBlock(Properties properties) {
    super(properties, IronChestsBlockEntityTypes.TRAPPED_DIRT_CHEST::get, IronChestsTypes.DIRT);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new TrappedDirtChestBlockEntity(blockPos, blockState);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }
}
