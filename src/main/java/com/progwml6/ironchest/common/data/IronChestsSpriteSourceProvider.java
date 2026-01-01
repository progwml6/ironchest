package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.client.renderer.IronChestsModels;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.SpriteSourceProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.AtlasIds.CHESTS;

public class IronChestsSpriteSourceProvider extends SpriteSourceProvider {

  public IronChestsSpriteSourceProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
    super(output, lookupProvider, IronChests.MODID);
  }

  @Override
  protected void gather() {
    for (IronChestsTypes type : IronChestsTypes.values()) {
      if (type == IronChestsTypes.WOOD) {
        continue;
      }
      atlas(CHESTS).addSource(new SingleFile(IronChestsModels.chooseChestMaterial(type, false).texture(), Optional.empty()));
      atlas(CHESTS).addSource(new SingleFile(IronChestsModels.chooseChestMaterial(type, true).texture(), Optional.empty()));
    }
  }
}
