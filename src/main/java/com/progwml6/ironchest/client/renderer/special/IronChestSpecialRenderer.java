package com.progwml6.ironchest.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.client.IronChestsClientRegistration;
import com.progwml6.ironchest.client.model.IronChestModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.Set;
import java.util.function.Consumer;

public class IronChestSpecialRenderer implements NoDataSpecialModelRenderer {

  public static final Identifier IRON_CHEST_TEXTURE = IronChests.prefix("model/iron_chest");
  public static final Identifier TRAPPED_IRON_CHEST_TEXTURE = IronChests.prefix("model/trapped_iron_chest");

  public static final Identifier GOLD_CHEST_TEXTURE = IronChests.prefix("model/gold_chest");
  public static final Identifier TRAPPED_GOLD_CHEST_TEXTURE = IronChests.prefix("model/trapped_gold_chest");

  public static final Identifier DIAMOND_CHEST_TEXTURE = IronChests.prefix("model/diamond_chest");
  public static final Identifier TRAPPED_DIAMOND_CHEST_TEXTURE = IronChests.prefix("model/trapped_diamond_chest");

  public static final Identifier COPPER_CHEST_TEXTURE = IronChests.prefix("model/copper_chest");
  public static final Identifier TRAPPED_COPPER_CHEST_TEXTURE = IronChests.prefix("model/trapped_copper_chest");

  public static final Identifier CRYSTAL_CHEST_TEXTURE = IronChests.prefix("model/crystal_chest");
  public static final Identifier TRAPPED_CRYSTAL_CHEST_TEXTURE = IronChests.prefix("model/trapped_crystal_chest");

  public static final Identifier OBSIDIAN_CHEST_TEXTURE = IronChests.prefix("model/obsidian_chest");
  public static final Identifier TRAPPED_OBSIDIAN_CHEST_TEXTURE = IronChests.prefix("model/trapped_obsidian_chest");

  public static final Identifier DIRT_CHEST_TEXTURE = IronChests.prefix("model/dirt_chest");
  public static final Identifier TRAPPED_DIRT_CHEST_TEXTURE = IronChests.prefix("model/trapped_dirt_chest");

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
      this.material.renderType(RenderTypes::entityCutout),
      packedLight,
      packedOverlay,
      -1,
      this.materials.get(this.material),
      outlineColor,
      null
    );
  }

  @Override
  public void getExtents(Consumer<Vector3fc> output) {
    PoseStack posestack = new PoseStack();
    this.model.setupAnim(this.openness);
    this.model.root().getExtentsForGui(posestack, output);
  }

  public record Unbaked(Identifier texture, float openness) implements SpecialModelRenderer.Unbaked {

    public static final MapCodec<IronChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
      unbakedInstance -> unbakedInstance.group(
          Identifier.CODEC.fieldOf("texture").forGetter(IronChestSpecialRenderer.Unbaked::texture),
          Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(IronChestSpecialRenderer.Unbaked::openness)
        )
        .apply(unbakedInstance, IronChestSpecialRenderer.Unbaked::new)
    );

    public Unbaked(Identifier Identifier) {
      this(Identifier, 0.0F);
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
