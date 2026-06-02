package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.client.renderer.special.IronChestSpecialRenderer;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.item.IronChestsItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class IronChestsModelProvider extends ModelProvider {

  public IronChestsModelProvider(PackOutput output) {
    super(output, IronChests.MODID);
  }

  @Override
  protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
    this.createChest(blockModels, itemModels, IronChestsBlocks.IRON_CHEST.get(), new Material(IronChests.prefix("block/iron_break")), IronChestSpecialRenderer.IRON_CHEST_TEXTURE);
    this.createChest(blockModels, itemModels, IronChestsBlocks.TRAPPED_IRON_CHEST.get(), new Material(IronChests.prefix("block/iron_break")), IronChestSpecialRenderer.TRAPPED_IRON_CHEST_TEXTURE);

    this.createChest(blockModels, itemModels, IronChestsBlocks.GOLD_CHEST.get(), new Material(IronChests.prefix("block/gold_break")), IronChestSpecialRenderer.GOLD_CHEST_TEXTURE);
    this.createChest(blockModels, itemModels, IronChestsBlocks.TRAPPED_GOLD_CHEST.get(), new Material(IronChests.prefix("block/gold_break")), IronChestSpecialRenderer.TRAPPED_GOLD_CHEST_TEXTURE);

    this.createChest(blockModels, itemModels, IronChestsBlocks.DIAMOND_CHEST.get(), new Material(IronChests.prefix("block/diamond_break")), IronChestSpecialRenderer.DIAMOND_CHEST_TEXTURE);
    this.createChest(blockModels, itemModels, IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(), new Material(IronChests.prefix("block/diamond_break")), IronChestSpecialRenderer.TRAPPED_DIAMOND_CHEST_TEXTURE);

    this.createChest(blockModels, itemModels, IronChestsBlocks.COPPER_CHEST.get(), new Material(IronChests.prefix("block/copper_break")), IronChestSpecialRenderer.COPPER_CHEST_TEXTURE);
    this.createChest(blockModels, itemModels, IronChestsBlocks.TRAPPED_COPPER_CHEST.get(), new Material(IronChests.prefix("block/copper_break")), IronChestSpecialRenderer.TRAPPED_COPPER_CHEST_TEXTURE);

    this.createChest(blockModels, itemModels, IronChestsBlocks.CRYSTAL_CHEST.get(), new Material(IronChests.prefix("block/crystal_break")), IronChestSpecialRenderer.CRYSTAL_CHEST_TEXTURE);
    this.createChest(blockModels, itemModels, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(), new Material(IronChests.prefix("block/crystal_break")), IronChestSpecialRenderer.TRAPPED_CRYSTAL_CHEST_TEXTURE);

    this.createChest(blockModels, itemModels, IronChestsBlocks.OBSIDIAN_CHEST.get(), TextureMapping.getBlockTexture(Blocks.OBSIDIAN), IronChestSpecialRenderer.OBSIDIAN_CHEST_TEXTURE);
    this.createChest(blockModels, itemModels, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get(), TextureMapping.getBlockTexture(Blocks.OBSIDIAN), IronChestSpecialRenderer.TRAPPED_OBSIDIAN_CHEST_TEXTURE);

    this.createChest(blockModels, itemModels, IronChestsBlocks.DIRT_CHEST.get(), TextureMapping.getBlockTexture(Blocks.DIRT), IronChestSpecialRenderer.DIRT_CHEST_TEXTURE);
    this.createChest(blockModels, itemModels, IronChestsBlocks.TRAPPED_DIRT_CHEST.get(), TextureMapping.getBlockTexture(Blocks.DIRT), IronChestSpecialRenderer.TRAPPED_DIRT_CHEST_TEXTURE);

    this.generateFlatItem(itemModels, IronChestsItems.IRON_TO_GOLD_CHEST_UPGRADE.get(), IronChests.prefix("item/iron_gold_upgrade"));
    this.generateFlatItem(itemModels, IronChestsItems.GOLD_TO_DIAMOND_CHEST_UPGRADE.get(), IronChests.prefix("item/gold_diamond_upgrade"));
    this.generateFlatItem(itemModels, IronChestsItems.COPPER_TO_IRON_CHEST_UPGRADE.get(), IronChests.prefix("item/copper_iron_upgrade"));
    this.generateFlatItem(itemModels, IronChestsItems.DIAMOND_TO_CRYSTAL_CHEST_UPGRADE.get(), IronChests.prefix("item/diamond_crystal_upgrade"));
    this.generateFlatItem(itemModels, IronChestsItems.WOOD_TO_IRON_CHEST_UPGRADE.get(), IronChests.prefix("item/wood_iron_upgrade"));
    this.generateFlatItem(itemModels, IronChestsItems.WOOD_TO_COPPER_CHEST_UPGRADE.get(), IronChests.prefix("item/wood_copper_upgrade"));
    this.generateFlatItem(itemModels, IronChestsItems.DIAMOND_TO_OBSIDIAN_CHEST_UPGRADE.get(), IronChests.prefix("item/diamond_obsidian_upgrade"));
  }

  public void createChest(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block chestBlock, Material particleTexture, Identifier texture) {
    blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(chestBlock, new MultiVariant(WeightedList.of(new Variant(ModelTemplates.PARTICLE_ONLY.create(chestBlock, TextureMapping.particle(particleTexture), blockModels.modelOutput))))));
    Item chestItem = chestBlock.asItem();
    Identifier Identifier = ModelTemplates.CHEST_INVENTORY.create(chestItem, TextureMapping.particle(particleTexture), blockModels.modelOutput);
    ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(Identifier, new IronChestSpecialRenderer.Unbaked(texture));
    itemModels.itemModelOutput.accept(chestItem, unbaked);
  }

  public void generateFlatItem(ItemModelGenerators itemModels, Item item, Identifier itemTexture) {
    itemModels.itemModelOutput.accept(item, ItemModelUtils.plainModel(ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(new Material(itemTexture)), itemModels.modelOutput)));
  }
}
