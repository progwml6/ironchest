package com.progwml6.ironchest.client.model;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsTypes;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class IronChestsModels {

  public static final ResourceLocation IRON_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/iron_chest");
  public static final ResourceLocation GOLD_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/gold_chest");
  public static final ResourceLocation DIAMOND_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/diamond_chest");
  public static final ResourceLocation COPPER_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/copper_chest");
  public static final ResourceLocation CRYSTAL_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/crystal_chest");
  public static final ResourceLocation OBSIDIAN_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/obsidian_chest");
  public static final ResourceLocation DIRT_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/dirt_chest");
  public static final ResourceLocation VANILLA_CHEST_LOCATION = ResourceLocation.withDefaultNamespace("entity/chest/normal");

  public static final ResourceLocation TRAPPED_IRON_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/trapped_iron_chest");
  public static final ResourceLocation TRAPPED_GOLD_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/trapped_gold_chest");
  public static final ResourceLocation TRAPPED_DIAMOND_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/trapped_diamond_chest");
  public static final ResourceLocation TRAPPED_COPPER_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/trapped_copper_chest");
  public static final ResourceLocation TRAPPED_CRYSTAL_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/trapped_crystal_chest");
  public static final ResourceLocation TRAPPED_OBSIDIAN_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/trapped_obsidian_chest");
  public static final ResourceLocation TRAPPED_DIRT_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IronChests.MODID, "model/trapped_dirt_chest");
  public static final ResourceLocation TRAPPED_VANILLA_CHEST_LOCATION = ResourceLocation.withDefaultNamespace("entity/chest/trapped");

  public static ResourceLocation chooseChestTexture(IronChestsTypes type, boolean trapped) {
    if (trapped) {
      return getResourceLocation(type, TRAPPED_IRON_CHEST_LOCATION, TRAPPED_GOLD_CHEST_LOCATION, TRAPPED_DIAMOND_CHEST_LOCATION, TRAPPED_COPPER_CHEST_LOCATION, TRAPPED_CRYSTAL_CHEST_LOCATION, TRAPPED_OBSIDIAN_CHEST_LOCATION, TRAPPED_DIRT_CHEST_LOCATION, TRAPPED_VANILLA_CHEST_LOCATION);
    } else {
      return getResourceLocation(type, IRON_CHEST_LOCATION, GOLD_CHEST_LOCATION, DIAMOND_CHEST_LOCATION, COPPER_CHEST_LOCATION, CRYSTAL_CHEST_LOCATION, OBSIDIAN_CHEST_LOCATION, DIRT_CHEST_LOCATION, VANILLA_CHEST_LOCATION);
    }
  }

  @NotNull
  private static ResourceLocation getResourceLocation(IronChestsTypes type, ResourceLocation ironChestLocation, ResourceLocation goldChestLocation, ResourceLocation diamondChestLocation, ResourceLocation copperChestLocation, ResourceLocation crystalChestLocation, ResourceLocation obsidianChestLocation, ResourceLocation dirtChestLocation, ResourceLocation vanillaChestLocation) {
    return switch (type) {
      case IRON -> ironChestLocation;
      case GOLD -> goldChestLocation;
      case DIAMOND -> diamondChestLocation;
      case COPPER -> copperChestLocation;
      case CRYSTAL -> crystalChestLocation;
      case OBSIDIAN -> obsidianChestLocation;
      case DIRT -> dirtChestLocation;
      default -> vanillaChestLocation;
    };
  }
}
