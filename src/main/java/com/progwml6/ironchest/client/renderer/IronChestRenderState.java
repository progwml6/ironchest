package com.progwml6.ironchest.client.renderer;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.Collections;
import java.util.List;

public class IronChestRenderState extends BlockEntityRenderState {
  public IronChestsTypes chestType = IronChestsTypes.IRON;
  public boolean trapped;
  public float open;
  public float angle;
  public List<ItemStackRenderState> items = Collections.emptyList();
  public float itemRotation;
}
