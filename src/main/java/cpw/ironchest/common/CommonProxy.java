package cpw.ironchest.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.ironchest.common.containers.ContainerIronChest;
import cpw.ironchest.common.lib.IronChestType;
import cpw.ironchest.common.tiles.TileEntityIronChest;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	public void registerRenderInformation() {}

	public void registerTileEntitySpecialRenderer(IronChestType typ) {}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int X, int Y, int Z) {
		TileEntity te = world.getTileEntity(X, Y, Z);
		if (te != null && te instanceof TileEntityIronChest) {
			TileEntityIronChest icte = (TileEntityIronChest) te;
			return new ContainerIronChest(player.inventory, icte, icte.getType(), 0, 0);
		} else {
			return null;
		}
	}

	public World getClientWorld() {
		return null;
	}
}