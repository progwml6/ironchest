package com.progwml6.ironchest.common.network;

import com.progwml6.ironchest.common.block.entity.ICrystalChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.stream.IntStream;

public class PacketTopStacksSync {

  private final BlockPos blockPos;
  private final NonNullList<ItemStack> topItemStacks;

  public PacketTopStacksSync(BlockPos blockPos, NonNullList<ItemStack> topItemStacks) {
    this.blockPos = blockPos;
    this.topItemStacks = topItemStacks;
  }

  public static void encode(PacketTopStacksSync msg, FriendlyByteBuf buf) {
    buf.writeBlockPos(msg.blockPos);
    buf.writeInt(msg.topItemStacks.size());
    msg.topItemStacks.forEach(buf::writeItem);
  }

  public static PacketTopStacksSync decode(FriendlyByteBuf buf) {
    BlockPos blockPos = buf.readBlockPos();
    int size = buf.readInt();
    NonNullList<ItemStack> topItemStacks = NonNullList.withSize(size, ItemStack.EMPTY);

    IntStream.range(0, size).forEach(item -> {
      ItemStack itemStack = buf.readItem();
      topItemStacks.set(item, itemStack);
    });

    return new PacketTopStacksSync(blockPos, topItemStacks);
  }

  public static void handle(PacketTopStacksSync msg, CustomPayloadEvent.Context ctx) {
    ctx.enqueueWork(() -> {
      ClientLevel level = Minecraft.getInstance().level;

      if (level != null) {
        BlockEntity blockEntity = level.getBlockEntity(msg.blockPos);

        if (blockEntity != null) {
          if (blockEntity instanceof ICrystalChest) {
            ((ICrystalChest) blockEntity).receiveMessageFromServer(msg.topItemStacks);

            Minecraft.getInstance().levelRenderer.blockChanged(null, msg.blockPos, null, null, 0);
          }
        }
      }
    });
    ctx.setPacketHandled(true);
  }
}
