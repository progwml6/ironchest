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
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import org.joml.Vector3fc;

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

  private final SpriteGetter sprites;
  private final IronChestModel model;
  private final SpriteId sprite;
  private final float openness;

  public IronChestSpecialRenderer(SpriteGetter sprites, IronChestModel model, SpriteId sprite, float openness) {
    this.sprites = sprites;
    this.model = model;
    this.sprite = sprite;
    this.openness = openness;
  }

  @Override
  public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
    submitNodeCollector.submitModel(this.model, this.openness, poseStack, lightCoords, overlayCoords, -1, this.sprite, this.sprites, outlineColor, null);
  }

  @Override
  public void getExtents(Consumer<Vector3fc> output) {
    PoseStack poseStack = new PoseStack();
    this.model.setupAnim(this.openness);
    this.model.root().getExtentsForGui(poseStack, output);
  }

  public record Unbaked(Identifier texture, float openness) implements NoDataSpecialModelRenderer.Unbaked {
    public static final MapCodec<IronChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
      i -> i.group(
          Identifier.CODEC.fieldOf("texture").forGetter(IronChestSpecialRenderer.Unbaked::texture),
          Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(IronChestSpecialRenderer.Unbaked::openness)
        )
        .apply(i, IronChestSpecialRenderer.Unbaked::new)
    );

    public Unbaked(Identifier texture) {
      this(texture, 0.0F);
    }

    @Override
    public MapCodec<IronChestSpecialRenderer.Unbaked> type() {
      return MAP_CODEC;
    }

    public IronChestSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
      IronChestModel model = new IronChestModel(context.entityModelSet().bakeLayer(IronChestsClientRegistration.IRON_CHEST));
      SpriteId fullTexture = new SpriteId(Sheets.CHEST_SHEET, this.texture);
      return new IronChestSpecialRenderer(context.sprites(), model, fullTexture, this.openness);
    }
  }
}
