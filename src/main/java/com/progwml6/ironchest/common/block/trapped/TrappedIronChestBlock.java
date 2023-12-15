package com.progwml6.ironchest.common.block.trapped;

import com.mojang.serialization.MapCodec;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.trapped.entity.TrappedIronChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TrappedIronChestBlock extends AbstractTrappedIronChestBlock {

  public static final MapCodec<TrappedIronChestBlock> CODEC = simpleCodec(TrappedIronChestBlock::new);

  public TrappedIronChestBlock(Properties properties) {
    super(properties, IronChestsBlockEntityTypes.TRAPPED_IRON_CHEST::get, IronChestsTypes.IRON);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new TrappedIronChestBlockEntity(blockPos, blockState);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }
}
