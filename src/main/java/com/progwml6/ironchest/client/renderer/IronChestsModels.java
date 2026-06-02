package com.progwml6.ironchest.client.renderer;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class IronChestsModels {

  public static final SpriteId IRON_CHEST_LOCATION = chestSpriteId(false, "model/iron_chest");
  public static final SpriteId GOLD_CHEST_LOCATION = chestSpriteId(false, "model/gold_chest");
  public static final SpriteId DIAMOND_CHEST_LOCATION = chestSpriteId(false, "model/diamond_chest");
  public static final SpriteId COPPER_CHEST_LOCATION = chestSpriteId(false, "model/copper_chest");
  public static final SpriteId CRYSTAL_CHEST_LOCATION = chestSpriteId(false, "model/crystal_chest");
  public static final SpriteId OBSIDIAN_CHEST_LOCATION = chestSpriteId(false, "model/obsidian_chest");
  public static final SpriteId DIRT_CHEST_LOCATION = chestSpriteId(false, "model/dirt_chest");
  public static final SpriteId VANILLA_CHEST_LOCATION = chestSpriteId(true, "normal");

  public static final SpriteId TRAPPED_IRON_CHEST_LOCATION = chestSpriteId(false, "model/trapped_iron_chest");
  public static final SpriteId TRAPPED_GOLD_CHEST_LOCATION = chestSpriteId(false, "model/trapped_gold_chest");
  public static final SpriteId TRAPPED_DIAMOND_CHEST_LOCATION = chestSpriteId(false, "model/trapped_diamond_chest");
  public static final SpriteId TRAPPED_COPPER_CHEST_LOCATION = chestSpriteId(false, "model/trapped_copper_chest");
  public static final SpriteId TRAPPED_CRYSTAL_CHEST_LOCATION = chestSpriteId(false, "model/trapped_crystal_chest");
  public static final SpriteId TRAPPED_OBSIDIAN_CHEST_LOCATION = chestSpriteId(false, "model/trapped_obsidian_chest");
  public static final SpriteId TRAPPED_DIRT_CHEST_LOCATION = chestSpriteId(false, "model/trapped_dirt_chest");
  public static final SpriteId TRAPPED_VANILLA_CHEST_LOCATION = chestSpriteId(true, "trapped");

  public static SpriteId chooseChestSpriteId(IronChestsTypes type, boolean trapped) {
    if (trapped) {
      return getSpriteId(type, TRAPPED_IRON_CHEST_LOCATION, TRAPPED_GOLD_CHEST_LOCATION, TRAPPED_DIAMOND_CHEST_LOCATION, TRAPPED_COPPER_CHEST_LOCATION, TRAPPED_CRYSTAL_CHEST_LOCATION, TRAPPED_OBSIDIAN_CHEST_LOCATION, TRAPPED_DIRT_CHEST_LOCATION, TRAPPED_VANILLA_CHEST_LOCATION);
    } else {
      return getSpriteId(type, IRON_CHEST_LOCATION, GOLD_CHEST_LOCATION, DIAMOND_CHEST_LOCATION, COPPER_CHEST_LOCATION, CRYSTAL_CHEST_LOCATION, OBSIDIAN_CHEST_LOCATION, DIRT_CHEST_LOCATION, VANILLA_CHEST_LOCATION);
    }
  }

  @NotNull
  private static SpriteId getSpriteId(IronChestsTypes type, SpriteId ironChestSpriteId, SpriteId goldChestSpriteId, SpriteId diamondChestSpriteId, SpriteId copperChestSpriteId, SpriteId crystalChestSpriteId, SpriteId obsidianChestSpriteId, SpriteId dirtChestSpriteId, SpriteId vanillaChestSpriteId) {
    return switch (type) {
      case IRON -> ironChestSpriteId;
      case GOLD -> goldChestSpriteId;
      case DIAMOND -> diamondChestSpriteId;
      case COPPER -> copperChestSpriteId;
      case CRYSTAL -> crystalChestSpriteId;
      case OBSIDIAN -> obsidianChestSpriteId;
      case DIRT -> dirtChestSpriteId;
      default -> vanillaChestSpriteId;
    };
  }

  private static SpriteId chestSpriteId(boolean vanillaChest, String chestName) {
    if (vanillaChest) {
      return new SpriteId(Sheets.CHEST_SHEET, Identifier.withDefaultNamespace("entity/chest/" + chestName));
    } else {
      return new SpriteId(Sheets.CHEST_SHEET, IronChests.prefix(chestName));
    }
  }
}
