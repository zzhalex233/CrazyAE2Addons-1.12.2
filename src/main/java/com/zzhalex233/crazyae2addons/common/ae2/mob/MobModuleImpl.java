package com.zzhalex233.crazyae2addons.common.ae2.mob;

import com.zzhalex233.crazyae2addons.common.ae2.mob.cell.ItemMobCellHousing;
import com.zzhalex233.crazyae2addons.common.ae2.mob.cell.MobCellTier;
import com.zzhalex233.crazyae2addons.common.ae2.mob.cell.item.ItemMobCell;
import com.zzhalex233.crazyae2addons.common.ae2.mob.registry.ModItemsMob;
import com.zzhalex233.crazyae2addons.common.init.IModModule;
import com.zzhalex233.crazyae2addons.common.util.ItemNaming;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public final class MobModuleImpl implements IModModule {

    @Override
    public void registerItems(IForgeRegistry<Item> registry) {
        ItemMobCellHousing cell_housing = new ItemMobCellHousing();
        cell_housing.setRegistryName(ModItemsMob.rl("mob_cell_housing"));
        ItemNaming.setNameKey(cell_housing, "mob_cell_housing");
        registry.register(cell_housing);
        ModItemsMob.MOB_CELL_HOUSING = cell_housing;

        ModItemsMob.MOB_CELL_1K   = registerCell(registry, MobCellTier.K1);
        ModItemsMob.MOB_CELL_4K   = registerCell(registry, MobCellTier.K4);
        ModItemsMob.MOB_CELL_16K  = registerCell(registry, MobCellTier.K16);
        ModItemsMob.MOB_CELL_64K  = registerCell(registry, MobCellTier.K64);
        ModItemsMob.MOB_CELL_256K = registerCell(registry, MobCellTier.K256);
    }

    private static Item registerCell(IForgeRegistry<Item> registry, MobCellTier tier) {
        ItemMobCell cell = new ItemMobCell(tier);
        cell.setRegistryName(ModItemsMob.rl(tier.idPath));
        ItemNaming.setNameKey(cell, tier.idPath);
        registry.register(cell);
        return cell;
    }
}