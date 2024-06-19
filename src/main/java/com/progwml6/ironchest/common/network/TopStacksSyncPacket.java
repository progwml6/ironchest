package com.progwml6.ironchest.common.network;

import com.progwml6.ironchest.IronChests;
import com.progwml6.ironchest.common.block.entity.ICrystalChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.stream.IntStream;

public class TopStacksSyncPacket implements CustomPacketPayload {

  public static final ResourceLocation ID = new ResourceLocation(IronChests.MODID, "top_stacks");

  private final BlockPos blockPos;
  private final NonNullList<ItemStack> topItemStacks;

  public TopStacksSyncPacket(BlockPos blockPos, NonNullList<ItemStack> topItemStacks) {
    this.blockPos = blockPos;
    this.topItemStacks = topItemStacks;
  }

  public TopStacksSyncPacket(FriendlyByteBuf buf) {
    BlockPos blockPos = buf.readBlockPos();
    int size = buf.readInt();
    NonNullList<ItemStack> topItemStacks = NonNullList.withSize(size, ItemStack.EMPTY);

    IntStream.range(0, size).forEach(item -> {
      ItemStack itemStack = buf.readItem();
      topItemStacks.set(item, itemStack);
    });

    this.blockPos = blockPos;
    this.topItemStacks = topItemStacks;
  }

  @Override
  public void write(FriendlyByteBuf buf) {
    buf.writeBlockPos(this.blockPos);
    buf.writeInt(this.topItemStacks.size());
    this.topItemStacks.forEach(buf::writeItem);
  }

  @Override
  public ResourceLocation id() {
    return ID;
  }

  public static void handle(TopStacksSyncPacket msg, PlayPayloadContext ctx) {
    if (ctx.flow().isClientbound()) {
      ctx.workHandler().execute(() -> {
        ClientLevel level = (ClientLevel) ctx.level().orElseThrow();

        BlockEntity blockEntity = level.getBlockEntity(msg.blockPos);

        if (blockEntity != null) {
          if (blockEntity instanceof ICrystalChest) {
            ((ICrystalChest) blockEntity).receiveMessageFromServer(msg.topItemStacks);

            Minecraft.getInstance().levelRenderer.blockChanged(null, msg.blockPos, null, null, 0);
          }
        }
      });
    }
  }
}
