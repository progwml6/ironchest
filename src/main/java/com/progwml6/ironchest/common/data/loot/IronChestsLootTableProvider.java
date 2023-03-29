package com.progwml6.ironchest.common.data.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class IronChestsLootTableProvider extends LootTableProvider {

  public IronChestsLootTableProvider(PackOutput pOutput) {
    super(pOutput, Set.of(), List.of(new SubProviderEntry(IronChestsBlockLoot::new, LootContextParamSets.BLOCK)));
  }
}
