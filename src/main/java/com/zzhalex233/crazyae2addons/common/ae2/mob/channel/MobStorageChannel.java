package com.zzhalex233.crazyae2addons.common.ae2.mob.channel;

import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IItemList;
import com.zzhalex233.crazyae2addons.common.util.ModLog;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;

public final class MobStorageChannel implements IMobStorageChannel {

    public static final MobStorageChannel INSTANCE = new MobStorageChannel();

    private MobStorageChannel() {}

    @Override
    public int transferFactor() {
        return 1;
    }

    @Override
    public int getUnitsPerByte() {
        return 1;
    }

    @Override
    public IItemList<AEMobStack> createList() {
        return new SimpleMobItemList();
    }

    @Override
    public AEMobStack createStack(Object input) {
        if (input instanceof AEMobStack) {
            return ((AEMobStack) input).copy();
        }
        if (input instanceof NBTTagCompound) {
            return createFromNBT((NBTTagCompound) input);
        }
        return null;
    }

    @Override
    public AEMobStack readFromPacket(ByteBuf buf) throws IOException {
        return AEMobStack.readFromPacket0(buf);
    }

    @Override
    public AEMobStack createFromNBT(NBTTagCompound tag) {
        return AEMobStack.fromNbt0(tag);
    }
}
