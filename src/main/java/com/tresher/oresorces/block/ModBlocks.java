package com.tresher.oresorces.block;


import com.tresher.oresorces.OreSources;
import com.tresher.oresorces.block.custom.Source_block;
import com.tresher.oresorces.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(OreSources.MOD_ID);

    public static final DeferredBlock<Block> IRON_SOURCE_BLOCK = registerBLock("iron_source_block",
        () -> new Source_block(BlockBehaviour.Properties.of()
                //.strength(-1.0F, 3600000.0F)
                .strength(3f, 3600000.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)
                .randomTicks()
    ));
    public static final DeferredBlock<Block> COPPER_SOURCE_BLOCK = registerBLock("copper_source_block",
        () -> new Source_block(BlockBehaviour.Properties.of()
                //.strength(-1.0F, 3600000.0F)
                .strength(3f, 3600000.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)
                .randomTicks()
    ));
    public static final DeferredBlock<Block> ZINC_SOURCE_BLOCK = registerBLock("zinc_source_block",
            () -> new Source_block(BlockBehaviour.Properties.of()
                    //.strength(-1.0F, 3600000.0F)
                    .strength(3f, 3600000.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .randomTicks()
            ));
    public static final DeferredBlock<Block> GOLD_SOURCE_BLOCK = registerBLock("gold_source_block",
            () -> new Source_block(BlockBehaviour.Properties.of()
                    //.strength(-1.0F, 3600000.0F)
                    .strength(3f, 3600000.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
                    .randomTicks()
            ));


    private static <T extends Block> DeferredBlock<T> registerBLock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name,() -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
