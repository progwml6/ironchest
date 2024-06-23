package com.progwml6.ironchest.common.data.loot;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.datacomponents.IronChestsDataComponents;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public class IronChestsBlockLoot extends BlockLootSubProvider {


  private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

  public IronChestsBlockLoot(HolderLookup.Provider provider) {
    super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), provider);
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

  protected LootTable.Builder createDirtChestNameableBlockEntityTable(Block pBlock) {
    return LootTable.lootTable()
      .withPool(
        this.applyExplosionCondition(
          pBlock,
          LootPool.lootPool()
            .setRolls(ConstantValue.exactly(1.0F))
            .add(
              LootItem.lootTableItem(pBlock)
                .apply(
                  CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                    .include(DataComponents.CUSTOM_NAME)
                    .include(IronChestsDataComponents.CHEST_PLACED_ALREADY.get())
                )
            )
        )
      );
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
