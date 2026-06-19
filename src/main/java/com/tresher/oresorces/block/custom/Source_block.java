package com.tresher.oresorces.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class Source_block extends Block {
    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public Source_block(Properties properties){
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(CLICKED,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
       builder.add(CLICKED);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!stack.is(net.minecraft.tags.ItemTags.PICKAXES))return ItemInteractionResult.FAIL;

        if(!level.isClientSide){
            boolean currentState=state.getValue(CLICKED);
            if(!state.getValue(CLICKED)){
                level.setBlockAndUpdate(pos,state.setValue(CLICKED, !currentState));
                dropResources(state,level,pos);
                stack.hurtAndBreak(1, (ServerLevel) level, player,
                        item -> player.onEquippedItemBroken(item, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND)
                );
                level.playSound(null,pos, SoundEvents.STONE_BREAK, SoundSource.BLOCKS);
                return ItemInteractionResult.SUCCESS;
            }

        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

}
