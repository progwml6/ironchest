package com.progwml6.ironchest.client.screen;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.inventory.IronChestMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class IronChestScreen extends AbstractContainerScreen<IronChestMenu> implements MenuAccess<IronChestMenu> {

  private final IronChestsTypes chestType;

  private final int textureXSize;

  private final int textureYSize;

  public IronChestScreen(IronChestMenu ironChestMenu, Inventory playerInventory, Component title) {
    super(ironChestMenu, playerInventory, title, ironChestMenu.getChestType().xSize, ironChestMenu.getChestType().ySize);

    this.chestType = ironChestMenu.getChestType();
    this.textureXSize = ironChestMenu.getChestType().textureXSize;
    this.textureYSize = ironChestMenu.getChestType().textureYSize;

    this.titleLabelX = 8;
    this.titleLabelY = 6;
    this.inventoryLabelX = 8;
    this.inventoryLabelY = this.imageHeight - 96 + 2;
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    this.extractBackground(graphics, mouseX, mouseY, a);
    super.extractRenderState(graphics, mouseX, mouseY, a);
    this.extractTooltip(graphics, mouseX, mouseY);
  }

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    super.extractBackground(graphics, mouseX, mouseY, a);
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;

    graphics.blit(RenderPipelines.GUI_TEXTURED, this.chestType.guiTexture, x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
  }
}
