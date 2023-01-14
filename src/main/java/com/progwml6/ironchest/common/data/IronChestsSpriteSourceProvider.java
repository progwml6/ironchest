package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

public class IronChestsSpriteSourceProvider extends SpriteSourceProvider {

  public IronChestsSpriteSourceProvider(PackOutput output, ExistingFileHelper fileHelper) {
    super(output, fileHelper, IronChests.MOD_ID);
  }

  @Override
  protected void addSources() {
    atlas(CHESTS_ATLAS).addSource(new DirectoryLister("model", "model/"));
    atlas(BLOCKS_ATLAS).addSource(new DirectoryLister("model", "model/"));
  }
}
