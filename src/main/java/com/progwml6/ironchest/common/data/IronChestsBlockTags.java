package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class IronChestsBlockTags extends BlockTagsProvider {

  public IronChestsBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper existingFileHelper) {
    super(output, lookup, IronChests.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
      IronChestsBlocks.DIRT_CHEST.get(),
      IronChestsBlocks.TRAPPED_DIRT_CHEST.get()
    );

    tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
      IronChestsBlocks.IRON_CHEST.get(),
      IronChestsBlocks.GOLD_CHEST.get(),
      IronChestsBlocks.DIAMOND_CHEST.get(),
      IronChestsBlocks.COPPER_CHEST.get(),
      IronChestsBlocks.CRYSTAL_CHEST.get(),
      IronChestsBlocks.OBSIDIAN_CHEST.get(),
      IronChestsBlocks.TRAPPED_IRON_CHEST.get(),
      IronChestsBlocks.TRAPPED_GOLD_CHEST.get(),
      IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(),
      IronChestsBlocks.TRAPPED_COPPER_CHEST.get(),
      IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(),
      IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get()
    );

    tag(BlockTags.FEATURES_CANNOT_REPLACE).add(
      IronChestsBlocks.IRON_CHEST.get(),
      IronChestsBlocks.GOLD_CHEST.get(),
      IronChestsBlocks.DIAMOND_CHEST.get(),
      IronChestsBlocks.COPPER_CHEST.get(),
      IronChestsBlocks.CRYSTAL_CHEST.get(),
      IronChestsBlocks.OBSIDIAN_CHEST.get()
    );

    tag(BlockTags.GUARDED_BY_PIGLINS).add(
      IronChestsBlocks.IRON_CHEST.get(),
      IronChestsBlocks.GOLD_CHEST.get(),
      IronChestsBlocks.DIAMOND_CHEST.get(),
      IronChestsBlocks.COPPER_CHEST.get(),
      IronChestsBlocks.CRYSTAL_CHEST.get(),
      IronChestsBlocks.OBSIDIAN_CHEST.get(),
      IronChestsBlocks.TRAPPED_IRON_CHEST.get(),
      IronChestsBlocks.TRAPPED_GOLD_CHEST.get(),
      IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(),
      IronChestsBlocks.TRAPPED_COPPER_CHEST.get(),
      IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(),
      IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get()
    );
  }
}
