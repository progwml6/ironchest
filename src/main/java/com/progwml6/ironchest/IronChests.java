package com.progwml6.ironchest;

import com.progwml6.ironchest.client.render.IronChestRenderer;
import com.progwml6.ironchest.client.screen.IronChestScreen;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.creativetabs.IronChestsCreativeTabs;
import com.progwml6.ironchest.common.data.IronChestsBlockTags;
import com.progwml6.ironchest.common.data.IronChestsLanguageProvider;
import com.progwml6.ironchest.common.data.IronChestsRecipeProvider;
import com.progwml6.ironchest.common.data.IronChestsSpriteSourceProvider;
import com.progwml6.ironchest.common.data.loot.IronChestsLootTableProvider;
import com.progwml6.ironchest.common.inventory.IronChestsContainerTypes;
import com.progwml6.ironchest.common.item.IronChestsItems;
import com.progwml6.ironchest.common.network.IronChestNetwork;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(IronChests.MOD_ID)
public class IronChests {

  public static final String MOD_ID = "ironchest";

  public IronChests() {
    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

    // General mod setup
    modBus.addListener(this::setup);
    modBus.addListener(this::gatherData);

    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
      // Client setup
      modBus.addListener(this::setupClient);
    });

    IronChestNetwork.setup();

    // Registry objects
    IronChestsBlocks.BLOCKS.register(modBus);
    IronChestsItems.ITEMS.register(modBus);
    IronChestsBlockEntityTypes.BLOCK_ENTITIES.register(modBus);
    IronChestsContainerTypes.CONTAINERS.register(modBus);
    IronChestsCreativeTabs.CREATIVE_MODE_TABS.register(modBus);
  }

  @OnlyIn(Dist.CLIENT)
  private void setupClient(final FMLClientSetupEvent event) {
    MenuScreens.register(IronChestsContainerTypes.IRON_CHEST.get(), IronChestScreen::new);
    MenuScreens.register(IronChestsContainerTypes.GOLD_CHEST.get(), IronChestScreen::new);
    MenuScreens.register(IronChestsContainerTypes.DIAMOND_CHEST.get(), IronChestScreen::new);
    MenuScreens.register(IronChestsContainerTypes.CRYSTAL_CHEST.get(), IronChestScreen::new);
    MenuScreens.register(IronChestsContainerTypes.COPPER_CHEST.get(), IronChestScreen::new);
    MenuScreens.register(IronChestsContainerTypes.OBSIDIAN_CHEST.get(), IronChestScreen::new);
    MenuScreens.register(IronChestsContainerTypes.DIRT_CHEST.get(), IronChestScreen::new);

    BlockEntityRenderers.register(IronChestsBlockEntityTypes.IRON_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.GOLD_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.DIAMOND_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.COPPER_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.CRYSTAL_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.OBSIDIAN_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.DIRT_CHEST.get(), IronChestRenderer::new);

    BlockEntityRenderers.register(IronChestsBlockEntityTypes.TRAPPED_IRON_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.TRAPPED_GOLD_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.TRAPPED_DIAMOND_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.TRAPPED_COPPER_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.TRAPPED_CRYSTAL_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.TRAPPED_OBSIDIAN_CHEST.get(), IronChestRenderer::new);
    BlockEntityRenderers.register(IronChestsBlockEntityTypes.TRAPPED_DIRT_CHEST.get(), IronChestRenderer::new);
  }

  private void setup(final FMLCommonSetupEvent event) {

  }

  private void gatherData(GatherDataEvent event) {
    ExistingFileHelper ext = event.getExistingFileHelper();
    DataGenerator gen = event.getGenerator();
    PackOutput packOutput = gen.getPackOutput();
    CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

    gen.addProvider(event.includeServer(), new IronChestsLootTableProvider(packOutput));

    gen.addProvider(event.includeClient(), new IronChestsRecipeProvider(packOutput));
    gen.addProvider(event.includeClient(), new IronChestsBlockTags(packOutput, lookupProvider, ext));
    gen.addProvider(event.includeClient(), new IronChestsSpriteSourceProvider(packOutput, ext));
    gen.addProvider(event.includeClient(), new IronChestsLanguageProvider(packOutput, "en_us"));
  }
}
