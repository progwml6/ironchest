package com.progwml6.ironchest.common.block;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.regular.CopperChestBlock;
import com.progwml6.ironchest.common.block.regular.CrystalChestBlock;
import com.progwml6.ironchest.common.block.regular.DiamondChestBlock;
import com.progwml6.ironchest.common.block.regular.DirtChestBlock;
import com.progwml6.ironchest.common.block.regular.GoldChestBlock;
import com.progwml6.ironchest.common.block.regular.IronChestBlock;
import com.progwml6.ironchest.common.block.regular.ObsidianChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedCopperChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedCrystalChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedDiamondChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedDirtChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedGoldChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedIronChestBlock;
import com.progwml6.ironchest.common.block.trapped.TrappedObsidianChestBlock;
import com.progwml6.ironchest.common.item.IronChestBlockItem;
import com.progwml6.ironchest.common.item.IronChestsItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class IronChestsBlocks {

  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(IronChests.MODID);

  static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F).sound(SoundType.METAL);

  public static final DeferredBlock<IronChestBlock> IRON_CHEST = registerWithItem("iron_chest", IronChestBlock::new, () -> properties, IronChestsTypes.IRON, false);
  public static final DeferredBlock<GoldChestBlock> GOLD_CHEST = registerWithItem("gold_chest", GoldChestBlock::new, () -> properties, IronChestsTypes.GOLD, false);
  public static final DeferredBlock<DiamondChestBlock> DIAMOND_CHEST = registerWithItem("diamond_chest", DiamondChestBlock::new, () -> properties, IronChestsTypes.DIAMOND, false);
  public static final DeferredBlock<CopperChestBlock> COPPER_CHEST = registerWithItem("copper_chest", CopperChestBlock::new, () -> properties, IronChestsTypes.COPPER, false);
  public static final DeferredBlock<CrystalChestBlock> CRYSTAL_CHEST = registerWithItem("crystal_chest", CrystalChestBlock::new, () -> properties, IronChestsTypes.CRYSTAL, false);
  public static final DeferredBlock<ObsidianChestBlock> OBSIDIAN_CHEST = registerWithItem("obsidian_chest", ObsidianChestBlock::new, () -> properties, IronChestsTypes.OBSIDIAN, false);
  public static final DeferredBlock<DirtChestBlock> DIRT_CHEST = registerWithItem("dirt_chest", DirtChestBlock::new, () -> properties, IronChestsTypes.DIRT, false);

  public static final DeferredBlock<TrappedIronChestBlock> TRAPPED_IRON_CHEST = registerWithItem("trapped_iron_chest", TrappedIronChestBlock::new, () -> properties, IronChestsTypes.IRON, true);
  public static final DeferredBlock<TrappedGoldChestBlock> TRAPPED_GOLD_CHEST = registerWithItem("trapped_gold_chest", TrappedGoldChestBlock::new, () -> properties, IronChestsTypes.GOLD, true);
  public static final DeferredBlock<TrappedDiamondChestBlock> TRAPPED_DIAMOND_CHEST = registerWithItem("trapped_diamond_chest", TrappedDiamondChestBlock::new, () -> properties, IronChestsTypes.DIAMOND, true);
  public static final DeferredBlock<TrappedCopperChestBlock> TRAPPED_COPPER_CHEST = registerWithItem("trapped_copper_chest", TrappedCopperChestBlock::new, () -> properties, IronChestsTypes.COPPER, true);
  public static final DeferredBlock<TrappedCrystalChestBlock> TRAPPED_CRYSTAL_CHEST = registerWithItem("trapped_crystal_chest", TrappedCrystalChestBlock::new, () -> properties, IronChestsTypes.CRYSTAL, true);
  public static final DeferredBlock<TrappedObsidianChestBlock> TRAPPED_OBSIDIAN_CHEST = registerWithItem("trapped_obsidian_chest", TrappedObsidianChestBlock::new, () -> properties, IronChestsTypes.OBSIDIAN, true);
  public static final DeferredBlock<TrappedDirtChestBlock> TRAPPED_DIRT_CHEST = registerWithItem("trapped_dirt_chest", TrappedDirtChestBlock::new, () -> properties, IronChestsTypes.DIRT, true);

  public static <T extends Block> DeferredBlock<T> registerWithItem(String name, Function<BlockBehaviour.Properties, T> block, Supplier<BlockBehaviour.Properties> properties, IronChestsTypes chestType, Boolean trapped) {
    DeferredBlock<T> ret = BLOCKS.register(name, () -> block.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK, IronChests.prefix(name)))));
    IronChestsItems.register(name, itemProps -> new IronChestBlockItem(ret.get(), itemProps, chestType, trapped), Item.Properties::new);
    return ret;
  }
}
