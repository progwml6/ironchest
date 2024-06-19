package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.item.IronChestsItems;
import com.progwml6.ironchest.common.item.IronChestsUpgradeType;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class IronChestsLanguageProvider extends LanguageProvider {

  public IronChestsLanguageProvider(PackOutput output, String locale) {
    super(output, IronChests.MODID, locale);
  }

  @Override
  protected void addTranslations() {
    this.addBlock(IronChestsBlocks.IRON_CHEST, "Iron Chest");
    this.addBlock(IronChestsBlocks.GOLD_CHEST, "Gold Chest");
    this.addBlock(IronChestsBlocks.DIAMOND_CHEST, "Diamond Chest");
    this.addBlock(IronChestsBlocks.COPPER_CHEST, "Copper Chest");
    this.addBlock(IronChestsBlocks.CRYSTAL_CHEST, "Crystal Chest");
    this.addBlock(IronChestsBlocks.OBSIDIAN_CHEST, "Obsidian Chest");
    this.addBlock(IronChestsBlocks.DIRT_CHEST, "DirtChest 9000!");

    this.addBlock(IronChestsBlocks.TRAPPED_IRON_CHEST, "Trapped Iron Chest");
    this.addBlock(IronChestsBlocks.TRAPPED_GOLD_CHEST, "Trapped Gold Chest");
    this.addBlock(IronChestsBlocks.TRAPPED_DIAMOND_CHEST, "Trapped Diamond Chest");
    this.addBlock(IronChestsBlocks.TRAPPED_COPPER_CHEST, "Trapped Copper Chest");
    this.addBlock(IronChestsBlocks.TRAPPED_CRYSTAL_CHEST, "Trapped Crystal Chest");
    this.addBlock(IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST, "Trapped Obsidian Chest");
    this.addBlock(IronChestsBlocks.TRAPPED_DIRT_CHEST, "Trapped DirtChest 9000!");

    this.addItem(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.IRON_TO_GOLD), "Iron to Gold Chest Upgrade");
    this.addItem(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.GOLD_TO_DIAMOND), "Gold to Diamond Chest Upgrade");
    this.addItem(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.COPPER_TO_IRON), "Copper to Iron Chest Upgrade");
    this.addItem(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.DIAMOND_TO_CRYSTAL), "Diamond to Crystal Chest Upgrade");
    this.addItem(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.WOOD_TO_IRON), "Wood to Iron Chest Upgrade");
    this.addItem(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.WOOD_TO_COPPER), "Wood to Copper Chest Upgrade");
    this.addItem(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.DIAMOND_TO_OBSIDIAN), "Diamond to Obsidian Chest Upgrade");

    this.addBookAndContents("dirtchest9000", "How to use your DirtChest 9000!",
      "Welcome to your new DirtChest 9000! We hope you will enjoy many happy years of storing your stack of dirt in our storage utility.",
      "Usage: simply insert the stack of dirt of your choice into the highly receptive slot and enjoy the great convenience of having that dirt available to you, any time you pass by this chest!",
      "We hope you have enjoyed reviewing this instruction manual, and hope you will consider using our products in future! Kind regards, The DirtChest 9000 manual writers incorporated.",
      "Warranty: This product has no warranty of any kind. Your dirt may not be stored, it may slowly leech into the environment, or alternatively, it may not do anything at all.",
      "DirtChest 9000 is kind to the environment. Please dispose of this guide book responsibly, and do not whatever you do just chuck it into some lava. We would be very sad.");

    this.add("itemGroup.ironchest", "Iron Chests");

    this.add("ironchest.container.iron_chest", "Iron Chest");
    this.add("ironchest.container.gold_chest", "Gold Chest");
    this.add("ironchest.container.diamond_chest", "Diamond Chest");
    this.add("ironchest.container.copper_chest", "Copper Chest");
    this.add("ironchest.container.silver_chest", "Silver Chest");
    this.add("ironchest.container.crystal_chest", "Crystal Chest");
    this.add("ironchest.container.obsidian_chest", "Obsidian Chest");
    this.add("ironchest.container.dirt_chest", "DirtChest 9000!");
  }

  public void addBookAndContents(String bookKey, String bookTitle, String... pages) {
    this.add("ironchest.book." + bookKey, bookTitle);
    int pageCount = 0;
    for (String page : pages) {
      pageCount++;
      this.add("ironchest.book." + bookKey + "." + pageCount, page);
    }
  }
}
