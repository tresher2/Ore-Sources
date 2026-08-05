package com.tresher.oresorces.block.entity;
import com.tresher.oresorces.OreSources;
import com.tresher.oresorces.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, OreSources.MOD_ID);

    public static final Supplier<BlockEntityType<Source_blockEntity>> SOURCE_BLOCK_BE=
            BLOCK_ENTITIES.register("source_block_be",()->BlockEntityType.Builder.of(
                    Source_blockEntity::new,
                    ModBlocks.COAL_SOURCE_BLOCK.get(),
                    ModBlocks.IRON_SOURCE_BLOCK.get(),
                    ModBlocks.COPPER_SOURCE_BLOCK.get(),
                    ModBlocks.GOLD_SOURCE_BLOCK.get(),
                    ModBlocks.DIAMOND_SOURCE_BLOCK.get(),
                    ModBlocks.EMERALD_SOURCE_BLOCK.get(),
                    ModBlocks.NETHER_QUARTZ_SOURCE_BLOCK.get(),
                    ModBlocks.LAPIS_SOURCE_BLOCK.get(),
                    ModBlocks.NETHER_GOLD_SOURCE_BLOCK.get(),
                    ModBlocks.ZINC_SOURCE_BLOCK.get(),
                    ModBlocks.ANCIENT_DEBRIS_SOURCE_BLOCK.get()
            ).build(null));
    
    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
