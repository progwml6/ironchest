package com.progwml6.ironchest;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.regular.entity.AbstractIronChestBlockEntity;
import com.progwml6.ironchest.common.creativetabs.IronChestsCreativeTabs;
import com.progwml6.ironchest.common.data.IronChestsBlockTags;
import com.progwml6.ironchest.common.data.IronChestsLanguageProvider;
import com.progwml6.ironchest.common.data.IronChestsModelProvider;
import com.progwml6.ironchest.common.data.IronChestsRecipeProvider;
import com.progwml6.ironchest.common.data.IronChestsSpriteSourceProvider;
import com.progwml6.ironchest.common.data.loot.IronChestsLootTableProvider;
import com.progwml6.ironchest.common.datacomponents.IronChestsDataComponents;
import com.progwml6.ironchest.common.inventory.IronChestsMenuTypes;
import com.progwml6.ironchest.common.item.IronChestsItems;
import com.progwml6.ironchest.common.network.TopStacksSyncPacket;
import net.minecraft.DetectedVersion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.internal.NeoForgeAdvancementProvider;
import net.neoforged.neoforge.common.data.internal.NeoForgeLootDataProvider;
import net.neoforged.neoforge.common.data.internal.NeoForgeRecipeProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mod(IronChests.MODID)
public class IronChests {

  public static final String MODID = "ironchest";

  public IronChests(IEventBus modEventBus) {
    // General mod setup
    modEventBus.addListener(this::onGatherRegistries);
    modEventBus.addListener(this::gatherData);
    modEventBus.addListener(this::setupPackets);
    modEventBus.addListener(this::registerCapabilities);

    // Registry objects
    IronChestsBlocks.BLOCKS.register(modEventBus);
    IronChestsItems.ITEMS.register(modEventBus);
    IronChestsBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
    IronChestsMenuTypes.CONTAINERS.register(modEventBus);
    IronChestsCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
    IronChestsDataComponents.COMPONENTS.register(modEventBus);
  }

  public void onGatherRegistries(GatherDataRegistryEntriesEvent event) {
    event.gatherFor(IronChests.MODID)
      .recipe(IronChestsRecipeProvider::new)
      .add(Registries.LOOT_TABLE, IronChestsLootTableProvider.create());
  }

  public void gatherData(GatherDataEvent.Client event) {
    event.createProvider(output -> new PackMetadataGenerator(output)
      .add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(
        Component.translatable("pack.ironchests.description"),
        new InclusiveRange<>(DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA)))));

    event.createProvider(IronChestsBlockTags::new);
    event.createProvider(IronChestsSpriteSourceProvider::new);
    event.createProvider(IronChestsLanguageProvider::new);
    event.createProvider(IronChestsModelProvider::new);
  }

  public void setupPackets(RegisterPayloadHandlersEvent event) {
    PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

    registrar.playBidirectional(TopStacksSyncPacket.TYPE, TopStacksSyncPacket.STREAM_CODEC, TopStacksSyncPacket::handleServer);
  }

  public void registerCapabilities(RegisterCapabilitiesEvent event) {
    event.registerBlock(Capabilities.Item.BLOCK, (level, pos, state, blockEntity, side) -> level.getBlockEntity(pos) instanceof AbstractIronChestBlockEntity ironChestBlockEntity ? VanillaContainerWrapper.of(ironChestBlockEntity) : null,
      IronChestsBlocks.IRON_CHEST.get(), IronChestsBlocks.TRAPPED_IRON_CHEST.get(),
      IronChestsBlocks.GOLD_CHEST.get(), IronChestsBlocks.TRAPPED_GOLD_CHEST.get(),
      IronChestsBlocks.DIAMOND_CHEST.get(), IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(),
      IronChestsBlocks.COPPER_CHEST.get(), IronChestsBlocks.TRAPPED_COPPER_CHEST.get(),
      IronChestsBlocks.CRYSTAL_CHEST.get(), IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(),
      IronChestsBlocks.OBSIDIAN_CHEST.get(), IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get(),
      IronChestsBlocks.DIRT_CHEST.get()
    );
  }

  public static Identifier prefix(String name) {
    return Identifier.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
  }
}
