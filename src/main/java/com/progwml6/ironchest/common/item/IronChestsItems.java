package com.progwml6.ironchest.common.item;

import com.progwml6.ironchest.IronChests;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class IronChestsItems {

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronChests.MODID);

  public static final DeferredItem<ChestUpgradeItem> IRON_TO_GOLD_CHEST_UPGRADE = register("iron_to_gold_chest_upgrade", properties -> new ChestUpgradeItem(IronChestsUpgradeType.IRON_TO_GOLD, properties), () -> new Item.Properties().stacksTo(1));
  public static final DeferredItem<ChestUpgradeItem> GOLD_TO_DIAMOND_CHEST_UPGRADE = register("gold_to_diamond_chest_upgrade", properties -> new ChestUpgradeItem(IronChestsUpgradeType.GOLD_TO_DIAMOND, properties), () -> new Item.Properties().stacksTo(1));
  public static final DeferredItem<ChestUpgradeItem> COPPER_TO_IRON_CHEST_UPGRADE = register("copper_to_iron_chest_upgrade", properties -> new ChestUpgradeItem(IronChestsUpgradeType.COPPER_TO_IRON, properties), () -> new Item.Properties().stacksTo(1));
  public static final DeferredItem<ChestUpgradeItem> DIAMOND_TO_CRYSTAL_CHEST_UPGRADE = register("diamond_to_crystal_chest_upgrade", properties -> new ChestUpgradeItem(IronChestsUpgradeType.DIAMOND_TO_CRYSTAL, properties), () -> new Item.Properties().stacksTo(1));
  public static final DeferredItem<ChestUpgradeItem> WOOD_TO_IRON_CHEST_UPGRADE = register("wood_to_iron_chest_upgrade", properties -> new ChestUpgradeItem(IronChestsUpgradeType.WOOD_TO_IRON, properties), () -> new Item.Properties().stacksTo(1));
  public static final DeferredItem<ChestUpgradeItem> WOOD_TO_COPPER_CHEST_UPGRADE = register("wood_to_copper_chest_upgrade", properties -> new ChestUpgradeItem(IronChestsUpgradeType.WOOD_TO_COPPER, properties), () -> new Item.Properties().stacksTo(1));
  public static final DeferredItem<ChestUpgradeItem> DIAMOND_TO_OBSIDIAN_CHEST_UPGRADE = register("diamond_to_obsidian_chest_upgrade", properties -> new ChestUpgradeItem(IronChestsUpgradeType.DIAMOND_TO_OBSIDIAN, properties), () -> new Item.Properties().stacksTo(1));

  public static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item, Supplier<Item.Properties> properties) {
    return ITEMS.register(name, () -> item.apply(properties.get().setId(ResourceKey.create(Registries.ITEM, IronChests.prefix(name)))));
  }

  public static <T extends Item> void register(String name, Function<Item.Properties, T> item, Supplier<Item.Properties> properties, ResourceKey<Block> blockResourceKey) {
    ITEMS.register(name, () -> item.apply(properties.get().setId(ResourceKey.create(Registries.ITEM, blockResourceKey.location())).useBlockDescriptionPrefix()));
  }
}
