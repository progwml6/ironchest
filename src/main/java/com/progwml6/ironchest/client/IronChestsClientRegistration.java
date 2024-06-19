package com.progwml6.ironchest.client;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.client.render.IronChestRenderer;
import com.progwml6.ironchest.client.screen.IronChestScreen;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.inventory.IronChestsMenuTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod.EventBusSubscriber(modid = IronChests.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IronChestsClientRegistration {

  public static final ModelLayerLocation IRON_CHEST = new ModelLayerLocation(new ResourceLocation(IronChests.MODID, "iron_chest"), "main");

  @SubscribeEvent
  public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(IRON_CHEST, IronChestRenderer::createLayerDefinition);
  }

  @SubscribeEvent
  public static void registerScreens(RegisterMenuScreensEvent event) {
    event.register(IronChestsMenuTypes.IRON_CHEST.get(), IronChestScreen::new);
    event.register(IronChestsMenuTypes.GOLD_CHEST.get(), IronChestScreen::new);
    event.register(IronChestsMenuTypes.DIAMOND_CHEST.get(), IronChestScreen::new);
    event.register(IronChestsMenuTypes.CRYSTAL_CHEST.get(), IronChestScreen::new);
    event.register(IronChestsMenuTypes.COPPER_CHEST.get(), IronChestScreen::new);
    event.register(IronChestsMenuTypes.OBSIDIAN_CHEST.get(), IronChestScreen::new);
    event.register(IronChestsMenuTypes.DIRT_CHEST.get(), IronChestScreen::new);
  }

  @SubscribeEvent
  public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.IRON_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.GOLD_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.DIAMOND_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.COPPER_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.CRYSTAL_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.OBSIDIAN_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.DIRT_CHEST.get(), IronChestRenderer::new);

    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.TRAPPED_IRON_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.TRAPPED_GOLD_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.TRAPPED_DIAMOND_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.TRAPPED_COPPER_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.TRAPPED_CRYSTAL_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.TRAPPED_OBSIDIAN_CHEST.get(), IronChestRenderer::new);
    event.registerBlockEntityRenderer(IronChestsBlockEntityTypes.TRAPPED_DIRT_CHEST.get(), IronChestRenderer::new);
  }
}
