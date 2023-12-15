package com.progwml6.ironchest.common.block.trapped;

import com.mojang.serialization.MapCodec;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedGoldChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TrappedGoldChestBlock extends AbstractTrappedIronChestBlock {

  public static final MapCodec<TrappedGoldChestBlock> CODEC = simpleCodec(TrappedGoldChestBlock::new);

  public TrappedGoldChestBlock(Properties properties) {
    super(properties, IronChestsBlockEntityTypes.TRAPPED_GOLD_CHEST::get, IronChestsTypes.GOLD);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new TrappedGoldChestBlockEntity(blockPos, blockState);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }
}
