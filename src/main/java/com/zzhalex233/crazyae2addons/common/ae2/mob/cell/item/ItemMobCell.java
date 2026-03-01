package com.zzhalex233.crazyae2addons.common.ae2.mob.cell.item;

import appeng.api.AEApi;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;
import appeng.api.storage.data.IItemList;
import appeng.api.util.IClientHelper;
import appeng.util.Platform;
import com.zzhalex233.crazyae2addons.common.ae2.mob.cell.MobCellTier;
import com.zzhalex233.crazyae2addons.common.ae2.mob.channel.AEMobStack;
import com.zzhalex233.crazyae2addons.common.ae2.mob.channel.IMobStorageChannel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMobCell extends Item implements appeng.api.implementations.items.IStorageCell<AEMobStack> {

    private final MobCellTier tier;

    public ItemMobCell(MobCellTier tier) {
        this.tier = tier;
        this.setMaxStackSize(1);
    }

    public MobCellTier getTier() {
        return tier;
    }


    @Override
    public int getBytes(ItemStack cellItem) {
        return tier.totalBytes();
    }

    @Override
    public int getBytesPerType(ItemStack cellItem) {
        return tier.bytesPerType();
    }

    @Override
    public int getTotalTypes(ItemStack cellItem) {
        return 63;
    }

    @Override
    public boolean isBlackListed(ItemStack cellItem, AEMobStack requestedAddition) {
        return false;
    }

    @Override
    public boolean storableInStorageCell() {
        return false;
    }

    @Override
    public boolean isStorageCell(ItemStack i) {
        return true;
    }

    @Override
    public double getIdleDrain() {
        return tier.idleDrain();
    }

    @Override
    public IStorageChannel<AEMobStack> getChannel() {
        return AEApi.instance().storage().getStorageChannel(IMobStorageChannel.class);
    }


    @Override
    public boolean isEditable(ItemStack is) {
        return true;
    }

    @Override
    public IItemHandler getUpgradesInventory(ItemStack is) {
        return new appeng.items.contents.CellUpgrades(is, 2);
    }

    @Override
    public IItemHandler getConfigInventory(ItemStack is) {
        return new appeng.items.contents.CellConfig(is);
    }

    @Override
    public appeng.api.config.FuzzyMode getFuzzyMode(ItemStack is) {
        NBTTagCompound tag = Platform.openNbtData(is);
        String v = tag.getString("FuzzyMode");
        try {
            return appeng.api.config.FuzzyMode.valueOf(v);
        } catch (Throwable ignored) {
            return appeng.api.config.FuzzyMode.IGNORE_ALL;
        }
    }

    @Override
    public void setFuzzyMode(ItemStack is, appeng.api.config.FuzzyMode fzMode) {
        NBTTagCompound tag = Platform.openNbtData(is);
        tag.setString("FuzzyMode", fzMode.name());
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        try {
            IClientHelper ch = AEApi.instance().client();
            IStorageChannel<AEMobStack> channel = getChannel();
            ICellInventoryHandler<AEMobStack> inv = AEApi.instance().registries().cell()
                    .getCellInventory(stack, null, channel);
            ch.addCellInformation(inv, tooltip);
        } catch (Throwable t) {
        }
    }


    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 0;
    }
}
