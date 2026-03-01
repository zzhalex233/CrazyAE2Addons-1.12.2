package com.zzhalex233.crazyae2addons.common.util;

import net.minecraft.item.Item;

import java.lang.reflect.Method;

public final class ItemNaming {
    private ItemNaming() {}

    public static void setNameKey(Item item, String key) {
        if (invoke(item, "setTranslationKey", key)) return;

        if (invoke(item, "setUnlocalizedName", key)) return;

    }

    private static boolean invoke(Item item, String method, String arg) {
        try {
            Method m = Item.class.getMethod(method, String.class);
            m.invoke(item, arg);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }
}
