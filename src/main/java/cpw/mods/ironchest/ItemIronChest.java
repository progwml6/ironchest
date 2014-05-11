package cpw.mods.ironchest;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemIronChest extends ItemBlock {
	public ItemIronChest(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int i) {
		return IronChestType.validateMeta(i);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return "tile.ironchest:" + IronChestType.values()[itemstack.getItemDamage()].name();
	}
}