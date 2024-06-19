package com.progwml6.ironchest;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.creativetabs.IronChestsCreativeTabs;
import com.progwml6.ironchest.common.data.IronChestsBlockTags;
import com.progwml6.ironchest.common.data.IronChestsLanguageProvider;
import com.progwml6.ironchest.common.data.IronChestsRecipeProvider;
import com.progwml6.ironchest.common.data.IronChestsSpriteSourceProvider;
import com.progwml6.ironchest.common.data.loot.IronChestsLootTableProvider;
import com.progwml6.ironchest.common.inventory.IronChestsMenuTypes;
import com.progwml6.ironchest.common.item.IronChestsItems;
import com.progwml6.ironchest.common.network.TopStacksSyncPacket;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import java.util.concurrent.CompletableFuture;

@Mod(IronChests.MODID)
public class IronChests {

  public static final String MODID = "ironchest";

  public IronChests(IEventBus modEventBus) {
    // General mod setup
    modEventBus.addListener(this::gatherData);
    modEventBus.addListener(this::setupPackets);

    // Registry objects
    IronChestsBlocks.BLOCKS.register(modEventBus);
    IronChestsItems.ITEMS.register(modEventBus);
    IronChestsBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
    IronChestsMenuTypes.CONTAINERS.register(modEventBus);
    IronChestsCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
  }

  public void gatherData(GatherDataEvent event) {
    ExistingFileHelper ext = event.getExistingFileHelper();
    DataGenerator gen = event.getGenerator();
    PackOutput packOutput = gen.getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

    gen.addProvider(event.includeServer(), new IronChestsLootTableProvider(packOutput));

    gen.addProvider(event.includeClient(), new IronChestsRecipeProvider(packOutput));
    gen.addProvider(event.includeClient(), new IronChestsBlockTags(packOutput, lookupProvider, ext));
    gen.addProvider(event.includeClient(), new IronChestsSpriteSourceProvider(packOutput, ext, lookupProvider));
    gen.addProvider(event.includeClient(), new IronChestsLanguageProvider(packOutput, "en_us"));
  }

  public void setupPackets(RegisterPayloadHandlerEvent event) {
    IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();

    registrar.play(TopStacksSyncPacket.ID, TopStacksSyncPacket::new, payload -> payload.client(TopStacksSyncPacket::handle));
  }
}
