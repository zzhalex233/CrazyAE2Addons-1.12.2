package com.zzhalex233.crazyae2addons;

import com.zzhalex233.crazyae2addons.common.init.ModNetwork;
import com.zzhalex233.crazyae2addons.common.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
        modid = Tags.MODID,
        name = Tags.MODNAME,
        version = Tags.VERSION,
        dependencies = "required-after:forge@[14.23.5.2847,);after:appliedenergistics2"
)
public class CrazyAE2Addons {

    @Mod.Instance(Tags.MODID)
    public static CrazyAE2Addons INSTANCE;

    @SidedProxy(
            clientSide = "com.zzhalex233.crazyae2addons.common.proxy.ClientProxy",
            serverSide = "com.zzhalex233.crazyae2addons.common.proxy.CommonProxy"
    )
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        PROXY.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModNetwork.init();
        PROXY.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(net.minecraftforge.fml.common.event.FMLServerStartingEvent event) {
        event.registerServerCommand(new com.zzhalex233.crazyae2addons.common.command.CommandCrazyAE2());
    }
}
