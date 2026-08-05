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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class FullGrowth_Source_block extends Source_block {
    //public static final IntegerProperty AGE = IntegerProperty.create("age", 0,3);
    //public static final MapCodec<Source_block> CODEC =simpleCodec(Source_block::new);

    public FullGrowth_Source_block(Properties properties){
        super(properties);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(player.isCreative())
            return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);

        int currentAGE=state.getValue(AGE);
        if(currentAGE==MAX_AGE){
            level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
            if (player.getMainHandItem().is(net.minecraft.tags.ItemTags.PICKAXES))
                dropResources(state, level, pos, null, player, player.getMainHandItem());
            if(level.getBlockEntity(pos) instanceof Source_blockEntity source_blockEntity){
                source_blockEntity.setPlacedDay(level.getGameTime(),0);
            }
        }
        return false;

    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if(state.getValue(AGE)!=MAX_AGE)return 0;
        return super.getDestroyProgress(state, player, level, pos);
    }

}
