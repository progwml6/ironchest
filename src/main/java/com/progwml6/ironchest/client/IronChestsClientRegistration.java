package com.progwml6.ironchest.client;

import com.mojang.math.Transformation;
import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.client.model.IronChestModel;
import com.progwml6.ironchest.client.renderer.IronChestRenderer;
import com.progwml6.ironchest.client.renderer.special.IronChestSpecialRenderer;
import com.progwml6.ironchest.client.screen.IronChestScreen;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.regular.IronChestBlock;
import com.progwml6.ironchest.common.inventory.IronChestsMenuTypes;
import com.progwml6.ironchest.common.network.TopStacksSyncPacket;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.SpecialBlockModelWrapper;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterBlockModelsEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;

import java.util.Optional;
import java.util.function.Function;

@EventBusSubscriber(modid = IronChests.MODID, value = Dist.CLIENT)
public class IronChestsClientRegistration {

  public static final ModelLayerLocation IRON_CHEST = new ModelLayerLocation(IronChests.prefix("iron_chest"), "main");

  @SubscribeEvent
  public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    event.registerLayerDefinition(IRON_CHEST, IronChestModel::createLayerDefinition);
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

  @SubscribeEvent
  public static void registerSpecialRenderers(RegisterSpecialModelRendererEvent event) {
    event.register(IronChests.prefix("iron_chest"), IronChestSpecialRenderer.Unbaked.MAP_CODEC);
  }

  @SubscribeEvent
  public static void registerSpecialBlockRenderers(RegisterBlockModelsEvent event) {
    event.register(createChest(IronChestSpecialRenderer.IRON_CHEST_TEXTURE), IronChestsBlocks.IRON_CHEST.get());
    event.register(createChest(IronChestSpecialRenderer.TRAPPED_IRON_CHEST_TEXTURE), IronChestsBlocks.TRAPPED_IRON_CHEST.get());

    event.register(createChest(IronChestSpecialRenderer.GOLD_CHEST_TEXTURE), IronChestsBlocks.GOLD_CHEST.get());
    event.register(createChest(IronChestSpecialRenderer.TRAPPED_GOLD_CHEST_TEXTURE), IronChestsBlocks.TRAPPED_GOLD_CHEST.get());

    event.register(createChest(IronChestSpecialRenderer.DIAMOND_CHEST_TEXTURE), IronChestsBlocks.DIAMOND_CHEST.get());
    event.register(createChest(IronChestSpecialRenderer.TRAPPED_DIAMOND_CHEST_TEXTURE), IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get());

    event.register(createChest(IronChestSpecialRenderer.COPPER_CHEST_TEXTURE), IronChestsBlocks.COPPER_CHEST.get());
    event.register(createChest(IronChestSpecialRenderer.TRAPPED_COPPER_CHEST_TEXTURE), IronChestsBlocks.TRAPPED_COPPER_CHEST.get());

    event.register(createChest(IronChestSpecialRenderer.CRYSTAL_CHEST_TEXTURE), IronChestsBlocks.CRYSTAL_CHEST.get());
    event.register(createChest(IronChestSpecialRenderer.TRAPPED_CRYSTAL_CHEST_TEXTURE), IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get());

    event.register(createChest(IronChestSpecialRenderer.OBSIDIAN_CHEST_TEXTURE), IronChestsBlocks.OBSIDIAN_CHEST.get());
    event.register(createChest(IronChestSpecialRenderer.TRAPPED_OBSIDIAN_CHEST_TEXTURE), IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get());

    event.register(createChest(IronChestSpecialRenderer.DIRT_CHEST_TEXTURE), IronChestsBlocks.DIRT_CHEST.get());
    event.register(createChest(IronChestSpecialRenderer.TRAPPED_DIRT_CHEST_TEXTURE), IronChestsBlocks.TRAPPED_DIRT_CHEST.get());
  }

  public static BuiltInBlockModels.SpecialModelFactory createChest(Identifier texture) {
    return specialModelWithPropertyDispatch(IronChestBlock.FACING, facing -> createChest(texture, facing));
  }

  public static BlockModel.Unbaked createChest(Identifier texture, Direction facing) {
    return special(new IronChestSpecialRenderer.Unbaked(texture), IronChestRenderer.modelTransformation(facing));
  }

  public static <P extends Comparable<P>> BuiltInBlockModels.SpecialModelFactory specialModelWithPropertyDispatch(
    Property<P> property, Function<P, BlockModel.Unbaked> blockModel
  ) {
    return state -> {
      P value = state.getValue(property);
      return blockModel.apply(value);
    };
  }

  public static BlockModel.Unbaked special(SpecialModelRenderer.Unbaked<?> model) {
    return new SpecialBlockModelWrapper.Unbaked<>(model, Optional.empty());
  }

  public static BlockModel.Unbaked special(SpecialModelRenderer.Unbaked<?> model, Transformation transformation) {
    return new SpecialBlockModelWrapper.Unbaked<>(model, Optional.of(transformation));
  }

  @SubscribeEvent // on the mod event bus only on the physical client
  public static void register(RegisterClientPayloadHandlersEvent event) {
    event.register(
      TopStacksSyncPacket.TYPE,
      TopStacksSyncPacket::handleClient
    );
  }
}
