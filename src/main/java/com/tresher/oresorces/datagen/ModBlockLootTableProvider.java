package com.tresher.oresorces.datagen;

import com.tresher.oresorces.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.Set;
import java.util.stream.Stream;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    protected LootTable.Builder createSourceMultipleOreDrop(Block block, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                block,
                LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops,maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE))
                )
        );
    }
    protected LootTable.Builder createSourceOreDrop(Block block, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                block,
                LootItem.lootTableItem(item)
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE))
                )
        );
    }

    @Override
    protected void generate() {
        //dropSelf(ModBlocks.COPPER_SOURCE_BLOCK); //если таблицу лута надо

        add(ModBlocks.COPPER_SOURCE_BLOCK.get(),
                block-> createSourceMultipleOreDrop(Blocks.COPPER_ORE,Items.RAW_COPPER, 2,5));
        add(ModBlocks.IRON_SOURCE_BLOCK.get(),
                block-> createSourceOreDrop(Blocks.IRON_ORE,Items.RAW_IRON));
        add(ModBlocks.GOLD_SOURCE_BLOCK.get(),
                block-> createSourceOreDrop(Blocks.GOLD_ORE,Items.RAW_GOLD));
        add(ModBlocks.COAL_SOURCE_BLOCK.get(),
                block-> createSourceOreDrop(Blocks.COAL_ORE,Items.COAL));
        add(ModBlocks.DIAMOND_SOURCE_BLOCK.get(),
                block-> createSourceOreDrop(Blocks.DIAMOND_ORE,Items.DIAMOND));
        add(ModBlocks.EMERALD_SOURCE_BLOCK.get(),
                block-> createSourceOreDrop(Blocks.EMERALD_ORE,Items.EMERALD));
        add(ModBlocks.NETHER_QUARTZ_SOURCE_BLOCK.get(),
                block-> createSourceOreDrop(Blocks.NETHER_QUARTZ_ORE,Items.QUARTZ));
        add(ModBlocks.LAPIS_SOURCE_BLOCK.get(),
                block-> createSourceMultipleOreDrop(Blocks.LAPIS_ORE,Items.LAPIS_LAZULI, 4,9));
        add(ModBlocks.ANCIENT_DEBRIS_SOURCE_BLOCK.get(),
                block-> createSourceOreDrop(Blocks.ANCIENT_DEBRIS,Items.NETHERITE_SCRAP));
        add(ModBlocks.NETHER_GOLD_SOURCE_BLOCK.get(),
                block-> createSourceMultipleOreDrop(Blocks.NETHER_GOLD_ORE,Items.GOLD_NUGGET,2,6));

        add(ModBlocks.REDSTONE_SOURCE_BLOCK.get(),
                block-> createSourceMultipleOreDrop(Blocks.REDSTONE_ORE,Items.REDSTONE, 4,5));


        Item rawZinc = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("create", "raw_zinc"));
        Block zinc_ore = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "zinc_ore"));
        add(ModBlocks.ZINC_SOURCE_BLOCK.get(),
                block -> createSourceOreDrop(zinc_ore,rawZinc));



    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
