package com.progwml6.ironchest.common.block.regular;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractIronChestBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

  public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  private static final VoxelShape SHAPE = Block.column(14.0, 0.0, 14.0);

  private final IronChestsTypes type;

  protected final Supplier<BlockEntityType<? extends AbstractIronChestBlockEntity>> blockEntityType;

  public AbstractIronChestBlock(BlockBehaviour.Properties properties, Supplier<BlockEntityType<? extends AbstractIronChestBlockEntity>> blockEntityType, IronChestsTypes type) {
    super(properties);

    this.type = type;
    this.blockEntityType = blockEntityType;

    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
  }

  @Override
  protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
    if (state.getValue(WATERLOGGED)) {
      scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
    }

    return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
  }

  @Override
  protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Direction direction = context.getHorizontalDirection().getOpposite();
    FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

    return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
  }

  @Override
  @Deprecated
  public FluidState getFluidState(BlockState blockState) {
    return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
  }

  @Override
  public void affectNeighborsAfterRemoval(BlockState blockState, ServerLevel level, BlockPos blockPos, boolean isMoving) {
    level.updateNeighbourForOutputSignal(blockPos, this);
  }

  @Override
  @Deprecated
  public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
    if (pLevel.isClientSide) {
      return InteractionResult.SUCCESS;
    } else {
      MenuProvider menuProvider = this.getMenuProvider(pState, pLevel, pPos);

      if (menuProvider != null) {
        pPlayer.openMenu(menuProvider);
        pPlayer.awardStat(this.getOpenChestStat());
      }

      return InteractionResult.CONSUME;
    }
  }

  protected Stat<ResourceLocation> getOpenChestStat() {
    return Stats.CUSTOM.get(Stats.OPEN_CHEST);
  }

  public BlockEntityType<? extends AbstractIronChestBlockEntity> blockEntityType() {
    return this.blockEntityType.get();
  }

  @Nullable
  @Override
  public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
    if (isChestBlockedAt(level, pos))
      return null;

    if (level.getBlockEntity(pos) instanceof AbstractIronChestBlockEntity ironChestBlockEntity)
      return ironChestBlockEntity;

    return null;
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    return level.isClientSide ? createTickerHelper(blockEntityType, this.blockEntityType(), AbstractIronChestBlockEntity::lidAnimateTick) : null;
  }

  public static boolean isChestBlockedAt(LevelAccessor level, BlockPos pos) {
    return isBlockedChestByBlock(level, pos) || isCatSittingOnChest(level, pos);
  }

  private static boolean isBlockedChestByBlock(BlockGetter level, BlockPos pos) {
    BlockPos above = pos.above();

    return level.getBlockState(above).isRedstoneConductor(level, above);
  }

  private static boolean isCatSittingOnChest(LevelAccessor levelAccessor, BlockPos blockPos) {
    List<Cat> list = levelAccessor.getEntitiesOfClass(
      Cat.class, new AABB(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 2, blockPos.getZ() + 1)
    );

    if (!list.isEmpty()) {
      for (Cat cat : list) {
        if (cat.isInSittingPose()) {
          return true;
        }
      }
    }

    return false;
  }

  @Override
  public boolean hasAnalogOutputSignal(BlockState blockState) {
    return true;
  }

  @Override
  protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
    if (!isChestBlockedAt(level, pos) && level.getBlockEntity(pos) instanceof AbstractIronChestBlockEntity ironChestBlockEntity)
      return AbstractContainerMenu.getRedstoneSignalFromContainer(ironChestBlockEntity);

    return AbstractContainerMenu.getRedstoneSignalFromContainer(null);
  }

  @Override
  public BlockState rotate(BlockState blockState, Rotation rotation) {
    return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
  }

  @Override
  protected BlockState mirror(BlockState state, Mirror mirror) {
    return state.rotate(mirror.getRotation(state.getValue(FACING)));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
    blockBlockStateBuilder.add(FACING, WATERLOGGED);
  }

  @Override
  protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
    return false;
  }

  @Override
  public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
    BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);

    if (blockEntity instanceof AbstractIronChestBlockEntity) {
      ((AbstractIronChestBlockEntity) blockEntity).recheckOpen();
    }
  }

  @Nullable
  public static IronChestsTypes getTypeFromBlock(Block block) {
    return block instanceof AbstractIronChestBlock ? ((AbstractIronChestBlock) block).getType() : null;
  }

  public IronChestsTypes getType() {
    return this.type;
  }
}
