package com.progwml6.ironchest.common.data.loot;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class IronChestsBlockLoot extends BlockLootSubProvider {

  private static final Set<Item> EXPLOSION_RESISTANT = Set.of();

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
    this.add(IronChestsBlocks.DIRT_CHEST.get(), this::createNameableBlockEntityTable);

    // Trapped Chests
    this.add(IronChestsBlocks.TRAPPED_IRON_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_GOLD_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_COPPER_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get(), this::createNameableBlockEntityTable);
    this.add(IronChestsBlocks.TRAPPED_DIRT_CHEST.get(), this::createNameableBlockEntityTable);
  }

  @Override
  protected Iterable<Block> getKnownBlocks() {
    return IronChestsBlocks.BLOCKS.getEntries() // Get all registered entries
      .stream() // Stream the wrapped objects
      .flatMap(RegistryObject::stream) // Get the object if available
      ::iterator; // Create the iterable
  }
}
