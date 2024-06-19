package com.progwml6.ironchest.common.inventory;

import com.progwml6.ironchest.IronChests;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IronChestsMenuTypes {

  public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, IronChests.MODID);

  public static final DeferredHolder<MenuType<?>, MenuType<IronChestMenu>> IRON_CHEST = CONTAINERS.register("iron_chest", () -> new MenuType<>(IronChestMenu::createIronContainer, FeatureFlags.REGISTRY.allFlags()));

  public static final DeferredHolder<MenuType<?>, MenuType<IronChestMenu>> GOLD_CHEST = CONTAINERS.register("gold_chest", () -> new MenuType<>(IronChestMenu::createGoldContainer, FeatureFlags.REGISTRY.allFlags()));

  public static final DeferredHolder<MenuType<?>, MenuType<IronChestMenu>> DIAMOND_CHEST = CONTAINERS.register("diamond_chest", () -> new MenuType<>(IronChestMenu::createDiamondContainer, FeatureFlags.REGISTRY.allFlags()));

  public static final DeferredHolder<MenuType<?>, MenuType<IronChestMenu>> CRYSTAL_CHEST = CONTAINERS.register("crystal_chest", () -> new MenuType<>(IronChestMenu::createCrystalContainer, FeatureFlags.REGISTRY.allFlags()));

  public static final DeferredHolder<MenuType<?>, MenuType<IronChestMenu>> COPPER_CHEST = CONTAINERS.register("copper_chest", () -> new MenuType<>(IronChestMenu::createCopperContainer, FeatureFlags.REGISTRY.allFlags()));

  public static final DeferredHolder<MenuType<?>, MenuType<IronChestMenu>> OBSIDIAN_CHEST = CONTAINERS.register("obsidian_chest", () -> new MenuType<>(IronChestMenu::createObsidianContainer, FeatureFlags.REGISTRY.allFlags()));

  public static final DeferredHolder<MenuType<?>, MenuType<IronChestMenu>> DIRT_CHEST = CONTAINERS.register("dirt_chest", () -> new MenuType<>(IronChestMenu::createDirtContainer, FeatureFlags.REGISTRY.allFlags()));
}
