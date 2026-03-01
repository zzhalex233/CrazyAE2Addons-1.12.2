package com.zzhalex233.crazyae2addons.common.ae2.mob.channel;

import appeng.api.config.FuzzyMode;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.data.IAEStack;
import com.zzhalex233.crazyae2addons.common.ae2.mob.stack.MobKey;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;

public final class AEMobStack implements IAEStack<AEMobStack> {

    private final MobKey key;

    private long stackSize;
    private long requestable;
    private boolean craftable;

    public AEMobStack(MobKey key, long stackSize) {
        this.key = Objects.requireNonNull(key, "key");
        this.stackSize = Math.max(0, stackSize);
        this.requestable = 0;
        this.craftable = false;
    }

    public MobKey getKey() {
        return key;
    }


    @Override
    public void add(AEMobStack other) {
        if (other == null) return;
        if (!this.fuzzyComparison(other, FuzzyMode.IGNORE_ALL)) {
            return; 
        }
        this.stackSize += other.stackSize;
        this.requestable += other.requestable;
        this.craftable |= other.craftable;
    }

    @Override
    public long getStackSize() {
        return stackSize;
    }

    @Override
    public AEMobStack setStackSize(long v) {
        this.stackSize = Math.max(0, v);
        return this;
    }

    @Override
    public long getCountRequestable() {
        return requestable;
    }

    @Override
    public AEMobStack setCountRequestable(long v) {
        this.requestable = Math.max(0, v);
        return this;
    }

    @Override
    public boolean isCraftable() {
        return craftable;
    }

    @Override
    public AEMobStack setCraftable(boolean v) {
        this.craftable = v;
        return this;
    }

    @Override
    public AEMobStack reset() {
        return new AEMobStack(this.key, 0);
    }

    @Override
    public boolean isMeaningful() {
        return stackSize > 0 || requestable > 0 || craftable;
    }

    @Override
    public void incStackSize(long v) {
        if (v > 0) stackSize += v;
    }

    @Override
    public void decStackSize(long v) {
        if (v > 0) stackSize = Math.max(0, stackSize - v);
    }

    @Override
    public void incCountRequestable(long v) {
        if (v > 0) requestable += v;
    }

    @Override
    public void decCountRequestable(long v) {
        if (v > 0) requestable = Math.max(0, requestable - v);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setString("id", key.getEntityId().toString());
        NBTTagCompound extra = key.getExtraNbt();
        if (extra != null && !extra.isEmpty()) {
            tag.setTag("tag", extra);
        }
        tag.setLong("amt", stackSize);
        tag.setLong("req", requestable);
        tag.setBoolean("craft", craftable);
    }

    @Override
    public boolean fuzzyComparison(AEMobStack other, FuzzyMode mode) {
        if (other == null) return false;
        return this.key.equals(other.key);
    }

    @Override
    public void writeToPacket(ByteBuf buf) throws IOException {
        writeString(buf, key.getEntityId().toString());

        NBTTagCompound extra = key.getExtraNbt();
        boolean has = extra != null && !extra.isEmpty();
        buf.writeBoolean(has);
        if (has) {
            writeNBT(buf, extra);
        }

        buf.writeLong(stackSize);
        buf.writeLong(requestable);
        buf.writeBoolean(craftable);
    }

    @Override
    public AEMobStack copy() {
        AEMobStack c = new AEMobStack(this.key, this.stackSize);
        c.requestable = this.requestable;
        c.craftable = this.craftable;
        return c;
    }

    @Override
    public AEMobStack empty() {
        return new AEMobStack(this.key, 0);
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public boolean isFluid() {
        return false;
    }

    @Override
    public IStorageChannel<AEMobStack> getChannel() {
        return MobStorageChannel.INSTANCE;
    }

    @Override
    public ItemStack asItemStackRepresentation() {
        ItemStack egg = new ItemStack(net.minecraft.init.Items.SPAWN_EGG);
        NBTTagCompound root = new NBTTagCompound();
        NBTTagCompound entityTag = new NBTTagCompound();
        entityTag.setString("id", key.getEntityId().toString());
        root.setTag("EntityTag", entityTag);
        egg.setTagCompound(root);
        return egg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AEMobStack)) return false;
        AEMobStack that = (AEMobStack) o;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }


    private static void writeString(ByteBuf buf, String s) {
        byte[] bytes = s.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    private static String readString(ByteBuf buf) throws IOException {
        int len = buf.readInt();
        if (len < 0 || len > 32767) {
            throw new IOException("Bad string length: " + len);
        }
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }

    private static void writeNBT(ByteBuf buf, NBTTagCompound tag) throws IOException {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.DataOutputStream dos = new java.io.DataOutputStream(baos);
        net.minecraft.nbt.CompressedStreamTools.write(tag, dos);
        dos.flush();
        byte[] data = baos.toByteArray();
        buf.writeInt(data.length);
        buf.writeBytes(data);
    }

    private static NBTTagCompound readNBT(ByteBuf buf) throws IOException {
        int len = buf.readInt();
        if (len < 0 || len > 2_000_000) { 
            throw new IOException("Bad NBT length: " + len);
        }
        byte[] data = new byte[len];
        buf.readBytes(data);
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(data);
        java.io.DataInputStream dis = new java.io.DataInputStream(bais);
        return net.minecraft.nbt.CompressedStreamTools.read(dis);
    }

    static AEMobStack readFromPacket0(ByteBuf buf) throws IOException {
        String idStr = readString(buf);
        net.minecraft.util.ResourceLocation id = new net.minecraft.util.ResourceLocation(idStr);

        boolean has = buf.readBoolean();
        @Nullable NBTTagCompound extra = null;
        if (has) {
            extra = readNBT(buf);
        }

        long amt = buf.readLong();
        long req = buf.readLong();
        boolean craft = buf.readBoolean();

        AEMobStack s = new AEMobStack(new MobKey(id, extra), amt);
        s.requestable = req;
        s.craftable = craft;
        return s;
    }

    static AEMobStack fromNbt0(NBTTagCompound tag) {
        net.minecraft.util.ResourceLocation id = new net.minecraft.util.ResourceLocation(tag.getString("id"));
        NBTTagCompound extra = tag.hasKey("tag") ? tag.getCompoundTag("tag") : null;
        long amt = tag.getLong("amt");
        long req = tag.hasKey("req") ? tag.getLong("req") : 0;
        boolean craft = tag.hasKey("craft") && tag.getBoolean("craft");

        AEMobStack s = new AEMobStack(new MobKey(id, extra), amt);
        s.requestable = req;
        s.craftable = craft;
        return s;
    }
}
