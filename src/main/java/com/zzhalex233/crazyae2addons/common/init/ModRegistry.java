package com.zzhalex233.crazyae2addons.common.init;

import com.zzhalex233.crazyae2addons.Tags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public final class ModRegistry {

    private ModRegistry() {}

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        for (IModModule m : Modules.commonModules()) m.registerBlocks(event.getRegistry());
        for (IModModule m : Modules.ae2Modules()) m.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        for (IModModule m : Modules.commonModules()) m.registerItems(event.getRegistry());
        for (IModModule m : Modules.ae2Modules()) m.registerItems(event.getRegistry());
    }
}
