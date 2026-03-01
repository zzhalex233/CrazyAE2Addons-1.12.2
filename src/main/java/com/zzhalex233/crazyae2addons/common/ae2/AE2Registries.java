package com.zzhalex233.crazyae2addons.common.ae2;

import com.zzhalex233.crazyae2addons.common.util.ModLog;

public final class AE2Registries {

    private static boolean registered = false;

    private AE2Registries() {}

    public static void registerAll() {
        if (registered) {
            return;
        }
        registered = true;

        ModLog.LOG.info("[CrazyAE2Addons] AE2Registries.registerAll (begin)");

        com.zzhalex233.crazyae2addons.common.ae2.mob.MobAe2Registration.registerAll();

        ModLog.LOG.info("[CrazyAE2Addons] AE2Registries.registerAll (end)");
    }
}
