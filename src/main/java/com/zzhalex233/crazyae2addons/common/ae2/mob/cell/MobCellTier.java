package com.zzhalex233.crazyae2addons.common.ae2.mob.cell;

public enum MobCellTier {
    K1  ("mob_storage_cell_1k",   1),
    K4  ("mob_storage_cell_4k",   4),
    K16 ("mob_storage_cell_16k", 16),
    K64 ("mob_storage_cell_64k", 64),
    K256("mob_storage_cell_256k",256);

    public final String idPath;
    public final int kilobytes;

    MobCellTier(String idPath, int kilobytes) {
        this.idPath = idPath;
        this.kilobytes = kilobytes;
    }

    public int totalBytes() {
        return this.kilobytes * 1024;
    }

    public int bytesPerType() {
        return this.kilobytes * 8;
    }

    public double idleDrain() {
        switch (this) {
            case K1:  return 0.5D;
            case K4:  return 1.0D;
            case K16: return 1.5D;
            case K64: return 2.0D;
            case K256:return 3.0D;
            default:  return 0.0D;
        }
    }
}