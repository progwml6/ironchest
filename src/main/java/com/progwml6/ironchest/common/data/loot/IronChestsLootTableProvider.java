package com.progwml6.ironchest.common.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class IronChestsLootTableProvider {

  public static SingleRegistryBootstrap<LootTable> create() {
    return new LootTableProvider(Set.of(), List.of(new LootTableProvider.SubProviderEntry(IronChestsBlockLoot::new, LootContextParamSets.BLOCK)));
  }
}
