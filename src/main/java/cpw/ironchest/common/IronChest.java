package cpw.ironchest.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import cpw.ironchest.common.blocks.BlockIronChest;
import cpw.ironchest.common.blocks.BlockIronChestItem;
import cpw.ironchest.common.lib.ChestChangerType;
import cpw.ironchest.common.lib.IronChestType;
import cpw.ironchest.common.lib.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = IronChest.modid, name = IronChest.name, dependencies = "required-after:Forge@[7.0,);required-after:FML@[5.0.5,)")
public class IronChest {
	public static final String modid = "IronChest", name = "Iron Chests";

	public static BlockIronChest ironChestBlock;

	public static CreativeTabs tabIronChest = new CreativeTabs(modid) {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ironChestBlock);
		}
	};

	@SidedProxy(clientSide = "cpw.ironchest.client.ClientProxy", serverSide = "cpw.ironchest.CommonProxy")
	public static CommonProxy proxy;

	@Instance(modid)
	public static IronChest instance;

	@EventHandler
	public void load(FMLInitializationEvent e) {
		ChestChangerType.buildItems();

		ironChestBlock = new BlockIronChest();
		GameRegistry.registerBlock(ironChestBlock, BlockIronChestItem.class, "BlockIronChest");

		for (IronChestType typ : IronChestType.values()) {
			GameRegistry.registerTileEntityWithAlternatives(typ.clazz, "IronChest." + typ.name(), typ.name());
			proxy.registerTileEntitySpecialRenderer(typ);
		}

		IronChestType.registerBlocksAndRecipes(ironChestBlock);
		ChestChangerType.generateRecipes();

		PacketHandler.INSTANCE.ordinal();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		proxy.registerRenderInformation();
		MinecraftForge.EVENT_BUS.register(this);
	}
}