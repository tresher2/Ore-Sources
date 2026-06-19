package com.tresher.oresorces.datagen;

import com.tresher.oresorces.OreSources;
import com.tresher.oresorces.block.ModBlocks;
import com.tresher.oresorces.block.custom.Source_block;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OreSources.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //blockWithItem(ModBlocks.COPPER_SOURCE_BLOCK);
        //blockWithItem(ModBlocks.IRON_SOURCE_BLOCK);
        customLamp(ModBlocks.IRON_SOURCE_BLOCK);
        customLamp(ModBlocks.COPPER_SOURCE_BLOCK);
    }
    private void customLamp(DeferredBlock<Block> currentBlock) {
        String currentName=currentBlock.getId().getPath().toLowerCase();
        getVariantBuilder(currentBlock.get()).forAllStates(state -> {
            if(state.getValue(Source_block.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(currentName,
                        ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName)))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll(currentName+"1",
                        ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+"1")))};
            }
        });

        simpleBlockItem(currentBlock.get(), models().cubeAll(currentName,//модель
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName)));
    }

    private void blockWithItem (DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(),cubeAll(deferredBlock.get()));
    }
}
