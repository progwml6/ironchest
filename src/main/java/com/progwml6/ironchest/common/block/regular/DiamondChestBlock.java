package com.progwml6.ironchest.common.block.regular;

import com.mojang.serialization.MapCodec;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.regular.entity.DiamondChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class DiamondChestBlock extends AbstractIronChestBlock {

  public static final MapCodec<DiamondChestBlock> CODEC = simpleCodec(DiamondChestBlock::new);

  public DiamondChestBlock(Properties properties) {
    super(properties, IronChestsBlockEntityTypes.DIAMOND_CHEST::get, IronChestsTypes.DIAMOND);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new DiamondChestBlockEntity(blockPos, blockState);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }
}
