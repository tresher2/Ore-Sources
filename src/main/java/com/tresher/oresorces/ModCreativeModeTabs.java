package com.tresher.oresorces;

import com.tresher.oresorces.block.ModBlocks;
import com.tresher.oresorces.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB=
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OreSources.MOD_ID);

    public static final Supplier<CreativeModeTab> ORESOURCES_MOD_TAB = CREATIVE_MODE_TAB.register("oresources_mod_tab",
            () -> CreativeModeTab.builder()
                    //.withTabsBefore(ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID,"предыдущее имени окно,но тут его нет"))
                    .icon(()->new ItemStack(ModBlocks.COPPER_SOURCE_BLOCK.get()))//я верю
                    .title(Component.translatable("creativetab.oresources.oresources_mod"))
                    .displayItems((itemDisplayParametrs, output)->{
                        output.accept(ModItems.STRANGEITEM);
                        output.accept(ModBlocks.COPPER_SOURCE_BLOCK);
                        output.accept(ModBlocks.IRON_SOURCE_BLOCK);
                        output.accept(ModBlocks.GOLD_SOURCE_BLOCK);
                        output.accept(ModBlocks.REDSTONE_SOURCE_BLOCK);

                        if (ModList.get().isLoaded("create"))
                            output.accept(ModBlocks.ZINC_SOURCE_BLOCK.get());

                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
