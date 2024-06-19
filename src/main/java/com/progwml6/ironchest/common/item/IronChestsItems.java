package com.progwml6.ironchest.common.item;

import com.google.common.collect.ImmutableMap;
import com.progwml6.ironchest.IronChests;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IronChestsItems {

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IronChests.MODID);

  public static final ImmutableMap<IronChestsUpgradeType, DeferredItem<ChestUpgradeItem>> UPGRADES = ImmutableMap.copyOf(Arrays.stream(IronChestsUpgradeType.values())
    .collect(Collectors.toMap(Function.identity(), type -> register(type.name().toLowerCase(Locale.ROOT) + "_chest_upgrade",
      () -> new ChestUpgradeItem(type, new Item.Properties().stacksTo(1))))));

  private static <T extends Item> DeferredItem<T> register(final String name, final Supplier<T> sup) {
    return ITEMS.register(name, sup);
  }
}
