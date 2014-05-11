package cpw.mods.ironchest.client;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import com.google.common.collect.Maps;

import cpw.mods.ironchest.IronChest;
import cpw.mods.ironchest.IronChestType;
import cpw.mods.ironchest.TileEntityIronChest;

public class IronChestRenderHelper extends TileEntityRendererChestHelper {
	private Map<Integer, TileEntityIronChest> itemRenders = Maps.newHashMap();

	public IronChestRenderHelper() {
		for (IronChestType typ : IronChestType.values())
			itemRenders.put(typ.ordinal(), (TileEntityIronChest) IronChest.ironChestBlock.createTileEntity(null, typ.ordinal()));
	}

	@Override
	public void renderChest(Block block, int i, float f) {
		if (block == IronChest.ironChestBlock) {
			TileEntityRendererDispatcher.instance.renderTileEntityAt(itemRenders.get(i), 0.0D, 0.0D, 0.0D, 0.0F);
		} else {
			super.renderChest(block, i, f);
		}
	}
}