package com.tresher.oresorces.block.custom;

import com.mojang.serialization.MapCodec;
import com.tresher.oresorces.block.entity.Source_blockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class Source_block extends BaseEntityBlock {
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0,3);
    public static final MapCodec<Source_block> CODEC =simpleCodec(Source_block::new);
    public static final short MAX_AGE = 3;

    //public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public Source_block(Properties properties){
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                /*.setValue(PLACED_DAY,0)*/
                .setValue(AGE,1)

        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    /* BlockEntity*/
    protected RenderShape getRenderShape(BlockState state){
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new Source_blockEntity(pos,state);
    }

    /*@Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock()!= newState.getBlock()){
            if(level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
                source_blockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }*/

    /* Block*/

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < MAX_AGE;
    }
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(!level.isClientSide && level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
            source_blockEntity.setPlacedDay(level.getGameTime(),1);
            level.setBlockAndUpdate(pos,state.setValue(AGE, 1));
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
            int age = source_blockEntity.getAge(level.getGameTime());
            if(age>=state.getValue(AGE))
                level.setBlockAndUpdate(pos,state
                        .setValue( AGE,Math.min(MAX_AGE,age ))
                );
        }


        super.randomTick(state, level, pos, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
       builder.add(/*PLACED_DAY,*/AGE);
    }
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(player.isCreative())
            return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);

        int currentAGE=state.getValue(AGE);
        if(currentAGE>=1){
            if (player.getMainHandItem().is(net.minecraft.tags.ItemTags.PICKAXES))
                dropResources(state, level, pos, null, player, player.getMainHandItem());
            if(currentAGE==MAX_AGE) {
                level.setBlockAndUpdate(pos, state.setValue(AGE, currentAGE - 1));
                if(level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
                    source_blockEntity.setPlacedDay(level.getGameTime(),1,MAX_AGE);
                }
            }
            else{
                if(level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
                    source_blockEntity.remove1Stage();
                }
                level.setBlockAndUpdate(pos,state.setValue(AGE, currentAGE-1));
            }

        }
        return false;

    }


    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if(state.getValue(AGE)==0)return 0;
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }


}
