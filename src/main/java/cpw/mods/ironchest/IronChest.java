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

@Mod(modid = "IronChest", name = "Iron Chests", dependencies = "required-after:Forge@[10.10,);required-after:FML@[7.2,)")
public class IronChest {
    public static BlockIronChest ironChestBlock;

    @SidedProxy(clientSide = "cpw.mods.ironchest.client.ClientProxy", serverSide = "cpw.mods.ironchest.CommonProxy")
    public static CommonProxy proxy;

    @Instance("IronChest")
    public static IronChest instance;

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
            GameRegistry.registerTileEntityWithAlternatives(typ.clazz, "IronChest." + typ.name(), typ.name());
            proxy.registerTileEntitySpecialRenderer(typ);
        }
        IronChestType.registerBlocksAndRecipes(ironChestBlock);
        ChestChangerType.generateRecipes();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        proxy.registerRenderInformation();
        MinecraftForge.EVENT_BUS.register(this);
    }

}
