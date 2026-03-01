package com.zzhalex233.crazyae2addons.common.ae2;

import com.zzhalex233.crazyae2addons.common.ae2.mob.MobAe2Registration;
import com.zzhalex233.crazyae2addons.common.ae2.mob.MobModule;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public final class AE2Module {

    private AE2Module() {}

    public static void preInit(FMLPreInitializationEvent event) {
        com.zzhalex233.crazyae2addons.common.util.ModLog.LOG.info("[CrazyAE2Addons] AE2Module.preInit");
        com.zzhalex233.crazyae2addons.common.ae2.AE2Registries.registerAll();
        MobModule.preInit(event);
    }

    public static void init(FMLInitializationEvent event) {
        com.zzhalex233.crazyae2addons.common.util.ModLog.LOG.info("[CrazyAE2Addons] AE2Module.init");
        MobModule.init(event);
        MobAe2Registration.registerInit();
    }
}