package com.progwml6.ironchest.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.progwml6.ironchest.client.IronChestsClientRegistration;
import com.progwml6.ironchest.client.model.IronChestModel;
import com.progwml6.ironchest.client.model.ModelItem;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.ICrystalChest;
import com.progwml6.ironchest.common.block.regular.AbstractIronChestBlock;
import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.AbstractTrappedIronChestBlockEntity;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IronChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T, IronChestRenderState> {

  private final MaterialSet materials;
  private final IronChestModel model;
  private final ItemModelResolver itemModelResolver;

  private static final List<ModelItem> MODEL_ITEMS = Arrays.asList(
    new ModelItem(new Vector3f(0.3F, 0.45F, 0.3F), 3.0F),
    new ModelItem(new Vector3f(0.7F, 0.45F, 0.3F), 3.0F),
    new ModelItem(new Vector3f(0.3F, 0.45F, 0.7F), 3.0F),
    new ModelItem(new Vector3f(0.7F, 0.45F, 0.7F), 3.0F),
    new ModelItem(new Vector3f(0.3F, 0.1F, 0.3F), 3.0F),
    new ModelItem(new Vector3f(0.7F, 0.1F, 0.3F), 3.0F),
    new ModelItem(new Vector3f(0.3F, 0.1F, 0.7F), 3.0F),
    new ModelItem(new Vector3f(0.7F, 0.1F, 0.7F), 3.0F),
    new ModelItem(new Vector3f(0.5F, 0.32F, 0.5F), 3.0F)
  );

  public IronChestRenderer(BlockEntityRendererProvider.Context context) {
    this.materials = context.materials();
    this.itemModelResolver = context.itemModelResolver();
    this.model = new IronChestModel(context.bakeLayer(IronChestsClientRegistration.IRON_CHEST));
  }

  @Override
  public IronChestRenderState createRenderState() {
    return new IronChestRenderState();
  }

  @Override
  public void extractRenderState(T blockEntity, IronChestRenderState renderState, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
    BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

    AbstractIronChestBlockEntity chestBlockEntity = (AbstractIronChestBlockEntity) blockEntity;
    boolean useBlockState = blockEntity.getLevel() != null;

    BlockState blockState = useBlockState ? chestBlockEntity.getBlockState() : chestBlockEntity.getBlockToUse().defaultBlockState().setValue(AbstractIronChestBlock.FACING, Direction.SOUTH);
    Block block = blockState.getBlock();
    IronChestsTypes chestType = IronChestsTypes.IRON;
    IronChestsTypes actualType = AbstractIronChestBlock.getTypeFromBlock(block);

    if (actualType != null) {
      chestType = actualType;
    }

    renderState.chestType = chestType;
    renderState.open = chestBlockEntity.getOpenNess(partialTick);
    renderState.angle = blockState.getValue(AbstractIronChestBlock.FACING).toYRot();
    renderState.trapped = blockEntity instanceof AbstractTrappedIronChestBlockEntity;
    renderState.items = new ArrayList<>();
    renderState.itemRotation = (float) (360D * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) - partialTick;

    // Construct the top item list
    int i = (int)blockEntity.getBlockPos().asLong();
    if (chestType.isTransparent() && chestBlockEntity instanceof ICrystalChest crystalChest) {
      for (int j = 0; j <  MODEL_ITEMS.size() - 1; j++) {
        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemStackRenderState, crystalChest.getTopItems().get(j), ItemDisplayContext.NONE, blockEntity.getLevel(), null, i + j);
        renderState.items.add(itemStackRenderState);
      }
    }
  }

  @Override
  public void submit(IronChestRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
    poseStack.pushPose();
    poseStack.translate(0.5F, 0.5F, 0.5F);
    poseStack.mulPose(Axis.YP.rotationDegrees(-renderState.angle));
    poseStack.translate(-0.5F, -0.5F, -0.5F);
    float f = renderState.open;
    f = 1.0F - f;
    f = 1.0F - f * f * f;

    Material material = IronChestsModels.chooseChestMaterial(renderState.chestType, renderState.trapped);
    RenderType renderType = material.renderType(RenderTypes::entityCutout);
    TextureAtlasSprite textureatlassprite = this.materials.get(material);

    nodeCollector.submitModel(
      this.model,
      f,
      poseStack,
      renderType,
      renderState.lightCoords,
      OverlayTexture.NO_OVERLAY,
      -1,
      textureatlassprite,
      0,
      renderState.breakProgress
    );

    poseStack.popPose();

    if (renderState.chestType.isTransparent() && Vec3.atCenterOf(renderState.blockPos).closerThan(cameraRenderState.pos, 128d)) {
      for (int i = 0; i < renderState.items.size(); i++) {
        ItemStackRenderState itemStackRenderState = renderState.items.get(i);
        if (!itemStackRenderState.isEmpty()) {
          ModelItem modelItem = MODEL_ITEMS.get(i);
          Vector3f center = modelItem.getCenter();
          float scale = modelItem.getSizeScaled();

          poseStack.pushPose();
          poseStack.translate(center.x(), center.y(), center.z());
          poseStack.mulPose(Axis.YP.rotationDegrees(renderState.itemRotation));
          poseStack.scale(scale, scale, scale);
          itemStackRenderState.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
          poseStack.popPose();
        }
      }
    }
  }

  @Override
  public AABB getRenderBoundingBox(T blockEntity) {
    BlockPos pos = blockEntity.getBlockPos();
    return AABB.encapsulatingFullBlocks(pos.offset(-1, 0, -1), pos.offset(1, 1, 1));
  }
}
