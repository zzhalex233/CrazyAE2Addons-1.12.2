package com.zzhalex233.crazyae2addons.common.ae2.mob.stack;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;

public final class MobKey {

    private final ResourceLocation entityId;
    @Nullable
    private final NBTTagCompound extraNbt; 

    public MobKey(ResourceLocation entityId, @Nullable NBTTagCompound extraNbt) {
        this.entityId = Objects.requireNonNull(entityId, "entityId");
        this.extraNbt = extraNbt == null ? null : extraNbt.copy();
    }

    public ResourceLocation getEntityId() {
        return entityId;
    }

    @Nullable
    public NBTTagCompound getExtraNbt() {
        return extraNbt == null ? null : extraNbt.copy();
    }

    public NBTTagCompound toNbt() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("id", entityId.toString());
        if (extraNbt != null && !extraNbt.isEmpty()) {
            tag.setTag("tag", extraNbt.copy());
        }
        return tag;
    }

    public static MobKey fromNbt(NBTTagCompound tag) {
        ResourceLocation id = new ResourceLocation(tag.getString("id"));
        NBTTagCompound extra = tag.hasKey("tag") ? tag.getCompoundTag("tag") : null;
        return new MobKey(id, extra);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MobKey)) return false;
        MobKey mobKey = (MobKey) o;
        return entityId.equals(mobKey.entityId) &&
                Objects.equals(extraNbt, mobKey.extraNbt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, extraNbt);
    }

    @Override
    public String toString() {
        return "MobKey{" +
                "entityId=" + entityId +
                ", extraNbt=" + (extraNbt == null ? "{}" : extraNbt.toString()) +
                '}';
    }


    public static MobKey fromNBT(NBTTagCompound tag) {
        return fromNbt(tag);
    }

    public void writeToNBT(NBTTagCompound out) {
        if (out == null) return;
        out.setString("id", this.entityId.toString());
        if (this.extraNbt != null && !this.extraNbt.isEmpty()) {
            out.setTag("tag", this.extraNbt.copy());
        } else {
            if (out.hasKey("tag")) {
                out.removeTag("tag");
            }
        }
    }

    public static MobKey fromStack(com.zzhalex233.crazyae2addons.common.ae2.mob.channel.AEMobStack stack) {
        return stack == null ? null : stack.getKey();
    }

    public com.zzhalex233.crazyae2addons.common.ae2.mob.channel.AEMobStack toAEMobStack(long amount) {
        return new com.zzhalex233.crazyae2addons.common.ae2.mob.channel.AEMobStack(this, amount);
    }
}
