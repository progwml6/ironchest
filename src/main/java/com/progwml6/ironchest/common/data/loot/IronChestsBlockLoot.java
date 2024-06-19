package com.progwml6.ironchest.common.data.loot;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class IronChestsBlockLoot extends BlockLootSubProvider {

  private static final Set<Item> EXPLOSION_RESISTANT = Set.of();

  private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

  public IronChestsBlockLoot() {
    super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
  }

  @Override
  protected void generate() {
    this.add(IronChestsBlocks.IRON_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.GOLD_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.DIAMOND_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.COPPER_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.CRYSTAL_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.OBSIDIAN_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.DIRT_CHEST.get(), this::createDirtChestNameableBlockEntityTable);

    // Trapped Chests
    this.add(IronChestsBlocks.TRAPPED_IRON_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_GOLD_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_COPPER_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_DIRT_CHEST.get(), this::createDirtChestNameableBlockEntityTable);
  }

  protected LootTable.Builder createDirtChestNameableBlockEntityTable(Block p_252291_) {
    return LootTable.lootTable().withPool(this.applyExplosionCondition(p_252291_, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(p_252291_).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("ChestPlacedAlready", "ChestPlacedAlready")))));
  }

  @Override
  protected void add(@NotNull Block block, @NotNull LootTable.Builder table) {
    //Overwrite the core register method to add to our list of known blocks
    super.add(block, table);
    knownBlocks.add(block);
  }
  
  @Override
  protected Iterable<Block> getKnownBlocks() {
    return knownBlocks;
  }
}
