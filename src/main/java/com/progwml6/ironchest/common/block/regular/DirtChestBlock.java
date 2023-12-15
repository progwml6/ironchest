package com.progwml6.ironchest.common.block.regular;

import com.mojang.serialization.MapCodec;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.regular.entity.DirtChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class DirtChestBlock extends AbstractIronChestBlock {

  public static final MapCodec<DirtChestBlock> CODEC = simpleCodec(DirtChestBlock::new);

  public DirtChestBlock(Properties properties) {
    super(properties, IronChestsBlockEntityTypes.DIRT_CHEST::get, IronChestsTypes.DIRT);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new DirtChestBlockEntity(blockPos, blockState);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }
}
