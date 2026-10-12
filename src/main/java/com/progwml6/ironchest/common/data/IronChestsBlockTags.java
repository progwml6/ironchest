package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class IronChestsBlockTags extends BlockTagsProvider {

  public IronChestsBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
    super(output, lookup, IronChests.MODID);
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

  protected record Appender(TagAppender<Block> app) implements TagAppender<Block> {
    @Override
    public IronChestsBlockTags.Appender add(ResourceKey<Block> element) {
      app.add(element);
      return this;
    }

    @Override
    public IronChestsBlockTags.Appender addOptional(ResourceKey<Block> element) {
      app.addOptional(element);
      return this;
    }

    @Override
    public IronChestsBlockTags.Appender addTag(TagKey<Block> tag) {
      app.addTag(tag);
      return this;
    }

    @Override
    public IronChestsBlockTags.Appender addOptionalTag(TagKey<Block> tag) {
      app.addOptionalTag(tag);
      return this;
    }

    @Override
    public IronChestsBlockTags.Appender add(TagEntry entry) {
      app.add(entry);
      return this;
    }

    @Override
    public IronChestsBlockTags.Appender replace(boolean value) {
      app.replace(value);
      return this;
    }

    @Override
    public IronChestsBlockTags.Appender remove(ResourceKey<Block> element) {
      app.remove(element);
      return this;
    }

    @Override
    public IronChestsBlockTags.Appender remove(TagKey<Block> tag) {
      app.remove(tag);
      return this;
    }

    public IronChestsBlockTags.Appender add(Block... blocks) {
      for (Block block : blocks) {
        add(BuiltInRegistries.BLOCK.wrapAsHolder(block).getKey());
      }
      return this;
    }

    public IronChestsBlockTags.Appender addAll(Iterable<Block> blocks) {
      for (Block block : blocks) {
        add(block);
      }
      return this;
    }
  }

  @Override
  protected IronChestsBlockTags.Appender tag(TagKey<Block> tag) {
    return new IronChestsBlockTags.Appender(super.tag(tag));
  }
}
