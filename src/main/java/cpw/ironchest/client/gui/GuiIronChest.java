package cpw.ironchest.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.ironchest.common.containers.ContainerIronChest;
import cpw.ironchest.common.lib.IronChestType;
import cpw.ironchest.common.tiles.TileEntityIronChest;

public class GuiIronChest extends GuiContainer {
	public enum ResourceList {
		IRON(new ResourceLocation("ironchest", "textures/gui/ironcontainer.png")),
		COPPER(new ResourceLocation("ironchest", "textures/gui/coppercontainer.png")),
		SILVER(new ResourceLocation("ironchest", "textures/gui/silvercontainer.png")),
		GOLD(new ResourceLocation("ironchest", "textures/gui/goldcontainer.png")),
		DIAMOND(new ResourceLocation("ironchest", "textures/gui/diamondcontainer.png")),
		DIRT(new ResourceLocation("ironchest", "textures/gui/dirtcontainer.png"));
		public final ResourceLocation location;

		private ResourceList(ResourceLocation loc) {
			this.location = loc;
		}
	}

	public enum GUI {
		IRON(184, 202, ResourceList.IRON, IronChestType.IRON),
		GOLD(184, 256, ResourceList.GOLD, IronChestType.GOLD),
		DIAMOND(238, 256, ResourceList.DIAMOND, IronChestType.DIAMOND),
		COPPER(184, 184, ResourceList.COPPER, IronChestType.COPPER),
		SILVER(184, 238, ResourceList.SILVER, IronChestType.SILVER),
		CRYSTAL(238, 256, ResourceList.DIAMOND, IronChestType.CRYSTAL),
		OBSIDIAN(238, 256, ResourceList.DIAMOND, IronChestType.OBSIDIAN),
		DIRTCHEST9000(184, 184, ResourceList.DIRT, IronChestType.DIRTCHEST9000);

		private int xSize, ySize;
		private ResourceList guiResourceList;
		private IronChestType mainType;

		private GUI(int xSize, int ySize, ResourceList guiResourceList, IronChestType mainType) {
			this.xSize = xSize;
			this.ySize = ySize;
			this.guiResourceList = guiResourceList;
			this.mainType = mainType;
		}

		protected Container makeContainer(IInventory player, IInventory chest) {
			return new ContainerIronChest(player, chest, mainType, xSize, ySize);
		}

		public static GuiIronChest buildGUI(IronChestType type, IInventory playerInventory, TileEntityIronChest chestInventory) {
			return new GuiIronChest(values()[chestInventory.getType().ordinal()], playerInventory, chestInventory);
		}
	}

	public int getRowLength() {
		return type.mainType.getRowLength();
	}

	private GUI type;

	private GuiIronChest(GUI type, IInventory player, IInventory chest) {
		super(type.makeContainer(player, chest));
		this.type = type;
		this.xSize = type.xSize;
		this.ySize = type.ySize;
		this.allowUserInput = false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}