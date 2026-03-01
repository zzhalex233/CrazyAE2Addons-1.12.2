package com.zzhalex233.crazyae2addons.common.init;

import com.zzhalex233.crazyae2addons.Tags;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class ModNetwork {

    public static SimpleNetworkWrapper CHANNEL;

    private ModNetwork() {}

    public static void init() {
        CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MODID);

    }
}
