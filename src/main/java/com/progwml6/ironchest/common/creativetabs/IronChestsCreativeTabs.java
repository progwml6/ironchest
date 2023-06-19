package com.progwml6.ironchest.common.creativetabs;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.item.IronChestsItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class IronChestsCreativeTabs {

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IronChests.MOD_ID);

  public static final RegistryObject<CreativeModeTab> IRON_CHEST_TAP = CREATIVE_MODE_TABS.register("ironchest", () -> CreativeModeTab.builder()
    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
    .title(Component.translatable("itemGroup.ironchest"))
    .icon(() -> new ItemStack(IronChestsBlocks.IRON_CHEST.get()))
    .displayItems((parameters, output) -> {
      for (final RegistryObject<Item> item : IronChestsItems.ITEMS.getEntries())
        output.accept(item.get());
    }).build());
}
