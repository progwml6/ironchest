package cpw.mods.ironchest;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.ironchest.net.UniversalProxy;

@Mod(modid = IronChest.modid, name = IronChest.name)
public class IronChest {
	public static final String modid = "IronChest", name = "Iron Chests", proxyPath = "cpw.mods.ironchest.net.UniversalProxy";

	@Instance(modid)
	public static IronChest instance;

	@SidedProxy(clientSide = proxyPath, serverSide = proxyPath)
	public static UniversalProxy proxy;

	public static BlockIronChest ironChestBlock;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        Version.init(e.getVersionProperties());
        e.getModMetadata().version = Version.fullVersionString();

        for (ChestChangerType type : ChestChangerType.values())
            type.buildItem();
        ironChestBlock = new BlockIronChest();
        GameRegistry.registerBlock(ironChestBlock, ItemIronChest.class, "BlockIronChest");

        PacketHandler.INSTANCE.ordinal();
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        for (IronChestType typ : IronChestType.values())
        {
            GameRegistry.registerTileEntityWithAlternatives(typ.clazz, modid + "." + typ.name(), typ.name());
            proxy.registerTileEntitySpecialRenderer(typ);
        }
        IronChestType.registerBlocksAndRecipes(ironChestBlock);
        ChestChangerType.generateRecipes();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        proxy.registerRenderInformation();
        MinecraftForge.EVENT_BUS.register(this);
    }

}
