package com.progwml6.ironchest.common.data;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.item.IronChestsItems;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class IronChestsRecipeProvider extends RecipeProvider implements IConditionBuilder {

  public IronChestsRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
    super(provider, output);
  }

  @Override
  protected void buildRecipes() {
    HolderGetter<Item> itemRegistryLookup = this.registries.lookupOrThrow(Registries.ITEM);
    this.addChestsRecipes(itemRegistryLookup);
    this.addUpgradesRecipes(itemRegistryLookup);
  }

  private void addChestsRecipes(HolderGetter<Item> itemRegistryLookup) {
    String folder = "chests/";

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.COPPER_CHEST.get())
      .define('M', Tags.Items.INGOTS_COPPER)
      .define('S', Tags.Items.CHESTS_WOODEN)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
      .save(this.output, createKey(folder + "vanilla_copper_chest"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.IRON_CHEST.get())
      .define('M', Tags.Items.INGOTS_IRON)
      .define('S', Tags.Items.CHESTS_WOODEN)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(this.output, createKey(folder + "vanilla_iron_chest"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.IRON_CHEST.get())
      .define('M', Tags.Items.INGOTS_IRON)
      .define('S', IronChestsBlocks.COPPER_CHEST.get())
      .define('G', Tags.Items.GLASS_BLOCKS)
      .pattern("MGM")
      .pattern("GSG")
      .pattern("MGM")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(this.output, createKey(folder + "copper_iron_chest"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.GOLD_CHEST.get())
      .define('M', Tags.Items.INGOTS_GOLD)
      .define('S', IronChestsBlocks.IRON_CHEST.get())
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
      .save(this.output, createKey(folder + "iron_gold_chest"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.DIAMOND_CHEST.get())
      .define('M', Tags.Items.GEMS_DIAMOND)
      .define('S', IronChestsBlocks.GOLD_CHEST.get())
      .define('G', Tags.Items.GLASS_BLOCKS)
      .pattern("GGG")
      .pattern("MSM")
      .pattern("GGG")
      .unlockedBy("has_diamonds", has(Tags.Items.GEMS_DIAMOND))
      .save(this.output, createKey(folder + "gold_diamond_chest"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.OBSIDIAN_CHEST.get())
      .define('M', Blocks.OBSIDIAN)
      .define('S', IronChestsBlocks.DIAMOND_CHEST.get())
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_obsidian", has(Blocks.OBSIDIAN))
      .save(this.output, createKey(folder + "diamond_obsidian_chest"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.CRYSTAL_CHEST.get())
      .define('G', Tags.Items.GLASS_BLOCKS)
      .define('S', IronChestsBlocks.DIAMOND_CHEST.get())
      .pattern("GGG")
      .pattern("GSG")
      .pattern("GGG")
      .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
      .save(this.output, createKey(folder + "diamond_crystal_chest"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.DIRT_CHEST.get())
      .define('M', Ingredient.of(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL))
      .define('S', Tags.Items.CHESTS_WOODEN)
      .pattern("MMM")
      .pattern("MSM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(Blocks.DIRT))
      .save(this.output, createKey(folder + "vanilla_dirt_chest"));

    ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_IRON_CHEST.get())
      .requires(IronChestsBlocks.IRON_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(this.output, createKey(folder + "trapped_iron_chest"));

    ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_GOLD_CHEST.get())
      .requires(IronChestsBlocks.GOLD_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(this.output, createKey(folder + "trapped_gold_chest"));

    ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get())
      .requires(IronChestsBlocks.DIAMOND_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(this.output, createKey(folder + "trapped_diamond_chest"));

    ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_COPPER_CHEST.get())
      .requires(IronChestsBlocks.COPPER_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(this.output, createKey(folder + "trapped_copper_chest"));

    ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get())
      .requires(IronChestsBlocks.CRYSTAL_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(this.output, createKey(folder + "trapped_crystal_chest"));

    ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get())
      .requires(IronChestsBlocks.OBSIDIAN_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(this.output, createKey(folder + "trapped_obsidian_chest"));

    ShapelessRecipeBuilder.shapeless(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsBlocks.TRAPPED_DIRT_CHEST.get())
      .requires(IronChestsBlocks.DIRT_CHEST.get())
      .requires(Blocks.TRIPWIRE_HOOK)
      .unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK))
      .save(this.output, createKey(folder + "trapped_dirt_chest"));
  }

  private void addUpgradesRecipes(HolderGetter<Item> itemRegistryLookup) {
    String folder = "upgrades/";

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsItems.WOOD_TO_COPPER_CHEST_UPGRADE.get())
      .define('M', Tags.Items.INGOTS_COPPER)
      .define('P', ItemTags.PLANKS)
      .pattern("MMM")
      .pattern("MPM")
      .pattern("MMM")
      .unlockedBy("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
      .save(this.output, createKey(folder + "wood_to_copper_chest_upgrade"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsItems.WOOD_TO_IRON_CHEST_UPGRADE.get())
      .define('M', Tags.Items.INGOTS_IRON)
      .define('P', ItemTags.PLANKS)
      .pattern("MMM")
      .pattern("MPM")
      .pattern("MMM")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(this.output, createKey(folder + "wood_to_iron_chest_upgrade"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsItems.COPPER_TO_IRON_CHEST_UPGRADE.get())
      .define('I', Tags.Items.INGOTS_IRON)
      .define('C', Tags.Items.INGOTS_COPPER)
      .define('G', Tags.Items.GLASS_BLOCKS)
      .pattern("IGI")
      .pattern("GCG")
      .pattern("IGI")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(this.output, createKey(folder + "copper_to_iron_chest_upgrade"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsItems.IRON_TO_GOLD_CHEST_UPGRADE.get())
      .define('I', Tags.Items.INGOTS_IRON)
      .define('G', Tags.Items.INGOTS_GOLD)
      .pattern("GGG")
      .pattern("GIG")
      .pattern("GGG")
      .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
      .save(this.output, createKey(folder + "iron_to_gold_chest_upgrade"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsItems.GOLD_TO_DIAMOND_CHEST_UPGRADE.get())
      .define('M', Tags.Items.GEMS_DIAMOND)
      .define('S', Tags.Items.INGOTS_GOLD)
      .define('G', Tags.Items.GLASS_BLOCKS)
      .pattern("GGG")
      .pattern("MSM")
      .pattern("GGG")
      .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
      .save(this.output, createKey(folder + "gold_to_diamond_chest_upgrade"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsItems.DIAMOND_TO_OBSIDIAN_CHEST_UPGRADE.get())
      .define('M', Blocks.OBSIDIAN)
      .define('G', Tags.Items.GLASS_BLOCKS)
      .pattern("MMM")
      .pattern("MGM")
      .pattern("MMM")
      .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
      .save(this.output, createKey(folder + "diamond_to_obsidian_chest_upgrade"));

    ShapedRecipeBuilder.shaped(itemRegistryLookup, RecipeCategory.DECORATIONS, IronChestsItems.DIAMOND_TO_CRYSTAL_CHEST_UPGRADE.get())
      .define('M', Blocks.OBSIDIAN)
      .define('G', Tags.Items.GLASS_BLOCKS)
      .pattern("GGG")
      .pattern("GMG")
      .pattern("GGG")
      .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
      .save(this.output, createKey(folder + "diamond_to_crystal_chest_upgrade"));
  }

  protected ResourceKey<Recipe<?>> createKey(String name) {
    return ResourceKey.create(Registries.RECIPE, IronChests.prefix(name));
  }

  // The runner to add to the data generator
  public static class Runner extends RecipeProvider.Runner {

    public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
      super(output, lookupProvider);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
      return new IronChestsRecipeProvider(provider, output);
    }

    @Override
    public String getName() {
      return "Iron Chest Boxes Recipes";
    }
  }
}
