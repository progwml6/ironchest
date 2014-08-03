package cpw.mods.ironchest;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
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
    public static boolean CACHE_RENDER = true;
    public static boolean OCELOTS_SITONCHESTS = true;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Version.init(event.getVersionProperties());
        event.getModMetadata().version = Version.fullVersionString();
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        try
        {
            cfg.load();
            ChestChangerType.buildItems(cfg);
            CACHE_RENDER = cfg.get(Configuration.CATEGORY_GENERAL, "cacheRenderingInformation", true).getBoolean(true);
            OCELOTS_SITONCHESTS = cfg.get(Configuration.CATEGORY_GENERAL, "ocelotsSitOnChests", true).getBoolean(true);
        }
        catch (Exception e)
        {
            FMLLog.log(Level.ERROR, e, "IronChest has a problem loading its configuration");
        }
        finally
        {
            if (cfg.hasChanged())
                cfg.save();
        }
        ironChestBlock = new BlockIronChest();
        GameRegistry.registerBlock(ironChestBlock, ItemIronChest.class, "BlockIronChest");
        PacketHandler.INSTANCE.ordinal();
    }

    @EventHandler
    public void load(FMLInitializationEvent evt)
    {
        for (IronChestType typ : IronChestType.values())
        {
            GameRegistry.registerTileEntityWithAlternatives(typ.clazz, "IronChest."+typ.name(), typ.name());
            proxy.registerTileEntitySpecialRenderer(typ);
        }
        IronChestType.registerBlocksAndRecipes(ironChestBlock);
        ChestChangerType.generateRecipes();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        proxy.registerRenderInformation();
        MinecraftForge.EVENT_BUS.register(this);
    }

}
