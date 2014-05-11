package cpw.ironchest.common.containers;

import cpw.ironchest.common.lib.IronChestType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ValidatingSlot extends Slot {
	private IronChestType type;

	public ValidatingSlot(IInventory inv, int i, int j, int k, IronChestType type) {
		super(inv, i, j, k);
		this.type = type;
	}

	@Override
	public boolean isItemValid(ItemStack is) {
		return type.acceptsStack(is);
	}
}