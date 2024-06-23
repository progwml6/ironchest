package com.progwml6.ironchest.common.datacomponents;

import com.mojang.serialization.Codec;
import com.progwml6.ironchest.IronChests;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class IronChestsDataComponents {
  public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.createDataComponents(IronChests.MODID);

  public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CHEST_PLACED_ALREADY = COMPONENTS.register("chest_placed_already", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
}
