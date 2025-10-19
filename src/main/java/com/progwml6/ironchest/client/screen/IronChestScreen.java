package com.progwml6.ironchest.client.screen;

import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.inventory.IronChestMenu;
import net.minecraft.client.gui.GuiGraphics;
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
    super(ironChestMenu, playerInventory, title);

    this.chestType = ironChestMenu.getChestType();
    this.imageWidth = ironChestMenu.getChestType().xSize;
    this.imageHeight = ironChestMenu.getChestType().ySize;
    this.textureXSize = ironChestMenu.getChestType().textureXSize;
    this.textureYSize = ironChestMenu.getChestType().textureYSize;
  }

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
    super.render(guiGraphics, mouseX, mouseY, partialTicks);
    this.renderTooltip(guiGraphics, mouseX, mouseY);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    guiGraphics.drawString(this.font, this.title, 8, 6, -12566464, false);

    guiGraphics.drawString(this.font, this.playerInventoryTitle, 8, (this.imageHeight - 96 + 2), -12566464, false);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;

    guiGraphics.blit(RenderPipelines.GUI_TEXTURED, this.chestType.guiTexture, x, y, 0, 0, this.imageWidth, this.imageHeight, this.textureXSize, this.textureYSize);
  }
}
