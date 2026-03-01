package com.zzhalex233.crazyae2addons.common.ae2;

import net.minecraftforge.fml.common.Loader;

public final class AE2Compat {

    public static final String AE2_MODID = "appliedenergistics2";

    private AE2Compat() {}

    public static boolean isAE2Loaded() {
        return Loader.isModLoaded(AE2_MODID);
    }
}
