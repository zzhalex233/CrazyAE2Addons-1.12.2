package com.zzhalex233.crazyae2addons.common.proxy;

import com.zzhalex233.crazyae2addons.common.ae2.AE2Compat;
import com.zzhalex233.crazyae2addons.common.ae2.AE2Module;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        com.zzhalex233.crazyae2addons.common.init.Modules.init();

        for (com.zzhalex233.crazyae2addons.common.init.IModModule m : com.zzhalex233.crazyae2addons.common.init.Modules.commonModules()) {
            m.registerTileEntities();
        }
        for (com.zzhalex233.crazyae2addons.common.init.IModModule m : com.zzhalex233.crazyae2addons.common.init.Modules.ae2Modules()) {
            m.registerTileEntities();
        }

        boolean ae2 = AE2Compat.isAE2Loaded();
        event.getModLog().info("[CrazyAE2Addons] AE2 loaded = {}", ae2);

        if (ae2) {
            AE2Module.preInit(event);
        }
    }

    public void init(FMLInitializationEvent event) {
        if (AE2Compat.isAE2Loaded()) {
            AE2Module.init(event);
        }
    }

    public void postInit(FMLPostInitializationEvent event) {}
}