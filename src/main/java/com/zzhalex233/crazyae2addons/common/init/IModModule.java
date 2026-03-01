package com.zzhalex233.crazyae2addons.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public interface IModModule {

    default void registerBlocks(IForgeRegistry<Block> registry) {}

    default void registerItems(IForgeRegistry<Item> registry) {}

    default void registerTileEntities() {}
}
