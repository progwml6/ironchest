package com.progwml6.ironchest.common.item;

import com.progwml6.ironchest.client.model.inventory.IronChestItemStackRenderer;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class IronChestBlockItem extends BlockItem {

  protected IronChestsTypes type;

  protected Boolean trapped;

  public IronChestBlockItem(Block block, Properties properties, IronChestsTypes type, Boolean trapped) {
    super(block, properties);

    this.type = type;
    this.trapped = trapped;
  }

  public IronChestsTypes getType() {
    return this.type;
  }

  public Boolean getTrapped() {
    return this.trapped;
  }

  public static final class IronChestRender implements IClientItemExtensions {

    public static final IronChestRender INSTANCE = new IronChestRender();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
      return new IronChestItemStackRenderer();
    }
  }
}
