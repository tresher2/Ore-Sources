package com.tresher.oresorces.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class Redstone_source_block extends RedStoneOreBlock {
    public static final IntegerProperty PLACED_DAY = IntegerProperty.create("placed_day",0,511);
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0,3);
    public static final int MAX_AGE = 3;
    public static final int COUNT_DAYS_TO_STAGE=1;
    //public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public Redstone_source_block(Properties properties){
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(PLACED_DAY,0)
                .setValue(AGE,1)

        );
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if(!level.isClientSide){
            level.setBlockAndUpdate(pos,state
                    .setValue(PLACED_DAY, (int)((level.getGameTime() / 24000L) % 512L - COUNT_DAYS_TO_STAGE)
            ));
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < MAX_AGE || state.getValue(LIT);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AGE)>=MAX_AGE){
            super.randomTick(state, level, pos, random);
            return;
        }
        int daysPassed = ( (int)((level.getGameTime() / 24000L) % 512L) - state.getValue(PLACED_DAY) + 512)%512;
        if(daysPassed>=COUNT_DAYS_TO_STAGE)
            if( (daysPassed/COUNT_DAYS_TO_STAGE)!=state.getValue(AGE) ){
                level.setBlockAndUpdate(pos,state
                        .setValue( AGE,Math.min(MAX_AGE,daysPassed/COUNT_DAYS_TO_STAGE ))
                        .setValue(LIT, false)
                );
                return;
            }


        super.randomTick(state, level, pos, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
       builder.add(PLACED_DAY,AGE, LIT);
    }
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(player.isCreative())
            return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);

        int currentAGE=state.getValue(AGE);
        if(currentAGE>=1){
            if (player.getMainHandItem().is(net.minecraft.tags.ItemTags.PICKAXES))
                dropResources(state, level, pos, null, player, player.getMainHandItem());
            if(currentAGE==MAX_AGE)
                level.setBlockAndUpdate(pos,state
                        .setValue(AGE, currentAGE-1)
                        .setValue(PLACED_DAY, (int)((level.getGameTime() / 24000L - COUNT_DAYS_TO_STAGE*(MAX_AGE-1) + 512L ) % 512L) )
                        );
            else
                level.setBlockAndUpdate(pos,state
                        .setValue(LIT, currentAGE>1 ? state.getValue(LIT) : false)
                        .setValue(AGE, currentAGE-1)
                        .setValue(PLACED_DAY, (state.getValue(PLACED_DAY)+COUNT_DAYS_TO_STAGE) % 512  )
                );
        }
        return false;

    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if(state.getValue(AGE)==0)return 0;
        return super.getDestroyProgress(state, player, level, pos);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if(state.getValue(AGE)==0)return;
        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if(state.getValue(AGE)==0)return;
        super.attack(state, level, pos, player);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(state.getValue(AGE)==0)return ItemInteractionResult.FAIL;
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

}
