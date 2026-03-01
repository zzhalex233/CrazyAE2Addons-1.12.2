package com.zzhalex233.crazyae2addons.common.ae2.mob.cell.item;

import com.zzhalex233.crazyae2addons.common.ae2.mob.cell.MobCellTier;
import net.minecraft.item.Item;

public class ItemMobStorageCell extends Item {

    private final MobCellTier tier;

    public ItemMobStorageCell(MobCellTier tier) {
        super();
        this.tier = tier;
        this.setMaxStackSize(1); 
    }

    public MobCellTier getTier() {
        return tier;
    }
}
