package com.tresher.oresorces.datagen;

import com.tresher.oresorces.OreSources;
import com.tresher.oresorces.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OreSources.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagKey<Block> createNonBreakable = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath("create", "non_breakable")
        );

        TagKey<Block> createNonMovable = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath("create", "non_movable")
        );
        TagKey<Block> simulatedNonMovable = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath("simulated", "non_movable")
        );


        Block[] sourceBlocks = new Block[]{
                ModBlocks.IRON_SOURCE_BLOCK.get(),
                ModBlocks.COPPER_SOURCE_BLOCK.get(),
                ModBlocks.GOLD_SOURCE_BLOCK.get(),
                ModBlocks.ZINC_SOURCE_BLOCK.get()
        };

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(sourceBlocks);

        tag(BlockTags.NEEDS_STONE_TOOL).add(sourceBlocks);

        tag(simulatedNonMovable).add(sourceBlocks);

        tag(createNonBreakable).add(sourceBlocks);

        tag(createNonMovable).add(sourceBlocks);

    }
}
