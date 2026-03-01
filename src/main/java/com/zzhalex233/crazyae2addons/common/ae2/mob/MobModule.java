package com.zzhalex233.crazyae2addons.common.ae2.mob;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import com.zzhalex233.crazyae2addons.common.util.ModLog;

public final class MobModule {

    private MobModule() {}

    public static void preInit(FMLPreInitializationEvent event) {
        ModLog.LOG.info("[CrazyAE2Addons] MobModule.preInit");
    }

    public static void init(FMLInitializationEvent event) {
        ModLog.LOG.info("[CrazyAE2Addons] MobModule.init");
    }
}
