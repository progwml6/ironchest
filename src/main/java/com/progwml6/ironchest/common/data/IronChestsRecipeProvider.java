package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.item.IronChestsItems;
import com.progwml6.ironchest.common.item.IronChestsUpgradeType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public class IronChestsRecipeProvider extends RecipeProvider implements IConditionBuilder {

  public IronChestsRecipeProvider(PackOutput output) {
    super(output);
  }

  @Override
  protected void buildRecipes(RecipeOutput recipeOutput) {
    this.addChestsRecipes(recipeOutput);
    this.addUpgradesRecipes(recipeOutput);
  }

  private void addChestsRecipes(RecipeOutput recipeOutput) {
    String folder = "chests/";

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsBlocks.COPPER_CHEST.get())
      .define('M', Tags.Items.INGOTS_COPPER)
      .define('S', Tags.Items.CHESTS_WOODEN)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
      .save(recipeOutput, location(folder + "vanilla_copper_chest"));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsBlocks.IRON_CHEST.get())
      .define('M', Tags.Items.INGOTS_IRON)
      .define('S', Tags.Items.CHESTS_WOODEN)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(recipeOutput, location(folder + "vanilla_iron_chest"));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsBlocks.IRON_CHEST.get())
      .define('M', Tags.Items.INGOTS_IRON)
      .define('S', IronChestsBlocks.COPPER_CHEST.get())
      .define('G', Tags.Items.GLASS)
      .pattern("MGM")
      .pattern("GSG")
      .pattern("MGM")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(recipeOutput, location(folder + "copper_iron_chest"));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsBlocks.GOLD_CHEST.get())
      .define('M', Tags.Items.INGOTS_GOLD)
      .define('S', IronChestsBlocks.IRON_CHEST.get())
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
      .save(recipeOutput, location(folder + "iron_gold_chest"));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsBlocks.DIAMOND_CHEST.get())
      .define('M', Tags.Items.GEMS_DIAMOND)
      .define('S', IronChestsBlocks.GOLD_CHEST.get())
      .define('G', Tags.Items.GLASS)
      .pattern("GGG")
      .pattern("MSM")
      .pattern("GGG")
      .unlockedBy("has_diamonds", has(Tags.Items.GEMS_DIAMOND))
      .save(recipeOutput, location(folder + "gold_diamond_chest"));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsBlocks.OBSIDIAN_CHEST.get())
      .define('M', Blocks.OBSIDIAN)
      .define('S', IronChestsBlocks.DIAMOND_CHEST.get())
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_obsidian", has(Blocks.OBSIDIAN))
      .save(recipeOutput, location(folder + "diamond_obsidian_chest"));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsBlocks.CRYSTAL_CHEST.get())
      .define('G', Tags.Items.GLASS)
      .define('S', IronChestsBlocks.DIAMOND_CHEST.get())
      .pattern("GGG")
      .pattern("GSG")
      .pattern("GGG")
      .unlockedBy("has_glass", has(Tags.Items.GLASS))
      .save(recipeOutput, location(folder + "diamond_crystal_chest"));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsBlocks.DIRT_CHEST.get())
      .define('M', Ingredient.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL))
      .define('S', Tags.Items.CHESTS_WOODEN)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(Blocks.DIRT))
      .save(recipeOutput, location(folder + "vanilla_dirt_chest"));

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_IRON_CHEST.get())
      .requires(IronChestsBlocks.IRON_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(recipeOutput, location(folder + "trapped_iron_chest"));

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_GOLD_CHEST.get())
      .requires(IronChestsBlocks.GOLD_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(recipeOutput, location(folder + "trapped_gold_chest"));

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get())
      .requires(IronChestsBlocks.DIAMOND_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(recipeOutput, location(folder + "trapped_diamond_chest"));

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_COPPER_CHEST.get())
      .requires(IronChestsBlocks.COPPER_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(recipeOutput, location(folder + "trapped_copper_chest"));

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get())
      .requires(IronChestsBlocks.CRYSTAL_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(recipeOutput, location(folder + "trapped_crystal_chest"));

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get())
      .requires(IronChestsBlocks.OBSIDIAN_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(recipeOutput, location(folder + "trapped_obsidian_chest"));

    ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_DIRT_CHEST.get())
      .requires(IronChestsBlocks.DIRT_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(recipeOutput, location(folder + "trapped_dirt_chest"));
  }

  private void addUpgradesRecipes(RecipeOutput recipeOutput) {
    String folder = "upgrades/";

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsItems.UPGRADES.get(IronChestsUpgradeType.WOOD_TO_COPPER).get())
      .define('M', Tags.Items.INGOTS_COPPER)
      .define('P', ItemTags.PLANKS)
      .pattern("MMM")
      .pattern("MPM")
      .pattern("MMM")
      .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
      .save(recipeOutput, prefix(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.WOOD_TO_COPPER).get(), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsItems.UPGRADES.get(IronChestsUpgradeType.WOOD_TO_IRON).get())
      .define('M', Tags.Items.INGOTS_IRON)
      .define('P', ItemTags.PLANKS)
      .pattern("MMM")
      .pattern("MPM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(recipeOutput, prefix(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.WOOD_TO_IRON).get(), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsItems.UPGRADES.get(IronChestsUpgradeType.COPPER_TO_IRON).get())
      .define('I', Tags.Items.INGOTS_IRON)
      .define('C', Tags.Items.INGOTS_COPPER)
      .define('G', Tags.Items.GLASS)
      .pattern("IGI")
      .pattern("GCG")
      .pattern("IGI")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(recipeOutput, prefix(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.COPPER_TO_IRON).get(), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsItems.UPGRADES.get(IronChestsUpgradeType.IRON_TO_GOLD).get())
      .define('I', Tags.Items.INGOTS_IRON)
      .define('G', Tags.Items.INGOTS_GOLD)
      .pattern("GGG")
      .pattern("GIG")
      .pattern("GGG")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(recipeOutput, prefix(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.IRON_TO_GOLD).get(), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsItems.UPGRADES.get(IronChestsUpgradeType.GOLD_TO_DIAMOND).get())
      .define('M', Tags.Items.GEMS_DIAMOND)
      .define('S', Tags.Items.INGOTS_GOLD)
      .define('G', Tags.Items.GLASS)
      .pattern("GGG")
      .pattern("MSM")
      .pattern("GGG")
      .unlockedBy("has_glass", has(Tags.Items.GLASS))
      .save(recipeOutput, prefix(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.GOLD_TO_DIAMOND).get(), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsItems.UPGRADES.get(IronChestsUpgradeType.DIAMOND_TO_OBSIDIAN).get())
      .define('M', Blocks.OBSIDIAN)
      .define('G', Tags.Items.GLASS)
      .pattern("MMM")
      .pattern("MGM")
      .pattern("MMM")
      .unlockedBy("has_glass", has(Tags.Items.GLASS))
      .save(recipeOutput, prefix(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.DIAMOND_TO_OBSIDIAN).get(), folder));

    ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, IronChestsItems.UPGRADES.get(IronChestsUpgradeType.DIAMOND_TO_CRYSTAL).get())
      .define('M', Blocks.OBSIDIAN)
      .define('G', Tags.Items.GLASS)
      .pattern("GGG")
      .pattern("GMG")
      .pattern("GGG")
      .unlockedBy("has_glass", has(Tags.Items.GLASS))
      .save(recipeOutput, prefix(IronChestsItems.UPGRADES.get(IronChestsUpgradeType.DIAMOND_TO_CRYSTAL).get(), folder));
  }

  protected static ResourceLocation prefix(ItemLike item, String prefix) {
    ResourceLocation registryName = BuiltInRegistries.ITEM.getResourceKey(item.asItem())
      .map(ResourceKey::location)
      .orElseThrow(() -> new IllegalStateException("Could not retrieve registry name for output."));
    return location(prefix + registryName.getPath());
  }

  private static ResourceLocation location(String id) {
    return new ResourceLocation(IronChests.MODID, id);
  }

  private static TagKey<Item> tag(String name) {
    return ItemTags.create(new ResourceLocation("forge", name));
  }
}
