package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class IronChestsBlockTags extends BlockTagsProvider {
    public IronChestsBlockTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, IronChests.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
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
    }
}
