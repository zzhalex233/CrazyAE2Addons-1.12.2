package com.zzhalex233.crazyae2addons.common.ae2.mob;

import appeng.api.AEApi;
import appeng.api.storage.IStorageHelper;
import com.zzhalex233.crazyae2addons.common.ae2.mob.channel.IMobStorageChannel;
import com.zzhalex233.crazyae2addons.common.ae2.mob.channel.MobStorageChannel;
import com.zzhalex233.crazyae2addons.common.util.ModLog;

public final class MobAe2Registration {

    private MobAe2Registration() {}

    private static boolean channelRegistered = false;


    public static void registerAll() {
        ModLog.LOG.info("[CrazyAE2Addons] MobAe2Registration.registerAll (begin)");
        registerPreInit(); 
        ModLog.LOG.info("[CrazyAE2Addons] MobAe2Registration.registerAll (end)");
    }

    public static void registerPreInit() {
        if (channelRegistered) {
            ModLog.LOG.info("[CrazyAE2Addons] MobAe2Registration.registerPreInit: already registered, skip");
            return;
        }

        ModLog.LOG.info("[CrazyAE2Addons] MobAe2Registration.registerPreInit (begin)");

        final IStorageHelper storage = AEApi.instance().storage();

        storage.registerStorageChannel(IMobStorageChannel.class, MobStorageChannel.INSTANCE);
        channelRegistered = true;

        final IMobStorageChannel got = storage.getStorageChannel(IMobStorageChannel.class);
        ModLog.LOG.info("[CrazyAE2Addons] MobStorageChannel registered = {}, gotClass={}",
                got == MobStorageChannel.INSTANCE,
                got == null ? "null" : got.getClass().getName());

        ModLog.LOG.info("[CrazyAE2Addons] AE2 storageChannels size = {}", storage.storageChannels().size());

        ModLog.LOG.info("[CrazyAE2Addons] MobAe2Registration.registerPreInit (end)");
    }

    public static void registerInit() {
        if (!channelRegistered) {
            ModLog.LOG.warn("[CrazyAE2Addons] MobAe2Registration.registerInit: channel not registered yet, doing it now");
            registerPreInit();
        }
    }
}


