package com.tresher.oresorces.block;


import com.tresher.oresorces.OreSources;
import com.tresher.oresorces.block.custom.copper_source_block;
import com.tresher.oresorces.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.IRON_ORE;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(OreSources.MOD_ID);


    public static final DeferredBlock<Block> IRON_SOURCE_BLOCK = registerBLock("iron_source_block",
        () -> new Block(BlockBehaviour.Properties.ofLegacyCopy(IRON_ORE)
    ));
    /*public static final DeferredBlock<Block> IRON_SOURCE_BLOCK = registerBLock("iron_source_block",
        () -> new Block(BlockBehaviour.Properties.of()
            .strength(3.0F, 3.0F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
    ));*/
    public static final DeferredBlock<Block> COPPER_SOURCE_BLOCK = registerBLock("copper_source_block",
        () -> new copper_source_block(BlockBehaviour.Properties.of()
                .strength(3.0F, 3.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)
    ));

    /*
    public static final Block IRON_ORE = register(
        "iron_ore",
        new DropExperienceBlock(
            ConstantInt.of(0),
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 3.0F)
        )
    );*/
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
