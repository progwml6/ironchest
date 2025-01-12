package com.progwml6.ironchest.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.client.IronChestsClientRegistration;
import com.progwml6.ironchest.client.model.IronChestModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IronChestSpecialRenderer implements NoDataSpecialModelRenderer {

  public static final ResourceLocation IRON_CHEST_TEXTURE = IronChests.prefix("model/iron_chest");
  public static final ResourceLocation TRAPPED_IRON_CHEST_TEXTURE = IronChests.prefix("model/trapped_iron_chest");

  public static final ResourceLocation GOLD_CHEST_TEXTURE = IronChests.prefix("model/gold_chest");
  public static final ResourceLocation TRAPPED_GOLD_CHEST_TEXTURE = IronChests.prefix("model/trapped_gold_chest");

  public static final ResourceLocation DIAMOND_CHEST_TEXTURE = IronChests.prefix("model/diamond_chest");
  public static final ResourceLocation TRAPPED_DIAMOND_CHEST_TEXTURE = IronChests.prefix("model/trapped_diamond_chest");

  public static final ResourceLocation COPPER_CHEST_TEXTURE = IronChests.prefix("model/copper_chest");
  public static final ResourceLocation TRAPPED_COPPER_CHEST_TEXTURE = IronChests.prefix("model/trapped_copper_chest");

  public static final ResourceLocation CRYSTAL_CHEST_TEXTURE = IronChests.prefix("model/crystal_chest");
  public static final ResourceLocation TRAPPED_CRYSTAL_CHEST_TEXTURE = IronChests.prefix("model/trapped_crystal_chest");

  public static final ResourceLocation OBSIDIAN_CHEST_TEXTURE = IronChests.prefix("model/obsidian_chest");
  public static final ResourceLocation TRAPPED_OBSIDIAN_CHEST_TEXTURE = IronChests.prefix("model/trapped_obsidian_chest");

  public static final ResourceLocation DIRT_CHEST_TEXTURE = IronChests.prefix("model/dirt_chest");
  public static final ResourceLocation TRAPPED_DIRT_CHEST_TEXTURE = IronChests.prefix("model/trapped_dirt_chest");

  private final IronChestModel model;
  private final Material material;
  private final float openness;

  public IronChestSpecialRenderer(IronChestModel model, Material material, float openness) {
    this.model = model;
    this.material = material;
    this.openness = openness;
  }

  @Override
  public void render(ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType) {
    VertexConsumer vertexconsumer = this.material.buffer(bufferSource, RenderType::entityCutout);
    this.model.setupAnim(this.openness);
    this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay);
  }

  @OnlyIn(Dist.CLIENT)
  public record Unbaked(ResourceLocation texture, float openness) implements SpecialModelRenderer.Unbaked {

    public static final MapCodec<IronChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
      unbakedInstance -> unbakedInstance.group(
          ResourceLocation.CODEC.fieldOf("texture").forGetter(IronChestSpecialRenderer.Unbaked::texture),
          Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(IronChestSpecialRenderer.Unbaked::openness)
        )
        .apply(unbakedInstance, IronChestSpecialRenderer.Unbaked::new)
    );

    public Unbaked(ResourceLocation resourceLocation) {
      this(resourceLocation, 0.0F);
    }

    @Override
    public MapCodec<IronChestSpecialRenderer.Unbaked> type() {
      return MAP_CODEC;
    }

    @Override
    public SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
      IronChestModel chestModel = new IronChestModel(entityModelSet.bakeLayer(IronChestsClientRegistration.IRON_CHEST));
      Material material = new Material(Sheets.CHEST_SHEET, texture);
      return new IronChestSpecialRenderer(chestModel, material, this.openness);
    }
  }
}
