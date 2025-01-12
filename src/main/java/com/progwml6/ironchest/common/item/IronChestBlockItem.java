package com.progwml6.ironchest.common.item;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

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
}
