package com.tresher.oresorces.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class copper_source_block extends Block {
    //public static final IntegerProperty MOISTURE = BlockStateProperties.MOISTURE;
    public copper_source_block(Properties properties){
        super(properties);
        //this.registerDefaultState(this.stateDefinition.any().setValue(MOISTURE, Integer.valueOf(0)));
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        //int i = state.getValue(MOISTURE);
        //level.setBlock(pos, state.setValue(MOISTURE, Integer.valueOf(i - 1)), 2);
        //level.playSound(null,pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS,1f,1f);
        return false;
        //return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
