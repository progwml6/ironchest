package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.concurrent.CompletableFuture;

public class IronChestsSpriteSourceProvider extends SpriteSourceProvider {

  public IronChestsSpriteSourceProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<Provider> lookupProvider) {
    super(output, lookupProvider, IronChests.MODID, fileHelper);
  }

  @Override
  protected void gather() {
    atlas(CHESTS_ATLAS).addSource(new DirectoryLister("model", "model/"));
  }
}
