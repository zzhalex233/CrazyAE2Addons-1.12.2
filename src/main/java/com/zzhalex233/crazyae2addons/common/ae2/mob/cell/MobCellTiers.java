package com.zzhalex233.crazyae2addons.common.ae2.mob.cell;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public final class MobCellTiers {

    private MobCellTiers() {}

    public static final int BYTES_PER_TYPE = 8;

    public static long getTotalTypesForBytes(long totalBytes) {
        if (totalBytes <= 1024) return 8;
        if (totalBytes <= 4096) return 32;
        if (totalBytes <= 16384) return 64;
        if (totalBytes <= 65536) return 128;
        if (totalBytes <= 262144) return 256;
        return 1024;
    }

    public static long getTotalBytes(ItemStack stack) {
        if (stack == null || stack.isEmpty() || stack.getItem() == null || stack.getItem().getRegistryName() == null) {
            return 0;
        }
        String path = stack.getItem().getRegistryName().getPath(); 
        long parsed = parseFromPath(path);
        return parsed > 0 ? parsed : 0;
    }

    private static long parseFromPath(@Nullable String path) {
        if (path == null) return 0;
        if (!path.startsWith("mob_cell_")) return 0;

        String tail = path.substring("mob_cell_".length()).toLowerCase(); 
        if (tail.endsWith("k")) {
            String num = tail.substring(0, tail.length() - 1);
            long k = safeParseLong(num);
            return k > 0 ? (k * 1024L) : 0;
        }

        if (tail.endsWith("mb")) {
            String num = tail.substring(0, tail.length() - 2);
            long mb = safeParseLong(num);
            return mb > 0 ? (mb * 1024L * 1024L) : 0;
        }

        return 0;
    }

    private static long safeParseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (Throwable t) {
            return 0;
        }
    }
}