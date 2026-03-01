package com.zzhalex233.crazyae2addons.common.ae2.mob.registry;

import com.zzhalex233.crazyae2addons.Tags;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public final class ModItemsMob {

    private ModItemsMob() {}

    public static Item MOB_CELL_HOUSING;

    public static Item MOB_CELL_1K;
    public static Item MOB_CELL_4K;
    public static Item MOB_CELL_16K;
    public static Item MOB_CELL_64K;
    public static Item MOB_CELL_256K; 

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(Tags.MODID, path);
    }
}
