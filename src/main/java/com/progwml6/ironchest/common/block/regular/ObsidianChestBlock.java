package com.progwml6.ironchest.common.block.regular;

import com.mojang.serialization.MapCodec;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.regular.entity.ObsidianChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ObsidianChestBlock extends AbstractIronChestBlock {

  public static final MapCodec<ObsidianChestBlock> CODEC = simpleCodec(ObsidianChestBlock::new);

  public ObsidianChestBlock(BlockBehaviour.Properties properties) {
    super(properties, IronChestsBlockEntityTypes.OBSIDIAN_CHEST::get, IronChestsTypes.OBSIDIAN);
  }

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new ObsidianChestBlockEntity(blockPos, blockState);
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }
}
