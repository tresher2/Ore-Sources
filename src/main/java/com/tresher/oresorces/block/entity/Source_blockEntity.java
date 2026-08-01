package com.tresher.oresorces.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class Source_blockEntity extends BlockEntity {
    private static final short COUNT_DAYS_TO_STAGE=1;

    private short placed_day; //%32768
    /*public int getPlacedDay(){
        return placed_day;
    }*/
    public int getAge(long current_time){
        return (((short) ((current_time/ 24000L)%32768) - placed_day + 32768)%32768)/COUNT_DAYS_TO_STAGE;
        //how many days passed(in 0-32767) / days to stage
    }
    /*public void setPlacedDay(long placed_time){
        placed_day= (short) ((placed_time/ 24000L)%32768);
    }*/
    public void setPlacedDay(long placed_time, int stage){
        placed_day= (short) (((placed_time / 24000L)+32768-( stage * COUNT_DAYS_TO_STAGE ))%32768);
    }
    public void setPlacedDay(long placed_time, int minusStages, int maxAge){
        placed_day= (short) (((placed_time / 24000L)+32768-( (maxAge-minusStages) * COUNT_DAYS_TO_STAGE ))%32768);
    }
    public void remove1Stage(){
        placed_day=(short)((placed_day+COUNT_DAYS_TO_STAGE)%32768);
    }
    public Source_blockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOURCE_BLOCK_BE.get(), pos, blockState);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putShort("placedDay",placed_day);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        placed_day = tag.getShort("placedDay");
    }
}
