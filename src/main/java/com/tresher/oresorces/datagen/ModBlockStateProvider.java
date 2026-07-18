package com.tresher.oresorces.datagen;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.tresher.oresorces.OreSources;
import com.tresher.oresorces.block.ModBlocks;
import com.tresher.oresorces.block.custom.FullGrowth_Source_block;
import com.tresher.oresorces.block.custom.Redstone_source_block;
import com.tresher.oresorces.block.custom.Source_block;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.slf4j.Logger;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OreSources.MOD_ID, exFileHelper);
    }
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    protected void registerStatesAndModels() {
        customSource(ModBlocks.IRON_SOURCE_BLOCK);
        customSource(ModBlocks.COPPER_SOURCE_BLOCK);
        customSource(ModBlocks.ZINC_SOURCE_BLOCK);
        customSource(ModBlocks.GOLD_SOURCE_BLOCK);
        customSource(ModBlocks.COAL_SOURCE_BLOCK);
        customSource(ModBlocks.DIAMOND_SOURCE_BLOCK);
        customSource(ModBlocks.EMERALD_SOURCE_BLOCK);
        customSourceWithFinalBottomTop(ModBlocks.NETHER_QUARTZ_SOURCE_BLOCK);
        customSource(ModBlocks.LAPIS_SOURCE_BLOCK);
        customSource(ModBlocks.NETHER_GOLD_SOURCE_BLOCK);
        customSourceGrowingTop(ModBlocks.ANCIENT_DEBRIS_SOURCE_BLOCK);
        customSource(ModBlocks.REDSTONE_SOURCE_BLOCK);

    }
    private void customSource(DeferredBlock<Block> currentBlock) {
        String currentName=currentBlock.getId().getPath().toLowerCase();
        var builder = getVariantBuilder(currentBlock.get());

        var info = new currentBlockInfo(currentBlock);

        for (int age = 0; age <= info.MAX_AGE; age++) {
            builder.partialState()
                    .with(info.AGE, age)
                    .setModels(new ConfiguredModel(models().cubeAll(
                            currentName+age,
                            ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age)
                    )));
        }
        simpleBlockItem(currentBlock.get(), models().cubeAll(currentName+"2",//модель
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+"2")));
    }
    private void customSourceWithFinalBottomTop(DeferredBlock<Block> currentBlock) {
        String currentName=currentBlock.getId().getPath().toLowerCase();
        var builder = getVariantBuilder(currentBlock.get());

        var info = new currentBlockInfo(currentBlock);

        for (int age = 0; age <= info.MAX_AGE; age++) {
            builder.partialState()
                    .with(info.AGE, age)
                    .setModels(
                            (age!=info.MAX_AGE) ?
                            new ConfiguredModel(models().cubeAll(
                                    currentName+age,
                                    ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age)
                            )):
                            new ConfiguredModel(models().cubeBottomTop(
                                    currentName+age,
                                    ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age + "_side"),
                                    ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age + "_top"),
                                    ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age + "_top")
                            ))

                    );
        }
        simpleBlockItem(currentBlock.get(), models().cubeAll(currentName+"2",//модель
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+"2")));
    }
    private void customSourceGrowingTop(DeferredBlock<Block> currentBlock) {
        String currentName=currentBlock.getId().getPath().toLowerCase();
        var builder = getVariantBuilder(currentBlock.get());

        var info = new currentBlockInfo(currentBlock);

        for (int age = 0; age <= info.MAX_AGE; age++) {
            //LOGGER.warn("block/" + currentName+age + "_side");
            builder.partialState()
                    .with(info.AGE, age)
                    .setModels(new ConfiguredModel(models().cubeBottomTop(
                            currentName+age,
                            ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age + "_side"),
                            ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+info.MAX_AGE + "_top"),
                            ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age + "_top")
                    )));
        }
        simpleBlockItem(currentBlock.get(), models().cubeBottomTop(currentName+info.MAX_AGE,//модель
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+info.MAX_AGE + "_side"),
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+info.MAX_AGE + "_top"),
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+info.MAX_AGE + "_top")
        ));
    }
    private class currentBlockInfo{
        public IntegerProperty AGE;
        public int MAX_AGE;
        currentBlockInfo(DeferredBlock<Block> currentBlock){
            switch (currentBlock.get()) {
                case Redstone_source_block ignored:
                    AGE = Redstone_source_block.AGE;
                    MAX_AGE = Redstone_source_block.MAX_AGE;
                    break;
                case FullGrowth_Source_block ignored:
                    AGE = FullGrowth_Source_block.AGE;
                    MAX_AGE = FullGrowth_Source_block.MAX_AGE;
                    break;
                default:
                    AGE = Source_block.AGE;
                    MAX_AGE = Source_block.MAX_AGE;
                    break;
            }
        }
    }

    /*private void blockWithItem (DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(),cubeAll(deferredBlock.get()));
    }*/
        /*private void customSourceWithBottomTop(DeferredBlock<Block> currentBlock) {
        String currentName=currentBlock.getId().getPath().toLowerCase();
        var builder = getVariantBuilder(currentBlock.get());
        for (int age = 0; age <= Source_block.MAX_AGE; age++) {
            builder.partialState()
                    .with(Source_block.AGE, age)
                    .setModels(new ConfiguredModel(models().cubeBottomTop(
                            currentName+age,
                            ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age + "_side"),
                            ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age + "_top"),
                            ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+age + "_top")
                    )));
        }
        simpleBlockItem(currentBlock.get(), models().cubeBottomTop(currentName+"2",//модель
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+"2" + "_side"),
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+"2" + "_top"),
                ResourceLocation.fromNamespaceAndPath(OreSources.MOD_ID, "block/" + currentName+"2" + "_top")
        ));
    }*/
}
