package com.zzhalex233.crazyae2addons.client;

import com.zzhalex233.crazyae2addons.CrazyAE2Addons;
import com.zzhalex233.crazyae2addons.Tags;
import com.zzhalex233.crazyae2addons.common.ae2.mob.registry.ModItemsMob;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Tags.MODID, value = Side.CLIENT)
public final class ModelRegistration {

    private ModelRegistration() {}

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        register(ModItemsMob.MOB_CELL_1K);
        register(ModItemsMob.MOB_CELL_4K);
        register(ModItemsMob.MOB_CELL_16K);
        register(ModItemsMob.MOB_CELL_256K);
        register(ModItemsMob.MOB_CELL_HOUSING);
    }

    private static void register(Item item) {
        if (item == null || item.getRegistryName() == null) {
            return;
        }

        ModelLoader.setCustomModelResourceLocation(
                item,
                0,
                new ModelResourceLocation(item.getRegistryName(), "inventory")
        );
    }
}
