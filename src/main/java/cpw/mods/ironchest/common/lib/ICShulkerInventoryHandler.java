/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package cpw.mods.ironchest.common.lib;

import javax.annotation.Nonnull;

import cpw.mods.ironchest.common.tileentity.shulker.TileEntityIronShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class ICShulkerInventoryHandler implements IItemHandlerModifiable
{
    int slots;

    TileEntityIronShulkerBox inv;

    int slotOffset;

    boolean[] canInsert;

    boolean[] canExtract;

    public ICShulkerInventoryHandler(int slots, TileEntityIronShulkerBox inventory, int slotOffset, boolean[] canInsert, boolean[] canExtract)
    {
        this.slots = slots;
        this.inv = inventory;
        this.slotOffset = slotOffset;
        this.canInsert = canInsert;
        this.canExtract = canExtract;
    }

    public ICShulkerInventoryHandler(int slots, TileEntityIronShulkerBox inventory)
    {
        this(slots, inventory, 0, new boolean[slots], new boolean[slots]);
        for (int i = 0; i < slots; i++)
            this.canExtract[i] = this.canInsert[i] = true;
    }

    public ICShulkerInventoryHandler(int slots, TileEntityIronShulkerBox inventory, int slotOffset, boolean canInsert, boolean canExtract)
    {
        this(slots, inventory, slotOffset, new boolean[slots], new boolean[slots]);
        for (int i = 0; i < slots; i++)
        {
            this.canInsert[i] = canInsert;
            this.canExtract[i] = canExtract;
        }
    }

    @Override
    public int getSlots()
    {
        return slots;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return this.inv.getItems().get(this.slotOffset + slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        if (!canInsert[slot] || stack.isEmpty())
            return stack;
        stack = stack.copy();

        if (!inv.isItemValidForSlot(this.slotOffset + slot, stack))
            return stack;

        int offsetSlot = this.slotOffset + slot;
        ItemStack currentStack = inv.getItems().get(offsetSlot);

        if (currentStack.isEmpty())
        {
            int accepted = Math.min(stack.getMaxStackSize(), inv.getInventoryStackLimit());
            if (accepted < stack.getCount())
            {
                if (!simulate)
                {
                    inv.getItems().set(offsetSlot, stack.splitStack(accepted));
                    inv.markDirty();
                    return stack;
                }
                else
                {
                    stack.shrink(accepted);
                    return stack;
                }
            }
            else
            {
                if (!simulate)
                {
                    inv.getItems().set(offsetSlot, stack);
                    inv.markDirty();
                }
                return ItemStack.EMPTY;
            }
        }
        else
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, currentStack))
                return stack;

            int accepted = Math.min(stack.getMaxStackSize(), inv.getInventoryStackLimit()) - currentStack.getCount();
            if (accepted < stack.getCount())
            {
                if (!simulate)
                {
                    ItemStack newStack = stack.splitStack(accepted);
                    newStack.grow(currentStack.getCount());
                    inv.getItems().set(offsetSlot, newStack);
                    inv.markDirty();
                    return stack;
                }
                else
                {
                    stack.shrink(accepted);
                    return stack;
                }
            }
            else
            {
                if (!simulate)
                {
                    ItemStack newStack = stack.copy();
                    newStack.grow(currentStack.getCount());
                    inv.getItems().set(offsetSlot, newStack);
                    inv.markDirty();
                }
                return ItemStack.EMPTY;
            }
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (!canExtract[slot] || amount == 0)
            return ItemStack.EMPTY;

        int offsetSlot = this.slotOffset + slot;
        ItemStack currentStack = inv.getItems().get(offsetSlot);

        if (currentStack.isEmpty())
            return ItemStack.EMPTY;

        int extracted = Math.min(currentStack.getCount(), amount);

        ItemStack copy = currentStack.copy();
        copy.setCount(extracted);
        if (!simulate)
        {
            if (extracted < currentStack.getCount())
                currentStack.shrink(extracted);
            else
                currentStack = ItemStack.EMPTY;
            inv.getItems().set(offsetSlot, currentStack);
            inv.markDirty();
        }
        return copy;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return 64;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        inv.getItems().set(this.slotOffset + slot, stack);
        inv.markDirty();
    }
}