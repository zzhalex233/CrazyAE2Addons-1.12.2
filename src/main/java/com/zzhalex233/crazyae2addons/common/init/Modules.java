package com.zzhalex233.crazyae2addons.common.init;

import com.zzhalex233.crazyae2addons.common.ae2.AE2Compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Modules {

    private static final List<IModModule> COMMON = new ArrayList<>();
    private static final List<IModModule> AE2 = new ArrayList<>();

    private Modules() {}

    public static void init() {
        COMMON.clear();
        AE2.clear();


        if (AE2Compat.isAE2Loaded()) {
            AE2.add(new com.zzhalex233.crazyae2addons.common.ae2.mob.MobModuleImpl());
        }
    }

    public static List<IModModule> commonModules() {
        return Collections.unmodifiableList(COMMON);
    }

    public static List<IModModule> ae2Modules() {
        return Collections.unmodifiableList(AE2);
    }
}
