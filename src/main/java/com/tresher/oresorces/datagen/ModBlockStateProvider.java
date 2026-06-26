package com.tresher.oresorces.datagen;

import com.tresher.oresorces.OreSources;
import com.tresher.oresorces.block.ModBlocks;
import com.tresher.oresorces.block.custom.Source_block;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
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
        customSource(ModBlocks.IRON_SOURCE_BLOCK);
        customSource(ModBlocks.COPPER_SOURCE_BLOCK);
        customSource(ModBlocks.ZINC_SOURCE_BLOCK);
        customSource(ModBlocks.GOLD_SOURCE_BLOCK);

    }
    private void customSource(DeferredBlock<Block> currentBlock) {
        String currentName=currentBlock.getId().getPath().toLowerCase();
        var builder = getVariantBuilder(currentBlock.get());
        for (int age = 0; age <= Source_block.MAX_AGE; age++) {
            builder.partialState()
                    .with(Source_block.AGE, age)
                    .setModels(new ConfiguredModel(models().cubeAll(
                            currentName+age,
                            ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age)
                    )));
        }
        simpleBlockItem(currentBlock.get(), models().cubeAll(currentName+"2",//модель
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+"2")));
    }

    private void blockWithItem (DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(),cubeAll(deferredBlock.get()));
    }
}
