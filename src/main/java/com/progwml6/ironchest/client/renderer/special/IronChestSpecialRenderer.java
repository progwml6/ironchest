package com.progwml6.ironchest.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.client.IronChestsClientRegistration;
import com.progwml6.ironchest.client.model.IronChestModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;

import java.util.Set;

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

  private final MaterialSet materials;
  private final IronChestModel model;
  private final Material material;
  private final float openness;

  public IronChestSpecialRenderer(MaterialSet materials, IronChestModel model, Material material, float openness) {
    this.materials = materials;
    this.model = model;
    this.material = material;
    this.openness = openness;
  }

  @Override
  public void submit(
    ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, boolean hasFoil, int outlineColor
  ) {
    nodeCollector.submitModel(
      this.model,
      this.openness,
      poseStack,
      this.material.renderType(RenderType::entityCutout),
      packedLight,
      packedOverlay,
      -1,
      this.materials.get(this.material),
      outlineColor,
      null
    );
  }

  @Override
  public void getExtents(Set<Vector3f> output) {
    PoseStack posestack = new PoseStack();
    this.model.setupAnim(this.openness);
    this.model.root().getExtentsForGui(posestack, output);
  }

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
    public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext context) {
      IronChestModel chestModel = new IronChestModel(context.entityModelSet().bakeLayer(IronChestsClientRegistration.IRON_CHEST));
      Material material = new Material(Sheets.CHEST_SHEET, texture);
      return new IronChestSpecialRenderer(context.materials(), chestModel, material, this.openness);
    }
  }
}
