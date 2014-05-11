package cpw.ironchest.client;

import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.ironchest.client.gui.GuiIronChest;
import cpw.ironchest.client.renderers.tile.IronChestRenderHelper;
import cpw.ironchest.client.renderers.tile.TileEntityIronChestRenderer;
import cpw.ironchest.common.CommonProxy;
import cpw.ironchest.common.lib.IronChestType;
import cpw.ironchest.common.tiles.TileEntityIronChest;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderInformation() {
		TileEntityRendererChestHelper.instance = new IronChestRenderHelper();
	}

	@Override
	public void registerTileEntitySpecialRenderer(IronChestType typ) {
		ClientRegistry.bindTileEntitySpecialRenderer(typ.clazz, new TileEntityIronChestRenderer());
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityIronChest) {
			return GuiIronChest.GUI.buildGUI(IronChestType.values()[ID], player.inventory, (TileEntityIronChest) te);
		} else {
			return null;
		}
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
}