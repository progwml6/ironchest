package cpw.ironchest.common.blocks;

import cpw.ironchest.common.lib.IronChestType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockIronChestItem extends ItemBlock {
	public BlockIronChestItem(Block block) {
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