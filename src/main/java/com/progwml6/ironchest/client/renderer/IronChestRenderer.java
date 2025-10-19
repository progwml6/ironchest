package com.progwml6.ironchest.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.progwml6.ironchest.client.IronChestsClientRegistration;
import com.progwml6.ironchest.client.model.IronChestModel;
import com.progwml6.ironchest.client.model.ModelItem;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import com.progwml6.ironchest.common.block.entity.ICrystalChest;
import com.progwml6.ironchest.common.block.regular.AbstractIronChestBlock;
import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import com.progwml6.ironchest.common.block.trapped.entity.AbstractTrappedIronChestBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;

public class IronChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {

  private final IronChestModel model;
  private final BlockEntityRenderDispatcher renderer;

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
    this.renderer = context.getBlockEntityRenderDispatcher();
    this.model = new IronChestModel(context.bakeLayer(IronChestsClientRegistration.IRON_CHEST));
  }

  @Override
  public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 p_401038_) {
    AbstractIronChestBlockEntity chestBlockEntity = (AbstractIronChestBlockEntity) blockEntity;

    Level level = chestBlockEntity.getLevel();
    boolean useTileEntityBlockState = level != null;

    BlockState blockState = useTileEntityBlockState ? chestBlockEntity.getBlockState() : chestBlockEntity.getBlockToUse().defaultBlockState().setValue(AbstractIronChestBlock.FACING, Direction.SOUTH);
    Block block = blockState.getBlock();
    IronChestsTypes chestType = IronChestsTypes.IRON;
    IronChestsTypes actualType = AbstractIronChestBlock.getTypeFromBlock(block);

    if (actualType != null) {
      chestType = actualType;
    }

    if (block instanceof AbstractIronChestBlock) {
      poseStack.pushPose();

      float f = blockState.getValue(AbstractIronChestBlock.FACING).toYRot();

      poseStack.translate(0.5D, 0.5D, 0.5D);
      poseStack.mulPose(Axis.YP.rotationDegrees(-f));
      poseStack.translate(-0.5D, -0.5D, -0.5D);

      float openness = chestBlockEntity.getOpenNess(partialTick);
      openness = 1.0F - openness;
      openness = 1.0F - openness * openness * openness;

      boolean trapped = blockEntity instanceof AbstractTrappedIronChestBlockEntity;

      Material material = IronChestsModels.chooseChestMaterial(chestType, trapped);
      VertexConsumer vertexConsumer = material.buffer(bufferSource, RenderType::entityCutout);
      this.render(poseStack, vertexConsumer, this.model, openness, packedLight, packedOverlay);

      poseStack.popPose();

      if (chestType.isTransparent() && chestBlockEntity instanceof ICrystalChest crystalChest && Vec3.atCenterOf(blockEntity.getBlockPos()).closerThan(this.renderer.camera.getPosition(), 128d)) {
        float rotation = (float) (360D * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL) - partialTick;

        for (int j = 0; j < MODEL_ITEMS.size() - 1; j++) {
          renderItem(poseStack, bufferSource, crystalChest.getTopItems().get(j), MODEL_ITEMS.get(j), rotation, packedLight);
        }
      }
    }
  }

  private void render(PoseStack poseStack, VertexConsumer buffer, IronChestModel model, float openness, int packedLight, int packedOverlay) {
    model.setupAnim(openness);
    model.renderToBuffer(poseStack, buffer, packedLight, packedOverlay);
  }

  /**
   * Renders a single item in a TESR
   *
   * @param matrices  Matrix stack instance
   * @param buffer    Buffer instance
   * @param item      Item to render
   * @param modelItem Model items for render information
   * @param light     Model light
   */
  public static void renderItem(PoseStack matrices, MultiBufferSource buffer, ItemStack item, ModelItem modelItem, float rotation, int light) {
    // if no stack, skip
    if (item.isEmpty()) return;

    // start rendering
    matrices.pushPose();
    Vector3f center = modelItem.getCenter();
    matrices.translate(center.x(), center.y(), center.z());

    matrices.mulPose(Axis.YP.rotationDegrees(rotation));

    // scale
    float scale = modelItem.getSizeScaled();
    matrices.scale(scale, scale, scale);

    // render the actual item
    Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, matrices, buffer, null, 0);

    matrices.popPose();
  }

  @Override
  public AABB getRenderBoundingBox(T blockEntity) {
    BlockPos pos = blockEntity.getBlockPos();
    return AABB.encapsulatingFullBlocks(pos.offset(-1, 0, -1), pos.offset(1, 1, 1));
  }
}
