package com.zzhalex233.crazyae2addons.common.ae2.mob.stack;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public final class MobStack {

    private final MobKey key;
    private long amount;

    public MobStack(MobKey key, long amount) {
        this.key = Objects.requireNonNull(key, "key");
        this.amount = Math.max(0, amount);
    }

    public MobKey getKey() {
        return key;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = Math.max(0, amount);
    }

    public NBTTagCompound toNbt() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("key", key.toNbt());
        tag.setLong("amt", amount);
        return tag;
    }

    public static MobStack fromNbt(NBTTagCompound tag) {
        MobKey key = MobKey.fromNbt(tag.getCompoundTag("key"));
        long amt = tag.getLong("amt");
        return new MobStack(key, amt);
    }

    @Override
    public String toString() {
        return "MobStack{key=" + key + ", amount=" + amount + '}';
    }
}
