package cpw.mods.ironchest.net;

import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.ironchest.ContainerIronChest;
import cpw.mods.ironchest.IronChestType;
import cpw.mods.ironchest.TileEntityIronChest;
import cpw.mods.ironchest.client.GUIChest;
import cpw.mods.ironchest.client.IronChestRenderHelper;
import cpw.mods.ironchest.client.TileEntityIronChestRenderer;

public class UniversalProxy implements IGuiHandler {
	public void registerRenderInformation() {
		TileEntityRendererChestHelper.instance = new IronChestRenderHelper();
	}

	public void registerTileEntitySpecialRenderer(IronChestType typ) {
		ClientRegistry.bindTileEntitySpecialRenderer(typ.clazz, new TileEntityIronChestRenderer());
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityIronChest) {
			TileEntityIronChest chest = (TileEntityIronChest) te;
			return new ContainerIronChest(player.inventory, chest, chest.getType(), 0, 0);
		} else
			return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && te instanceof TileEntityIronChest)
			return GUIChest.GUI.buildGUI(IronChestType.values()[ID], player.inventory, (TileEntityIronChest) te);
		else
			return null;
	}
}